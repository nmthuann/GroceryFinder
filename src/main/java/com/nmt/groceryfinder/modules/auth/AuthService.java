package com.nmt.groceryfinder.modules.auth;

import com.nmt.groceryfinder.common.dtos.Payload;
import com.nmt.groceryfinder.common.dtos.Tokens;
import com.nmt.groceryfinder.common.enums.RoleEnum;
import com.nmt.groceryfinder.common.enums.SubjectMailEnum;
import com.nmt.groceryfinder.common.messages.AuthMessages;
import com.nmt.groceryfinder.exceptions.ModuleException;
import com.nmt.groceryfinder.exceptions.messages.AuthExceptionMessages;
import com.nmt.groceryfinder.exceptions.messages.UsersModuleExceptionMessages;
import com.nmt.groceryfinder.modules.auth.dtos.requests.*;
import com.nmt.groceryfinder.modules.auth.dtos.responses.AuthenticationResponseDto;
import com.nmt.groceryfinder.modules.auth.dtos.responses.LoginResponseDto;
import com.nmt.groceryfinder.modules.auth.dtos.responses.RegisterAdminResponseDto;
import com.nmt.groceryfinder.modules.auth.dtos.responses.RegisterResponseDto;
import com.nmt.groceryfinder.modules.users.domain.mappers.UserMapper;
import com.nmt.groceryfinder.modules.users.domain.model.dtos.AccountDto;
import com.nmt.groceryfinder.modules.users.domain.model.dtos.EmployeeDto;
import com.nmt.groceryfinder.modules.users.domain.model.dtos.UserDto;
import com.nmt.groceryfinder.modules.users.services.IEmployeeService;
import com.nmt.groceryfinder.modules.users.services.IUserService;
import com.nmt.groceryfinder.shared.passport.impl.PassportContext;
import com.nmt.groceryfinder.shared.redis.RedisService;
import com.nmt.groceryfinder.utils.DefaultValueUtil;
import com.nmt.groceryfinder.utils.JwtServiceUtil;
import com.nmt.groceryfinder.utils.MailServiceUtil;
import com.nmt.groceryfinder.utils.PasswordUtil;
import jakarta.mail.MessagingException;
import jakarta.security.auth.message.AuthException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;

@Service
public class AuthService implements IAuthService {

    private static final String BASE_STRING_NUMERIC = "0123456789";

    private final PassportContext passportContext;
    private final JwtServiceUtil jwtServiceUtil;
    private final PasswordUtil passwordUtil;
    private final MailServiceUtil mailServiceUtil;
    private final IUserService userService;
    private final UserMapper userMapper;
    private final RedisService redisService;
    private final IEmployeeService employeeService;


    @Autowired
    public AuthService(
            IUserService userService,
            UserMapper userMapper,
            PassportContext passportContext,
            JwtServiceUtil jwtServiceUtil,
            PasswordUtil passwordUtil,
            MailServiceUtil mailServiceUtil,
            RedisService redisService,
            IEmployeeService employeeService
            ) {
        this.passportContext = passportContext;
        this.userMapper = userMapper;
        this.jwtServiceUtil = jwtServiceUtil;
        this.passwordUtil = passwordUtil;
        this.mailServiceUtil = mailServiceUtil;
        this.userService = userService;
        this.redisService = redisService;
        this.employeeService = employeeService;
    }


    @Override
    public LoginResponseDto login(LoginRequestDto data) throws AuthException {
        Payload validateAccount = passportContext.executeStrategy(
                data.authMethodId(),
                new PassportDto(data.firstParameter(), data.secondParameter())
        );
        if (validateAccount == null) {
            throw new AuthException(AuthExceptionMessages.LOGIN_FAILED.getMessage());
        }
        Optional<UserDto> getUser = this.userService.getOneById(validateAccount.userId());
        if (getUser.isEmpty()) {
            throw new AuthException(
                    UsersModuleExceptionMessages.ACCOUNT_NOT_FOUND.getMessage() + validateAccount.userId());
        }
        Tokens generateTokens = this.jwtServiceUtil.getTokens(validateAccount);

        return new LoginResponseDto(
                validateAccount.email(),
                getUser.get().getFirstName(),
                getUser.get().getLastName(),
                getUser.get().getAvatarURL(),
                getUser.get().getAddress(),
                generateTokens.accessToken(),
                generateTokens.refreshToken()
        );
    }

    @Override
    @Transactional
    public RegisterResponseDto register(RegisterRequestDto data) throws AuthException {
        Optional<AccountDto> findAccount = this.userService.getAccountUserByEmail(data.email());
        if (findAccount.isPresent()) {
            throw new AuthException(AuthExceptionMessages.EMAIL_EXIST.getMessage());
        }
        String hashedPassword = this.passwordUtil.hashPassword(data.password());
        String gender = data.gender() != null && !data.gender().isEmpty() ? data.gender() : "";
        String avatarUrl = data.avatarUrl() != null && !data.avatarUrl().isEmpty() ? data.avatarUrl() : "";
        Date birthday = data.birthday() != null ? data.birthday() : DefaultValueUtil.getDefaultBirthday();
        CreateUserDto createUserDto  = new CreateUserDto(
                data.email(),
                hashedPassword,
                "",
                avatarUrl,
                data.firstName(),
                data.lastName(),
                gender,
                birthday,
                data.phone(),
                data.address(),
                data.authMethodId(),
                data.roleId()
        );
        UserDto userCreated = this.userService.createOne(createUserDto);
        Tokens tokens = this.jwtServiceUtil.getTokens(new Payload(
                userCreated.getId(),
                data.email(),
                RoleEnum.USER.name()
        ));
        return new RegisterResponseDto (
                userCreated.getEmail(),
                userCreated.getFirstName(),
                userCreated.getLastName(),
                userCreated.getAvatarURL(),
                userCreated.getAddress(),
                tokens.accessToken(),
                tokens.refreshToken()
        );
    }

    @Override
    public AuthenticationResponseDto verifyEmail(VerifyEmailRequestDto data) throws ModuleException {
        String otpCode = this.passwordUtil.randomPassword(6, BASE_STRING_NUMERIC);
        String subject = SubjectMailEnum.VERIFY_EMAIL_SUBJECT.getSubject();
        String htmlContent = this.mailServiceUtil.generateVerificationEmailContent(data.email(), otpCode);
        try{
            Optional<AccountDto> findAccount = this.userService.getAccountUserByEmail(data.email());
            if(findAccount.isPresent()){
                throw new ModuleException(AuthExceptionMessages.EMAIL_EXIST.getMessage());
            }
            String key = "user:" + data.email() + ":otp";
            redisService.setCacheWithExpiration(key, otpCode,5);
            this.mailServiceUtil.sendHtmlEmail(data.email(), subject, htmlContent);
            return new AuthenticationResponseDto(
                    true,
                    AuthMessages.VERIFY_EMAIL_SUCCESS.getMessage()
            );
        }catch (ModuleException e) {
            throw e;
        } catch (MessagingException | RuntimeException e) {
            throw new RuntimeException(AuthExceptionMessages.VERIFY_MAIL_FAILED.getMessage(), e);
        }
    }

    @Override
    public AuthenticationResponseDto checkOtp(CheckOtpRequestDto data) throws ModuleException {
        String key = redisService.generateKey("user", data.email(), "otp");
        String cachedOtp = redisService.getCache(key).toString();
        if (cachedOtp != null && cachedOtp.equals(data.otp())) {
            redisService.clearCache(key);
            return new AuthenticationResponseDto(true, AuthMessages.OTP_VALID.getMessage());
        } else {
            throw new ModuleException(AuthExceptionMessages.OTP_INVALID.getMessage());
        }
    }

    @Override
    public AuthenticationResponseDto resetPassword(String email) {
        try {
            Optional<AccountDto> getAccount = userService.getAccountUserByEmail(email);
            if (getAccount.isPresent()) {
                throw new AuthException(AuthExceptionMessages.EMAIL_EXIST.getMessage());
            } else {
                String defaultPassword = this.passwordUtil.randomPassword(8, BASE_STRING_NUMERIC);
                String subject = SubjectMailEnum.RESET_PASSWORD_SUBJECT.getSubject();
                String content = this.mailServiceUtil.generateResetPasswordContent(defaultPassword);
                this.mailServiceUtil.sendEmail(email, subject, content);
                return new AuthenticationResponseDto(
                        true,
                        AuthMessages.RESET_PASSWORD_SUCCESS.getMessage()
                );
            }
        } catch (RuntimeException | AuthException ex) {
            throw new RuntimeException(ex.getMessage());
        }
    }

    @Override
    public AuthenticationResponseDto changePassword(String email) {
        return null;
    }

    @Override
    public AuthenticationResponseDto forgetPassword(String email) {
        return null;
    }

    @Override
    public AuthenticationResponseDto logout(String email) {
        return null;
    }

    @Override
    @Transactional
    public RegisterAdminResponseDto registerAdmin (
            RegisterAdminRequestDto data
    ) throws AuthException, ModuleException, MessagingException {
        Optional<AccountDto> findAccount = this.userService.getAccountUserByEmail(data.email());
        if (findAccount.isPresent()) {
            throw new AuthException(AuthExceptionMessages.EMAIL_EXIST.getMessage());
        }
        String randomPassword = this.passwordUtil.randomPassword(8, BASE_STRING_NUMERIC);
        String htmlContent = this.mailServiceUtil.generateAdminRegistrationEmailContent(data.email(), randomPassword);
        String subject = SubjectMailEnum.REGISTER_ADMIN_SUBJECT.getSubject();
        this.mailServiceUtil.sendHtmlEmail(data.email(), subject, htmlContent);
        String hashedPassword = this.passwordUtil.hashPassword(randomPassword);
        CreateUserDto createUserDto  = new CreateUserDto(
                data.email(),
                hashedPassword,
                "",
                data.avatarURL(),
                data.firstName(),
                data.lastName(),
                data.gender(),
                data.birthday(),
                data.phone(),
                data.address(),
                data.authMethodId(),
                data.roleId()
        );
        UserDto userCreated = this.userService.createOne(createUserDto);
        Tokens tokens = this.jwtServiceUtil.getTokens(
                new Payload(
                        userCreated.getId(),
                        data.email(),
                        RoleEnum.ADMIN.name()
                ));
        CreateEmployeeDto createEmployee = new CreateEmployeeDto(
                data.cccd(),
                data.salary(),
                data.position()
        );
        EmployeeDto employeeCreated = this.employeeService.createOne(
                this.userMapper.toEntity(userCreated),
                createEmployee
        );
        return new RegisterAdminResponseDto (
                userCreated.getEmail(),
                userCreated.getFirstName(),
                userCreated.getLastName(),
                userCreated.getAvatarURL(),
                userCreated.getAddress(),
                tokens.accessToken(),
                tokens.refreshToken(),
                employeeCreated.getPosition()
        );
    }
}
package com.nmt.groceryfinder.modules.users.user;

import com.nmt.groceryfinder.modules.users.user.dtos.ProfileDto;
import com.nmt.groceryfinder.modules.users.user.dtos.UserDto;
import com.nmt.groceryfinder.shared.logging.LoggingInterceptor;
import com.nmt.groceryfinder.shared.ratelimit.RateLimiter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;


import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/v1/users")
@Tag(name = "Users")
public class UserController {
    private final IUserService userService;

    @Autowired
    public UserController(IUserService userService){
        this.userService = userService;
    }

    @GetMapping("/{id}")
    @LoggingInterceptor
    public ResponseEntity<Optional<UserDto>> getOneById(@PathVariable UUID id) {
        Optional<UserDto> userDto = this.userService.getOneById(id);
        return new ResponseEntity<>(userDto, HttpStatus.OK);
    }


    @GetMapping("/me")
    @LoggingInterceptor
    @RateLimiter
    public ResponseEntity<ProfileDto> getOneByAccessToken(
            @AuthenticationPrincipal UserDetails userDetails
    ) {
        ProfileDto profileDto = this.userService.getProfileByEmail(userDetails.getUsername())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Profile not found"));
        return new ResponseEntity<>(profileDto, HttpStatus.OK);
    }


    @GetMapping("")
    @LoggingInterceptor
    @PreAuthorize("hasRole('ADMIN')")
    @RateLimiter
    public ResponseEntity<?> getAllPaginated (
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        Pageable pageable = PageRequest.of(page, size);
        Page<UserDto> roles = this.userService.getAllPaginated(pageable);
        return new ResponseEntity<>(roles, HttpStatus.OK);
    }
}

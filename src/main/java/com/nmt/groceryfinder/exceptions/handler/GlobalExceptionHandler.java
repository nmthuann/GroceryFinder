package com.nmt.groceryfinder.exceptions.handler;

import com.nmt.groceryfinder.exceptions.ModuleException;
import com.nmt.groceryfinder.exceptions.RestErrorResponse;
import io.jsonwebtoken.JwtException;
import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;


@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {
    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException ex,
            HttpHeaders headers,
            HttpStatusCode status,
            WebRequest request
    ) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        String path = request.getContextPath();
        String message = ex.getBindingResult().getFieldErrors().get(0).getDefaultMessage();
        RestErrorResponse re = new RestErrorResponse(
                status.value(),
                HttpStatus.BAD_REQUEST.getReasonPhrase(),
                "Method Argument Not Valid!",
                message,
                errors.toString(),
                LocalDateTime.now(),
                path
        );
        return new ResponseEntity<>(re, status);
    }



    /**
     * Handles IllegalStateException, typically thrown when an action is attempted that is illegal in the
     * current state, such as when a security violation occurs.
     *
     * @param request the HttpServletRequest that resulted in the exception
     * @param ex the exception that was thrown
     * @return a ResponseEntity containing a RestErrorResponse with a 403 Forbidden status
     */
    @ExceptionHandler(IllegalStateException.class)
    @ResponseBody
    public ResponseEntity<RestErrorResponse> handleAccessDeniedException(
            HttpServletRequest request,
            IllegalStateException ex
    ) {
        HttpStatus statusCode = HttpStatus.FORBIDDEN;
        String path = request.getRequestURI();
        String method = request.getMethod();
        String message = "IllegalStateException: " + ex.getMessage();
        logger.error("Access Denied on {} {}: {}", method, path, ex.getMessage());
        RestErrorResponse re = new RestErrorResponse(
                statusCode.value(),
                statusCode.getReasonPhrase(),
                "Spring Security Access Denied Error",
                message,
                "IllegalStateException::: " + message,
                LocalDateTime.now(),
                path
        );
        return new ResponseEntity<>(re, statusCode);
    }

    /**
     * Handles ServletException, usually thrown when a servlet encounters difficulty.
     *
     * @param request the HttpServletRequest that resulted in the exception
     * @param ex the exception that was thrown
     * @return a ResponseEntity containing a RestErrorResponse with a 500 Internal Server Error status
     */
    @ExceptionHandler(ServletException.class)
    @ResponseBody
    public ResponseEntity<RestErrorResponse> handleServletException(
            HttpServletRequest request,
            ServletException ex
    ) {
        HttpStatus statusCode = HttpStatus.INTERNAL_SERVER_ERROR;
        String path = request.getRequestURI();

        // Log the exception details
        logger.error("Servlet Exception on {}: {}", path, ex.getMessage());

        RestErrorResponse re = new RestErrorResponse(
                statusCode.value(),
                statusCode.getReasonPhrase(),
                ServletException.class.getName(),
                ex.getMessage(),
                "",
                LocalDateTime.now(),
                path
        );
        return new ResponseEntity<>(re, statusCode);
    }

    /**
     * Handles JwtException, typically thrown when there is an issue with JWT authentication.
     *
     * @param request the HttpServletRequest that resulted in the exception
     * @param ex the exception that was thrown
     * @return a ResponseEntity containing a RestErrorResponse with a 401 Unauthorized status
     */
    @ExceptionHandler(JwtException.class)
    @ResponseBody
    public ResponseEntity<RestErrorResponse> handleJwtException(HttpServletRequest request, JwtException ex) {
        HttpStatus statusCode = HttpStatus.UNAUTHORIZED;
        String path = request.getRequestURI();
        logger.error("JWT Exception on {}: {}", path, ex.getMessage());
        RestErrorResponse re = new RestErrorResponse(
                statusCode.value(),
                statusCode.getReasonPhrase(),
                JwtException.class.getName(),
                ex.getMessage(),
                "",
                LocalDateTime.now(),
                path
        );
        return new ResponseEntity<>(re, statusCode);
    }

    /**
     * Handles EntityNotFoundException, typically thrown when a requested entity is not found in the database.
     *
     * @param request the HttpServletRequest that resulted in the exception
     * @param ex the exception that was thrown
     * @return a ResponseEntity containing a RestErrorResponse with a 404 Not Found status
     */
    @ExceptionHandler(EntityNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ResponseBody
    public ResponseEntity<RestErrorResponse> handleEntityNotFoundException(
            HttpServletRequest request,
            EntityNotFoundException ex) {

        HttpStatus statusCode = HttpStatus.NOT_FOUND;
        String path = request.getRequestURI();

        // Log the exception details
        logger.error("Entity Not Found on {}: {}", path, ex.getMessage());

        RestErrorResponse re = new RestErrorResponse(
                statusCode.value(),
                statusCode.getReasonPhrase(),
                EntityNotFoundException.class.getName(),
                ex.getMessage(),
                "",
                LocalDateTime.now(),
                path
        );
        return new ResponseEntity<>(re, statusCode);
    }

    /**
     * Handles MethodArgumentTypeMismatchException, typically thrown when a method argument is not the expected type.
     *
     * @param ex the exception that was thrown
     * @param request the HttpServletRequest that resulted in the exception
     * @return a ResponseEntity containing a RestErrorResponse with a 400 Bad Request status
     */
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<RestErrorResponse> handleTypeMismatchException(
            MethodArgumentTypeMismatchException ex,
            HttpServletRequest request
    ) {
        String path = request.getRequestURI();
        logger.error("Method Argument Type Mismatch on {}: {}", path, ex.getMessage());
        RestErrorResponse error = new RestErrorResponse(
                HttpStatus.BAD_REQUEST.value(),
                HttpStatus.BAD_REQUEST.getReasonPhrase(),
                MethodArgumentTypeMismatchException.class.getName(),
                ex.getMessage(),
                "",
                LocalDateTime.now(),
                path
        );
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    /**
     * Handles ModuleException, a custom application exception.
     *
     * @param ex the exception that was thrown
     * @param request the HttpServletRequest that resulted in the exception
     * @return a ResponseEntity containing a RestErrorResponse with a 400 Bad Request status
     */
    @ExceptionHandler(ModuleException.class)
    @ResponseBody
    public ResponseEntity<RestErrorResponse> handleModuleException(
            ModuleException ex,
            HttpServletRequest request
    ) {
        HttpStatus statusCode = HttpStatus.BAD_REQUEST;
        String path = request.getRequestURI();
        logger.error("Module Exception on {}: {}", path, ex.getMessage());
        RestErrorResponse re = new RestErrorResponse (
                statusCode.value(),
                statusCode.getReasonPhrase(),
                ModuleException.class.getName(),
                ex.getMessage(),
                "Module Exception...",
                LocalDateTime.now(),
                path
        );
        return new ResponseEntity<>(re, statusCode);
    }

    /**
     * Handles JsonMappingException, typically thrown when there is a problem mapping JSON data.
     *
     * @param request the HttpServletRequest that resulted in the exception
     * @param ex the exception that was thrown
     * @return a ResponseEntity containing a RestErrorResponse with a 500 Internal Server Error status
     */
    @ExceptionHandler(com.fasterxml.jackson.databind.JsonMappingException.class)
    @ResponseBody
    public ResponseEntity<RestErrorResponse> handleJsonMappingException(
            HttpServletRequest request, com.fasterxml.jackson.databind.JsonMappingException ex) {

        HttpStatus statusCode = HttpStatus.INTERNAL_SERVER_ERROR;
        String path = request.getRequestURI();

        // Log the exception details
        logger.error("JSON Mapping Exception on {}: {}", path, ex.getOriginalMessage());

        RestErrorResponse re = new RestErrorResponse(
                statusCode.value(),
                statusCode.getReasonPhrase(),
                "JSON Mapping Error",
                ex.getMessage(),
                "Detail: " + ex.getOriginalMessage(),
                LocalDateTime.now(),
                path
        );
        return new ResponseEntity<>(re, statusCode);
    }

    /**
     * Handles AuthenticationException, typically thrown when authentication fails.
     *
     * @param ex the exception that was thrown
     * @return a ResponseEntity containing a RestError with a 401 Unauthorized status
     */
    @ExceptionHandler({ AuthenticationException.class })
    @ResponseBody
    public ResponseEntity<RestErrorResponse> handleAuthenticationException(
            AuthenticationException ex,
            HttpServletRequest request
    ) {
        HttpStatus statusCode = HttpStatus.UNAUTHORIZED;
        String path = request.getRequestURI();
        String message = request.getAttribute("message").toString();
        logger.error("Authentication Exception on {}: {}", path, ex.getMessage());
        RestErrorResponse re = new RestErrorResponse(
                statusCode.value(),
                statusCode.getReasonPhrase(),
                AuthenticationException.class.getName(),
                "Authentication Failed: " + message,
                "",
                LocalDateTime.now(),
                path
        );
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(re);
    }


    @ExceptionHandler(Exception.class)
    @ResponseBody
    public ResponseEntity<RestErrorResponse> handleException(HttpServletRequest request, Exception ex) {
        HttpStatus statusCode = HttpStatus.INTERNAL_SERVER_ERROR;
        RestErrorResponse re = new RestErrorResponse(
                statusCode.value(),
                statusCode.getReasonPhrase(),
                Exception.class.getName(),
                "Internal Server Error",
                ex.getMessage(),
                LocalDateTime.now(),
                request.getRequestURI()
        );
        return new ResponseEntity<>(re, statusCode);
    }
}





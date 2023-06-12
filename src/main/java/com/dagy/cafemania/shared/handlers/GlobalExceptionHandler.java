package com.dagy.cafemania.shared.handlers;


import com.dagy.cafemania.shared.exceptions.*;
import com.dagy.cafemania.shared.helpers.APIResponse;
import com.dagy.cafemania.shared.helpers.ApiDataResponse;
import com.dagy.cafemania.shared.helpers.ErrorDTO;
import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.DisabledException;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingPathVariableException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import static java.time.LocalDateTime.now;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final String FAILED = "Failed";

    @ExceptionHandler({RuntimeException.class, NullPointerException.class})
    public ResponseEntity<Object> handleRuntimeExceptions(RuntimeException exception) {
        log.error(exception.getMessage());

        ApiDataResponse response =  ApiDataResponse.builder()
                .time(now())
                .message(exception.getMessage())
                .httpStatus(HttpStatus.INTERNAL_SERVER_ERROR)
                .errors(Collections.singletonList(
                        new ErrorDTO(
                                "",
                                "Une erreur interne du serveur s'est produite")))
                .statusCode(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .build();

        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<Object> handleHttpRequestMethodNotSupportedException(HttpRequestMethodNotSupportedException exception) {

        log.error(exception.getMessage());

        APIResponse<ErrorDTO> response = new APIResponse<>();
        response.setStatus(FAILED);
        response.setErrors(Collections.singletonList(new ErrorDTO("", "L'URL demandée ne prend pas en charge cette méthode")));

        return new ResponseEntity<>(response, HttpStatus.METHOD_NOT_ALLOWED);
    }


    @ExceptionHandler(IllegalStateException.class)
    public ResponseEntity<ApiDataResponse> handleIllegalStateException(IllegalStateException exception) {
        log.error(exception.getMessage());
        return ResponseEntity.ok(
                ApiDataResponse.builder()
                        .time(now())
                        .message(exception.getMessage())
                        .httpStatus(HttpStatus.BAD_REQUEST)
                        .statusCode(HttpStatus.BAD_REQUEST.value())
                        .error(Map.of("errors", ResponseEntity.badRequest().body(exception.getMessage())))
                        .build()
        );
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<?> handleEntityNotFoundException(EntityNotFoundException exception) {
        log.error(exception.getMessage());

        ApiDataResponse response =  ApiDataResponse.builder()
                .time(now())
                .message(exception.getMessage())
                .httpStatus(HttpStatus.NOT_FOUND)
                .errors(Collections.singletonList(
                        new ErrorDTO(
                                "",
                                "La ressource demandée n'a pas été trouvée")))
                .statusCode(HttpStatus.NOT_FOUND.value())
                .build();
        return new ResponseEntity<>(response,  HttpStatus.NOT_FOUND);

    }

    @ExceptionHandler({
            ObjectNotValidException.class,
            MethodArgumentNotValidException.class,
            MissingServletRequestParameterException.class,
            MissingPathVariableException.class})
    public ResponseEntity<?> handleValidationExceptions(Exception exception, ObjectNotValidException except) {
        log.error(exception.getMessage());

        List<ErrorDTO> errors = new ArrayList<>();
        if (exception instanceof MethodArgumentNotValidException ex) {

            ex.getBindingResult().getAllErrors().forEach(error -> {
                String fieldName = ((FieldError) error).getField();
                String errorMessage = error.getDefaultMessage();
                errors.add(new ErrorDTO(fieldName, errorMessage));
            });

        } else if (exception instanceof MissingServletRequestParameterException ex) {

            String parameterName = ex.getParameterName();
            errors.add(new ErrorDTO("", "Le paramètre requis est manquant: " + parameterName));

        } else if (exception instanceof MissingPathVariableException ex) {

            String variableName = ex.getVariableName();
            errors.add(new ErrorDTO("", "Missing path variable: " + variableName));
        }
        else if (exception instanceof ObjectNotValidException ex) {
            ex.getErrorMessages().forEach( error -> {
                errors.add(
                        ErrorDTO.builder()
                                .field(except.getFields().toString())
                                .errorMessage(error)
                                .build());
            });
        }

        return ResponseEntity
                .badRequest()
                .body(ApiDataResponse.builder()
                        .time(now())
                        .message(FAILED)
                        .httpStatus(HttpStatus.BAD_REQUEST)
                        .statusCode(HttpStatus.BAD_REQUEST.value())
                        .errors(errors)
//                        .error(Map.of("Error", except.getErrorMessages()))
                        .build());

    }

    @ExceptionHandler({
            EntityAllReadyExistException.class,
            BusinessLogicException.class,
            DataAccessException.class})
    public ResponseEntity<?> handleException(Exception exception) {
        log.error(exception.getMessage());
        return ResponseEntity
                .badRequest()
                .body(ApiDataResponse.builder()
                        .time(now())
                        .message(FAILED)
                        .httpStatus(HttpStatus.BAD_REQUEST)
                        .statusCode(HttpStatus.BAD_REQUEST.value())
                        .errors(Collections.singletonList(
                                 ErrorDTO.builder()
                                         .errorMessage(exception.getMessage())
                                         .build()))
                        .statusCode(HttpStatus.NOT_FOUND.value())
                        .build());

    }


    @ExceptionHandler(DisabledException.class)
    public ResponseEntity<?> handleException(DisabledException exception) {
        log.error(exception.getMessage());
        return ResponseEntity
                .badRequest()
                .body(ApiDataResponse.builder()
                        .time(now())
                        .message(FAILED)
                        .httpStatus(HttpStatus.FORBIDDEN)
                        .statusCode(HttpStatus.FORBIDDEN.value())
                        .errors(Collections.singletonList(
                                ErrorDTO.builder()
                                        .errorMessage(exception.getMessage())
                                        .build()))
                        .build());

    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<?> handleException(ResourceNotFoundException exception) {
        log.error(exception.getMessage());
        return ResponseEntity
                .badRequest()
                .body(ApiDataResponse.builder()
                        .time(now())
                        .message(exception.getMessage())
                        .httpStatus(HttpStatus.NOT_FOUND)
                        .statusCode(HttpStatus.NOT_FOUND.value())
                        .errors(Collections.singletonList(
                                ErrorDTO.builder()
                                        .errorMessage(exception.getMessage())
                                        .build()))
                        .build());

    }

    @ExceptionHandler(UnauthorizedException.class)
    public ResponseEntity<?> handleException(UnauthorizedException exception) {
        log.error(exception.getMessage());
        return ResponseEntity
                .badRequest()
                .body(ApiDataResponse.builder()
                        .time(now())
                        .message(FAILED)
                        .httpStatus(HttpStatus.UNAUTHORIZED)
                        .statusCode(HttpStatus.UNAUTHORIZED.value())
                        .errors(Collections.singletonList(
                                ErrorDTO.builder()
                                        .errorMessage(exception.getMessage())
                                        .build()))
                        .build());

    }

    @ExceptionHandler(InvalidCredentialException.class)
    public ResponseEntity<?> handleException(InvalidCredentialException exception) {
        log.error(exception.getMessage());
        return ResponseEntity
                .badRequest()
                .body(ApiDataResponse.builder()
                        .time(now())
                        .message(FAILED)
                        .httpStatus(HttpStatus.BAD_REQUEST)
                        .statusCode(HttpStatus.BAD_REQUEST.value())
                        .errors(Collections.singletonList(
                                ErrorDTO.builder()
                                        .errorMessage(exception.getMessage())
                                        .build()))
                        .build());

    }

    @ExceptionHandler(IncorrectPasswordException.class)
    public ResponseEntity<?> handleException(IncorrectPasswordException exception) {
        log.error(exception.getMessage());
        return ResponseEntity
                .badRequest()
                .body(ApiDataResponse.builder()
                        .time(now())
                        .message(FAILED)
                        .httpStatus(HttpStatus.BAD_REQUEST)
                        .statusCode(HttpStatus.BAD_REQUEST.value())
                        .errors(Collections.singletonList(
                                ErrorDTO.builder()
                                        .errorMessage(exception.getMessage())
                                        .build()))
                        .build());

    }

    @ExceptionHandler(InvalidTokenException.class)
    public ResponseEntity<?> handleException(InvalidTokenException exception) {
        log.error(exception.getMessage());
        return ResponseEntity
                .badRequest()
                .body(ApiDataResponse.builder()
                        .time(now())
                        .message(FAILED)
                        .httpStatus(HttpStatus.BAD_REQUEST)
                        .statusCode(HttpStatus.BAD_REQUEST.value())
                        .errors(Collections.singletonList(
                                ErrorDTO.builder()
                                        .errorMessage(exception.getMessage())
                                        .build()))
                        .build());

    }


}

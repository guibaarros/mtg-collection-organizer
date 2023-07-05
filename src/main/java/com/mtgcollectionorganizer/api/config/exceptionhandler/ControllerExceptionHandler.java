package com.mtgcollectionorganizer.api.config.exceptionhandler;

import com.mtgcollectionorganizer.api.config.dto.ErrorDTO;
import com.mtgcollectionorganizer.api.user.collection.exception.UserCollectionNotFoundException;
import com.mtgcollectionorganizer.api.user.exception.UserAlreadyExistsException;
import com.mtgcollectionorganizer.api.user.exception.UserNotAuthorizedException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.Collections;
import java.util.List;

@ControllerAdvice
public class ControllerExceptionHandler {

    @ResponseBody
    @ExceptionHandler(value = {UserCollectionNotFoundException.class})
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public List<ErrorDTO> handleUserNotFoundException(final RuntimeException ex) {
        return Collections.singletonList(
                ErrorDTO.builder()
                        .code(HttpStatus.NOT_FOUND.toString())
                        .message(ex.getMessage())
                        .build()
        );
    }

    @ResponseBody
    @ExceptionHandler(value = {UserAlreadyExistsException.class})
    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    public List<ErrorDTO> handleUserAlreadyExistsException(final RuntimeException ex) {
        return Collections.singletonList(
                ErrorDTO.builder()
                        .code(HttpStatus.UNPROCESSABLE_ENTITY.toString())
                        .message(ex.getMessage())
                        .build()
        );
    }

    @ResponseBody
    @ExceptionHandler(value = {UserNotAuthorizedException.class})
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public List<ErrorDTO> handleUserNotAuthorizedException(final RuntimeException ex) {
        return Collections.singletonList(
                ErrorDTO.builder()
                        .code(HttpStatus.UNAUTHORIZED.toString())
                        .message(ex.getMessage())
                        .build()
        );
    }
}

package com.crio.coder_hack.advices;

import com.crio.coder_hack.exceptions.InvalidBadgesException;
import com.crio.coder_hack.exceptions.InvalidScoreException;
import com.crio.coder_hack.exceptions.ResourceNotFoundException;
import com.crio.coder_hack.exceptions.UsernameAlreadyExistsException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ApiError> handleResourceNotFoundException(ResourceNotFoundException exception){
        ApiError apiError=new ApiError(exception.getMessage(), HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(apiError,apiError.getHttpStatus());
    }

    @ExceptionHandler(UsernameAlreadyExistsException.class)
    public ResponseEntity<ApiError> handleUserAlreadyExistsException(UsernameAlreadyExistsException exception){
        ApiError apiError=new ApiError(exception.getMessage(), HttpStatus.CONFLICT);
        return new ResponseEntity<>(apiError,apiError.getHttpStatus());
    }

    @ExceptionHandler(InvalidScoreException.class)
    public ResponseEntity<ApiError> handleInvalidScoreException(InvalidScoreException exception){
        ApiError apiError=new ApiError(exception.getMessage(),HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>(apiError,apiError.getHttpStatus());
    }

    @ExceptionHandler(InvalidBadgesException.class)
    public ResponseEntity<ApiError> handleInvalidBadgesException(InvalidBadgesException exception){
        ApiError apiError=new ApiError(exception.getMessage(),HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>(apiError,apiError.getHttpStatus());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiError> handleInternalServerError(Exception exception){
        ApiError apiError=new ApiError(exception.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
        return new ResponseEntity<>(apiError,apiError.getHttpStatus());
    }

}

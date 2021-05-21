package com.example.bidding.exception;

import com.example.bidding.model.Problem;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import org.springframework.web.bind.annotation.ExceptionHandler;


/**
 * This class converts exceptions into problem pojo.
 * It made to simplify the exception cause
 */
@ControllerAdvice
public class BidderExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(IncompatibleParamException.class)
    public ResponseEntity<Problem> handleBadRequestException(RuntimeException runtimeException){
        Problem problem = new Problem();
        problem.setStatus(HttpStatus.BAD_REQUEST.value());
        problem.setTitle(HttpStatus.BAD_REQUEST.getReasonPhrase());
        problem.setMessage(runtimeException.getMessage());
        return new ResponseEntity<>(problem,HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(BidNotFoundException.class)
    public ResponseEntity<Problem> handleBidNotFoundException(RuntimeException runtimeException){
        Problem problem = new Problem();
        problem.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
        problem.setTitle(HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase());
        problem.setMessage(runtimeException.getMessage());
        return new ResponseEntity<>(problem,HttpStatus.INTERNAL_SERVER_ERROR);
    }

}

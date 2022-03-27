package com.travix.medusa.busyflights.buildingblocks.web;

import com.travix.medusa.busyflights.buildingblocks.exceptios.ArgumentNotValidException;
import org.hibernate.validator.internal.engine.path.PathImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@ControllerAdvice
public class WebExceptionHandler {

    // Targets @RequestParam
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<List<String>> handleValidationExceptions(ConstraintViolationException ex) {
        List<String> errors = new ArrayList<>(ex.getConstraintViolations().size());
        ex.getConstraintViolations().forEach(violation -> errors.add(getConstraintViolationMessage(violation)));
        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }

    // Simple implementation, messages would look quite raw this way :P
    private String getConstraintViolationMessage(ConstraintViolation<?> violation) {
        final String baseMessage = violation.getMessage();

        if (violation.getPropertyPath() instanceof PathImpl path) {
            return path.getLeafNode().asString() + " " + baseMessage;
        } else {
            return baseMessage;
        }
    }

    /**
     * Very raw implementation, it would report binding errors and will probably expose
     * sensitive information. e.g stacktraces
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(BindException.class)
    public ResponseEntity<List<String>> handleValidationExceptions(BindException ex) {
        List<String> errors = new ArrayList<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.add(fieldName + " " + errorMessage);
        });
        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(ArgumentNotValidException.class)
    public ResponseEntity<List<String>> handleValidationExceptions(ArgumentNotValidException ex) {
        return new ResponseEntity<>(Collections.singletonList(ex.getMessage()), HttpStatus.BAD_REQUEST);
    }

}

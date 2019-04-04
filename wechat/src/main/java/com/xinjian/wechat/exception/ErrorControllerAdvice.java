package com.freshman.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@ControllerAdvice(basePackages = {"com.xinjian.wechat.controller"})
public class ErrorControllerAdvice {
    private static final Logger LOG;

    static {
        LOG = LoggerFactory.getLogger(ErrorControllerAdvice.class);
    }

    @ExceptionHandler({
            DuplicateKeyException.class,
            DataIntegrityViolationException.class,
            DataAccessException.class,
            Exception.class
    })
    public ResponseEntity<ErrorMessage> handleException(Exception e) {
        LOG.error(e.getMessage(), e);

        if (e instanceof DuplicateKeyException) {
            return handleMessage("40001", e.getMessage());
        }

        if (e instanceof DataIntegrityViolationException) {
            return handleMessage("40002", e.getMessage());
        }

        if (e instanceof DataAccessException) {
            return handleMessage("40003", e.getMessage());
        }

        return handleMessage("40000", e.getMessage());
    }

    private ResponseEntity<ErrorMessage> handleMessage(String code, String message) {
        return ResponseEntity.badRequest().body(new ErrorMessage(code, message));
    }
}

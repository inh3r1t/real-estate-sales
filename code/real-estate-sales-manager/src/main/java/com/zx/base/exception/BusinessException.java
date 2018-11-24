package com.zx.base.exception;

/**
 * 业务类异常
 *
 * @author Xiafl.
 * @version 2017/12/08
 */
public class BusinessException extends RuntimeException {

    public BusinessException(String message) {
        super(message);
    }

    public BusinessException(Exception ex) {
        super(ex);
    }

    public BusinessException(String message, Exception e) {
        super(message, e);
    }

}

package com.zx.base.exception;

/**
 * @Author: ytxu3
 * @Description:
 * @Date: 2018/12/30 18:35
 */
public class WechatAuthException extends BusinessException {
    public WechatAuthException(String message) {
        super(message);
    }

    public WechatAuthException(Exception ex) {
        super(ex);
    }

    public WechatAuthException(String message, Exception e) {
        super(message, e);
    }
}

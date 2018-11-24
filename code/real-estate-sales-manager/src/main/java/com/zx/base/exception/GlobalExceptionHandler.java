package com.zx.base.exception;

import com.zx.base.model.ReturnModel;
import com.zx.lib.utils.LogUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

/**
 * 全局的异常处理类
 *
 * @author Xiafl.
 * @version 2017/12/08
 */
@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(value = BusinessException.class)
    @ResponseBody
    public ReturnModel jsonErrorHandler(HttpServletRequest req, BusinessException e) {
        ReturnModel returnModel = new ReturnModel();
        returnModel.setState(false);
        returnModel.setMessage("系统异常，请稍后再试");
        if (e.getCause() != null) {
            LogUtil.warn(returnModel.getMessage(), e.getCause());
        }
        return returnModel;
    }

    /**
     * hibernate validator 验证
     * 验证失败时会抛出 Model Bing 异常
     * 上层捕获异常，相应前台
     **/
    @ExceptionHandler(value = org.springframework.validation.BindException.class)
    @ResponseBody
    public ReturnModel handle(BindException exception) {
        ReturnModel returnModel = new ReturnModel();
        returnModel.setState(false);
        if (exception.hasFieldErrors()){
            FieldError fieldError = exception.getFieldError();
            //String msg = String.format("%s",fieldError.getDefaultMessage());
            returnModel.setType(ReturnModel.ReturnType.FormValidate);
            returnModel.setMessage(fieldError.getDefaultMessage());
        }
        if (exception.getCause() != null) {
            LogUtil.warn(returnModel.getMessage(), exception.getCause());
        }
        return returnModel;
    }
}

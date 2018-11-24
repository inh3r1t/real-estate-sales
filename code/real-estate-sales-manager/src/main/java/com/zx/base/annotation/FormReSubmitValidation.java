package com.zx.base.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 表单验证重复提交注解
 *
 * @author Xiafl.
 * @version 2018/02/28
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface FormReSubmitValidation {

    /**
     * 表示当前是验证方法
     **/
    boolean value() default false;
}

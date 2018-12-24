package com.zx.base.annotation;

import java.lang.annotation.*;

/**
 * 验证微信小程序的session
 */

@Documented
@Inherited
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface WechatAuthorize {

}

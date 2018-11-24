package com.zx.base.annotation;

import java.lang.annotation.*;

/**
 * 验证忽略 ： 不需要验证添加注释
 */

@Documented
@Inherited
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface AuthorizeIgnore {

}

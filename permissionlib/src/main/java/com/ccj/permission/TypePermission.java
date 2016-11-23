package com.ccj.permission;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by Administrator on 2016/11/23.
 */

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)

public @interface TypePermission {
    String[] value();

}

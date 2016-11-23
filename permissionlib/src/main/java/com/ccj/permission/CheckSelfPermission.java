package com.ccj.permission;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by Administrator on 2016/11/23.
 * 方法本身会涉及到的权限
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)

public @interface CheckSelfPermission {
    String[] value();

}

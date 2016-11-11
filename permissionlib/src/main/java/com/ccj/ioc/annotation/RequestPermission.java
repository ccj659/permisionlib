package com.ccj.ioc.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by Administrator on 2016/11/10.
 */


@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)

public @interface RequestPermission {



}

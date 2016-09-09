package com.efly.permissionlib;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by Administrator on 2016/8/15.
 *
 *   //1.方法中手动触发,注解 needRequest(); 可以手动放权限,也可自动遍历-->得到类

 //2.类中直接请求needRequest();

 //3.请求分为放入请求,自动遍历权限
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface NeedPermission {

    String[] value();

}

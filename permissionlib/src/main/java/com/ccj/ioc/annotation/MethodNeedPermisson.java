package com.ccj.ioc.annotation;

import android.view.View;

import com.efly.permissionlib.ListenEvent;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by Administrator on 2016/11/10.
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@ListenEvent(setListener="setOnClickListener"
        ,listenerType=View.OnClickListener.class
        ,callbackMethod="onClick")
public @interface MethodNeedPermisson {

    String[] value();


}

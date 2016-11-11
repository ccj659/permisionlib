package com.efly.permissionlib;

import android.view.View.OnClickListener;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)

@ListenEvent(setListener="setOnClickListener"
,listenerType=OnClickListener.class
,callbackMethod="onClick")


public @interface InjectEvent {
	int[] value();
}

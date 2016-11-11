package com.efly.permissionlib;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


@Retention(RetentionPolicy.RUNTIME) //保存策略为运行时
@Target(ElementType.TYPE)//目标为类

public @interface InjectLayout {
	//接口值
	int value();
}

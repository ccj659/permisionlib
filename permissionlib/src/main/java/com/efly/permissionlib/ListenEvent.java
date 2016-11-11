package com.efly.permissionlib;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.ANNOTATION_TYPE)


public @interface ListenEvent {
	/*事物的本质是一样的, 只不过实现的方法不一样.
	 * 就像Onclick事件.
	 * 要想实现监听,都要经过三个步骤.
	 * 1.监听类型-->需要ClickListener接口本身
	 * 2.设置监听-->有一个事务要setOnClickListener
	 * 3.回调方法-->事件被触发之后，执行的回调方法onClick
	 * 
	 * */
	
	/**
	 * 事件监听的类型
	 * @return
	 */
	String setListener(); //此处 可以用setOnClickListener ,setOnLongClickListener等等
	
	
	/**
	 * 设置监听
	 * @return
	 */
	Class<?> listenerType();//此处 可以用OnClickListener.class ,OnLongClickListener.class等等
	
	
	
	/**
	 * 设置回调方法
	 * @return
	 */
	String callbackMethod();
	
	
	
}

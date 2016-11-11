package com.efly.permissionlib;

import android.app.Activity;
import android.view.View;

import com.efly.permissionlib.InjectLayout;
import com.efly.permissionlib.InjectView;
import com.efly.permissionlib.ListenEvent;
import com.ccj.ioc.proxy.ListenerInvocationHandler;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.HashMap;
import java.util.Map;

public class InjectUtils {

	private static final String TAG = "InjectUtils";

	public static void inject(Activity activity) {
		// 注入布局
		injectLayout(activity);
		// 注入视图
		injectViews(activity);
		// 注入事件
		injectEvents(activity);

	}

	private static void injectLayout(Activity activity) {
		// TODO Auto-generated method stub
		Class<?> clazz = activity.getClass();
		// 获得指定class文件的注解
		InjectLayout annotation = clazz.getAnnotation(InjectLayout.class);
		// 获得注解上的资源id值
		int layout = annotation.value();
		// 让acitivity绑定View
		activity.setContentView(layout);
	}

	private static void injectViews(Activity activity) {
		// TODO Auto-generated method stub
		Class<?> clazz = activity.getClass();
		// 得到class上的所有成员变量
		Field[] fields = clazz.getDeclaredFields();
		// 遍历成员变量,得到变量上的注解
		for (Field field : fields) {
			InjectView injectView = field.getAnnotation(InjectView.class);
			if (injectView != null) {
				// 得到id
				int id = injectView.value();
				View view = activity.findViewById(id);
				try {
					// Attempts to set the accessible flag.
					field.setAccessible(true);

					// Sets the value of the field in the specified object to
					// the value.
					// This reproduces the effect of object.fieldName = value
					// field.set 是一个native方法
					// 意思就是在指定的对象,设置字段activity.field的值为view。字段名=值,将view赋值给带有该注解的变量
					// 例如TextView tv 带有注解,就让activity.tv和找到的view关联起来;
					field.set(activity, view);

				} catch (IllegalAccessException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IllegalArgumentException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

		}

	}

	public static void injectEvents(Activity activity) {
	
		// 假如show方法使用了注解InjectEvent

		// 1.得到方法show中 ,注解上InjectEvent上的ListenEvent的内容
		Class<?> clzz = activity.getClass();
		Method[] methods = clzz.getDeclaredMethods();

		for (Method method : methods) {
			Annotation[] annotations = method.getAnnotations();
			for (Annotation annotation : annotations) {
				Class<?> annotationType = annotation.annotationType();
				ListenEvent listenEvent = annotationType.getAnnotation(ListenEvent.class);

				// 获取事件三要素
				String setListener = listenEvent.setListener();
				Class<?> listenerType = listenEvent.listenerType();
				String callbackMethod = listenEvent.callbackMethod();

				// 2.获取并绑定需要注入事件的控件对象btn id,即InjectEvent的value值
				try {

					Method valueMethod = annotationType.getDeclaredMethod("value");
					// 反射得到value的数组值
					int[] viewIds = (int[]) valueMethod.invoke(annotation);
					// 遍历注解的值,
					for (int viewId : viewIds) {
						View view = activity.findViewById(viewId);
						if (view == null) {
							continue;
						}

						// 3.拿到该view的setListener方法
						Method setListenerMethod = view.getClass().getDeclaredMethod(setListener, listenerType);
						Map<String, Method> methodMap = new HashMap<String, Method>();
						// 这里callbackMethod就是我们写在注解中的InjectEvent方法,而method就是有注解的方法show
						methodMap.put(callbackMethod, method);

						// 4.修改 回调 方法为自己定义的方法{@link ListenerInvocationHandler}
						InvocationHandler handler = new ListenerInvocationHandler(activity, methodMap);
						Object proxy = Proxy.newProxyInstance(listenerType.getClassLoader(),
								new Class<?>[] { listenerType }, handler);
						setListenerMethod.invoke(view, proxy);

					}
				} catch (NoSuchMethodException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IllegalArgumentException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (InvocationTargetException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}

		}

	}

}

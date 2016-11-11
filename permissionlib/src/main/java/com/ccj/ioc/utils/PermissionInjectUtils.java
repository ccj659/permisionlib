package com.ccj.ioc.utils;

import android.app.Activity;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;

import com.ccj.ioc.annotation.NeedPermission;
import com.ccj.ioc.annotation.RequestPermission;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Created by Administrator on 2016/11/10.
 */

public class PermissionInjectUtils {


    private static final String TAG = "PermissionLib-->";

    public static void inject(Activity activity) {

        // 注入事件
        injectNeedPermission(activity);

    }

    /**
     * 尝试从 点击方法时候,执行此方法,检查
     * //如何监听有没有调用 带有某个注解的方法
     * @param activity
     */
    private static void methodInject(Activity activity) {
    // TODO: 2016/11/11
            /*// 假如show方法使用了注解InjectEvent

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
                        String[] viewIds = (String[]) valueMethod.invoke(annotation);
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
                            InvocationHandler handler = new NeedListenerInvocationHandler(activity, methodMap);
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

            }*/


    }

        public static boolean injectNeedPermission(Activity activity) {

        boolean flag = false;
        String[] permissions=null;
        Class<?> clzz = activity.getClass();
        Method[] methods = clzz.getDeclaredMethods();
        Method requestMethod=null;
        RequestPermission requestPermission = null;
        StringBuffer stringBuffer=new StringBuffer();
        if(!(activity instanceof ActivityCompat.OnRequestPermissionsResultCallback)) {
            throw new RuntimeException("申请权限的Activity未实现OnRequestPermissionsResultCallback接口");
        }



        for (Method method : methods) {
            NeedPermission needPermission = method.getAnnotation(NeedPermission.class);

            RequestPermission  requestPermissions= method.getAnnotation(RequestPermission.class);

            if (requestPermissions!=null){
                requestPermission=requestPermissions;
                requestMethod=method;

                Log.i(TAG,"method-->"+requestPermission);
            }
            if (needPermission != null) {
                // 得到id
                Class<?> annotationType = needPermission.annotationType();
                Method valueMethod = null;
                try {
                    valueMethod = annotationType.getDeclaredMethod("value");
                     permissions = (String[]) valueMethod.invoke(needPermission);


                    //检查权限是否存在
                    for(String permission:permissions){
                        if(ContextCompat.checkSelfPermission(activity,permission) != PackageManager.PERMISSION_GRANTED){
                            flag = true;
                            Log.i(TAG,"权限"+permission+"不存在");
                            //stringBuffer.append(getPermissionMsg(permission));
                            break;
                        }
                    }

                } catch (NoSuchMethodException e) {
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }


        }


        //动态申请权限
        if(flag){
            //
            Log.i(TAG,"需要申请权限");
            Log.i(TAG,"requestPermission"+requestPermission);
                if (requestMethod != null) {
                    try {
                        //调用activity中 使用注解RequestPermission的方法.
                        requestMethod.setAccessible(true);
                        Log.i(TAG,"setAccessible");

                        Object permissionObj=permissions;
                        requestMethod.invoke(activity,permissionObj);
                        Log.i(TAG,"invoke完毕");
                    } catch (IllegalAccessException e) {
                        Log.e(TAG,e.getMessage().toString());
                    } catch (InvocationTargetException e) {
                        Log.e(TAG,e.getMessage().toString());
                    }

                }

        }
return flag;

    }



}

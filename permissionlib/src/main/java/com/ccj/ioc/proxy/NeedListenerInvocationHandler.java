package com.ccj.ioc.proxy;

import android.app.Activity;

import com.ccj.ioc.utils.PermissionInjectUtils;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.Map;

/**
 * Created by Administrator on 2016/11/10.
 */

public class NeedListenerInvocationHandler implements InvocationHandler {

    private Activity activity;
    private Map<String, Method> methodMap;

    public NeedListenerInvocationHandler(Activity activity,Map<String, Method> methodMap) {
        // TODO Auto-generated constructor stub
        this.activity=activity;
        this.methodMap=methodMap;


    }

    /**
     * proxy the proxy instance on which the method was invoked
     method the method invoked on the proxy instance
     args an array of objects containing the parameters passed to the method, or null if no arguments are expected. Primitive types are boxed.

     */
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        // TODO Auto-generated method stub
        // method就是我们的onClick
        String name=method.getName();
        //调用自己的方法show(view)
        Method myMethod=methodMap.get(name);



        if (PermissionInjectUtils.injectNeedPermission(activity)){
            if (myMethod!=null) {
                return 	myMethod.invoke(activity, args);
            }
        }else {
            return 	method.invoke(activity, args);

        }
        return 	method.invoke(activity, args);
    }

}

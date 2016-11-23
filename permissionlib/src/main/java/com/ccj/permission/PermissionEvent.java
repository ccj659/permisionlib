package com.ccj.permission;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;

/**
 * Created by ccj on 2016/9/2.
 */

@Aspect
public class PermissionEvent {



    private static final String TAG = PermissionEvent.class.getSimpleName();
    private CallPermissionMsg callPermissionMsg;

    public boolean getResetPermissionMsg() {
        return resetPermissionMsg;
    }

    public void setResetPermissionMsg(boolean resetPermissionMsg) {
        this.resetPermissionMsg = resetPermissionMsg;
    }

    private boolean resetPermissionMsg=false;

    @Pointcut("execution(@NeedPermission public * *..*.*(..)) && @annotation(name)")
    public void needPermissionAspect(NeedPermission name) {

    }

    @Around("needPermissionAspect(NeedPermission)")
    public void needPermissionAspectEvent(ProceedingJoinPoint joinPoint) throws Throwable {
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        NeedPermission needPermission = methodSignature.getMethod().getAnnotation(NeedPermission.class);
        String[] value = needPermission.value();
        for (int i = 0; i <value.length ; i++) {
            Log.i(TAG,"value="+value[i]);
        }

        ArrayList<String> strings = new ArrayList<>();
        ArrayList<String> stringsMsg = new ArrayList<>();
        Context context = (Context) joinPoint.getTarget();

        //判断是否有权限没有申请通过
        boolean flag = false;
        //检查权限是否存在
        for (String permission : value) {
            if (ContextCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED) {
                flag = true;
                stringsMsg.add(setCallPermissionMsg(context,permission));
                strings.add(permission);
                Log.i(TAG,"needed value="+permission);
                break;
            }
        }


       if (flag == false) {
            joinPoint.proceed();
        } else {
           String[] values = new String[strings.size()];
           strings.toArray(values);
           Log.i(TAG,"strings="+strings.toString()+",values="+values);
           callRequestPermission(context, stringsMsg.toString(), value);
        }
    }



    /**
     *
     * @param context 上下文
     * @param message 请求权限的信息,
     * @param permissions 所需的权限
     */
    private void callRequestPermission(Context context, String message, String[] permissions) {

        Class<?> clzz = context.getClass();
        Method[] methods = clzz.getDeclaredMethods();
        Method requestMethod = null;
        if (!(context instanceof ActivityCompat.OnRequestPermissionsResultCallback)) {
            throw new RuntimeException("申请权限的Activity未实现OnRequestPermissionsResultCallback接口");
        }


        for (Method method : methods) {
            RequestPermission requestPermissions = method.getAnnotation(RequestPermission.class);
            if (requestPermissions != null) {
                requestMethod = method;
            }
        }


        if (requestMethod != null) {

            //调用activity中 使用注解RequestPermission的方法.
            Object permissionMsg = message;
            Object permissionsList = permissions;
            requestMethod.setAccessible(true);

            try {
                Log.e(TAG,"invoke");
                requestMethod.invoke(context, permissionMsg, permissionsList);

            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }


        }
    }


    /**
     *  当前运行时权限转中文选择器
     *
     * 可以在activity中设置setResetPermissionMsg(true),
     * 然后实现接口{@link CallPermissionMsg },达到想要的文字样式
     * 可参考 getDefaultMsg 方法,进行实现
     *
     * @param permission
     *
     */
    private String setCallPermissionMsg( Context mContext,String permission) throws Exception {

        String permissions=null;
        if (resetPermissionMsg){
            if (mContext instanceof CallPermissionMsg){
                permissions= callPermissionMsg.callPermissionMsg(permission);
            }else {
                throw new Exception("请实现CallPermissionMsg接口中的方法");

            }
        }else {
            permissions = getDefaultMsg(permission);
        }
        return permissions;
    }


    /**
     *  默认的权限信息转换
     * @param permission
     * @return
     */
    @NonNull
    private String getDefaultMsg(String permission) {
        String permissions = null;
        switch (permission) {
            case Manifest.permission.RECORD_AUDIO:
                permissions = "录音权限";
                break;
            case Manifest.permission.GET_ACCOUNTS:
                permissions = "账户权限";
                break;
            case Manifest.permission.READ_PHONE_STATE:
                permissions = "电话状态权限";
                break;
            case Manifest.permission.CALL_PHONE:
                permissions = "打电话权限";
                break;
            case Manifest.permission.CAMERA:
                permissions = "相机权限";
                break;
            case Manifest.permission.ACCESS_FINE_LOCATION:
                permissions = "精准定位权限";
                break;
            case Manifest.permission.ACCESS_COARSE_LOCATION:
                permissions = "普通定位权限";
                break;
            case Manifest.permission.READ_EXTERNAL_STORAGE:
                permissions = "读取内存权限";
                break;
            case Manifest.permission.WRITE_EXTERNAL_STORAGE:
                permissions = "修改内存权限";
                break;
            default:
                permissions = "其他权限";
                break;
        }
        return permissions;
    }


  /*  @Pointcut("execution(@CheckSelfPermission public * *..*.*(..)) && @annotation(name)")
    public void checkSelfPermissionAspect(NeedPermission name) {

    }

    @Around("checkSelfPermissionAspect(CheckSelfPermission)")
    public void checkSelfPermissionAspectEvent(ProceedingJoinPoint joinPoint) throws Throwable {
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        CheckSelfPermission needPermission = methodSignature.getMethod().getAnnotation(CheckSelfPermission.class);
        String[] value = needPermission.value();
        for (int i = 0; i <value.length ; i++) {
            Log.i(TAG,"value="+value[i]);
        }

        ArrayList<String> strings = new ArrayList<>();
        ArrayList<String> stringsMsg = new ArrayList<>();
        Context context = (Context) joinPoint.getTarget();

        //判断是否有权限没有申请通过
        boolean flag = false;
        //检查权限是否存在
        for (String permission : value) {
            if (ContextCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED) {
                flag = true;
                stringsMsg.add(setCallPermissionMsg(context,permission));
                strings.add(permission);
                Log.i(TAG,"needed value="+permission);
                break;
            }
        }


        if (flag == false) {

            joinPoint.proceed();
        } else {
            //strings.add(value[0]);
            String[] values = new String[strings.size()];
            strings.toArray(values);
            callRequestPermission(context, stringsMsg.toString(), values);
        }
    }




    private void TypePermissionEvent(){





    }

*/




}

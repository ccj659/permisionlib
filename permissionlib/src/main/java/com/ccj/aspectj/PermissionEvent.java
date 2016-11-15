package com.ccj.aspectj;

import android.content.Context;
import android.widget.Toast;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;

/**
 * Created by Administrator on 2016/11/15.
 */

@Aspect
public class PermissionEvent {
    @Pointcut("execution(@TestAspectJ public * *..*.*(..)) && @annotation(name)")
    public void testAspect(NeedPermission name) {

    }

    @Before("testAspect()")
    public void testTestAspect(JoinPoint joinPoint) {
        Toast.makeText((Context) joinPoint.getTarget(),"OK",Toast.LENGTH_SHORT).show();
        //joinPoint.proceed();
    }


}

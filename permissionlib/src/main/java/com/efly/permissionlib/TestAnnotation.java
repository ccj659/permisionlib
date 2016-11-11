package com.efly.permissionlib;


import android.Manifest;

import com.ccj.ioc.annotation.NeedPermission;

/**
 * Created by Administrator on 2016/8/15.
 */


public class TestAnnotation {


    @NeedPermission(Manifest.permission.CAMERA)
    public void takePhoto(){


    }


}

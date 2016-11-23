package com.ccj.permisionlib;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.ccj.permission.CallPermissionMsg;
import com.ccj.permission.NeedPermission;
import com.ccj.permission.RequestPermission;


public class MainActivity extends AppCompatActivity implements CallPermissionMsg{

    private static final String TAG = MainActivity.class.getSimpleName();
    public static final int REQUEST_PERMISSION = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                takePhoto1();
            }
        });

    }



    //2.将需要申请的方法上,进行添加注解内容()
    @NeedPermission({Manifest.permission.CAMERA
            , Manifest.permission.WRITE_EXTERNAL_STORAGE
            , Manifest.permission.ACCESS_COARSE_LOCATION
            , Manifest.permission.ACCESS_FINE_LOCATION
            , Manifest.permission.READ_EXTERNAL_STORAGE})
    public void takePhoto1() {
        Intent intent = new Intent();
        intent.setAction(MediaStore.ACTION_IMAGE_CAPTURE);
        MainActivity.this.startActivityForResult(intent, 300);


    }




    /**
     * 3.权限开启提示回调,方法内进行提示,并申请权限,dialog提示
     * @param mMessage
     * @param permissionObj
     *
     */
     @RequestPermission
    private  void showNeedPermissionMsg(String mMessage,String[] permissionObj) {
         Log.e(TAG,"showNeedPermissionMsg"+permissionObj[0]);
        final String[] permissionObjs=permissionObj;
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("权限申请")
                .setMessage(mMessage)
                .setPositiveButton("开启权限", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        ActivityCompat.requestPermissions(MainActivity.this, permissionObjs, REQUEST_PERMISSION);
                    }
                })
                .setNegativeButton("暂不开启", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                })
                .show();
    }


    /**
     * 结果回调 进行重新判断,进行下一步
     * @param requestCode
     * @param permissions
     * @param grantResults
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode){
            case REQUEST_PERMISSION:
                if(grantResults.length >0 &&grantResults[0]== PackageManager.PERMISSION_GRANTED){
                    //用户同意授权
                    takePhoto1();
                }else{
                    Toast.makeText(this, "您已禁止该权限，需要重新开启。", Toast.LENGTH_SHORT).show();

                }
                break;
        }
    }

    //重写即可{@link CallPermissionMsg }
    @Override
    public String callPermissionMsg(String permission) {


        return null;
    }
}

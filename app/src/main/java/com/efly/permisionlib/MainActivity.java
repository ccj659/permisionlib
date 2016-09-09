package com.efly.permisionlib;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import kr.co.namee.permissiongen.PermissionGen;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        PermissionGen.with()
    }
}

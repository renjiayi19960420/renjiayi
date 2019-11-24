package com.renjiayi.testapp;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Button;

import com.huawei.hms.aaid.HmsInstanceId;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
      //  Button button = findViewById(R.id.ssssss);
//        button.setTextColor(Color.RED);
        getToken();
        setContentView(R.layout.activity_main);
    }

    public void getToken() {
        new Thread() {
            @Override
            public void run() {
                try {
                    String getToken =  HmsInstanceId.getInstance(MainActivity.this).getToken(
                            "101390085", "HCM");
                    if (!TextUtils.isEmpty(getToken)) {
                        Log.d("renjiayi", "run: " + getToken);
                    }
                } catch (Exception e) {
                    Log.e("renjiayi", "getToken failed.", e);
                }
            }
        }.start();
    }
}

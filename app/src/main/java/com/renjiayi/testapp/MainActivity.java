package com.renjiayi.testapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;


import com.huawei.hms.aaid.HmsInstanceId;

import java.io.File;
import java.io.FileOutputStream;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    ImageView aaa ;
    TextView right;
    private Context context;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = getApplicationContext();
        setContentView(R.layout.activity_main);
        View view = LayoutInflater.from(getApplicationContext()).inflate(R.layout.view_test, null);
        TextView textView = view.findViewById(R.id.wwwww);
        measureView(textView);
        PopupWindow popupWindow = new PopupWindow(view, LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        popupWindow.getContentView().measure(0,0);

        Log.d("renjiayi123", "onClick request: 有权限" + popupWindow.getContentView().getMeasuredHeight());
        Log.d("renjiayi123", "onClick request: 有权限" + popupWindow.getContentView().getMeasuredWidth());
        Log.d("renjiayi123", "onClick request: 有权限" + textView.getMeasuredWidth());
        Button request = findViewById(R.id.request_premission);
        Button select = findViewById(R.id.select_calendar);
        Button add = findViewById(R.id.add_calendar);
        Button Event= findViewById(R.id.add_Event);
        Button Delete = findViewById(R.id.delete_Event);
        Delete.setOnClickListener(this);
        request.setOnClickListener(this);
        select.setOnClickListener(this);
        add.setOnClickListener(this);
        Event.setOnClickListener(this);
        TextView left = findViewById(R.id.tv_left);
        TextView midd = findViewById(R.id.tv_middle);
        right = findViewById(R.id.tv_right);
        right.setOnClickListener(this);
        getSressWigth();
        addContentView(view, new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
    }

    private void getSressWigth() {
        WindowManager wm = this.getWindowManager();
        int width = wm.getDefaultDisplay().getWidth();
        int height = wm.getDefaultDisplay().getHeight();
        Log.d("renjiayi123", "onClick request: 有权限" + width);
        Log.d("renjiayi123", "onClick request: 有权限" + height);
    }


    private void measureView(View child) {
        ViewGroup.LayoutParams lp = child.getLayoutParams();
        if (lp == null) {
            lp = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.FILL_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        }
        //headerView的宽度信息
        int childMeasureWidth = ViewGroup.getChildMeasureSpec(0, 0, lp.width);
        int childMeasureHeight;
        if (lp.height > 0) {
            childMeasureHeight = View.MeasureSpec.makeMeasureSpec(lp.height, View.MeasureSpec.EXACTLY);
            //最后一个参数表示：适合、匹配
        } else {
            childMeasureHeight = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);//未指定
        }
//System.out.println("childViewWidth"+childMeasureWidth);
//System.out.println("childViewHeight"+childMeasureHeight);
        //将宽和高设置给child
        child.measure(childMeasureWidth, childMeasureHeight);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.request_premission:
                //检查权限
                if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_CALENDAR)
                        != PackageManager.PERMISSION_GRANTED && ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_CALENDAR)
                        != PackageManager.PERMISSION_GRANTED) {
                    //进入到这里代表没有权限.
                    Log.d("renjiayi123", "没有权限，需要获取");
                    ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_CALENDAR,
                            Manifest.permission.WRITE_CALENDAR}, 1);
                } else {
                    Log.d("renjiayi123", "onClick request: 有权限");
                }
                break;
            case R.id.select_calendar:
                int i = CalendarProviderUtil.isHaveCalender(context);
                Log.d("renjiayi123", "onClick2: 查询" + i);
                break;
            case R.id.add_calendar:
                long j = CalendarProviderUtil.addCalendar(context);
                Log.d("renjiayi123", "onClick: 添加日历表" + j);
                break;
            case R.id.add_Event:
                 CalendarProviderUtil.addEvent(context);
                Log.d("renjiayi123", "onClick: 添加日历事件");
                 break;
            case R.id.delete_Event:
                CalendarProviderUtil.deleteCalendarEvent(context);
                break;
            case R.id.tv_right:
                right.setText("以加提醒");
            default:
                Log.d("renjiayi123", "onClick: cuowu");
                break;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        Log.d("renjiayi", "onRequestPermissionsResult: requestCode " + requestCode);
        Log.d("renjiayi", "onRequestPermissionsResult: permissions " + permissions.toString());
        Log.d("renjiayi", "onRequestPermissionsResult: grantResults " + grantResults.toString());
        if (requestCode == 1) {
            Log.d("renjiayi", "onRequestPermissionsResult: 权限获取成功！");
        }
    }
}

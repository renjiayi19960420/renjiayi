package com.renjiayi.testapp;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.renjiayi.testapp.utils.CalendarProviderUtil;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    ImageView aaa;
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
        popupWindow.getContentView().measure(0, 0);

        Button request = findViewById(R.id.request_premission);
        Button select = findViewById(R.id.select_calendar);
        Button add = findViewById(R.id.add_calendar);
        Button Event = findViewById(R.id.add_Event);
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
                    ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_CALENDAR,
                            Manifest.permission.WRITE_CALENDAR}, 1);
                } else {
                }
                break;
            case R.id.select_calendar:
                int i = CalendarProviderUtil.isHaveCalender(context);
                break;
            case R.id.add_calendar:
                long j = CalendarProviderUtil.addCalendar(context);
                break;
            default:
                break;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 1) {
        }
    }
}

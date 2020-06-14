package com.renjiayi.testapp;

import android.Manifest;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Color;
import android.net.Uri;
import android.provider.CalendarContract;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

public class CalendarProviderUtil {

    // ContentProvider的uri
    private static String calendarUri = "content://com.android.calendar/calendars";
    private static String eventUri = "content://com.android.calendar/events";
    private static String reminderUri = "content://com.android.calendar/reminders";


    /**
     * 检查是否有日历表,有返回日历id，没有-1
     * */
    public static int isHaveCalender(Context context) {
        // 查询日历表的cursor
        Cursor cursor = context.getContentResolver().query(Uri.parse(calendarUri), null, null, null, null);
        Log.d("renjiayi123", "isHaveCalender: " + cursor.getCount());
        if (cursor == null || cursor.getCount() == 0){
            Log.d("renjiayi123", "isHaveCalender: " + cursor.getCount());
            return -1;
        }else {
            // 如果有日历表
            try {
                cursor.moveToFirst();
                // 通过cursor返回日历表的第一行的属性值 第一个日历的id
                cursor.getString(cursor.getColumnIndex(CalendarContract.Calendars.NAME));
                Log.d("renjiayi123", "isHaveCalender: " + cursor.getString(cursor.getColumnIndex(CalendarContract.Calendars.ACCOUNT_NAME)));
                return cursor.getInt(cursor.getColumnIndex(CalendarContract.Calendars._ID));
            }finally {
                cursor.close();
            }
        }
    }

    /**
     * 添加日历表
     * */
    public static long addCalendar(Context context){
        // 时区
        TimeZone timeZone = TimeZone.getDefault();
        // 配置Calendar
        ContentValues value = new ContentValues();
        value.put(CalendarContract.Calendars.NAME, "我的日历表");
        value.put(CalendarContract.Calendars.ACCOUNT_NAME, "myAccount");
        value.put(CalendarContract.Calendars.ACCOUNT_TYPE, "myType");
        value.put(CalendarContract.Calendars.CALENDAR_DISPLAY_NAME, "myDisplayName");
        value.put(CalendarContract.Calendars.VISIBLE, 1);
        value.put(CalendarContract.Calendars.CALENDAR_COLOR, Color.BLUE);
        value.put(CalendarContract.Calendars.CALENDAR_ACCESS_LEVEL, CalendarContract.Calendars.CAL_ACCESS_OWNER);
        value.put(CalendarContract.Calendars.SYNC_EVENTS, 1);
        value.put(CalendarContract.Calendars.CALENDAR_TIME_ZONE, timeZone.getID());
        Log.d("renjiayi123", "addCalendar: " + timeZone.getID());
        value.put(CalendarContract.Calendars.OWNER_ACCOUNT, "myAccount");
        value.put(CalendarContract.Calendars.CAN_ORGANIZER_RESPOND, 0);
        value.put(CalendarContract.CALLER_IS_SYNCADAPTER,true);

        // 插入calendar
        Uri insertCalendarUri = context.getContentResolver().insert(Uri.parse(calendarUri).buildUpon().appendQueryParameter(CalendarContract.CALLER_IS_SYNCADAPTER, "true").build(),value);
        Log.d("renjiayi123", "addCalendar: " + insertCalendarUri);
        if (insertCalendarUri == null){
            return -1;
        }else {
            // return Integer.parseInt(insertCalendarUri.toString());
            return ContentUris.parseId(insertCalendarUri);
        }

    }

    /**
     *  添加日历事件
     * */
    public static void addEvent(Context context){

        // 日历表id
        int calendarId = isHaveCalender(context);
        if (calendarId == -1){
            addCalendar(context);
            calendarId = isHaveCalender(context);
        }

        // startMillis
        Calendar beginTime = Calendar.getInstance();
        beginTime.set(2019,8,15);
        long startMillis = beginTime.getTimeInMillis();
        Log.d("renjiayi123", "addEvent: " + startMillis);

        // endMillis
        Calendar endTime = Calendar.getInstance();
        endTime.set(2019,8,15);
        long endMillis = endTime.getTimeInMillis();
        Log.d("renjiayi123", "addEvent: " + endMillis);
        // 准备event
        ContentValues valueEvent = new ContentValues();
        long aLong = Long.parseLong("1582407600000");
        long bLong = Long.parseLong("1582409600000");
        valueEvent.put(CalendarContract.Events.DTSTART,aLong);
        valueEvent.put(CalendarContract.Events.DTEND,bLong);
        valueEvent.put(CalendarContract.Events.TITLE,"同花顺水测试");
        valueEvent.put(CalendarContract.Events.DESCRIPTION,"今天晚fsdasdasdfsdf上通宵");
        valueEvent.put(CalendarContract.Events.CALENDAR_ID,calendarId);
        valueEvent.put(CalendarContract.Events.EVENT_TIMEZONE,"Asia/Shanghai");

        // 添加event
        Uri insertEventUri = context.getContentResolver().insert(Uri.parse(eventUri),valueEvent);
        if (insertEventUri == null){
            Toast.makeText(context,"添加event失败",Toast.LENGTH_SHORT).show();
        }

        // 添加提醒
        //long eventId = ContentUris.parseId(insertEventUri);

        long id = Long.parseLong(insertEventUri.getLastPathSegment());
        Log.d("renjiayi123", "addEvent: " + id);
        ContentValues valueReminder = new ContentValues();
        valueReminder.put(CalendarContract.Reminders.EVENT_ID,id);
        valueReminder.put(CalendarContract.Reminders.MINUTES,0);
        valueReminder.put(CalendarContract.Reminders.METHOD, CalendarContract.Reminders.METHOD_ALERT);
        Uri insertReminderUri = context.getContentResolver().insert(Uri.parse(reminderUri),valueReminder);
        if (insertReminderUri == null){
            Toast.makeText(context,"添加reminder失败",Toast.LENGTH_SHORT).show();
        }
    }
    /**
     * 删除日历事件
     */
    public static void deleteCalendarEvent(Context context) {
        if (context == null) {
            return;
        }
        Cursor eventCursor = context.getContentResolver().query(Uri.parse(eventUri), null, null, null, null);
        try {
            if (eventCursor == null) { //查询返回空值
                return;
            }
            if (eventCursor.getCount() > 0) {
                //遍历所有事件，找到title跟需要查询的title一样的项
                for (eventCursor.moveToFirst(); !eventCursor.isAfterLast(); eventCursor.moveToNext()) {
                    String eventTitle = eventCursor.getString(eventCursor.getColumnIndex("title"));
                    if (!TextUtils.isEmpty("同花顺水测试") && "同花顺水测试".equals(eventTitle)) {
                        int id = eventCursor.getInt(eventCursor.getColumnIndex(CalendarContract.Calendars._ID));//取得id

                        Uri deleteUri = ContentUris.withAppendedId(Uri.parse(eventUri), id);
                        int rows = context.getContentResolver().delete(deleteUri, null, null);
                        if (rows == -1) { //事件删除失败
                            Log.d("renjiayi123", "deleteCalendarEvent: 没有改事件");
                            return;
                        }
                        Log.d("renjiayi123", "deleteCalendarEvent: " + id);
                    }
                }
            }
        } finally {
            if (eventCursor != null) {
                eventCursor.close();
            }
        }
    }

}

package com.tphy.hospitaldoctor.utils;

import android.app.Activity;
import android.graphics.Color;
import android.widget.TextView;

import com.tphy.hospitaldoctor.views.datetimepicker.common.LineConfig;
import com.tphy.hospitaldoctor.views.datetimepicker.picker.DateTimePicker;

import java.util.Calendar;


/**
 * Created by Android Studio.
 * User: tphy_ydys
 * Date: 2019/12/18
 * Time: 21:04
 */

public class DateTimePickerNew {
    public static void showTimeText(final TextView textView, Activity activity){
        DateTimePicker picker = new DateTimePicker(activity, DateTimePicker.HOUR_24);
        picker.setActionButtonTop(false);

        initShowValue(picker,textView);
        picker.setCanLinkage(false);
        picker.setTitleText("请选择");
//        picker.setStepMinute(5);
        picker.setWeightEnable(true);
        picker.setWheelModeEnable(true);
        LineConfig config = new LineConfig();
        config.setColor(Color.BLUE);//线颜色
        config.setAlpha(120);//线透明度
        config.setVisible(true);//线不显示 默认显示
        picker.setLineConfig(config);
        picker.setLabel(null,null,null,null,null);
        picker.setOnDateTimePickListener(new DateTimePicker.OnYearMonthDayTimePickListener() {
            @Override
            public void onDateTimePicked(String year, String month, String day, String hour, String minute) {
                // showToast();
                String stemp = year + "-" + month + "-" + day + " " + hour + ":" + minute;
                textView.setText(stemp);
            }
        });
        picker.show();
    }

    private static void initShowValue(DateTimePicker picker, TextView textView) {
        if (picker==null) return;
        if (null ==textView) return;
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int min = calendar.get(Calendar.MINUTE);
        int second = calendar.get(Calendar.SECOND);
        picker.setDateRangeStart(year-5, 1, 1);
        picker.setDateRangeEnd(year+5, 11, 11);
        String time1 = textView.getText().toString();
        if (!time1.equals("")&&time1.contains("-")){

            String syear = time1.split("-")[0];
            String smonth = time1.split("-")[1];
            smonth = smonth.length()>1?smonth:"0"+smonth;
            String sday = "";
            if (!time1.contains(" ")){//涓嶅付鏃跺垎绉?
                sday = time1.split("-")[2].toString().trim();
                sday = sday.length() > 1 ? sday : "0" + sday;
                picker.setSelectedItem(Integer.valueOf(syear), Integer.valueOf(smonth), Integer.valueOf(sday), 00, 00);
            }else {
                sday = time1.split("-")[2].split(" ")[0];
                sday = sday.length() > 1 ? sday : "0" + sday;
                String sf = time1.split("-")[2].split(" ")[1];
                String shour = sf.split(":")[0];
                shour = shour.length() > 1 ? shour : "0" + shour;
                String sminute = sf.split(":")[1];
                sminute = sminute.length() > 1 ? sminute : "0" + sminute;

              //  showtime = syear+"-"+smonth+"-"+sday+" "+shour+":"+sminute;
                picker.setSelectedItem(Integer.valueOf(syear), Integer.valueOf(smonth), Integer.valueOf(sday), Integer.valueOf(shour), Integer.valueOf(sminute));

            }

        }else {
            picker.setSelectedItem(year, month+1, day, hour, min);
        }
        picker.setTimeRangeStart(9, 0);
        picker.setTimeRangeEnd(20, 30);
    }
}

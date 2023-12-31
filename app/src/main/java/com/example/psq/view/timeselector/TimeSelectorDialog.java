package com.example.psq.view.timeselector;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import com.example.psq.R;
import com.example.psq.utils.DpUtil;
import com.example.psq.view.timeselector.adapter.NumericWheelAdapter;
import com.example.psq.view.timeselector.api.DateListener;
import com.example.psq.view.timeselector.api.OnWheelChangedListener;
import com.example.psq.view.timeselector.view.WheelView;

import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TimeSelectorDialog extends Dialog {
    private Context context;

    //标题
    private TextView textview_title;
    //显示的时间
    private TextView time_select_date;
    //3个按钮
    private Button btn1, btn2, btn3;
    //时间显示样式
    private int isShowtype;
    //年份初始日期
    private int START_YEAR = 1900;
    //年份结束日期
    private int END_YEAR = Integer.parseInt(PowerDateUtils.
            timeMillis2String1(System.currentTimeMillis() + TimeConfig.tenYears));

    private DateListener dateListener;
    static int year;
    static int month;
    static int day;
    static int newDay;
    static int hour;
    static int minute;
    int textSize;
    private static String selectTime;

    public TimeSelectorDialog(Context context) {
        super(context, R.style.dialog_normal);
        this.context = context;
        textSize = PowerDateUtils.dip2px(context, 14);
        setContentView(R.layout.dialog_select_time);
        textview_title = (TextView) findViewById(R.id.textview_time_title);
        btn2 = (Button) findViewById(R.id.btn_time_select_empty);
        btn3 = (Button) findViewById(R.id.btn_time_select_cancel);
        btn1 = (Button) findViewById(R.id.btn_time_select_submit);
    }

    public TimeSelectorDialog(Context context, int theme) {
        super(context, theme);
    }

    public void setDialogSize(int width, int height) {
        WindowManager.LayoutParams lp = getWindow().getAttributes();
        if (width > 0) {
            lp.width = DpUtil.dp2px(width);
        }
        if (height > 0) {
            lp.height = DpUtil.dp2px(height);
        }
        getWindow().setAttributes(lp);
    }

    /**
     * 初始化时间   yyyy-MM-dd kk:mm
     *
     * @param dateString
     */
    public void setCurrentDate(String dateString) {
        year = 0;
        month = 0;
        day = 0;
        hour = 0;
        minute = 0;
        newDay = 0;
        //年月日时分
        String pattern0 = "(\\d{4})[-|/](\\d{1,2})[-|/](\\d{1,2})\\s+(\\d{1,2}):(\\d{1,2})";
        //年月日时
        String pattern1 = "(\\d{4})[-|/](\\d{1,2})[-|/](\\d{1,2})\\s+(\\d{1,2})";
        //年月日
        String pattern2 = "(\\d{4})[-|/](\\d{1,2})[-|/](\\d{1,2})";
        //时分
        String pattern3 = "(\\d{1,2}):(\\d{1,2})";
        //创建Pattern对象
        Pattern nyrsf = Pattern.compile(pattern0);
        Pattern nyrs = Pattern.compile(pattern1);
        Pattern nyr = Pattern.compile(pattern2);
        Pattern sf = Pattern.compile(pattern3);
        //现在创建matcher对象
        Matcher m0 = nyrsf.matcher(dateString);
        Matcher m1 = nyrs.matcher(dateString);
        Matcher m2 = nyr.matcher(dateString);
        Matcher m3 = sf.matcher(dateString);
        if (m0.find()) {
            year = Integer.parseInt(m0.group(1));
            month = Integer.parseInt(m0.group(2)) - 1;
            day = Integer.parseInt(m0.group(3));
            hour = Integer.parseInt(m0.group(4));
            minute = Integer.parseInt(m0.group(5));
        }
        if (m1.find()) {
            year = Integer.parseInt(m1.group(1));
            month = Integer.parseInt(m1.group(2)) - 1;
            day = Integer.parseInt(m1.group(3));
            hour = Integer.parseInt(m1.group(4));
        }
        if (m2.find()) {
            year = Integer.parseInt(m2.group(1));
            month = Integer.parseInt(m2.group(2)) - 1;
            day = Integer.parseInt(m2.group(3));
        }
        if (m3.find()) {
            hour = Integer.parseInt(m3.group(1));
            minute = Integer.parseInt(m3.group(2));
        }
    }

    /**
     * 时间返回格式
     *
     * @param isShowtype
     * @return
     */
    public String showTime(int isShowtype) {
        switch (isShowtype) {
            case TimeConfig.YEAR_MONTH_DAY_HOUR_MINUTE:
                selectTime = PowerDateUtils.getDateStr(year, month, day, hour, minute);
                break;
            case TimeConfig.YEAR_MONTH_DAY_HOUR:
                selectTime = PowerDateUtils.getDateStr(year, month, day, hour);
                break;
            case TimeConfig.YEAR_MONTH_DAY:
                selectTime = PowerDateUtils.getDateStr(year, month, day);
                break;
            case TimeConfig.HOUR_MINUTE:
                selectTime = PowerDateUtils.getDateStr(hour, minute);
                break;
            case TimeConfig.YEAR_MONTH:
                selectTime = PowerDateUtils.getDateStr(year, month);
                break;
        }
        return selectTime;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //添加大小月月份并将其转换为list,方便之后的判断
        String[] months_big = {"1", "3", "5", "7", "8", "10", "12"};
        String[] months_little = {"4", "6", "9", "11"};
        final List<String> list_big = Arrays.asList(months_big);
        final List<String> list_little = Arrays.asList(months_little);
        time_select_date = (TextView) findViewById(R.id.time_select_date);
        selectTime = showTime(isShowtype);
        time_select_date.setText(selectTime);

        //年
        final WheelView wv_year = (WheelView) findViewById(R.id.year);
        //设置"年"的显示数据
        wv_year.setAdapter(new NumericWheelAdapter(START_YEAR, END_YEAR));
        //可循环滚动
        wv_year.setCyclic(true);
        //添加文字
        wv_year.setLabel("年");
        //初始化时显示的数据
        wv_year.setCurrentItem(year - START_YEAR);

        //月
        final WheelView wv_month = (WheelView) findViewById(R.id.month);
        wv_month.setAdapter(new NumericWheelAdapter(1, 12));
        wv_month.setCyclic(true);
        wv_month.setLabel("月");
        wv_month.setCurrentItem(month);
        //时
        final WheelView wv_hour = (WheelView) findViewById(R.id.hour);
        wv_hour.setAdapter(new NumericWheelAdapter(0, 23));
        wv_hour.setCyclic(true);
        wv_hour.setLabel("时");
        wv_hour.setCurrentItem(hour);
        //分
        final WheelView wv_minute = (WheelView) findViewById(R.id.minute);
        wv_minute.setVisibility(View.VISIBLE);
        wv_minute.setAdapter(new NumericWheelAdapter(0, 60));//new NumericWheelAdapter(0, 50, "minute", 10)
        wv_minute.setCyclic(true);
        wv_minute.setLabel("分");
        wv_minute.setCurrentItem(minute);
        //添加“分”监听
        OnWheelChangedListener wheelListener_minute = (wheel, oldValue, newValue) -> {
            minute = newValue;
            selectTime = showTime(isShowtype);
            time_select_date.setText(selectTime);
        };
        wv_minute.addChangingListener(wheelListener_minute);
        //日
        final WheelView wv_day = (WheelView) findViewById(R.id.day);
        wv_day.setCyclic(true);
        //判断大小月及是否闰年,用来确定"日"的数据
        if (list_big.contains(String.valueOf(month + 1))) {
            wv_day.setAdapter(new NumericWheelAdapter(1, 31));
        } else if (list_little.contains(String.valueOf(month + 1))) {
            wv_day.setAdapter(new NumericWheelAdapter(1, 30));
        } else {
            //闰年
            if ((year % 4 == 0 && year % 100 != 0) || year % 400 == 0) {
                wv_day.setAdapter(new NumericWheelAdapter(1, 29));
            } else {
                wv_day.setAdapter(new NumericWheelAdapter(1, 28));
            }
        }
        wv_day.setLabel("日");
        wv_day.setCurrentItem(day - 1);

        //添加"年"监听
        OnWheelChangedListener wheelListener_year = (wheel, oldValue, newValue) -> {
            year = newValue + START_YEAR;
            //判断大小月及是否闰年,用来确定"日"的数据
            if (list_big.contains(String.valueOf(wv_month.getCurrentItem() + 1))) {
                wv_day.setAdapter(new NumericWheelAdapter(1, 31));
            } else if (list_little.contains(String.valueOf(wv_month.getCurrentItem() + 1))) {
                wv_day.setAdapter(new NumericWheelAdapter(1, 30));
            } else {
                if ((year % 4 == 0 && year % 100 != 0) || year % 400 == 0) {
                    wv_day.setAdapter(new NumericWheelAdapter(1, 29));
                } else {
                    wv_day.setAdapter(new NumericWheelAdapter(1, 28));
                }
            }
            selectTime = showTime(isShowtype);
            time_select_date.setText(selectTime);
        };

        //添加"月"监听
        OnWheelChangedListener wheelListener_month = (wheel, oldValue, newValue) -> {
            month = newValue + 1;
            //判断大小月及是否闰年,用来确定"日"的数据
            if (list_big.contains(String.valueOf(month))) {
                wv_day.setAdapter(new NumericWheelAdapter(1, 31));
                if (newDay > 0) {
                    day = newDay;
                    wv_day.setCurrentItem(30);
                }
                newDay = 0;
            } else if (list_little.contains(String.valueOf(month))) {
                if (day >= 31) {
                    newDay = day;
                    day = 30;
                    wv_day.setCurrentItem(29);
                }
                wv_day.setAdapter(new NumericWheelAdapter(1, 30));
            } else {
                if (((wv_year.getCurrentItem() + START_YEAR) % 4 == 0 && (wv_year
                        .getCurrentItem() + START_YEAR) % 100 != 0)
                        || (wv_year.getCurrentItem() + START_YEAR) % 400 == 0) {
                    if (day >= 30) {
                        newDay = day;
                        day = 29;
                        wv_day.setCurrentItem(28);
                    }
                    wv_day.setAdapter(new NumericWheelAdapter(1, 29));
                } else {
                    if (day >= 29) {
                        newDay = day;
                        day = 28;
                        wv_day.setCurrentItem(27);
                    }
                    wv_day.setAdapter(new NumericWheelAdapter(1, 28));
                }
            }
            month = month - 1;
            selectTime = showTime(isShowtype);
            time_select_date.setText(selectTime);
        };

        //添加“日”监听
        OnWheelChangedListener wheelListener_day = (wheel, oldValue, newValue) -> {
            day = newValue + 1;
            selectTime = showTime(isShowtype);
            time_select_date.setText(selectTime);
        };

        //添加“时”监听
        OnWheelChangedListener wheelListener_hour = (wheel, oldValue, newValue) -> {
            hour = newValue;
            selectTime = showTime(isShowtype);
            time_select_date.setText(selectTime);
        };

        wv_year.addChangingListener(wheelListener_year);
        wv_month.addChangingListener(wheelListener_month);
        wv_day.addChangingListener(wheelListener_day);
        wv_hour.addChangingListener(wheelListener_hour);

        // 根据屏幕密度来指定选择器字体的大小
        wv_day.TEXT_SIZE = textSize;
        wv_month.TEXT_SIZE = textSize;
        wv_year.TEXT_SIZE = textSize;
        wv_hour.TEXT_SIZE = textSize;
        wv_minute.TEXT_SIZE = textSize;
        btn2.setOnClickListener(view -> {
            if (isShowing()) {
                dismiss();
            }
            dateListener.onReturnDate("");
        });
        btn3.setOnClickListener(v -> dismiss());
        btn1.setOnClickListener(v -> {
            if (isShowing()) {
                selectTime = showTime(isShowtype);
                dateListener.onReturnDate(selectTime, year,
                        month + 1, day, hour, minute, isShowtype);
                dismiss();
            }
        });

        switch (isShowtype) {
            case TimeConfig.YEAR_MONTH_DAY_HOUR_MINUTE:
                selectTime = PowerDateUtils.getDateStr(year, month, day, hour, minute);
                break;
            case TimeConfig.YEAR_MONTH_DAY_HOUR:
                selectTime = PowerDateUtils.getDateStr(year, month, day, hour);
                wv_minute.setVisibility(View.GONE);
                break;
            case TimeConfig.YEAR_MONTH_DAY:
                selectTime = PowerDateUtils.getDateStr(year, month, day);
                wv_minute.setVisibility(View.GONE);
                wv_hour.setVisibility(View.GONE);
                break;
            case TimeConfig.HOUR_MINUTE:
                selectTime = PowerDateUtils.getDateStr(hour, minute);
                wv_year.setVisibility(View.GONE);
                wv_month.setVisibility(View.GONE);
                wv_day.setVisibility(View.GONE);
                break;
            case TimeConfig.YEAR_MONTH:
                selectTime = PowerDateUtils.getDateStr(hour, minute);
                wv_day.setVisibility(View.GONE);
                wv_minute.setVisibility(View.GONE);
                wv_hour.setVisibility(View.GONE);
                break;
        }
    }

    public void setDateListener(DateListener dateListener) {
        this.dateListener = dateListener;
    }

    public void setTimeTitle(String title) {
        textview_title.setText(title);
    }

    public void setIsShowtype(int isShowtype) {
        this.isShowtype = isShowtype;
    }

    /**
     * 取消按钮是否显示
     *
     * @param isShow
     */
    public void setCancleIsShow(boolean isShow) {
        if (isShow) {
            btn3.setVisibility(View.VISIBLE);
        } else {
            btn3.setVisibility(View.GONE);
        }
    }

    /**
     * 清除按钮是否显示
     *
     * @param isShow
     */
    public void setEmptyIsShow(boolean isShow) {
        if (isShow) {
            btn2.setVisibility(View.VISIBLE);
        } else {
            btn2.setVisibility(View.GONE);
        }
    }

    /**
     * 确定按钮是否显示
     *
     * @param isShow
     */
    public void setSubmitIsShow(boolean isShow) {
        if (isShow) {
            btn1.setVisibility(View.VISIBLE);
        } else {
            btn1.setVisibility(View.GONE);
        }
    }

    /**
     * 设置初始年份
     *
     * @param
     */
    public void setStartYear(int year) {
        START_YEAR = year;
    }

    /**
     * 设置日期字体大小
     *
     * @param textSize
     */
    public void setTextSize(int textSize) {
        this.textSize = PowerDateUtils.dip2px(context, textSize);
    }

    /**
     * 确定按钮字体颜色
     *
     * @param textColor
     */
    public void setSubmitTextColor(int textColor) {
        btn1.setTextColor(textColor);
    }

    /**
     * 清除按钮字体颜色
     *
     * @param textColor
     */
    public void setEmptyTextColor(int textColor) {
        btn2.setTextColor(textColor);
    }

    /**
     * 取消按钮字体颜色
     *
     * @param textColor
     */
    public void setCancleTextColor(int textColor) {
        btn3.setTextColor(textColor);
    }
}

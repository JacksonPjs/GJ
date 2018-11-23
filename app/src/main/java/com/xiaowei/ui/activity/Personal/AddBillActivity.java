package com.xiaowei.ui.activity.Personal;

import android.app.DatePickerDialog;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.text.format.DateUtils;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TextView;

import com.bigkoo.pickerview.TimePickerView;
import com.xiaowei.R;
import com.xiaowei.ui.activity.BaseActivity;
import com.xiaowei.utils.DataUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @author jackson
 * @date 2018/11/19
 * 添加账单
 */
public class AddBillActivity extends BaseActivity {
    @Bind(R.id.tv_date)
    TextView tv_date;
    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.title)
    TextView title;
    @Bind(R.id.tv_late)
    TextView tv_late;
    int mYear, mMonth, mDay;
     Date curDate;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addbill);
        ButterKnife.bind(this);
        initView();
    }


    private void initView() {
        toolbar.setBackgroundResource(R.color.colorPrimary);
        title.setText(getString(R.string.bill_title));

        initData();
    }

    private void initData() {
        Calendar ca = Calendar.getInstance();
        mYear = ca.get(Calendar.YEAR);
        mMonth = ca.get(Calendar.MONTH);
        mDay = ca.get(Calendar.DAY_OF_MONTH);
        curDate = new Date(System.currentTimeMillis());//获取当前时间
        tv_date.setText(DataUtils.getTime(curDate));
    }

    @OnClick({R.id.date_rl})
    public void OnClick(View view) {
        switch (view.getId()) {
            case R.id.date_rl:
//                new DatePickerDialog(AddBillActivity.this, onDateSetListener, mYear, mMonth, mDay).show();
                initTimePicker1();
                break;

        }


    }

    /**
     * 日期选择器对话框监听
     */
    private DatePickerDialog.OnDateSetListener onDateSetListener = new DatePickerDialog.OnDateSetListener() {

        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            mYear = year;
            mMonth = monthOfYear;
            mDay = dayOfMonth;
            String days;
            if (mMonth + 1 < 10) {
                if (mDay < 10) {
                    days = new StringBuffer().append(mYear).append("年").append("0").
                            append(mMonth + 1).append("月").append("0").append(mDay).append("日").toString();
                } else {
                    days = new StringBuffer().append(mYear).append("年").append("0").
                            append(mMonth + 1).append("月").append(mDay).append("日").toString();
                }

            } else {
                if (mDay < 10) {
                    days = new StringBuffer().append(mYear).append("年").
                            append(mMonth + 1).append("月").append("0").append(mDay).append("日").toString();
                } else {
                    days = new StringBuffer().append(mYear).append("年").
                            append(mMonth + 1).append("月").append(mDay).append("日").toString();
                }

            }
            tv_date.setText(days);
        }
    };


    //    };
    private void initTimePicker1() {//选择出生年月日
        //控制时间范围(如果不设置范围，则使用默认时间1900-2100年，此段代码可注释)
        //因为系统Calendar的月份是从0-11的,所以如果是调用Calendar的set方法来设置时间,月份的范围也要是从0-11



        Calendar selectedDate = Calendar.getInstance();//系统当前时间
        Calendar startDate = Calendar.getInstance();
        startDate.set(1900, 0, 1);
        Calendar endDate = Calendar.getInstance();
//        endDate.set(year_int, mouth_int - 1, day_int);
        endDate.set(2100, 11, 30);
        //时间选择器
        TimePickerView pvTime = new TimePickerView.Builder(this, new TimePickerView.OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date, View v) {//选中事件回调
                // 这里回调过来的v,就是show()方法里面所添加的 View 参数，如果show的时候没有添加参数，v则为null
                /*btn_Time.setText(getTime(date));*/
                tv_date.setText(DataUtils.getTime(date));
                String endTime=DataUtils.getTime(date);
                String startTime=DataUtils.getTime(curDate);
                if (DataUtils.compare_date(startTime,endTime)==1){
                    tv_late.setText(DataUtils.dateDiff(endTime,startTime,"yyyy-MM-dd")+"天前");
                }else {
                    tv_late.setText(DataUtils.dateDiff(startTime,endTime,"yyyy-MM-dd")+"天后");


                }
            }
        })
                .setType(new boolean[]{true, true, true, false, false, false}) //年月日时分秒 的显示与否，不设置则默认全部显示
                .setLabel("年", "月", "日", "", "", "")//默认设置为年月日时分秒
                .isCenterLabel(false)
                .setDividerColor(Color.parseColor("#b3b3b3"))
                .setTextColorCenter(Color.BLACK)//设置选中项的颜色
                .setTextColorOut(Color.parseColor("#b3b3b3"))//设置没有被选中项的颜色
                .setContentSize(21)
                .setDate(selectedDate)
                .setLineSpacingMultiplier(1.2f)
                .setTextXOffset(-10, 0, 10, 0, 0, 0)//设置X轴倾斜角度[ -90 , 90°]
                .setRangDate(startDate, endDate)
//                .setBackgroundId(0x00FFFFFF) //设置外部遮罩颜色
                .setDecorView(null)
                .build();

        pvTime.show();
    }




}

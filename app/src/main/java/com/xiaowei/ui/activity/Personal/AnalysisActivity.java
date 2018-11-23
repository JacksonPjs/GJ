package com.xiaowei.ui.activity.Personal;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.blibrary.citypicker.view.SideIndexBar;
import com.example.blibrary.loadinglayout.LoadingLayout;
import com.example.blibrary.loadinglayout.Utils;
import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.LegendEntry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.PercentFormatter;

import com.xiaowei.R;
import com.xiaowei.ui.Adapter.AnalysisAdapter;
import com.xiaowei.ui.Adapter.BillAdapter;
import com.xiaowei.ui.activity.BaseActivity;
import com.xiaowei.utils.DataUtils;
import com.xiaowei.widget.DividerItemDecoration;
import com.xiaowei.widget.ObservableScrollView;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @author jackson
 * @date 2018/11/19
 * 借款分析
 */
public class AnalysisActivity extends BaseActivity implements View.OnTouchListener {
    Activity activity;
    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.title)
    TextView title;
    @Bind(R.id.pie_chart)
    PieChart pieChart;

    @Bind(R.id.recycle)
    RecyclerView recyclerView;
    @Bind(R.id.nestedscroll)
    NestedScrollView nestedScrollView;
    @Bind(R.id.pie_ll)
    LinearLayout linearLayout;
    @Bind(R.id.tv_date)
    TextView tvDate;
    @Bind(R.id.date_year)
    TextView dateYear;
    String curDate;
    String curWeek;
    String curYear;
    String curWeekDay;
    String dateFormat = "yyyy-MM-dd";
    String dateFormatMD = "MM-dd";
    String dateFormatY = "yyyy";
    AnalysisAdapter adapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_analysis);
        ButterKnife.bind(this);
        activity = this;
        initView();
    }

    private void initView() {
        toolbar.setBackgroundResource(R.color.colorPrimary);
        title.setText(getString(R.string.bill_title));
        LinearLayoutManager manager = new LinearLayoutManager(this);
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(manager);
        recyclerView.addItemDecoration(new DividerItemDecoration(activity, DividerItemDecoration.VERTICAL_LIST));
        recyclerView.setNestedScrollingEnabled(false);
        initChart();
        initData();
    }

    private void initData() {
        curDate = DataUtils.getCurDate(dateFormat);

        List<String> list = new ArrayList();
        for (int i = 0; i < 10; i++) {
            list.add("" + getString(R.string.exit_app));
        }
        adapter = new AnalysisAdapter(this, list);
        recyclerView.setAdapter(adapter);

        linearLayout.setOnTouchListener(this);
        pieChart.setOnTouchListener(this);
        try {
            curWeek = DataUtils.getFirstAndLastOfWeek(curDate, dateFormat, dateFormatMD);
            curWeekDay = DataUtils.getWeekLastDay(curDate, dateFormat);
            curYear = DataUtils.getTime(curWeekDay,dateFormatY);
            tvDate.setText(curWeek);
            dateYear.setText(curYear+"");
        } catch (ParseException e) {
            e.printStackTrace();
        }

//
    }


    @OnClick({R.id.data_start, R.id.data_end})
    public void OnClick(View view) {
        switch (view.getId()) {
            case R.id.data_start:
                try {
                    curDate = DataUtils.getTheLast(curDate, dateFormat);
                    curWeek = DataUtils.getFirstAndLastOfWeek(curDate, dateFormat, dateFormatMD);
                    curWeekDay = DataUtils.getWeekLastDay(curDate, dateFormat);
                    curYear = DataUtils.getTime(curWeekDay,dateFormatY);
                    dateYear.setText(curYear+"");
                    tvDate.setText(curWeek);
                } catch (ParseException e) {
                    e.printStackTrace();
                }

                break;
            case R.id.data_end:
                try {
                    curDate = DataUtils.getNextWeek(curDate, dateFormat);
                    curWeek = DataUtils.getFirstAndLastOfWeek(curDate, dateFormat, dateFormatMD);
                    curWeekDay = DataUtils.getWeekLastDay(curDate, dateFormat);
                    curYear = DataUtils.getTime(curWeekDay,dateFormatY);
                    dateYear.setText(curYear+"");
                    tvDate.setText(curWeek);

                } catch (ParseException e) {
                    e.printStackTrace();
                }

                break;
        }
    }


    private void initChart() {
        //饼状图

        pieChart.setUsePercentValues(true);//设置为TRUE的话，图标中的数据自动变为percent
        pieChart.getDescription().setEnabled(false);
        pieChart.setExtraOffsets(5, 10, 5, 5);//设置额外的偏移量(在图表视图周围)

        pieChart.setDragDecelerationFrictionCoef(0.95f);//设置滑动减速摩擦系数，在0~1之间
        //设置中间文件
        pieChart.setCenterText("测试");
        pieChart.setDrawCenterText(true);//饼状图中间可以添加文字
//        pieChart.setDrawSliceText(false);//设置隐藏饼图上文字，只显示百分比
        pieChart.setDrawSliceText(true);
        pieChart.setDrawHoleEnabled(true);//设置为TRUE时，饼中心透明
        pieChart.setHoleColor(Color.WHITE);//设置饼中心颜色

        pieChart.setTransparentCircleColor(Color.WHITE);//透明的圆
        pieChart.setTransparentCircleAlpha(110);//透明度

        pieChart.setHoleRadius(50f);//中间圆的半径占总半径的百分数
//        pieChart.setHoleRadius(0);//实心圆
        //pieChart.setTransparentCircleRadius(61f);//// 半透明圈

        pieChart.setRotationEnabled(false); // 不可以手动旋转

        //模拟数据
        ArrayList<PieEntry> entries = new ArrayList<PieEntry>();
        entries.add(new PieEntry(4000, "4000"));
        entries.add(new PieEntry(5000, "5000"));

        //设置数据
        setData(entries);


        // 输入标签样式
        pieChart.setEntryLabelColor(Color.BLACK);
        pieChart.setEntryLabelTextSize(12f);
        /**
         * 设置比例图
         */
        Legend mLegend = pieChart.getLegend();
        mLegend.setPosition(Legend.LegendPosition.BELOW_CHART_CENTER);  //在底部中间显示比例图
        mLegend.setFormSize(14f);//比例块字体大小 
        mLegend.setXEntrySpace(30f);//设置距离饼图的距离，防止与饼图重合
        mLegend.setYEntrySpace(4f);
        //设置比例块换行...
        mLegend.setWordWrapEnabled(true);
        mLegend.setDirection(Legend.LegendDirection.LEFT_TO_RIGHT);//设置字跟图表的左右顺序

        //mLegend.setTextColor(getResources().getColor(R.color.alpha_80));
        mLegend.setForm(Legend.LegendForm.SQUARE);//设置比例块形状，默认为方块
//        mLegend.setEnabled(false);//设置禁用比例块
//        colors=mLegend.getColors();
//        labels=mLegend.getLabels();
//        customizeLegend();
    }

    //设置数据
    private void setData(ArrayList<PieEntry> entries) {
        PieDataSet dataSet = new PieDataSet(entries, "");
        dataSet.setSliceSpace(2f);//饼图区块之间的距离
        dataSet.setSelectionShift(5f);//


        ArrayList<Integer> colors = new ArrayList<Integer>();
        colors.add(getResources().getColor(R.color.x_yellow));
        colors.add(getResources().getColor(R.color.blue_item_btn));
        dataSet.setColors(colors);


        PieData data = new PieData(dataSet);
        data.setValueFormatter(new PercentFormatter());
        data.setValueTextSize(11f);
        data.setValueTextColor(Color.WHITE);
//        data.setValueTextColors(colors);


        data.setDrawValues(true);//饼状图上显示值
//        dataSet.setValueLinePart1Length(0.3f);//设置连接线的长度
        dataSet.setValueLinePart2Length(0.3f);
//x,y值在圆外显示(在圆外才会有连接线)
        dataSet.setXValuePosition(PieDataSet.ValuePosition.OUTSIDE_SLICE);
//        dataSet.setYValuePosition(PieDataSet.ValuePosition.OUTSIDE_SLICE);
//数据连接线距图形片内部边界的距离，为百分数(0~100f)
        dataSet.setValueLinePart1OffsetPercentage(50f);


        pieChart.setData(data);
        pieChart.highlightValues(null);//在给定的数据集中突出显示给定索引的值
        pieChart.invalidate();

    }


    private int[] colors;//颜色集合
    private String[] labels;//标签文本
    private float[] datas = {16912f, 2488f, 600f};//数据，可以是任何类型的数据，如String,int
    /**
     * 定制图例，通过代码生成布局
     */
//    private void customizeLegend(){
//        for(int i=0;i<datas.length;i++){
//            LinearLayout.LayoutParams lp=new LinearLayout.
//                    LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.MATCH_PARENT);
//            lp.weight=1;//设置比重为1
//            LinearLayout layout=new LinearLayout(this);//单个图例的布局
//            layout.setOrientation(LinearLayout.HORIZONTAL);//水平排列
//            layout.setGravity(Gravity.CENTER_VERTICAL);//垂直居中
//            layout.setLayoutParams(lp);
//
//            //添加color
//            LinearLayout.LayoutParams colorLP=new LinearLayout.
//                    LayoutParams(20,20);
//            colorLP.setMargins(0, 0, 20, 0);
//            LinearLayout colorLayout=new LinearLayout(this);
//            colorLayout.setLayoutParams(colorLP);
//            colorLayout.setBackgroundColor(colors[i]);
//            layout.addView(colorLayout);
//
//            //添加label
//            TextView labelTV=new TextView(this);
//            labelTV.setText(labels[i]+" ");
//            layout.addView(labelTV);
//
//            //添加data
//            TextView dataTV=new TextView(this);
//            dataTV.setText(datas[i]+"");
//            layout.addView(dataTV);
//
//            linearLayout.addView(layout);//legendLayout为外层布局即整个图例布局，是在xml文件中定义
//
//        }
//    }


    //手指按下的点为(x1, y1)手指离开屏幕的点为(x2, y2)
    float x1 = 0;
    float x2 = 0;
    float y1 = 0;
    float y2 = 0;

//    @Override
//    public boolean onTouchEvent(MotionEvent event) {
//
//       // 继承了Activity的onTouchEvent方法，直接监听点击事件
//        if (event.getAction() == MotionEvent.ACTION_DOWN) {
//            //当手指按下的时候
//            x1 = event.getX();
//            y1 = event.getY();
//        }
//
//        if (event.getAction() == MotionEvent.ACTION_UP) {
//            //当手指离开的时候
//            x2 = event.getX();
//            y2 = event.getY();
//            if (x1 -x2 >50) {
////                Toast.makeText(activity, "向左滑", Toast.LENGTH_SHORT).show();
//                if (y1 - y2 >100) {
////                Toast.makeText(activity, "向上滑", Toast.LENGTH_SHORT).show();
//                    return false;
//                }  else if (y2 - y1 > 100) {
////                Toast.makeText(activity, "向下滑", Toast.LENGTH_SHORT).show();
//                    return false;
//                }
//                Log.e("滑动","向左滑");
//            }
//             if (x2 -x1>50 ) {
//                if (y1 - y2 >150) {
////                Toast.makeText(activity, "向上滑", Toast.LENGTH_SHORT).show();
//                    return false;
//                }  else if (y2 - y1 >150) {
////                Toast.makeText(activity, "向下滑", Toast.LENGTH_SHORT).show();
//                    return false;
//                }
////                Toast.makeText(activity, "向右滑", Toast.LENGTH_SHORT).show();
//                Log.e("滑动","向右滑");
//            }
//        }
////        Log.e("onTouchEvent==", x1 + "=x1" + x2 + "=x2" );
//
//
//        return super.onTouchEvent(event);
//    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        nestedScrollView.setNestedScrollingEnabled(false);
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            //当手指按下的时候
            x1 = event.getX();
            y1 = event.getY();
        }

        if (event.getAction() == MotionEvent.ACTION_UP) {
            //当手指离开的时候
            x2 = event.getX();
            y2 = event.getY();
            if (x1 - x2 > 10) {
//                Toast.makeText(activity, "向左滑", Toast.LENGTH_SHORT).show();
                if (y1 - y2 > 150) {
//                Toast.makeText(activity, "向上滑", Toast.LENGTH_SHORT).show();
                    return false;
                } else if (y2 - y1 > 150) {
//                Toast.makeText(activity, "向下滑", Toast.LENGTH_SHORT).show();
                    return false;
                }
                Log.e("滑动", "向左滑");
            }
            if (x2 - x1 > 10) {
                if (y1 - y2 > 150) {
//                Toast.makeText(activity, "向上滑", Toast.LENGTH_SHORT).show();
                    return false;
                } else if (y2 - y1 > 150) {
//                Toast.makeText(activity, "向下滑", Toast.LENGTH_SHORT).show();
                    return false;
                }
//                Toast.makeText(activity, "向右滑", Toast.LENGTH_SHORT).show();
                Log.e("滑动", "向右滑");
            }
        }
//        Log.e("onTouchEvent==", x1 + "=x1" + x2 + "=x2" );
        return true;

    }
}
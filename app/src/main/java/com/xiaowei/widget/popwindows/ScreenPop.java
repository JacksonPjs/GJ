package com.xiaowei.widget.popwindows;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.xiaowei.R;
import com.xiaowei.bean.ScreenBean;
import com.xiaowei.ui.Adapter.itemTextviewAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;


/**
 * @author jackson
 * @date 2018/11/19
 * 筛选窗口
 */
public class ScreenPop extends BasePopwindows {

    List<String> loanList;
    List<String> termList;
    List<String> precisionList;
    List<ScreenBean> beans;
    RecyclerView recyclerView;
    TextView del;
    TextView complete;
    Context context;
    itemTextviewAdapter adapter;
    int page = 1;
    int pageSize = 10;
    String minLoan = "";//最小金额
    String maxLoan = "";//最大金额
    String minTerm = "";//最低期限(天)
    String maxTerm = "";//最高期限
    String condition = "";//
    //    String condition="speed";//精准speed/rate/amount
    int sort = 1;//1/-1  （1：升序，-1：降序）

    private  static ScreenPop screenPop;
    public ScreenPop(Context context) {
        super(context);
        this.context=context;
        init();
    }
    public static ScreenPop getInstance(Context context) {

        if (screenPop == null) {
            screenPop = new ScreenPop(context);
        }
        return screenPop;
    }

    @Override
    protected int setLayout() {
        return R.layout.pop_screen_home;
    }



    private  void  init(){
        View view = super.initLayout(setLayout());
        setContentView(view);
        initView(view);
        setFocusable(true);
        setSize(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
    }
    private void initView(View view){
        recyclerView=view.findViewById(R.id.loanview);
        del=view.findViewById(R.id.del);
        complete=view.findViewById(R.id.complete);

        initData(view);

    }

    private void  initData(View view){
        loanList = new ArrayList();
        termList = new ArrayList();
        precisionList = new ArrayList();
        beans = new ArrayList<>();
        ScreenBean bean = new ScreenBean();
        loanList.add("不限额度");
        loanList.add("1000以下");
        loanList.add("1000~3000");
        loanList.add("3000~5000");
        loanList.add("5000~10000");
        loanList.add("10000以上");
        bean.setTitle("贷款金额");
        bean.setName(loanList);
        beans.add(bean);

        ScreenBean bean1 = new ScreenBean();
        bean1.setTitle("贷款期限");

        termList.add("不限期限");
        termList.add("0-7天");
        termList.add("7-15天");
        termList.add("15-30天");
        termList.add("30天以上");
        bean1.setName(termList);

        beans.add(bean1);

        ScreenBean bean2 = new ScreenBean();
        bean2.setTitle("精准标签");
        precisionList.add("下款速度最快");
        precisionList.add("最热门");
        precisionList.add("通过率最高");
        bean2.setName(precisionList);
        beans.add(bean2);
        LinearLayoutManager manager = new LinearLayoutManager(context);

        manager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(manager);
        adapter = new itemTextviewAdapter(beans, context);
        recyclerView.setAdapter(adapter);

        adapter.setOnItemClickLitener(new itemTextviewAdapter.OnItemClickLitener() {
            @Override
            public void onItemClick(int pos, int itempos) {
//                loanAdapter.setSelection(pos);
                switch (pos) {
                    case 0:
                        switch (itempos) {
                            case 0:
                                maxLoan = "";
                                minLoan = "";
                                break;
                            case 1:
                                maxLoan = 1000 + "";
                                minLoan = "0";
                                break;
                            case 2:
                                maxLoan = 3000 + "";
                                minLoan = 1000 + "";
                                break;
                            case 3:
                                maxLoan = 5000 + "";
                                minLoan = 3000 + "";
                                break;
                            case 4:
                                maxLoan = "10000";
                                minLoan = 5000 + "";
                                break;
                            case 5:
                                maxLoan = "";
                                minLoan = 10000 + "";
                                break;

                        }
                        break;
                    case 1:
                        switch (itempos) {
                            case 0:
                                minTerm = "";
                                maxTerm = "";
                                break;
                            case 1:
                                minTerm = 0 + "";
                                maxTerm = "7";
                                break;
                            case 2:
                                minTerm = 8 + "";
                                maxTerm = 15+ "";
                                break;
                            case 3:
                                minTerm = 15+ "";
                                maxTerm = 30 + "";
                                break;
                            case 4:
                                minTerm = "30";
                                maxTerm =  "";
                                break;
                        }
                        break;
                    case 2:

                        break;

                }

                if (onClickPopListener!=null){
                    onClickPopListener.onData(minLoan,maxLoan,minTerm,maxTerm);
                }
            }

            @Override
            public void OnItemLongClick(int pos) {

            }
        });

        del.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onClickPopListener!=null){
                    adapter.setResetting();
                    onClickPopListener.onCancel();
                }
            }
        });
        complete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onClickPopListener!=null){
                    onClickPopListener.onComplete();
                }
            }
        });

    }
    OnClickPopListener onClickPopListener;

    public void setOnClickPopListener(OnClickPopListener onClickPopListener) {
        this.onClickPopListener = onClickPopListener;
    }

    public interface OnClickPopListener{
        void onData(String minLoan,String maxLoan,String mixTerm,String maxTerm);
        void onCancel();
        void onComplete();
    }

}

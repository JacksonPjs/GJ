package com.xiaowei.widget.popwindows;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.xiaowei.R;

/**
 * @author jackson
 * @date 2018/11/19
 * 额度窗口
 */
public class SortPop extends BasePopwindows {

    TextView sortDi;
    TextView sortGao;
    @Override
    protected int setLayout() {
        return R.layout.pop_sort_home;
    }
    private  static SortPop sortPop;
    public SortPop(Context context) {
        super(context);
        init();
    }
    public static SortPop getInstance(Context context) {

        if (sortPop == null) {
            sortPop = new SortPop(context);
        }
        return sortPop;
    }

    private void init(){
        View view = super.initLayout(setLayout());
        setContentView(view);
        initView(view);
        setFocusable(true);
        setSize(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
    }
    private  void initView(View view){
        sortDi = view.findViewById(R.id.sort_di);
        sortGao = view.findViewById(R.id.sort_gao);

        initData();
    }
    private void initData(){
        sortDi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onClickSortListener!=null)
                    onClickSortListener.onSortDi();
            }
        });
        sortGao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onClickSortListener!=null)
                    onClickSortListener.onSortGao();
            }
        });

    }
    OnClickSortListener onClickSortListener;

    public void setOnClickSortListener(OnClickSortListener onClickSortListener) {
        this.onClickSortListener = onClickSortListener;
    }

    public interface OnClickSortListener{
        void  onSortDi();
        void  onSortGao();
    }

}

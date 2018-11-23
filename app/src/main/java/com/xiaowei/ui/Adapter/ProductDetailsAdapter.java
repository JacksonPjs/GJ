package com.xiaowei.ui.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.blibrary.flowlayout.FlowLayout;
import com.example.blibrary.flowlayout.TagAdapter;
import com.example.blibrary.flowlayout.TagFlowLayout;
import com.xiaowei.R;
import com.xiaowei.bean.DetailsItemBean;
import com.xiaowei.bean.ProductDetailsBean;
import com.xiaowei.bean.ScreenBean;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * @author jackson
 * @date 2018/11/15
 */
public class ProductDetailsAdapter extends RecyclerView.Adapter<ProductDetailsAdapter.ViewHolder> {
    private List<DetailsItemBean> datas;
    private Context context;
    int select = 0;
    OnItemClickLitener onItemClickLitener;

    public ProductDetailsAdapter(List<DetailsItemBean> datas, Context context) {
        this.datas = datas;
        this.context = context;
    }

    public void setOnItemClickLitener(OnItemClickLitener mOnItemClickLitener) {
        this.onItemClickLitener = mOnItemClickLitener;
    }


    @Override
    public ProductDetailsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_details, parent, false);
        return new ProductDetailsAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ProductDetailsAdapter.ViewHolder holder, final int position) {
        ViewGroup.LayoutParams layoutParams = holder.itemView.getLayoutParams();
        layoutParams.height = LinearLayout.LayoutParams.WRAP_CONTENT;
        holder.title.setText(datas.get(position).getTitle() + "");
        holder.tv.setText(datas.get(position).getDetails() + "");


    }


    @Override
    public int getItemCount() {

        return datas.size();

    }

    static class ViewHolder extends RecyclerView.ViewHolder {


        @Bind(R.id.details_item_title)
        TextView title;
        @Bind(R.id.details_item_tv)
        TextView tv;


        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

    public interface OnItemClickLitener {
        void onItemClick(int pos, int itempos);

        void OnItemLongClick(int pos);
    }


}

package com.xiaowei.ui.Adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.xiaowei.R;
import com.xiaowei.bean.ProductListBean;
import com.xiaowei.net.NetService;
import com.xiaowei.widget.BImageView.CustomRoundAngleImageView;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class HomeAdapter extends RecyclerView.Adapter<HomeAdapter.ViewHolder> {
    private List<ProductListBean.DataBean.contentBean> datas;
    private Context context;
    OnItemClickLitener onItemClickLitener;


    public HomeAdapter(List<ProductListBean.DataBean.contentBean> datas, Context context) {
        this.datas = datas;
        this.context = context;
    }

    public void setOnItemClickLitener(OnItemClickLitener mOnItemClickLitener) {
        this.onItemClickLitener = mOnItemClickLitener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_main, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        ProductListBean.DataBean.contentBean d = datas.get(position);
        Glide.with(context).load(d.getIcon())
                .error(R.mipmap.ic_launcher)
                .placeholder(R.mipmap.ic_launcher)
                .into(holder.imageView);

        holder.title.setText(d.getApplicants()+"人申请");
        holder.name.setText(d.getName() + "");
        String moneyShow="";
        if (d.getMinLoan()==d.getMaxLoan()){
            moneyShow=d.getMinLoan()+"";
        }else {
            moneyShow=d.getMinLoan()+"~"+d.getMaxLoan();
        }
        String str1 = "额度:";
        String str2 = "" + moneyShow;


        holder.tv_pos.setText(d.getSynopsis()+"");

        SpannableStringBuilder builder = new SpannableStringBuilder(str1 + str2);
        builder.setSpan(new ForegroundColorSpan(Color.parseColor("#666666")),
                str1.length(), (str1 + str2).length(), Spannable.SPAN_EXCLUSIVE_INCLUSIVE);

        holder.money.setText(builder);

        String str3 = "参考日利率:";
        String str4 = "" + d.getDayRateStr();
        SpannableStringBuilder builder1 = new SpannableStringBuilder(str3 + str4);
        builder1.setSpan(new ForegroundColorSpan(Color.parseColor("#F9593D")),
                str3.length(), (str3 + str4).length(), Spannable.SPAN_EXCLUSIVE_INCLUSIVE);
        holder.lilv.setText(builder1);

        holder.apply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onItemClickLitener != null) {
                    onItemClickLitener.OnItemViewClick(position);
                }
            }
        });
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onItemClickLitener != null) {
                    onItemClickLitener.onItemClick(position);
                }
            }
        });

        //  holder.circleProgressbar.setProgressNotInUiThread(80);


    }

    @Override
    public int getItemCount() {

        return datas.size();

    }

    static class ViewHolder extends RecyclerView.ViewHolder {


        @Bind(R.id.connect_usb_bt1)
        TextView title;
        @Bind(R.id.send_bmp_bt1)
        TextView money;
        @Bind(R.id.send_jpg_bt1)
        TextView lilv;
        @Bind(R.id.send_jpg_bt)
        TextView apply;//申请按钮
        @Bind(R.id.name)
        TextView name;
        @Bind(R.id.tv_pos)
        TextView tv_pos;
        @Bind(R.id.connect_usb_bt)
        CustomRoundAngleImageView imageView;


        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

    public interface OnItemClickLitener {
        void onItemClick(int pos);

        void OnItemViewClick(int pos);
    }


}


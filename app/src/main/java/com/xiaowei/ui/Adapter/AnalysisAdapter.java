package com.xiaowei.ui.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.xiaowei.R;
import com.xiaowei.utils.WidgetUtils;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * @author jackson
 * @date 2018/11/22
 */
public class AnalysisAdapter extends RecyclerView.Adapter<AnalysisAdapter.ViewHodle> {
    Context context;
    List list;

    public AnalysisAdapter(Context context, List<String> list) {
        this.context = context;
        this.list = list;
    }


    @NonNull
    @Override
    public ViewHodle onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_analysis, viewGroup, false);
        return new ViewHodle(view);
    }

    @Override
    public void onBindViewHolder(ViewHodle holder, final int i) {
        ViewGroup.LayoutParams layoutParams = holder.itemView.getLayoutParams();
        layoutParams.height = LinearLayout.LayoutParams.WRAP_CONTENT;
        holder.tvName.setText("小微");

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class ViewHodle extends RecyclerView.ViewHolder {
        @Bind(R.id.name)
        TextView tvName;
//        @Bind(R.id.money)
//        TextView tvMoney;
//        @Bind(R.id.data)
//        TextView tvData;
//        @Bind(R.id.type)
//        TextView tvType;

        public ViewHodle(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

}


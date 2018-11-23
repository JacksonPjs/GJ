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
 * @date 2018/11/20
 */
public class BillAdapter extends RecyclerView.Adapter<BillAdapter.ViewHodle> {
    Context context;
    List list;

    public BillAdapter(Context context, List<String> list) {
        this.context = context;
        this.list = list;
    }


    @NonNull
    @Override
    public ViewHodle onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_bill, viewGroup, false);
        return new ViewHodle(view);
    }

    @Override
    public void onBindViewHolder(ViewHodle holder, final int i) {
        ViewGroup.LayoutParams layoutParams = holder.itemView.getLayoutParams();
        layoutParams.height = LinearLayout.LayoutParams.WRAP_CONTENT;
        holder.tvName.setText("大萨达");
        holder.tvMoney.setText(WidgetUtils.changTVsize("¥2000.00"));
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onBillClickListener != null)
                    onBillClickListener.onItemClick(i);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class ViewHodle extends RecyclerView.ViewHolder {
        @Bind(R.id.name)
        TextView tvName;
        @Bind(R.id.money)
        TextView tvMoney;
        @Bind(R.id.data)
        TextView tvData;

        public ViewHodle(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    OnBillClickListener onBillClickListener;

    public void setOnBillClickListener(OnBillClickListener onBillClickListener) {
        this.onBillClickListener = onBillClickListener;
    }

    public interface OnBillClickListener {
        void onItemClick(int pos);
    }
}

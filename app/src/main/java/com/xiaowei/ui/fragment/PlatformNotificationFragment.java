package com.xiaowei.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.xiaowei.R;

import butterknife.ButterKnife;

/**
 * @author jackson
 * @date 2018/11/23
 */
public class PlatformNotificationFragment extends BaseFragment {

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        Bundle bundle = getArguments();
//        id = bundle.getString("id");
//        borrowStatus = bundle.getInt("borrowStatus");
    }

    @Override
    public View initView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_notification_platform, null);
        ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void fillDate() {

    }

    @Override
    public void requestData() {

    }
}

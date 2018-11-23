package com.xiaowei.ui.Adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

public class ViewPagerFramentAdapter extends FragmentPagerAdapter {

	List<Fragment> fragments ;
	
	public ViewPagerFramentAdapter(FragmentManager fm , List<Fragment> fragments) {
		super(fm);
		this.fragments = fragments;
	}

	@Override
	public Fragment getItem(int arg0) {
		
		return fragments.get(arg0);
	}

	@Override
	public int getCount() {
		
		return fragments.size();
	}

	
	

}

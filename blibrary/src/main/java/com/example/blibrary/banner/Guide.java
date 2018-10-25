package com.example.blibrary.banner;

import android.app.Activity;
import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Scroller;

import java.lang.ref.WeakReference;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class Guide extends ViewPager {
    private long interval = 1500;
    private int currentPosition;
    private Context context;

    private MyHandler myHandler;
    private MyPagerAdapter pagerAdapter;
    private List<Object> mDataSource;

    private int dataSourceSize = 0;
    private BannerIndicator indicator;

    public Guide(Context context) {
        super(context);
        init(context);
    }

    public Guide(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);

    }

    private void init(Context context) {
        this.context = context;
        setOffscreenPageLimit(3);
        addOnPageChangeListener(mOnPageChangeListener);

        mDataSource = new ArrayList<>();
        myHandler = new MyHandler(this);
    }

    public void attachIndicator(BannerIndicator bannerIndicator) {
        indicator = bannerIndicator;
        indicator.setImageIndicator(dataSourceSize);
        setIndicatorSelectItem(0);
    }


    public long getInterval() {
        return interval;
    }

    public void setInterval(long interval) {
        this.interval = interval;
    }

    public void setPageChangeDuration(int duration) {
        try {
            Field scrollerField = ViewPager.class.getDeclaredField("mScroller");
            scrollerField.setAccessible(true);
            scrollerField.set(this, new BannerScroller(getContext(), duration));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public class BannerScroller extends Scroller {
        private int mDuration = 1000;

        public BannerScroller(Context context, int duration) {
            super(context);
            mDuration = duration;
        }

        @Override
        public void startScroll(int startX, int startY, int dx, int dy) {
            super.startScroll(startX, startY, dx, dy, mDuration);
        }

        @Override
        public void startScroll(int startX, int startY, int dx, int dy, int duration) {
            super.startScroll(startX, startY, dx, dy, mDuration);
        }
    }


    public void setDataSource(Object dataSource) {
        mDataSource.clear();
        if (!(dataSource instanceof List) || dataSource == null) {
            return;
        }
        List<Object> data = (List<Object>) dataSource;
        if (data.isEmpty()) return;
        dataSourceSize = data.size();

        if (dataSourceSize >= 2) {
//            mDataSource.add(data.get(dataSourceSize - 1));
            mDataSource.addAll(data);
//            mDataSource.add(data.get(0));
        } else {
            mDataSource.add(data.get(0));
        }

        if (mDataSource == null || mDataSource.size() == 1) {
            setEnabled(false);
            setClickable(false);
            setFocusable(false);
            setFocusableInTouchMode(false);
        }
        pagerAdapter = new MyPagerAdapter(mDataSource);
        setAdapter(pagerAdapter);
        if (indicator != null) {
            indicator.setImageIndicator(dataSourceSize);
        }

        currentPosition = 0;
        setCurrentItem(0);
    }

    public void showNextView() {
        if (mDataSource == null || mDataSource.size() == 1)
            return;
        if (mDataSource.size() == currentPosition)
            return;
        if (mDataSource.size() == currentPosition+1){
            if (onLastItemChangeListener!=null){
                onLastItemChangeListener.onLastItem(currentPosition);
            }
        }

        setCurrentItem(currentPosition + 1, true);
        setIndicatorSelectItem(currentPosition);
    }

    boolean isScrolled=false;
    ViewPager.OnPageChangeListener mOnPageChangeListener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float baifen, int offset) {

        }


        @Override
        public void onPageSelected(int position) {
            if (pagerAdapter.getCount() == 0)
                return;
            if(position>currentPosition){
//从左向右滑
                Log.e("onPageSelected","从左向右滑" );
                currentPosition = position;
                if (position >= pagerAdapter.getCount()) {
//                    if (onLastItemChangeListener!=null){
//                        onLastItemChangeListener.onLastItem();
//                    }
                }
            }
            if(position<currentPosition) {
//从右向左滑
                Log.e("onPageSelected","从右向左滑");
                currentPosition = position;

            }
//            currentPosition = position;
            if (position < 1) {
//                currentPosition = pagerAdapter.getCount() - 2;
            } else if (position >= pagerAdapter.getCount()) {
                currentPosition=pagerAdapter.getCount() - 1;
//                currentPosition = 1;
            }
            if (onBannerItemChangeListener != null) {
                onBannerItemChangeListener.onItemChage(position);
            }

            setIndicatorSelectItem(currentPosition);
        }

        @Override
        public void onPageScrollStateChanged(int state) {
            if (pagerAdapter.getCount() == 0)
                return;
            if (state == ViewPager.SCROLL_STATE_SETTLING) {
//滑动后自然沉降的状态
                isScrolled=true;
            } else if (state == ViewPager.SCROLL_STATE_DRAGGING) {
                //滑动状态
                currentPosition = getCurrentItem();
                isScrolled=false;
                if (currentPosition < 1) {
//                    currentPosition = pagerAdapter.getCount() - 2;
                    setCurrentItem(currentPosition, false);
                } else if (currentPosition >= pagerAdapter.getCount() - 1) {

//                    currentPosition = 1;
//                    setCurrentItem(currentPosition, false);
//
                }
//                pauseScroll();
            } else if (state == ViewPager.SCROLL_STATE_IDLE) {
                //空闲状态
                if (currentPosition >= pagerAdapter.getCount() - 1&& !isScrolled) {
//                    currentPosition = 1;
                    if (onLastItemChangeListener!=null){
                        //最后一页向后滑动
                        onLastItemChangeListener.onLastItem(currentPosition);
                    }
                    isScrolled=true;
//                    setCurrentItem(currentPosition, false);
//
                }
                setCurrentItem(currentPosition, false);

                resumeScroll();
            }
        }
    };

    public void resumeScroll() {
        if (mDataSource == null || mDataSource.size() == 1)
            return;
        myHandler.sendMessageDelayed(myHandler.obtainMessage(MyHandler.MESSAGE_CHECK), interval);
    }

    public void pauseScroll() {
        myHandler.removeMessages(MyHandler.MESSAGE_CHECK);
    }

    public interface BannerDataInit {
        ImageView initImageView();

        void initImgData(ImageView imageView, Object imgPath);
    }

    Banner.BannerDataInit bannerDataInit;

    public void setBannerDataInit(Banner.BannerDataInit bannerDataInit) {
        this.bannerDataInit = bannerDataInit;
    }

    private void setIndicatorSelectItem(int position) {
        if (indicator == null) return;

            indicator.setSelectItem(position);
    }

    private class MyPagerAdapter extends PagerAdapter {

        private final ArrayList<ImageView> viewList;

        public MyPagerAdapter(List<Object> data) {
            if (data == null) {
                viewList = new ArrayList<>();
                return;
            }

            viewList = new ArrayList<>();

            for (int i = 0; i < data.size(); i++) {
                ImageView imageView;
                imageView = bannerDataInit.initImageView();


                bannerDataInit.initImgData(imageView, mDataSource.get(i));
                viewList.add(imageView);
            }
        }


        @Override
        public boolean isViewFromObject(View arg0, Object arg1) {
            return arg0 == arg1;
        }

        @Override
        public int getCount() {
            return viewList.size();
        }

        @Override
        public void destroyItem(ViewGroup container, int position,
                                Object object) {
            container.removeView(viewList.get(position));

        }

        @Override
        public int getItemPosition(Object object) {
            return POSITION_NONE;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return "title" + position;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            ViewGroup.LayoutParams lp = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
            container.addView(viewList.get(position), lp);
            return viewList.get(position);
        }

    }

    private static class MyHandler extends Handler {
        public static final int MESSAGE_CHECK = 9001;
        private WeakReference<Guide> innerObject;

        public MyHandler(Guide context) {
            this.innerObject = new WeakReference<>(context);
        }

        @Override
        public void handleMessage(Message msg) {
            if (MESSAGE_CHECK == msg.what) {
                Guide guide = innerObject.get();
                if (guide == null)
                    return;
                if (guide.getContext() instanceof Activity) {
                    Activity activity = (Activity) guide.getContext();
                    if (activity.isFinishing())
                        return;
                }
                guide.showNextView();

                removeMessages(MESSAGE_CHECK);
                sendMessageDelayed(obtainMessage(MESSAGE_CHECK), guide.interval);
                return;
            }
            super.handleMessage(msg);
        }

    }

    OnBannerItemClickListener onBannerItemClickListener;
    OnBannerItemChangeListener onBannerItemChangeListener;
    OnLastItemChangeListener onLastItemChangeListener;

    public void setOnLastItemChangeListener(OnLastItemChangeListener onLastItemChangeListener) {
        this.onLastItemChangeListener = onLastItemChangeListener;
    }

    public void setmOnPageChangeListener(OnPageChangeListener mOnPageChangeListener) {
        this.mOnPageChangeListener = mOnPageChangeListener;
    }

    public void setOnBannerItemClickListener(OnBannerItemClickListener onBannerItemClickListener) {
        this.onBannerItemClickListener = onBannerItemClickListener;
    }

    public void setOnBannerItemChangeListener(OnBannerItemChangeListener onBannerItemChangeListener) {
        this.onBannerItemChangeListener = onBannerItemChangeListener;
    }

    //监听点击事件
    public interface OnBannerItemClickListener {
        void onItemClick(int position);

    }

    //监听滑动事件
    public interface OnBannerItemChangeListener {
        void onItemChage(int position);

    }

    //监听滑动事件
    public interface OnLastItemChangeListener {
        void onLastItem(int position);

    }
}

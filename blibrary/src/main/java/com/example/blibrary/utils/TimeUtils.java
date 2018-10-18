package com.example.blibrary.utils;

import android.os.CountDownTimer;

/**
 * 创建日期：2018/5/26 on 13:57
 * 描述:日期时间工具类
 * 作者:jackson Administrator
 */
public class TimeUtils {


    static int TOTAL_TIME = 60 * 1000;//倒计时的总时间，单位ms
    static int ONECE_TIME = 1000;//倒计时的时间间隔，单位ms

    public static int getTotalTime() {
        return TOTAL_TIME;
    }

    public static void setTotalTime(int totalTime) {
        TOTAL_TIME = totalTime;
    }

    public static int getOneceTime() {
        return ONECE_TIME;
    }

    public static void setOneceTime(int oneceTime) {
        ONECE_TIME = oneceTime;
    }

    public interface CountDownTimerlistener {
        void onTick(String time);

        void onFinish();
    }

    static CountDownTimerlistener Timerlistener;


    public static void setCountDownTimerListener(CountDownTimerlistener countDownTimerListener) {
        Timerlistener = countDownTimerListener;
    }
    private static MyCountDownTimer countDownTimer;

    /**
     * CountDownTimer 实现倒计时
     */
    static class  MyCountDownTimer extends CountDownTimer{

        public MyCountDownTimer(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }

        @Override
        public void onTick(long millisUntilFinished) {
            String value = String.valueOf((int) (millisUntilFinished / 1000));
            if (Timerlistener != null) {
                Timerlistener.onTick(value);
            }
        }

        @Override
        public void onFinish() {
            if (Timerlistener != null) {
                Timerlistener.onFinish();
            }
        }
    }


//    public static CountDownTimer countDownTimer = new CountDownTimer(TOTAL_TIME, ONECE_TIME) {
//        @Override
//        public void onTick(long millisUntilFinished) {
//            String value = String.valueOf((int) (millisUntilFinished / 1000));
//            if (Timerlistener != null) {
//                Timerlistener.onTick(value);
//            }
//        }
//
//        @Override
//        public void onFinish() {
//            if (Timerlistener != null) {
//                Timerlistener.onFinish();
//            }
//        }
//    };

    /**
     * 取消倒计时
     */
    public static void timerCancel() {

        if (countDownTimer != null)
            countDownTimer.cancel();
    }

    /**
     * 开始倒计时
     */
    public static void timerStart(int TOTAL_TIME,int ONECE_TIME) {
        countDownTimer =new MyCountDownTimer(TOTAL_TIME,ONECE_TIME);
        countDownTimer.start();
    }

}

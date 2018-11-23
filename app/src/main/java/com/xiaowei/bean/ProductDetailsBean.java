package com.xiaowei.bean;

import java.util.List;

/**
 * @author jackson
 * @date 2018/11/15
 */
public class ProductDetailsBean {
    private int code;
    private String msg;
    private  DataBean data;

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public static class DataBean{
        int id;
        float dayRate;
        int applicants;
        int minTerm;
        int maxTerm;
        int loanTime;
        int sort;
        int state;
        int minLoan;
        int maxLoan;
        String name;
        String code;
        String icon;

        String appleLink;
        String jumpLink;
        String synopsis;
        String loanTimeStr;
        String dayRateStr;
        String androidLink;
        String process;//办理流程
        String applicationConditions;//申请条件
        String requiredMaterials;//所需材料

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public float getDayRate() {
            return dayRate;
        }

        public void setDayRate(float dayRate) {
            this.dayRate = dayRate;
        }

        public int getApplicants() {
            return applicants;
        }

        public void setApplicants(int applicants) {
            this.applicants = applicants;
        }

        public int getMinTerm() {
            return minTerm;
        }

        public void setMinTerm(int minTerm) {
            this.minTerm = minTerm;
        }

        public int getMaxTerm() {
            return maxTerm;
        }

        public void setMaxTerm(int maxTerm) {
            this.maxTerm = maxTerm;
        }

        public int getLoanTime() {
            return loanTime;
        }

        public void setLoanTime(int loanTime) {
            this.loanTime = loanTime;
        }

        public int getSort() {
            return sort;
        }

        public void setSort(int sort) {
            this.sort = sort;
        }

        public int getState() {
            return state;
        }

        public void setState(int state) {
            this.state = state;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public String getIcon() {
            return icon;
        }

        public void setIcon(String icon) {
            this.icon = icon;
        }

        public int getMinLoan() {
            return minLoan;
        }

        public void setMinLoan(int minLoan) {
            this.minLoan = minLoan;
        }

        public int getMaxLoan() {
            return maxLoan;
        }

        public void setMaxLoan(int maxLoan) {
            this.maxLoan = maxLoan;
        }

        public String getAppleLink() {
            return appleLink;
        }

        public void setAppleLink(String appleLink) {
            this.appleLink = appleLink;
        }

        public String getJumpLink() {
            return jumpLink;
        }

        public void setJumpLink(String jumpLink) {
            this.jumpLink = jumpLink;
        }

        public String getSynopsis() {
            return synopsis;
        }

        public void setSynopsis(String synopsis) {
            this.synopsis = synopsis;
        }

        public String getLoanTimeStr() {
            return loanTimeStr;
        }

        public void setLoanTimeStr(String loanTimeStr) {
            this.loanTimeStr = loanTimeStr;
        }

        public String getDayRateStr() {
            return dayRateStr;
        }

        public void setDayRateStr(String dayRateStr) {
            this.dayRateStr = dayRateStr;
        }

        public String getAndroidLink() {
            return androidLink;
        }

        public void setAndroidLink(String androidLink) {
            this.androidLink = androidLink;
        }

        public String getProcess() {
            return process;
        }

        public void setProcess(String process) {
            this.process = process;
        }

        public String getApplicationConditions() {
            return applicationConditions;
        }

        public void setApplicationConditions(String applicationConditions) {
            this.applicationConditions = applicationConditions;
        }

        public String getRequiredMaterials() {
            return requiredMaterials;
        }

        public void setRequiredMaterials(String requiredMaterials) {
            this.requiredMaterials = requiredMaterials;
        }
    }
}

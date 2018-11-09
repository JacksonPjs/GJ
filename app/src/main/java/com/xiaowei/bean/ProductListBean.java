package com.xiaowei.bean;

import java.util.List;

public class ProductListBean {
    /*{
    "code": 0,
    "msg": "success",
    "data": [{
        id: 1,
        name: "人人贷",
        icon: "192.168.1.12/xxx.jpg",
        minLoan: 1000,
        maxLoan: 1500,
        dayRate: 0.0006,
        dayRateStr: "0.06%",
        minTerm: 7,
        maxTerm: 14,
        loanTime: 60,
        loanTimeStr: "1小时",
        sort: 99,
        applicants: 2000
    }]
}
    * */
    private int code;
    private String msg;
    private DataBean data;

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

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {

        private List<contentBean> content;

        public List<contentBean> getContent() {
            return content;
        }

        public void setContent(List<contentBean> content) {
            this.content = content;
        }

        public static class contentBean {
            private int id;
            private int loanTime;
            private int maxLoan;
            private int minLoan;
            private int maxTerm;
            private int minTerm;
            private int sort;
            private String name;
            private String icon;
            private int applicants;
            private String dayRate;
            private String dayRateStr;
            private String loanTimeStr;
            private String synopsis;
            private String jumpLink;
            private String registrationLink;//注册连接
            private String androidDownloadLink;//第三方下载页面链接

            public String getRegistrationLink() {
                return registrationLink;
            }

            public void setRegistrationLink(String registrationLink) {
                this.registrationLink = registrationLink;
            }

            public String getAndroidDownloadLink() {
                return androidDownloadLink;
            }

            public void setAndroidDownloadLink(String androidDownloadLink) {
                this.androidDownloadLink = androidDownloadLink;
            }

            public String getJumpLink() {
                return jumpLink;
            }

            public void setJumpLink(String jumpLink) {
                this.jumpLink = jumpLink;
            }

            ;

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public int getLoanTime() {
                return loanTime;
            }

            public void setLoanTime(int loanTime) {
                this.loanTime = loanTime;
            }

            public int getMaxLoan() {
                return maxLoan;
            }

            public void setMaxLoan(int maxLoan) {
                this.maxLoan = maxLoan;
            }

            public int getMinLoan() {
                return minLoan;
            }

            public void setMinLoan(int minLoan) {
                this.minLoan = minLoan;
            }

            public int getMaxTerm() {
                return maxTerm;
            }

            public void setMaxTerm(int maxTerm) {
                this.maxTerm = maxTerm;
            }

            public int getMinTerm() {
                return minTerm;
            }

            public void setMinTerm(int minTerm) {
                this.minTerm = minTerm;
            }

            public int getSort() {
                return sort;
            }

            public void setSort(int sort) {
                this.sort = sort;
            }

            public String getIcon() {
                return icon;
            }

            public void setIcon(String icon) {
                this.icon = icon;
            }

            public int getApplicants() {
                return applicants;
            }

            public void setApplicants(int applicants) {
                this.applicants = applicants;
            }

            public String getDayRate() {
                return dayRate;
            }

            public void setDayRate(String dayRate) {
                this.dayRate = dayRate;
            }

            public String getDayRateStr() {
                return dayRateStr;
            }

            public void setDayRateStr(String dayRateStr) {
                this.dayRateStr = dayRateStr;
            }

            public String getLoanTimeStr() {
                return loanTimeStr;
            }

            public void setLoanTimeStr(String loanTimeStr) {
                this.loanTimeStr = loanTimeStr;
            }

            public String getSynopsis() {
                return synopsis;
            }

            public void setSynopsis(String synopsis) {
                this.synopsis = synopsis;
            }
        }


    }


}

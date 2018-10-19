package com.xiaowei.bean;

import java.util.List;
/*公告实体类
* */
public class NoticeBean {
/*{
    "code": 0,
    "msg": "success",
    "data": [{
        id: 1,
        theme: "主题",
        publisher: "发布人",
        releaseTime: "发布时间",
        content: "内容"
    }]
}*/

    private int code;
    private String msg;
    private List<NoticeData> data;

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

    public List<NoticeData> getData() {
        return data;
    }

    public void setData(List<NoticeData> data) {
        this.data = data;
    }

    public static class NoticeData {
        int id;
        String theme;
        String publisher;//位置
        String releaseTime;
        String content;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getTheme() {
            return theme;
        }

        public void setTheme(String theme) {
            this.theme = theme;
        }

        public String getPublisher() {
            return publisher;
        }

        public void setPublisher(String publisher) {
            this.publisher = publisher;
        }

        public String getReleaseTime() {
            return releaseTime;
        }

        public void setReleaseTime(String releaseTime) {
            this.releaseTime = releaseTime;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }
    }
}

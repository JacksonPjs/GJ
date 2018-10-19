package com.xiaowei.bean;

import java.util.List;
/*轮播图实体类*/
public class BannerBean {
    /*{
    "code": 0,
    "msg": "success",
    "data": [{
        id: 1,
        name: "广告名称",
        sort: 1,            //轮播图位置
        image: "图片url",
        url: "广告链接",
        remark: "备注"
    }]
}*/
    private int code;
    private String msg;
    private List<BannerData> data;

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

    public List<BannerData> getData() {
        return data;
    }

    public void setData(List<BannerData> data) {
        this.data = data;
    }

    public static class BannerData {
        int id;
        String name;
        String sort;//位置
        String image;
        String url;
        String remark;

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

        public String getSort() {
            return sort;
        }

        public void setSort(String sort) {
            this.sort = sort;
        }

        public String getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public String getRemark() {
            return remark;
        }

        public void setRemark(String remark) {
            this.remark = remark;
        }

    }


}

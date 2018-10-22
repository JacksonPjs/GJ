package com.xiaowei.bean;

import java.io.Serializable;
import java.util.List;
/*开屏与弹窗广告实体类*/
public class AdvertBean implements Serializable {
    /*
    * {
    "code": 0,
    "msg": "success",
    "data": [{
        id: 1,
        name: "广告名称",
        position: 1,
        positionStr: "开屏广告",
        image: "图片url",
        url: "广告链接",
        remark: "备注"
    }]
}
    * */
    private int code;
    private String msg;
    private List<AdverBean> data;

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

    public List<AdverBean> getData() {
        return data;
    }

    public void setData(List<AdverBean> data) {
        this.data = data;
    }

    public static class AdverBean implements Serializable{
        int id;
        String name;
        String positionStr;
        String image;
        String url;
        String remark;
        int position;

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

        public String getPositionStr() {
            return positionStr;
        }

        public void setPositionStr(String positionStr) {
            this.positionStr = positionStr;
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

        public int getPosition() {
            return position;
        }

        public void setPosition(int position) {
            this.position = position;
        }
    }

}

package com.hhyg.TyClosing.entities.brand;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by user on 2017/8/9.
 */

public class Res {




    private int errcode;
    private String op;
    private DataBean data;
    private int channel;

    public int getErrcode() {
        return errcode;
    }

    public void setErrcode(int errcode) {
        this.errcode = errcode;
    }

    public String getOp() {
        return op;
    }

    public void setOp(String op) {
        this.op = op;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public int getChannel() {
        return channel;
    }

    public void setChannel(int channel) {
        this.channel = channel;
    }

    public static class DataBean {

        private Map<String,ArrayList<BrandInfo>> info;
        private List<String> keys;
        private List<BrandInfo> hotbrand;

        public Map<String,ArrayList<BrandInfo>> getInfo() {
            return info;
        }

        public void setInfo( Map<String,ArrayList<BrandInfo>> info) {
            this.info = info;
        }

        public List<String> getKeys() {
            return keys;
        }

        public void setKeys(List<String> keys) {
            this.keys = keys;
        }

        public List<BrandInfo> getHotbrand() {
            return hotbrand;
        }

        public void setHotbrand(List<BrandInfo> hotbrand) {
            this.hotbrand = hotbrand;
        }



    }
}

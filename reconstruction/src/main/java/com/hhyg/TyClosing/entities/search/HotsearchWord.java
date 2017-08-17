package com.hhyg.TyClosing.entities.search;

import java.util.List;

/**
 * Created by user on 2017/8/14.
 */

public class HotsearchWord {


    private int errcode;
    private String op;
    private int channel;
    private List<RecommendBean> recommend;

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

    public int getChannel() {
        return channel;
    }

    public void setChannel(int channel) {
        this.channel = channel;
    }

    public List<RecommendBean> getRecommend() {
        return recommend;
    }

    public void setRecommend(List<RecommendBean> recommend) {
        this.recommend = recommend;
    }

    public static class RecommendBean {
        /**
         * hotword : 二三四五六七八九
         */

        private String hotword;

        public String getHotword() {
            return hotword;
        }

        public void setHotword(String hotword) {
            this.hotword = hotword;
        }
    }
}

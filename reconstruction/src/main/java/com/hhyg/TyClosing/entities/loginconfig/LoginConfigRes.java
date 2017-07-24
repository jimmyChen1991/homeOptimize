package com.hhyg.TyClosing.entities.loginconfig;

/**
 * Created by user on 2017/6/27.
 */

public class LoginConfigRes {

    /**
     * errcode : 1
     * op : getconfig
     * channel :
     * data : {"card_active":1,"privilege_active":1}
     * msg : 获取成功
     */

    private int errcode;
    private String op;
    private String channel;
    private DataBean data;
    private String msg;

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

    public String getChannel() {
        return channel;
    }

    public void setChannel(String channel) {
        this.channel = channel;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public static class DataBean {
        /**
         * card_active : 1
         * privilege_active : 1
         */

        private String card_active;
        private String privilege_active;

        public String getCard_active() {
            return card_active;
        }

        public void setCard_active(String card_active) {
            this.card_active = card_active;
        }

        public String getPrivilege_active() {
            return privilege_active;
        }

        public void setPrivilege_active(String privilege_active) {
            this.privilege_active = privilege_active;
        }
    }
}

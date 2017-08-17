package com.hhyg.TyClosing.entities.order;

/**
 * Created by user on 2017/8/14.
 */

public class BindSalerRes {

    /**
     * errcode : 1
     * op : getallgoodsinfo
     * msg :
     */

    private int errcode;
    private String op;
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

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}

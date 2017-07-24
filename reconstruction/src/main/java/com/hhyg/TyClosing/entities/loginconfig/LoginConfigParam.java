package com.hhyg.TyClosing.entities.loginconfig;

/**
 * Created by user on 2017/6/27.
 */

public class LoginConfigParam {

    /**
     * shopid : 1
     * channel : 673
     * imei : axxxxxx
     * saler_id : 8
     */

    private String shopid;
    private int channel;
    private String imei;
    private String saler_id;

    public String getShopid() {
        return shopid;
    }

    public void setShopid(String shopid) {
        this.shopid = shopid;
    }

    public int getChannel() {
        return channel;
    }

    public void setChannel(int channel) {
        this.channel = channel;
    }

    public String getImei() {
        return imei;
    }

    public void setImei(String imei) {
        this.imei = imei;
    }

    public String getSaler_id() {
        return saler_id;
    }

    public void setSaler_id(String saler_id) {
        this.saler_id = saler_id;
    }
}

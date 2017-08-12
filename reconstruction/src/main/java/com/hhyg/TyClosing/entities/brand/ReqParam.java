package com.hhyg.TyClosing.entities.brand;

/**
 * Created by user on 2017/8/9.
 */

public class ReqParam {

    /**
     * channel : 840
     * shopid : 6
     * imei : asdasd
     * platformId : 3
     */

    private String channel;
    private String shopid;
    private String imei;
    private String platformId;

    public String getChannel() {
        return channel;
    }

    public void setChannel(String channel) {
        this.channel = channel;
    }

    public String getShopid() {
        return shopid;
    }

    public void setShopid(String shopid) {
        this.shopid = shopid;
    }

    public String getImei() {
        return imei;
    }

    public void setImei(String imei) {
        this.imei = imei;
    }

    public String getPlatformId() {
        return platformId;
    }

    public void setPlatformId(String platformId) {
        this.platformId = platformId;
    }
}

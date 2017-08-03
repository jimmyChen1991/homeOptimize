package com.hhyg.TyClosing.entities.home;

/**
 * Created by user on 2017/8/2.
 */

public class ReqParam {

    /**
     * device_type : android
     * platformId : 3
     * imei : d30a10153d976f3a
     * shopid : 6
     * channel : 840
     * saleId : 8
     */

    private String platformId;
    private String imei;
    private String shopid;
    private String channel;

    public String getPlatformId() {
        return platformId;
    }

    public void setPlatformId(String platformId) {
        this.platformId = platformId;
    }

    public String getImei() {
        return imei;
    }

    public void setImei(String imei) {
        this.imei = imei;
    }

    public String getShopid() {
        return shopid;
    }

    public void setShopid(String shopid) {
        this.shopid = shopid;
    }

    public String getChannel() {
        return channel;
    }

    public void setChannel(String channel) {
        this.channel = channel;
    }
}

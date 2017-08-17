package com.hhyg.TyClosing.entities.order;

/**
 * Created by user on 2017/8/14.
 */

public class BindSalerReq {

    /**
     * shopid : 1
     * platformid : 3
     * op : changesaler
     * channel : 673
     * imei : 0
     * salerid : 297
     * bianhao : lianyuanfu
     * ordersn : 2017073168825
     */

    private String shopid;
    private String platformid;
    private String op;
    private String channel;
    private String imei;
    private String salerid;
    private String bianhao;
    private String ordersn;

    public String getShopid() {
        return shopid;
    }

    public void setShopid(String shopid) {
        this.shopid = shopid;
    }

    public String getPlatformid() {
        return platformid;
    }

    public void setPlatformid(String platformid) {
        this.platformid = platformid;
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

    public String getImei() {
        return imei;
    }

    public void setImei(String imei) {
        this.imei = imei;
    }

    public String getSalerid() {
        return salerid;
    }

    public void setSalerid(String salerid) {
        this.salerid = salerid;
    }

    public String getBianhao() {
        return bianhao;
    }

    public void setBianhao(String bianhao) {
        this.bianhao = bianhao;
    }

    public String getOrdersn() {
        return ordersn;
    }

    public void setOrdersn(String ordersn) {
        this.ordersn = ordersn;
    }
}

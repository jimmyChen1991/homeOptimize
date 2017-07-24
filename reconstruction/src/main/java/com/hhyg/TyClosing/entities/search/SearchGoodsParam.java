package com.hhyg.TyClosing.entities.search;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by user on 2017/6/9.
 */

public class SearchGoodsParam implements Cloneable{

    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

    /**
     * op : searchgoods
     * channel : 体验店渠道号
     * imei : imei号
     * shopid : 体验店ID
     * platformId : 平台号，Android传3，H5传1
     * data : {"keyword":"搜索关键字（非必需(需要UrlEncode 特殊字符需要进行处理)","brandId":"品牌Id（非必需：多个之间用英文半角逗号隔开）","class1Id":"一级类目ID（非必需）","class2Id":"二级类目ID（非必需）","class3Id":"三级类目ID（非必需）","minPrice":"最小价格（非必需）","maxPrice":"最大价格（非必需）","available":"是否有货（非必需：0无货，1有货）","sortType":"sortType（非必需：0权重降序(默认)， 1销量升序 ，-1销量降序， 2上架时间升序 ，-2上架时间降序 ，3价格升序 ，-3价格降序）","propertyList":"属性列表（非必须，格式：属性名1_属性值1,属性名2_属性值2。需要UrlEncode 特殊字符需要进行处理）","pageNo":"页号","activityId":"活动ID（非必须，多个之间用英文半角逗号隔开）","userCart":{"sign":"秘钥","userId":"用户id"}}
     */

    private String op;
    private String channel;
    private String imei;
    private String shopid;
    private String platformId;
    private DataBean data;

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

    public String getShopid() {
        return shopid;
    }

    public void setShopid(String shopid) {
        this.shopid = shopid;
    }

    public String getPlatformId() {
        return platformId;
    }

    public void setPlatformId(String platformId) {
        this.platformId = platformId;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "SearchGoodsParam{" +
                "op='" + op + '\'' +
                ", channel='" + channel + '\'' +
                ", imei='" + imei + '\'' +
                ", shopid='" + shopid + '\'' +
                ", platformId='" + platformId + '\'' +
                ", data=" + data +
                '}';
    }

    public static class DataBean implements Parcelable,Cloneable {

        @Override
        public Object clone() throws CloneNotSupportedException {
            return super.clone();
        }

        /**
         * keyword : 搜索关键字（非必需(需要UrlEncode 特殊字符需要进行处理)
         * brandId : 品牌Id（非必需：多个之间用英文半角逗号隔开）
         * class1Id : 一级类目ID（非必需）
         * class2Id : 二级类目ID（非必需）
         * class3Id : 三级类目ID（非必需）
         * minPrice : 最小价格（非必需）
         * maxPrice : 最大价格（非必需）
         * available : 是否有货（非必需：0无货，1有货）
         * sortType : sortType（非必需：0权重降序(默认)， 1销量升序 ，-1销量降序， 2上架时间升序 ，-2上架时间降序 ，3价格升序 ，-3价格降序）
         * propertyList : 属性列表（非必须，格式：属性名1_属性值1,属性名2_属性值2。需要UrlEncode 特殊字符需要进行处理）
         * pageNo : 页号
         * activityId : 活动ID（非必须，多个之间用英文半角逗号隔开）
         * userCart : {"sign":"秘钥","userId":"用户id"}
         */
        public void clear(){

        }
        private String keyword;
        private String brandId;
        private String class1Id;
        private String class2Id;
        private String class3Id;
        private String minPrice;
        private String maxPrice;
        private String available;
        private String sortType;
        private String propertyList;
        private int pageNo;
        private String activityId;
        private String pageSize;

        public String getKeyword() {
            return keyword;
        }

        public void setKeyword(String keyword) {
            this.keyword = keyword;
        }

        public String getBrandId() {
            return brandId;
        }

        public void setBrandId(String brandId) {
            this.brandId = brandId;
        }

        public String getClass1Id() {
            return class1Id;
        }

        public void setClass1Id(String class1Id) {
            this.class1Id = class1Id;
        }

        public String getClass2Id() {
            return class2Id;
        }

        public void setClass2Id(String class2Id) {
            this.class2Id = class2Id;
        }

        public String getClass3Id() {
            return class3Id;
        }

        public void setClass3Id(String class3Id) {
            this.class3Id = class3Id;
        }

        public String getMinPrice() {
            return minPrice;
        }

        public void setMinPrice(String minPrice) {
            this.minPrice = minPrice;
        }

        public String getMaxPrice() {
            return maxPrice;
        }

        public void setMaxPrice(String maxPrice) {
            this.maxPrice = maxPrice;
        }

        public String getAvailable() {
            return available;
        }

        public void setAvailable(String available) {
            this.available = available;
        }

        public String getSortType() {
            return sortType;
        }

        public void setSortType(String sortType) {
            this.sortType = sortType;
        }

        public String getPropertyList() {
            return propertyList;
        }

        public void setPropertyList(String propertyList) {
            this.propertyList = propertyList;
        }

        public int getPageNo() {
            return pageNo;
        }

        public void setPageNo(int pageNo) {
            this.pageNo = pageNo;
        }

        public String getActivityId() {
            return activityId;
        }

        public void setActivityId(String activityId) {
            this.activityId = activityId;
        }

        public void setPageSize(String pageSize) {
            this.pageSize = pageSize;
        }

        public String getPageSize() {
            return pageSize;
        }

        @Override
        public String toString() {
            return "DataBean{" +
                    "keyword='" + keyword + '\'' +
                    ", brandId='" + brandId + '\'' +
                    ", class1Id='" + class1Id + '\'' +
                    ", class2Id='" + class2Id + '\'' +
                    ", class3Id='" + class3Id + '\'' +
                    ", minPrice='" + minPrice + '\'' +
                    ", maxPrice='" + maxPrice + '\'' +
                    ", available='" + available + '\'' +
                    ", sortType='" + sortType + '\'' +
                    ", propertyList='" + propertyList + '\'' +
                    ", pageNo='" + pageNo + '\'' +
                    ", activityId='" + activityId + '\'' +
                    ", pageSize='" + pageSize + '\'' +
                    '}';
        }

        public DataBean() {
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(this.keyword);
            dest.writeString(this.brandId);
            dest.writeString(this.class1Id);
            dest.writeString(this.class2Id);
            dest.writeString(this.class3Id);
            dest.writeString(this.minPrice);
            dest.writeString(this.maxPrice);
            dest.writeString(this.available);
            dest.writeString(this.sortType);
            dest.writeString(this.propertyList);
            dest.writeInt(this.pageNo);
            dest.writeString(this.activityId);
            dest.writeString(this.pageSize);
        }

        protected DataBean(Parcel in) {
            this.keyword = in.readString();
            this.brandId = in.readString();
            this.class1Id = in.readString();
            this.class2Id = in.readString();
            this.class3Id = in.readString();
            this.minPrice = in.readString();
            this.maxPrice = in.readString();
            this.available = in.readString();
            this.sortType = in.readString();
            this.propertyList = in.readString();
            this.pageNo = in.readInt();
            this.activityId = in.readString();
            this.pageSize = in.readString();
        }

        public static final Creator<DataBean> CREATOR = new Creator<DataBean>() {
            @Override
            public DataBean createFromParcel(Parcel source) {
                return new DataBean(source);
            }

            @Override
            public DataBean[] newArray(int size) {
                return new DataBean[size];
            }
        };
    }
}

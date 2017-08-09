package com.hhyg.TyClosing.entities.home;

/**
 * Created by user on 2017/8/7.
 */

public class GoodsBean {
        /**
         * shelve_status : 1
         * status : 1
         * tags :
         * image : https://img.mianshui365.com/upload/20170510/19170550242.jpg@300h_300w_95q_1wh
         * full :
         * market_price : 10300
         * cit_brand_id : 104
         * cit_name : 手表
         * stock : 6
         * jiaobiao :
         * best_seller : 0
         * attr_info : 黑色表盘
         * name : 贝伦赛丽JUBILEE系列机械男士腕表 M86903138
         * catename : 男士机械表
         * cit_cate_id : 3
         * active_cut :
         * full_reduce :
         * id : 10927
         * is_active : false
         * active_price :
         * cate_weight : 0
         * del_flag : 0
         * recommend :
         * update_time : 1496737478
         * barcode : 7612330120094
         * cit_amount : 6
         * price : 8000
         * brandname : MIDO美度
         * cate_id : 100
         * activeinfo : {"short_desc":"满200减20","time_begin":1494548460000,"time_end":1530356400000,"type_name":"满额减","price":2000,"active_id":"AI2017051209253042960","is_show":true,"active_type":"3","detailPrmTag":true,"active_name":"满200元减20元 满300元减30元 满400元减40元 满500元减50.00元","active_price":1800,"isprivilege":0}
         */
        private boolean setColor;
        private String cheapPrice;
        private String normalPrice;
        private String image;
        private String market_price;
        private int stock;
        private String jiaobiao;
        private String attr_info;
        private String name;
        private String catename;
        private String barcode;
        private String price;
        private String brandname;
        private ActiveinfoBean activeinfo;

    public void setSetColor(boolean setColor) {
        this.setColor = setColor;
    }

    public boolean isSetColor() {
        return setColor;
    }

    public void setCheapPrice(String cheapPrice) {
        this.cheapPrice = cheapPrice;
    }

    public String getCheapPrice() {
        return cheapPrice;
    }

    public void setNormalPrice(String normalPrice) {
        this.normalPrice = normalPrice;
    }

    public String getNormalPrice() {
        return normalPrice;
    }

    public String getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
        }

        public String getMarket_price() {
            return market_price;
        }

        public void setMarket_price(String market_price) {
            this.market_price = market_price;
        }

        public int getStock() {
            return stock;
        }

        public void setStock(int stock) {
            this.stock = stock;
        }

        public String getJiaobiao() {
            return jiaobiao;
        }

        public void setJiaobiao(String jiaobiao) {
            this.jiaobiao = jiaobiao;
        }

        public String getAttr_info() {
            return attr_info;
        }

        public void setAttr_info(String attr_info) {
            this.attr_info = attr_info;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getCatename() {
            return catename;
        }

        public void setCatename(String catename) {
            this.catename = catename;
        }

        public String getBarcode() {
            return barcode;
        }

        public void setBarcode(String barcode) {
            this.barcode = barcode;
        }

        public String getPrice() {
            return price;
        }

        public void setPrice(String price) {
            this.price = price;
        }

        public String getBrandname() {
            return brandname;
        }

        public void setBrandname(String brandname) {
            this.brandname = brandname;
        }

        public ActiveinfoBean getActiveinfo() {
            return activeinfo;
        }

        public void setActiveinfo(ActiveinfoBean activeinfo) {
            this.activeinfo = activeinfo;
        }

        public static class ActiveinfoBean {
            /**
             * short_desc : 满200减20
             * time_begin : 1494548460000
             * time_end : 1530356400000
             * type_name : 满额减
             * price : 2000
             * active_id : AI2017051209253042960
             * is_show : true
             * active_type : 3
             * detailPrmTag : true
             * active_name : 满200元减20元 满300元减30元 满400元减40元 满500元减50.00元
             * active_price : 1800
             * isprivilege : 0
             */

            private String short_desc;
            private String type_name;
            private String active_type;
            private String active_name;
            private String active_price;
            private int isprivilege;

            public String getShort_desc() {
                return short_desc;
            }

            public void setShort_desc(String short_desc) {
                this.short_desc = short_desc;
            }

            public String getType_name() {
                return type_name;
            }

            public void setType_name(String type_name) {
                this.type_name = type_name;
            }

            public String getActive_type() {
                return active_type;
            }

            public void setActive_type(String active_type) {
                this.active_type = active_type;
            }

            public String getActive_name() {
                return active_name;
            }

            public void setActive_name(String active_name) {
                this.active_name = active_name;
            }

            public String getActive_price() {
                return active_price;
            }

            public void setActive_price(String active_price) {
                this.active_price = active_price;
            }

            public int getIsprivilege() {
                return isprivilege;
            }

            public void setIsprivilege(int isprivilege) {
                this.isprivilege = isprivilege;
            }
        }
    }

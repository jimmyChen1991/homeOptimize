package com.hhyg.TyClosing.entities.home;

import java.util.List;

/**
 * Created by user on 2017/8/2.
 */

public class ContentRes {



    private int errcode;
    private String op;
    private int channel;
    private DataBean data;

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

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {


        private TyPadIndexHotbrandBean ty_pad_index_hotbrand;
        private TyPadIndexXianshitehuiBean ty_pad_index_xianshitehui;
        private List<TyPadIndexSliderBean> ty_pad_index_slider;
        private List<TyPadIndexAdvertisingBean> ty_pad_index_advertising;
        private List<TyPadIndexCateBean> ty_pad_index_cate;
        private List<TyPadIndexGiftsthemeBean> ty_pad_index_giftstheme;
        private List<TyPadIndexRecommendgoodBean> ty_pad_index_recommendgood;

        public TyPadIndexHotbrandBean getTy_pad_index_hotbrand() {
            return ty_pad_index_hotbrand;
        }

        public void setTy_pad_index_hotbrand(TyPadIndexHotbrandBean ty_pad_index_hotbrand) {
            this.ty_pad_index_hotbrand = ty_pad_index_hotbrand;
        }

        public TyPadIndexXianshitehuiBean getTy_pad_index_xianshitehui() {
            return ty_pad_index_xianshitehui;
        }

        public void setTy_pad_index_xianshitehui(TyPadIndexXianshitehuiBean ty_pad_index_xianshitehui) {
            this.ty_pad_index_xianshitehui = ty_pad_index_xianshitehui;
        }

        public List<TyPadIndexSliderBean> getTy_pad_index_slider() {
            return ty_pad_index_slider;
        }

        public void setTy_pad_index_slider(List<TyPadIndexSliderBean> ty_pad_index_slider) {
            this.ty_pad_index_slider = ty_pad_index_slider;
        }

        public List<TyPadIndexAdvertisingBean> getTy_pad_index_advertising() {
            return ty_pad_index_advertising;
        }

        public void setTy_pad_index_advertising(List<TyPadIndexAdvertisingBean> ty_pad_index_advertising) {
            this.ty_pad_index_advertising = ty_pad_index_advertising;
        }

        public List<TyPadIndexCateBean> getTy_pad_index_cate() {
            return ty_pad_index_cate;
        }

        public void setTy_pad_index_cate(List<TyPadIndexCateBean> ty_pad_index_cate) {
            this.ty_pad_index_cate = ty_pad_index_cate;
        }

        public List<TyPadIndexGiftsthemeBean> getTy_pad_index_giftstheme() {
            return ty_pad_index_giftstheme;
        }

        public void setTy_pad_index_giftstheme(List<TyPadIndexGiftsthemeBean> ty_pad_index_giftstheme) {
            this.ty_pad_index_giftstheme = ty_pad_index_giftstheme;
        }

        public List<TyPadIndexRecommendgoodBean> getTy_pad_index_recommendgood() {
            return ty_pad_index_recommendgood;
        }

        public void setTy_pad_index_recommendgood(List<TyPadIndexRecommendgoodBean> ty_pad_index_recommendgood) {
            this.ty_pad_index_recommendgood = ty_pad_index_recommendgood;
        }

        public static class TyPadIndexHotbrandBean {

            private String more_brand_image;
            private List<HotbrandBean> hotbrand;

            public String getMore_brand_image() {
                return more_brand_image;
            }

            public void setMore_brand_image(String more_brand_image) {
                this.more_brand_image = more_brand_image;
            }

            public List<HotbrandBean> getHotbrand() {
                return hotbrand;
            }

            public void setHotbrand(List<HotbrandBean> hotbrand) {
                this.hotbrand = hotbrand;
            }

            public static class HotbrandBean {
                /**
                 * imgurl : http://img.mianshui365.com/upload/2d/f3/82/2df382ddb229541a2a14ec418975de73.jpg
                 * brandid : 80
                 * updatetime : 1500537222
                 * brandname : ESTÉE LAUDER雅诗兰黛
                 */

                private String imgurl;
                private String brandid;
                private String brandname;

                public String getImgurl() {
                    return imgurl;
                }

                public void setImgurl(String imgurl) {
                    this.imgurl = imgurl;
                }

                public String getBrandid() {
                    return brandid;
                }

                public void setBrandid(String brandid) {
                    this.brandid = brandid;
                }

                public String getBrandname() {
                    return brandname;
                }

                public void setBrandname(String brandname) {
                    this.brandname = brandname;
                }
            }
        }

        public static class TyPadIndexXianshitehuiBean {

            private String specialid;
            private List<GoodsBean> goods;

            public String getSpecialid() {
                return specialid;
            }

            public void setSpecialid(String specialid) {
                this.specialid = specialid;
            }

            public List<GoodsBean> getGoods() {
                return goods;
            }

            public void setGoods(List<GoodsBean> goods) {
                this.goods = goods;
            }

            public static class GoodsBean {

                private String id;
                private String cate_id;
                private String cit_cate_id;
                private String cit_brand_id;
                private String name;
                private String barcode;
                private String attr_info;
                private String image;
                private String tags;
                private int stock;
                private String price;
                private String market_price;
                private int status;
                private int shelve_status;
                private String recommend;
                private String brandname;
                private String catename;
                private String active_cut;
                private String active_price;
                private String full;
                private String full_reduce;
                private String cit_name;
                private int cit_amount;
                private boolean is_active;
                private ActiveinfoBean activeinfo;

                public String getId() {
                    return id;
                }

                public void setId(String id) {
                    this.id = id;
                }

                public String getCate_id() {
                    return cate_id;
                }

                public void setCate_id(String cate_id) {
                    this.cate_id = cate_id;
                }

                public String getCit_cate_id() {
                    return cit_cate_id;
                }

                public void setCit_cate_id(String cit_cate_id) {
                    this.cit_cate_id = cit_cate_id;
                }

                public String getCit_brand_id() {
                    return cit_brand_id;
                }

                public void setCit_brand_id(String cit_brand_id) {
                    this.cit_brand_id = cit_brand_id;
                }

                public String getName() {
                    return name;
                }

                public void setName(String name) {
                    this.name = name;
                }

                public String getBarcode() {
                    return barcode;
                }

                public void setBarcode(String barcode) {
                    this.barcode = barcode;
                }

                public String getAttr_info() {
                    return attr_info;
                }

                public void setAttr_info(String attr_info) {
                    this.attr_info = attr_info;
                }

                public String getImage() {
                    return image;
                }

                public void setImage(String image) {
                    this.image = image;
                }

                public String getTags() {
                    return tags;
                }

                public void setTags(String tags) {
                    this.tags = tags;
                }

                public int getStock() {
                    return stock;
                }

                public void setStock(int stock) {
                    this.stock = stock;
                }

                public String getPrice() {
                    return price;
                }

                public void setPrice(String price) {
                    this.price = price;
                }

                public String getMarket_price() {
                    return market_price;
                }

                public void setMarket_price(String market_price) {
                    this.market_price = market_price;
                }

                public int getStatus() {
                    return status;
                }

                public void setStatus(int status) {
                    this.status = status;
                }

                public int getShelve_status() {
                    return shelve_status;
                }

                public void setShelve_status(int shelve_status) {
                    this.shelve_status = shelve_status;
                }

                public String getRecommend() {
                    return recommend;
                }

                public void setRecommend(String recommend) {
                    this.recommend = recommend;
                }

                public String getBrandname() {
                    return brandname;
                }

                public void setBrandname(String brandname) {
                    this.brandname = brandname;
                }

                public String getCatename() {
                    return catename;
                }

                public void setCatename(String catename) {
                    this.catename = catename;
                }

                public String getActive_cut() {
                    return active_cut;
                }

                public void setActive_cut(String active_cut) {
                    this.active_cut = active_cut;
                }

                public String getActive_price() {
                    return active_price;
                }

                public void setActive_price(String active_price) {
                    this.active_price = active_price;
                }

                public String getFull() {
                    return full;
                }

                public void setFull(String full) {
                    this.full = full;
                }

                public String getFull_reduce() {
                    return full_reduce;
                }

                public void setFull_reduce(String full_reduce) {
                    this.full_reduce = full_reduce;
                }

                public String getCit_name() {
                    return cit_name;
                }

                public void setCit_name(String cit_name) {
                    this.cit_name = cit_name;
                }

                public int getCit_amount() {
                    return cit_amount;
                }

                public void setCit_amount(int cit_amount) {
                    this.cit_amount = cit_amount;
                }

                public boolean isIs_active() {
                    return is_active;
                }

                public void setIs_active(boolean is_active) {
                    this.is_active = is_active;
                }

                public ActiveinfoBean getActiveinfo() {
                    return activeinfo;
                }

                public void setActiveinfo(ActiveinfoBean activeinfo) {
                    this.activeinfo = activeinfo;
                }

                public static class ActiveinfoBean {
                    /**
                     * is_show : true
                     * price : 3540
                     * detailPrmTag : true
                     * active_id : AI2017041601481448147
                     * active_name : 健乐士 5折
                     * active_type : 1
                     * active_price : 1770
                     * short_desc : 5折
                     * type_name : 折扣
                     * time_begin : 1492276200000
                     * time_end : 1514735580000
                     * isprivilege : 0
                     */

                    private boolean is_show;
                    private String price;
                    private boolean detailPrmTag;
                    private String active_id;
                    private String active_name;
                    private String active_type;
                    private String active_price;
                    private String short_desc;
                    private String type_name;
                    private int isprivilege;

                    public boolean isIs_show() {
                        return is_show;
                    }

                    public void setIs_show(boolean is_show) {
                        this.is_show = is_show;
                    }

                    public String getPrice() {
                        return price;
                    }

                    public void setPrice(String price) {
                        this.price = price;
                    }

                    public boolean isDetailPrmTag() {
                        return detailPrmTag;
                    }

                    public void setDetailPrmTag(boolean detailPrmTag) {
                        this.detailPrmTag = detailPrmTag;
                    }

                    public String getActive_id() {
                        return active_id;
                    }

                    public void setActive_id(String active_id) {
                        this.active_id = active_id;
                    }

                    public String getActive_name() {
                        return active_name;
                    }

                    public void setActive_name(String active_name) {
                        this.active_name = active_name;
                    }

                    public String getActive_type() {
                        return active_type;
                    }

                    public void setActive_type(String active_type) {
                        this.active_type = active_type;
                    }

                    public String getActive_price() {
                        return active_price;
                    }

                    public void setActive_price(String active_price) {
                        this.active_price = active_price;
                    }

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

                    public int getIsprivilege() {
                        return isprivilege;
                    }

                    public void setIsprivilege(int isprivilege) {
                        this.isprivilege = isprivilege;
                    }
                }
            }
        }

        public static class TyPadIndexSliderBean {
            /**
             * imgurl : http://img.mianshui365.com/upload/7e/61/1a/7e611aa2be220f80255b968c44348344.jpg
             * specialid : 96
             * sort : 1
             * updatetime : 1498833067
             * end_time : 1514649600
             * begin_time : 1474992000
             */

            private String imgurl;
            private String specialid;
            private int sort;

            public String getImgurl() {
                return imgurl;
            }

            public void setImgurl(String imgurl) {
                this.imgurl = imgurl;
            }

            public String getSpecialid() {
                return specialid;
            }

            public void setSpecialid(String specialid) {
                this.specialid = specialid;
            }

            public int getSort() {
                return sort;
            }

            public void setSort(int sort) {
                this.sort = sort;
            }
        }

        public static class TyPadIndexAdvertisingBean {
            /**
             * imgurl : http://img.mianshui365.com/upload/f5/32/1e/f5321eb813e5553246f736154e16aac1.jpg
             * specialid : 100
             * updatetime : 1499832153
             */

            private String imgurl;
            private String specialid;

            public String getImgurl() {
                return imgurl;
            }

            public void setImgurl(String imgurl) {
                this.imgurl = imgurl;
            }

            public String getSpecialid() {
                return specialid;
            }

            public void setSpecialid(String specialid) {
                this.specialid = specialid;
            }
        }

        public static class TyPadIndexCateBean {
            /**
             * name : 护肤
             * imgurl : http://img.mianshui365.com/upload/d2/d0/93/d2d093d721e892e3f87f9c2e37465c4b.jpg
             * catid : 1
             * updatetime : 1500601761
             * level : 1
             */

            private String name;
            private String imgurl;
            private String catid;
            private int level;

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getImgurl() {
                return imgurl;
            }

            public void setImgurl(String imgurl) {
                this.imgurl = imgurl;
            }

            public String getCatid() {
                return catid;
            }

            public void setCatid(String catid) {
                this.catid = catid;
            }

            public int getLevel() {
                return level;
            }

            public void setLevel(int level) {
                this.level = level;
            }
        }

        public static class TyPadIndexGiftsthemeBean {
            /**
             * sort : 1
             * imgurl : http://img.mianshui365.com/upload/02/a5/1b/02a51b9175915430dda36c1179be3e1e.jpg
             * specialid : 103
             * updatetime : 1500609247
             */

            private int sort;
            private String imgurl;
            private String specialid;

            public int getSort() {
                return sort;
            }

            public void setSort(int sort) {
                this.sort = sort;
            }

            public String getImgurl() {
                return imgurl;
            }

            public void setImgurl(String imgurl) {
                this.imgurl = imgurl;
            }

            public String getSpecialid() {
                return specialid;
            }

            public void setSpecialid(String specialid) {
                this.specialid = specialid;
            }
        }

        public static class TyPadIndexRecommendgoodBean {

            private String title;
            private List<GoodsBeanX> goods;

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public List<GoodsBeanX> getGoods() {
                return goods;
            }

            public void setGoods(List<GoodsBeanX> goods) {
                this.goods = goods;
            }

            public static class GoodsBeanX {

                private String id;
                private String cate_id;
                private String cit_cate_id;
                private String cit_brand_id;
                private String name;
                private String barcode;
                private String attr_info;
                private String image;
                private String tags;
                private String stock;
                private String price;
                private String market_price;
                private int status;
                private int shelve_status;
                private String recommend;
                private String brandname;
                private String catename;
                private String active_cut;
                private String active_price;
                private String full;
                private String full_reduce;
                private String cit_name;
                private int cit_amount;
                private boolean is_active;

                public String getId() {
                    return id;
                }

                public void setId(String id) {
                    this.id = id;
                }

                public String getCate_id() {
                    return cate_id;
                }

                public void setCate_id(String cate_id) {
                    this.cate_id = cate_id;
                }

                public String getCit_cate_id() {
                    return cit_cate_id;
                }

                public void setCit_cate_id(String cit_cate_id) {
                    this.cit_cate_id = cit_cate_id;
                }

                public String getCit_brand_id() {
                    return cit_brand_id;
                }

                public void setCit_brand_id(String cit_brand_id) {
                    this.cit_brand_id = cit_brand_id;
                }

                public String getName() {
                    return name;
                }

                public void setName(String name) {
                    this.name = name;
                }

                public String getBarcode() {
                    return barcode;
                }

                public void setBarcode(String barcode) {
                    this.barcode = barcode;
                }

                public String getAttr_info() {
                    return attr_info;
                }

                public void setAttr_info(String attr_info) {
                    this.attr_info = attr_info;
                }

                public String getImage() {
                    return image;
                }

                public void setImage(String image) {
                    this.image = image;
                }

                public String getTags() {
                    return tags;
                }

                public void setTags(String tags) {
                    this.tags = tags;
                }

                public String getStock() {
                    return stock;
                }

                public void setStock(String stock) {
                    this.stock = stock;
                }

                public String getPrice() {
                    return price;
                }

                public void setPrice(String price) {
                    this.price = price;
                }

                public String getMarket_price() {
                    return market_price;
                }

                public void setMarket_price(String market_price) {
                    this.market_price = market_price;
                }

                public int getStatus() {
                    return status;
                }

                public void setStatus(int status) {
                    this.status = status;
                }

                public int getShelve_status() {
                    return shelve_status;
                }

                public void setShelve_status(int shelve_status) {
                    this.shelve_status = shelve_status;
                }

                public String getRecommend() {
                    return recommend;
                }

                public void setRecommend(String recommend) {
                    this.recommend = recommend;
                }

                public String getBrandname() {
                    return brandname;
                }

                public void setBrandname(String brandname) {
                    this.brandname = brandname;
                }

                public String getCatename() {
                    return catename;
                }

                public void setCatename(String catename) {
                    this.catename = catename;
                }

                public String getActive_cut() {
                    return active_cut;
                }

                public void setActive_cut(String active_cut) {
                    this.active_cut = active_cut;
                }

                public String getActive_price() {
                    return active_price;
                }

                public void setActive_price(String active_price) {
                    this.active_price = active_price;
                }

                public String getFull() {
                    return full;
                }

                public void setFull(String full) {
                    this.full = full;
                }

                public String getFull_reduce() {
                    return full_reduce;
                }

                public void setFull_reduce(String full_reduce) {
                    this.full_reduce = full_reduce;
                }

                public String getCit_name() {
                    return cit_name;
                }

                public void setCit_name(String cit_name) {
                    this.cit_name = cit_name;
                }

                public int getCit_amount() {
                    return cit_amount;
                }

                public void setCit_amount(int cit_amount) {
                    this.cit_amount = cit_amount;
                }

                public boolean isIs_active() {
                    return is_active;
                }

                public void setIs_active(boolean is_active) {
                    this.is_active = is_active;
                }
            }
        }
    }
}

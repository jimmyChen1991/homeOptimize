package com.hhyg.TyClosing.entities.home;

import java.util.List;

/**
 * Created by user on 2017/8/2.
 */

public class ContentRes {

    private int errcode;
    private String op;
    private DataBean data;
    private int channel;

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

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public int getChannel() {
        return channel;
    }

    public void setChannel(int channel) {
        this.channel = channel;
    }

    public static class DataBean {

        private TyPadIndexNewXianshitehuiBean ty_pad_index_new_xianshitehui;
        private TyPadIndexNewHotbrandBean ty_pad_index_new_hotbrand;
        private List<TyPadIndexNewSliderBean> ty_pad_index_new_slider;
        private List<TyPadIndexNewGoodstopicBean> ty_pad_index_new_goodstopic;
        private List<TyPadIndexNewRecommendgoodBean> ty_pad_index_new_recommendgood;
        private List<TyPadIndexNewAdvertisingBean> ty_pad_index_new_advertising;
        private List<TyPadIndexNewCateBean> ty_pad_index_new_cate;

        public TyPadIndexNewXianshitehuiBean getTy_pad_index_new_xianshitehui() {
            return ty_pad_index_new_xianshitehui;
        }

        public void setTy_pad_index_new_xianshitehui(TyPadIndexNewXianshitehuiBean ty_pad_index_new_xianshitehui) {
            this.ty_pad_index_new_xianshitehui = ty_pad_index_new_xianshitehui;
        }

        public TyPadIndexNewHotbrandBean getTy_pad_index_new_hotbrand() {
            return ty_pad_index_new_hotbrand;
        }

        public void setTy_pad_index_new_hotbrand(TyPadIndexNewHotbrandBean ty_pad_index_new_hotbrand) {
            this.ty_pad_index_new_hotbrand = ty_pad_index_new_hotbrand;
        }

        public List<TyPadIndexNewSliderBean> getTy_pad_index_new_slider() {
            return ty_pad_index_new_slider;
        }

        public void setTy_pad_index_new_slider(List<TyPadIndexNewSliderBean> ty_pad_index_new_slider) {
            this.ty_pad_index_new_slider = ty_pad_index_new_slider;
        }

        public List<TyPadIndexNewGoodstopicBean> getTy_pad_index_new_goodstopic() {
            return ty_pad_index_new_goodstopic;
        }

        public void setTy_pad_index_new_goodstopic(List<TyPadIndexNewGoodstopicBean> ty_pad_index_new_goodstopic) {
            this.ty_pad_index_new_goodstopic = ty_pad_index_new_goodstopic;
        }

        public List<TyPadIndexNewRecommendgoodBean> getTy_pad_index_new_recommendgood() {
            return ty_pad_index_new_recommendgood;
        }

        public void setTy_pad_index_new_recommendgood(List<TyPadIndexNewRecommendgoodBean> ty_pad_index_new_recommendgood) {
            this.ty_pad_index_new_recommendgood = ty_pad_index_new_recommendgood;
        }

        public List<TyPadIndexNewAdvertisingBean> getTy_pad_index_new_advertising() {
            return ty_pad_index_new_advertising;
        }

        public void setTy_pad_index_new_advertising(List<TyPadIndexNewAdvertisingBean> ty_pad_index_new_advertising) {
            this.ty_pad_index_new_advertising = ty_pad_index_new_advertising;
        }

        public List<TyPadIndexNewCateBean> getTy_pad_index_new_cate() {
            return ty_pad_index_new_cate;
        }

        public void setTy_pad_index_new_cate(List<TyPadIndexNewCateBean> ty_pad_index_new_cate) {
            this.ty_pad_index_new_cate = ty_pad_index_new_cate;
        }

        public static class TyPadIndexNewXianshitehuiBean {


            private String specialid;
            private String imgurl;
            private String color;
            private List<GoodsBean> goods;

            public String getSpecialid() {
                return specialid;
            }

            public void setSpecialid(String specialid) {
                this.specialid = specialid;
            }

            public void setImgurl(String imgurl) {
                this.imgurl = imgurl;
            }

            public String getImgurl() {
                return imgurl;
            }

            public List<GoodsBean> getGoods() {
                return goods;
            }

            public void setGoods(List<GoodsBean> goods) {
                this.goods = goods;
            }

            public void setColor(String color) {
                this.color = color;
            }

            public String getColor() {
                return color;
            }

        }

        public static class TyPadIndexNewHotbrandBean {


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
                 * updatetime : 1501572081
                 * imgurl : http://img.mianshui365.com/upload/66/73/f0/6673f039386e8a3cc46c843fac47a581.png
                 * brandname : Anne Klein II克莱恩
                 * brandid : 88
                 */

                private String imgurl;
                private String brandname;
                private String brandid;
                private String brandname_en;
                private String brandname_cn;

                public void setBrandname_cn(String brandname_cn) {
                    this.brandname_cn = brandname_cn;
                }

                public void setBrandname_en(String brandname_en) {
                    this.brandname_en = brandname_en;
                }

                public String getBrandname_cn() {
                    return brandname_cn;
                }

                public String getBrandname_en() {
                    return brandname_en;
                }

                public String getImgurl() {
                    return imgurl;
                }

                public void setImgurl(String imgurl) {
                    this.imgurl = imgurl;
                }

                public String getBrandname() {
                    return brandname;
                }

                public void setBrandname(String brandname) {
                    this.brandname = brandname;
                }

                public String getBrandid() {
                    return brandid;
                }

                public void setBrandid(String brandid) {
                    this.brandid = brandid;
                }
            }
        }

        public static class TyPadIndexNewSliderBean {
            /**
             * updatetime : 1501571832
             * end_time : 1512057600
             * begin_time : 1474128000
             * imgurl : http://img.mianshui365.com/upload/d6/14/bd/d614bde51bc5bbe6d81419a65f765923.png
             * sort : 1
             * specialid : 70
             */

            private String imgurl;
            private int sort;
            private String specialid;

            public String getImgurl() {
                return imgurl;
            }

            public void setImgurl(String imgurl) {
                this.imgurl = imgurl;
            }

            public int getSort() {
                return sort;
            }

            public void setSort(int sort) {
                this.sort = sort;
            }

            public String getSpecialid() {
                return specialid;
            }

            public void setSpecialid(String specialid) {
                this.specialid = specialid;
            }
        }

        public static class TyPadIndexNewGoodstopicBean {


            private String imgurl;
            private int sort;
            private String color;
            private String specialid;
            private List<GoodsBean> goods;

            public String getImgurl() {
                return imgurl;
            }

            public void setImgurl(String imgurl) {
                this.imgurl = imgurl;
            }

            public int getSort() {
                return sort;
            }

            public void setSort(int sort) {
                this.sort = sort;
            }

            public String getColor() {
                return color;
            }

            public void setColor(String color) {
                this.color = color;
            }

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


        }

        public static class TyPadIndexNewRecommendgoodBean {


            private String title;
            private String cateid;
            private List<GoodsBean> goods;
            private int level;

            public void setLevel(int level) {
                this.level = level;
            }

            public int getLevel() {
                return level;
            }

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public String getCateid() {
                return cateid;
            }

            public void setCateid(String cateid) {
                this.cateid = cateid;
            }

            public List<GoodsBean> getGoods() {
                return goods;
            }

            public void setGoods(List<GoodsBean> goods) {
                this.goods = goods;
            }

        }

        public static class TyPadIndexNewAdvertisingBean {


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

        public static class TyPadIndexNewCateBean {


            private String catid;
            private int level;
            private String name;
            private String imgurl;

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
        }
    }
}
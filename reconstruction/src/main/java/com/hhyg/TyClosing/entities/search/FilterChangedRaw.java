package com.hhyg.TyClosing.entities.search;

import java.util.ArrayList;

/**
 * Created by chenqiyang on 17/6/26.
 */

public class FilterChangedRaw {
    private ArrayList<FilterBean> filterBeens = new ArrayList<>();
    private PriceBean priceBean = new PriceBean();
    private String available;

    public ArrayList<FilterBean> getFilterBeens() {
        return filterBeens;
    }

    public void setFilterBeens(ArrayList<FilterBean> filterBeens) {
        this.filterBeens = filterBeens;
    }

    public PriceBean getPriceBean() {
        return priceBean;
    }

    public void setPriceBean(PriceBean priceBean) {
        this.priceBean = priceBean;
    }

    public String getAvailable() {
        return available;
    }

    public void setAvailable(String available) {
        this.available = available;
    }

    public static class PriceBean{
        private String minPrice;
        private String maxPrice;

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
    }
}

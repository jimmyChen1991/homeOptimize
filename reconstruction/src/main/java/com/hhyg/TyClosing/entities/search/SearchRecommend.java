package com.hhyg.TyClosing.entities.search;

import java.util.List;

/**
 * Created by user on 2017/6/30.
 */

public class SearchRecommend {

    /**
     * recommend : 雅诗兰黛
     * moreneed : ["倩碧","资生堂"]
     */

    private String recommend;
    private List<String> moreneed;

    public String getRecommend() {
        return recommend;
    }

    public void setRecommend(String recommend) {
        this.recommend = recommend;
    }

    public List<String> getMoreneed() {
        return moreneed;
    }

    public void setMoreneed(List<String> moreneed) {
        this.moreneed = moreneed;
    }
}

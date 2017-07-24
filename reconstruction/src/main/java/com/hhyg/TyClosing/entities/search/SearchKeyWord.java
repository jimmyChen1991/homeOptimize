package com.hhyg.TyClosing.entities.search;

import io.realm.RealmObject;
import io.realm.annotations.Ignore;
import io.realm.annotations.PrimaryKey;

/**
 * Created by chenqiyang on 17/6/26.
 */

public class SearchKeyWord extends RealmObject{
    @PrimaryKey
    private String id;
    private String word;
    @Ignore
    private boolean hightlight;
    @Ignore
    private int specialId;
    private String updateTime;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public boolean isHightlight() {
        return hightlight;
    }

    public void setHightlight(boolean hightlight) {
        this.hightlight = hightlight;
    }

    public int getSpecialId() {
        return specialId;
    }

    public void setSpecialId(int specialId) {
        this.specialId = specialId;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }
}

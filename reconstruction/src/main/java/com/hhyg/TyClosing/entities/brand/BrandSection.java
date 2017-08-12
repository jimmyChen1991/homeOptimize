package com.hhyg.TyClosing.entities.brand;

import com.chad.library.adapter.base.entity.SectionEntity;

/**
 * Created by user on 2017/8/11.
 */

public class BrandSection extends SectionEntity<BrandInfo>{

    public BrandSection(boolean isHeader, String header) {
        super(isHeader, header);
    }

    public BrandSection(BrandInfo brandInfo) {
        super(brandInfo);
    }

}

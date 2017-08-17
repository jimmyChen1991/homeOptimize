package com.hhyg.TyClosing.ui.adapter.brand;

import android.text.TextUtils;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseSectionQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.hhyg.TyClosing.R;
import com.hhyg.TyClosing.entities.brand.BrandInfo;
import com.hhyg.TyClosing.entities.brand.BrandSection;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by user on 2017/8/11.
 */

public class BrandAdapter extends BaseSectionQuickAdapter<BrandSection,BaseViewHolder> {


    /**
     * Same as QuickAdapter#QuickAdapter(Context,int) but with
     * some initialization data.
     *
     * @param layoutResId      The layout resource id of each item.
     * @param sectionHeadResId The section head layout id for each item
     * @param data             A new list is created out of this one to avoid mutable list
     */
    public BrandAdapter(int layoutResId, int sectionHeadResId, List<BrandSection> data) {
        super(layoutResId, sectionHeadResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, BrandSection item) {
        BrandInfo info = item.t;
        helper.setText(R.id.name_cn,info.getName_cn())
                .setText(R.id.name_en,info.getName_en());
        if(!TextUtils.isEmpty(info.getUrl())){
            Picasso.with(helper.itemView.getContext()).load(info.getUrl()).into((ImageView) helper.getView(R.id.img));
        }
    }

    @Override
    protected void convertHead(BaseViewHolder helper, BrandSection item) {
        helper.setText(R.id.head_letter,item.header);
    }
}

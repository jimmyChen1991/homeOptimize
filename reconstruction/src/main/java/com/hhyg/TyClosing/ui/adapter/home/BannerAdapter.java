package com.hhyg.TyClosing.ui.adapter.home;

import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.hhyg.TyClosing.R;
import com.hhyg.TyClosing.entities.home.GoodsBean;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by user on 2017/8/7.
 */

public class BannerAdapter extends BaseQuickAdapter<GoodsBean,BaseViewHolder>{

    public BannerAdapter(@LayoutRes int layoutResId, @Nullable List<GoodsBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, GoodsBean item) {
        helper.setText(R.id.brand,item.getBrandname())
                .setText(R.id.attr_info,item.getAttr_info());
        Picasso.with(helper.itemView.getContext()).load(item.getImage()).into((ImageView) helper.getView(R.id.img));
    }

    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Log.d(TAG, "come in");
        return super.onCreateViewHolder(parent, viewType);
    }
}

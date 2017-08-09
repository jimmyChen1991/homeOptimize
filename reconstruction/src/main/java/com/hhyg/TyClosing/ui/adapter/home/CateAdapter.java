package com.hhyg.TyClosing.ui.adapter.home;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseViewHolder;
import com.hhyg.TyClosing.R;
import com.hhyg.TyClosing.entities.home.ContentRes;
import com.hhyg.TyClosing.entities.search.SearchGoodsParam;
import com.hhyg.TyClosing.entities.search.SearchType;
import com.hhyg.TyClosing.ui.CategoryActivity;
import com.hhyg.TyClosing.ui.SearchGoodActivity;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by user on 2017/8/8.
 */

public class CateAdapter extends RecyclerView.Adapter {

    private LayoutInflater inflater;
    private ArrayList<ContentRes.DataBean.TyPadIndexNewCateBean> mData;

    public CateAdapter(Context context) {
        inflater = LayoutInflater.from(context);
    }

    public ArrayList<ContentRes.DataBean.TyPadIndexNewCateBean> getmData() {
        return mData;
    }

    public void setmData(ArrayList<ContentRes.DataBean.TyPadIndexNewCateBean> mData) {
        this.mData = mData;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MainViewHolder(inflater.inflate(R.layout.home_cateitem,parent,false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        MainViewHolder viewHolder = (MainViewHolder) holder;
        if(position == getItemCount() - 1){
            viewHolder.img.setBackgroundResource(R.drawable.cate_seemore);
            viewHolder.cateName.setText("更多");
            viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent it = new Intent(v.getContext(), CategoryActivity.class);
                    v.getContext().startActivity(it);
                }
            });
        }else{
            final ContentRes.DataBean.TyPadIndexNewCateBean bean = mData.get(position);
            Picasso.with(holder.itemView.getContext()).load(bean.getImgurl()).into(viewHolder.img);
            viewHolder.cateName.setText(bean.getName());
            viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent it = new Intent();
                    it.setClass(v.getContext(), SearchGoodActivity.class);
                    SearchGoodsParam.DataBean paramBean = new SearchGoodsParam.DataBean();
                    if(bean.getLevel() == 1){
                        paramBean.setClass1Id(bean.getCatid());
                    }else if(bean.getLevel() == 2){
                        paramBean.setClass2Id(bean.getCatid());
                    }else{
                        paramBean.setClass3Id(bean.getCatid());
                    }
                    it.putExtra(v.getContext().getString(R.string.search_type), SearchType.CATE.ordinal());
                    it.putExtra(v.getContext().getString(R.string.search_token), paramBean);
                    it.putExtra(v.getContext().getString(R.string.search_content),bean.getName());
                    v.getContext().startActivity(it);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return mData.size() + 1;
    }

    static class MainViewHolder extends BaseViewHolder{

        @BindView(R.id.img)
        ImageView img;

        @BindView(R.id.cate_name)
        TextView cateName;
        public MainViewHolder(View view) {
            super(view);
            ButterKnife.bind(this,view);
        }


    }
}

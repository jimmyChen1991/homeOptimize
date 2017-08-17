package com.hhyg.TyClosing.ui.adapter.home;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.hhyg.TyClosing.R;
import com.hhyg.TyClosing.entities.home.ContentRes;
import com.hhyg.TyClosing.entities.search.SearchGoodsParam;
import com.hhyg.TyClosing.entities.search.SearchType;
import com.hhyg.TyClosing.ui.BrandActivity;
import com.hhyg.TyClosing.ui.SearchGoodActivity;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by user on 2017/8/8.
 */

public class BrandBannerAdapter extends RecyclerView.Adapter {

    private int NORMAL_TYPE = 0x0001;
    private int LAST_ITEM_TYPE = 0x0002;
    private LayoutInflater inflater;
    private ArrayList<ContentRes.DataBean.TyPadIndexNewHotbrandBean.HotbrandBean> mData;

    public BrandBannerAdapter(Context context) {
        inflater = LayoutInflater.from(context);
    }

    public ArrayList<ContentRes.DataBean.TyPadIndexNewHotbrandBean.HotbrandBean> getmData() {
        return mData;
    }

    public void setmData(ArrayList<ContentRes.DataBean.TyPadIndexNewHotbrandBean.HotbrandBean> mData) {
        this.mData = mData;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder viewHolder;
        if(viewType == NORMAL_TYPE){
            viewHolder = new BrandBannerAdapter.MainViewHolder(inflater.inflate(R.layout.home_brandbanner_item,parent,false));
        }else{
            viewHolder = new BrandBannerAdapter.MoreViewHolder(inflater.inflate(R.layout.home_smallitem_last,parent,false));
        }
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if(holder instanceof BrandBannerAdapter.MainViewHolder){
            final ContentRes.DataBean.TyPadIndexNewHotbrandBean.HotbrandBean bean = mData.get(position);
            ((MainViewHolder) holder).englishName.setText(bean.getBrandname_en());
            ((MainViewHolder) holder).chineseName.setText(bean.getBrandname_cn());
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(v.getContext(), SearchGoodActivity.class);
                    SearchGoodsParam.DataBean sbean = new SearchGoodsParam.DataBean();
                    sbean.setBrandId(bean.getBrandid());
                    intent.putExtra(v.getContext().getString(R.string.search_token),sbean);
                    intent.putExtra(v.getContext().getString(R.string.search_content),bean.getBrandname());
                    intent.putExtra(v.getContext().getString(R.string.search_type), SearchType.BRAND.ordinal());
                    v.getContext().startActivity(intent);
                }
            });
            if(!TextUtils.isEmpty(bean.getImgurl())){
                Picasso.with(holder.itemView.getContext()).load(bean.getImgurl()).into(((MainViewHolder) holder).img);
            }
        }else if(holder instanceof BrandBannerAdapter.MoreViewHolder){
            ((MoreViewHolder) holder).img.setBackgroundResource(R.drawable.normal_seemore);
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(v.getContext(), BrandActivity.class);
                    v.getContext().startActivity(intent);
                }
            });
        }

    }

    @Override
    public int getItemCount() {
        return mData.size() + 1;
    }

    @Override
    public int getItemViewType(int position) {
        int type = 0;
        if(position != getItemCount() - 1){
            type = NORMAL_TYPE;
        }else {
            type = LAST_ITEM_TYPE;
        }
        return type;
    }

    static class MainViewHolder extends RecyclerView.ViewHolder{

        @BindView(R.id.img)
        ImageView img;
        @BindView(R.id.english_name)
        TextView englishName;
        @BindView(R.id.chinese_name)
        TextView chineseName;
        public MainViewHolder(View view) {
            super(view);
            ButterKnife.bind(this,view);
        }
    }

    static class MoreViewHolder extends RecyclerView.ViewHolder{

        @BindView(R.id.img)
        ImageView img;

        public MoreViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
}

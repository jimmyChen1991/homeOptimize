package com.hhyg.TyClosing.ui.adapter.home;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.hhyg.TyClosing.R;
import com.hhyg.TyClosing.entities.home.ContentRes;
import com.hhyg.TyClosing.entities.home.GoodsBean;
import com.hhyg.TyClosing.ui.GoodsInfoActivity;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by user on 2017/8/8.
 */

public class GoodsAdapter extends RecyclerView.Adapter{

    private LayoutInflater inflater;
    private int HEAD_TYPE = 0x0001;
    private int ITEM_TYPE = 0x0002;
    private ArrayList<ContentRes.DataBean.TyPadIndexNewRecommendgoodBean> mData;

    public ArrayList<ContentRes.DataBean.TyPadIndexNewRecommendgoodBean> getmData() {
        return mData;
    }

    public void setmData(ArrayList<ContentRes.DataBean.TyPadIndexNewRecommendgoodBean> mData) {
        this.mData = mData;
    }

    private int[] getHeadsPosition(ArrayList<ContentRes.DataBean.TyPadIndexNewRecommendgoodBean> mData) {
        int size = mData.size();
        int heads[] = new int[size];
        int count = 0;
        for (int index = 0; index < size ; index ++){
            ContentRes.DataBean.TyPadIndexNewRecommendgoodBean bean = mData.get(index);
            count += 1;
            if(bean.getGoods() != null){
                count += bean.getGoods().size();
            }
            if(index > 0){
                heads[index] = count + 1;
            }
        }
        return heads;
    }

    public GoodsAdapter(Context context) {
        inflater = LayoutInflater.from(context);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder holder;
        if(viewType == HEAD_TYPE){
            holder = new HeadViewHolder(inflater.inflate(R.layout.home_goods_head,parent,false));
        }else{
            holder = new MainViewHolder(inflater.inflate(R.layout.home_goods_item,parent,false));
        }
        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        if(holder instanceof  HeadViewHolder){
            int heads[] = getHeadsPosition(mData);
            for (int index = 0; index < heads.length ; index ++){
                if(position == heads[index]){
                    final ContentRes.DataBean.TyPadIndexNewRecommendgoodBean bean = mData.get(index);
                    ((HeadViewHolder) holder).cateName.setText("---" + bean.getTitle() + "---");
                    ((HeadViewHolder) holder).seeMore.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                        }
                    });
                    break;
                }
            }

        }else if (holder instanceof MainViewHolder){
            int heads[] = getHeadsPosition(mData);
            int count = 0;
            for (int index = 0; index < heads.length ; index ++){
                final ContentRes.DataBean.TyPadIndexNewRecommendgoodBean recommendgoodBean = mData.get(index);
                count += recommendgoodBean.getGoods().size() + 1;
                if(position == heads[index]){
                    int realPosition = position - count;
                    final GoodsBean bean = recommendgoodBean.getGoods().get(realPosition);
                    ((BannerAdapter.MainViewHolder) holder).brand.setText(bean.getBrandname());
                    ((BannerAdapter.MainViewHolder) holder).attr.setText(bean.getAttr_info());
                    if (bean.isSetColor()){
                        ((BannerAdapter.MainViewHolder) holder).cheap_price.setText(Html.fromHtml(bean.getCheapPrice()));
                    }else{
                        ((BannerAdapter.MainViewHolder) holder).cheap_price.setText(bean.getCheapPrice());
                    }
                    ((BannerAdapter.MainViewHolder) holder).price.setText(bean.getNormalPrice());
                    holder.itemView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent it = new Intent(v.getContext(), GoodsInfoActivity.class);
                            it.putExtra("barcode",bean.getBarcode());
                            v.getContext().startActivity(it);
                        }
                    });
                    Picasso.with(holder.itemView.getContext()).load(bean.getImage()).into(((BannerAdapter.MainViewHolder) holder).img);
                    break;
                }
            }

        }

    }

    @Override
    public int getItemCount() {
        int count = 0;
        if(mData != null){
            count += mData.size();
            for (ContentRes.DataBean.TyPadIndexNewRecommendgoodBean bean : mData){
                if(bean.getGoods() != null){
                    count += bean.getGoods().size();
                }
            }
        }
        return count;
    }

    @Override
    public int getItemViewType(int position) {
        int type = ITEM_TYPE;
        if(mData != null){
            for (int head : getHeadsPosition(mData)) {
                if (position == head) {
                    type = HEAD_TYPE;
                }
            }
        }
        return type;

    }


    static class MainViewHolder extends RecyclerView.ViewHolder{

        @BindView(R.id.img)
        ImageView img;
        @BindView(R.id.brand)
        TextView brand;
        @BindView(R.id.attr_info)
        TextView attr;
        @BindView(R.id.cheap_price)
        TextView cheapPrice;
        @BindView(R.id.price)
        TextView price;

        public MainViewHolder(View itemView) {
            super(itemView);
        }
    }

    static class HeadViewHolder extends RecyclerView.ViewHolder{

        @BindView(R.id.cate_title)
        TextView cateName;
        @BindView(R.id.seemore)
        TextView seeMore;

        public HeadViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }

    }

}

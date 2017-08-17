package com.hhyg.TyClosing.ui.adapter.home;

import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.hhyg.TyClosing.R;
import com.hhyg.TyClosing.entities.home.ContentRes;
import com.hhyg.TyClosing.entities.home.GoodsBean;
import com.hhyg.TyClosing.entities.search.SearchGoodsParam;
import com.hhyg.TyClosing.entities.search.SearchType;
import com.hhyg.TyClosing.ui.GoodsInfoActivity;
import com.hhyg.TyClosing.ui.SearchGoodActivity;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by user on 2017/8/8.
 */

public class GoodsAdapter extends RecyclerView.Adapter{

    private LayoutInflater inflater;
    private int HEAD_TYPE = 0x0001;
    private int ITEM_TYPE = 0x0002;

    private ContentRes.DataBean.TyPadIndexNewRecommendgoodBean data;

    public ContentRes.DataBean.TyPadIndexNewRecommendgoodBean getData() {
        return data;
    }

    public void setData(ContentRes.DataBean.TyPadIndexNewRecommendgoodBean data) {
        this.data = data;
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
            ((HeadViewHolder) holder).cateName.setText(data.getTitle());
            ((HeadViewHolder) holder).seeMore.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent it = new Intent();
                    it.setClass(v.getContext(), SearchGoodActivity.class);
                    SearchGoodsParam.DataBean paramBean = new SearchGoodsParam.DataBean();
                    if(data.getLevel() == 1){
                        paramBean.setClass1Id(data.getCateid());
                    }else if(data.getLevel() == 2){
                        paramBean.setClass2Id(data.getCateid());
                    }else{
                        paramBean.setClass3Id(data.getCateid());
                    }
                    it.putExtra(v.getContext().getString(R.string.search_type), SearchType.CATE.ordinal());
                    it.putExtra(v.getContext().getString(R.string.search_token), paramBean);
                    it.putExtra(v.getContext().getString(R.string.search_content),data.getTitle());
                    v.getContext().startActivity(it);
                }
            });
        }else if (holder instanceof MainViewHolder){
            final GoodsBean bean = data.getGoods().get(position - 1);
            ((MainViewHolder) holder).brand.setText(bean.getBrandname());
            ((MainViewHolder) holder).attr.setText(bean.getName());
            if(!TextUtils.isEmpty(bean.getJiaobiao())){
                ((MainViewHolder) holder).jiaobiao.setVisibility(View.VISIBLE);
                ((MainViewHolder) holder).jiaobiao.setText(bean.getJiaobiao());
            }else{
                ((MainViewHolder) holder).jiaobiao.setVisibility(View.GONE);
            }
            if(bean.getActiveinfo() != null && !TextUtils.isEmpty(bean.getActiveinfo().getShort_desc())){
                ((MainViewHolder) holder).promotion.setVisibility(View.VISIBLE);
                ((MainViewHolder) holder).promotion.setText(bean.getActiveinfo().getShort_desc());
            }else{
                ((MainViewHolder) holder).promotion.setVisibility(View.GONE);
            }
            if (bean.isSetColor()){
                ((MainViewHolder) holder).cheapPrice.setText(Html.fromHtml(bean.getCheapPrice()));
            }else{
                ((MainViewHolder) holder).cheapPrice.setText(bean.getCheapPrice());
            }
            ((MainViewHolder) holder).price.setText(bean.getNormalPrice());
            ((MainViewHolder) holder).price.setPaintFlags(Paint.STRIKE_THRU_TEXT_FLAG);
            ((MainViewHolder) holder).price.getPaint().setAntiAlias(true);
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent it = new Intent(v.getContext(), GoodsInfoActivity.class);
                    it.putExtra("barcode",bean.getBarcode());
                    v.getContext().startActivity(it);
                }
            });
            if(!TextUtils.isEmpty(bean.getImage())){
                Picasso.with(holder.itemView.getContext()).load(bean.getImage()).into(((MainViewHolder) holder).img);
            }
        }

    }

    @Override
    public int getItemCount() {
        int count = 1;
        if(data != null && data.getGoods() != null){
            count += data.getGoods().size();
        }
        return count;
    }

    @Override
    public int getItemViewType(int position) {
        int type = ITEM_TYPE;
        if(position == 0){
            type = HEAD_TYPE;
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
        @BindView(R.id.jiaobiao)
        TextView jiaobiao;
        @BindView(R.id.promotion)
        TextView promotion;

        public MainViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
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

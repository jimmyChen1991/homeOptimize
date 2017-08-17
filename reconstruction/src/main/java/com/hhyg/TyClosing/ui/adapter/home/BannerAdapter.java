package com.hhyg.TyClosing.ui.adapter.home;

import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.hhyg.TyClosing.R;
import com.hhyg.TyClosing.entities.home.GoodsBean;
import com.hhyg.TyClosing.ui.CategoryActivity;
import com.hhyg.TyClosing.ui.GoodsInfoActivity;
import com.hhyg.TyClosing.ui.SpecialActivity;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by user on 2017/8/7.
 */

public class BannerAdapter extends RecyclerView.Adapter{
    private String specialId;
    private int NORMAL_TYPE = 0x0001;
    private int LAST_ITEM_TYPE = 0x0002;
    private LayoutInflater inflater;
    private ArrayList<GoodsBean> mData;

    public void setSpecialId(String specialId) {
        this.specialId = specialId;
    }

    public String getSpecialId() {
        return specialId;
    }

    public BannerAdapter(Context context) {
        inflater = LayoutInflater.from(context);
    }

    public ArrayList<GoodsBean> getmData() {
        return mData;
    }

    public void setmData(ArrayList<GoodsBean> mData) {
        this.mData = mData;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder viewHolder;
        if(viewType == NORMAL_TYPE){
            viewHolder = new MainViewHolder(inflater.inflate(R.layout.home_smallitem,parent,false));
        }else{
            viewHolder = new MoreViewHolder(inflater.inflate(R.layout.home_smallitem_last,parent,false));
        }
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        if(holder instanceof MainViewHolder){
            final GoodsBean bean = mData.get(position);
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
                ((MainViewHolder) holder).cheap_price.setText(Html.fromHtml(bean.getCheapPrice()));
            }else{
                ((MainViewHolder) holder).cheap_price.setText(bean.getCheapPrice());
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
        }else if(holder instanceof MoreViewHolder){
            ((MoreViewHolder) holder).img.setBackgroundResource(R.drawable.normal_seemore);
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent it = new Intent(v.getContext(), SpecialActivity.class);
                    it.putExtra("specialid", specialId);
                    v.getContext().startActivity(it);
                }
            });
        }

    }

    @Override
    public int getItemCount() {
        int count = 1;
        if(mData != null){
            count = mData.size() + 1;
        }
        return count;
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
        @BindView(R.id.brand)
        TextView brand;
        @BindView(R.id.attr_info)
        TextView attr;
        @BindView(R.id.cheap_price)
        TextView cheap_price;
        @BindView(R.id.price)
        TextView price;
        @BindView(R.id.jiaobiao)
        TextView jiaobiao;
        @BindView(R.id.promotion)
        TextView promotion;
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

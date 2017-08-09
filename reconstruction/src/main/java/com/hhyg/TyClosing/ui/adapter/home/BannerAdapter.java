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
import com.hhyg.TyClosing.entities.home.GoodsBean;
import com.hhyg.TyClosing.ui.CategoryActivity;
import com.hhyg.TyClosing.ui.GoodsInfoActivity;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by user on 2017/8/7.
 */

public class BannerAdapter extends RecyclerView.Adapter{

    private int NORMAL_TYPE = 0x0001;
    private int LAST_ITEM_TYPE = 0x0002;
    private LayoutInflater inflater;
    private ArrayList<GoodsBean> mData;

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
            ((MainViewHolder) holder).attr.setText(bean.getAttr_info());
            if (bean.isSetColor()){
                ((MainViewHolder) holder).cheap_price.setText(Html.fromHtml(bean.getCheapPrice()));
            }else{
                ((MainViewHolder) holder).cheap_price.setText(bean.getCheapPrice());
            }
            ((MainViewHolder) holder).price.setText(bean.getNormalPrice());
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent it = new Intent(v.getContext(), GoodsInfoActivity.class);
                    it.putExtra("barcode",bean.getBarcode());
                    v.getContext().startActivity(it);
                }
            });
            Picasso.with(holder.itemView.getContext()).load(bean.getImage()).into(((MainViewHolder) holder).img);
        }else if(holder instanceof MoreViewHolder){
            ((MoreViewHolder) holder).img.setBackgroundResource(R.drawable.normal_seemore);
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent it = new Intent(v.getContext(), CategoryActivity.class);
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

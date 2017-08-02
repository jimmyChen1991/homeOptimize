package com.hhyg.TyClosing.fragment;

import java.util.ArrayList;

import com.hhyg.TyClosing.R;
import com.hhyg.TyClosing.allShop.adapter.AllShopBaseAdapter;
import com.hhyg.TyClosing.allShop.adapter.OnItemClickListener;
import com.hhyg.TyClosing.allShop.info.CateInfo;
import com.hhyg.TyClosing.entities.search.SearchGoodsParam;
import com.hhyg.TyClosing.entities.search.SearchType;
import com.hhyg.TyClosing.global.ImageHelper;
import com.hhyg.TyClosing.ui.SearchGoodActivity;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.imageaware.ImageAware;
import com.nostra13.universalimageloader.core.imageaware.ImageViewAware;
import com.squareup.picasso.Picasso;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

public class CategoryHotFragment extends Fragment{
	private GridView mGridView;
	private HotCategoryAdapter mHotCategoryAdapter;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.cathotimg_frag, container,false);
		findView(view);
		init();
		return view;
	}
	public void showHotcateInfo(ArrayList<CateInfo> fristInfos){
		mHotCategoryAdapter.addItem(fristInfos);
	}
	private void findView(View root) {
		mGridView = (GridView) root.findViewById(R.id.cateimggrid);
	}
	private void init(){
		mHotCategoryAdapter = new HotCategoryAdapter(getActivity());
		mHotCategoryAdapter.setOnItemClickLister(new OnItemClickListener<CateInfo>(){
			@Override
			public void onClick(CateInfo item) {
				jumpToSearchGoodListActivity(item);
			}});
		mGridView.setAdapter(mHotCategoryAdapter);
	}
	private void jumpToSearchGoodListActivity(CateInfo item){
		Intent it = new Intent();
		it.setClass(getActivity(), SearchGoodActivity.class);
		SearchGoodsParam.DataBean bean = new SearchGoodsParam.DataBean();
		if(item.cateLevel == 1){
			bean.setClass1Id(item.cateId);
		}else if(item.cateLevel == 2){
			bean.setClass2Id(item.cateId);
		}else{
			bean.setClass3Id(item.cateId);
		}
		it.putExtra(getString(R.string.search_token), bean);
		it.putExtra(getString(R.string.search_content),item.name);
		it.putExtra(getString(R.string.search_type), SearchType.CATE.ordinal());
		startActivity(it);
	}
	class HotCategoryAdapter extends AllShopBaseAdapter<CateInfo,HotCategoryAdapter.ViewHolder>{
		public HotCategoryAdapter(Context context) {
			super(context);
		}
		class ViewHolder{
			public ImageView hotCateImgItem;
			public TextView hotCateName;
		}
		@Override
		protected View getConvertView() {
			View view  = inflateItemView(R.layout.catehot_item);
			return view;
		}
		@Override
		protected ViewHolder getViewInstance(View convertView, ViewHolder viewHolder) {
			viewHolder = new ViewHolder();
			viewHolder.hotCateImgItem = (ImageView) convertView.findViewById(R.id.cateitemimg);
			viewHolder.hotCateName = (TextView) convertView.findViewById(R.id.cateitemname);
			return viewHolder;
		}

		@Override
		protected void bindDataToItemView(ViewHolder viewHolder, CateInfo item) {
			viewHolder.hotCateName.setText(item.name);
//			ImageAware imageAware = new ImageViewAware(viewHolder.hotCateImgItem, false);
//			ImageLoader.getInstance().displayImage(item.netUri, imageAware, ImageHelper.initCatPathOption());
			Picasso.with(getActivity()).load(item.netUri).into(viewHolder.hotCateImgItem);
		}
		
	}
	
}

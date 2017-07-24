package com.hhyg.TyClosing.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hhyg.TyClosing.R;
import com.hhyg.TyClosing.allShop.adapter.AllShopBrandAdapter;
import com.hhyg.TyClosing.allShop.adapter.OnItemClickListener;
import com.hhyg.TyClosing.allShop.info.BrandImgInfo;
import com.hhyg.TyClosing.entities.search.SearchGoodsParam;
import com.hhyg.TyClosing.entities.search.SearchType;
import com.hhyg.TyClosing.ui.SearchGoodActivity;
import com.hhyg.TyClosing.ui.view.InSideGridView;

import java.util.ArrayList;
public class AllShopBrandFragment extends AllShopBaseFragment{
	private ArrayList<BrandImgInfo> mBrandList;
	private InSideGridView mGridView;
	private AllShopBrandAdapter mAdapter;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.allshop_brand_frag, container);
		findView(view);
		init();
		return view;
	}
	private void findView(View root){
		mGridView = (InSideGridView) root.findViewById(R.id.brandgrid);
	}
	private void init(){
		mBrandList = new ArrayList<BrandImgInfo>();
		mAdapter = new AllShopBrandAdapter(getActivity());
		mAdapter.setOnItemClickLister(new OnItemClickListener<BrandImgInfo>(){
			@Override
			public void onClick(BrandImgInfo item) {
				jumpToSearchActivity(item);
			}});
		mGridView.setAdapter(mAdapter);
	}
	@Override
	public void setLastestContent() {
		mBrandList = mAllShopInfoMgr.getAllShopInfo().brandInfoList;
		mAdapter.clear();
		mAdapter.addItem(mBrandList);
		mAdapter.notifyDataSetChanged();
	}
	private void jumpToSearchActivity(BrandImgInfo item){
		SearchGoodsParam.DataBean bean = new SearchGoodsParam.DataBean();
		bean.setBrandId(item.id);
		Intent intent = new Intent();
		intent.setClass(getActivity(), SearchGoodActivity.class);
		intent.putExtra(getString(R.string.search_content),item.name);
		intent.putExtra(getString(R.string.search_token),bean);
		intent.putExtra(getString(R.string.search_type), SearchType.BRAND.ordinal());
		startActivity(intent);
	}
}

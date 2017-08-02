package com.hhyg.TyClosing.fragment;

import java.util.ArrayList;

import com.hhyg.TyClosing.R;
import com.hhyg.TyClosing.allShop.info.CategoryInfo;
import com.hhyg.TyClosing.entities.search.SearchGoodsParam;
import com.hhyg.TyClosing.entities.search.SearchType;
import com.hhyg.TyClosing.mgr.UserTrackMgr;
import com.hhyg.TyClosing.ui.SearchGoodActivity;
import com.nostra13.universalimageloader.cache.disc.impl.BrandFileGetter;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;
import com.nostra13.universalimageloader.core.display.SimpleBitmapDisplayer;
import com.nostra13.universalimageloader.core.imageaware.ImageAware;
import com.nostra13.universalimageloader.core.imageaware.ImageViewAware;
import com.squareup.picasso.Picasso;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

public class AllShopCateFragment extends AllShopBaseFragment implements View.OnClickListener{
	private ArrayList<CategoryInfo> mCateInfoList;
	private ArrayList<ImageView> mImageViewList;
	private DisplayImageOptions options;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view =inflater.inflate(R.layout.allshop_cate_frag, container);
		mImageViewList = new ArrayList<>();
		findView(view);
		return view;
	}
	private void findView(View root) {
		ImageView cateOne = (ImageView) root.findViewById(R.id.cat1);
		ImageView cateTwo = (ImageView) root.findViewById(R.id.cat2);
		ImageView cateThree = (ImageView) root.findViewById(R.id.cat3);
		ImageView cateFour = (ImageView) root.findViewById(R.id.cat4);
		ImageView cateFive = (ImageView) root.findViewById(R.id.cat5);
		ImageView cateSix = (ImageView) root.findViewById(R.id.cat6);
		mImageViewList.add(cateOne);
		mImageViewList.add(cateTwo);
		mImageViewList.add(cateThree);
		mImageViewList.add(cateFour);
		mImageViewList.add(cateFive);
		mImageViewList.add(cateSix);
	}
	@Override
	public void setLastestContent() {
//		options = new DisplayImageOptions.Builder()
//		        .resetViewBeforeLoading(false)  // default
//		        .delayBeforeLoading(1000)
//		        .diskCachePath(new BrandFileGetter())
//		        .cacheInMemory(true) // default
//		        .cacheOnDisk(true) // default
//		        .considerExifParams(false) // default
//		        .imageScaleType(ImageScaleType.IN_SAMPLE_INT) // default
//		        .bitmapConfig(Bitmap.Config.RGB_565)
//		        .displayer(new SimpleBitmapDisplayer()) // default
//		        .handler(new Handler())
//		        .displayer(new RoundedBitmapDisplayer(20))// default
//		        .build();
		mCateInfoList = mAllShopInfoMgr.getAllShopInfo().catInfoList;
		for(int idx = 0;idx<mCateInfoList.size();idx++){
			ImageView view = mImageViewList.get(idx);
			view.setTag(mCateInfoList.get(idx));
			view.setOnClickListener(this);
			Picasso.with(getActivity()).load(mCateInfoList.get(idx).netUri).into(view);
//			ImageAware imageAware = new ImageViewAware(view, false);
//			ImageLoader.getInstance().displayImage(mCateInfoList.get(idx).netUri, imageAware, options);
		}
	}
	@Override
	public void onClick(View v) {
		UserTrackMgr.getInstance().clear();
		//UserTrackMgr.getInstance().enter("AllShopCateFragment");
		CategoryInfo item = (CategoryInfo) v.getTag();
        UserTrackMgr.getInstance().onEvent("cataloguetolist", item.cateName);
		jumpToSearchActivity((CategoryInfo) v.getTag());
	}
	private void jumpToSearchActivity(CategoryInfo item) {
		Intent it = new Intent();
		SearchGoodsParam.DataBean bean = new SearchGoodsParam.DataBean();
		if(item.level == 1){
			bean.setClass1Id(item.id);
		}else if(item.level ==2){
			bean.setClass2Id(item.id);
		}else if(item.level == 3){
			bean.setClass3Id(item.id);
		}
		it.putExtra(getResources().getString(R.string.search_token),bean);
		it.putExtra(getString(R.string.search_content),item.cateName);
		it.putExtra(getString(R.string.search_type), SearchType.CATE.ordinal());
		it.setClass(getActivity(), SearchGoodActivity.class);
		startActivity(it);
	}
}

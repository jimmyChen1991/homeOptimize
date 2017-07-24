package com.hhyg.TyClosing.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.hhyg.TyClosing.R;
import com.hhyg.TyClosing.allShop.info.SpecialInfo;
import com.hhyg.TyClosing.global.ImageHelper;
import com.hhyg.TyClosing.ui.SpecialActivity;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.imageaware.ImageAware;
import com.nostra13.universalimageloader.core.imageaware.ImageViewAware;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.Transformer;
import com.youth.banner.listener.OnBannerListener;

import java.util.ArrayList;

public class AllShopSpecialFragment extends AllShopBaseFragment implements View.OnClickListener {

	private ArrayList<SpecialInfo> mSliderList;
	private ArrayList<SpecialInfo> mAdList;
	private ImageView AdOne;
	private ImageView AdTwo;
	private Banner banner;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.allshop_special_frag, container);
		findView(view);
		banner.setBannerStyle(BannerConfig.CIRCLE_INDICATOR);
		banner.setImageLoader(new BannerImgLoader());
		banner.setDelayTime(3000);
		banner.setIndicatorGravity(BannerConfig.CENTER);
		banner.setBannerAnimation(Transformer.DepthPage);
		banner.setOnBannerListener(new OnBannerListener() {
			@Override
			public void OnBannerClick(int position) {
				for (SpecialInfo info : mSliderList){
					Log.d("AllShopSpecialFragment", info.id);
				}
				String id = mSliderList.get(position).id;
				Log.d("AllShopSpecialFragment", "real " + id + "  position" + position);
				jumpToSpecialActivity(id);
			}
		});
		return view;
	}
	@Override
	public void setLastestContent() {
		mSliderList = mAllShopInfoMgr.getAllShopInfo().sliderInfoList;
		mAdList = mAllShopInfoMgr.getAllShopInfo().AdInfoList;
		banner.setImages(mSliderList);
		banner.start();
		AdSetTag();
		showAd();
	}
	private void showAd() {
		ImageLoader.getInstance().displayImage(mAdList.get(0).netUri, AdOne, ImageHelper.initSpecialPathOption());
		ImageLoader.getInstance().displayImage(mAdList.get(1).netUri, AdTwo, ImageHelper.initSpecialPathOption());
	}
	private void AdSetTag() {
		AdOne.setTag(mAdList.get(0).id);
		AdTwo.setTag(mAdList.get(1).id);
	}
	private void findView(View root) {
		banner = (Banner) root.findViewById(R.id.banner);
		AdOne = (ImageView) root.findViewById(R.id.adone);
		AdTwo = (ImageView) root.findViewById(R.id.adtwo);
		AdOne.setOnClickListener(this);
		AdTwo.setOnClickListener(this);
	}

	@Override
	public void onResume() {
		super.onResume();
		banner.stopAutoPlay();
		Log.d("AllShopSpecialFragment", "resume");
	}

	@Override
	public void onPause() {
		super.onPause();
		banner.startAutoPlay();
		Log.d("AllShopSpecialFragment", "on pause");
	}

	@Override
	public void onClick(View v) {
		String SpecialId = (String) v.getTag();
		jumpToSpecialActivity(SpecialId);
	}

	private void jumpToSpecialActivity(String specialId) {
		Intent intent = new Intent(getActivity(), SpecialActivity.class);
		intent.putExtra("specialid", specialId);
		startActivity(intent);
	}
	class BannerImgLoader extends com.youth.banner.loader.ImageLoader{
		@Override
		public void displayImage(Context context, Object path, ImageView imageView) {
			SpecialInfo specialInfo = (SpecialInfo) path;
			ImageAware imageAware = new ImageViewAware(imageView, false);
			ImageLoader.getInstance().displayImage(specialInfo.netUri, imageAware, ImageHelper.initSpecialPathOption());
		}
	}
}

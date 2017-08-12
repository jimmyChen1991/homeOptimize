package com.hhyg.TyClosing.fragment;


import java.util.ArrayList;

import com.hhyg.TyClosing.R;
import com.hhyg.TyClosing.allShop.info.BrandImgInfo;
import com.hhyg.TyClosing.presenter.BrandTitlePresenter;
import com.hhyg.TyClosing.ui.BrandActivity;
import com.hhyg.TyClosing.view.BrandTitleView;

import android.annotation.SuppressLint;
import android.app.Fragment;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;

public class BrandTitleFragment extends Fragment{
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.brand_title_frg, container, false);
		return view;
	}
}

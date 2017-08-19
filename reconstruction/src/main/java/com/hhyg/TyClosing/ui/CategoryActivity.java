package com.hhyg.TyClosing.ui;

import java.util.ArrayList;

import com.google.gson.Gson;
import com.hhyg.TyClosing.R;
import com.hhyg.TyClosing.allShop.info.CateInfo;
import com.hhyg.TyClosing.apiService.HotsearchWordSevice;
import com.hhyg.TyClosing.di.componet.DaggerBrandComponent;
import com.hhyg.TyClosing.di.componet.DaggerCategoryComponent;
import com.hhyg.TyClosing.di.componet.DaggerCommonNetParamComponent;
import com.hhyg.TyClosing.di.module.CommonNetParamModule;
import com.hhyg.TyClosing.entities.CommonParam;
import com.hhyg.TyClosing.entities.search.HotsearchWord;
import com.hhyg.TyClosing.fragment.CategoryContentFragment;
import com.hhyg.TyClosing.fragment.CategoryHotFragment;
import com.hhyg.TyClosing.global.MyApplication;
import com.hhyg.TyClosing.log.Logger;
import com.hhyg.TyClosing.mgr.ShoppingCartMgr;
import com.hhyg.TyClosing.presenter.CategotyPresenter;
import com.hhyg.TyClosing.ui.view.AllShopTopEdit;
import com.hhyg.TyClosing.ui.view.ProgressBar;
import com.hhyg.TyClosing.ui.view.SimpleProgressBar;
import com.hhyg.TyClosing.ui.view.TopEdit;
import com.hhyg.TyClosing.view.CategoryView;
import com.umeng.analytics.MobclickAgent;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

public class CategoryActivity extends Activity implements CategoryView{
	private ListView mListView;
	private CateAdapter mCateTitleAdapter;
	private CategotyPresenter mCategotyPresenter;
	private CategoryHotFragment mCategoryHotFragment;
	private Fragment mCurFragment;
	private ArrayList<CategoryContentFragment> mCategoryContentFragments;
	private ProgressBar mProgressBar;
	@BindView(R.id.hhyg_icon)
	ImageButton hhygIcon;
	@BindView(R.id.cate)
	ImageButton cate;
	@BindView(R.id.hotsearch_content)
	TextView hotWord;
	@BindView(R.id.bar_start)
	TextView searchStart;
	@BindView(R.id.shopcat_point)
	TextView shopcartNum;
	@Inject
	HotsearchWordSevice wordSevice;
	@Inject
	CommonParam commonParam;
	@Inject
	Gson gson;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.category);
		ButterKnife.bind(this);
		DaggerCategoryComponent.builder()
				.applicationComponent(MyApplication.GetInstance().getComponent())
				.commonNetParamComponent(DaggerCommonNetParamComponent.builder().commonNetParamModule(new CommonNetParamModule()).build())
				.build()
				.inject(this);
		findView();
		init();
		if(!TextUtils.isEmpty(MyApplication.GetInstance().getHotSearchWord())){
			hotWord.setText(MyApplication.GetInstance().getHotSearchWord());
			searchStart.setText(getString(R.string.everyone_search));
		}else {
			getHotWord();
		}
		mCategotyPresenter.attach(this);
		mCategotyPresenter.fetchLastedCategotyDate();
		Logger.GetInstance().Track("CategoryActivity on Create");
	}

	private void getHotWord() {
		Observable.just(commonParam)
				.flatMap(new Function<CommonParam, ObservableSource<HotsearchWord>>() {
					@Override
					public ObservableSource<HotsearchWord> apply(@NonNull CommonParam param) throws Exception {
						return wordSevice.getRecommend(gson.toJson(param));
					}
				})
				.observeOn(Schedulers.io())
				.observeOn(AndroidSchedulers.mainThread())
				.subscribe(new Observer<HotsearchWord>() {
					@Override
					public void onSubscribe(@NonNull Disposable d) {

					}

					@Override
					public void onNext(@NonNull HotsearchWord hotsearchWord) {
						if(hotsearchWord.getRecommend() != null && hotsearchWord.getRecommend().size() != 0){
							MyApplication.GetInstance().setHotSearchWord(hotsearchWord);
							searchStart.setText(getString(R.string.everyone_search));
							hotWord.setText(MyApplication.GetInstance().getHotSearchWord());
						}else{
							searchStart.setText(getString(R.string.search_indictor));
						}
					}

					@Override
					public void onError(@NonNull Throwable e) {
						searchStart.setText(getString(R.string.search_indictor));
					}

					@Override
					public void onComplete() {

					}
				});
	}

	@Override
	protected void onResume() {
		super.onResume();
		MobclickAgent.onResume(this);
		MobclickAgent.onPageStart("CategoryActivity");
		Logger.GetInstance().Track("CategoryActivity on onResume");
		shopcartNum.setText(String.valueOf(ShoppingCartMgr.getInstance().getAll().size()));
	}
	@Override
	protected void onPause() {
		super.onPause();
		MobclickAgent.onPause(this);
		MobclickAgent.onPageEnd("CategoryActivity");
		Logger.GetInstance().Track("CategoryActivity on onPause");
	}
	@Override
	protected void onDestroy() {
		super.onDestroy();
		mCategotyPresenter.detach();
	}
	private void init() {
		mCateTitleAdapter = new CateAdapter(this);
		mCategotyPresenter = new CategotyPresenter();
		mListView.setAdapter(mCateTitleAdapter);
		mCategoryHotFragment = new CategoryHotFragment();
		mCategoryContentFragments = new ArrayList<CategoryContentFragment>();
		addFragment(mCategoryHotFragment);
		getFragmentManager().executePendingTransactions();
		mCurFragment = mCategoryHotFragment;
	}
	private void findView(){
		mProgressBar = new SimpleProgressBar((ImageView) findViewById(R.id.infoOperating));
		mListView = (ListView) findViewById(R.id.title_list);
		hhygIcon.setBackgroundResource(R.drawable.back);
		LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) hhygIcon.getLayoutParams();
		layoutParams.setMargins(20,0,0,0);
		hhygIcon.setLayoutParams(layoutParams);
		cate.setBackgroundResource(R.drawable.cate_icon_pressed);

	}
	@Override
	public void showCategoryView(CateInfo rootCateInfo) {
		mCateTitleAdapter.addItem(rootCateInfo.childInfos);
		setCategoryContentFragment(rootCateInfo);
		mCategoryHotFragment.showHotcateInfo(rootCateInfo.childInfos.get(0).childInfos);
	}
	private void setCategoryContentFragment(CateInfo rootCateInfo){
		for(int idx = 1;idx<rootCateInfo.childInfos.size();idx++){
			CategoryContentFragment frag = new CategoryContentFragment();
			addFragment(frag);
			getFragmentManager().beginTransaction().hide(frag).commit();
			getFragmentManager().executePendingTransactions();
			frag.showCateInfo(rootCateInfo.childInfos.get(idx));
			mCategoryContentFragments.add(frag);
		}
	}
	private void addFragment(Fragment fragment) {
		getFragmentManager().beginTransaction().add(R.id.cate_content_layout, fragment).commit();
    }
    private void replaceFragment(Fragment frag,Fragment fragment) {
    	getFragmentManager().beginTransaction().hide(frag).commit();
    	getFragmentManager().beginTransaction().show(fragment).commit();
    }
    class CateAdapter extends BaseAdapter{
    	private LayoutInflater mLayoutInflater; 
    	private boolean[] btnStatus;
    	private int Titleindex;
    	private Button curButton;
    	private boolean firstIn;
    	private ArrayList<CateInfo> mData = new ArrayList<CateInfo>();
    	class ViewHolder{
    		public Button cateTitleBtn;
    	}
    	protected CateAdapter(Context context) {
			super();
			mLayoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		}
		public void addItem(ArrayList<CateInfo> infos){
			btnStatus = new boolean[infos.size()];
			firstIn = true;
			btnStatus[0] = true;
			Titleindex = 0;
    		mData = infos;
    		notifyDataSetChanged();
    	}
		@Override
		public int getCount() {
			return mData.size();
		}
		@Override
		public Object getItem(int position) {
			return null;
		}
		@Override
		public long getItemId(int position) {
			return 0;
		}
		@SuppressLint("InflateParams")
		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			ViewHolder viewHolder = null;
			if(convertView == null){
				viewHolder = new ViewHolder();
				convertView = mLayoutInflater.inflate(R.layout.catetitle_item, null);
				viewHolder.cateTitleBtn = (Button) convertView.findViewById(R.id.catetitlebtn);
				convertView.setTag(viewHolder);
			}else{
				viewHolder = (ViewHolder) convertView.getTag();
			}
			final int curPosition = position;
			final CateInfo item = mData.get(position);
			viewHolder.cateTitleBtn.setText(item.name);
			if(firstIn == true && position == 0){
				curButton = viewHolder.cateTitleBtn;
				firstIn = false;
			}
			if(!btnStatus[position]){
				viewHolder.cateTitleBtn.setBackgroundColor(Color.rgb(51, 51, 51));
				viewHolder.cateTitleBtn.setTextColor(Color.rgb(153, 153, 153));
			}else{
				viewHolder.cateTitleBtn.setTextColor(Color.rgb(195, 140, 86));
				viewHolder.cateTitleBtn.setBackgroundResource(R.drawable.catebg);
			}
			viewHolder.cateTitleBtn.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					Button btn = (Button) v;
					if(curButton != null){
						curButton.setBackgroundColor(Color.rgb(51, 51, 51));
						curButton.setTextColor(Color.rgb(153, 153, 153));
					}
					curButton = btn;
					btnStatus[curPosition] = true;
					btnStatus[Titleindex] = false;
					Titleindex = curPosition;
					btn.setTextColor(Color.rgb(195, 140, 86));
					btn.setBackgroundResource(R.drawable.catebg);
					setContentFragment(item);
				}
			});
			return convertView;
		}
	    private void setContentFragment(CateInfo item){
	    	if(item.name.equals("热门品类")){
				replaceFragment(mCurFragment,mCategoryHotFragment);
				getFragmentManager().executePendingTransactions();
				Logger.GetInstance().Debug(item.name);
				mCurFragment = mCategoryHotFragment;
			}else{
				replaceFragment(mCurFragment,mCategoryContentFragments.get(Titleindex-1));
				getFragmentManager().executePendingTransactions();
				mCurFragment = mCategoryContentFragments.get(Titleindex-1);
			}
	    }
    }
	@Override
	public void startProgress() {
		mProgressBar.startProgress();
	}
	@Override
	public void disProgress() {
		mProgressBar.stopProgress();
		
	}

	@OnClick({R.id.search_wrap})
	public void onViewClicked(View view) {
		Intent intent = new Intent();
		switch (view.getId()) {
			case R.id.search_wrap:
				intent.setClass(this, SearchActivity.class);
				break;
		}
		startActivity(intent);
	}
	@OnClick({R.id.hhyg_icon})
	public void onViewClicked2(View view) {
		switch (view.getId()) {
			case R.id.hhyg_icon:
				finish();
				break;
		}
	}
	@OnClick({R.id.home, R.id.brand, R.id.shopcat})
	public void onViewClicked1(View view) {
		Intent intent = new Intent();
		switch (view.getId()) {
			case R.id.home:
				intent.setClass(this, HomeActivity.class);
				break;
			case R.id.brand:
				intent.setClass(this, BrandActivity.class);
				break;
			case R.id.shopcat:
				intent.setClass(this, ShopCartActivity.class);
				break;
		}
		startActivity(intent);
	}
}

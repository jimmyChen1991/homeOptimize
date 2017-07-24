package com.hhyg.TyClosing.ui;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.hhyg.TyClosing.R;
import com.hhyg.TyClosing.apiService.SearchSevice;
import com.hhyg.TyClosing.di.componet.DaggerCommonNetParamComponent;
import com.hhyg.TyClosing.di.componet.DaggerSearchGoodComponent;
import com.hhyg.TyClosing.di.module.CommonNetParamModule;
import com.hhyg.TyClosing.di.module.SearchGoodsModule;
import com.hhyg.TyClosing.entities.search.FilterBean;
import com.hhyg.TyClosing.entities.search.FilterChangedRaw;
import com.hhyg.TyClosing.entities.search.FilterItem;
import com.hhyg.TyClosing.entities.search.FilterType;
import com.hhyg.TyClosing.entities.search.PeoperFilter;
import com.hhyg.TyClosing.entities.search.PeopertyOfCate;
import com.hhyg.TyClosing.entities.search.PerFilterRes;
import com.hhyg.TyClosing.entities.search.PropertyListBean;
import com.hhyg.TyClosing.entities.search.SearchFilterParam;
import com.hhyg.TyClosing.entities.search.SearchFilterRes;
import com.hhyg.TyClosing.entities.search.SearchGoods;
import com.hhyg.TyClosing.entities.search.SearchGoodsParam;
import com.hhyg.TyClosing.entities.search.SearchRecommend;
import com.hhyg.TyClosing.entities.search.SearchType;
import com.hhyg.TyClosing.entities.shopcart.CastDetail;
import com.hhyg.TyClosing.entities.shopcart.ShopcartListParam;
import com.hhyg.TyClosing.entities.shopcart.ShopcartListRes;
import com.hhyg.TyClosing.exceptions.DataEmtryException;
import com.hhyg.TyClosing.exceptions.ServiceDataException;
import com.hhyg.TyClosing.exceptions.ServiceMsgException;
import com.hhyg.TyClosing.fragment.LoadingDialogFragment;
import com.hhyg.TyClosing.global.MyApplication;
import com.hhyg.TyClosing.info.ShoppingCartInfo;
import com.hhyg.TyClosing.info.SpuInfo;
import com.hhyg.TyClosing.mgr.ShoppingCartMgr;
import com.hhyg.TyClosing.ui.adapter.search.GoodRecAdapter;
import com.hhyg.TyClosing.ui.adapter.search.HorizontalFilterAdapter;
import com.hhyg.TyClosing.ui.adapter.search.PeopertyPopAdapter;
import com.hhyg.TyClosing.ui.adapter.search.VerticalFilterAdapter;
import com.hhyg.TyClosing.ui.adapter.search.VerticalFilterItemAdapter;
import com.hhyg.TyClosing.ui.view.PeopertyPopwindow;
import com.hhyg.TyClosing.util.PauseOnRecScrollListener;
import com.kyleduo.switchbutton.SwitchButton;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import es.dmoral.toasty.Toasty;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.BiFunction;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.functions.Predicate;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by user on 2017/6/7.
 */

public class SearchGoodActivity extends AppCompatActivity {
    private static final String TAG = "SearchGoodActivity";
    @BindView(R.id.backbtn)
    ImageButton backbtn;
    @BindView(R.id.searchtitle)
    TextView searchtitle;
    @BindView(R.id.searchbar)
    TextView searchbar;
    @BindString(R.string.search_token)
    String seach_token;
    @Inject
    SearchGoodsParam param_filter;
    @Inject
    SearchGoodsParam param_use;
    @Inject
    SearchGoodsParam param;
    @Inject
    SearchGoodsParam param_raw;
    @Inject
    @Named("slowSevice")
    SearchSevice searchSevice;
    @Inject
    @Named("fastSevice")
    SearchSevice fastSearchSevice;
    @Inject
    GoodRecAdapter goodRecAdapter;
    @Inject
    HorizontalFilterAdapter horizontalFilterAdapter;
    @Inject
    PeopertyPopAdapter popAdapter;
    @Inject
    VerticalFilterAdapter verticalFilterAdapter;
    @Inject
    CompositeDisposable compositeDisposable;
    @Inject
    Gson gson;
    @Inject
    LoadingDialogFragment dialog;
    @Inject
    SearchType searchType;
    @Inject
    ArrayList<PeopertyOfCate> peopertyOfCates;
    @Inject
    VerticalFilterItemAdapter verticalFilterItemAdapter;
    @Inject
    ShopcartListParam shopcartListParam;
    @Inject
    FilterHelper filterHelper;
    @Inject
    FilterChangedRaw changedRaw;
    @Inject
    @Named("action")
    String action;
    @BindView(R.id.chosehotsale)
    ImageButton chosehotsale;
    @BindView(R.id.chosenew)
    ImageButton chosenew;
    @BindView(R.id.choseprice)
    ImageButton choseprice;
    @BindView(R.id.tochosebtn)
    ImageButton tochosebtn;
    @BindView(R.id.attr_group_wrap)
    RecyclerView attrGroupWrap;
    @BindView(R.id.goods_wrap)
    RecyclerView goodsWrap;
    @BindView(R.id.backtotop)
    ImageButton backtotop;
    @BindView(R.id.backtomain)
    ImageButton backtomain;
    @BindView(R.id.chosengerenal)
    ImageButton chosengerenal;
    @BindView(R.id.drawer_layout)
    DrawerLayout drawerLayout;
    @BindView(R.id.verticalpeoperty_rv)
    RecyclerView verticalpeopertyRv;
    @BindView(R.id.peopertyitem_rv)
    RecyclerView peopertyitemRv;
    @BindView(R.id.vertical_reset)
    Button verticalReset;
    @BindView(R.id.vertical_confirm)
    Button verticalConfirm;
    int totalPage;
    @BindView(R.id.has_stock_cb)
    SwitchButton hasStockCb;
    @BindView(R.id.cast)
    TextView cast;
    @BindView(R.id.cut)
    TextView cut;
    @BindView(R.id.goshopcat)
    Button goshopcat;
    @BindView(R.id.bottomshopcat)
    LinearLayout bottomshopcat;
    @BindView(R.id.fullin)
    TextView fullin;
    @BindView(R.id.full)
    LinearLayout full;
    @BindView(R.id.activitydetail)
    TextView activitydetail;
    @BindView(R.id.activelayout)
    RelativeLayout activelayout;
    @BindView(R.id.selected_icon)
    ImageView selectedIcon;
    @BindView(R.id.pricedef_min)
    EditText pricedefMin;
    @BindView(R.id.pricedef_max)
    EditText pricedefMax;
    @BindView(R.id.pricedef_wrap)
    RelativeLayout pricedefWrap;
    @BindView(R.id.wrap)
    View wrap;
    @BindView(R.id.protect_wrap)
    ViewGroup protectWrap;
    private SearchFilterRes rawFilterRes;
    private PeopertyPopwindow popWindow;
    private View retryView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_searchgood);
        ButterKnife.bind(this);
        DaggerSearchGoodComponent.builder()
                .applicationComponent(MyApplication.GetInstance().getComponent())
                .commonNetParamComponent(DaggerCommonNetParamComponent.builder().commonNetParamModule(new CommonNetParamModule()).build())
                .searchGoodsModule(new SearchGoodsModule((SearchGoodsParam.DataBean) getIntent().getParcelableExtra(seach_token), this))
                .build()
                .inject(this);
        initView();
        param_filter.getData().setAvailable(null); //获取筛选条件，有货没货不传
        Observable.just(param)
                .flatMap(new Function<SearchGoodsParam, ObservableSource<SearchGoods>>() {
                    @Override
                    public ObservableSource<SearchGoods> apply(@NonNull SearchGoodsParam searchGoodsParam) throws Exception {
                        return searchSevice.searchGoodsApi(action,gson.toJson(searchGoodsParam));
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnNext(new Consumer<SearchGoods>() {
                    @Override
                    public void accept(@NonNull SearchGoods searchGoods) throws Exception {
                        if(searchGoods.getData().getNoneResults() != null){
                            displayNoResults(searchGoods.getData().getNoneResults());
                        }
                    }
                })
                .doOnNext(new Consumer<SearchGoods>() {
                    @Override
                    public void accept(@NonNull SearchGoods searchGoods) throws Exception {
                        totalPage = searchGoods.getData().getTotalPages();
                        if (searchGoods.getData().getGoodsList().size() == 0) {
                            goodRecAdapter.setEmptyView(R.layout.empty_view);
                            throw new DataEmtryException();
                        } else {
                            goodRecAdapter.addData(searchGoods.getData().getGoodsList());
                        }
                    }
                })
                .doOnNext(new Consumer<SearchGoods>() {
                    @Override
                    public void accept(@NonNull SearchGoods searchGoods) throws Exception {
                        if((searchType == SearchType.ACTIVITY || searchType == SearchType.PRIVILEGE) && searchGoods.getData().getTitle() != null){
                            activitydetail.setText(searchGoods.getData().getTitle());
                        }
                    }
                })
                .doOnNext(new Consumer<SearchGoods>() {
                    @Override
                    public void accept(@NonNull SearchGoods searchGoods) throws Exception {
                        protectWrap.setVisibility(View.GONE);
                    }
                })
                .doOnError(new Consumer<Throwable>() {
                    @Override
                    public void accept(@NonNull Throwable throwable) throws Exception {
                        protectWrap.setVisibility(View.GONE);
                        if( !(throwable instanceof DataEmtryException)){
                            Toasty.error(SearchGoodActivity.this, getString(R.string.netconnect_exception)).show();
                        }
                    }
                })
                .observeOn(Schedulers.io())
                .filter(new Predicate<SearchGoods>() {
                    @Override
                    public boolean test(@NonNull SearchGoods searchGoods) throws Exception {
                        return searchType == SearchType.KEY_WORD;
                    }
                })
                .map(new Function<SearchGoods, SearchGoodsParam>() {
                    @Override
                    public SearchGoodsParam apply(@NonNull SearchGoods searchGoods) throws Exception {
                        if(searchGoods.getData().getSearchKey() != null && searchGoods.getData().getSearchKey().length() != 0){
                            param_filter.getData().setKeyword(searchGoods.getData().getSearchKey());
                        }
                        return param_filter;
                    }
                })
                .first(param_filter)
                .toObservable()
                .flatMap(new Function<SearchGoodsParam, ObservableSource<SearchFilterRes>>() {
                    @Override
                    public ObservableSource<SearchFilterRes> apply(@NonNull SearchGoodsParam searchGoodsParam) throws Exception {
                        return searchSevice.searchFilterApi(gson.toJson(searchGoodsParam)).retry();
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .doOnNext(new Consumer<SearchFilterRes>() {
                    @Override
                    public void accept(@NonNull SearchFilterRes searchFilterRes) throws Exception {
                        horizontalFilterAdapter.setNewData(new FilterHelper(searchFilterRes).invoke());
                    }
                })
                .observeOn(Schedulers.io())
                .map(new Function<SearchFilterRes, ArrayList<FilterBean>>() {
                    @Override
                    public ArrayList<FilterBean> apply(@NonNull SearchFilterRes searchFilterRes) throws Exception {
                        rawFilterRes = searchFilterRes;
                        return new FilterHelper(searchFilterRes).invokeWithPrice();
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .doOnNext(new Consumer<ArrayList<FilterBean>>() {
                    @Override
                    public void accept(@NonNull ArrayList<FilterBean> filterBeen) throws Exception {
                        verticalFilterAdapter.setNewData(filterBeen);
                        if (filterBeen.size() != 0) {
                            Log.d(TAG, "show");
                            verticalFilterAdapter.getOnItemClickListener().onItemClick(verticalFilterAdapter, null, 0);
                        }
                    }
                })
                .doOnError(new Consumer<Throwable>() {
                    @Override
                    public void accept(@NonNull Throwable throwable) throws Exception {
                        Log.d(TAG, "accept");
                        printErr(throwable);
                    }
                })
                .observeOn(Schedulers.io())
                .flatMap(new Function<ArrayList<FilterBean>, ObservableSource<FilterBean>>() {
                    @Override
                    public ObservableSource<FilterBean> apply(@NonNull ArrayList<FilterBean> filterBeen) throws Exception {
                        return Observable.fromIterable(filterBeen);
                    }
                })
                .filter(new Predicate<FilterBean>() {
                    @Override
                    public boolean test(@NonNull FilterBean filterBean) throws Exception {
                        return filterBean.getType() == FilterType.CATEGORY;
                    }
                })
                .firstElement()
                .toObservable()
                .flatMap(new Function<FilterBean, ObservableSource<SearchGoodsParam.DataBean>>() {
                    @Override
                    public ObservableSource<SearchGoodsParam.DataBean> apply(@NonNull FilterBean filterBean) throws Exception {
                        int size = filterBean.getDataSet().size() - 1;
                        Log.d(TAG, "ss" + size);
                        SearchGoodsParam.DataBean params[] = new SearchGoodsParam.DataBean[size];
                        for (int idx = 1; idx < size + 1; idx++) {
                            FilterItem item = filterBean.getDataSet().get(idx);
                            SearchGoodsParam.DataBean TmpParam = (SearchGoodsParam.DataBean) param.getData().clone();
                            TmpParam.setClass3Id(item.getId());
                            Log.d(TAG, item.getId());
                            params[idx - 1] = TmpParam;
                        }
                        return Observable.fromArray(params);
                    }
                })
                .map(new Function<SearchGoodsParam.DataBean, SearchGoodsParam>() {
                    @Override
                    public SearchGoodsParam apply(@NonNull SearchGoodsParam.DataBean dataBean) throws Exception {
                        SearchGoodsParam tmpParam = (SearchGoodsParam) param.clone();
                        tmpParam.setData(dataBean);
                        return tmpParam;
                    }
                })
                .flatMap(new Function<SearchGoodsParam, ObservableSource<PerFilterRes>>() {
                    @Override
                    public ObservableSource<PerFilterRes> apply(@NonNull SearchGoodsParam searchGoodsParam) throws Exception {
                        return fastSearchSevice.searchFilterApi(gson.toJson(searchGoodsParam))
                                .zipWith(Observable.just(searchGoodsParam.getData().getClass3Id()), new BiFunction<SearchFilterRes, String, PerFilterRes>() {
                                    @Override
                                    public PerFilterRes apply(@NonNull SearchFilterRes searchFilterRes, @NonNull String s) throws Exception {
                                        PerFilterRes res = new PerFilterRes();
                                        res.setCateId(s);
                                        res.setRaw(searchFilterRes);
                                        return res;
                                    }
                                })
                                .retry();
                    }
                })
                .map(new Function<PerFilterRes, PeopertyOfCate>() {
                    @Override
                    public PeopertyOfCate apply(@NonNull PerFilterRes perFilterRes) throws Exception {
                        PeopertyOfCate res = new PeopertyOfCate();
                        res.setCateId(perFilterRes.getCateId());
                        res.setDataSet((ArrayList<PropertyListBean>) perFilterRes.getRaw().getData().getPropertyList());
                        return res;
                    }
                })
                .subscribe(new Observer<PeopertyOfCate>() {

                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        compositeDisposable.add(d);
                    }

                    @Override
                    public void onNext(@NonNull PeopertyOfCate peopertyOfCate) {
                        Log.d(TAG, "start next");
                        peopertyOfCates.add(peopertyOfCate);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        printErr(e);
                        Log.d(TAG, e.toString());
                    }

                    @Override
                    public void onComplete() {

                    }
                });
        Log.d(TAG, "on create end");
    }

    private void displayNoResults(final SearchRecommend r) {
        SpannableStringBuilder sb = new SpannableStringBuilder();
        final String content = "没找到" + "\"" + getIntent().getStringExtra(getString(R.string.search_content)) + "\" 相关商品，为您推荐\"";
        SpannableString noFind = new SpannableString(content);
        noFind.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.red)),4,4+getIntent().getStringExtra(getString(R.string.search_content)).length(), Spanned.SPAN_INCLUSIVE_INCLUSIVE);
        sb.append(noFind);
        if(r.getRecommend() != null){
            final String reCommoned = r.getRecommend() + "\"相关商品，您还可能需要";
            SpannableString reCommonSpan = new SpannableString(reCommoned);
            reCommonSpan.setSpan(new ClickableSpan() {
                @Override
                public void onClick(View widget) {
                    final String searchContent = r.getRecommend();
                    jumpToNewThis(searchContent);
                }
            },0,r.getRecommend().length(),Spanned.SPAN_INCLUSIVE_INCLUSIVE);
            sb.append(reCommonSpan);
        }
        if(r.getMoreneed() != null && r.getMoreneed().size() != 0){
            for (final String item : r.getMoreneed()){
                final String other = "\"" + item + "\"  ";
                SpannableString otherSpan = new SpannableString(other);
                otherSpan.setSpan(new ClickableSpan() {
                    @Override
                    public void onClick(View widget) {
                        jumpToNewThis(item);
                    }
                },1,1 + item.length(),Spanned.SPAN_INCLUSIVE_INCLUSIVE);
                sb.append(otherSpan);
            }
        }
        activitydetail.setMovementMethod(LinkMovementMethod.getInstance());
        activitydetail.setText(sb);
        activelayout.setVisibility(View.VISIBLE);
    }

    private void jumpToNewThis(String searchContent) {
        SearchGoodsParam.DataBean bean =  new SearchGoodsParam.DataBean();
        bean.setKeyword(searchContent);
        Intent it = new Intent(SearchGoodActivity.this,SearchGoodActivity.class);
        it.putExtra(getString(R.string.search_token),bean);
        it.putExtra(getString(R.string.search_content),searchContent);
        it.putExtra(getString(R.string.search_type), SearchType.KEY_WORD.ordinal());
        startActivity(it);
    }

//    @Override
//    public boolean dispatchTouchEvent(MotionEvent ev) {
//        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
//            View v = getCurrentFocus();
//            if (isShouldHideInput(v, ev)) {
//                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
//                if (imm != null) {
//                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
//                }
//            }
//            return super.dispatchTouchEvent(ev);
//        }
//        // 必不可少，否则所有的组件都不会有TouchEvent了
//        if (getWindow().superDispatchTouchEvent(ev)) {
//            return true;
//        }
//        return onTouchEvent(ev);
//    }
//
//    public boolean isShouldHideInput(View v, MotionEvent event) {
//        if (v != null && (v instanceof EditText)) {
//            int[] leftTop = {0, 0};
//            //获取输入框当前的location位置
//            v.getLocationInWindow(leftTop);
//            int left = leftTop[0];
//            int top = leftTop[1];
//            int bottom = top + v.getHeight();
//            int right = left + v.getWidth();
//            if (event.getX() > left && event.getX() < right
//                    && event.getY() > top && event.getY() < bottom) {
//                // 点击的是输入框区域，保留点击EditText的事件
//                return false;
//            } else {
//                return true;
//            }
//        }
//        return false;
//    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK) && (drawerLayout.isDrawerOpen(Gravity.RIGHT))) {
            drawerLayout.closeDrawer(Gravity.RIGHT);
            return false;
        } else {
            return super.onKeyDown(keyCode, event);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (searchType == SearchType.ACTIVITY) {
            getCaset();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        disposableScrollUp();
        compositeDisposable.clear();
    }

    private void initView() {
        initSearchType();
        retryView =  LayoutInflater.from(this).inflate(R.layout.layout_retry, null, false);
        Button retry = (Button) retryView.findViewById(R.id.retry);
        retry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeSearchGoods();
            }
        });
        chosengerenal.setClickable(false);
        searchtitle.setText(getIntent().getStringExtra(getString(R.string.search_content)));
        goodsWrap.setLayoutManager(new GridLayoutManager(this, 3, GridLayoutManager.VERTICAL, false));
        goodsWrap.setHasFixedSize(true);
        goodsWrap.addOnScrollListener(new PauseOnRecScrollListener(true, true));
        attrGroupWrap.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        attrGroupWrap.setHasFixedSize(true);
        goodsWrap.setAdapter(goodRecAdapter);
        goodRecAdapter.bindToRecyclerView(goodsWrap);
        attrGroupWrap.setAdapter(horizontalFilterAdapter);
        goodRecAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                SearchGoods.DataBean.GoodsListBean bean = (SearchGoods.DataBean.GoodsListBean) adapter.getData().get(position);
                Intent it = new Intent(SearchGoodActivity.this, GoodsInfoActivity.class);
                it.putExtra("barcode", bean.getBarcode());
                startActivity(it);
            }
        });
        goodRecAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                SearchGoods.DataBean.GoodsListBean bean = (SearchGoods.DataBean.GoodsListBean) adapter.getItem(position);
                addToShopcart(bean);
            }
        });
        goodRecAdapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
                if (param.getData().getPageNo() >= totalPage) {
                    goodRecAdapter.loadMoreEnd();
                } else {
                    param.getData().setPageNo(param.getData().getPageNo() + 1);
                    fastSearchSevice.searchGoodsApi(action,gson.toJson(param))
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(new Consumer<SearchGoods>() {
                                @Override
                                public void accept(@NonNull SearchGoods searchGoods) throws Exception {
                                    Log.d(TAG, "accept scroll");
                                    protectWrap.setVisibility(View.GONE);
                                    goodRecAdapter.addData(searchGoods.getData().getGoodsList());
                                    goodRecAdapter.loadMoreComplete();
                                }
                            }, new Consumer<Throwable>() {
                                @Override
                                public void accept(@NonNull Throwable throwable) throws Exception {
                                    Log.d(TAG, "disposable" + throwable.toString());
                                    protectWrap.setVisibility(View.GONE);
                                    param.getData().setPageNo(param.getData().getPageNo() - 1);
                                    goodRecAdapter.loadMoreFail();
                                }
                            }, new Action() {
                                @Override
                                public void run() throws Exception {
                                    Log.d(TAG, "complete scrool");
                                }
                            }, new Consumer<Disposable>() {
                                @Override
                                public void accept(@NonNull Disposable disposable) throws Exception {
                                    Log.d(TAG, "start scroll");
                                    protectWrap.setVisibility(View.VISIBLE);
                                }
                            });
                }
            }
        }, goodsWrap);
        verticalpeopertyRv.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        verticalpeopertyRv.setHasFixedSize(true);
        verticalpeopertyRv.setAdapter(verticalFilterAdapter);
        verticalFilterAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                verticalFilterAdapter.clearSelectStatus();
                FilterBean bean = (FilterBean) adapter.getData().get(position);
                bean.setVertacalShow(true);
                verticalFilterItemAdapter.setFilterBean(bean);
                adapter.notifyDataSetChanged();
                verticalFilterItemAdapter.setNewData(bean.getDataSet());
                if (bean.getType() == FilterType.PRICE) {
                    pricedefWrap.setVisibility(View.VISIBLE);
                } else {
                    pricedefWrap.setVisibility(View.GONE);
                }
            }
        });
        peopertyitemRv.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        peopertyitemRv.setHasFixedSize(true);
        peopertyitemRv.setAdapter(verticalFilterItemAdapter);
        hasStockCb.setChecked(true);
        hasStockCb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    hasStockCb.setBackColorRes(R.color.maincolor);
                    param.getData().setAvailable("1");
                } else {
                    hasStockCb.setBackColorRes(R.color.delight);
                    param.getData().setAvailable("");
                }
            }
        });
        View popContent = LayoutInflater.from(this).inflate(R.layout.popwindow_peoperty, null);
        RecyclerView recyclerView = (RecyclerView) popContent.findViewById(R.id.pop_rv);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 3, GridLayoutManager.VERTICAL, false));
        recyclerView.setAdapter(popAdapter);
        Button reset = (Button) popContent.findViewById(R.id.reset);
        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (popAdapter.getFilterData().getType() == FilterType.CATEGORY) {
                    categoryChange();
                }
                final FilterBean bean = popAdapter.getFilterData();
                FilterBean otherBean = new FilterBean();
                ArrayList<FilterBean> beans = (ArrayList<FilterBean>) verticalFilterAdapter.getData();
                for (FilterBean tmpBean : beans) {
                    if (tmpBean.getName().equals(bean.getName())) {
                        otherBean = tmpBean;
                    }
                }
                clearFilterStatus(bean, otherBean);
            }
        });
        Button confirm = (Button) popContent.findViewById(R.id.confirm);
        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popWindow.dismiss();
            }
        });
        popWindow = new PeopertyPopwindow(this, popContent);

        horizontalFilterAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                FilterBean bean = (FilterBean) adapter.getData().get(position);
                bean.setShowNow(true);
                adapter.notifyDataSetChanged();
                popAdapter.setFilterData(bean);
                copyFilter((ArrayList<FilterBean>) horizontalFilterAdapter.getData());
                changeBackgroupAlpha(true);
                popWindow.showAsDropDown(attrGroupWrap, 0, 0);
            }
        });
        popWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                popAdapter.getFilterData().setShowNow(false);
                horizontalFilterAdapter.notifyDataSetChanged();
                checkFilterStatus4View();
                changeBackgroupAlpha(false);
                if (checkFilter4Change((ArrayList<FilterBean>) horizontalFilterAdapter.getData())) {
                    changeSearchGoods();
                }
            }
        });
        verticalFilterItemAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                if (position == 0) {
                    Log.d(TAG, "asd");
                }
                final FilterBean beanArg1 = verticalFilterItemAdapter.getFilterBean();
                final FilterItem itemArg1 = (FilterItem) adapter.getItem(position);
                if (itemArg1.isAllchoseFlag() && itemArg1.isSelected()) {
                    return;
                }
                if (beanArg1.getType() == FilterType.PRICE) {
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    if (imm != null) {
                        imm.hideSoftInputFromWindow(pricedefMin.getWindowToken(), 0);
                        imm.hideSoftInputFromWindow(pricedefMax.getWindowToken(), 0);
                        selectedIcon.setVisibility(View.GONE);
                        pricedefMin.clearFocus();
                        pricedefMax.clearFocus();
                        pricedefMax.getText().clear();
                        pricedefMin.getText().clear();
                    }
                }
                FilterBean beanArg2 = new FilterBean();
                ArrayList<FilterBean> beans = (ArrayList<FilterBean>) horizontalFilterAdapter.getData();
                for (FilterBean tmpBean : beans) {
                    if (tmpBean.getName().equals(beanArg1.getName())) {
                        beanArg2 = tmpBean;
                        break;
                    }
                }
                FilterItem itemArg2 = new FilterItem();
                for (FilterItem tmpItem : beanArg2.getDataSet()) {
                    if (tmpItem.getName().equals(itemArg1.getName())) {
                        itemArg2 = tmpItem;
                        break;
                    }
                }
                changeItemStatus(beanArg1, itemArg1, beanArg2, itemArg2);
            }
        });

        popAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                final FilterBean beanArg1 = popAdapter.getFilterData();
                final FilterItem itemArg1 = (FilterItem) adapter.getItem(position);
                if (itemArg1.isAllchoseFlag() && itemArg1.isSelected()) {
                    Log.d(TAG, "return");
                    return;
                }
                FilterBean beanArg2 = new FilterBean();
                ArrayList<FilterBean> beans = (ArrayList<FilterBean>) verticalFilterAdapter.getData();
                for (FilterBean tmpBean : beans) {
                    if (tmpBean.getName().equals(beanArg1.getName())) {
                        beanArg2 = tmpBean;
                        break;
                    }
                }
                FilterItem itemArg2 = new FilterItem();
                for (FilterItem tmpItem : beanArg2.getDataSet()) {
                    if (tmpItem.getName().equals(itemArg1.getName())) {
                        itemArg2 = tmpItem;
                        break;
                    }
                }
                changeItemStatus(beanArg1, itemArg1, beanArg2, itemArg2);
            }
        });

        drawerLayout.addDrawerListener(new DrawerLayout.DrawerListener() {
            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {

            }

            @Override
            public void onDrawerOpened(View drawerView) {
                copyFilter((ArrayList<FilterBean>) verticalFilterAdapter.getData());
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                checkFilterStatus4View();
                if (checkFilter4Change((ArrayList<FilterBean>) verticalFilterAdapter.getData())) {
                    changeSearchGoods();
                }
            }

            @Override
            public void onDrawerStateChanged(int newState) {

            }
        });
        initPriceDef();
    }

    private void changeBackgroupAlpha(boolean showAlpha) {
//        WindowManager.LayoutParams lp = getWindow().getAttributes();
//        lp.alpha = alpha;
//        getWindow().setAttributes(lp);
        if(showAlpha){
            wrap.setVisibility(View.VISIBLE);
        }else {
            wrap.setVisibility(View.GONE);
        }
        wrap.setAlpha((float) 0.3);
//        goodsWrap.setBackgroundResource(R.color.myalpha);
    }

    private void copyFilter(ArrayList<FilterBean> source) {
        String minPrice = pricedefMin.getText().toString();
        String maxPrice = pricedefMax.getText().toString();
        changedRaw.getPriceBean().setMinPrice(minPrice);
        changedRaw.getPriceBean().setMaxPrice(maxPrice);
        changedRaw.setAvailable(param.getData().getAvailable());
        changedRaw.getFilterBeens().clear();
        for (FilterBean bean : source) {
            try {
                changedRaw.getFilterBeens().add((FilterBean) bean.clone());
            } catch (CloneNotSupportedException e) {
                e.printStackTrace();
            }
        }
    }

    public boolean checkFilter4Change(ArrayList<FilterBean> source) {
        Log.d(TAG, "start");
        boolean changed = false;
        if (!param.getData().getAvailable().equals(changedRaw.getAvailable())) {
            return true;
        }
        String minPrice = pricedefMin.getText().toString();
        String maxPrice = pricedefMax.getText().toString();
        String minPriceRaw = changedRaw.getPriceBean().getMinPrice();
        String maxPriceRaw = changedRaw.getPriceBean().getMaxPrice();
        if (minPrice != null && maxPrice != null && minPriceRaw != null && maxPriceRaw != null) {
            if (!(minPriceRaw.equals(minPrice) && maxPriceRaw.equals(maxPrice))) {
                return true;
            }
        } else if (minPrice != null && minPriceRaw == null) {
            return true;
        } else if (maxPrice != null && maxPriceRaw == null) {
            return true;
        }
        if (source.size() != changedRaw.getFilterBeens().size()) {
            changed = true;
        } else {
            for (int index = 0; index < source.size(); index++) {
                FilterBean arg1 = source.get(index);
                FilterBean arg2 = changedRaw.getFilterBeens().get(index);
                if (!arg1.getName().equals(arg2.getName())) {
                    changed = true;
                    break;
                }
                if (arg1.isSelected() != arg2.isSelected()) {
                    changed = true;
                    break;
                }
                if (arg1.getDataSet().size() != arg2.getDataSet().size()) {
                    changed = true;
                    break;
                } else {
                    boolean shouldBreak = false;
                    for (int index2 = 0; index2 < arg1.getDataSet().size(); index2++) {
                        FilterItem item1 = arg1.getDataSet().get(index2);
                        FilterItem item2 = arg2.getDataSet().get(index2);
                        if (!item1.getName().equals(item2.getName())) {
                            changed = true;
                            shouldBreak = true;
                            break;
                        }
                        if (item1.isSelected() != item2.isSelected()) {
                            changed = true;
                            shouldBreak = true;
                            break;
                        }
                    }
                    if (shouldBreak) {
                        break;
                    }
                }
            }
        }
        Log.d(TAG, "done");
        return changed;
    }

    private void initPriceDef() {
        pricedefMin.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                priceEditFocusChange(hasFocus);
            }
        });
        pricedefMax.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                priceEditFocusChange(hasFocus);
            }
        });
    }

    private void priceEditFocusChange(boolean hasFocus) {
        if (hasFocus) {
            selectedIcon.setVisibility(View.VISIBLE);
            priceDefALLStatus();
        }
    }

//    private void textChanged() {
//        if (pricedefMax.getText().toString().length() == 0 && pricedefMin.getText().toString().length() == 0) {
//            priceDefALLStatus();
//        } else if (pricedefMax.getText().toString().length() != 0 || pricedefMin.getText().toString().length() != 0) {
//            priceDefChangeStatus();
//        }
//    }
//
//    private void priceDefChangeStatus() {
//        verticalFilterItemAdapter.getFilterBean().setSelected(true);
//        verticalFilterItemAdapter.getFilterBean().setSelectedName(pricedefMin.getText().toString() + "---" + pricedefMax.getText().toString());
//        for (FilterItem item : verticalFilterItemAdapter.getData()) {
//            item.setSelected(false);
//        }
//        verticalFilterAdapter.notifyDataSetChanged();
//        verticalFilterItemAdapter.notifyDataSetChanged();
//    }

    private void priceDefALLStatus() {
        verticalFilterItemAdapter.getFilterBean().setSelected(true);
        verticalFilterItemAdapter.getFilterBean().setSelectedName("自定义");
        for (FilterItem filterItem : verticalFilterItemAdapter.getData()) {
            filterItem.setSelected(false);
        }
        verticalFilterItemAdapter.notifyDataSetChanged();
        verticalFilterAdapter.notifyDataSetChanged();
    }

    private void changeItemStatus(FilterBean beanArg1, FilterItem itemArg1, FilterBean beanArg2, FilterItem itemArg2) {
        if (beanArg1.getType() == FilterType.CATEGORY && itemArg1.isAllchoseFlag()) {
            categoryChange();
        } else if (beanArg1.getType() == FilterType.CATEGORY && (!itemArg1.isAllchoseFlag())) {
            if (itemArg1.isSelected()) {
                categoryChange();
            } else {
                categoryChange(itemArg1);
            }
        }

        if (itemArg1.isAllchoseFlag()) {
            clearFilterStatus(beanArg1, beanArg2);
        } else if (itemArg1.isSelected()) {
            switch (beanArg1.getType()) {
                case CATEGORY:
                    Observable.just(beanArg1, beanArg2)
                            .observeOn(AndroidSchedulers.mainThread())
                            .doOnNext(new Consumer<FilterBean>() {
                                @Override
                                public void accept(@NonNull FilterBean filterBean) throws Exception {
                                    filterBean.setSelected(false);
                                    filterBean.setSelectedName("");
                                }
                            })
                            .flatMap(new Function<FilterBean, ObservableSource<FilterItem>>() {
                                @Override
                                public ObservableSource<FilterItem> apply(@NonNull FilterBean filterBean) throws Exception {
                                    return Observable.fromIterable(filterBean.getDataSet());
                                }
                            })
                            .subscribe(new Observer<FilterItem>() {
                                @Override
                                public void onSubscribe(@NonNull Disposable d) {

                                }

                                @Override
                                public void onNext(@NonNull FilterItem filterItem) {
                                    if (filterItem.isAllchoseFlag()) {
                                        filterItem.setSelected(true);
                                    } else {
                                        filterItem.setSelected(false);
                                    }
                                }

                                @Override
                                public void onError(@NonNull Throwable e) {

                                }

                                @Override
                                public void onComplete() {
                                    popAdapter.notifyDataSetChanged();
                                    horizontalFilterAdapter.notifyDataSetChanged();
                                    verticalFilterAdapter.notifyDataSetChanged();
                                    verticalFilterItemAdapter.notifyDataSetChanged();
                                }
                            });
                    break;
                case PRICE:
                    Observable.just(beanArg1)
                            .observeOn(AndroidSchedulers.mainThread())
                            .doOnNext(new Consumer<FilterBean>() {
                                @Override
                                public void accept(@NonNull FilterBean filterBean) throws Exception {
                                    filterBean.setSelected(false);
                                    filterBean.setSelectedName("");
                                }
                            })
                            .flatMap(new Function<FilterBean, ObservableSource<FilterItem>>() {
                                @Override
                                public ObservableSource<FilterItem> apply(@NonNull FilterBean filterBean) throws Exception {
                                    return Observable.fromIterable(filterBean.getDataSet());
                                }
                            })
                            .subscribe(new Observer<FilterItem>() {
                                @Override
                                public void onSubscribe(@NonNull Disposable d) {

                                }

                                @Override
                                public void onNext(@NonNull FilterItem filterItem) {
                                    if (filterItem.isAllchoseFlag()) {
                                        filterItem.setSelected(true);
                                    } else {
                                        filterItem.setSelected(false);
                                    }
                                }

                                @Override
                                public void onError(@NonNull Throwable e) {

                                }

                                @Override
                                public void onComplete() {
                                    popAdapter.notifyDataSetChanged();
                                    horizontalFilterAdapter.notifyDataSetChanged();
                                    verticalFilterAdapter.notifyDataSetChanged();
                                    verticalFilterItemAdapter.notifyDataSetChanged();
                                }
                            });
                    break;
                case BRAND:
                case PEOPERTY:
                    itemArg1.setSelected(false);
                    itemArg2.setSelected(false);
                    boolean b2 = false;
                    StringBuilder sb2 = new StringBuilder();
                    for (FilterItem filterItem : beanArg1.getDataSet()) {
                        if (filterItem.isSelected()) {
                            b2 = true;
                            sb2.append(filterItem.getName());
                            sb2.append("，");
                        }
                    }
                    if (b2) {
                        beanArg1.setSelected(true);
                        beanArg1.setSelectedName(sb2.deleteCharAt(sb2.length() - 1).toString());
                        beanArg2.setSelected(true);
                        beanArg2.setSelectedName(sb2.deleteCharAt(sb2.length() - 1).toString());
                    } else {
                        for (FilterItem item : beanArg1.getDataSet()) {
                            if (item.isAllchoseFlag()) {
                                item.setSelected(true);
                                break;
                            }
                        }
                        for (FilterItem item : beanArg2.getDataSet()) {
                            if (item.isAllchoseFlag()) {
                                item.setSelected(true);
                                break;
                            }
                        }
                        beanArg1.setSelected(false);
                        beanArg2.setSelected(false);
                    }
                    popAdapter.notifyDataSetChanged();
                    verticalFilterAdapter.notifyDataSetChanged();
                    verticalFilterItemAdapter.notifyDataSetChanged();
                    horizontalFilterAdapter.notifyDataSetChanged();
                    break;
            }

        } else if (!itemArg1.isSelected()) {
            for (FilterItem item : beanArg1.getDataSet()) {
                if (item.isAllchoseFlag()) {
                    item.setSelected(false);
                    break;
                }
            }
            for (FilterItem item : beanArg2.getDataSet()) {
                if (item.isAllchoseFlag()) {
                    item.setSelected(false);
                    break;
                }
            }
            switch (beanArg1.getType()) {
                case BRAND:
                case PEOPERTY:
                    itemArg1.setSelected(true);
                    itemArg2.setSelected(true);
                    String seletedName = buildSeletedName(beanArg1);
                    beanArg1.setSelected(true);
                    beanArg1.setSelectedName(seletedName);
                    beanArg2.setSelected(true);
                    beanArg2.setSelectedName(seletedName);
                    verticalFilterAdapter.notifyDataSetChanged();
                    verticalFilterItemAdapter.notifyDataSetChanged();
                    horizontalFilterAdapter.notifyDataSetChanged();
                    popAdapter.notifyDataSetChanged();
                    break;
                case CATEGORY:
                case PRICE:
                    for (FilterItem item : beanArg1.getDataSet()) {
                        if (item.isSelected()) {
                            item.setSelected(false);
                            break;
                        }
                    }
                    for (FilterItem item : beanArg2.getDataSet()){
                        if (item.isSelected()) {
                            item.setSelected(false);
                            break;
                        }
                    }
                    itemArg1.setSelected(true);
                    itemArg2.setSelected(true);
                    String seletedName1 = buildSeletedName(beanArg1);
                    beanArg1.setSelected(true);
                    beanArg1.setSelectedName(seletedName1);
                    beanArg2.setSelected(true);
                    beanArg2.setSelectedName(seletedName1);
                    popAdapter.notifyDataSetChanged();
                    verticalFilterAdapter.notifyDataSetChanged();
                    verticalFilterItemAdapter.notifyDataSetChanged();
                    horizontalFilterAdapter.notifyDataSetChanged();
            }
        }
        Log.d(TAG, "verticalChange");
    }

    private String buildSeletedName(FilterBean beanArg1) {
        StringBuilder sb = new StringBuilder();
        for (FilterItem item : beanArg1.getDataSet()) {
            if (item.isSelected()) {
                sb.append(item.getName());
                sb.append("，");
            }
        }
        sb.deleteCharAt(sb.length() - 1);
        return sb.toString();
    }

    private void checkFilterStatus4View() {
        Observable.fromIterable(verticalFilterAdapter.getData())
                .observeOn(AndroidSchedulers.mainThread())
                .any(new Predicate<FilterBean>() {
                    @Override
                    public boolean test(@NonNull FilterBean filterBean) throws Exception {
                        return filterBean.isSelected();
                    }
                })
                .subscribe(new SingleObserver<Boolean>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onSuccess(@NonNull Boolean aBoolean) {
                        if (aBoolean) {
                            Log.d(TAG, "success");
                            tochosebtn.setBackgroundResource(R.drawable.allshop_search_goodlist_tochose_pressed);
                        } else {
                            Log.d(TAG, "fail");
                            tochosebtn.setBackgroundResource(R.drawable.allshop_search_goodlist_tochose_normal);
                        }
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        Log.d(TAG, "check" + e.toString());
                    }
                });
    }

    private void clearFilterStatus(FilterBean bean, FilterBean otherBean) {
        Observable.just(bean, otherBean)
                .observeOn(AndroidSchedulers.mainThread())
                .doOnNext(new Consumer<FilterBean>() {
                    @Override
                    public void accept(@NonNull FilterBean filterBean) throws Exception {
                        Log.d(TAG, "accept");
                        filterBean.setSelected(false);
                        filterBean.setSelectedName("");
                    }
                })
                .flatMap(new Function<FilterBean, ObservableSource<FilterItem>>() {
                    @Override
                    public ObservableSource<FilterItem> apply(@NonNull FilterBean filterBean) throws Exception {
                        return Observable.fromIterable(filterBean.getDataSet());
                    }
                })
                .subscribe(new Observer<FilterItem>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        compositeDisposable.add(d);
                    }

                    @Override
                    public void onNext(@NonNull FilterItem filterItem) {
                        if (!filterItem.isAllchoseFlag()) {
                            filterItem.setSelected(false);
                        } else {
                            filterItem.setSelected(true);
                        }
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        Log.d(TAG, e.toString());
                    }

                    @Override
                    public void onComplete() {
                        Log.d(TAG, "complete");
                        printStatus();
                        verticalFilterItemAdapter.notifyDataSetChanged();
                        verticalFilterAdapter.notifyDataSetChanged();
                        horizontalFilterAdapter.notifyDataSetChanged();
                        popAdapter.notifyDataSetChanged();
                    }
                });
    }

    private void printStatus() {
//        for (FilterBean bean : verticalFilterAdapter.getData()) {
//            Log.d(TAG, "x" + bean.isSelected());
//            for (FilterItem item : bean.getDataSet()) {
//                Log.d(TAG, "c" + item.isSelected());
//            }
//        }
    }

    private void categoryChange(FilterItem item) {
        removePeoperty();
        changePeoperty(item);
    }

    private void categoryChange() {
        removePeoperty();
        FilterItem filterItem = new FilterItem();
        filterItem.setSelected(false);
        changePeoperty(filterItem);
    }

    private void addToShopcart(SearchGoods.DataBean.GoodsListBean bean) {
        final int choseCnt;
        if (!ShoppingCartMgr.getInstance().isInfoExist(bean.getBarcode())) {
            choseCnt = 1;
        } else {
            choseCnt = ShoppingCartMgr.getInstance().getInfoByBarCode(bean.getBarcode()).cnt + 1;
        }
        if (choseCnt > bean.getStock()) {
            Toasty.error(SearchGoodActivity.this,"该商品库存不足").show();
            return;
        }
        if (bean.getStock() > 0) {
            SpuInfo spu = new SpuInfo();
            spu.barCode = bean.getBarcode();
            spu.activeId = bean.getActive_code();
            spu.name = bean.getName();
            spu.stock = bean.getStock();
            spu.citAmount = 1000;
            spu.attrInfo = bean.getName();
            spu.msPrice = bean.getMianshui_price();
            if (bean.getImage() != null && !bean.getImage().equals("")) {
                ArrayList<String> imgLinks = new ArrayList<>();
                imgLinks.add(bean.getImage());
                spu.imageLinks = imgLinks;
            }
            ShoppingCartMgr mShoppingCartMgr =  ShoppingCartMgr.getInstance();
            if (mShoppingCartMgr.isInfoExist(bean.getBarcode())) {
                mShoppingCartMgr.updateShopCnt(bean.getBarcode(), choseCnt);
            } else {
                mShoppingCartMgr.addInfo(spu, bean.getName(), bean.getBrand_name(), choseCnt, 1211, bean.getName());
            }
            if (!(searchType == SearchType.PRIVILEGE)) {
                mShoppingCartMgr.updateActiveId(bean.getBarcode(), bean.getActive_code());
            } else {
                if (bean.getActive_code() != null) {
                    mShoppingCartMgr.updateActiveId(bean.getBarcode(), bean.getActive_code());
                } else {
                    mShoppingCartMgr.updateActiveId(bean.getBarcode(), "");
                }
            }
            Toasty.success(SearchGoodActivity.this,"加入购物车成功").show();
            if (!(searchType == SearchType.PRIVILEGE)) {
                getCaset();
            }
        }
    }

    private void getCaset() {
        ShoppingCartMgr mShoppingCartMgr = ShoppingCartMgr.getInstance();
        Observable.fromIterable(mShoppingCartMgr.getAll())
                .filter(new Predicate<ShoppingCartInfo>() {
                    @Override
                    public boolean test(@NonNull ShoppingCartInfo shoppingCartInfo) throws Exception {
                        return shoppingCartInfo.activeId.equals(param.getData().getActivityId());
                    }
                })
                .toList()
                .toObservable()
                .map(new Function<List<ShoppingCartInfo>, ShopcartListParam>() {
                    @Override
                    public ShopcartListParam apply(@NonNull List<ShoppingCartInfo> shoppingCartInfos) throws Exception {
                        ArrayList<ShopcartListParam.DataBean> res = new ArrayList<ShopcartListParam.DataBean>();
                        for (ShoppingCartInfo info : shoppingCartInfos) {
                            ShopcartListParam.DataBean bean = new ShopcartListParam.DataBean();
                            bean.setBarcode(info.barCode);
                            bean.setActivity(info.activeId);
                            bean.setNumber("" + info.cnt);
                            res.add(bean);
                        }
                        shopcartListParam.setData(res);
                        return shopcartListParam;
                    }
                })
                .flatMap(new Function<ShopcartListParam, ObservableSource<ShopcartListRes>>() {
                    @Override
                    public ObservableSource<ShopcartListRes> apply(@NonNull ShopcartListParam shopcartListParam) throws Exception {
                        return searchSevice.shopcartList(gson.toJson(shopcartListParam));
                    }
                })
                .map(new Function<ShopcartListRes, ShopcartListRes.DataBean>() {
                    @Override
                    public ShopcartListRes.DataBean apply(@NonNull ShopcartListRes shopcartListRes) throws Exception {
                        return shopcartListRes.getData();
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<ShopcartListRes.DataBean>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        compositeDisposable.add(d);
                    }

                    @Override
                    public void onNext(@NonNull ShopcartListRes.DataBean dataBean) {
                        CastDetail detail = dataBean.getActive_columns().get(param.getData().getActivityId());
                        setPrice(detail.getType(),detail.getActive_price(), detail.getPreferentialPrice(), detail.getDesc_fee());
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        Log.d(TAG, e.toString());
                        if (e instanceof JsonSyntaxException) {
                            full.setVisibility(View.GONE);
                            cast.setText("小计   " + getString(R.string.server_settle_moeny) + "0");
                            cut.setText("优惠   " + getString(R.string.server_settle_moeny) + "0");
                        }

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    private void setPrice(String type,String cast2, String fee, String comment) {
        if (cast2 != null && fee != null) {
            cast.setText("小计   " + getString(R.string.server_settle_moeny) + cast2);
            cut.setText("优惠   " + getString(R.string.server_settle_moeny) + fee);
        } else {
            cast.setText("小计   " + getString(R.string.server_settle_moeny) + "0");
            cut.setText("优惠   " + getString(R.string.server_settle_moeny) + "0");
        }
        if (comment != null && !comment.equals("") && type != null && !type.equals("1")) {
            comment = comment.replace("去凑单>", "");
            full.setVisibility(View.VISIBLE);
            fullin.setText(Html.fromHtml(comment));
        } else {
            full.setVisibility(View.GONE);
        }
    }

    private void initSearchType() {
        if (searchType == SearchType.ACTIVITY) {
            bottomshopcat.setVisibility(View.VISIBLE);
            activelayout.setVisibility(View.VISIBLE);
            goodsWrap.setPadding(40, 0, 40, 220);
        } else if (searchType == SearchType.PRIVILEGE) {
            bottomshopcat.setVisibility(View.VISIBLE);
            goodsWrap.setPadding(40, 0, 40, 150);
            activelayout.setVisibility(View.VISIBLE);
            cast.setVisibility(View.GONE);
            cut.setVisibility(View.GONE);
            final String activeDesc = "以下商品享" + getIntent().getStringExtra(getString(R.string.search_desc)) + "优惠，可使用特权价购买";
            activitydetail.setText(activeDesc);
        }
    }

    private void changeSearchGoods() {
        printStatus();
        Observable<SearchFilterParam> A = Observable.fromIterable(verticalFilterAdapter.getData())
                .filter(new Predicate<FilterBean>() {
                    @Override
                    public boolean test(@NonNull FilterBean filterBean) throws Exception {
                        return filterBean.isSelected();
                    }
                })
                .filter(new Predicate<FilterBean>() {
                    @Override
                    public boolean test(@NonNull FilterBean filterBean) throws Exception {
                        return filterBean.getType() == FilterType.CATEGORY;
                    }
                })
                .flatMap(new Function<FilterBean, ObservableSource<FilterItem>>() {
                    @Override
                    public ObservableSource<FilterItem> apply(@NonNull FilterBean filterBean) throws Exception {
                        return Observable.fromIterable(filterBean.getDataSet());
                    }
                })
                .filter(new Predicate<FilterItem>() {
                    @Override
                    public boolean test(@NonNull FilterItem filterItem) throws Exception {
                        return filterItem.isSelected();
                    }
                })
                .map(new Function<FilterItem, SearchFilterParam>() {
                    @Override
                    public SearchFilterParam apply(@NonNull FilterItem filterBean) throws Exception {
                        SearchFilterParam param = new SearchFilterParam();
                        param.setType(FilterType.CATEGORY);
                        param.setParam(filterBean.getId());
                        return param;
                    }
                });
        Observable<SearchFilterParam> B = Observable.fromIterable(verticalFilterAdapter.getData())
                .filter(new Predicate<FilterBean>() {
                    @Override
                    public boolean test(@NonNull FilterBean filterBean) throws Exception {
                        return filterBean.isSelected();
                    }
                })
                .filter(new Predicate<FilterBean>() {
                    @Override
                    public boolean test(@NonNull FilterBean filterBean) throws Exception {
                        return filterBean.getType() == FilterType.PRICE;
                    }
                })
                .flatMap(new Function<FilterBean, ObservableSource<FilterItem>>() {
                    @Override
                    public ObservableSource<FilterItem> apply(@NonNull FilterBean filterBean) throws Exception {
                        return Observable.fromIterable(filterBean.getDataSet());
                    }
                })
                .filter(new Predicate<FilterItem>() {
                    @Override
                    public boolean test(@NonNull FilterItem filterItem) throws Exception {
                        Log.d(TAG, "max" + filterItem.getMaxPrice() + "min" + filterItem.getMinPrice() + filterItem.isSelected());
                        return filterItem.isSelected();
                    }
                })
                .map(new Function<FilterItem, SearchFilterParam>() {
                    @Override
                    public SearchFilterParam apply(@NonNull FilterItem filterItem) throws Exception {
                        Log.d(TAG, "map _ price");
                        SearchFilterParam param = new SearchFilterParam();
                        param.setType(FilterType.PRICE);
                        param.setParam(filterItem.getMinPrice());
                        param.setParam2(filterItem.getMaxPrice());
                        return param;
                    }
                });

        Observable<SearchFilterParam> E = Observable.just(pricedefMin, pricedefMax)
                .map(new Function<EditText, String>() {
                    @Override
                    public String apply(@NonNull EditText editText) throws Exception {
                        return editText.getText().toString();
                    }
                })
                .toList()
                .toObservable()
                .filter(new Predicate<List<String>>() {
                    @Override
                    public boolean test(@NonNull List<String> strings) throws Exception {
                        boolean res = false;
                        for (String num : strings) {
                            if (num.length() > 0) {
                                res = true;
                            }
                        }
                        return res;
                    }
                })
                .map(new Function<List<String>, SearchFilterParam>() {
                    @Override
                    public SearchFilterParam apply(@NonNull List<String> strings) throws Exception {
                        SearchFilterParam filterParam = new SearchFilterParam();
                        filterParam.setType(FilterType.PRICE);
                        String value1 = strings.get(0);
                        String value2 = strings.get(1);
                        if (value1.length() > 0 && value2.length() > 0) {
                            Integer intValue1 = Integer.valueOf(value1);
                            Integer intValue2 = Integer.valueOf(value2);
                            if (intValue1 > intValue2) {
                                filterParam.setParam(value2);
                                filterParam.setParam2(value1);
                            } else {
                                filterParam.setParam(value1);
                                filterParam.setParam2(value2);
                            }
                        } else if (pricedefMin.getText().length() > 0) {
                            filterParam.setParam(pricedefMin.getText().toString());
                        } else if (pricedefMax.getText().length() > 0) {
                            filterParam.setParam2(pricedefMax.getText().toString());
                        }
                        return filterParam;
                    }
                });

        Observable<SearchFilterParam> C = Observable.fromIterable(verticalFilterAdapter.getData())
                .filter(new Predicate<FilterBean>() {
                    @Override
                    public boolean test(@NonNull FilterBean filterBean) throws Exception {
                        return filterBean.getType() == FilterType.BRAND;
                    }
                })
                .filter(new Predicate<FilterBean>() {
                    @Override
                    public boolean test(@NonNull FilterBean filterBean) throws Exception {
                        Log.d(TAG, "test" + filterBean.isSelected());
                        return filterBean.isSelected();
                    }
                })
                .doOnNext(new Consumer<FilterBean>() {
                    @Override
                    public void accept(@NonNull FilterBean filterBean) throws Exception {
                        Log.d(TAG, filterBean.getName());
                    }
                })
                .doOnError(new Consumer<Throwable>() {
                    @Override
                    public void accept(@NonNull Throwable throwable) throws Exception {
                        Log.d(TAG, throwable.toString());
                    }
                })
                .flatMap(new Function<FilterBean, ObservableSource<List<FilterItem>>>() {
                    @Override
                    public ObservableSource<List<FilterItem>> apply(@NonNull FilterBean filterBean) throws Exception {
                        return Observable.fromIterable(filterBean.getDataSet()).filter(new Predicate<FilterItem>() {
                            @Override
                            public boolean test(@NonNull FilterItem filterItem) throws Exception {
                                return filterItem.isSelected();
                            }
                        }).toList().toObservable();
                    }
                })
                .map(new Function<List<FilterItem>, SearchFilterParam>() {
                    @Override
                    public SearchFilterParam apply(@NonNull List<FilterItem> filterItems) throws Exception {
                        SearchFilterParam param = new SearchFilterParam();
                        param.setType(FilterType.BRAND);
                        StringBuilder sb = new StringBuilder();
                        for (FilterItem item : filterItems) {
                            Log.d(TAG, item.getName());
                            sb.append(item.getId());
                            sb.append(",");
                        }
                        param.setParam(sb.toString());
                        return param;
                    }
                });
        Observable<FilterBean> cD = Observable.fromIterable(verticalFilterAdapter.getData())
                .filter(new Predicate<FilterBean>() {
                    @Override
                    public boolean test(@NonNull FilterBean filterBean) throws Exception {
                        return filterBean.isSelected();
                    }
                })
                .filter(new Predicate<FilterBean>() {
                    @Override
                    public boolean test(@NonNull FilterBean filterBean) throws Exception {
                        return filterBean.getType() == FilterType.PEOPERTY;
                    }
                });

        Observable<SearchFilterParam> D = cD.flatMap(new Function<FilterBean, ObservableSource<List<FilterItem>>>() {
            @Override
            public ObservableSource<List<FilterItem>> apply(@NonNull FilterBean filterBean) throws Exception {
                return Observable.fromIterable(filterBean.getDataSet()).filter(new Predicate<FilterItem>() {
                    @Override
                    public boolean test(@NonNull FilterItem filterItem) throws Exception {
                        return filterItem.isSelected();
                    }
                }).toList().toObservable();
            }
        })
                .zipWith(cD, new BiFunction<List<FilterItem>, FilterBean, PeoperFilter>() {
                    @Override
                    public PeoperFilter apply(@NonNull List<FilterItem> filterItems, @NonNull FilterBean filterBean) throws Exception {
                        PeoperFilter filter = new PeoperFilter();
                        filter.setName(filterBean.getName());
                        filter.setValues((ArrayList<FilterItem>) filterItems);
                        return filter;
                    }
                })
                .toList()
                .toObservable()
                .map(new Function<List<PeoperFilter>, SearchFilterParam>() {
                    @Override
                    public SearchFilterParam apply(@NonNull List<PeoperFilter> peoperFilters) throws Exception {
                        SearchFilterParam param = new SearchFilterParam();
                        param.setType(FilterType.PEOPERTY);
                        StringBuilder sb = new StringBuilder();
                        for (PeoperFilter filter : peoperFilters) {
                            sb.append(filter.getName());
                            for (FilterItem item : filter.getValues()) {
                                Log.d(TAG, "big name" + filter.getName());
                                Log.d(TAG, "small name" + item.getName());
                                sb.append("_");
                                sb.append(item.getName());
                            }
                            sb.append(",");
                        }
                        Log.d(TAG, "peoperty");
                        param.setParam(sb.toString());
                        return param;
                    }
                });
        Observable.concatArray(A, B, C, D, E)
                .toList()
                .toObservable()
                .map(new Function<List<SearchFilterParam>, SearchGoodsParam>() {
                    @Override
                    public SearchGoodsParam apply(@NonNull List<SearchFilterParam> searchFilterParams) throws Exception {
                        SearchGoodsParam.DataBean data = (SearchGoodsParam.DataBean) param_raw.getData().clone();
                        Log.d(TAG, searchFilterParams.toString());
                        for (SearchFilterParam filterParam : searchFilterParams) {
                            if (filterParam.getType() == FilterType.BRAND) {
                                data.setBrandId(filterParam.getParam());
                            } else if (filterParam.getType() == FilterType.CATEGORY) {
                                data.setClass3Id(filterParam.getParam());
                            } else if (filterParam.getType() == FilterType.PRICE) {
                                data.setMinPrice(filterParam.getParam());
                                data.setMaxPrice(filterParam.getParam2());
                            } else {
                                data.setPropertyList(filterParam.getParam());
                            }
                        }
                        data.setPageNo(1);
                        data.setAvailable(param.getData().getAvailable());
                        data.setSortType(param.getData().getSortType());
                        param.setData(data);
                        return param;
                    }
                })
                .flatMap(new Function<SearchGoodsParam, ObservableSource<SearchGoods>>() {
                    @Override
                    public ObservableSource<SearchGoods> apply(@NonNull SearchGoodsParam searchGoodsParam) throws Exception {
                        return fastSearchSevice.searchGoodsApi(action,gson.toJson(searchGoodsParam));
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<SearchGoods>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        disposableScrollUp();
                        if(goodRecAdapter.getEmptyView() != null){
                            ViewGroup group = (ViewGroup) goodRecAdapter.getEmptyView();
                            if(group.getChildAt(0) != null){
                                group.getChildAt(0).setVisibility(View.GONE);
                            }
                        }
                        goodRecAdapter.getData().clear();
                        goodRecAdapter.notifyDataSetChanged();
                        dialog.setCancelable(false);
                        dialog.show(getFragmentManager(),"dialog");
                    }

                    @Override
                    public void onNext(@NonNull SearchGoods searchGoods) {
                        dialog.dismiss();
                        totalPage = searchGoods.getData().getTotalPages();
                        goodRecAdapter.setNewData(searchGoods.getData().getGoodsList());
                        if (searchGoods.getData().getGoodsList().size() == 0) {
                            goodRecAdapter.setEmptyView(R.layout.empty_view);
                        }
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        dialog.dismiss();
                        Log.d(TAG, e.toString());
                        goodRecAdapter.setEmptyView(retryView);
                        printErr(e);
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    private void disposableScrollUp() {

    }

    private void clearStatus() {
        hasStockCb.setChecked(true);
        selectedIcon.setVisibility(View.GONE);
        pricedefMax.clearFocus();
        pricedefMin.clearFocus();
        pricedefMax.getText().clear();
        pricedefMin.getText().clear();
        Observable<FilterBean> A = Observable.fromIterable(horizontalFilterAdapter.getData());
        Observable<FilterBean> B = Observable.fromIterable(verticalFilterAdapter.getData());
        Observable.concat(A, B)
                .doOnNext(new Consumer<FilterBean>() {
                    @Override
                    public void accept(@NonNull FilterBean filterBean) throws Exception {
                        filterBean.setSelected(false);
                        filterBean.setSelectedName("");
                    }
                })
                .flatMap(new Function<FilterBean, ObservableSource<FilterItem>>() {
                    @Override
                    public ObservableSource<FilterItem> apply(@NonNull FilterBean filterBean) throws Exception {
                        return Observable.fromIterable(filterBean.getDataSet());
                    }
                })
                .doOnNext(new Consumer<FilterItem>() {
                    @Override
                    public void accept(@NonNull FilterItem filterItem) throws Exception {
                        filterItem.setSelected(false);
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<FilterItem>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onNext(@NonNull FilterItem filterItem) {
                        if (!filterItem.isAllchoseFlag()) {
                            filterItem.setSelected(false);
                        } else {
                            filterItem.setSelected(true);
                        }
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        Log.d(TAG, e.toString());
                    }

                    @Override
                    public void onComplete() {
                        boolean hasCatetory = false;
                        for (FilterBean tmpBean : verticalFilterAdapter.getData()) {
                            if (tmpBean.getType() == FilterType.CATEGORY) {
                                hasCatetory = true;
                                break;
                            }
                        }
                        if (hasCatetory) {
                            categoryChange();
                        }
                        if (verticalFilterAdapter.getData().size() != 0) {
                            verticalFilterAdapter.getOnItemClickListener().onItemClick(verticalFilterAdapter, null, 0);
                        }
                        horizontalFilterAdapter.notifyDataSetChanged();
                        verticalFilterAdapter.notifyDataSetChanged();
                        verticalFilterItemAdapter.notifyDataSetChanged();
                        Log.d(TAG, "clear up");
                    }
                });
    }

    private void changePeoperty(final FilterItem item) {
        Observable<ArrayList<PropertyListBean>> A = Observable.just(rawFilterRes)
                .filter(new Predicate<SearchFilterRes>() {
                    @Override
                    public boolean test(@NonNull SearchFilterRes searchFilterRes) throws Exception {
                        return !item.isSelected();
                    }
                })
                .map(new Function<SearchFilterRes, ArrayList<PropertyListBean>>() {
                    @Override
                    public ArrayList<PropertyListBean> apply(@NonNull SearchFilterRes searchFilterRes) throws Exception {
                        return (ArrayList<PropertyListBean>) searchFilterRes.getData().getPropertyList();
                    }
                });
        Observable<ArrayList<PropertyListBean>> C = Observable.fromIterable(peopertyOfCates)
                .filter(new Predicate<PeopertyOfCate>() {
                    @Override
                    public boolean test(@NonNull PeopertyOfCate peopertyOfCate) throws Exception {
                        Log.d(TAG, "test" + !item.isSelected());
                        return item.isSelected();
                    }
                })
                .filter(new Predicate<PeopertyOfCate>() {
                    @Override
                    public boolean test(@NonNull PeopertyOfCate peopertyOfCate) throws Exception {
                        return peopertyOfCate.getCateId().equals(item.getId());
                    }
                })
                .map(new Function<PeopertyOfCate, ArrayList<PropertyListBean>>() {
                    @Override
                    public ArrayList<PropertyListBean> apply(@NonNull PeopertyOfCate peopertyOfCate) throws Exception {
                        return peopertyOfCate.getDataSet();
                    }
                });

        Observable.concat(A, C)
                .flatMap(new Function<ArrayList<PropertyListBean>, ObservableSource<PropertyListBean>>() {
                    @Override
                    public ObservableSource<PropertyListBean> apply(@NonNull ArrayList<PropertyListBean> propertyListBeen) throws Exception {
                        return Observable.fromIterable(propertyListBeen);
                    }
                })
                .map(new Function<PropertyListBean, FilterBean>() {
                    @Override
                    public FilterBean apply(@NonNull PropertyListBean propertyListBean) throws Exception {
                        FilterBean bean = new FilterBean();
                        bean.setType(FilterType.PEOPERTY);
                        bean.setName(propertyListBean.getName());
                        for (String attr : propertyListBean.getValue()) {
                            FilterItem item = new FilterItem();
                            item.setName(attr);
                            bean.addItem(item);
                        }
                        return bean;
                    }
                })
                .doOnNext(new Consumer<FilterBean>() {
                    @Override
                    public void accept(@NonNull FilterBean filterBean) throws Exception {
                        filterHelper.addAllChoseItem(filterBean);
                        horizontalFilterAdapter.getData().add(filterBean);
                        verticalFilterAdapter.getData().add(filterBean);
                    }
                })
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<FilterBean>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onNext(@NonNull FilterBean filterBean) {
                        Log.d(TAG, filterBean.getName());

                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        Log.d(TAG, e.toString());

                    }

                    @Override
                    public void onComplete() {
                        Log.d(TAG, "peopertyChange");
                        horizontalFilterAdapter.notifyDataSetChanged();
                        verticalFilterAdapter.notifyDataSetChanged();
                    }
                });
    }

    private void removePeoperty() {
        for (int i = 0; i < horizontalFilterAdapter.getData().size(); i++) {
            if (horizontalFilterAdapter.getData().get(i).getType() == FilterType.PEOPERTY) {
                horizontalFilterAdapter.getData().remove(i);
                --i;
            }
        }
        for (int i = 0; i < verticalFilterAdapter.getData().size(); i++) {
            if (verticalFilterAdapter.getData().get(i).getType() == FilterType.PEOPERTY) {
                verticalFilterAdapter.getData().remove(i);
                --i;
            }
        }
    }


    @OnClick(R.id.backbtn)
    public void onViewClicked() {
        finish();
    }

    @OnClick(R.id.searchbar)
    public void onViewClick() {
        Intent it = new Intent(this, SearchActivity.class);
        startActivity(it);
    }

    @OnClick({R.id.chosengerenal, R.id.chosehotsale, R.id.chosenew, R.id.choseprice})
    public void onViewClicked(final View view) {
        SearchGoodsParam.DataBean bean = null;
        try {
            bean = (SearchGoodsParam.DataBean) param.getData().clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        switch (view.getId()) {
            case R.id.chosengerenal:
                bean.setSortType("0");
                break;
            case R.id.chosehotsale:
                bean.setSortType("1");
                break;
            case R.id.chosenew:
                bean.setSortType("2");
                break;
            case R.id.choseprice:
                if (bean.getSortType().equals("3")) {
                    bean.setSortType("-3");
                } else {
                    bean.setSortType("3");
                }
                break;
        }
        bean.setPageNo(1);
        param_use.setData(bean);
        Observable.just(param_use)
                .flatMap(new Function<SearchGoodsParam, ObservableSource<SearchGoods>>() {
                    @Override
                    public ObservableSource<SearchGoods> apply(@NonNull SearchGoodsParam bean) throws Exception {
                        return fastSearchSevice.searchGoodsApi(action,gson.toJson(bean));
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(new Consumer<Disposable>() {
                    @Override
                    public void accept(@NonNull Disposable disposable) throws Exception {
                        dialog.setCancelable(false);
                        dialog.show(getFragmentManager(),"dialog");
                    }
                })
                .flatMap(new Function<SearchGoods, ObservableSource<SearchGoods.DataBean>>() {
                    @Override
                    public ObservableSource<SearchGoods.DataBean> apply(@NonNull final SearchGoods searchGoods) throws Exception {
                        return Observable.create(new ObservableOnSubscribe<SearchGoods.DataBean>() {
                            @Override
                            public void subscribe(@NonNull ObservableEmitter<SearchGoods.DataBean> e) throws Exception {
                                if (searchGoods.getErrcode() != 1) {
                                    e.onError(new ServiceMsgException(searchGoods.getMsg()));
                                }
                                if (searchGoods.getData() != null && searchGoods.getData().getGoodsList() != null) {
                                    e.onNext(searchGoods.getData());
                                    e.onComplete();
                                } else {
                                    e.onError(new ServiceDataException());
                                }
                            }
                        });
                    }
                })
                .doFinally(new Action() {
                    @Override
                    public void run() throws Exception {
                        dialog.dismiss();
                    }
                })
                .subscribe(new Consumer<SearchGoods.DataBean>() {
                    @Override
                    public void accept(@NonNull SearchGoods.DataBean dataBean) throws Exception {
                        totalPage = dataBean.getTotalPages();
                        goodRecAdapter.setNewData(dataBean.getGoodsList());
                        if (dataBean.getGoodsList().size() == 0) {
                            goodRecAdapter.setEmptyView(R.layout.empty_view);
                        }
                        updateChosenStatus(view);
                        param.setData(param_use.getData());
                        Log.v(TAG, "success");
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(@NonNull Throwable throwable) throws Exception {
                        Toasty.error(SearchGoodActivity.this, getString(R.string.netconnect_exception)).show();
                        Log.v(TAG, throwable.toString());
                    }
                }, new Action() {
                    @Override
                    public void run() throws Exception {
                        Log.v(TAG, "complete");
                    }
                }, new Consumer<Disposable>() {
                    @Override
                    public void accept(@NonNull Disposable disposable) throws Exception {
                        disposableScrollUp();
                        compositeDisposable.add(disposable);
                    }
                });
    }

    private void updateChosenStatus(View view) {
        switch (view.getId()) {
            case R.id.chosengerenal:
                view.setBackgroundResource(R.drawable.allshop_search_goodlist_gerenal_pressed);
                view.setClickable(false);
                chosehotsale.setClickable(true);
                chosehotsale.setBackgroundResource(R.drawable.allshop_search_goodlist_salenew_normal);
                chosenew.setClickable(true);
                chosenew.setBackgroundResource(R.drawable.allshop_search_goodlist_newgood_normal);
                choseprice.setClickable(true);
                choseprice.setBackgroundResource(R.drawable.allshop_search_goodlist_price_normal);
                break;
            case R.id.chosehotsale:
                view.setBackgroundResource(R.drawable.allshop_search_goodlist_salenew_pressed);
                view.setClickable(false);
                chosenew.setBackgroundResource(R.drawable.allshop_search_goodlist_newgood_normal);
                chosenew.setClickable(true);
                choseprice.setBackgroundResource(R.drawable.allshop_search_goodlist_price_normal);
                choseprice.setClickable(true);
                chosengerenal.setBackgroundResource(R.drawable.allshop_search_goodlist_gerenal_normal);
                chosengerenal.setClickable(true);
                break;
            case R.id.chosenew:
                view.setBackgroundResource(R.drawable.allshop_search_goodlist_newgood_pressed);
                view.setClickable(false);
                chosehotsale.setBackgroundResource(R.drawable.allshop_search_goodlist_salenew_normal);
                chosehotsale.setClickable(true);
                choseprice.setBackgroundResource(R.drawable.allshop_search_goodlist_price_normal);
                choseprice.setClickable(true);
                chosengerenal.setBackgroundResource(R.drawable.allshop_search_goodlist_gerenal_normal);
                chosengerenal.setClickable(true);
                break;
            case R.id.choseprice:
                chosehotsale.setBackgroundResource(R.drawable.allshop_search_goodlist_salenew_normal);
                chosehotsale.setClickable(true);
                chosenew.setBackgroundResource(R.drawable.allshop_search_goodlist_newgood_normal);
                chosenew.setClickable(true);
                chosengerenal.setBackgroundResource(R.drawable.allshop_search_goodlist_gerenal_normal);
                chosengerenal.setClickable(true);
                choseprice.setClickable(true);
                if (param_use.getData().getSortType().equals("3")) {
                    choseprice.setBackgroundResource(R.drawable.allshop_search_goodlist_price_pressed_up);
                } else {
                    choseprice.setBackgroundResource(R.drawable.allshop_search_goodlist_price_pressed_down);
                }
                break;
        }
    }

    @OnClick(R.id.tochosebtn)
    public void chosenFilter() {
        drawerLayout.openDrawer(Gravity.RIGHT);
    }

    @OnClick(R.id.backtotop)
    public void onViewClickedTotop() {
        goodsWrap.smoothScrollToPosition(0);
    }

    @OnClick(R.id.backtomain)
    public void onViewClickedTomain() {
        Intent it = new Intent(this, AllShopActivity.class);
        startActivity(it);
    }

    @OnClick(R.id.vertical_confirm)
    public void onViewClickedVerticalConfirm(View view) {
        drawerLayout.closeDrawer(Gravity.RIGHT);
    }

    @OnClick(R.id.vertical_reset)
    public void onViewClickedVerticalCancel(View view) {
        clearStatus();
    }

    @OnClick(R.id.goshopcat)
    public void onViewClickedGoshopcat() {
        Intent it = new Intent();
        it.setClass(SearchGoodActivity.this, ShopCartActivity.class);
        startActivity(it);
    }

    public static class FilterHelper {
        private SearchFilterRes.DataBean searchFilterRes;

        public FilterHelper(SearchFilterRes searchFilterRes) {
            this.searchFilterRes = searchFilterRes.getData();
        }

        public ArrayList<FilterBean> invoke() {
            ArrayList<FilterBean> res = new ArrayList<>();
            if (searchFilterRes.getBrandList() != null && searchFilterRes.getBrandList().size() != 0 && searchFilterRes.getBrandList().size() != 1) {
                FilterBean brandBean = new FilterBean();
                brandBean.setType(FilterType.BRAND);
                brandBean.setName("品牌");
                for (SearchFilterRes.DataBean.BrandListBean bean : searchFilterRes.getBrandList()) {
                    FilterItem item = new FilterItem();
                    item.setId(bean.getId());
                    item.setName(bean.getName());
                    brandBean.addItem(item);
                }
                addAllChoseItem(brandBean);
                res.add(brandBean);
            }
            if (searchFilterRes.getClass3List() != null && searchFilterRes.getClass3List().size() != 0 && searchFilterRes.getClass3List().size() != 1) {
                FilterBean cateBean = new FilterBean();
                cateBean.setType(FilterType.CATEGORY);
                cateBean.setName("分类");
                for (SearchFilterRes.DataBean.Class3ListBean bean : searchFilterRes.getClass3List()) {
                    FilterItem item = new FilterItem();
                    item.setName(bean.getName());
                    item.setId(bean.getId());
                    cateBean.addItem(item);
                }
                addAllChoseItem(cateBean);
                res.add(cateBean);
            }
            if (searchFilterRes.getPropertyList() != null && searchFilterRes.getPropertyList().size() != 0) {
                for (PropertyListBean BiBean : searchFilterRes.getPropertyList()) {
                    FilterBean peopertyBean = new FilterBean();
                    peopertyBean.setType(FilterType.PEOPERTY);
                    peopertyBean.setName(BiBean.getName());
                    if (BiBean.getValue().size() > 1) {
                        for (String attr : BiBean.getValue()) {
                            FilterItem item = new FilterItem();
                            item.setName(attr);
                            peopertyBean.addItem(item);
                        }
                        addAllChoseItem(peopertyBean);
                        res.add(peopertyBean);
                    }
                }
            }
            return res;
        }

        private void addAllChoseItem(final FilterBean bean) {
            FilterItem item = new FilterItem();
            item.setName("全部");
            item.setSelected(true);
            item.setAllchoseFlag(true);
            bean.addItem(0, item);
        }

        private ArrayList<FilterBean> getprice(ArrayList<FilterBean> res) {
            if (searchFilterRes.getPriceList() != null && searchFilterRes.getPriceList().size() != 0) {
                FilterBean priceBean = new FilterBean();
                priceBean.setType(FilterType.PRICE);
                priceBean.setName("价格");
                for (SearchFilterRes.DataBean.PriceListBean bean : searchFilterRes.getPriceList()) {
                    FilterItem item = new FilterItem();
                    if (bean.getMaxPrice() != null) {
                        item.setMaxPrice(bean.getMaxPrice());
                    }
                    item.setMinPrice(bean.getMinPrice());
                    if (bean.getMaxPrice() != null) {
                        item.setName(bean.getMinPrice() + " --- " + bean.getMaxPrice());
                    } else {
                        item.setName(bean.getMinPrice());
                    }
                    priceBean.addItem(item);
                }
                addAllChoseItem(priceBean);
                if (res.size() > 2) {
                    res.add(2, priceBean);
                } else {
                    res.add(priceBean);
                }
            }
            return res;
        }

        public ArrayList<FilterBean> invokeWithPrice() {
            return getprice(invoke());
        }
    }

    private void printErr(@NonNull Throwable e) {
//        for (int index = 0; index < e.getStackTrace().length; index++) {
//            Log.d(TAG, e.getStackTrace()[index].toString());
//        }
    }

}

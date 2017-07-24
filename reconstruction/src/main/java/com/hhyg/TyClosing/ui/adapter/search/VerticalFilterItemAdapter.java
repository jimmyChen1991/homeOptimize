package com.hhyg.TyClosing.ui.adapter.search;


import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.hhyg.TyClosing.R;
import com.hhyg.TyClosing.entities.search.FilterBean;
import com.hhyg.TyClosing.entities.search.FilterItem;

/**
 * Created by user on 2017/6/16.
 */

public class VerticalFilterItemAdapter extends BaseQuickAdapter<FilterItem,BaseViewHolder>{
    private FilterBean bean;

    public VerticalFilterItemAdapter() {
        super(R.layout.adapter_filteritem);
    }

    @Override
    protected void convert(BaseViewHolder helper, FilterItem item) {
        helper.setText(R.id.filterName,item.getName())
                .setVisible(R.id.selected_icon,item.isSelected());
    }

    public FilterBean getFilterBean() {
        return bean;
    }

    public void setFilterBean(FilterBean bean) {
        this.bean = bean;
    }
}

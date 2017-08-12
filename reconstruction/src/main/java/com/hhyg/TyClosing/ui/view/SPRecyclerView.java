package com.hhyg.TyClosing.ui.view;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;

/**
 * Created by user on 2017/8/9.
 */

public class SPRecyclerView extends RecyclerView{

    private OnScrollChangedListener mListener;

    public OnScrollChangedListener getmListener() {
        return mListener;
    }

    public void setmListener(OnScrollChangedListener mListener) {
        this.mListener = mListener;
    }

    public SPRecyclerView(Context context) {
        super(context);
    }

    public SPRecyclerView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public SPRecyclerView(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);
        if(mListener != null){
            mListener.onScrollChanged(t);
        }
    }

    public interface OnScrollChangedListener{
        void onScrollChanged(int y);
    }
}

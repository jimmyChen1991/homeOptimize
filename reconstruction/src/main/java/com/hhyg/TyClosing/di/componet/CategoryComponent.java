package com.hhyg.TyClosing.di.componet;

import com.hhyg.TyClosing.di.module.CatetoryModule;
import com.hhyg.TyClosing.di.scope.PerActivity;
import com.hhyg.TyClosing.ui.CategoryActivity;

import dagger.Component;

/**
 * Created by user on 2017/8/17.
 */
@PerActivity
@Component(dependencies = {CommonNetParamComponent.class,ApplicationComponent.class},modules = CatetoryModule.class)
public interface CategoryComponent {
    void inject(CategoryActivity activity);
}

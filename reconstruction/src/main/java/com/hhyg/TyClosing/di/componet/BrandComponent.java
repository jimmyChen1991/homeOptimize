package com.hhyg.TyClosing.di.componet;

import com.hhyg.TyClosing.di.module.BrandModule;
import com.hhyg.TyClosing.di.scope.PerActivity;
import com.hhyg.TyClosing.ui.BrandActivity;

import dagger.Component;

/**
 * Created by user on 2017/8/9.
 */
@PerActivity
@Component(dependencies = {ApplicationComponent.class,CommonNetParamComponent.class},modules = BrandModule.class)
public interface BrandComponent {
    void inject(BrandActivity activity);
}

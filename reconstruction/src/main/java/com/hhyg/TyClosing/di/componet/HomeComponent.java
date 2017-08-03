package com.hhyg.TyClosing.di.componet;

import com.hhyg.TyClosing.di.module.HomeModule;
import com.hhyg.TyClosing.di.scope.PerActivity;
import com.hhyg.TyClosing.ui.HomeActivity;

import dagger.Component;

/**
 * Created by user on 2017/8/2.
 */
@PerActivity
@Component(dependencies = {ApplicationComponent.class,CommonNetParamComponent.class},modules = HomeModule.class)
public interface HomeComponent {
    void inject(HomeActivity activity);
}

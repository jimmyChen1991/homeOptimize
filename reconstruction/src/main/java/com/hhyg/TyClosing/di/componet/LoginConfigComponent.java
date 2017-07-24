package com.hhyg.TyClosing.di.componet;

import com.hhyg.TyClosing.di.module.LoginConfigModule;
import com.hhyg.TyClosing.di.scope.PerActivity;
import com.hhyg.TyClosing.ui.SalerLoginActivity;

import dagger.Component;

/**
 * Created by user on 2017/6/27.
 */
@PerActivity
@Component(dependencies = {ApplicationComponent.class},modules = LoginConfigModule.class)
public interface LoginConfigComponent {
    void inject(SalerLoginActivity aty);
}

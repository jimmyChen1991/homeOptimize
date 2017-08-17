package com.hhyg.TyClosing.di.componet;

import com.hhyg.TyClosing.di.module.PaySuccessModule;
import com.hhyg.TyClosing.di.scope.PerActivity;
import com.hhyg.TyClosing.ui.PaySuccessActivity;

import dagger.Component;

/**
 * Created by user on 2017/8/14.
 */
@PerActivity
@Component(dependencies = {CommonNetParamComponent.class,ApplicationComponent.class},modules = PaySuccessModule.class)
public interface PaySuccessComponent {
    void inject(PaySuccessActivity aty);
}

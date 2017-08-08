package com.hhyg.TyClosing.config;

public class UrlConfig4Active implements NetrulConfig{

	@Override
	public String getIndexUrl() {
		return "http://wangwangcommonapi.test89.mianshui365.net/index.php";
	}

	@Override
	public String getServiceUrl() {
		return "http://exps.mianshui365.net/api/MSService.php";
	}

}

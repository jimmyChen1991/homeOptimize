package com.hhyg.TyClosing.config;

public class UrlConfig4Online implements NetrulConfig{
	@Override
	public String getIndexUrl() {
		return "http://commonapi_v4.mianshui365.com/index.php";
	}

	@Override
	public String getServiceUrl() {
		return "http://exps4.mianshui365.com/api/MSService.php";
	}

}

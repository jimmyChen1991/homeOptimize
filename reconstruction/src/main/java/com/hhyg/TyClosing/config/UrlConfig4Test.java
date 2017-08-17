package com.hhyg.TyClosing.config;

public class UrlConfig4Test implements NetrulConfig{
	@Override
	public String getIndexUrl() {
		return "http://wangwangcommonapi.mianshui365.net/index.php";
	}

	@Override
	public String getServiceUrl() {
		return "http://wangwangexps.mianshui365.net/api/MSService.php";
		
	}
}

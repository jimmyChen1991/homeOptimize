package com.hhyg.TyClosing.global;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Environment;
import android.provider.Settings;
import android.util.Log;

import com.hhyg.TyClosing.R;
import com.hhyg.TyClosing.di.componet.ApplicationComponent;
import com.hhyg.TyClosing.di.componet.DaggerApplicationComponent;
import com.hhyg.TyClosing.di.module.NetModule;
import com.hhyg.TyClosing.entities.search.HotsearchWord;
import com.hhyg.TyClosing.info.PickUpInfo;
import com.hhyg.TyClosing.log.CrashHandler;
import com.hhyg.TyClosing.log.Logger;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.DefaultRefreshHeaderCreater;
import com.scwang.smartrefresh.layout.api.RefreshHeader;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.constant.SpinnerStyle;
import com.scwang.smartrefresh.layout.header.ClassicsHeader;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import io.realm.Realm;
import io.realm.RealmConfiguration;

//import com.activeandroid.ActiveAndroid;

/**
 * 全局唯一Application，单例
 */
public class MyApplication extends Application implements HttpUtil {

    private static MyApplication mInstance;
    public static final String SDPATH = Environment
            .getExternalStorageDirectory().getAbsolutePath();
    public final String BASE_LOCAL_PATH = SDPATH + "/hhyg/";
    public final String BASE_LOCAL_PATH_PIC = BASE_LOCAL_PATH + "pic/";
    private final String BOUNDARY = java.util.UUID.randomUUID().toString();
    private final String PREFIX = "--", LINEND = "\r\n";
    private final String MULTIPART_FROM_DATA = "multipart/form-data";
    private final String CHARSET = "UTF-8";
    private final String END_STR = PREFIX + BOUNDARY + PREFIX + LINEND;
    public final int MSG_TYPE_ERROR = 1;
    public final int MSG_TYPE_VALUE = 0;
    private String AndroidId;
    public String Imei;
    public String UUID;
    public String SaleId;
	private String SpecialCode;
    private String hotSearchWord;
    public final int SUC = 0x1000;
    private final int NET_TIMEOUT = 30*1000;
    private ApplicationComponent component;

//    static {
//        //设置全局的Header构建器
//        SmartRefreshLayout.setDefaultRefreshHeaderCreater(new DefaultRefreshHeaderCreater() {
//            @Override
//            public RefreshHeader createRefreshHeader(Context context, RefreshLayout layout) {
//                layout.setPrimaryColorsId(R.color.result_view, android.R.color.white);//全局设置主题颜色
//                return new ClassicsHeader(context).setSpinnerStyle(SpinnerStyle.Translate);//指定为经典Header，默认是 贝塞尔雷达Header
//            }
//        });
//    }
    public static MyApplication GetInstance() {
        return mInstance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;
//        ImageHelper.LoaderInit(this);
        Realm.init(this);
        RealmConfiguration configuration = new RealmConfiguration.Builder().build();
        Realm.setDefaultConfiguration(configuration);
        CrashHandler crashHandler = CrashHandler.getInstance();
        crashHandler.init(getApplicationContext());
        SharedPreferences pref =this.getSharedPreferences(this.getPackageName(), MODE_PRIVATE);
        SaleId = pref.getString("SaleMan", "");
        component = DaggerApplicationComponent.builder().netModule(new NetModule()).build();
    }
    @Override
    public void onTerminate() {
        super.onTerminate();
    }

    public ApplicationComponent getComponent() {
        return component;
    }

    public void setHotSearchWord(HotsearchWord hotsearchWord) {
        if(hotsearchWord == null || hotsearchWord.getRecommend() == null || hotsearchWord.getRecommend().size() == 0){
            this.hotSearchWord = null;
        }else{
            StringBuilder sb = new StringBuilder();
            int size = hotsearchWord.getRecommend().size();
            for (int index = 0 ; index < size ; index ++){
                HotsearchWord.RecommendBean bean  = hotsearchWord.getRecommend().get(index);
                sb.append(bean.getHotword());
                if(index != size -1){
                    sb.append(" | ");
                }
            }
            this.hotSearchWord = sb.toString();
        }
    }

    public String getHotSearchWord() {
        return hotSearchWord;
    }

    public void setUserSelectAir(PickUpInfo p){
        if(p == null)
            return;
        SharedPreferences pref = getSharedPreferences(getPackageName(), MODE_PRIVATE);
        SharedPreferences.Editor eidt = pref.edit();
        eidt.putString("name", p.name);
        eidt.putInt("id", p.id);
        eidt.putInt("prepareTime", p.prepareTime);
        eidt.commit();
    }

    public PickUpInfo getUserSelectAir(){
        SharedPreferences pref =this.getSharedPreferences(this.getPackageName(), MODE_PRIVATE);
        int id = pref.getInt("id",0);
        int prepareTime = pref.getInt("prepareTime",0);
        String name = pref.getString("name", "");
        PickUpInfo info = new PickUpInfo();
        info.id = id;
        info.prepareTime = prepareTime;
        info.name = name;
        return info;
    }

    public void setSpecialCode(String code) {
		SpecialCode = code;
		SharedPreferences pref = getSharedPreferences(getPackageName(), MODE_PRIVATE);
    	SharedPreferences.Editor eidt = pref.edit();
    	eidt.putString("SpecialCode", SpecialCode);
    	eidt.commit();
	}
    public String getSpecialCode() {
    	if(SpecialCode == null){
    		SharedPreferences pref =this.getSharedPreferences(this.getPackageName(), MODE_PRIVATE);
    		SpecialCode = pref.getString("SpecialCode", "");
    	}
		return SpecialCode;
	}
    public String getAndroidId(){
    	if(AndroidId == null || AndroidId.equals("")){
    	    AndroidId = Settings.System.getString(getContentResolver(), Settings.System.ANDROID_ID);
    	}
    	return AndroidId;
    }
    @Override
    public void post(final String actionUrl, final String valueStr,
                     final INetWorkCallBack call){

        final class NetworkThread extends Thread {
            @Override
            public void run() {
                // TODO Auto-generated method stub
                super.run();
                String uuidStr = java.util.UUID.randomUUID().toString();

                String strSendDataToLog = " url is : " + actionUrl + "  valueStr is : " + valueStr;
                Log.v("net", actionUrl +"\n" + valueStr);
                StringBuilder resultData = new StringBuilder("");
                String resultStr = "";
                URL uri = null;
                try {
                    uri = new URL(actionUrl);
                } catch (MalformedURLException e1) {
                    Logger.GetInstance().Net(Logger.GetInstance().LEVEL_ERROR,strSendDataToLog + " exception is : " + e1.getMessage());
                    e1.printStackTrace();
                }
                InputStreamReader in = null;
                HttpURLConnection conn = null;
                DataOutputStream outStream = null;
                String strNet = "post:  url is " + actionUrl + "\npost data is " + valueStr;
                int responseCode = 0;
                try {
                    //构造请求
                    conn = (HttpURLConnection) uri.openConnection();
                    conn.setReadTimeout(NET_TIMEOUT+20*1000);
                    conn.setConnectTimeout(NET_TIMEOUT+10*1000);
                    conn.setDoInput(true);// 允许输入
                    conn.setDoOutput(true);// 允许输出
                    conn.setUseCaches(false);
                    conn.setRequestMethod("POST"); // Post方式
                    conn.setRequestProperty("connection", "keep-alive");
                    conn.setRequestProperty("Charsert", "UTF-8");
                    conn.setRequestProperty("Content-Type", MULTIPART_FROM_DATA
                            + ";boundary=" + BOUNDARY);
                    // 首先组拼文本类型的参数
                    final StringBuilder sb = new StringBuilder();
                    sb.append(PREFIX);
                    sb.append(BOUNDARY);
                    sb.append(LINEND);
                    sb.append("Content-Disposition: form-data; name=\""
                            + "parameter" + "\"" + LINEND);
                    sb.append("Content-Type: text/plain; charset="
                            + CHARSET + LINEND);
                    sb.append("Content-Transfer-Encoding: 8bit" + LINEND);
                    sb.append(LINEND);
                    sb.append(valueStr);//valueStr为json参数串
                    sb.append(LINEND);
                    strNet += "\n   header success";
                    //获取输出流
                    outStream = new DataOutputStream(conn.getOutputStream());
                    outStream.write((sb.toString() + END_STR).getBytes());
                    outStream.flush();
                    responseCode = conn.getResponseCode();
                    Logger.GetInstance().Net(Logger.GetInstance().LEVEL_INFO,uuidStr + "  responseCode" + responseCode);
                    strNet += "\n    response code :" + responseCode + "\n      ";
                    // 得到响应
                    in = new InputStreamReader(conn.getInputStream());
                    BufferedReader buffer = new BufferedReader(in);
                    String str = null;
                    while ((str = buffer.readLine()) != null) {
                        resultData.append(str);
                    }
                    strNet += "response string is :  " + resultData + "\n";
                    Logger.GetInstance().Net(Logger.GetInstance().LEVEL_INFO,uuidStr + "  Recive Msg=>"+resultData.toString());
                    Log.v("net", strNet);
                }
                catch (Exception e) {
                    Logger.GetInstance().Net(Logger.GetInstance().LEVEL_ERROR,
                            strSendDataToLog + " responseCode code is : "  + responseCode + " resultData is : " +
                                    resultData +  " exception is : " + e.toString());

                    e.printStackTrace();
                    if(responseCode == 500){
                    	call.PostProcess(MSG_TYPE_ERROR, NetErrorState.SERVICE_ERR);
                    	return;
                    }
					call.PostProcess(MSG_TYPE_ERROR, NetErrorState.NET_ERROR);
                    Log.e("myapplication post", "4097 error occured!!");
                    return;
                } 
                finally {
                    //IO流需要放在finally中关闭
                    if (conn != null) {
                	    conn.disconnect();
                    }
                    try {
                        if(in != null) {
                            in.close();
                        }
                        if(outStream != null) {
                            outStream.close();
                        }
                    }catch (IOException io){
                        Logger.GetInstance().Net(Logger.GetInstance().LEVEL_ERROR,
                                strSendDataToLog + " responseCode code is : "  + responseCode + " resultData is : " +
                                        resultData +  " exception is : " + io.getMessage());
                    }
                }
                resultStr = resultData.toString();
                if (resultStr == null) {
                    call.PostProcess(MSG_TYPE_ERROR,NetErrorState.RESULT_NULL);
                    Logger.GetInstance().Net(Logger.GetInstance().LEVEL_ERROR,
                            strSendDataToLog + " resultData is null responseCode code is : "  + responseCode);
                }
                //响应回调
                call.PostProcess(MSG_TYPE_VALUE, resultStr);
            }
        }
        Thread workThread = new NetworkThread();
        boolean isConnected = IsConnectingToInternet();
        if (isConnected) {
        	workThread.start();
        } else {
            Logger.GetInstance().Net(Logger.GetInstance().LEVEL_ERROR, "net not open");
            call.PostProcess(MSG_TYPE_ERROR, NetErrorState.NET_ERROR);
        }
    }

    public boolean IsConnectingToInternet() {
        ConnectivityManager connectivity = (ConnectivityManager) this
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivity != null) {
            NetworkInfo[] info = connectivity.getAllNetworkInfo();
            if (info != null) {
                for (int idx = 0; idx < info.length; idx++) {
                    if (info[idx].getState() == NetworkInfo.State.CONNECTED) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

	public void setAndroidId(String androidId) {
		AndroidId = androidId;
	}
}

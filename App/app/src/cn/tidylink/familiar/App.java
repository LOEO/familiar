package cn.tidylink.familiar;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;
import cn.tidylink.familiar.db.DataBaseHelper;
import cn.tidylink.familiar.service.LocationService;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by LT on 2015-05-10.
 */
public class App extends Application  {
    public static final String SERVER_CFG = "SERVER_CFG";
    public static final String USER_CFG = "USER_CFG";
    public static final String COOKIE = "COOKIE";
    private static App instance;
    private static Context context;
    private String ip = "";
    private String port = "";
    private String baseUrl = "";
    private SharedPreferences sp;
    private Map<String,Object> sharedData = new HashMap<>();

    public Map<String, Object> getSharedData() {
        return sharedData;
    }

    public void setSharedData(Map<String, Object> sharedData) {
        this.sharedData = sharedData;
    }

    public static App getInstance(){
        if(instance == null){
            instance = new App();
        }
        return instance;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getPort() {
        return port;
    }

    public void setPort(String port) {
        this.port = port;
    }

    public String getBaseUrl() {
        return baseUrl;
    }

    public void setBaseUrl(String baseUrl) {
        this.baseUrl = baseUrl;
    }

    public String getCookie(){
        sp = getSharedPreferences(COOKIE,MODE_PRIVATE);
        return sp.getString("cookie","");
    }

    public void setCookie(String cookie){
        sp = getSharedPreferences(COOKIE,MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString("cookie",cookie);
        editor.commit();
    }

    public void syncCookie(){
        CookieSyncManager.createInstance(this);
        CookieManager cookieManager = CookieManager.getInstance();
        cookieManager.setAcceptCookie(true);
        Log.i("-------cookie--------", this.getCookie());
        //cookieManager.setCookie(getBaseUrl(), "username="+this.curUser.getEmpNo());
        CookieSyncManager.getInstance().sync();
    }

    public static Context getContext() {
        return context;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        this.instance = this;
        this.context = getApplicationContext();
        sp = getSharedPreferences(SERVER_CFG, MODE_PRIVATE);
        this.ip = sp.getString("ip","");
        this.port = sp.getString("port","");
        this.baseUrl = "http://" + ip + ":"+port;
/*        Intent intent = new Intent(this, LocationService.class);
        startService(intent);*/
    }
}

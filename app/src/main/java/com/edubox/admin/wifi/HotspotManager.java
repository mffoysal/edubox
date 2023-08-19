package com.edubox.admin.wifi;

import android.content.Context;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiManager;
import android.util.Log;
import java.lang.reflect.Method;

public class HotspotManager {

    private Context context;
    private WifiManager wifiManager;

    public HotspotManager(Context context) {
        this.context = context;
        wifiManager = (WifiManager) context.getApplicationContext().getSystemService(Context.WIFI_SERVICE);
    }

    public boolean startHotspot(String ssid, String password) {
        if (isWifiApEnabled()) {
            Log.e("HotspotManager", "Hotspot is already enabled.");
            return false;
        }

        WifiConfiguration wifiConfiguration = new WifiConfiguration();
        wifiConfiguration.SSID = ssid;
        wifiConfiguration.preSharedKey = password;

        try {
            Method method = wifiManager.getClass().getMethod("setWifiApEnabled", WifiConfiguration.class, boolean.class);
            return (boolean) method.invoke(wifiManager, wifiConfiguration, true);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean stopHotspot() {
        if (!isWifiApEnabled()) {
            Log.e("HotspotManager", "Hotspot is already disabled.");
            return false;
        }

        try {
            Method method = wifiManager.getClass().getMethod("setWifiApEnabled", WifiConfiguration.class, boolean.class);
            return (boolean) method.invoke(wifiManager, null, false);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    private boolean isWifiApEnabled() {
        try {
            Method method = wifiManager.getClass().getMethod("isWifiApEnabled");
            return (boolean) method.invoke(wifiManager);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}

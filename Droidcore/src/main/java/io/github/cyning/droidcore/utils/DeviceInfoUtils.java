package io.github.cyning.droidcore.utils;

import android.content.Context;
import android.os.Build;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.util.DisplayMetrics;
import android.view.WindowManager;
import java.math.BigInteger;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.security.SecureRandom;
import java.util.Enumeration;
import java.util.Locale;

/**
 * @author Cyning
 * @since 2015.10.21
 * Time    9:55 AM
 * Desc    <p>跟设备相关的工具类</p>
 */

public class DeviceInfoUtils {

    public static final String Androd_Id = "com.neteease.android.id";

    public static String getOSVersion() {
        return Build.VERSION.RELEASE;
    }

    public static String getDevice() {
        return Build.MODEL;
    }

    public static String getResolution(Context context) {
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics metrics = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(metrics);
        return metrics.widthPixels + "x" + metrics.heightPixels;
    }

    public static String getCarrier(Context context) {
        TelephonyManager manager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        return manager.getNetworkOperatorName();
    }

    public static String getImei(Context context) {
        TelephonyManager manager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        return manager.getDeviceId();
    }

    public static String getLocale() {
        Locale locale = Locale.getDefault();
        return locale.getLanguage() + "_" + locale.getCountry();
    }

    private static String generateAndroidId(Context mContext) {
        String android_id = Settings.Secure.getString(mContext.getContentResolver(), "android_id");
        if (android_id == null || android_id.equals("9774d56d682e549c") || android_id.length() < 15) {
            SecureRandom random = new SecureRandom();
            android_id = (new BigInteger(64, random)).toString(16);
        }
        android_id = Md5Utils.md5(android_id);
        SharedPreferUtisl.putString(mContext, Androd_Id, "android_id", android_id);
        return android_id;
    }

    /**
     * 获取Android
     * <p></p>
     * http://blog.csdn.net/billpig/article/details/6728573
     * @param mContext
     *
     * @return
     */
    public static String getAndroidId(Context mContext) {
        String android_id = SharedPreferUtisl.getString(mContext, Androd_Id, "android_id");//先从本地读取
        if (android_id == null || android_id.equals("") || android_id.equals("null")) {//没有就生成
            android_id = generateAndroidId(mContext);
        }
        return android_id;
    }

    /**
     * 是不是虚拟机
     * @param context
     *
     * @return
     */
    public static boolean isEmulator(Context context) {
        boolean result = false;
        String android_id = Settings.Secure.getString(context.getContentResolver(), "android_id");
        if (android_id != null && android_id.equals("9774d56d682e549c")) {
            result = true;
        }
        return result;
    }

    public static String getIP(Context context) {
        Object ip = null;

        try {
            Enumeration e = NetworkInterface.getNetworkInterfaces();
            NetworkInterface inf = (NetworkInterface)e.nextElement();
            Enumeration enumAddress = inf.getInetAddresses();

            while(enumAddress.hasMoreElements()) {
                InetAddress in = (InetAddress)enumAddress.nextElement();
                if(!in.isLinkLocalAddress()) {
                    return in.getHostAddress();
                }
            }
        } catch (Exception var6) {
            var6.printStackTrace();
        }

        return (String)ip;
    }

}

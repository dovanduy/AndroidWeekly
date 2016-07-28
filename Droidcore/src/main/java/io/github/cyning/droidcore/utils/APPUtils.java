package io.github.cyning.droidcore.utils;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.text.TextUtils;

/**
 * @author cyning
 */
public class APPUtils {

    /**
     * 返回应用版本号210 version=210
     * @param context
     *
     * @return
     */
    public static String getAppVersionNO(Context context) {
        String strVersion = getAppVersion(context, context.getPackageName());
        return strVersion.replace(".", "");
    }

    /**
     * 根据packageName包名的应用获取应用版本名称,如未安装返回null
     * @param context
     * @param packageName
     *
     * @return
     */
    public static String getAppVersion(Context context, String packageName) {
        if (TextUtils.isEmpty(packageName)) {
            return null;
        }
        try {
            PackageInfo info = context.getPackageManager()
                                      .getPackageInfo(packageName, 0);
            return info.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            return "";
        }
    }

    /**
     * 得到当前应用的版本号
     * @param context
     *
     * @return
     */
    public static String getAppVersion(Context context) {
      return   getAppVersion(context,context.getPackageName());
    }
    public static int getAppVerCode(Context context) {
        // 取得版本号
        PackageManager manager = context.getPackageManager();
        PackageInfo info = null;
        try {
            info = manager.getPackageInfo(context.getPackageName(), 0);

          return    info.versionCode;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

}

package io.github.cyning.mobilenews.common;

import android.widget.Toast;
import io.github.cyning.droidcore.utils.ResUtils;
import io.github.cyning.mobilenews.HotArticleApplication;

/**
 * @author Cyning
 * @since 2016.03.21
 * Time    10:27 PM
 * Desc    <p>toast的工具类</p>
 */

public class ToastUtils {

    public static void show(String tips){
        Toast.makeText(HotArticleApplication.getInstance(),tips,Toast.LENGTH_SHORT);
    }

    public static void show(int tips){
        String msg = ResUtils.getString(HotArticleApplication.getInstance(),tips);
        Toast.makeText(HotArticleApplication.getInstance(),msg,Toast.LENGTH_SHORT);
    }

}

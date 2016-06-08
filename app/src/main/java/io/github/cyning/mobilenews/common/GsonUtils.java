package io.github.cyning.mobilenews.common;

import com.google.gson.Gson;

/**
 * @author Cyning
 * @since 2016.03.20
 * Time    10:07 PM
 * Desc    <p>类/接口描述</p>
 */

public class GsonUtils {

    private static GsonUtils mInstance = null;

    private Gson gson;

    private GsonUtils (){
        gson = new Gson();

    }
    public static GsonUtils getInstance() {
        if (mInstance == null) {
            synchronized (GsonUtils.class) {
                if (mInstance == null) {
                    mInstance = new GsonUtils();
                }
            }
        }
        return mInstance;
    }

    public Gson getGson() {
        return gson;
    }
}

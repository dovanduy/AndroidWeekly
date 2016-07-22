package io.github.cyning.mobilenews.thrid;

import io.github.cyning.mobilenews.base.OkHttpAPIClient;
import io.github.cyning.mobilenews.hotarticle.client.HotArticleClient;
import io.github.cyning.mobilenews.hotarticle.client.IHotArticleAPI;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * @author Cyning
 * @since 2016.07.22
 * Time    下午2:54
 * Desc    <p>类/接口描述</p>
 */

public class APPClient {


    private static APPClient mInstance = null;
    IThridAPP iThridAPP;

    private APPClient (){
        Retrofit retrofit0 = new Retrofit.Builder()
                .baseUrl("http://zixun.html5.qq.com/")
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .client(OkHttpAPIClient.getInstance().getClient())
                .build();

        iThridAPP=retrofit0.create(IThridAPP.class);
    }
    public static APPClient getInstance() {
        if (mInstance == null) {
            synchronized (APPClient.class) {
                if (mInstance == null) {
                    mInstance = new APPClient();
                }
            }
        }
        return mInstance;
    }

    public IThridAPP get3partAPI() {
        return iThridAPP;
    }
}

package io.github.cyning.mobilenews.hotarticle.client;

import io.github.cyning.mobilenews.base.OkHttpAPIClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * @author Cyning
 * @since 2016.03.03
 * Time    1:24 PM
 * Desc    <p>类/接口描述</p>
 */

public class HotArticleClient {

    private static HotArticleClient mInstance = null;
    final IHotArticleAPI hotArticleAPI;

    private HotArticleClient (){
        Retrofit retrofit0 = new Retrofit.Builder()
            .baseUrl("http://zixun.html5.qq.com/")
            .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .client(OkHttpAPIClient.getInstance().getClient())
            .build();

        hotArticleAPI=retrofit0.create(IHotArticleAPI.class);
    }
    public static HotArticleClient getInstance() {
        if (mInstance == null) {
            synchronized (HotArticleClient.class) {
                if (mInstance == null) {
                    mInstance = new HotArticleClient();
                }
            }
        }
        return mInstance;
    }

    public IHotArticleAPI getHotArticleAPI() {
        return hotArticleAPI;
    }
}

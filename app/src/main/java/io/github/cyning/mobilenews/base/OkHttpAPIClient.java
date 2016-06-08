package io.github.cyning.mobilenews.base;

import io.github.cyning.mobilenews.BuildConfig;
import java.io.IOException;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;

/**
 * @author Cyning
 * @since 2016.03.03
 * Time    2:01 PM
 * Desc    <p>类/接口描述</p>
 */

public class OkHttpAPIClient {

    Interceptor headerInterceptor = new Interceptor() {
        @Override
        public Response intercept(Chain chain) throws IOException {
            Request newRequest = chain.request().newBuilder()
                .addHeader("X-LC-Id", "gR91O36BPimUdyXdt9qNq01d-gzGzoHsz")
                .addHeader("X-LC-Key", "hoOc0q3Bn4NIU9YObBQY0VsL")
                .addHeader("User-Agent","Mozilla/5.0 (Macintosh; Intel Mac OS X 10_11_1) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/48.0.2564.116 Safari/537.36")
                .
                    build();
            return chain.proceed(newRequest);
        }
    };




    private static OkHttpAPIClient mInstance = null;
    OkHttpClient client ;

    private OkHttpAPIClient(){

        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        HttpLoggingInterceptor logging = null;
        logging = new HttpLoggingInterceptor();
        if (BuildConfig.DEBUG) {

            logging.setLevel(HttpLoggingInterceptor.Level.BODY);

        }else {
            logging.setLevel(HttpLoggingInterceptor.Level.NONE);
        }
        client = builder.addInterceptor(headerInterceptor)
            .addInterceptor(logging).build();
    }
    public static OkHttpAPIClient getInstance() {
        if (mInstance == null) {
            synchronized (OkHttpAPIClient.class) {
                if (mInstance == null) {
                    mInstance = new OkHttpAPIClient();
                }
            }
        }
        return mInstance;
    }

    public OkHttpClient getClient() {
        return client;
    }
}

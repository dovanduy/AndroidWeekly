package io.github.cyning.mobilenews.cartoon.client;

import io.github.cyning.mobilenews.base.OkHttpAPIClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by cyning on 4/9/16.
 */
public class CartoonClient {

  private static CartoonClient mInstance = null;
  final ICartoonAPI cartoonAPI;

  private CartoonClient (){
    Retrofit retrofit0 = new Retrofit.Builder()
        .baseUrl("http://zixun.html5.qq.com/")
        .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
        .addConverterFactory(GsonConverterFactory.create())
        .client(OkHttpAPIClient.getInstance().getClient())
        .build();

    cartoonAPI=retrofit0.create(ICartoonAPI.class);
  }
  public static CartoonClient getInstance() {
    if (mInstance == null) {
      synchronized (CartoonClient.class) {
        if (mInstance == null) {
          mInstance = new CartoonClient();
        }
      }
    }
    return mInstance;
  }

  public ICartoonAPI getCartoonAPI() {
    return cartoonAPI;
  }
}

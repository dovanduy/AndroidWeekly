package io.github.cyning.mobilenews.hotarticle.client;

import io.github.cyning.mobilenews.hotarticle.model.HotArticleData;
import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

/**
 * @author Cyning
 * @since 2016.03.03
 * Time    12:56 PM
 * Desc    <p>类/接口描述</p>
 */

public interface IHotArticleAPI {


    @GET("https://api.leancloud.cn/1.1/classes/WeekArticle")
    Observable<HotArticleData> getHotArticle(@Query("order") String order,@Query("where") String where);
}

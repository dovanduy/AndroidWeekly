package io.github.cyning.mobilenews.cartoon.client;

import io.github.cyning.mobilenews.cartoon.model.CartoonComicWrapper;
import io.github.cyning.mobilenews.cartoon.model.CartoonListWrapper;
import retrofit2.http.GET;
import retrofit2.http.Path;
import rx.Observable;

/**
 * @author Cyning
 * @since 2016.04.09
 * Time    10:12 PM
 * Desc    <p>卡通的API接口</p>
 */

public interface ICartoonAPI {

    /**
     * 获取日本漫画的所有的动画片
     * @param page
     * @return
     */
    @GET("http://mobilev3.ac.qq.com/Classify/comicClassifyDetailV2/listcnt/15/classify_id/5/page/{page}/sort_type/2/")
    public Observable<CartoonListWrapper> loadCartoonList(@Path("page") String page);


    /**
     * 得到某一个动画片的章节、介绍、推荐等情况
     * @param comicID
     * @return
     */
    @GET("http://mobilev3.ac.qq.com/Comic/ComicDetailV3/comic_id/{comic_id}/")
    public Observable<CartoonComicWrapper> loadComicItemList(@Path("comic_id")String comicID);


}

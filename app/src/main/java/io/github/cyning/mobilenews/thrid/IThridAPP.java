package io.github.cyning.mobilenews.thrid;

import retrofit2.http.GET;
import rx.Observable;

/**
 * @author Cyning
 * @since 2016.07.22
 * Time    下午2:37
 * Desc    <p>类/接口描述</p>
 */

public interface IThridAPP {

    @GET(WebInterface.PIC)
    Observable<ZhihuLanuch> reqLaunchPic();


}

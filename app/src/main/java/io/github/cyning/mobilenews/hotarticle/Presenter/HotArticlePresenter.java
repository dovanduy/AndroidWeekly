package io.github.cyning.mobilenews.hotarticle.Presenter;

import io.github.cyning.droidcore.log.LayzLog;
import io.github.cyning.droidcore.utils.Md5Utils;
import io.github.cyning.greendao.HotArticle;
import io.github.cyning.mobilenews.HotArticleApplication;
import io.github.cyning.mobilenews.Key;
import io.github.cyning.mobilenews.base.Load;
import io.github.cyning.mobilenews.hotarticle.client.HotArticleClient;
import io.github.cyning.mobilenews.database.db.DBManager;
import io.github.cyning.mobilenews.hotarticle.base.IArticlePresenter;
import io.github.cyning.mobilenews.hotarticle.base.IHotArticleView;
import io.github.cyning.mobilenews.hotarticle.model.HotArticleData;
import java.util.List;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func0;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * @author Cyning
 * @since 2016.03.03
 * Time    6:07 PM
 * Desc    <p>类/接口描述</p>
 */

public class HotArticlePresenter implements IArticlePresenter {

    IHotArticleView<List<HotArticle>> mView;

    /**
     * 得到热文的借口
     */
    @Override public void loadArt(String seriNO, final boolean isFirst, final String tabId) {
        String order = "-seriNo";
        String where = String.format("{\"seriNo\":{\"$lt\":\"%s\"}}",seriNO);
        String sign = Key.getSign();
        Observable<HotArticleData> observable = HotArticleClient.getInstance().getHotArticleAPI().getHotArticle(order,where,sign);

        observable.map(new Func1<HotArticleData, List<HotArticle>>() {
            @Override public List<HotArticle> call(HotArticleData hotArticleData) {

                List<HotArticle> hotArticles    = hotArticleData.getResults();
                return hotArticles;
            }
        })
            .doOnNext(new Action1<List<HotArticle>>() {
                @Override public void call(List<HotArticle> hotArticleData) {
                    cacheHotArticle(hotArticleData);
                }
            })
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(new Subscriber<List<HotArticle>>() {

                @Override public void onStart() {
                    super.onStart();
                    mView.loadStart(isFirst);
                }

                @Override public void onCompleted() {

                }

                @Override public void onError(Throwable e) {
                    LayzLog.e(e, "%s", "error");
                    mView.loadComplete(new Load(Load.FAIL, Load.UPDATE), null, null);
                }

                @Override public void onNext(List<HotArticle> hotArticles) {

                    int deal = isFirst ? Load.UPDATE : Load.LOADMORE;
                    mView.loadComplete(new Load(Load.SUCCESS, deal), hotArticles, null);
                }
            });
    }



    private void cacheHotArticle(List<HotArticle> hotArticleData) {
        DBManager.getInstance(HotArticleApplication.getInstance(), DBManager.DEFAULT_NAME)
            .getDaoSession()
            .getHotArticleDao()
            .insertOrReplaceInTx(hotArticleData);
    }

    @Override public void setup(IHotArticleView mview) {
        mView = mview;
    }

    @Override public void loadCacheList() {
        Observable.defer(new Func0<Observable<List<HotArticle>>>() {
            @Override public Observable<List<HotArticle>> call() {
                return Observable.just(getCacheList());
            }

            private List<HotArticle> getCacheList() {
                return DBManager.getInstance(HotArticleApplication.getInstance(), DBManager.DEFAULT_NAME)
                    .getDaoSession()
                    .getHotArticleDao()
                    .loadAll();
            }
        })
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(new Subscriber<List<HotArticle>>() {
                @Override public void onCompleted() {

                }

                @Override public void onError(Throwable e) {

                }

                @Override public void onNext(List<HotArticle> hotArticles) {
                    mView.loadComplete(new Load(Load.SUCCESS, Load.INIT), hotArticles, null);
                }
            });
    }
}

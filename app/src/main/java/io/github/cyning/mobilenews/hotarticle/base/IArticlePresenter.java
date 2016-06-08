package io.github.cyning.mobilenews.hotarticle.base;

import io.github.cyning.mobilenews.base.IPresenter;

/**
 * @author Cyning
 * @since 2016.03.03
 * Time    5:51 PM
 * Desc    <p>类/接口描述</p>
 */

public interface IArticlePresenter extends IPresenter {

    public void setup(IHotArticleView mview);

    public void loadArt(String timeStap, boolean isFirst, String tabID);

    public void loadCacheList();
}

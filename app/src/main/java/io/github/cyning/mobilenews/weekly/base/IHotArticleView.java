package io.github.cyning.mobilenews.weekly.base;

import io.github.cyning.mobilenews.base.IView;
import io.github.cyning.mobilenews.base.Load;

/**
 * @author Cyning
 * @since 2016.03.03
 * Time    5:54 PM
 * Desc    <p>类/接口描述</p>
 */
public interface IHotArticleView<T> extends IView {

    public void loadStart(boolean isFirstLoad);

    public  void  loadComplete(Load load,T data,Object msg);

}

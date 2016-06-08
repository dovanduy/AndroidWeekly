package io.github.cyning.mobilenews.cartoon.base;

import io.github.cyning.mobilenews.base.Load;
import io.github.cyning.mobilenews.cartoon.model.CartoonListWrapper;

/**
 * @author Cyning
 * @since 2016.04.10
 * Time    12:38 AM
 * Desc    <p>类/接口描述</p>
 */

public interface ICartoonView {

    public void loadStart(boolean isFirst);

    public void loadComplete(Load load, boolean fromStart,CartoonListWrapper data, Object extra);
}

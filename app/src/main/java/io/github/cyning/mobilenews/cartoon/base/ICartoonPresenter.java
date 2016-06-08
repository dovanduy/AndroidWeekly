package io.github.cyning.mobilenews.cartoon.base;

/**
 * Created by cyning on 4/9/16.
 */
public interface ICartoonPresenter {

  public void setupView(ICartoonView iCartoonView);

  public void loadCartoomList(int page);

  public void querryCartoomCache();


}

package io.github.cyning.mobilenews.cartoon.presenter;

import io.github.cyning.mobilenews.base.Load;
import io.github.cyning.mobilenews.base.NESubsciber;
import io.github.cyning.mobilenews.cartoon.base.ICartoonPresenter;
import io.github.cyning.mobilenews.cartoon.base.ICartoonView;
import io.github.cyning.mobilenews.cartoon.client.CartoonClient;
import io.github.cyning.mobilenews.cartoon.model.CartoonListWrapper;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by cyning on 4/9/16.
 */
public class CartoonListPresenter implements ICartoonPresenter  {

    public static final int  FirstPage = 1;
    ICartoonView cartoonView;

    @Override
    public void setupView(ICartoonView iCartoonView) {
        cartoonView = iCartoonView;
    }

    @Override
    public void loadCartoomList(final int page) {
        CartoonClient.getInstance().getCartoonAPI().loadCartoonList(Math.max(page,FirstPage) + "")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new NESubsciber<CartoonListWrapper>() {
                    @Override
                    public void onStart() {
                        super.onStart();
                        if (cartoonView != null){
                            cartoonView.loadStart(Math.max(page,FirstPage) == FirstPage);
                        }
                    }

                    @Override
                    public void onNext(CartoonListWrapper cartoonListWrapper) {
                        super.onNext(cartoonListWrapper);
                        if (cartoonView != null){
                            cartoonView.loadComplete(new Load(Load.UPDATE,Load.SUCCESS),Math.max(page,FirstPage) == FirstPage,cartoonListWrapper,null);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        if (cartoonView != null){
                            cartoonView.loadComplete(new Load(Load.UPDATE,Load.FAIL),Math.max(page,FirstPage) == FirstPage,null,e);
                        }
                    }
                });

    }

    @Override
    public void querryCartoomCache() {

    }
}

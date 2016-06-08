package io.github.cyning.mobilenews.cartoon.ui;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import io.github.cyning.droidcore.ui.BaseFragment;
import io.github.cyning.mobilenews.R;
import io.github.cyning.mobilenews.base.Load;
import io.github.cyning.mobilenews.cartoon.base.ICartoonPresenter;
import io.github.cyning.mobilenews.cartoon.base.ICartoonView;
import io.github.cyning.mobilenews.cartoon.model.CartoonInfo;
import io.github.cyning.mobilenews.cartoon.model.CartoonListWrapper;
import io.github.cyning.mobilenews.cartoon.presenter.CartoonListPresenter;
import io.github.cyning.mobilenews.common.CollectionsUtils;
import io.github.cyning.mobilenews.widgets.DividerLine;
import io.github.cyning.mobilenews.widgets.SuperRecycleView;

/**
 * Created by cyning on 4/9/16.
 */
public class CartoonListFragment extends BaseFragment  implements ICartoonView{

  private boolean hasExit = false;

  @Bind(R.id.recViewList) SuperRecycleView recViewList;
  @Bind(R.id.swRefreshLayout) SwipeRefreshLayout swRefreshLayout;

  private CartoonListAdapter listAdapter = null;

  private ArrayList<CartoonInfo> cartoonInfos = new ArrayList<>();


  private ICartoonPresenter cartoonPresenter = null;

  @Override protected int getRootViewId() {
    return R.layout.cartoon_fragment;
  }

  @Override protected void setupViews(View view, Bundle savedInstanceState) {
    super.setupViews(view, savedInstanceState);
  }

  @Override public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    cartoonPresenter = new CartoonListPresenter();
    cartoonPresenter.setupView(this);
  }

  @Override public View onCreateView(LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState) {
    View rootView = super.onCreateView(inflater, container, savedInstanceState);
    ButterKnife.bind(this, rootView);
    return rootView;
  }

  @Override
  public void onViewCreated(View view, Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);
    if (listAdapter == null){
      listAdapter = new CartoonListAdapter(cartoonInfos,getNeActivity());
      recViewList.setAdapter(listAdapter);

      recViewList.setOnLoadMoreListener(new SuperRecycleView.OnLoadMoreListener() {
        @Override public void onMoreAsked(int overallItemsCount, int itemsBeforeMore, int maxLastVisiblePosition) {
          if (cartoonPresenter != null) {
            cartoonPresenter.loadCartoomList(1);
          }
        }
      });
      LinearLayoutManager manager = new LinearLayoutManager(getNeActivity());
      manager.setOrientation(LinearLayoutManager.VERTICAL);
      recViewList.setLayoutManager(manager);

      DividerLine dividerLine = new DividerLine(DividerLine.VERTICAL);
      dividerLine.setSize(1);
      dividerLine.setColor(getResources().getColor(R.color.color_div));
      recViewList.addItemDecoration(dividerLine);
      recViewList.setAdapter(listAdapter);
    }
  }

  @Override public void onActivityCreated(Bundle savedInstanceState) {
    super.onActivityCreated(savedInstanceState);
    if (!hasExit){
      cartoonPresenter.loadCartoomList(1);
    }
  }

  @Override
  public void loadStart(final boolean fromStart) {
    if (fromStart){
      swRefreshLayout.post(new Runnable() {
        @Override
        public void run() {
          if (swRefreshLayout != null){
            swRefreshLayout.setRefreshing(true);
          }
        }
      });
    }else {
      //loadMore
    }

  }

  @Override
  public void loadComplete(Load load, boolean fromStart,CartoonListWrapper data, Object extra) {

    if (isDestroyView ){
      return;
    }
     if (data != null ){
       if (data.isSuccess() && CollectionsUtils.hasEle(data.getData())){
          if (fromStart){
            cartoonInfos.clear();
            cartoonInfos.addAll(data.getData());
            listAdapter.notifyDataSetChanged();
          }else {
            int count = cartoonInfos.size();
            cartoonInfos.addAll(data.getData());
            listAdapter.notifyItemRangeInserted(count-1,cartoonInfos.size()-1);
          }
       }

     }

    if (swRefreshLayout != null){
      swRefreshLayout.setRefreshing(false);
    }

  }

  @Override public void onDestroyView() {
    super.onDestroyView();
    ButterKnife.unbind(this);
  }
}


package io.github.cyning.mobilenews.hotarticle.ui;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TabHost;
import butterknife.Bind;
import butterknife.ButterKnife;
import com.timehop.stickyheadersrecyclerview.StickyRecyclerHeadersDecoration;
import io.github.cyning.droidcore.ui.BaseFragment;
import io.github.cyning.droidcore.utils.DisplayUtil;
import io.github.cyning.greendao.HotArticle;
import io.github.cyning.androidweekly.R;
import io.github.cyning.mobilenews.base.Load;
import io.github.cyning.mobilenews.common.ToastUtils;
import io.github.cyning.mobilenews.hotarticle.Presenter.HotArticlePresenter;
import io.github.cyning.mobilenews.hotarticle.base.IArticlePresenter;
import io.github.cyning.mobilenews.hotarticle.base.IHotArticleView;
import io.github.cyning.mobilenews.hotarticle.model.HotArticleTabSpec;
import io.github.cyning.mobilenews.widgets.DividerLine;
import io.github.cyning.mobilenews.widgets.SuperRecycleView;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Cyning
 * @since 2016.03.03
 * Time    12:39 PM
 * Desc    <p>热文的fragment</p>
 */

public class HotArticleFragment extends BaseFragment
    implements IHotArticleView<List<HotArticle>>, SwipeRefreshLayout.OnRefreshListener {

    public static final String KEY_TAB_INFO = "tab_info";

    @Bind(R.id.swRefreshLayout) SwipeRefreshLayout swRefreshLayout;
    @Bind(R.id.recViewList) SuperRecycleView recViewList;

    HotAritcleAdapter hotAritcleAdapter;

    List<HotArticle> hotArticleList = new ArrayList<>();

    IArticlePresenter articlePresenter = null;

    HotArticleTabSpec tab;


    @Override public void onAttach(Activity activity) {
        super.onAttach(activity);
        articlePresenter = new HotArticlePresenter();
        articlePresenter.setup(this);
    }

    @Override public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override protected int getRootViewId() {
        return R.layout.hotarticle_fragment;
    }

    @Override protected void setupViews(View view, Bundle savedInstanceState) {
        super.setupViews(view, savedInstanceState);
        ButterKnife.bind(this, rootView);

        recViewList.setOnLoadMoreListener(new SuperRecycleView.OnLoadMoreListener() {
            @Override public void onMoreAsked(int overallItemsCount, int itemsBeforeMore, int maxLastVisiblePosition) {
                if (articlePresenter != null) {
                    articlePresenter.loadArt(hotArticleList.get(hotArticleList.size()-1).getSerNo(), false,"");
                }
            }
        });
        hotAritcleAdapter = new HotAritcleAdapter(hotArticleList, getNeActivity());
        LinearLayoutManager manager = new LinearLayoutManager(getNeActivity());
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        recViewList.setLayoutManager(manager);

        DividerLine dividerLine = new DividerLine(DividerLine.VERTICAL);
        dividerLine.setSize(DisplayUtil.dp2px(getNeActivity(),4));
        dividerLine.setColor(getResources().getColor(R.color.color_div));
        recViewList.addItemDecoration(dividerLine);
        recViewList.setAdapter(hotAritcleAdapter);


        final StickyRecyclerHeadersDecoration headersDecor = new StickyRecyclerHeadersDecoration(hotAritcleAdapter);
        recViewList.addItemDecoration(headersDecor);
        // Add decoration for dividers between list items
        //        mLiveRecycleView.addItemDecoration(new DividerDecoration(this));
        hotAritcleAdapter.registerAdapterDataObserver(new RecyclerView.AdapterDataObserver() {
            @Override public void onChanged() {
                headersDecor.invalidateHeaders();
            }
        });


        swRefreshLayout.setOnRefreshListener(this);

    }

    @Override public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        tab = (HotArticleTabSpec)getArguments().getSerializable(KEY_TAB_INFO);
        if (hasExist){
            articlePresenter.loadCacheList();
        }else {
            articlePresenter.loadArt(System.currentTimeMillis() + "", true,null);
        }
    }

    @Override public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);

    }

    @Override public void loadStart(boolean isFirstLoad) {
        if (isFirstLoad){
            swRefreshLayout.post(new Runnable() {
                @Override public void run() {
                    swRefreshLayout.setRefreshing(true);
                }
            });
        }
    }

    @Override public void loadComplete(Load load, List<HotArticle> data, Object msg) {

        if (isDestroyView){
            return;
        }
        hasExist = true;
        if (load.getState() == Load.SUCCESS) {
            if (load.getDeal() == Load.UPDATE) {
                hotArticleList.addAll(0, data);
            } else {
                hotArticleList.addAll(data);
                recViewList.setLoadingMore(false);
            }
            hotAritcleAdapter.notifyDataSetChanged();
        } else {
            if (load.getDeal() == Load.UPDATE) {
                ToastUtils.show("加载失败");
            }
        }
        swRefreshLayout.post(new Runnable() {
            @Override public void run() {
                if (swRefreshLayout != null){
                    swRefreshLayout.setRefreshing(false);
                }

            }
        });


    }

    @Override public void onRefresh() {
        if (articlePresenter != null) {
            articlePresenter.loadArt(System.currentTimeMillis() + "", true,"2");
        }
    }

    public static HotArticleFragment newInstance(HotArticleTabSpec tab) {
        Bundle args = new Bundle();
        args.putSerializable(KEY_TAB_INFO, tab);
        HotArticleFragment fragment = new HotArticleFragment();
        fragment.setArguments(args);
        return fragment;
    }
}

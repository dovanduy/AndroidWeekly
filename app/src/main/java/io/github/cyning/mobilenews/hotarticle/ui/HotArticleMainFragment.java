package io.github.cyning.mobilenews.hotarticle.ui;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import io.github.cyning.droidcore.ui.BaseFragment;
import io.github.cyning.androidweekly.R;
import io.github.cyning.mobilenews.hotarticle.model.HotArticleTabSpec;

/**
 * @author Cyning
 * @since 2016.03.12
 * Time    1:56 AM
 * Desc    <p>类/接口描述</p>
 */

public class HotArticleMainFragment extends BaseFragment {

    @Bind(R.id.tabTop)
    TabLayout tabTop;
    @Bind(R.id.vpContent)
    ViewPager vpContent;

    HotArticlePagerAdapter pagerAdapter = null;

    @Override
    protected int getRootViewId() {
        return R.layout.hotarticle_main_fragment;
    }

    @Override
    protected void setupViews(View view, Bundle savedInstanceState) {
        super.setupViews(view, savedInstanceState);
        ButterKnife.bind(this, view);

    }


    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        List<HotArticleTabSpec> tabSpecs = new ArrayList<>();

        if (pagerAdapter == null) {
            pagerAdapter = new HotArticlePagerAdapter(getChildFragmentManager(), tabSpecs);
        }

        vpContent.setAdapter(pagerAdapter);
        tabTop.setupWithViewPager(vpContent);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        ButterKnife.bind(this, rootView);
        return rootView;
    }
}

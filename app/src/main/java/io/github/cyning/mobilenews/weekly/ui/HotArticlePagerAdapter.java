package io.github.cyning.mobilenews.weekly.ui;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import io.github.cyning.mobilenews.common.CollectionsUtils;
import io.github.cyning.mobilenews.weekly.model.HotArticleTabSpec;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Cyning
 * @since 2016.03.12
 * Time    2:00 AM
 * Desc    <p>主页的PagerAdapter</p>
 */

public class HotArticlePagerAdapter extends FragmentPagerAdapter {
    public static final String TAG = "HotArticleFragment";
    private List<HotArticleTabSpec> tabSpecs;
    Map<String,HotArticleFragment> fragmentMaps = new HashMap<>();

    public HotArticlePagerAdapter(FragmentManager fm, List<HotArticleTabSpec> tabSpecs) {
        super(fm);
        this.tabSpecs = tabSpecs;
    }

    @Override public Fragment getItem(int position) {
        HotArticleTabSpec spec = tabSpecs.get(position);
        HotArticleFragment fragment = null;
        if (fragmentMaps.get(TAG+spec.getTabId()) == null){
            fragment =  HotArticleFragment.newInstance(spec);
        }else {
            fragment = fragmentMaps.get(TAG+spec.getTabId());
        }
        return fragment;

    }

    @Override public int getCount() {
        return CollectionsUtils.hasEle(tabSpecs)?tabSpecs.size():0;
    }

    @Override public CharSequence getPageTitle(int position) {
        return tabSpecs.get(position).getTabName();
    }
}

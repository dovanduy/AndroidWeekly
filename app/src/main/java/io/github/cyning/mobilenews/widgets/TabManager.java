package io.github.cyning.mobilenews.widgets;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import java.util.HashMap;

/**
 * Created by cyning on 4/9/16.
 */
public class TabManager {

  private final FragmentActivity mActivity;
  private final HashMap<String, TabInfo> mTabs = new HashMap<String, TabInfo>();
  private final int              mContainerId;
  TabInfo mLastTab;

  OnNETabChangeListener tabChangeListener;

  public TabManager(FragmentActivity mActivity, int mContainerId) {
    this.mActivity = mActivity;
    this.mContainerId = mContainerId;
  }

  static final class TabInfo {
    private final String   tag;
    private final Class<?> clss;
    private final Bundle args;
    private Fragment fragment;

    TabInfo(String _tag, Class<?> _class, Bundle _args) {
      tag = _tag;
      clss = _class;
      args = _args;
    }

    public Fragment getFragment() {
      return fragment;
    }
  }


  public TabManager addTab(String mTag, Class<?> clss, Bundle args) {
    String tag = mTag;

    TabInfo info = new TabInfo(tag, clss, args);

    // Check to see if we already have a fragment for this tab, probably
    // from a previously saved state. If so, deactivate it, because our
    // initial state is that a tab isn't shown.
    info.fragment = mActivity.getSupportFragmentManager().findFragmentByTag(tag);
    if (info.fragment != null && !info.fragment.isDetached()) {
      FragmentTransaction ft = mActivity.getSupportFragmentManager().beginTransaction();
      ft.hide(info.fragment);
      ft.commit();
    }

    mTabs.put(tag, info);
    return this;
  }

  public void onTabChange(String tabId){
    TabInfo newTab = mTabs.get(tabId);
    if (mLastTab != newTab) {
      FragmentTransaction ft = mActivity.getSupportFragmentManager().beginTransaction();
      if (mLastTab != null) {
        if (mLastTab.fragment != null) {
          ft.hide(mLastTab.fragment);
        }
      }
      if (newTab != null) {
        if (newTab.fragment == null) {
          newTab.fragment = Fragment.instantiate(mActivity, newTab.clss.getName(), newTab.args);
          ft.add(mContainerId, newTab.fragment, newTab.tag);
        } else {
          ft.show(newTab.fragment);
        }
      }

      mLastTab = newTab;
      ft.commit();
      mActivity.getSupportFragmentManager().executePendingTransactions();
      if (tabChangeListener != null){
        tabChangeListener.onTabChanged(tabId);
      }
    }
  }

  public TabManager setTabChangeListener(OnNETabChangeListener tabChangeListener) {
    this.tabChangeListener = tabChangeListener;
    return this;
  }

  public interface OnNETabChangeListener {
    public  void  onTabChanged(String tabId);
  }

}

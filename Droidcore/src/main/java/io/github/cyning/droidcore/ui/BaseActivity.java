package io.github.cyning.droidcore.ui;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import io.github.cyning.droidcore.log.LayzLog;
import io.github.cyning.droidcore.utils.StringUtils;
import io.github.cyning.droidcore.utils.ViewUtils;
import java.util.Stack;

/**
 * Author: cyning
 * Date  : 2015.06.08
 * Time  : 下午4:16
 * Desc  : Activity的基类
 */
public class BaseActivity extends AppCompatActivity {
    public boolean isDestroyed = false; // 记录是否已经移除Handler接受回调消息

    protected Intent mActvityIntent;

    protected FragmentManager mManager;

    private static Stack<BaseActivity> activityList = new Stack<>();

    @Override public void setContentView(View view) {
        super.setContentView(view);
    }

    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LayzLog.w("---- %s", "onCreate");
//        setTranslateBar();
        activityList.add(this);
        mManager = getSupportFragmentManager();

        getIntentData(savedInstanceState);
        setContentView();
        setupToolbar();
        setupViews();
    }

    private void setTranslateBar() {
        getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS
                    | WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.TRANSPARENT);
            window.setNavigationBarColor(Color.TRANSPARENT);
        }
    }

    @Override public void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        loadData();
    }

    protected void loadData() {

    }

    protected void setupToolbar() {

    }

    protected void getIntentData(Bundle savedInstanceState) {
        mActvityIntent = getIntent();
    }

    protected void setContentView() {
        int contentViewId = getRootViewId();
        if (contentViewId != 0) {
            setContentView(getRootViewId());
        }
    }

    protected int getRootViewId() {
        return 0;
    }

    protected void onBack() {
        onBackPressed();
    }

    protected void showBackIcon(boolean isShow) {
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(isShow);
            actionBar.setHomeButtonEnabled(isShow);
            actionBar.setDisplayShowHomeEnabled(isShow);
        }
    }

    /**
     * 显示ActionBar上面的返回键
     *
     * @deprecated
     */
    protected void showBackIcon() {
        showBackIcon(true);
    }

    /**
     * 显示ActionBar上面的返回键
     */
    protected void hideBackIcon() {
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(false);
            actionBar.setDisplayUseLogoEnabled(false);
        }
    }

    @Override public void setTitle(CharSequence title) {
        super.setTitle(title);
        showBackIcon(true);
    }

    public void setTitle(String mTitle, boolean isShowBack) {
        ActionBar actionBar = getNEActionBar();
        if (actionBar != null) {
            actionBar.setTitle(mTitle);
        }
        if (isShowBack) {
            showBackIcon();
        } else {
            hideBackIcon();
        }
    }

    @Override protected void onResume() {
        super.onResume();
        LayzLog.w("---- %s", "onResume");
    }

    @Override protected void onStart() {
        super.onStart();
        LayzLog.w("---- %s", "onStart");
    }

    public void setTitle(int mTitleID, boolean isShowBack) {
        ActionBar actionBar = getNEActionBar();
        if (actionBar != null) {
            actionBar.setTitle(mTitleID);
        }
        showBackIcon(isShowBack);
    }

    /**
     * return the {@link android.support.v7.app.ActionBar}  from the {@link android.support.v7.app.AppCompatActivity}
     */
    public ActionBar getNEActionBar() {
        return getSupportActionBar();
    }

    @Override public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            onBack();
            return true;
        }
        return true;
    }

    public void setupViews() {
    }

    public BaseActivity getNeActivity() {
        return this;
    }

    public <T extends View> T v(int id) {
        return ViewUtils.find(this, id);
    }

    @Override protected void onStop() {
        super.onStop();
        LayzLog.w("---- %s", "onStop");
    }

    @Override protected void onDestroy() {
        super.onDestroy();
        LayzLog.w("---- %s", "onDestroy");
        activityList.remove(this);
        isDestroyed = true;
    }

    String tag = null;

    public String getNeTag() {
        if (!StringUtils.hasText(tag)) {
            tag = this.getClass().getName();
        }
        return tag;
    }

    public static BaseActivity getTopActivity() {
        if (activityList.isEmpty()) {
            return null;
        }
        return activityList.peek();
    }

    public static boolean isActivityExist(Class activityClass) {
        if (activityList.isEmpty()) {
            return false;
        }
        for (BaseActivity activity : activityList) {
            return activity.getClass().equals(activityClass);
        }
        return false;
    }
}

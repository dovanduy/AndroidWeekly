package io.github.cyning.mobilenews.weekly.ui;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import butterknife.Bind;
import butterknife.ButterKnife;
import cyning.me.baseui.ProgressWebView;
import io.github.cyning.droidcore.utils.StringUtils;
import io.github.cyning.greendao.HotArticle;
import io.github.cyning.androidweekly.R;
import io.github.cyning.mobilenews.widgets.swipeback.app.SwipeBackActivity;

import static io.github.cyning.droidcore.log.LogTimber.w;

/**
 * @author Cyning
 * @since 2016.03.07
 * Time    11:42 PM
 * Desc    <p>热文的详情</p>
 */

public class HotDetailActivity extends SwipeBackActivity {

    public static final String KEY_HOT_ARTICLE = "Key_hot_article";
    HotArticle hotArticle;

    @Bind(R.id.toolbar) Toolbar            toolbar;

    @Bind(R.id.webContainer)
    SwipeRefreshLayout swipeRefreshLayout;


    ProgressWebView mWebView;

    @Override protected void setContentView() {
        super.setContentView();
        ButterKnife.bind(this);
    }

    @Override protected void getIntentData(Bundle savedInstanceState) {
        super.getIntentData(savedInstanceState);
        hotArticle = (HotArticle) getIntent().getSerializableExtra(KEY_HOT_ARTICLE);
    }

    @Override public void setupViews() {
        super.setupViews();

        mWebView = v(R.id.webview);
        mWebView.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);
        mWebView.getSettings().setJavaScriptEnabled(true);
        mWebView.setBackgroundColor(getResources().getColor(R.color.darkerWhite));
        mWebView.setWebViewClient(new WebViewClient(){

            //@Override
            //public boolean shouldOverrideUrlLoading(WebView view, String url) {
            //    return true;
            //}

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                swipeRefreshLayout.setRefreshing(true);
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                swipeRefreshLayout.setRefreshing(false);
            }
        });

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
               String url =  mWebView.getUrl();
                mWebView.loadUrl(url);
            }
        });
        loadWebUrl();


    }

    private void loadWebUrl() {
        if (hotArticle != null){
            String url = hotArticle.getLink();
            w("url %s", url);
            mWebView.loadUrl(url);
        }
    }

    @Override protected void setupToolbar() {
        super.setupToolbar();
        if (hotArticle != null){
            toolbar.setTitle("详情");
        }

        setSupportActionBar(toolbar);
        showBackIcon(true);
    }

    @Override protected int getRootViewId() {
        return R.layout.hotarticle_detail_activity;
    }

    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.share){

           share();

        }
        return super.onOptionsItemSelected(item);
    }

    private void share() {
        Intent sharingIntent = new Intent(Intent.ACTION_SEND);
        sharingIntent.setType("text/plain");
        if (StringUtils.hasText(hotArticle.getImgUrl())){
            Uri imageUri = Uri.parse("http://stacktoheap.com/images/stackoverflow.png");
            sharingIntent.setType("image/png");
            sharingIntent.putExtra(Intent.EXTRA_STREAM, imageUri);
        }
        sharingIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET);

        sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, hotArticle.getTitle());
        sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, mWebView.getUrl());
        startActivity(Intent.createChooser(sharingIntent,  getString(R.string.share_menu)));
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.hot_article_detail, menu);
        return super.onCreateOptionsMenu(menu);
    }

    public  static  void launch(Context activity, HotArticle hotArticle){
        Intent intent = new Intent();
        intent.setClass(activity,HotDetailActivity.class);
        intent.putExtra(KEY_HOT_ARTICLE,hotArticle);
        activity.startActivity(intent);
    }
}

package io.github.cyning.mobilenews;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import io.github.cyning.androidweekly.R;
import io.github.cyning.mobilenews.hotarticle.ui.HotArticleFragment;
import io.github.cyning.mobilenews.hotarticle.ui.HotArticleMainFragment;
import java.util.Arrays;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import io.github.cyning.droidcore.ui.BaseActivity;
import io.github.cyning.mobilenews.cartoon.ui.CartoonListFragment;
import io.github.cyning.mobilenews.widgets.TabManager;
import io.github.cyning.mobilenews.widgets.bottomNavigation.BottomNavigation;

public class MainActivity extends BaseActivity {

    public static final String TAB_HOT_ARITICLE = "TAB_HOT_ARITICLE";

    public static final String TAB_HOT_CARTOON = "TAB_HOT_CARTOON";





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        if (savedInstanceState == null){
            HotArticleFragment mainFragment = new HotArticleFragment();
            getSupportFragmentManager().beginTransaction().replace(R.id.main_content,mainFragment,"Main")
            .commitAllowingStateLoss();
        }


    }




    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }



}

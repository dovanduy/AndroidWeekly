package io.github.cyning.mobilenews;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import io.github.cyning.androidweekly.R;
import io.github.cyning.mobilenews.weekly.ui.HotArticleMainFragment;

import butterknife.ButterKnife;
import io.github.cyning.droidcore.ui.BaseActivity;

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
            HotArticleMainFragment mainFragment = new HotArticleMainFragment();
            getSupportFragmentManager().beginTransaction().replace(R.id.main_content,mainFragment,"Main")
            .commitAllowingStateLoss();
        }


    }




    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }



}

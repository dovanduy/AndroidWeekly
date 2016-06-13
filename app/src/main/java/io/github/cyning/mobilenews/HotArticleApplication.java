package io.github.cyning.mobilenews;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.stetho.Stetho;

import io.github.cyning.androidweekly.BuildConfig;
import io.github.cyning.droidcore.ui.BaseApplication;

/**
 * @author Cyning
 * @since 2016.03.07
 * Time    9:46 PM
 * Desc    <p>类/接口描述</p>
 */

public class HotArticleApplication extends BaseApplication {
    @Override public void onCreate() {
        super.onCreate();
        initFresco();
        initStetho();
        initConfigLog(BuildConfig.DEBUG);
    }

    private void initFresco() {
        Fresco.initialize(this);
    }

    private void initStetho() {
        Stetho.initialize(Stetho.newInitializerBuilder(this)
            .enableDumpapp(Stetho.defaultDumperPluginsProvider(this))
            .enableWebKitInspector(Stetho.defaultInspectorModulesProvider(this))
            .build());
    }
}

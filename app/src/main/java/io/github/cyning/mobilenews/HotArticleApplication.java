package io.github.cyning.mobilenews;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.stetho.Stetho;

import java.util.List;

import io.github.cyning.androidweekly.BuildConfig;
import io.github.cyning.droidcore.log.LayzLog;
import io.github.cyning.droidcore.ui.BaseApplication;
import io.github.cyning.droidcore.utils.APPUtils;
import io.github.cyning.droidcore.utils.RxSchedulers;
import io.github.cyning.mobilenews.base.NESubsciber;
import io.github.cyning.mobilenews.common.CollectionsUtils;
import io.github.cyning.mobilenews.hotarticle.client.HotArticleClient;
import io.github.cyning.mobilenews.hotarticle.model.PatchBean;

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


        checkPatch();
    }

    private void checkPatch() {

        String version = APPUtils.getAppVersion(getApplicationContext());
        String patchVersion = "0";
        String sign = Key.getSign();

        String where = String.format("{\"patchId\":{\"$gt\":\"%s\"},\"patchVersion\":{\"$in\":[\"%s\"]}}",patchVersion,version);
        HotArticleClient.getInstance().getHotArticleAPI()
                .checkPatch(where,sign).compose(RxSchedulers.<List<PatchBean>>io())
                .subscribe(new NESubsciber<List<PatchBean>>(){
                    @Override
                    public void onNext(List<PatchBean> patchBeans) {
                        if (CollectionsUtils.hasEle(patchBeans)){
                            LayzLog.e("patchBeans = %s", patchBeans);
                        }
                        super.onNext(patchBeans);
                    }
                });
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

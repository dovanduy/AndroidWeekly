package io.github.cyning.mobilenews;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;

import com.facebook.drawee.view.SimpleDraweeView;

import java.util.concurrent.TimeUnit;

import butterknife.Bind;
import butterknife.ButterKnife;
import io.github.cyning.androidweekly.R;
import io.github.cyning.droidcore.log.LayzLog;
import io.github.cyning.droidcore.ui.BaseActivity;
import io.github.cyning.droidcore.utils.RxSchedulers;
import io.github.cyning.mobilenews.base.NESubsciber;
import io.github.cyning.mobilenews.thrid.APPClient;
import io.github.cyning.mobilenews.thrid.ZhihuLanuch;
import rx.Observable;
import rx.Subscription;

/**
 * @author Cyning
 * @since 2016.07.22
 * Time    下午2:23
 * Desc    <p>类/接口描述</p>
 */

public class LaunchActivity extends BaseActivity {

    @Bind(R.id.ivLaunch)
    SimpleDraweeView ivLaunch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected int getRootViewId() {
        return R.layout.activity_launch;
    }

    @Override
    public void setupViews() {
        super.setupViews();
        ButterKnife.bind(this);

        delayTofinish();
        loadPic();

    }

    private void delayTofinish() {
        Observable.interval(0,1, TimeUnit.SECONDS)
                .subscribe(new NESubsciber<Long>(){
                    @Override
                    public void onNext(Long aLong) {
                        super.onNext(aLong);
                        if (aLong == 4){
                            startActivity(new Intent(getNeActivity(),MainActivity.class));
                            finish();
                        }

                    }

                    @Override
                    public void onCompleted() {
                        super.onCompleted();

                    }
                });
    }

    private void loadPic() {
     Subscription sub =  APPClient.getInstance().get3partAPI().reqLaunchPic()
                .compose(RxSchedulers.<ZhihuLanuch>io())
                .subscribe(new NESubsciber<ZhihuLanuch>(){
                    @Override
                    public void onNext(ZhihuLanuch zhihuLanuch) {
                        super.onNext(zhihuLanuch);
                        ivLaunch.setImageURI(Uri.parse(zhihuLanuch.getImg()));
                    }
                });

        add(getPageId(),sub);

    }

    @Override
    protected void onDestroy() {
        remove(getPageId());
        super.onDestroy();
    }
}

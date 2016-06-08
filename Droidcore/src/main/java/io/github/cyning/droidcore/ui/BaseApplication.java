package io.github.cyning.droidcore.ui;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import io.github.cyning.droidcore.log.LayzLog;
import io.github.cyning.droidcore.log.LogTimber;

/**
 * Author: cyning
 * Date  : 2015.06.08
 * Time  : 下午2:10
 * Desc  : Application的基类
 */
public class BaseApplication extends Application implements Thread.UncaughtExceptionHandler {

    private static Context instance;
    FragmentCallProxy mFragmentProxy;
    private Handler handler;

    @Override public void onCreate() {
        super.onCreate();
        mFragmentProxy = new FragmentCallProxy();
        instance = this;
        Thread.currentThread().setPriority(Thread.MAX_PRIORITY);
        handler = new Handler();
    }


    /**
     * 配置是否打印log
     */
    public void initConfigLog(boolean isDebug) {
        if (isDebug) {
            LayzLog.plant(new LogTimber.DebugTree());
        }
    }

    public void registerFragmentLifecycleCallbacks(FragmentCallProxy.FragmentLifecycleCallbacks callback) {
        mFragmentProxy.registerFragmentLifecycleCallbacks(callback);
    }

    public void unregisterFragmentLifecycleCallbacks(FragmentCallProxy.FragmentLifecycleCallbacks callback) {
        mFragmentProxy.unregisterFragmentLifecycleCallbacks(callback);
    }

    public void onFragmentCreated(Fragment mFragment, Bundle savedInstanceState) {
        mFragmentProxy.onFragmentCreated(mFragment, savedInstanceState);
    }

    public void onFragmentAttach(Fragment mFragment) {
        mFragmentProxy.onFragmentAttach(mFragment);
    }

    public void onFragmentStarted(Fragment mFragment) {
        mFragmentProxy.onFragmentStarted(mFragment);
    }

    public void onFragmentResumed(Fragment mFragment) {
        mFragmentProxy.onFragmentResumed(mFragment);
    }

    public void onFragmentPaused(Fragment mFragment) {
        mFragmentProxy.onFragmentPaused(mFragment);
    }

    public void onFragmentStopped(Fragment mFragment) {
        mFragmentProxy.onFragmentStopped(mFragment);
    }

    public void onFragmentSaveInstanceState(Fragment mFragment, Bundle outState) {
        mFragmentProxy.onFragmentSaveInstanceState(mFragment, outState);
    }

    public void onFragmentDestroyed(Fragment mFragment) {
        mFragmentProxy.onFragmentDestroyed(mFragment);
    }

    public void onFragmentDetach(Fragment mFragment) {
        mFragmentProxy.onFragmentDetach(mFragment);
    }

    public void onFragmentDestroyView(Fragment mFragment) {
        mFragmentProxy.onFragmentDestroyView(mFragment);
    }

    public void onFragmentCreateView(Fragment mFragment, Bundle outState) {
        mFragmentProxy.onFragmentCreateView(mFragment, outState);
    }

    @Override public void uncaughtException(Thread thread, Throwable ex) {
        System.exit(0);

        if (setExceptionPage() != null) {
            Intent intent = new Intent(this, setExceptionPage());
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        }
    }

    protected Class<?> setExceptionPage() {
        return null;
    }

    public static Context getInstance() {
        return instance;
    }
}

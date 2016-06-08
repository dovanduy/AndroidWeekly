package io.github.cyning.droidcore.ui;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import java.util.ArrayList;

/**
 *
 * <p>
 *    这是一个可以回调@see{#android.support.v4.app.Fragment}的一个检测类，是和Application同在的。
 *    对于每个fragment对象，我们想知道在他不同生命周期下，注册一些列的监听，如数据统计中的页面PV等。
 * </p>
 *
 *
 *
 *
 *
 *
 * Author Cyning
 * Date   2015.09.02
 * Time   下午6:12
 * Desc   <p>监听Fragment的代理类</p>
 */

public class FragmentCallProxy {

    private ArrayList<FragmentLifecycleCallbacks> mFragmentCallbacks = new ArrayList<FragmentLifecycleCallbacks>();

    public void registerFragmentLifecycleCallbacks(FragmentLifecycleCallbacks callback) {
        synchronized (mFragmentCallbacks) {
            mFragmentCallbacks.add(callback);
        }
    }

    public void unregisterFragmentLifecycleCallbacks(FragmentLifecycleCallbacks callback) {
        synchronized (mFragmentCallbacks) {
            mFragmentCallbacks.remove(callback);
        }
    }

    private Object[] collectFragmentLifecycleCallbacks() {
        Object[] callbacks = null;
        synchronized (mFragmentCallbacks) {
            if (mFragmentCallbacks.size() > 0) {
                callbacks = mFragmentCallbacks.toArray();
            }
        }
        return callbacks;
    }

    public void onFragmentCreated(Fragment mFragment, Bundle savedInstanceState) {
        Object[] callbacks = collectFragmentLifecycleCallbacks();
        if (callbacks != null) {
            for (int i = 0; i < callbacks.length; i++) {
                ((FragmentLifecycleCallbacks) callbacks[i]).onFragmentCreated(mFragment, savedInstanceState);
            }
        }
    }

    public void onFragmentAttach(Fragment mFragment) {
        Object[] callbacks = collectFragmentLifecycleCallbacks();
        if (callbacks != null) {
            for (int i = 0; i < callbacks.length; i++) {
                ((FragmentLifecycleCallbacks) callbacks[i]).onFragmentAttach(mFragment);
            }
        }
    }

    public void onFragmentStarted(Fragment mFragment) {
        Object[] callbacks = collectFragmentLifecycleCallbacks();
        if (callbacks != null) {
            for (int i = 0; i < callbacks.length; i++) {
                ((FragmentLifecycleCallbacks) callbacks[i]).onFragmentStarted(mFragment);
            }
        }
    }

    public void onFragmentResumed(Fragment mFragment) {
        Object[] callbacks = collectFragmentLifecycleCallbacks();
        if (callbacks != null) {
            for (int i = 0; i < callbacks.length; i++) {
                ((FragmentLifecycleCallbacks) callbacks[i]).onFragmentResumed(mFragment);
            }
        }
    }

    public void onFragmentPaused(Fragment mFragment) {
        Object[] callbacks = collectFragmentLifecycleCallbacks();
        if (callbacks != null) {
            for (int i = 0; i < callbacks.length; i++) {
                ((FragmentLifecycleCallbacks) callbacks[i]).onFragmentPaused(mFragment);
            }
        }
    }

    public void onFragmentStopped(Fragment mFragment) {
        Object[] callbacks = collectFragmentLifecycleCallbacks();
        if (callbacks != null) {
            for (int i = 0; i < callbacks.length; i++) {
                ((FragmentLifecycleCallbacks) callbacks[i]).onFragmentStopped(mFragment);
            }
        }
    }

    public void onFragmentSaveInstanceState(Fragment mFragment, Bundle outState) {
        Object[] callbacks = collectFragmentLifecycleCallbacks();
        if (callbacks != null) {
            for (int i = 0; i < callbacks.length; i++) {
                ((FragmentLifecycleCallbacks) callbacks[i]).onFragmentSaveInstanceState(mFragment, outState);
            }
        }
    }

    public void onFragmentDestroyed(Fragment mFragment) {
        Object[] callbacks = collectFragmentLifecycleCallbacks();
        if (callbacks != null) {
            for (int i = 0; i < callbacks.length; i++) {
                ((FragmentLifecycleCallbacks) callbacks[i]).onFragmentDestroyed(mFragment);
            }
        }
    }

    public void onFragmentDetach(Fragment mFragment) {
        Object[] callbacks = collectFragmentLifecycleCallbacks();
        if (callbacks != null) {
            for (int i = 0; i < callbacks.length; i++) {
                ((FragmentLifecycleCallbacks) callbacks[i]).onFragmentDetach(mFragment);
            }
        }
    }

    public void onFragmentDestroyView(Fragment mFragment) {
        Object[] callbacks = collectFragmentLifecycleCallbacks();
        if (callbacks != null) {
            for (int i = 0; i < callbacks.length; i++) {
                ((FragmentLifecycleCallbacks) callbacks[i]).onFragmentDestroyView(mFragment);
            }
        }
    }

    public void onFragmentCreateView(Fragment mFragment, Bundle outState) {
        Object[] callbacks = collectFragmentLifecycleCallbacks();
        if (callbacks != null) {
            for (int i = 0; i < callbacks.length; i++) {
                ((FragmentLifecycleCallbacks) callbacks[i]).onFragmentCreateView(mFragment, outState);
            }
        }
    }

    public static interface FragmentLifecycleCallbacks {
        void onFragmentAttach(Fragment mFragment);

        void onFragmentCreated(Fragment mFragment, Bundle savedInstanceState);

        void onFragmentStarted(Fragment mFragment);

        void onFragmentResumed(Fragment mFragment);

        void onFragmentPaused(Fragment mFragment);

        void onFragmentStopped(Fragment mFragment);

        void onFragmentSaveInstanceState(Fragment mFragment, Bundle outState);

        void onFragmentDestroyed(Fragment mFragment);

        void onFragmentDetach(Fragment mFragment);

        void onFragmentDestroyView(Fragment mFragment);

        void onFragmentCreateView(Fragment mFragment, Bundle outState);
    }
}

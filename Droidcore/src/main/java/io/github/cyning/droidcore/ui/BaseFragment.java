package io.github.cyning.droidcore.ui;

import android.app.Activity;
import android.app.Application;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import io.github.cyning.droidcore.log.LayzLog;
import io.github.cyning.droidcore.utils.StringUtils;
import io.github.cyning.droidcore.utils.ViewUtils;
import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.List;

/**
 * Fragment的基类
 */
public class BaseFragment extends Fragment {

    protected boolean hasExist = false;

    public static final String FRAGMENT_ARG = "ARG";

    public static final String TAG_ISFIRSTAPPEAR = "TAG_ISFIRSTAPPEAR";

    public static final String STATE_8954201239547 = "internalSavedViewState8954201239547";

    protected String frgmentTag = null;

    protected View rootView;

    protected boolean isCreated = false;

    protected boolean isDestroyView = false;

    private BaseApplication application;

    /**
     * 传递参数时，   需要传的序列化数据
     * @param data 序列化的数据
     */
    public void putSerializable(Serializable data) {
        Bundle bundle = new Bundle();
        bundle.putSerializable(FRAGMENT_ARG, data);
        setArguments(bundle);
    }

    /**
     * Get the Serializable paramters ,which is from  {@link #putSerializable(Serializable data)} to move a piece.
     * @return
     */
    public Serializable getSerializable() {
        Bundle bundle = getArguments();
        if (bundle != null) {
            return bundle.getSerializable(FRAGMENT_ARG);
        }
        return null;
    }

    public Activity getNeActivity() {
        return getActivity();
    }

    public <T extends View> T v(View v, int id) {
        return ViewUtils.find(v, id);
    }

    /**
     * Set a layout ID for the Fragment
     * @return
     */
    protected int getRootViewId() {
        return -1;
    }

    public ActionBar getActionBar() {
        if (getActivity() != null && getActivity() instanceof FragmentActivity) {
            return ((AppCompatActivity) getActivity()).getSupportActionBar();
        }
        return null;
    }

    public ActionBar getNEActionBar() {
        return getActionBar();
    }

    public View getRootView() {
        return rootView;
    }

    /**
     * setup the view
     * @param view
     */
    protected void setupViews(View view, Bundle savedInstanceState) { }

    /**
     * To get the BaseApplication
     * @return
     */
    protected BaseApplication getBaseApplication() {
        Application application = getActivity().getApplication();
        if (application != null && application instanceof BaseApplication) {
            return (BaseApplication) application;
        } else {
            return null;
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle arg = getArguments();
        getIntentData(arg);
        LayzLog.i(" %s---->  %s", getNeTag(), "onCreate");
        if (application != null) {
            application.onFragmentCreated(this, savedInstanceState);
        }
    }

    protected void getIntentData(Bundle arg) {
    }

    protected String getNeTag() {
        if (!StringUtils.hasText(frgmentTag)) {
            frgmentTag = getClass().getSimpleName();
        }
        return frgmentTag;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        LayzLog.i(" %s---->  %s", getNeTag(), "onCreateView");
        if (application != null) {
            application.onFragmentCreateView(this, savedInstanceState);
        }
        if (rootView == null) {
            rootView = setContentView(inflater);
        } else {
            isCreated = true;
        }
        if (rootView != null) {
            setupViews(rootView, savedInstanceState);
        }
        isDestroyView = false;
        if (rootView.getBackground() == null) {
        }
        return rootView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        LayzLog.i(" %s---->  %s", getNeTag(), "onViewCreated");
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        application = getBaseApplication();
        if (application != null) {
            application.onFragmentAttach(this);
        }
    }

    protected View setContentView(LayoutInflater inflater) {
        View view = inflater.inflate(getRootViewId(), null);
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        LayzLog.i(" %s---->  %s", getNeTag(), "onResume");
        if (application != null) {
            application.onFragmentResumed(this);
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        LayzLog.i(" %s---->  %s", getNeTag(), "onStart");
        if (application != null) {
            application.onFragmentStarted(this);
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        LayzLog.i(" %s---->  %s", getNeTag(), "onPause");
        if (application != null) {
            application.onFragmentPaused(this);
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        LayzLog.i(" %s---->  %s", getNeTag(), "onSaveInstanceState");
        saveStateToArguments();
        if (application != null) {
            application.onFragmentSaveInstanceState(this, outState);
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        saveStateToArguments();
        LayzLog.i(" %s---->  %s", getNeTag(), "onStop");
        if (application != null) {
            application.onFragmentStopped(this);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        LayzLog.i(" %s---->  %s", getNeTag(), "onDestroyView");
        saveStateToArguments();
        isDestroyView = true;
        if (application != null) {
            application.onFragmentDestroyView(this);
        }
        //        rootView = null;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        LayzLog.i(" %s---->  %s", getNeTag(), "onDestroy");
        if (application != null) {
            application.onFragmentDestroyed(this);
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        onHackyDetach();
        LayzLog.i(" %s---->  %s", getNeTag(), "onDetach");
        if (application != null) {
            application.onFragmentDetach(this);
        }
    }
    // http://stackoverflow.com/questions/15207305/getting-the-error-java-lang-illegalstateexception-activity-has-been-destroyed

    private void onHackyDetach() {
        try {
            Field childFragmentManager = Fragment.class.getDeclaredField("mChildFragmentManager");
            childFragmentManager.setAccessible(true);
            childFragmentManager.set(this, null);
        } catch (NoSuchFieldException e) {
            throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    Bundle savedState;

    public BaseFragment() {
        super();
        if (getArguments() == null) {
            setArguments(new Bundle());
        }
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        LayzLog.i(" %s---->  %s", getNeTag(), "onActivityCreated");
        // Restore State Here
        if (!isrestoreState()) {
            // First Time, Initialize something here
            onFirstTimeLaunched();
        } else {
            restoreState();
        }
    }

    /**
     * Called when the fragment is launched for the first time.
     * In the other words, fragment is now recreated.
     */

    protected void onFirstTimeLaunched() {
        LayzLog.i(" %s---->  %s", getNeTag(), "onActivityCreated");
    }
    ////////////////////
    // Don't Touch !!
    ////////////////////

    private void saveStateToArguments() {
        savedState = saveState();
        if (savedState != null) {
            Bundle b = getArguments();
            if (b != null) {
                b.putBundle(STATE_8954201239547, savedState);
            }
        }
    }

    protected boolean isrestoreState() {
        Bundle b = getArguments();
        if (b != null) {
            savedState = b.getBundle(STATE_8954201239547);
            if (savedState != null) {
                return true;
            }
        }
        return false;
    }
    /////////////////////////////////
    // Restore Instance State Here
    /////////////////////////////////

    private void restoreState() {
        if (savedState != null) {
            onNERestoreState(savedState);
        }
    }

    /**
     * Called when the fragment's activity has been created and this
     * fragment's view hierarchy instantiated.  It can be used to do final
     * initialization once these pieces are in place, such as retrieving
     * views or restoring state.  This is called after {@link #onCreateView}
     * and before {@link #onViewStateRestored(Bundle)}.
     * @param savedInstanceState If the fragment is being re-created from
     * a previous saved state, this is the state.
     */

    protected void onNERestoreState(Bundle savedInstanceState) {
        LayzLog.i(" %s---->  %s", getNeTag(), "onNERestoreState");
    }
    //////////////////////////////
    // Save Instance State Here
    //////////////////////////////

    private Bundle saveState() {
        Bundle state = new Bundle();
        state.putBoolean(TAG_ISFIRSTAPPEAR, false);
        onNESaveState(state);
        return state;
    }

    /**
     * Called to ask the fragment to save its current dynamic state, so it
     * can later be reconstructed in a new instance of its process is
     * restarted.  If a new instance of the fragment later needs to be
     * created, the data you place in the Bundle here will be available
     * in the Bundle given to {@link #onNERestoreState(Bundle)}.
     * <p>This corresponds to {@link Activity#onSaveInstanceState(Bundle)
     * Activity.onSaveInstanceState(Bundle)} and most of the discussion there
     * applies here as well.  Note however: <em>this method may be called
     * at any time before {@link #onDestroy()}</em>.  There are many situations
     * where a fragment may be mostly torn down (such as when placed on the
     * back stack with no UI showing), but its state will not be saved until
     * its owning activity actually needs to save its state.
     * @param outState Bundle in which to place your saved state.
     */

    protected void onNESaveState(Bundle outState) {
    }

    @Override
    public void startActivityForResult(Intent intent, int requestCode) {
        Fragment mFragment = this;
        if (getParentFragment() != null){
            mFragment = getParentFragment();
        }
        if (getActivity() == null) {
            throw new IllegalStateException("Fragment " + this + " not attached to Activity");
        }
        getActivity().startActivityFromFragment(mFragment, intent, requestCode);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // notifying nested fragments (support library bug fix)
        final FragmentManager childFragmentManager = getChildFragmentManager();

        if (childFragmentManager != null) {
            final List<Fragment> nestedFragments = childFragmentManager.getFragments();

            if (nestedFragments == null || nestedFragments.size() == 0) return;

            for (Fragment childFragment : nestedFragments) {
                if (childFragment != null && !childFragment.isDetached() && !childFragment.isRemoving()) {
                    childFragment.onActivityResult(requestCode, resultCode, data);
                }
            }
        }
    }

    public  boolean isAcitive(){
        return isAcitive(this);
    }

    public BaseFragment getFragment(){
        return  this;
    }

    public static  boolean isAcitive(Fragment mFragment){
      return  mFragment != null &&mFragment.getActivity() != null  && !mFragment.isDetached() && !mFragment
                .isRemoving();
    }

    public void finish() {
        if (getNeActivity() != null) {
            getNeActivity().finish();
        }
    }
}

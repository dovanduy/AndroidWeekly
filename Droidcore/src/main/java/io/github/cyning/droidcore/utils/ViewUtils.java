package io.github.cyning.droidcore.utils;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Shader;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.annotation.LayoutRes;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.view.TouchDelegate;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.HeaderViewListAdapter;
import android.widget.TextView;

/**
 * Created by wjying on 13-12-2.
 */
public class ViewUtils {

    /**
     * 通知View重新设置Adapter
     *
     * @param root
     * @param id
     */
    public static void notifyViewResetAdapter(View root, int id) {
        if (root == null) {
            return;
        }
        View view = root.findViewById(id);
        if (view == null) {
            return;
        } else if (view instanceof AdapterView) {
            notifyAdapterViewChanged((AdapterView) view, true);
        } else if (view instanceof ViewPager) {
            notifyViewPagerChanged((ViewPager) view, true);
        }
    }

    /**
     * 通知view更新数据
     *
     * @param root
     * @param id
     */
    public static void notifyViewDatasetChanged(View root, int id) {
        if (root == null) {
            return;
        }
        View view = root.findViewById(id);
        if (view == null) {
            return;
        } else if (view instanceof AdapterView) {
            notifyAdapterViewChanged((AdapterView) view, false);
        } else if (view instanceof ViewPager) {
            notifyViewPagerChanged((ViewPager) view, false);
        }
    }

    /**
     * notify for ViewPager
     *
     * @param viewPager
     */
    private static void notifyViewPagerChanged(ViewPager viewPager, boolean resetAdapter) {
        if (viewPager == null) {
            return;
        }
        PagerAdapter adapter = viewPager.getAdapter();

        if (resetAdapter) {
            viewPager.setAdapter(adapter);
        } else if (adapter != null) {
            adapter.notifyDataSetChanged();
        }
    }

    /**
     * notify for AdapterView
     *
     * @param adapterView
     */
    private static void notifyAdapterViewChanged(AdapterView adapterView, boolean resetAdapter) {
        if (adapterView == null) {
            return;
        }
        Adapter adapter = adapterView.getAdapter();
        if (adapter != null && adapter instanceof HeaderViewListAdapter) {
            adapter = ((HeaderViewListAdapter) adapter).getWrappedAdapter();
        }
        if (adapter == null || !(adapter instanceof BaseAdapter)) {
            return;
        }

        ((BaseAdapter) adapter).notifyDataSetChanged();
    }

    public static int dp2px(Context context, float dp) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dp * scale + 0.5f);
    }

    public static int[] getViewLocationOnScreen(View view) {
        int[] location = new int[2];
        view.getLocationOnScreen(location);
        return location;
    }

    @SuppressWarnings("unchecked")
    public static <V extends View> V find(Activity ac, int id) {
        if (ac == null) {
            throw new NullPointerException(" Activity is null");
        }
        return (V) ac.findViewById(id);
    }

    @SuppressWarnings("unchecked")
    public static <V extends View> V inflate(Context mContext, @LayoutRes int layoutId) {
        if (mContext == null) {
            throw new NullPointerException(" Context is null");
        }
        return (V) View.inflate(mContext, layoutId, null);
    }

    @SuppressWarnings("unchecked")
    public static <V extends View> V find(View view, int id) {

        if (view == null) {
            throw new NullPointerException(" view is null");
        }
        return (V) view.findViewById(id);
    }



    public static void gone(View _view) {
        if (_view != null && _view.getVisibility() != View.GONE) {
            _view.setVisibility(View.GONE);
        }
    }

    public static void gone(View _view, int second) {
        if (_view != null && _view.getVisibility() != View.GONE) {

            _view.setVisibility(View.GONE);
        }
    }

    public static void view(View _view) {
        if (_view != null && _view.getVisibility() != View.VISIBLE) {
            _view.setVisibility(View.VISIBLE);
        }
    }

    public static float getTextWidth(TextView mTextView, String text) {
        Paint mPaint = mTextView.getPaint();
        if (TextUtils.isEmpty(text)) {
            text = new String();
        }
        float lenght = mPaint.measureText(text);

        return lenght;
    }

    public static ViewGroup getActionBarView(Activity activity) {
        int id = activity.getResources().getIdentifier("action_bar", "id", "android");
        ViewGroup actionBar = null;
        if (id != 0) {
            actionBar = (ViewGroup) activity.findViewById(id);
        }
        if (actionBar == null) {
            actionBar = findToolbar((ViewGroup) activity.findViewById(android.R.id.content)
                    .getRootView());
        }
        return actionBar;
    }

    private static ViewGroup findToolbar(ViewGroup viewGroup) {
        ViewGroup toolbar = null;
        for (int i = 0, len = viewGroup.getChildCount(); i < len; i++) {
            View view = viewGroup.getChildAt(i);
            if (view.getClass().getName().equals("android.support.v7.widget.Toolbar")
                    || view.getClass().getName().equals("android.widget.Toolbar")) {
                toolbar = (ViewGroup) view;
            } else if (view instanceof ViewGroup) {
                toolbar = findToolbar((ViewGroup) view);
            }
            if (toolbar != null) {
                break;
            }
        }
        return toolbar;
    }

    public static Bitmap captureActivity(Activity mActivity) {
        Bitmap bitmap = null;
        View view = mActivity.getWindow().getDecorView();
        view.setDrawingCacheEnabled(false);
        // 设置是否可以进行绘图缓存
        if (!view.isDrawingCacheEnabled()) {
            view.setDrawingCacheEnabled(true);
        }

        // 如果绘图缓存无法，强制构建绘图缓存
        view.buildDrawingCache();
        // 返回这个缓存视图
        bitmap = view.getDrawingCache();

        // 获取状态栏高度
        Rect frame = new Rect();
        // 测量屏幕宽和高
        view.getWindowVisibleDisplayFrame(frame);
        int stautsHeight = frame.top;

        int width = mActivity.getWindowManager().getDefaultDisplay().getWidth();
        int height = mActivity.getWindowManager().getDefaultDisplay().getHeight();
        // 根据坐标点和需要的宽和高创建bitmap
        bitmap = Bitmap.createBitmap(bitmap, 0, stautsHeight, width, height - stautsHeight);
        return bitmap;
    }

    public static Bitmap capturView(View mView) {
        return ViewUtils.capture(mView, mView.getWidth(), mView.getHeight(), false, Bitmap.Config.ARGB_4444);
    }

    /**
     * @param view，当前想要创建截图的View
     * @param width，设置截图的宽度
     * @param height，设置截图的高度
     * @param scroll，如果为真则从View的当前滚动位置开始绘制截图
     * @param config，Bitmap的质量，比如ARGB_8888等
     * @return 截图的Bitmap
     */
    public static Bitmap capture(View view, float width, float height, boolean scroll, Bitmap.Config config) {
        view.setDrawingCacheEnabled(false);
        if (!view.isDrawingCacheEnabled()) {
            view.setDrawingCacheEnabled(true);
        }
        view.buildDrawingCache();
        Bitmap bitmap = Bitmap.createBitmap((int) width, (int) height, config);
        bitmap.eraseColor(Color.WHITE);
        Canvas canvas = new Canvas(bitmap);
        int left = view.getLeft();
        int top = view.getTop();
        if (scroll) {
            left = view.getScrollX();
            top = view.getScrollY();
        }
        int status = canvas.save();
        canvas.translate(-left, -top);
        float scale = width / view.getWidth();
        canvas.scale(scale, scale, left, top);
        view.draw(canvas);
        canvas.restoreToCount(status);
        Paint alphaPaint = new Paint();
        alphaPaint.setColor(Color.TRANSPARENT);
        canvas.drawRect(0f, 0f, 1f, height, alphaPaint);
        canvas.drawRect(width - 1f, 0f, width, height, alphaPaint);
        canvas.drawRect(0f, 0f, width, 1f, alphaPaint);
        canvas.drawRect(0f, height - 1f, width, height, alphaPaint);
        canvas.setBitmap(null);
        view.setDrawingCacheEnabled(false);
        return bitmap;
    }


    /**
     * 扩大View的触摸和点击响应范围,最大不超过其父View范围
     *
     * @param view
     * @param top
     * @param bottom
     * @param left
     * @param right
     */
    public static void expandViewTouchDelegate(final View view, final int top,
                                               final int bottom, final int left, final int right) {

        ((View) view.getParent()).post(new Runnable() {
            @Override
            public void run() {
                Rect bounds = new Rect();
                view.setEnabled(true);
                view.getHitRect(bounds);

                bounds.top -= top;
                bounds.bottom += bottom;
                bounds.left -= left;
                bounds.right += right;

                TouchDelegate touchDelegate = new TouchDelegate(bounds, view);

                if (View.class.isInstance(view.getParent())) {
                    ((View) view.getParent()).setTouchDelegate(touchDelegate);
                }
            }
        });
    }

    /**
     * 还原View的触摸和点击响应范围,最小不小于View自身范围
     *
     * @param view
     */
    public static void restoreViewTouchDelegate(final View view) {

        ((View) view.getParent()).post(new Runnable() {
            @Override
            public void run() {
                Rect bounds = new Rect();
                bounds.setEmpty();
                TouchDelegate touchDelegate = new TouchDelegate(bounds, view);

                if (View.class.isInstance(view.getParent())) {
                    ((View) view.getParent()).setTouchDelegate(touchDelegate);
                }
            }
        });
    }

    public static void fixBackgroundRepeat(View view) {
        Drawable bg = view.getBackground();
        if (bg != null) {
            if (bg instanceof BitmapDrawable) {
                BitmapDrawable bmp = (BitmapDrawable) bg;
                bmp.mutate(); // make sure that we aren't sharing state anymore
                bmp.setTileModeXY(Shader.TileMode.REPEAT, Shader.TileMode.REPEAT);
            }
        }
    }
}

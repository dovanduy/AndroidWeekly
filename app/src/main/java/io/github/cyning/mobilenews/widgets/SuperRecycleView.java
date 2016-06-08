package io.github.cyning.mobilenews.widgets;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.AttributeSet;

/**
 * @author Cyning
 * @since 2016.03.07
 * Time    11:13 PM
 * Desc    <p>类/接口描述</p>
 */

public class SuperRecycleView extends RecyclerView {
    OnLoadMoreListener onLoadMoreListener;

    private static final int ITEM_LEFT_TO_LOAD_MORE = 2;

    LAYOUT_MANAGER_TYPE layoutManagerType = LAYOUT_MANAGER_TYPE.LINEAR;

    boolean isLoadingMore = false;



    private int[] lastScrollPositions;
    public SuperRecycleView(Context context) {
        super(context);
        initView();
    }

    public SuperRecycleView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    public SuperRecycleView(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        initView();
    }

    private void initView() {
        this.addOnScrollListener(new OnScrollListener() {
            @Override public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();
                int lastVisibleItemPosition = getLastVisibleItemPosition(layoutManager);
                int visibleItemCount = layoutManager.getChildCount();
                int totalItemCount = layoutManager.getItemCount();

                if (((totalItemCount - lastVisibleItemPosition) <= ITEM_LEFT_TO_LOAD_MORE ||
                    (totalItemCount - lastVisibleItemPosition) == 0 && totalItemCount > visibleItemCount)
                    && !isLoadingMore) {

                    isLoadingMore = true;
                    if (onLoadMoreListener != null) {
                        onLoadMoreListener.onMoreAsked(recyclerView.getAdapter().getItemCount(), ITEM_LEFT_TO_LOAD_MORE, lastVisibleItemPosition);
                    }
                }
            }
        });
    }

    public SuperRecycleView setLoadingMore(boolean loadingMore) {
        isLoadingMore = loadingMore;
        return this;
    }

    public SuperRecycleView setOnLoadMoreListener(OnLoadMoreListener onLoadMoreListener) {
        this.onLoadMoreListener = onLoadMoreListener;
        return this;
    }

    private int getLastVisibleItemPosition(RecyclerView.LayoutManager layoutManager) {
        int lastVisibleItemPosition = -1;

        if (layoutManagerType == null) {
            if (layoutManager instanceof GridLayoutManager) {
                layoutManagerType = LAYOUT_MANAGER_TYPE.GRID;
            } else if (layoutManager instanceof LinearLayoutManager) {
                layoutManagerType = LAYOUT_MANAGER_TYPE.LINEAR;
            } else if (layoutManager instanceof StaggeredGridLayoutManager) {
                layoutManagerType = LAYOUT_MANAGER_TYPE.STAGGERED_GRID;
            } else {
                throw new RuntimeException("Unsupported LayoutManager used. Valid ones are LinearLayoutManager, GridLayoutManager and StaggeredGridLayoutManager");
            }
        }

        switch (layoutManagerType) {
            case LINEAR:
                lastVisibleItemPosition = ((LinearLayoutManager) layoutManager).findLastVisibleItemPosition();
                break;
            case GRID:
                lastVisibleItemPosition = ((GridLayoutManager) layoutManager).findLastVisibleItemPosition();
                break;
            case STAGGERED_GRID:
                lastVisibleItemPosition = caseStaggeredGrid(layoutManager);
                break;
        }
        return lastVisibleItemPosition;
    }

    private int caseStaggeredGrid(RecyclerView.LayoutManager layoutManager) {
        StaggeredGridLayoutManager staggeredGridLayoutManager = (StaggeredGridLayoutManager) layoutManager;
        if (lastScrollPositions == null)
            lastScrollPositions = new int[staggeredGridLayoutManager.getSpanCount()];

        staggeredGridLayoutManager.findLastVisibleItemPositions(lastScrollPositions);
        return findMax(lastScrollPositions);
    }
    private int findMax(int[] lastPositions) {
        int max = Integer.MIN_VALUE;
        for (int value : lastPositions) {
            if (value > max)
                max = value;
        }
        return max;
    }
    public interface OnLoadMoreListener{
        public   void onMoreAsked(int overallItemsCount, int itemsBeforeMore, int maxLastVisiblePosition);

    }

    public enum LAYOUT_MANAGER_TYPE {
        LINEAR,
        GRID,
        STAGGERED_GRID
    }
}

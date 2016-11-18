package com.xujun.funapp.common.recyclerView;

/**
 * @ explain:
 * @ author：xujun on 2016/11/18 18:58
 * @ email：gdutxiaoxu@163.com
 */
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;

public abstract class RecyclerOnScrollListener extends RecyclerView.OnScrollListener {
    public static String TAG = EndlessRecyclerOnScrollListener.class.getSimpleName();
    private int scrolledDistance = 0;
    private boolean controlsVisible = false;

    // True if we are still waiting for the last set of data to load.
    private boolean loading = true;
    // The minimum amount of items to have below your current scroll position before loading more.
    private int visibleThreshold = 5;


    private int pastVisibleItems, visibleItemCount, totalItemCount;

    private int current_page = 1;

    private StaggeredGridLayoutManager mStaggeredGridLayoutManager;
    private int previousTotal;

    public RecyclerOnScrollListener(StaggeredGridLayoutManager staggeredGridLayoutManager) {
        this.mStaggeredGridLayoutManager = staggeredGridLayoutManager;

    }

    @Override
    public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
        super.onScrolled(recyclerView, dx, dy);

        visibleItemCount = recyclerView.getChildCount();
        totalItemCount = mStaggeredGridLayoutManager.getItemCount();
        //firstVisibleItem = mStaggeredGridLayoutManager.findFirstVisibleItemPosition();
        int[] firstVisibleItems = null;
        firstVisibleItems = mStaggeredGridLayoutManager.findFirstVisibleItemPositions(firstVisibleItems);
        if (firstVisibleItems != null && firstVisibleItems.length > 0) {
            pastVisibleItems = firstVisibleItems[0];
        }


        if (loading) {
            if ((visibleItemCount + pastVisibleItems) >= totalItemCount) {
                loading = false;
                previousTotal = totalItemCount;
            }
        }
        if (!loading && (totalItemCount - visibleItemCount)
                <= (pastVisibleItems + visibleThreshold)) {
            // End has been reached

            // Do something
            current_page++;

            onLoadMore(current_page);

            loading = true;
        }

        if (scrolledDistance > 1 && controlsVisible) {
            controlsVisible = false;
            scrolledDistance = 0;
        } else if (scrolledDistance < -1 && !controlsVisible) {
            controlsVisible = true;
            scrolledDistance = 0;
        }

        if ((controlsVisible && dy > 0) || (!controlsVisible && dy < 0)) {
            scrolledDistance += dy;
        }
    }

    public abstract void onLoadMore(int current_page);

    ;
}
package com.xujun.funapp.common;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;

import com.xujun.funapp.common.recyclerView.LayoutMangerType;
import com.xujun.funapp.widget.divider.DividerGridItemDecoration;
import com.xujun.funapp.widget.divider.DividerItemDecoration;

/**
 * @ explain:
 * @ author：xujun on 2016/10/17 20:33
 * @ email：gdutxiaoxu@163.com
 */
public class RecyclerUtils {



    public static void init(RecyclerView recyclerView, LayoutMangerType type) {
        Context context = recyclerView.getContext();
        if (type == LayoutMangerType.Linear) {
            recyclerView.setLayoutManager(new LinearLayoutManager(context));

        } else if (type == LayoutMangerType.Grid) {
            recyclerView.setLayoutManager(new GridLayoutManager(context, 2));
        } else {
            recyclerView.setLayoutManager(new StaggeredGridLayoutManager(2, LinearLayoutManager
                    .VERTICAL));
        }
        init(recyclerView);

    }

    public static void init(RecyclerView recyclerView) {
        Context context = recyclerView.getContext();

        RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();
        if (layoutManager == null) {

            layoutManager = new LinearLayoutManager(context);
            DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(context,
                    LinearLayoutManager.VERTICAL);
            recyclerView.addItemDecoration(dividerItemDecoration);
            recyclerView.setLayoutManager(layoutManager);
        } else {
            if (layoutManager instanceof LinearLayoutManager &&
                    false == layoutManager instanceof GridLayoutManager) {
                DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(context,
                        LinearLayoutManager.VERTICAL);
                recyclerView.addItemDecoration(dividerItemDecoration);
            } else if (layoutManager instanceof GridLayoutManager) {
                DividerGridItemDecoration dividerGridItemDecoration = new
                        DividerGridItemDecoration(context);
                recyclerView.addItemDecoration(dividerGridItemDecoration);

            } else if (layoutManager instanceof StaggeredGridLayoutManager) {
                DividerGridItemDecoration dividerGridItemDecoration = new
                        DividerGridItemDecoration(context);
                recyclerView.addItemDecoration(dividerGridItemDecoration);
            }
        }
    }
}

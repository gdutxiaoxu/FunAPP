package com.xujun.funapp.common;

import android.app.Activity;
import android.util.Log;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author xujun  on 2016/12/27.
 * @email gdutxiaoxu@163.com
 */

public class ActivityCollector {

    public static  final String TAG="ActivityCollector";

    private static List<Activity> mActivitys = new ArrayList<>();

    public static void add(Activity activity) {
        mActivitys.add(activity);
    }

    public static void remove(Activity activity) {
        mActivitys.remove(activity);
    }

    public static Activity getTop() {
        if (mActivitys.isEmpty()) {
            return null;
        }
        return mActivitys.get(mActivitys.size() - 1);
    }

    public static void quit(){
        try{
            ArrayList<Activity> activities = new ArrayList<>();
            Collections.copy(activities,mActivitys);
            for (Activity a:activities) {
                if(!a.isFinishing()){
                    a.finish();
                    mActivitys.remove(activities);
                }

            }
        }catch (Exception e){
            System.exit(0);
            Log.e(TAG, "quit: e=" +e.getMessage() );
        }

    }
}

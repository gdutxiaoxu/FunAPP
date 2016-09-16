package com.xujun.funapp.common.util;

import android.app.Activity;
import android.content.Context;
import android.view.View;

import com.szl.mobileoa.common.Constants;
import com.szl.mobileoa.view.prepare.PrepareActivity;

import me.drakeet.materialdialog.MaterialDialog;

/**
 * Created by Domen、on 2016/5/30.
 */
public class DialogUtils {
    public static void showLoginOutDialog(final Context context) {
        final MaterialDialog dialog = new MaterialDialog(context)
                .setTitle("注意：")
                .setMessage("您的账号在别的设备登录，请重新登录！")
                .setCanceledOnTouchOutside(false);
        dialog.setPositiveButton("ok", new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                PrepareActivity.start(context, PrepareActivity.LOGIN);
                ((Activity) context).finish();
                SPUtils.remove(Constants.TOKEN, Constants.ID, Constants.USERNAME, Constants.USER);
                SPUtils.clear(Constants.CONFIG);
            }
        });
        dialog.show();
    }
}

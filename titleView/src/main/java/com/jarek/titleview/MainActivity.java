package com.jarek.titleview;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.jarek.library.TitleView;

/**
 * Created by Jarek on 2016/9/6.
 */
public class MainActivity extends AppCompatActivity {

    private TitleView mTitleMain;
    private TitleView mTitleMain2;

    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    private void initView() {
        mTitleMain = (TitleView) findViewById(R.id.title_main);
        mTitleMain2 = (TitleView) findViewById(R.id.title_main2);
        mTitleMain.setOnTitleListener(new TitleView.ITitleViewListener() {
            @Override
            public void onLeftTextClick() {
                Log.i(TAG, "onLeftTextClick: =");
            }

            @Override
            public void onLeftImageClick() {
                Log.i(TAG, "onLeftImageClick: =");
            }

            @Override
            public void onTitleClick() {
                Log.i(TAG, "onTitleClick: =");

            }

            @Override
            public void onRightTextClick() {
                Log.i(TAG, "onRightTextClick: =");
            }

            @Override
            public void onRightImageClick() {
                Log.i(TAG, "onRightImageClick: =");
            }

            @Override
            public void onRightTextTwoClick() {

            }

            @Override
            public void onRightImageTwoClick() {

            }
        });
    }
}

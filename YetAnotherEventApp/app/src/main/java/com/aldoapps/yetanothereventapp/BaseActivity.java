package com.aldoapps.yetanothereventapp;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import butterknife.ButterKnife;

/**
 * Created by aldo on 10/27/16.
 */
public abstract class BaseActivity extends AppCompatActivity {

    protected abstract int getLayout();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(getLayout());
        ButterKnife.bind(this);
    }
}

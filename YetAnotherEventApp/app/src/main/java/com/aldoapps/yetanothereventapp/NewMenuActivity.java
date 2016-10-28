package com.aldoapps.yetanothereventapp;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.widget.EditText;

import butterknife.BindView;

/**
 * Created by aldo on 10/29/16.
 */

public class NewMenuActivity extends BaseActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.et_resto_menu)
    EditText etMenu;

    @BindView(R.id.et_resto_description)
    EditText etDescription;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initToolbar();
    }

    private void initToolbar() {
        toolbar.setTitle(getString(R.string.label_new_menu));
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
    }


    @Override
    protected int getLayout() {
        return R.layout.activity_new_menu;
    }
}

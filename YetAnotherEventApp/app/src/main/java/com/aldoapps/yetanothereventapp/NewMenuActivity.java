package com.aldoapps.yetanothereventapp;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.OnClick;

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

    @BindView(R.id.rating)
    RatingBar rating;

    String restoKey = "";

    private DatabaseReference menuRef = FirebaseDatabase.getInstance().getReference("menu");

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initToolbar();

        Bundle args = getIntent().getExtras();
        if (args != null) {
            RestoMenu restoMenu = args.getParcelable(BundleKeys.MENU_KEY);
            setRestoMenu(restoMenu);
        }
    }

    private void setRestoMenu(RestoMenu restoMenu) {
        restoKey = restoMenu.getKey();
        etMenu.setText(restoMenu.getMenu());
        etDescription.setText(restoMenu.getDescription());
        rating.setRating(restoMenu.getRating());
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

    @OnClick(R.id.btn_submit)
    void onSubmitClick() {
        DatabaseReference newMenuRef;

        if (TextUtils.isEmpty(restoKey)) {
            newMenuRef = menuRef.push();
            restoKey = newMenuRef.getKey();
        } else {
            newMenuRef = menuRef.child(restoKey);
        }

        RestoMenu restoMenu = new RestoMenu(
            restoKey,
            etMenu.getText().toString(),
            etDescription.getText().toString(),
            rating.getRating(),
            "http://lorempixel.com/400/200/"
        );

        newMenuRef.setValue(restoMenu).addOnCompleteListener(this, new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                Toast.makeText(NewMenuActivity.this, "Berhasil", Toast.LENGTH_SHORT).show();
                finish();
            }
        });


    }
}

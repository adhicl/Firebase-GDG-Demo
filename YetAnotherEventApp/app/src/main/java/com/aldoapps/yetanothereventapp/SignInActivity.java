package com.aldoapps.yetanothereventapp;

import android.support.design.widget.TextInputLayout;
import android.widget.Button;
import android.widget.EditText;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by aldo on 10/27/16.
 */

public class SignInActivity extends BaseActivity {

    @BindView(R.id.til_email)
    TextInputLayout tilEmail;

    @BindView(R.id.et_email)
    EditText etEmail;

    @BindView(R.id.et_password)
    EditText etPassword;

    @BindView(R.id.btn_facebook)
    Button btnFacebook;

    @BindView(R.id.btn_gplus)
    Button btnGplus;

    @BindView(R.id.btn_signin)
    Button btnSignin;

    @Override
    protected int getLayout() {
        return R.layout.activity_signin;
    }

    @OnClick(R.id.btn_signin)
    void onBtnSignInClick() {

    }

    @OnClick(R.id.btn_facebook)
    void onBtnFacebookClick() {

    }
}

package com.aldoapps.yetanothereventapp;

import com.google.firebase.auth.FirebaseAuth;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;

import butterknife.OnClick;

/**
 * Created by aldo on 10/27/16.
 */
public class LandingActivity extends BaseActivity {

    private FirebaseAuth auth = FirebaseAuth.getInstance();

    @Override
    protected int getLayout() {
        return R.layout.activity_landing;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        checkUserAuth();

        // if user already login, don't bother inflate layout
        setContentView(R.layout.activity_landing);
    }

    private void checkUserAuth() {
        if (auth.getCurrentUser() != null) {
            goToMainActivity();
            finish();
        }
    }

    private void goToMainActivity() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    @OnClick(R.id.btn_signup)
    void onBtnSignUpClick() {
        Intent intent = new Intent(this, SignUpActivity.class);
        startActivity(intent);
    }

    @OnClick(R.id.btn_signin)
    void onBtnSignInClick() {
        Intent intent = new Intent(this, SignInActivity.class);
        startActivity(intent);
    }
}

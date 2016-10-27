package com.aldoapps.yetanothereventapp;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import android.app.AlertDialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.support.v7.widget.Toolbar;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.OnClick;
import dmax.dialog.SpotsDialog;

/**
 * Created by aldo on 10/27/16.
 */

public class SignUpActivity extends BaseActivity {

    @BindView(R.id.et_email)
    EditText etEmail;

    @BindView(R.id.et_password)
    EditText etPassword;

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    private AlertDialog loadingDialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        loadingDialog = new SpotsDialog(this);
    }

    private void initToolbar() {
        toolbar.setTitle(getString(R.string.label_signup));
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_signup;
    }

    @OnClick(R.id.btn_signup)
    void onBtnSignUpClick() {
        loadingDialog.show();

        doRegister();
    }

    private void doRegister() {
        final FirebaseAuth auth = FirebaseAuth.getInstance();

        String email = etEmail.getText().toString();
        String password = etPassword.getText().toString();
        auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(
            new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    loadingDialog.dismiss();

                    if (!task.isSuccessful()) {
                        toastFailSignUp();
                    } else {
                        toastSuccessSignUp();
                    }

                    finish();
                }
            });
    }

    private void toastFailSignUp() {
        Toast.makeText(SignUpActivity.this, R.string.sign_up_fail,
            Toast.LENGTH_SHORT).show();
    }

    private void toastSuccessSignUp() {
        Toast.makeText(SignUpActivity.this, R.string.sign_up_success,
            Toast.LENGTH_SHORT).show();
    }
}

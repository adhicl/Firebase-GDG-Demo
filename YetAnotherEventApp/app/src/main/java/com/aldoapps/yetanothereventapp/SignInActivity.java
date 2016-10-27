package com.aldoapps.yetanothereventapp;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.GoogleAuthProvider;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Arrays;

import butterknife.BindView;
import dmax.dialog.SpotsDialog;

/**
 * Created by aldo on 10/27/16.
 */

public class SignInActivity extends BaseActivity implements View.OnClickListener, GoogleApiClient
    .ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {

    private static final int RC_SIGN_IN = 1;

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

    private AlertDialog loadingDialog;

    private GoogleApiClient googleApiClient;

    private CallbackManager callbackManager = CallbackManager.Factory.create();

    private FirebaseAuth auth = FirebaseAuth.getInstance();

    @Override
    protected int getLayout() {
        return R.layout.activity_sign_in;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        btnSignin.setOnClickListener(this);
        btnGplus.setOnClickListener(this);
        btnGplus.setOnClickListener(this);

        loadingDialog = new SpotsDialog(this);

        initFacebookLogin();
        initGoogleSignIn();
    }

    private void initFacebookLogin() {
        LoginManager.getInstance()
            .registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
                @Override
                public void onSuccess(LoginResult loginResult) {
                    handleFacebookAccessToken(loginResult.getAccessToken());
                }

                @Override
                public void onCancel() {
                }

                @Override
                public void onError(FacebookException error) {
                }
            });
    }

    private void initGoogleSignIn() {
        // Configure Google Sign In
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(
            GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build();

        googleApiClient = new GoogleApiClient.Builder(this)
            .addConnectionCallbacks(this)
            .addOnConnectionFailedListener(this)
            .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
            .build();
    }

    @Override
    public void onClick(View v) {
        loadingDialog.show();

        switch (v.getId()) {
            case R.id.btn_signin:
                doLoginWithEmail();
                break;

            case R.id.btn_facebook:
                doFacebookLogin();
                break;

            case R.id.btn_gplus:
                doGPlusLogin();
                break;
        }
    }

    private void doGPlusLogin() {
        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(googleApiClient);
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    private void doFacebookLogin() {
        LoginManager.getInstance().logInWithReadPermissions(this, Arrays
            .asList("public_profile", "email"));
    }

    private void doLoginWithEmail() {
        String email = etEmail.getText().toString();
        String password = etPassword.getText().toString();

        auth.signInWithEmailAndPassword(email, password).addOnCompleteListener(
            new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    checkTask(task);
                }
            });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RC_SIGN_IN) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            if (result.isSuccess()) {
                GoogleSignInAccount account = result.getSignInAccount();
                firebaseAuthWithGoogle(account);
            } else {
                toastFailSignIn();
            }
        } else {
            // for facebook
            callbackManager.onActivityResult(requestCode, resultCode, data);
        }
    }

    private void firebaseAuthWithGoogle(GoogleSignInAccount acct) {
        AuthCredential credential = GoogleAuthProvider.getCredential(acct.getIdToken(), null);
        auth.signInWithCredential(credential)
            .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    checkTask(task);
                }
            });
    }

    private void handleFacebookAccessToken(AccessToken token) {
        AuthCredential credential = FacebookAuthProvider.getCredential(token.getToken());
        auth.signInWithCredential(credential)
            .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    checkTask(task);
                }
            });
    }

    private void checkTask(Task<AuthResult> task) {
        loadingDialog.dismiss();

        if (!task.isSuccessful()) {
            toastFailSignIn();
        } else {
            toastSuccessSignIn();
            // TODO: goto main activity
        }
    }

    private void toastFailSignIn() {
        Toast.makeText(SignInActivity.this, R.string.sign_in_fail,
            Toast.LENGTH_SHORT).show();
    }

    private void toastSuccessSignIn() {
        Toast.makeText(SignInActivity.this, R.string.sign_in_success,
            Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {

    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }
}

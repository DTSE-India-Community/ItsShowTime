package com.huawei.ist.activity;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.huawei.agconnect.auth.AGConnectAuth;
import com.huawei.agconnect.auth.AGConnectAuthCredential;
import com.huawei.agconnect.auth.AGConnectUser;
import com.huawei.agconnect.auth.HwIdAuthProvider;
import com.huawei.agconnect.auth.SignInResult;
import com.huawei.hmf.tasks.OnSuccessListener;
import com.huawei.hmf.tasks.Task;
import com.huawei.hms.common.ApiException;
import com.huawei.hms.support.api.entity.auth.Scope;
import com.huawei.hms.support.api.entity.hwid.HwIDConstant;
import com.huawei.hms.support.hwid.HuaweiIdAuthManager;
import com.huawei.hms.support.hwid.request.HuaweiIdAuthParams;
import com.huawei.hms.support.hwid.request.HuaweiIdAuthParamsHelper;
import com.huawei.hms.support.hwid.result.AuthHuaweiId;
import com.huawei.hms.support.hwid.service.HuaweiIdAuthService;
import com.huawei.ist.R;

import java.util.ArrayList;
import java.util.List;

public class SplashActivty extends AppCompatActivity {

    private AGConnectUser mUser;
    private ProgressBar mProgressBar;
    private Button mLoginButton;
    private static final int SIGN_CODE = 1002;
    private HuaweiIdAuthService mHuaweiIdAuthService;
    private HuaweiIdAuthParams mHuaweiIdAuthParams;
    private AGConnectAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        mProgressBar = findViewById(R.id.progressBar);
        mLoginButton = findViewById(R.id.btnLogin);
        init();
    }

    private void init() {
        mUser = AGConnectAuth.getInstance().getCurrentUser();
        mHuaweiIdAuthParams = new HuaweiIdAuthParamsHelper(HuaweiIdAuthParams.DEFAULT_AUTH_REQUEST_PARAM)
                .setIdToken()
                .setAccessToken()
                .createParams();
        mHuaweiIdAuthService = HuaweiIdAuthManager.getService(SplashActivty.this, mHuaweiIdAuthParams);
        if (mUser!=null){
            Intent intent = new Intent(this,MainActivity.class);
            startActivity(intent);
            mProgressBar.setVisibility(View.INVISIBLE);
        }else {
            mLoginButton.setVisibility(View.VISIBLE);
            mProgressBar.setVisibility(View.INVISIBLE);
            mLoginButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    startActivityForResult(mHuaweiIdAuthService.getSignInIntent(), SIGN_CODE);
                }
            });

        }

    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == SIGN_CODE) {
            Task<AuthHuaweiId> authHuaweiIdTask = HuaweiIdAuthManager.parseAuthResultFromIntent(data);
            if (authHuaweiIdTask.isSuccessful()) {
                AuthHuaweiId huaweiAccount = authHuaweiIdTask.getResult();
                Log.i("TAG", "accessToken:" + huaweiAccount.getAccessToken());
                AGConnectAuthCredential credential = HwIdAuthProvider.credentialWithToken(huaweiAccount.getAccessToken());
                mAuth.signIn(credential).addOnSuccessListener(new OnSuccessListener<SignInResult>() {
                    @Override
                    public void onSuccess(SignInResult signInResult) {

                    }
                });
            } else {
                Log.e("TAG", "sign in failed : " + ((ApiException) authHuaweiIdTask.getException()).getStatusCode());
            }
        }
    }
}
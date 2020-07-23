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

import com.huawei.agconnect.AGConnectInstance;
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
import java.util.Timer;
import java.util.TimerTask;

public class SplashActivty extends AppCompatActivity {

    private AGConnectUser mUser;
    private ProgressBar mProgressBar;
    private Button mLoginButton;
    private static final int SIGN_CODE = 1002;
    private HuaweiIdAuthService mHuaweiIdAuthService;
    private HuaweiIdAuthParams mHuaweiIdAuthParams;
    private TimerTask timer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        grantPermission();
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        AGConnectInstance.initialize(this);
        mProgressBar = findViewById(R.id.progressBar);
        TextView textView = findViewById(R.id.textView);
        textView.setTypeface(Constant.getTypeface(this,1));
        mLoginButton = findViewById(R.id.btnLogin);
        mUser = AGConnectAuth.getInstance().getCurrentUser();
        Timer RunSplash = new Timer();
        timer = new TimerTask() {
            @Override
            public void run() {
                if (mUser!=null){
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            mProgressBar.setVisibility(View.INVISIBLE);
                            Intent intent = new Intent(SplashActivty.this,MovieListActivity.class);
                            startActivity(intent);
                            finish();
                        }
                    });
                }else {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            mLoginButton.setVisibility(View.VISIBLE);
                            mProgressBar.setVisibility(View.INVISIBLE);
                        }
                    });
                }
            }
        };
        RunSplash.schedule(timer, 300);
    }
    public void doLogin(View view){
       idTokenSignIn();
    }

    private void idTokenSignIn() {
        mHuaweiIdAuthParams = new HuaweiIdAuthParamsHelper(HuaweiIdAuthParams.DEFAULT_AUTH_REQUEST_PARAM)
                .setIdToken()
                .setAccessToken()
                .createParams();
        mHuaweiIdAuthService = HuaweiIdAuthManager.getService(SplashActivty.this, mHuaweiIdAuthParams);
        startActivityForResult(mHuaweiIdAuthService.getSignInIntent(), SIGN_CODE);

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
                AGConnectAuth.getInstance().signIn(credential).addOnSuccessListener(new OnSuccessListener<SignInResult>() {
                    @Override
                    public void onSuccess(SignInResult signInResult) {
                        mUser = AGConnectAuth.getInstance().getCurrentUser();
                        SharedPreferenceHelper.setSharedPreferenceObject(SplashActivty.this,"userDetails",mUser);
                        Intent intent = new Intent(SplashActivty.this,MovieListActivity.class);
                        startActivity(intent);
                        finish();
                    }
                });
            } else {
                Log.e("TAG", "sign in failed : " + ((ApiException) authHuaweiIdTask.getException()).getStatusCode());
            }
        }
    }
}
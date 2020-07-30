package com.huawei.ist.activity;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
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
import com.huawei.ist.utility.Constant;
import com.huawei.ist.utility.SharedPreferenceHelper;
import java.util.Timer;
import java.util.TimerTask;

import static android.content.ContentValues.TAG;

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
        mLoginButton.setTypeface(Constant.getTypeface(this,2));
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
        RunSplash.schedule(timer, 500);
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
    private void grantPermission(){
        if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.P) {
            Log.i(TAG, "sdk < 28 Q");
            if (ActivityCompat.checkSelfPermission(this,
                    Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                    && ActivityCompat.checkSelfPermission(this,
                    Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                String[] strings =
                        {Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION};
                ActivityCompat.requestPermissions(this, strings, 1);
            }
        } else {
            if (ActivityCompat.checkSelfPermission(this,
                    Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                    && ActivityCompat.checkSelfPermission(this,
                    Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED
                    && ActivityCompat.checkSelfPermission(this,
                    "android.permission.ACCESS_BACKGROUND_LOCATION") != PackageManager.PERMISSION_GRANTED) {
                String[] strings = {android.Manifest.permission.ACCESS_FINE_LOCATION,
                        android.Manifest.permission.ACCESS_COARSE_LOCATION,
                        "android.permission.ACCESS_BACKGROUND_LOCATION"};
                ActivityCompat.requestPermissions(this, strings, 2);
            }
        }
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 1) {
            if (grantResults.length > 1 && grantResults[0] == PackageManager.PERMISSION_GRANTED
                    && grantResults[1] == PackageManager.PERMISSION_GRANTED) {
                Log.i(TAG, "onRequestPermissionsResult: apply LOCATION PERMISSION successful");
            } else {
                Log.i(TAG, "onRequestPermissionsResult: apply LOCATION PERMISSSION  failed");
            }
        }

        if (requestCode == 2) {
            if (grantResults.length > 2 && grantResults[2] == PackageManager.PERMISSION_GRANTED
                    && grantResults[0] == PackageManager.PERMISSION_GRANTED
                    && grantResults[1] == PackageManager.PERMISSION_GRANTED) {
                Log.i(TAG, "onRequestPermissionsResult: apply ACCESS_BACKGROUND_LOCATION successful");
            } else {
                Log.i(TAG, "onRequestPermissionsResult: apply ACCESS_BACKGROUND_LOCATION  failed");
            }
        }
    }
}




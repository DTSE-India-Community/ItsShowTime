package com.huawei.ist.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.huawei.ist.R;
import com.huawei.ist.utility.JweUtil;

public class BookingDetails extends AppCompatActivity {
    private String passObject;
    private String issuerId;
    private String browserUrl = "https://walletpass-dra.cloud.huawei.com/walletkit/consumer";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.booking_details);
        generateJWEStr();
    }

    private void generateJWEStr() {
        String jweStr = getJweFromAppServer(passObject);
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse(browserUrl + "/pass/save?jwt=" + Uri.encode(jweStr)));
        try {
            startActivity(intent);
        } catch (ActivityNotFoundException e) {
            Log.i("TAG", "HMS error:ActivityNotFoundException");
        }
    }

    private String getJweFromAppServer(String passObject) {
        String jweStr = "";
        try {
            jweStr = JweUtil.generateJwe(issuerId, passObject);
        } catch (Exception e) {
            Toast.makeText(this, "fail ：jwe trans error", Toast.LENGTH_LONG).show();
            Log.i("TAG", "jwe trans error");
            return null;
        }
        Log.i("TAG", "jweStr:" + jweStr);
        return jweStr;
    }
}
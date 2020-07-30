package com.huawei.ist.activity;

import androidx.fragment.app.FragmentActivity;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.huawei.ist.R;
import com.huawei.ist.network.GetDataService;
import com.huawei.ist.network.RetrofitClientInstance;
import com.huawei.ist.utility.util.JweUtil;

public class PaymentActivity extends FragmentActivity {

    private String seats;
    private String passObject = "";
    private String issuerId = "";
    private String browserUrl = "https://walletpass-dra.cloud.huawei.com/walletkit/consumer";
    private TextView mMovieName;
    private TextView mMovieBanner;
    private TextView mSelectedSeats;
    private TextView mTotalAmount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);
        getDetails();
        mMovieName = findViewById(R.id.txtMovieName);
        mMovieBanner = findViewById(R.id.txtMovieBanner);
        mSelectedSeats = findViewById(R.id.txtSeatNo);
        mTotalAmount = findViewById(R.id.txtPrice);
        mMovieName.setText("Movie : " + " sdsdsd");
        mMovieBanner.setText("Theater Name : " + " sdsdsd");
        mSelectedSeats.setText("Selected Seats : " + seats);
        mTotalAmount.setText("100 " + "RS");
        generateJWEStr();
        GetDataService service = RetrofitClientInstance.getRetrofitInstance().create(GetDataService.class);

    }

    private void getDetails() {
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            seats = bundle.getString("seatIds");
        }
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

    public void proceedToPay(View view) {

    }
}
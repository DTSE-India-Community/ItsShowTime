package com.huawei.ist.activity;

import androidx.fragment.app.FragmentActivity;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonParser;
import com.huawei.ist.R;
import com.huawei.ist.network.GetDataService;
import com.huawei.ist.network.RetrofitClientInstance;
import com.huawei.ist.utility.Constant;
import com.huawei.ist.utility.util.JweUtil;

import org.json.JSONException;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PaymentActivity extends FragmentActivity {

    private String seats;
    private String passObject = "";
    private String issuerId = "102594783";
    private String browserUrl = "https://walletpass-dra.cloud.huawei.com/walletkit/consumer";
    private TextView mMovieName,txtHeader;
    private TextView mMovieBanner;
    private TextView mSelectedSeats;
    private TextView mTotalAmount;
    private ProgressBar progressBar;
    private GetDataService service;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_payment);
        getDetails();
        txtHeader = findViewById(R.id.txtHeader);
        progressBar = findViewById(R.id.progressBar);
        txtHeader.setText("PAYMENT DETAILS");
        txtHeader.setTypeface(Constant.getTypeface(this,1));
        mMovieName = findViewById(R.id.txtMovieName);
        mMovieBanner = findViewById(R.id.txtMovieBanner);
        mSelectedSeats = findViewById(R.id.txtSeatNo);
        mTotalAmount = findViewById(R.id.txtPrice);
        mMovieName.setText("Movie : " + " Dil Bechara");
        mMovieBanner.setText("Theater Name : " + " PVR CINEMAS");
        mSelectedSeats.setText("Selected Seats : " + seats);
        mTotalAmount.setText("200 " + "RS");
        //generateJWEStr();
        service = RetrofitClientInstance.getRetrofitInstance().create(GetDataService.class);

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
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_LONG).show();
            System.out.println("ERROR >>>>" + e.getMessage().toString());
            Log.i("TAG", "jwe trans error");
            return null;
        }
        Log.i("TAG", "jweStr:" + jweStr);
        System.out.println("JWE >>>" + jweStr);
        return jweStr;
    }

    public void proceedToPay(View view) {
        progressBar.setVisibility(View.VISIBLE);
        HashMap<String, String> ticket = new HashMap<>();
        ticket.put("seat", seats);
        ticket.put("username","Sanghati Mukherjee");
        Call<Object> call = service.createMovieTicket(ticket);
        call.enqueue(new Callback<Object>() {
            @Override
            public void onResponse(Call<Object> call, Response<Object> response) {

                passObject = new Gson().toJson(JSON.toJSONString(response.body()));
                passObject = "{\"instanceIds\": [\""+response.body().toString().replace(".0","").trim()+"\"]}";
                progressBar.setVisibility(View.GONE);
                generateJWEStr();

            }

            @Override
            public void onFailure(Call<Object> call, Throwable t) {
                Toast.makeText(PaymentActivity.this, t.getMessage(), Toast.LENGTH_LONG).show();
                System.out.println("RESPONSE >>> " + t.getMessage());

                progressBar.setVisibility(View.GONE);
            }
        });
    }
}
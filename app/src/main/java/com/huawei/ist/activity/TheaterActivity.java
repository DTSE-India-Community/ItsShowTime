package com.huawei.ist.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.huawei.ist.R;
import com.huawei.ist.adapter.MovieTheaterAdapter;
import com.huawei.ist.utility.Constant;

import java.util.ArrayList;

public class TheaterActivity extends AppCompatActivity {

    private TextView txtHeader;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_theater);
        txtHeader = findViewById(R.id.txtHeader);
        txtHeader.setTypeface(Constant.getTypeface(this,1));
        txtHeader.setText("CINEMAS");
        Intent intent = getIntent();
        Bundle args = intent.getBundleExtra("BUNDLE");
        ArrayList<String> siteNameList = (ArrayList<String>) args.getSerializable("ARRAYLIST");
        ListView listView = findViewById(R.id.listviewTheater);
        MovieTheaterAdapter customAdapter = new MovieTheaterAdapter(this, siteNameList);
        listView.setAdapter(customAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(TheaterActivity.this, SeatActivity.class);
                startActivity(intent);
            }
        });

    }


}

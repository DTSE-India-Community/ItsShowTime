package com.huawei.ist.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.huawei.ist.R;

import java.util.ArrayList;

public class MovieTheaterAdapter extends BaseAdapter {
    Context context;
    ArrayList<String> siteList = new ArrayList<>();
    LayoutInflater inflter;

    public MovieTheaterAdapter(Context applicationContext, ArrayList<String>  siteListName) {
        this.context = applicationContext;
        this.siteList = siteListName;

        inflter = (LayoutInflater.from(applicationContext));
    }

    @Override
    public int getCount() {
        return siteList.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        view = inflter.inflate(R.layout.theater_item, null);

        TextView txtTheaterName = (TextView) view.findViewById(R.id.txtTheaterName);
        txtTheaterName.setText(siteList.get(i));
        return view;
    }
}
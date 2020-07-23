package com.huawei.ist.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.graphics.drawable.RoundedBitmapDrawable;
import androidx.core.graphics.drawable.RoundedBitmapDrawableFactory;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.google.gson.Gson;
import com.huawei.hmf.tasks.OnFailureListener;
import com.huawei.hmf.tasks.OnSuccessListener;
import com.huawei.hms.location.FusedLocationProviderClient;
import com.huawei.hms.location.HWLocation;
import com.huawei.hms.location.LocationRequest;
import com.huawei.hms.location.LocationServices;
import com.huawei.hms.location.SettingsClient;
import com.huawei.ist.R;
import com.huawei.ist.utility.Utility;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static android.content.ContentValues.TAG;

public class MovieListActivity extends AppCompatActivity {

    private FusedLocationProviderClient fusedLocationProviderClient;
    private SettingsClient settingsClient;
    LocationRequest mLocationRequest;
    private TextView txtWelcomMsg,txtHeader;
    private String movie_image ="poster";
    private String movie_name= "movie";
    private String movie_banner= "banner";
    private String movie_director= "director";
    private String movie_producer = "producer";
    private String movie_cast = "cast";
    private List<HashMap<String, Object>> movieFinalList = new ArrayList<HashMap<String, Object>>();
    private ListView listView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_movie_list);
        txtHeader = findViewById(R.id.txtHeader);
        txtHeader.setText("MOVIES");
        listView = (ListView) findViewById(R.id.listviewMovies);
        txtWelcomMsg = (TextView)findViewById(R.id.txtWelcomMsg);
        txtWelcomMsg.setSelected(true);
        setCity();
        showMovieList();
    }

    private void setCity(){
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
        settingsClient = LocationServices.getSettingsClient(this);
        mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(10000);
        mLocationRequest.setNeedAddress(true);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        fusedLocationProviderClient.getLastLocationWithAddress(mLocationRequest)
                .addOnSuccessListener(new OnSuccessListener<HWLocation>() {
                    @SuppressLint("SetTextI18n")
                    @Override
                    public void onSuccess(HWLocation hwLocation) {
                        System.out.println("CITY >>> " + hwLocation.getCity());
                        txtWelcomMsg.setText("You are right now in " +hwLocation.getCity()+ " location. Grab a pop corn and book a movie because It's Show Time.");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(Exception e) {

                    }
                });
    }

    private void showMovieList(){
        movieFinalList.clear();
        movieFinalList.addAll(Utility.getMovieList());
        final SimpleAdapter simAdapter = new SimpleAdapter(MovieListActivity.this, Utility.getMovieList() , R.layout.movie_item,
                new String[]{movie_image, movie_name, movie_banner,movie_director,movie_producer,movie_cast}, new int[]{
                R.id.poster, R.id.txtMovieName, R.id.txtMovieBanner,R.id.txtMovieDirector,R.id.txtMovieProducer,R.id.txtCasts})
        {
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                View view = convertView;
                for(int i=0;i<movieFinalList.size();i++) {

                        LayoutInflater vi = getLayoutInflater();
                        view = vi.inflate(R.layout.movie_item, null);
                        TextView txtMovieName = view.findViewById(R.id.txtMovieName);
                        TextView txtMovieBanner = view.findViewById(R.id.txtMovieBanner);
                        TextView txtMovieDirector = view.findViewById(R.id.txtMovieDirector);
                        TextView txtMovieProducer = view.findViewById(R.id.txtMovieProducer);
                        TextView txtMovieCast = view.findViewById(R.id.txtCasts);
                        ImageView imgMoviePoster = view.findViewById(R.id.poster);
                        Button btnBookTicket = view.findViewById(R.id.btnBookTicket);
                        String movieName = "<b>Movie: </b> " + movieFinalList.get(position).get(movie_name).toString();
                        String movieBanner = "<b>Banner: </b> " + movieFinalList.get(position).get(movie_banner).toString();
                        String movieDirector = "<b>Director: </b> " + movieFinalList.get(position).get(movie_director).toString();
                        String movieProducer = "<b>Producer: </b> " + movieFinalList.get(position).get(movie_producer).toString();
                        String movieCast = "<b>Cast: </b> " + movieFinalList.get(position).get(movie_cast).toString();

                        txtMovieName.setText(Html.fromHtml(movieName));
                        txtMovieBanner.setText(Html.fromHtml(movieBanner));
                        txtMovieDirector.setText(Html.fromHtml(movieDirector));
                        txtMovieProducer.setText(Html.fromHtml(movieProducer));
                        txtMovieCast.setText(Html.fromHtml(movieCast));
                        if(movieFinalList.get(position).get(movie_image).toString().equalsIgnoreCase("poster1")){
                            imgMoviePoster.setImageDrawable(getResources().getDrawable(R.drawable.poster1));
                        }else if(movieFinalList.get(position).get(movie_image).toString().equalsIgnoreCase("poster2")){
                            imgMoviePoster.setImageDrawable(getResources().getDrawable(R.drawable.poster2));
                        }else if(movieFinalList.get(position).get(movie_image).toString().equalsIgnoreCase("poster3")){
                            imgMoviePoster.setImageDrawable(getResources().getDrawable(R.drawable.poster3));
                        }else if(movieFinalList.get(position).get(movie_image).toString().equalsIgnoreCase("poster4")){
                            imgMoviePoster.setImageDrawable(getResources().getDrawable(R.drawable.poster4));
                        }else if(movieFinalList.get(position).get(movie_image).toString().equalsIgnoreCase("poster5")){
                            imgMoviePoster.setImageDrawable(getResources().getDrawable(R.drawable.poster5));
                        }

                    btnBookTicket.setTag(R.id.txtMovieName,movieFinalList.get(position).get(movie_name));
                    btnBookTicket.setTag(R.id.txtMovieBanner,movieFinalList.get(position).get(movie_banner));
                    btnBookTicket.setTag(R.id.txtMovieDirector,movieFinalList.get(position).get(movie_director));
                    btnBookTicket.setTag(R.id.txtMovieProducer,movieFinalList.get(position).get(movie_producer));
                    btnBookTicket.setTag(R.id.txtCasts,movieFinalList.get(position).get(movie_cast));
                    btnBookTicket.setTag(R.id.poster,movieFinalList.get(position).get(movie_image));

                    btnBookTicket.setOnClickListener(bookTicket());


                }
                return view;
            }
        };
        listView.setAdapter(simAdapter);
        listView.requestLayout();
        simAdapter.notifyDataSetChanged();
    }

    private View.OnClickListener bookTicket(){
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String movieName = (String) v.getTag(R.id.txtMovieName);
                String movieBanner = (String) v.getTag(R.id.txtMovieBanner);
                String movieDirector = (String) v.getTag(R.id.txtMovieDirector);
                String movieProducer = (String) v.getTag(R.id.txtMovieProducer);
                String movieCast = (String) v.getTag(R.id.txtCasts);
                String moviePoster = (String) v.getTag(R.id.poster);
                System.out.println("Movie Name >>>" + movieName);

                


            }
        };
    }
}

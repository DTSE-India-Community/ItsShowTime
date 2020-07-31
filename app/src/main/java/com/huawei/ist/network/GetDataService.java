package com.huawei.ist.network;

import com.huawei.ist.model.LoginModel;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface GetDataService {

    @POST("/createTicket")
    Call<Object> createMovieTicket(@Body HashMap<String, String> map);
}

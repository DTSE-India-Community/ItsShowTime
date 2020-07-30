package com.huawei.ist.network;

import com.huawei.ist.model.LoginModel;

import retrofit2.Call;
import retrofit2.http.POST;

public interface GetDataService {
    @POST("/api")
    Call<LoginModel> getAllPhotos();
}

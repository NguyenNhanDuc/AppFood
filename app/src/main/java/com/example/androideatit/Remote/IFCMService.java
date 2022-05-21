package com.example.androideatit.Remote;

import com.example.androideatit.Model.FCMRespone;
import com.example.androideatit.Model.FCMSendData;

import io.reactivex.Observable;

import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface IFCMService {
    @Headers({
            "Content-Type:application/json",
            "Authorization:key=AAAAbgwPuOI:APA91bGgRlILMGYrfXKc7gsfNKKjDej4OyumBOUKPauhx8UTHcKb6P7tOtWGaPCphPrCfCeHZ4l3FUey9Cq0i9vOSGjBF091L7J_0K7NEZBQfWaMgYomddAgvtAqB-z9uBbp6RKmOb_8"
    })
    @POST("fcm/send")
    Observable<FCMRespone> sendNotification(@Body FCMSendData body);
}

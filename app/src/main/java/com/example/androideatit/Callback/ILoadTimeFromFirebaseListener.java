package com.example.androideatit.Callback;

import com.example.androideatit.Model.Order;

public interface ILoadTimeFromFirebaseListener {
    void onLoadTimeSuccess(Order order, long estimateTimeInMs);
    void onLoadTimeFailed(String message);
}

package com.example.androideatit.Callback;

import com.example.androideatit.Model.Order;

import java.util.List;

public interface ILoadOrderCallbackListener {
    void onLoadOrderSuccess(List<Order>orderList);
    void onLoadOrderFailed(String message);
}

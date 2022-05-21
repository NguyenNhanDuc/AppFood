package com.example.androideatit.Callback;

import com.example.androideatit.Model.BestDealModel;
import com.example.androideatit.Model.PopularCategoryModel;

import java.util.List;

public interface IBestDealCallbackListener {
    void onBestDealLoadSuccess(List<BestDealModel> bestDealModels);
    void onBestDealLoadFailed(String message);
}

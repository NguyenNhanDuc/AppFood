package com.example.androideatit.ui.fooddetail;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.androideatit.Common.Common;
import com.example.androideatit.Model.CommentModel;
import com.example.androideatit.Model.FoodModel;

import java.util.List;

public class FoodDetailViewModel extends ViewModel {

    private MutableLiveData<FoodModel> mutableLiveDataFood;
    private MutableLiveData<CommentModel> mutableLiveDataComment;

    public MutableLiveData<FoodModel> getMutableLiveDataFood() {
        return mutableLiveDataFood;
    }

    public void setMutableLiveDataFood(MutableLiveData<FoodModel> mutableLiveDataFood) {
        this.mutableLiveDataFood = mutableLiveDataFood;
    }

    public MutableLiveData<CommentModel> getMutableLiveDataComment() {
        return mutableLiveDataComment;
    }

    public void setCommentModel(CommentModel commentModel) {
        if(mutableLiveDataComment != null)
            mutableLiveDataComment.setValue(commentModel);
    }

    public FoodDetailViewModel() {
        mutableLiveDataComment = new MutableLiveData<>();
    }

    public MutableLiveData<FoodModel> getMutableLiveDataFoodList() {
        if (mutableLiveDataFood == null) {
            mutableLiveDataFood = new MutableLiveData<>();
        }
        mutableLiveDataFood.setValue(Common.selectedFood);
        return mutableLiveDataFood;
    }

    public void setFoodModel(FoodModel foodModel) {
        if ((mutableLiveDataFood != null))
            mutableLiveDataFood.setValue(foodModel);
    }
}
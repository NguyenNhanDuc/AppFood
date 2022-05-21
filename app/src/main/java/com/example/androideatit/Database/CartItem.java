package com.example.androideatit.Database;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "Cart", primaryKeys = {"uid", "foodId", "foodAddon", "foodSize"})
public class CartItem {

    @NonNull
    @ColumnInfo(name = "foodId")
    private String foodId;

    @ColumnInfo(name = "foodName")
    private String foodName;

    @ColumnInfo(name = "foodImage")
    private String foodImage;

    @ColumnInfo(name = "foodPrice")
    private double foodPrice;

    @ColumnInfo(name = "foodQuantity")
    private int foodQuantity;

    @ColumnInfo(name = "userPhone")
    private String userPhone;

    @ColumnInfo(name = "foodExtraPrice")
    private double foodExtraPrice;

    @NonNull
    @ColumnInfo(name = "foodAddon")
    private String foodAddon;


    @NonNull
    @ColumnInfo(name = "foodSize")
    private String foodSize;

    @NonNull
    @ColumnInfo(name = "uid")
    private String uid;

    public CartItem(@NonNull String foodId, String foodName, String foodImage, double foodPrice,
                    int foodQuantity, String userPhone, double foodExtraPrice, String foodAddon,
                    String foodSize, String uid) {
        this.foodId = foodId;
        this.foodName = foodName;
        this.foodImage = foodImage;
        this.foodPrice = foodPrice;
        this.foodQuantity = foodQuantity;
        this.userPhone = userPhone;
        this.foodExtraPrice = foodExtraPrice;
        this.foodAddon = foodAddon;
        this.foodSize = foodSize;
        this.uid = uid;
    }

    public CartItem() {
    }

    @NonNull
    public String getFoodId() {
        return foodId;
    }

    public void setFoodId(@NonNull String foodId) {
        this.foodId = foodId;
    }

    public String getFoodName() {
        return foodName;
    }

    public void setFoodName(String foodName) {
        this.foodName = foodName;
    }

    public String getFoodImage() {
        return foodImage;
    }

    public void setFoodImage(String foodImage) {
        this.foodImage = foodImage;
    }

    public double getFoodPrice() {
        return foodPrice;
    }

    public void setFoodPrice(double foodPrice) {
        this.foodPrice = foodPrice;
    }

    public int getFoodQuantity() {
        return foodQuantity;
    }

    public void setFoodQuantity(int foodQuantity) {
        this.foodQuantity = foodQuantity;
    }

    public String getUserPhone() {
        return userPhone;
    }

    public void setUserPhone(String userPhone) {
        this.userPhone = userPhone;
    }

    public double getFoodExtraPrice() {
        return foodExtraPrice;
    }

    public void setFoodExtraPrice(double foodExtraPrice) {
        this.foodExtraPrice = foodExtraPrice;
    }

    public String getFoodAddon() {
        return foodAddon;
    }

    public void setFoodAddon(String foodAddon) {
        this.foodAddon = foodAddon;
    }

    public String getFoodSize() {
        return foodSize;
    }

    public void setFoodSize(String foodSize) {
        this.foodSize = foodSize;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    //Ctrl+O
    @Override
    public boolean equals(@Nullable Object obj) {
        if (obj == this)
            return true;
        if (!(obj instanceof CartItem))
                return false;
        CartItem cartItem = (CartItem) obj;
        return cartItem.getFoodId().equals(this.foodId)&&
                cartItem.getFoodAddon().equals(this.foodAddon)&&
                cartItem.getFoodSize().equals(this.foodSize);
    }
}

package com.example.androideatit.EventBus;

public class CounterCartEvent {
    private Boolean success;

    public CounterCartEvent(boolean success) {
        this.success = success;
    }

    public CounterCartEvent() {
    }

    public Boolean isSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }
}

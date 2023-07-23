package com.lethsmarketenterprise.lethsmarket.firebaseinteractor;

public interface FirebaseCallback<T> {
    void onSuccess(T result);
    void onFailure(String errorMessage);
}
package com.lethsmarketenterprise.lethsmarket.firebaseinteractor;

import androidx.lifecycle.LiveData;

import com.lethsmarketenterprise.lethsmarket.models.Product;
import com.lethsmarketenterprise.lethsmarket.models.User;

import java.util.List;

public interface FirebaseInteractor {
    void checkUserExists(String idUser, FirebaseCallback callback);
    void createUser(User user);
    void listProducts(FirebaseCallback callback);
    void insertProduct(FirebaseCallback callback, Product product);
    void getProductById(FirebaseCallback callback, String idProduct);
    void updateProduct(FirebaseCallback callback, Product product);
    void removeProduct();


}

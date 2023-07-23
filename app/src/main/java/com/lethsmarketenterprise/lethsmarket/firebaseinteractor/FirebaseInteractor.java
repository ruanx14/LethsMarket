package com.lethsmarketenterprise.lethsmarket.firebaseinteractor;

import androidx.lifecycle.LiveData;

import com.lethsmarketenterprise.lethsmarket.models.Product;
import com.lethsmarketenterprise.lethsmarket.models.User;

import java.util.List;

public interface FirebaseInteractor {
    void checkUserExists(String idUser, FirebaseCallback callback);
    void createUser(User user);
    LiveData<List<Product>> listProducts();
    LiveData<Boolean> insertProduct(Product product);
    void removeProduct();
    void editProduct();

}

package com.lethsmarketenterprise.lethsmarket.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.lethsmarketenterprise.lethsmarket.firebaseinteractor.FirebaseCallback;
import com.lethsmarketenterprise.lethsmarket.firebaseinteractor.FirebaseInteractorImpl;
import com.lethsmarketenterprise.lethsmarket.models.Product;

import java.util.List;

public class ProductViewModel extends ViewModel {
    private FirebaseInteractorImpl firebaseInteractor;
    private MutableLiveData<Boolean> insertSuccess = new MutableLiveData<>();
    private LiveData<List<Product>> productsLiveData;

    public ProductViewModel() {
        this.firebaseInteractor = new FirebaseInteractorImpl();
        loadProducts();
    }

    public LiveData<Boolean> insertProduct(Product product) {
        return firebaseInteractor.insertProduct(product);
    }
    public LiveData<List<Product>> getProductsLiveData() {
        return productsLiveData;
    }
    public void loadProducts() {
        productsLiveData = firebaseInteractor.listProducts();
    }

    /*
    private MutableLiveData<Boolean> loadingState = new MutableLiveData<>();

    public LiveData<Boolean> getLoadingState() {
        return loadingState;
    }

    public void loadProducts() {
        loadingState.setValue(true);
        firebaseInteractor.listProducts().observeForever(products -> {
            productsLiveData.setValue(products);
            loadingState.setValue(false);
        });
    }
     */
}

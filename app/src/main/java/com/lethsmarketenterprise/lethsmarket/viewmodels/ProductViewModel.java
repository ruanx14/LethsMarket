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
    private MutableLiveData<List<Product>> productsLiveData = new MutableLiveData<>();


    public ProductViewModel() {
        this.firebaseInteractor = new FirebaseInteractorImpl();
        loadProducts();
    }

    public LiveData<Boolean> insertProduct(Product product) {
        MutableLiveData<Boolean> insertSuccessLiveData = new MutableLiveData<>();
        firebaseInteractor.insertProduct(new FirebaseCallback<Boolean>() {
            @Override
            public void onSuccess(Boolean result) {
                insertSuccessLiveData.setValue(result);
            }

            @Override
            public void onFailure(String errorMessage) {
                // Lidar com a falha na inserção do produto
            }
        }, product);

        return insertSuccessLiveData;
    }

    public LiveData<List<Product>> getProductsLiveData() {
        return productsLiveData;
    }
    public void loadProducts() {
        firebaseInteractor.listProducts(new FirebaseCallback<List<Product>>() {
            @Override
            public void onSuccess(List<Product> result) {
                productsLiveData.setValue(result);
            }

            @Override
            public void onFailure(String errorMessage) {
                // Lidar com a falha na obtenção da lista de produtos
            }
        });
    }
    public LiveData<Product> getProductById(String idProduct) {
        MutableLiveData<Product> productLiveData = new MutableLiveData<>();
        firebaseInteractor.getProductById(new FirebaseCallback<Product>() {
            @Override
            public void onSuccess(Product result) {
                productLiveData.setValue(result);
            }

            @Override
            public void onFailure(String errorMessage) {
                // Lidar com a falha na obtenção do produto por ID
            }
        }, idProduct);

        return productLiveData;
    }
    public LiveData<Boolean> updateProduct(Product product){
        MutableLiveData<Boolean> updateSuccessLiveData = new MutableLiveData<>();
        firebaseInteractor.updateProduct(new FirebaseCallback<Boolean>() {
            @Override
            public void onSuccess(Boolean result) {
                updateSuccessLiveData.setValue(result);
            }
            @Override
            public void onFailure(String errorMessage) {

            }
        }, product);
        return updateSuccessLiveData;
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

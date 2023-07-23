package com.lethsmarketenterprise.lethsmarket.view.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.lethsmarketenterprise.lethsmarket.R;
import com.lethsmarketenterprise.lethsmarket.databinding.FragmentProductsBinding;
import com.lethsmarketenterprise.lethsmarket.models.Product;
import com.lethsmarketenterprise.lethsmarket.viewmodels.ProductViewModel;
import com.lethsmarketenterprise.lethsmarket.view.adapters.ProductAdapter;
import com.lethsmarketenterprise.lethsmarket.view.activities.CreateProductActivity;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class ProductsFragment extends Fragment{

    private FragmentProductsBinding binding;
    private FloatingActionButton fabNewProduct;
    private ProductViewModel productViewModel;
    private RecyclerView recyclerViewProducts;
    private ProductAdapter productAdapter;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentProductsBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        productViewModel = new ViewModelProvider(this).get(ProductViewModel.class);


        recyclerViewProducts = binding.recyclerViewProducts;
        recyclerViewProducts.setLayoutManager(new LinearLayoutManager(requireContext()));

        productAdapter = new ProductAdapter(new ArrayList<>());
        recyclerViewProducts.setAdapter(productAdapter);

        productViewModel.getProductsLiveData().observe(getViewLifecycleOwner(), products -> {
            productAdapter.setData(products);
        });


        fabNewProduct = root.findViewById(R.id.fabNewProduct);
        fabNewProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), CreateProductActivity.class);
                startActivity(intent);
            }
        });

        return root;
    }



    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}
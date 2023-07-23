package com.lethsmarketenterprise.lethsmarket.view.activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.widget.Button;

import com.lethsmarketenterprise.lethsmarket.R;
import com.lethsmarketenterprise.lethsmarket.databinding.ActivityCreateProductBinding;
import com.lethsmarketenterprise.lethsmarket.models.Product;
import com.lethsmarketenterprise.lethsmarket.viewmodels.ProductViewModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class CreateProductActivity extends AppCompatActivity {

private ActivityCreateProductBinding binding;
    ProductViewModel productViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCreateProductBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        Button insertButton = findViewById(R.id.buttonInsertProduct);
        insertButton.setOnClickListener(view -> onFormSubmit());

        productViewModel = new ViewModelProvider(this).get(ProductViewModel.class);



        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        if (currentUser == null) {
            Intent intent = new Intent(CreateProductActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        }
    }
    private void onFormSubmit() {
        String name = binding.textInputName.getText().toString();
        String category = binding.textInputCategory.getText().toString();
        String quantity = binding.textInputQuantity.getText().toString();
        String priceSell = binding.textInputPriceSell.getText().toString();
        String priceBought = binding.textInputPriceBought.getText().toString();
        String validity = binding.textInputValidity.getText().toString();
        String brand = binding.textInputBrand.getText().toString();
        String commentProduct = binding.textInputCommentProduct.getText().toString();
        Product product = new Product("null", name, category, quantity, priceSell, priceBought, validity, brand, commentProduct);

        productViewModel.insertProduct(product).observe(this, isSuccess -> {
            if (isSuccess) {
                clearFields();
                showAddMoreConfirmationDialog();
            } else {
                // Falha na inserção
            }
        });

    }
    private void clearFields() {
        binding.textInputName.setText("");
        binding.textInputCategory.setText("");
        binding.textInputQuantity.setText("");
        binding.textInputPriceSell.setText("");
        binding.textInputPriceBought.setText("");
        binding.textInputValidity.setText("");
        binding.textInputBrand.setText("");
        binding.textInputCommentProduct.setText("");
    }
    private void showAddMoreConfirmationDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Adicionar Mais Produtos");
        builder.setMessage("Deseja adicionar mais produtos?");
        builder.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.setNegativeButton("Não", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                startActivity(new Intent(CreateProductActivity.this, HomeActivity.class));
                finish();
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }
}
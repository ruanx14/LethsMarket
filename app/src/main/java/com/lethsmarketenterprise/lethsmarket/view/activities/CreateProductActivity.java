package com.lethsmarketenterprise.lethsmarket.view.activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.widget.Button;
import android.widget.Toast;

import com.lethsmarketenterprise.lethsmarket.R;
import com.lethsmarketenterprise.lethsmarket.databinding.ActivityCreateProductBinding;
import com.lethsmarketenterprise.lethsmarket.models.Product;
import com.lethsmarketenterprise.lethsmarket.viewmodels.ProductViewModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class CreateProductActivity extends AppCompatActivity {

private ActivityCreateProductBinding binding;
    ProductViewModel productViewModel;
    private boolean isEditing = false;
    private String idProduct;
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


        //Edit
        idProduct = getIntent().getStringExtra("idProduct");
        if (idProduct != null) {
            isEditing = true;
            productViewModel.getProductById(idProduct).observe(this, new Observer<Product>() {
                @Override
                public void onChanged(Product product) {
                    binding.textInputName.setText(product.getName());
                    binding.textInputCategory.setText(product.getCategory());
                    binding.textInputQuantity.setText(product.getQuantity());
                    binding.textInputPriceSell.setText(product.getPriceSell());
                    binding.textInputPriceBought.setText(product.getPriceBought());
                    binding.textInputValidity.setText(product.getValidity());
                    binding.textInputBrand.setText(product.getBrand());
                    binding.textInputCommentProduct.setText(product.getComment());
                    binding.textViewProductEngine.setText("Editar produto");
                    binding.buttonInsertProduct.setText("Salvar alteração");
                }
            });
        }

    }
    private void onFormSubmit() {
        Product product = new Product();
        product.setName(binding.textInputName.getText().toString());
        product.setCategory(binding.textInputCategory.getText().toString());
        product.setQuantity(binding.textInputQuantity.getText().toString());
        product.setPriceSell(binding.textInputPriceSell.getText().toString());
        product.setPriceBought(binding.textInputPriceBought.getText().toString());
        product.setValidity(binding.textInputValidity.getText().toString());
        product.setBrand(binding.textInputBrand.getText().toString());
        product.setComment(binding.textInputCommentProduct.getText().toString());
        product.setIdProduct(idProduct);
        //Product product = new Product("null", name, category, quantity, priceSell, priceBought, validity, brand, commentProduct);
        if(isEditing){
            productViewModel.updateProduct(product).observe(this, isSuccess -> {
               if(isSuccess){
                   AlertDialog.Builder builder = new AlertDialog.Builder(this);
                   builder.setMessage("Edição salva com sucesso")
                           .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                               public void onClick(DialogInterface dialog, int id) {
                                   dialog.dismiss();
                                   startActivity(new Intent(CreateProductActivity.this, HomeActivity.class));

                               }
                           });
                   AlertDialog dialog = builder.create();
                   dialog.show();
                   //Toast.makeText(this, "Edição feita com sucesso", Toast.LENGTH_SHORT).show();
               }else{

               }
            });
        }else {
            productViewModel.insertProduct(product).observe(this, isSuccess -> {
                if (isSuccess) {
                    clearFields();
                    showAddMoreConfirmationDialog();
                } else {
                    // Falha na inserção
                }
            });
        }
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
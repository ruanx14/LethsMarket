    package com.lethsmarketenterprise.lethsmarket.firebaseinteractor;

    import androidx.annotation.NonNull;
    import androidx.lifecycle.LiveData;
    import androidx.lifecycle.MutableLiveData;

    import com.lethsmarketenterprise.lethsmarket.models.Product;
    import com.google.firebase.auth.FirebaseAuth;
    import com.google.firebase.auth.FirebaseUser;
    import com.google.firebase.database.DataSnapshot;
    import com.google.firebase.database.DatabaseError;
    import com.google.firebase.database.DatabaseReference;
    import com.google.firebase.database.ValueEventListener;
    import com.lethsmarketenterprise.lethsmarket.models.User;

    import java.util.ArrayList;
    import java.util.List;

    public class FirebaseInteractorImpl extends BaseFirebaseInteractor implements FirebaseInteractor {


        @Override
        public void createUser(User user) {
            DatabaseReference userRef = database.child("users").child(user.getUserId());
            userRef.setValue(user);
        }
        @Override
        public void checkUserExists(String idUser, FirebaseCallback callback) {
            DatabaseReference userRef = database.child("users").child(idUser);
            userRef.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    boolean userExists = dataSnapshot.exists();
                    callback.onSuccess(userExists);
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    callback.onFailure(databaseError.getMessage());
                }
            });
        }

        public String getCurrentUserId() {
            FirebaseUser authUser = FirebaseAuth.getInstance().getCurrentUser();
            if (authUser != null) {
                return authUser.getUid();
            } else {
                return null;
            }
        }
       // ** Products ***********
       public LiveData<List<Product>> listProducts() {
            MutableLiveData<List<Product>> productListLiveData = new MutableLiveData<>();

            String currentUserId = getCurrentUserId();
            DatabaseReference productsRef = database.child("users").child(currentUserId).child("products");
            productsRef.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    List<Product> listProduct = new ArrayList<>();
                    for(DataSnapshot productSnapshot : snapshot.getChildren()){
                        Product product = productSnapshot.getValue(Product.class);
                        listProduct.add(product);
                    }
                    productListLiveData.postValue(listProduct);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    productListLiveData.postValue(null);
                }
            });

            return productListLiveData;
        }

        public LiveData<Boolean> insertProduct(Product product) {
            MutableLiveData<Boolean> insertSuccessLiveData = new MutableLiveData<>();
            String productId = database.push().getKey();
            product.setIdProduct(productId);

            database.child("users").child(getCurrentUserId()).child("products").child(productId).setValue(product)
                    .addOnSuccessListener(e -> insertSuccessLiveData.postValue(true))
                    .addOnFailureListener(e -> insertSuccessLiveData.postValue(false));

            return insertSuccessLiveData;
        }

        @Override
        public void removeProduct() {

        }

        @Override
        public void editProduct() {

        }



    }

    package com.lethsmarketenterprise.lethsmarket.firebaseinteractor;

    import android.util.Log;

    import androidx.annotation.NonNull;
    import androidx.lifecycle.LiveData;
    import androidx.lifecycle.MutableLiveData;

    import com.google.android.gms.tasks.OnFailureListener;
    import com.google.android.gms.tasks.OnSuccessListener;
    import com.google.firebase.firestore.DocumentChange;
    import com.google.firebase.firestore.DocumentReference;
    import com.google.firebase.firestore.DocumentSnapshot;
    import com.google.firebase.firestore.FirebaseFirestore;
    import com.google.firebase.firestore.FirebaseFirestoreException;
    import com.google.firebase.firestore.Query;
    import com.google.firebase.firestore.QuerySnapshot;
    import com.lethsmarketenterprise.lethsmarket.models.Product;
    import com.google.firebase.auth.FirebaseAuth;
    import com.google.firebase.auth.FirebaseUser;
    import com.lethsmarketenterprise.lethsmarket.models.User;

    import java.util.ArrayList;
    import java.util.List;

    public class FirebaseInteractorImpl implements FirebaseInteractor {
        private FirebaseFirestore firestore;
        private FirebaseAuth auth;
        public FirebaseInteractorImpl() {
            firestore = FirebaseFirestore.getInstance();
            auth = FirebaseAuth.getInstance();
        }
        @Override
        public void createUser(User user) {
            firestore.collection("users").document(user.getUserId()).set(user);
        }
        @Override
        public void checkUserExists(String idUser, FirebaseCallback callback) {
            firestore.collection("users").document(idUser).get()
                    .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                        @Override
                        public void onSuccess(DocumentSnapshot documentSnapshot) {
                            boolean userExists = documentSnapshot.exists();
                            callback.onSuccess(userExists);
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            callback.onFailure(e.getMessage());
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
           firestore.collection("users").document(currentUserId)
                   .collection("products")
                   .addSnapshotListener(new com.google.firebase.firestore.EventListener<QuerySnapshot>() {
                       @Override
                       public void onEvent(QuerySnapshot querySnapshot, FirebaseFirestoreException e) {
                           if (e != null) {
                               productListLiveData.postValue(null);
                               return;
                           }

                           List<Product> listProduct = new ArrayList<>();
                           for (DocumentSnapshot documentSnapshot : querySnapshot.getDocuments()) {
                               Product product = documentSnapshot.toObject(Product.class);
                               listProduct.add(product);
                           }
                           Log.d("ProductData", "Products: " + listProduct);

                           productListLiveData.postValue(listProduct);
                       }
                   });

           return productListLiveData;
       }





        public LiveData<Boolean> insertProduct(Product product) {
            MutableLiveData<Boolean> insertSuccessLiveData = new MutableLiveData<>();

            firestore.collection("users").document(getCurrentUserId())
                    .collection("products").add(product)
                    .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                        @Override
                        public void onSuccess(DocumentReference documentReference) {
                            insertSuccessLiveData.postValue(true);
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            insertSuccessLiveData.postValue(false);
                        }
                    });

            return insertSuccessLiveData;
        }

        @Override
        public void removeProduct() {

        }

        @Override
        public void editProduct() {

        }



    }

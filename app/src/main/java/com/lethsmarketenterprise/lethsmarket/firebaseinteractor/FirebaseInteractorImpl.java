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

       // ** Products ********
       public void listProducts(FirebaseCallback callback) {
           String currentUserId = getCurrentUserId();
           firestore.collection("users").document(currentUserId)
                   .collection("products")
                   .get()
                   .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                       @Override
                       public void onSuccess(QuerySnapshot querySnapshot) {
                           List<Product> listProduct = new ArrayList<>();
                           for (DocumentSnapshot documentSnapshot : querySnapshot.getDocuments()) {
                               Product product = documentSnapshot.toObject(Product.class);
                               product.setIdProduct(documentSnapshot.getId());
                               listProduct.add(product);
                           }
                           callback.onSuccess(listProduct);
                       }
                   })
                   .addOnFailureListener(new OnFailureListener() {
                       @Override
                       public void onFailure(@NonNull Exception e) {
                           callback.onFailure(e.getMessage());
                       }
                   });
       }
        public void insertProduct(FirebaseCallback callback,Product product) {
            firestore.collection("users").document(getCurrentUserId())
                    .collection("products").add(product)
                    .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                        @Override
                        public void onSuccess(DocumentReference documentReference) {
                            String documentId = documentReference.getId();
                            product.setIdProduct(documentId);
                            callback.onSuccess(true);
                             /*firestore.collection("users").document(getCurrentUserId())
                                    .collection("products").document(documentId)
                                    .set(product)
                                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void aVoid) {
                                            insertSuccessLiveData.postValue(true);
                                            // Produto adicionado com sucesso com o campo documentId
                                        }
                                    })
                                    .addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            // Lidar com falha na atualização do documento
                                        }
                                    });*/
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            callback.onFailure(e.getMessage());
                        }
                    });
        }
        public void getProductById(FirebaseCallback callback, String idProduct) {
            firestore.collection("users").document(getCurrentUserId())
                    .collection("products").document(idProduct)
                    .get()
                    .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                        @Override
                        public void onSuccess(DocumentSnapshot documentSnapshot) {
                            Product product = documentSnapshot.toObject(Product.class);
                            if (product != null) {
                                product.setIdProduct(documentSnapshot.getId());
                                callback.onSuccess(product);
                            } else {
                                callback.onFailure("Product not found.");
                            }
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            callback.onFailure(e.getMessage());
                        }
                    });
        }
        @Override
        public void updateProduct(FirebaseCallback callback, Product product) {
            String currentUserId = getCurrentUserId();
            firestore.collection("users")
                    .document(currentUserId)
                    .collection("products")
                    .document(product.getIdProduct())
                    .set(product)
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            callback.onSuccess(true);
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            callback.onFailure(e.getMessage());
                        }
                    });
        }
        @Override
        public void removeProduct() {
            /*
            String currentUserId = getCurrentUserId();

            firestore.collection("users")
                .document(currentUserId)
                .collection("products")
                .document(productId)
                .delete()
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        callback.onSuccess(true);
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        callback.onFailure(e.getMessage());
                    }
            });

             */
        }
    }

package com.lethsmarketenterprise.lethsmarket.firebaseinteractor;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public abstract class BaseFirebaseInteractor {
        protected DatabaseReference database;

        public BaseFirebaseInteractor() {
            database = FirebaseDatabase.getInstance().getReference();
        }
}

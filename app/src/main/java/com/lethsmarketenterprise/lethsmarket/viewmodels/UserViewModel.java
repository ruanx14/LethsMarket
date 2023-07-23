package com.lethsmarketenterprise.lethsmarket.viewmodels;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.lethsmarketenterprise.lethsmarket.firebaseinteractor.FirebaseCallback;
import com.lethsmarketenterprise.lethsmarket.firebaseinteractor.FirebaseInteractor;
import com.lethsmarketenterprise.lethsmarket.firebaseinteractor.FirebaseInteractorImpl;
import com.lethsmarketenterprise.lethsmarket.models.User;

public class UserViewModel extends ViewModel {

    private FirebaseAuth auth;
    private MutableLiveData<FirebaseUser> authUserLiveData = new MutableLiveData<>();
    private FirebaseInteractor firebaseInteractor;
    private MutableLiveData<Boolean> logoutSuccessLiveData = new MutableLiveData<>();


    public UserViewModel() {
        auth = FirebaseAuth.getInstance();
        firebaseInteractor = new FirebaseInteractorImpl();
    }
    public LiveData<FirebaseUser> getAuthUserLiveData() {
        return authUserLiveData;
    }
    public LiveData<Boolean> getLogoutSuccessLiveData() {
        return logoutSuccessLiveData;
    }
    public void firebaseAuth(String idToken) {
        AuthCredential credential = GoogleAuthProvider.getCredential(idToken, null);
        auth.signInWithCredential(credential)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        FirebaseUser authUser = auth.getCurrentUser();
                        firebaseInteractor.checkUserExists(authUser.getUid(), new FirebaseCallback<Boolean>() {
                            @Override
                            public void onSuccess(Boolean userExists) {
                                if (!userExists) {
                                    User user = new User(authUser.getUid(), authUser.getDisplayName(), authUser.getPhotoUrl().toString());
                                    firebaseInteractor.createUser(user);
                                }
                                authUserLiveData.setValue(authUser);
                            }

                            @Override
                            public void onFailure(String errorMessage) {
                                // tratativa de erro
                            }
                        });
                    } else {
                        // tratativa de erro
                    }
                });
    }
    public void performLogout(GoogleSignInClient signInClient) {
        signInClient.revokeAccess().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                FirebaseAuth.getInstance().signOut();
                logoutSuccessLiveData.setValue(true);
            } else {
                logoutSuccessLiveData.setValue(false);
            }
        });
    }

}

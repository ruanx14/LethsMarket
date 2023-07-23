package com.lethsmarketenterprise.lethsmarket.view.activities;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.lethsmarketenterprise.lethsmarket.R;
import com.lethsmarketenterprise.lethsmarket.databinding.ActivityMainBinding;
import com.lethsmarketenterprise.lethsmarket.firebaseinteractor.FirebaseCallback;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;
import com.lethsmarketenterprise.lethsmarket.viewmodels.UserViewModel;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding binding;
    private GoogleSignInClient mGoogleSignInClient;
    private static final int RC_SIGN_IN = 40;
    private UserViewModel userViewModel;
        // ...
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        userViewModel = new ViewModelProvider(this).get(UserViewModel.class);

        Properties properties = new Properties();
        try {
            InputStream inputStream = this.getAssets().open("app.properties");
            properties.load(inputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
        String apiKey = properties.getProperty("API_KEY");

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(apiKey)
                        .requestEmail()
                                .build();
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);



            binding.btnGoogleAuth.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    binding.progressBar.setVisibility(View.VISIBLE);
                    binding.btnGoogleAuth.setEnabled(false);
                    SignIn();
                }
            });
            userViewModel.getAuthUserLiveData().observe(this, authUser -> {
                binding.progressBar.setVisibility(View.INVISIBLE);
                binding.btnGoogleAuth.setEnabled(true);
                if (authUser != null) {
                    Intent intent = new Intent(MainActivity.this, HomeActivity.class);
                    startActivity(intent);
                    finish();
                }
            });
    }
    private void SignIn(){
        binding.btnGoogleAuth.setEnabled(false);
        Intent intent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(intent, RC_SIGN_IN);
    }

       @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
           if(requestCode==RC_SIGN_IN){
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try{
                GoogleSignInAccount account = task.getResult(ApiException.class);
                userViewModel.firebaseAuth(account.getIdToken());
            }catch(ApiException e){
                Log.e("GoogleSignIn", "Error during Google Sign-In", e);
                throw new RuntimeException(e);
            }
        }
    }
}


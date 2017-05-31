package com.sdproject.szabi.pizzaclient.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.sdproject.szabi.pizzaclient.R;
import com.sdproject.szabi.pizzaclient.datamodel.User;
import com.sdproject.szabi.pizzaclient.domain.LoginPresenter;
import com.sdproject.szabi.pizzaclient.ui.home.HomeActivity;
import com.sdproject.szabi.pizzaclient.utils.PreferencesManager;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Szabi on 5/27/2017.
 */

public class LoginActivity extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener {

    @BindView(R.id.login_base_layout)
    CoordinatorLayout baseLayout;
    @BindView(R.id.login_image)
    ImageView image;
    @BindView(R.id.login_sign_in)
    SignInButton signIn;

    private static final int RC_SIGN_IN = 3;
    private GoogleApiClient googleApiClient;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);

        User user = PreferencesManager.getInstance(this).getUser();
        if (user != null){
            openHomeScreen();
        }

        /*
        Glide.with(this.getApplicationContext())
                .load("https://firebasestorage.googleapis.com/v0/b/pizzamanager-90645.appspot.com/o/vegetables-italian-pizza-restaurant.jpg?alt=media&token=37c82536-991a-46d0-98ae-4b9c56e2db0e")
                .centerCrop()
                .into(image);
        */
        // Configure sign-in to request the user's ID, email address, and basic
        // profile. ID and basic profile are included in DEFAULT_SIGN_IN.
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        // Build a GoogleApiClient with access to the Google Sign-In API and the
        // options specified by gso.
        googleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this /* FragmentActivity */, this /* OnConnectionFailedListener */)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();
    }

    @Override
    protected void onResume() {
        User user = PreferencesManager.getInstance(this).getUser();
        if (user == null){
            signIn.setVisibility(View.VISIBLE);
        }
        super.onResume();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            handleSignInResult(result);
        }
    }

    @OnClick(R.id.login_sign_in)
    void onSignInClick(){
        signIn();
    }

    private void signIn() {
        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(googleApiClient);
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    private void handleSignInResult(GoogleSignInResult result) {
        Log.d("login", "handleSignInResult:" + result.isSuccess());
        if (result.isSuccess()) {
            // Signed in successfully, show authenticated UI.
            GoogleSignInAccount acct = result.getSignInAccount();

            if (acct!= null) {
                User user = new User(acct.getDisplayName(), acct.getEmail());

                LoginPresenter presenter = new LoginPresenter();
                presenter.addUserToDatabase(user);

                openHomeScreen();
            }
        } else {
            Log.d("login", "sign out");
        }
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        showSnackBar("Connection failed!");
    }

    private void showSnackBar(String msg){
        Snackbar.make(baseLayout, msg, Snackbar.LENGTH_SHORT);
    }

    private void openHomeScreen(){
        startActivity(new Intent(this, HomeActivity.class));
    }
}

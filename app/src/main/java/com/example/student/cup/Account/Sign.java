package com.example.student.cup.Account;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;
import android.util.Log;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;

/**
 * Created by Felix on 2017/10/8.
 */

public class Sign {

    public static final int RC_SIGN_IN = 0;
    public static final String TAG = "GoogleSignDemo";
    private GoogleApiClient mGoogleApiClient;
    private FragmentActivity act;


    public Sign(final FragmentActivity act) {

        this.act = act;


        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();


        mGoogleApiClient = new GoogleApiClient.Builder(act)
//                .enableAutoManage(this /* FragmentActivity */, this /* OnConnectionFailedListener */)
                .enableAutoManage(act, new GoogleApiClient.OnConnectionFailedListener() {
                    @Override
                    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

                        Log.d(TAG, connectionResult.getErrorMessage());

                    }
                })
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();


        SignIn();

//        SignInButton signInButton = (SignInButton) act.findViewById(R.id.sign_in_button);
//        signInButton.setSize(SignInButton.SIZE_STANDARD);
//        signInButton.setScopes(gso.getScopeArray());
//        signInButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//                switch (view.getId()) {
//                    case R.id.sign_in_button:
//                        SignIn();
//                        break;
//                }
//
//            }
//        });


    }


    private void SignIn() {

        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
        act.startActivityForResult(signInIntent, RC_SIGN_IN);

    }


    public void Result(Intent data) {

        GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);

        Log.d(Sign.TAG, "SignInResult:" + result.isSuccess());
        if (result.isSuccess()) {
            // Signed in successfully, show authenticated UI.
            GoogleSignInAccount acct = result.getSignInAccount();

            Info.gEmail = acct.getEmail();
            Info.gDisplayName = acct.getDisplayName();
            Info.gGivenName = acct.getGivenName();
            Info.gFamilyName = acct.getFamilyName();
            Info.gId = acct.getId();
            Info.gPhotoUrl = acct.getPhotoUrl();

            Info.gDisplayNameNick = Info.gDisplayName;
            new GetUrlImg(act/*, iv*/).execute(Info.gPhotoUrl);

        }

    }


}

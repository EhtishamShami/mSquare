package thinktechsol.msquare.activities.buyer;

import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.util.Base64;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.Profile;
import com.facebook.appevents.AppEventsLogger;
import com.facebook.login.*;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.plus.Plus;
import com.google.android.gms.plus.model.people.Person;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Arrays;

import thinktechsol.msquare.R;
import thinktechsol.msquare.activities.SellerDeshBoardActivity;
import thinktechsol.msquare.globels.globels;
import thinktechsol.msquare.model.Buyer.BuyerLogin;
import thinktechsol.msquare.services.buyer.BuyerCustomLogin;
import thinktechsol.msquare.utils.Constant;

public class BuyerLoginActivity extends FragmentActivity implements GoogleApiClient.OnConnectionFailedListener, GoogleApiClient.ConnectionCallbacks {

    ImageView app_logo;
    RelativeLayout two_btn;
    ImageView btn_login, btn_fb, btn_twitter, btn_google, btn_guest, btn_register;
    //    SignInButton btn_google;
    EditText email, password;
    private CallbackManager callbackManager;
    LoginButton fbloginButton;
    private boolean signedInUser;
    private ConnectionResult mConnectionResult;
    // Google client to communicate with Google
    private GoogleApiClient mGoogleApiClient;
    private static final int RC_SIGN_IN = 0;
    private boolean mIntentInProgress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        FacebookSdk.sdkInitialize(getApplicationContext());
        setContentView(R.layout.activity_buyer_login);

        AppEventsLogger.activateApp(this);

        mGoogleApiClient = new GoogleApiClient.Builder(this).addConnectionCallbacks(this).addOnConnectionFailedListener(this).addApi(Plus.API, Plus.PlusOptions.builder().build()).addScope(Plus.SCOPE_PLUS_LOGIN).build();


        //getting app key hashes for the facebook sdk requirements
        try {
            PackageInfo info = getPackageManager().getPackageInfo(
                    "thinktechsol.msquare",
                    PackageManager.GET_SIGNATURES);
            for (Signature signature : info.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                Log.e("KeyHash:", Base64.encodeToString(md.digest(), Base64.DEFAULT));
            }
        } catch (PackageManager.NameNotFoundException e) {

        } catch (NoSuchAlgorithmException e) {

        }


        app_logo = (ImageView) findViewById(R.id.app_logo);
        email = (EditText) findViewById(R.id.email);
        password = (EditText) findViewById(R.id.password);
        btn_login = (ImageView) findViewById(R.id.btn_login);
        btn_fb = (ImageView) findViewById(R.id.btn_fb);
        two_btn = (RelativeLayout) findViewById(R.id.two_btn);
        btn_twitter = (ImageView) findViewById(R.id.btn_twitter);
        btn_google = (ImageView) findViewById(R.id.btn_google);
//        btn_google = (SignInButton) findViewById(R.id.btn_google);
        btn_guest = (ImageView) findViewById(R.id.btn_guest);
        btn_register = (ImageView) findViewById(R.id.btn_register);


        //setting the sizes of the views
        app_logo.setLayoutParams(AppLayoutParam(20.83f, 45.00f, 0, 10, 0, 5, null));
        email.setLayoutParams(AppLayoutParam(5f, 80f, 0, 0, 3, 0, app_logo));
        password.setLayoutParams(AppLayoutParam(5f, 80f, 0, 1, 0, 0, email));
        btn_login.setLayoutParams(AppLayoutParam(6f, 45.20f, 0, 2, 0, 0, password));
        btn_fb.setLayoutParams(AppLayoutParam(10f, 80f, 0, 7, 0, 0, btn_login));
        two_btn.setLayoutParams(AppLayoutParam(10f, 80f, 0, -2, 0, 0, btn_fb));
        btn_twitter.setLayoutParams(AppLayoutParam2(10f, 39f, 0, 0, 1, 0, 0));

        googlePluseLogin();
        btn_google.setLayoutParams(AppLayoutParam2(10f, 39f, 1, 0, 0, 0, R.id.btn_twitter));
//        btn_google.setSize(SignInButton.SIZE_STANDARD);
//        btn_google.setScopes(gso.getScopeArray());

        btn_guest.setLayoutParams(AppLayoutParam(10f, 80f, 0, 6, 0, 0, btn_fb));
        btn_register.setLayoutParams(AppLayoutParam(10f, 80f, 0, -2, 0, 0, btn_guest));


        //action listener on all the views
        btn_login.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View arg0, MotionEvent arg1) {
                int action = arg1.getAction();
                if (action == MotionEvent.ACTION_DOWN) {
                    Constant.makeImageAlphLowOrHigh(btn_login, 0.3f);
                    return true;
                } else if (action == MotionEvent.ACTION_UP) {
                    Constant.makeImageAlphLowOrHigh(btn_login, 1f);

                    if (email.getText().toString().trim().length() > 0 && password.getText().toString().trim().length() > 0) {
                        new BuyerCustomLogin(BuyerLoginActivity.this, BuyerLoginActivity.this, email.getText().toString(), password.getText().toString());
                    } else {
                        Toast.makeText(BuyerLoginActivity.this, "Please Enter Email & Password", Toast.LENGTH_SHORT).show();
                    }
                    return true;
                }
                return false;
            }
        });

        btn_fb.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View arg0, MotionEvent arg1) {
                int action = arg1.getAction();
                if (action == MotionEvent.ACTION_DOWN) {
                    Constant.makeImageAlphLowOrHigh(btn_fb, 0.3f);
                    return true;
                } else if (action == MotionEvent.ACTION_UP) {

                    Constant.logInAs = "facebook";
                    fb_login();

                    Constant.makeImageAlphLowOrHigh(btn_fb, 1f);
                    return true;
                }
                return false;
            }
        });

        btn_twitter.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View arg0, MotionEvent arg1) {
                int action = arg1.getAction();
                if (action == MotionEvent.ACTION_DOWN) {
                    Constant.makeImageAlphLowOrHigh(btn_twitter, 0.3f);
                    return true;
                } else if (action == MotionEvent.ACTION_UP) {

                    Constant.logInAs = "twitter";

                    Constant.makeImageAlphLowOrHigh(btn_twitter, 1f);
                    return true;
                }
                return false;
            }
        });
        btn_register.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View arg0, MotionEvent arg1) {
                int action = arg1.getAction();
                if (action == MotionEvent.ACTION_DOWN) {
                    Constant.makeImageAlphLowOrHigh(btn_register, 0.3f);
                    return true;
                } else if (action == MotionEvent.ACTION_UP) {

//                    Toast.makeText(BuyerLoginActivity.this, "Register btn clicked", Toast.LENGTH_SHORT).show();

                    Constant.makeImageAlphLowOrHigh(btn_register, 1f);

                    startActivity(new Intent(BuyerLoginActivity.this, BuyerRegisterationActivity.class));

                    return true;
                }
                return false;
            }
        });

        btn_google.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View arg0, MotionEvent arg1) {
                int action = arg1.getAction();
                if (action == MotionEvent.ACTION_DOWN) {
                    Constant.makeImageAlphLowOrHigh(btn_google, 0.3f);
                    return true;
                } else if (action == MotionEvent.ACTION_UP) {

                    Constant.logInAs = "google";
//                    Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
//                    startActivityForResult(signInIntent, RC_SIGN_IN);

//                    if(mGoogleApiClient!=null){
//                        mGoogleApiClient.disconnect();
//                    }
//
//                    mGoogleApiClient = new GoogleApiClient.Builder(BuyerLoginActivity.this)
//                            .addConnectionCallbacks(BuyerLoginActivity.this)
//                            .addOnConnectionFailedListener(BuyerLoginActivity.this)
//                            .addApi(Plus.API)
//                            .addScope(Plus.SCOPE_PLUS_LOGIN)
//                            .addScope(Plus.SCOPE_PLUS_PROFILE)
//                            .build();
//                    mGoogleApiClient.connect();

                    if (!mGoogleApiClient.isConnecting()) {
                        signedInUser = true;
                        resolveSignInError();
                    }


                    Constant.makeImageAlphLowOrHigh(btn_google, 1f);
                    return true;
                }
                return false;
            }
        });

        btn_guest.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View arg0, MotionEvent arg1) {
                int action = arg1.getAction();
                if (action == MotionEvent.ACTION_DOWN) {
                    Constant.makeImageAlphLowOrHigh(btn_guest, 0.3f);
                    return true;
                } else if (action == MotionEvent.ACTION_UP) {
                    Constant.makeImageAlphLowOrHigh(btn_guest, 1f);

                    Constant.logInAs = "guest";
                    startActivity(new Intent(BuyerLoginActivity.this, HomeActivity.class));
//                    startActivity(new Intent(BuyerLoginActivity.this, SalonDetailsActivity.class));

                    return true;
                }
                return false;
            }
        });


    }

    public RelativeLayout.LayoutParams AppLayoutParam(float height, float width, float mL, float mT, float mR, float mB, View below) {
        RelativeLayout.LayoutParams paramName = new RelativeLayout.LayoutParams(
                getSize("w", width),
                getSize("h", height));
        paramName.addRule(RelativeLayout.CENTER_HORIZONTAL, RelativeLayout.TRUE);
        if (below != null)
            paramName.addRule(RelativeLayout.BELOW, below.getId());
        paramName.setMargins(getSize("h", mL), getSize("h", mT), getSize("h", mR), getSize("h", mB));
        return paramName;
    }

    public RelativeLayout.LayoutParams AppLayoutParam2(float height, float width, float mL, float mT, float mR, float mB, int toRightView) {
        RelativeLayout.LayoutParams paramName = new RelativeLayout.LayoutParams(
                getSize("w", width),
                getSize("h", height));
        // paramName.addRule(RelativeLayout.CENTER_HORIZONTAL, RelativeLayout.TRUE);
//        if (below != null)
//            paramName.addRule(RelativeLayout.BELOW, below.getId());

        if (toRightView != 0)
            paramName.addRule(RelativeLayout.RIGHT_OF, toRightView);
        paramName.setMargins(getSize("h", mL), getSize("h", mT), getSize("h", mR), getSize("h", mB));
        return paramName;
    }

    public int getSize(String dimension, float size) {
        float x = 0;
        if (dimension.equals("w")) {
            x = (size / 100) * Constant.screenWidth;
        } else {
            x = (size / 100) * Constant.screenHeight;
        }
        return (int) x;
    }

    public void transation() {
        Intent sellerDeshBoard = new Intent(BuyerLoginActivity.this, SellerDeshBoardActivity.class);
        startActivity(sellerDeshBoard);
        overridePendingTransition(R.anim.animation_enter, R.anim.animation_leave);
        finish();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

//        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
//        if (requestCode == RC_SIGN_IN) {
//            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
//
        Log.e("BuyerLoginActivity", "sign in activity Result:" + resultCode);
//
//            handleSignInResult(result);

        if (resultCode == RC_SIGN_IN) {
            signedInUser = false;
            mIntentInProgress = false;
            if (!mGoogleApiClient.isConnecting()) {
                mGoogleApiClient.connect();
            }
        } else {
            if (callbackManager != null) {
                callbackManager.onActivityResult(requestCode, resultCode, data);
                Log.e("data", data.toString());
            }
        }
    }


    @Override
    protected void onResume() {
        super.onResume();
        AppEventsLogger.activateApp(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        AppEventsLogger.deactivateApp(this);
    }

    public void fb_login() {
        callbackManager = CallbackManager.Factory.create();

        LoginManager.getInstance().logInWithReadPermissions(this, Arrays.asList("email", "user_photos", "public_profile"));

        LoginManager.getInstance().registerCallback(callbackManager,
                new FacebookCallback<LoginResult>() {
                    @Override
                    public void onSuccess(LoginResult loginResult) {

                        Profile profile = Profile.getCurrentProfile();
                        Log.e("BuyerLoginActivity", "link of profile=" + profile.getProfilePictureUri(100, 100));
                        profile.getName();

                    }

                    @Override
                    public void onCancel() {
                        Toast.makeText(BuyerLoginActivity.this, "onCancel", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onError(FacebookException error) {
                        Toast.makeText(BuyerLoginActivity.this, "onError" + error, Toast.LENGTH_SHORT).show();
                    }
                });
    }


    GoogleSignInOptions gso;

    public void googlePluseLogin() {

//        if (GooglePlayServicesUtil.isGooglePlayServicesAvailable(this) == ConnectionResult.SUCCESS) {
//            if (mGoogleApiClient == null) {
//                Plus.PlusOptions plusOptions = new Plus.PlusOptions.Builder().addActivityTypes(
//                        "http://schemas.google.com/AddActivity", "http://schemas.google.com/ReviewActivity").build();
//                mGoogleApiClient = new GoogleApiClient.Builder(this).addApi(Games.API).addScope(Games.SCOPE_GAMES)
//                        .addApi(Plus.API, plusOptions).addScope(Plus.SCOPE_PLUS_LOGIN)
//                        .addConnectionCallbacks(this).addOnConnectionFailedListener(this).build();
//            }
//            mGoogleApiClient.connect();
//        } else {
//            Toast.makeText(this, "R.string.texteErreurGPlus", Toast.LENGTH_LONG).show();
//        }

//        // Configure sign-in to request the user's ID, email address, and basic
//        // profile. ID and basic profile are included in DEFAULT_SIGN_IN.
//        gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
//                .requestEmail()
//                .build();
//
//        // Build a GoogleApiClient with access to the Google Sign-In API and the
//        // options specified by gso.
//        mGoogleApiClient = new GoogleApiClient.Builder(this)
//                .enableAutoManage(this /* FragmentActivity */, this /* OnConnectionFailedListener */)
//                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
//                .build();
    }

    @Override
    public void onBackPressed() {
        System.exit(0);
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult result) {
        Log.e("BuyerLoginActivity", "sign in onConnectionFailed:" + result);
        if (!result.hasResolution()) {
            GooglePlayServicesUtil.getErrorDialog(result.getErrorCode(), this, 0).show();
            return;
        }

        if (!mIntentInProgress) {
            // store mConnectionResult
            mConnectionResult = result;

            if (signedInUser) {
                resolveSignInError();
            }
        }
    }

    private void handleSignInResult(GoogleSignInResult result) {
        Log.e("BuyerLoginActivity", "sign in top:" + result.isSuccess());
        if (result.isSuccess()) {
            // Signed in successfully, show authenticated UI.
            GoogleSignInAccount acct = result.getSignInAccount();

//            mStatusTextView.setText(getString(R.string.signed_in_fmt, acct.getDisplayName()));
//            updateUI(true);

            Log.e("BuyerLoginActivity", "sign in successful:" + acct.getDisplayName());
        } else {
            // Signed out, show unauthenticated UI.
            //updateUI(false);
        }
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
//        Log.e("BuyerLoginActivity", "sign in onConnected:" + bundle.size());
        signedInUser = false;
        Toast.makeText(this, "Connected", Toast.LENGTH_LONG).show();
        getProfileInformation();
    }

    @Override
    public void onConnectionSuspended(int i) {
        Log.e("BuyerLoginActivity", "sign in onConnectionSuspended:" + i);
        mGoogleApiClient.connect();
    }

    protected void onStart() {
        super.onStart();
        mGoogleApiClient.connect();
    }

    protected void onStop() {
        super.onStop();
        if (mGoogleApiClient.isConnected()) {
            mGoogleApiClient.disconnect();
        }
    }

    private void resolveSignInError() {
        if (mConnectionResult != null) {
            if (mConnectionResult.hasResolution()) {
                try {
                    mIntentInProgress = true;
                    mConnectionResult.startResolutionForResult(this, RC_SIGN_IN);
                } catch (IntentSender.SendIntentException e) {
                    mIntentInProgress = false;
                    mGoogleApiClient.connect();
                }
            }
        }
    }

    private void getProfileInformation() {
        try {
            if (Plus.PeopleApi.getCurrentPerson(mGoogleApiClient) != null) {
                Person currentPerson = Plus.PeopleApi.getCurrentPerson(mGoogleApiClient);
                String personName = currentPerson.getDisplayName();
                String personPhotoUrl = currentPerson.getImage().getUrl();
//                String email = Plus.AccountApi.getAccountName(mGoogleApiClient);
                Toast.makeText(this, "name=" + personName, Toast.LENGTH_LONG).show();
//                username.setText(personName);
//                emailLabel.setText(email);

                //new LoadProfileImage(image).execute(personPhotoUrl);

                // update profile frame with new info about Google Account
                // profile
//                updateProfile(true);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void onLoginCompleted(ArrayList<BuyerLogin> list) {
        globels.getGlobelRef().buyerLoginId = list.get(0).id;
        Constant.logInAs = "customeLogin";
        startActivity(new Intent(BuyerLoginActivity.this, HomeActivity.class));
    }
}

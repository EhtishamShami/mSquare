package thinktechsol.msquare.activities.buyer;

import android.app.Dialog;
import android.content.Intent;
import android.content.IntentSender;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.Profile;
import com.facebook.appevents.AppEventsLogger;
import com.facebook.login.*;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.plus.Plus;
import com.google.android.gms.plus.model.people.Person;
import com.twitter.sdk.android.Twitter;
import com.twitter.sdk.android.core.Callback;
import com.twitter.sdk.android.core.Result;
import com.twitter.sdk.android.core.TwitterAuthConfig;
import com.twitter.sdk.android.core.TwitterAuthToken;
import com.twitter.sdk.android.core.TwitterException;
import com.twitter.sdk.android.core.TwitterSession;
import com.twitter.sdk.android.core.identity.TwitterAuthClient;

import org.json.JSONException;
import org.json.JSONObject;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Arrays;

import io.fabric.sdk.android.Fabric;
import thinktechsol.msquare.R;
import thinktechsol.msquare.activities.SellerDeshBoardActivity;
import thinktechsol.msquare.globels.globels;
import thinktechsol.msquare.model.Buyer.BuyerLogin;
import thinktechsol.msquare.model.Buyer.RegisterModel;
import thinktechsol.msquare.model.Buyer.RegisterRequestModel;
import thinktechsol.msquare.services.buyer.BuyerCustomLogin;
import thinktechsol.msquare.services.buyer.BuyerRegisterationForSocialMedia;
import thinktechsol.msquare.services.buyer.ForgotPasswordService;
import thinktechsol.msquare.services.buyer.UpdateDeviceInfoService;
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
    private static final int RC_SIGN_IN = 100;
    private boolean mIntentInProgress;
    boolean signInBySocialMedia = false;

    SharedPreferences preferences;
    SharedPreferences.Editor editor;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);


        FacebookSdk.sdkInitialize(getApplicationContext());
        callbackManager = CallbackManager.Factory.create();

        setContentView(R.layout.activity_buyer_login);

        PopUpForForgotPassword();

        preferences = PreferenceManager.getDefaultSharedPreferences(this);
        editor = preferences.edit();

        AppEventsLogger.activateApp(this);

        // mGoogleApiClient = new GoogleApiClient.Builder(this).addConnectionCallbacks(this).addOnConnectionFailedListener(this).addApi(Plus.API, Plus.PlusOptions.builder().build()).addScope(Plus.SCOPE_PLUS_LOGIN).build();
        //google plus sign in
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this /* FragmentActivity */, this /* OnConnectionFailedListener */)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();

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
        TextView forgot_password = (TextView) findViewById(R.id.forgot_password);
        btn_login = (ImageView) findViewById(R.id.btn_login);
        btn_fb = (ImageView) findViewById(R.id.btn_fb);
        two_btn = (RelativeLayout) findViewById(R.id.two_btn);
        btn_twitter = (ImageView) findViewById(R.id.btn_twitter);
        btn_google = (ImageView) findViewById(R.id.btn_google);
        btn_guest = (ImageView) findViewById(R.id.btn_guest);
        btn_register = (ImageView) findViewById(R.id.btn_register);


        //setting the sizes of the views
        app_logo.setLayoutParams(AppLayoutParam(20.83f, 45.00f, 0, 10, 0, 5, null));
        email.setLayoutParams(AppLayoutParam(5f, 80f, 0, 0, 3, 0, app_logo));
        password.setLayoutParams(AppLayoutParam(5f, 80f, 0, 1, 0, 0, email));
        btn_login.setLayoutParams(AppLayoutParam(6f, 45.20f, 0, 2, 0, 0, password));
        forgot_password.setLayoutParams(AppLayoutParam(4f, 35.20f, 0, 2, 0, 0, btn_login));
        btn_fb.setLayoutParams(AppLayoutParam(10f, 80f, 0, 5, 0, 0, forgot_password));
        two_btn.setLayoutParams(AppLayoutParam(10f, 80f, 0, -2, 0, 0, btn_fb));
        btn_twitter.setLayoutParams(AppLayoutParam2(10f, 39f, 0, 0, 1, 0, 0));

        // googlePluseLogin();
        btn_google.setLayoutParams(AppLayoutParam2(10f, 39f, 1, 0, 0, 0, R.id.btn_twitter));
//        btn_google.setSize(SignInButton.SIZE_STANDARD);
//        btn_google.setScopes(gso.getScopeArray());

        btn_guest.setLayoutParams(AppLayoutParam(10f, 80f, 0, 6, 0, 0, btn_fb));
        btn_register.setLayoutParams(AppLayoutParam(10f, 80f, 0, -2, 0, 0, btn_guest));

        //    LoginManager.getInstance().logOut();
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

        forgot_password.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                forgotPasswordDialog.show();
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
                    twitter_login();
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

                    signInBySocialMedia = true;
                    Constant.logInAs = "google";
                    googlePluseLogin();

//                    if (!mGoogleApiClient.isConnecting()) {
//                        signedInUser = true;
//                        resolveSignInError();
//                    }
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

        Log.e("BuyerLoginActivity", "sign in activity Result:" + resultCode);
//
//            handleSignInResult(result);


//        if (resultCode == RC_SIGN_IN) {
//            signedInUser = false;
//            mIntentInProgress = false;
//            if (mGoogleApiClient != null) {
//                if (!mGoogleApiClient.isConnecting()) {
//                    mGoogleApiClient.connect();
//                }
//            }
//        }
        if (requestCode == RC_SIGN_IN) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            handleSignInResult(result);
        } else if (Constant.logInAs.equals("facebook")) {
            try {
                if (callbackManager != null) {
                    callbackManager.onActivityResult(requestCode, resultCode, data);
                    Log.e("BuyerLoginActivyt", "fb login=" + data.toString());
                }
            } catch (Exception e) {
            }
        } else {
            Log.e("BuyerLoginActivyt", "twitter login=" + data.getData());
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

    String emailOfFbLogin = "";
    String nameOfFbLogin = null;

    public void fb_login() {

        AccessToken accessToken = AccessToken.getCurrentAccessToken();
        if (accessToken.getCurrentAccessToken() != null)
            LoginManager.getInstance().logOut();

        LoginManager.getInstance().logInWithReadPermissions(this, Arrays.asList("email", "user_photos", "public_profile"));

        LoginManager.getInstance().registerCallback(callbackManager,
                new FacebookCallback<LoginResult>() {
                    @Override
                    public void onSuccess(LoginResult loginResult) {

                        try {
                            //final Profile profile = Profile.getCurrentProfile();
                            // Log.e("BuyerLoginActivity", "link of profile=" + profile.getName());
//                            Log.e("BuyerLoginActivity", "link of profile img=" + profile.getProfilePictureUri(500,500));
//                            Log.e("BuyerLoginActivity", "link of profile img=" +);
                            //profile.getName();

                            // Facebook Email address
                            GraphRequest request = GraphRequest.newMeRequest(
                                    loginResult.getAccessToken(),
                                    new GraphRequest.GraphJSONObjectCallback() {
                                        @Override
                                        public void onCompleted(
                                                JSONObject object,
                                                GraphResponse response) {
                                            Log.e("LoginActivity Response ", "fbb response of fb=" + response);
                                            Log.e("LoginActivity Response ", "fbb object of fb 2=" + object);
                                            try {
                                                if (object.has("email"))
                                                    emailOfFbLogin = object.getString("email");

                                                nameOfFbLogin = object.getString("name");

                                                Log.e("LoginActivity= ", "email of fb login is=" + emailOfFbLogin);
                                                RegisterRequestModel requestModel = new RegisterRequestModel(nameOfFbLogin, nameOfFbLogin, emailOfFbLogin, "", "facebook", "", "1");
                                                new BuyerRegisterationForSocialMedia(BuyerLoginActivity.this, BuyerLoginActivity.this, requestModel);
                                            } catch (JSONException e) {
                                                Toast.makeText(BuyerLoginActivity.this, "Login Failed Please try again=1=" + e, Toast.LENGTH_SHORT).show();
                                                e.printStackTrace();
                                            }
                                        }
                                    });
                            Bundle parameters = new Bundle();
                            parameters.putString("fields", "id,name,email,gender, birthday");
                            request.setParameters(parameters);
                            request.executeAsync();
                            Constant.logInAs = "facebook";

                        } catch (Exception e) {
                            Toast.makeText(BuyerLoginActivity.this, "Login Failed Please try again=====" + e, Toast.LENGTH_SHORT).show();
                            Log.e("BuyerLoginActivity", "catching the exception=" + e);
                        }
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

        Auth.GoogleSignInApi.signOut(mGoogleApiClient).setResultCallback(
                new ResultCallback<Status>() {
                    @Override
                    public void onResult(Status status) {
                        Log.e("BuyerLoginActivity", "google sign out status=" + status);
                        //Toast.makeText(BuyerLoginActivity.this, "google sign out status="+status, Toast.LENGTH_SHORT).show();
                    }
                });
        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
        startActivityForResult(signInIntent, RC_SIGN_IN);


//        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
//                .requestEmail()
//                .build();

//        mGoogleApiClient = new GoogleApiClient.Builder(this)
//                .enableAutoManage(this, this)
//                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
//                .build();

//        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
//        startActivityForResult(signInIntent, RC_SIGN_IN);

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
        finish();
    }


    private void handleSignInResult(GoogleSignInResult result) {
        Log.e("BuyerLoginActivity", "sign in top:" + result.isSuccess());
        if (result.isSuccess()) {
            // Signed in successfully, show authenticated UI.
            GoogleSignInAccount acct = result.getSignInAccount();

            Log.e("BuyerLoginActivity", "google plus sign in successful:" + acct.getDisplayName());

            Log.e("LoginActivity= ", "email of gplus login is=" + acct.getEmail());
//
            RegisterRequestModel requestModel = new RegisterRequestModel(acct.getDisplayName(), "", acct.getEmail(), "", "google", "", "1");
            new BuyerRegisterationForSocialMedia(BuyerLoginActivity.this, BuyerLoginActivity.this, requestModel);

        } else {
            Toast.makeText(this, "Error in signing in", Toast.LENGTH_SHORT).show();
        }
    }


    protected void onStart() {
        super.onStart();
//        mGoogleApiClient.connect();
    }

//    protected void onStop() {
//        super.onStop();
////        if (mGoogleApiClient.isConnected()) {
////            mGoogleApiClient.disconnect();
////        }
//    }

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

        globels.getGlobelRef().MessageType = "2";
        globels.getGlobelRef().buyerLoginId = list.get(0).id;
        globels.getGlobelRef().buyerLoginResponse = list;

        globels.getGlobelRef().loginAsBuyerOrSeller = "buyer";

        String address = list.get(0).houseNo + " " + list.get(0).streetNo + " " + list.get(0).area + " " + list.get(0).state + " " + list.get(0).phoneNo;
        Log.e("BuyerLoginActivity", "address=" + address);

        editor.putString("houseNo", list.get(0).houseNo);
        editor.putString("streetNo", list.get(0).streetNo);
        editor.putString("area", list.get(0).area);
        editor.putString("state", list.get(0).state);
        editor.putString("phoneNo", list.get(0).phoneNo);


        editor.putString("buyerLoginId", list.get(0).id);
        editor.putString("buyeremailaddress", list.get(0).email);
        editor.putBoolean("isBuyerLogin", true);
        editor.commit();


        new UpdateDeviceInfoService(BuyerLoginActivity.this, "buyer", globels.getGlobelRef().buyerLoginId, globels.getGlobelRef().deviceToken);


        Constant.logInAs = "customeLogin";
        startActivity(new Intent(BuyerLoginActivity.this, HomeActivity.class));
    }

    public void onRegistrationCompletedOfSocialMed(ArrayList<RegisterModel> list) {

    }

    //google plus
    @Override
    public void onConnected(@Nullable Bundle bundle) {
        //if (signInBySocialMedia) {
        //   signedInUser = false;
        Toast.makeText(this, "google plus Connected", Toast.LENGTH_LONG).show();
//            getProfileInformation();
//            Constant.logInAs = "googleplus";
//            startActivity(new Intent(BuyerLoginActivity.this, HomeActivity.class));
//            finish();
//        }
    }

    @Override
    public void onConnectionSuspended(int i) {
        Log.e("BuyerLoginActivity", "sign in onConnectionSuspended:" + i);
//        mGoogleApiClient.connect();
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult result) {
        Log.e("BuyerLoginActivity", "sign in onConnectionFailed:" + result);
//        if (!result.hasResolution()) {
//            GooglePlayServicesUtil.getErrorDialog(result.getErrorCode(), this, 0).show();
//            return;
//        }
//
//        if (!mIntentInProgress) {
//            mConnectionResult = result;
//            if (signedInUser) {
//                resolveSignInError();
//            }
//        }
    }


    Dialog forgotPasswordDialog;

    public void PopUpForForgotPassword() {
        forgotPasswordDialog = new Dialog(BuyerLoginActivity.this);
        forgotPasswordDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        forgotPasswordDialog.setCancelable(false);

        LayoutInflater inflater = (LayoutInflater) getLayoutInflater();
        View customView = inflater.inflate(R.layout.dialog_forgot_password, null);

        forgotPasswordDialog.setContentView(customView);

        int width = (int) (getResources().getDisplayMetrics().widthPixels * 0.95);
        int height = (int) (getResources().getDisplayMetrics().heightPixels * 0.3);
        forgotPasswordDialog.getWindow().setLayout(width, height);

        final EditText email = (EditText) forgotPasswordDialog.findViewById(R.id.staffET);

        Button OkBtn = (Button) forgotPasswordDialog.findViewById(R.id.OkBtn);
        OkBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new ForgotPasswordService(BuyerLoginActivity.this, BuyerLoginActivity.this, email.getText().toString().trim());
            }
        });

        Button CancelBtn = (Button) forgotPasswordDialog.findViewById(R.id.CancelBtn);
        CancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                forgotPasswordDialog.dismiss();
            }
        });

//        forgotPasswordDialog.show();
    }

    public void onForgotPassRequestSubmitted() {
        if (forgotPasswordDialog != null)
            forgotPasswordDialog.dismiss();
    }

    private static final String TWITTER_KEY = "PKcsC7sCMj7mlt5W50hLlv5fk";
    private static final String TWITTER_SECRET = "ZhawpbsZSHuYRN9ohZ7iGBRKenZjv7gLSB3QT05vs67Z2m4tGp";
    private TwitterAuthClient client;

    public void twitter_login() {
        TwitterAuthConfig authConfig = new TwitterAuthConfig(TWITTER_KEY, TWITTER_SECRET);
        Fabric.with(this, new Twitter(authConfig));

//        TwitterSession session = Twitter.getSessionManager().getActiveSession();
//        TwitterAuthToken authToken = session.getAuthToken();
//        String token = authToken.token;
//        String secret = authToken.secret;

//        Log.e("BuyerLogin","token="+token);
//        Log.e("BuyerLogin","secret="+secret);



        client = new TwitterAuthClient();

        client.authorize(BuyerLoginActivity.this, new Callback<TwitterSession>() {
            @Override
            public void success(Result<TwitterSession> twitterSessionResult) {
                Toast.makeText(BuyerLoginActivity.this, "success", Toast.LENGTH_SHORT).show();

//                twitterSessionResult.data.getUserName();
            }

            @Override
            public void failure(TwitterException e) {
                Toast.makeText(BuyerLoginActivity.this, "failure=" + e, Toast.LENGTH_SHORT).show();
            }
        });
    }
}

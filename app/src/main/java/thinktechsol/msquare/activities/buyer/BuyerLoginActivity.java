package thinktechsol.msquare.activities.buyer;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
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
import com.facebook.HttpMethod;
import com.facebook.Profile;
import com.facebook.appevents.AppEventsLogger;
import com.facebook.login.*;
import com.facebook.login.widget.LoginButton;

import org.json.JSONException;
import org.json.JSONObject;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

import thinktechsol.msquare.R;
import thinktechsol.msquare.activities.SellerDeshBoardActivity;
import thinktechsol.msquare.activities.SellerLoginActivity;
import thinktechsol.msquare.activities.UserTypeActivity;
import thinktechsol.msquare.services.sellerLogIn;
import thinktechsol.msquare.utils.Constant;

public class BuyerLoginActivity extends Activity {

    ImageView app_logo;
    RelativeLayout two_btn;
    ImageView btn_login, btn_fb, btn_twitter, btn_google, btn_guest;
    EditText email, password;
    private CallbackManager callbackManager;
    LoginButton fbloginButton;

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
        btn_guest = (ImageView) findViewById(R.id.btn_guest);


        //setting the sizes of the views
        app_logo.setLayoutParams(AppLayoutParam(20.83f, 45.00f, 0, 10, 0, 5, null));
        email.setLayoutParams(AppLayoutParam(5f, 80f, 0, 0, 3, 0, app_logo));
        password.setLayoutParams(AppLayoutParam(5f, 80f, 0, 1, 0, 0, email));
        btn_login.setLayoutParams(AppLayoutParam(6f, 45.20f, 0, 2, 0, 0, password));
        btn_fb.setLayoutParams(AppLayoutParam(10f, 80f, 0, 7, 0, 0, btn_login));
        two_btn.setLayoutParams(AppLayoutParam(10f, 80f, 0, -2, 0, 0, btn_fb));
        btn_twitter.setLayoutParams(AppLayoutParam2(10f, 39f, 0, 0, 1, 0, 0));
        btn_google.setLayoutParams(AppLayoutParam2(10f, 39f, 1, 0, 0, 0, R.id.btn_twitter));
        btn_guest.setLayoutParams(AppLayoutParam(10f, 80f, 0, 6, 0, 0, btn_fb));


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

        btn_google.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View arg0, MotionEvent arg1) {
                int action = arg1.getAction();
                if (action == MotionEvent.ACTION_DOWN) {
                    Constant.makeImageAlphLowOrHigh(btn_guest, 0.3f);
                    return true;
                } else if (action == MotionEvent.ACTION_UP) {

                    Constant.logInAs = "google";

                    Constant.makeImageAlphLowOrHigh(btn_guest, 1f);
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
        callbackManager.onActivityResult(requestCode, resultCode, data);
        Log.e("data", data.toString());
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
}

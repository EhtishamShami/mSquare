package thinktechsol.msquare.activities;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.os.PowerManager;
import android.preference.PreferenceManager;
import android.support.v4.content.LocalBroadcastManager;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;



import thinktechsol.msquare.R;
import thinktechsol.msquare.activities.buyer.BuyerLoginActivity;
import thinktechsol.msquare.gcm.GCMRegistrationService;
import thinktechsol.msquare.globels.globels;
import thinktechsol.msquare.utils.Constant;

public class UserTypeActivity extends Activity {

    ImageView type_seller_btn, type_buyer_btn, app_logo;
    int btnSelectorColor;
    protected PowerManager.WakeLock mWakeLock;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);


        setContentView(R.layout.activity_activity_user_type);

        gcm();

        final PowerManager pm = (PowerManager) getSystemService(Context.POWER_SERVICE);
        this.mWakeLock = pm.newWakeLock(PowerManager.SCREEN_DIM_WAKE_LOCK, "My Tag");
        this.mWakeLock.acquire();


        btnSelectorColor = getResources().getColor(R.color.buttonSelectorColor);

        getScreenSize();

        app_logo = (ImageView) findViewById(R.id.app_logo);
        type_seller_btn = (ImageView) findViewById(R.id.type_seller_btn);
        type_buyer_btn = (ImageView) findViewById(R.id.type_buyer_btn);

        type_buyer_btn.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View arg0, MotionEvent arg1) {
                int action = arg1.getAction();
                if (action == MotionEvent.ACTION_DOWN) {
                    Constant.makeImageAlphLowOrHigh(type_buyer_btn, 0.3f);
                    return true;
                } else if (action == MotionEvent.ACTION_UP) {
                    Constant.makeImageAlphLowOrHigh(type_buyer_btn, 1f);
                    Intent sellerLogin = new Intent(UserTypeActivity.this, BuyerLoginActivity.class);
                    startActivity(sellerLogin);
                    overridePendingTransition(R.anim.animation_enter, R.anim.animation_leave);
                    //finish();
                    return true;
                }
                return false;
            }
        });

        type_seller_btn.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View arg0, MotionEvent arg1) {
                int action = arg1.getAction();
                if (action == MotionEvent.ACTION_DOWN) {
                    Constant.makeImageAlphLowOrHigh(type_seller_btn, 0.3f);
                    return true;
                } else if (action == MotionEvent.ACTION_UP) {
                    Constant.makeImageAlphLowOrHigh(type_seller_btn, 1f);
                    Intent sellerLogin = new Intent(UserTypeActivity.this, SellerLoginActivity.class);
                    startActivity(sellerLogin);
                    overridePendingTransition(R.anim.animation_enter, R.anim.animation_leave);
                    //finish();

                    return true;
                }
                return false;
            }
        });


        app_logo.setLayoutParams(AppLayoutParam(20.83f, 45.00f, 0, 0, 0, 10, null));
        type_buyer_btn.setLayoutParams(AppLayoutParam(37.08f, 49.58f, 0, 10, 0, 0, app_logo));
        type_seller_btn.setLayoutParams(AppLayoutParam(37.08f, 49.58f, 0, 5, 0, 0, type_buyer_btn));
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

    public void getScreenSize() {
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        Constant.screenWidth = dm.widthPixels;
        Constant.screenHeight = dm.heightPixels;

        Log.e("MainActivity", "width=" + Constant.screenWidth + " height=" + Constant.screenHeight);
    }

    public int getSize(String dimension, float size) {
        float x = 0;
        if (dimension.equals("w")) {
            x = (size / 100) * Constant.screenWidth;
        } else {
            x = (size / 100) * Constant.screenWidth;
        }
        return (int) x;
    }


    //Creating a broadcast receiver for gcm registration
    private BroadcastReceiver mRegistrationBroadcastReceiver;
    SharedPreferences preferences;
    SharedPreferences.Editor editor;


    public void gcm() {
        //Initializing our broadcast receiver
        mRegistrationBroadcastReceiver = new BroadcastReceiver() {

            //When the broadcast received
            //We are sending the broadcast from GCMRegistrationIntentService

            @Override
            public void onReceive(Context context, Intent intent) {
                //If the broadcast has received with success
                //that means device is registered successfully
                if (intent.getAction().equals(GCMRegistrationService.REGISTRATION_SUCCESS)) {
                    //Getting the registration token from the intent
                    String token = intent.getStringExtra("token");

                    preferences = PreferenceManager.getDefaultSharedPreferences(UserTypeActivity.this);
                    editor = preferences.edit();

                    //String gcmToken = preferences.getString("token", "");

                    editor = preferences.edit();
                    editor.putString("token", token);
                    editor.commit();
                    //Displaying the token as toast
                    //Toast.makeText(getApplicationContext(), "Registration token:" + token, Toast.LENGTH_LONG).show();

                    globels.getGlobelRef().deviceToken = token;

                    //if the intent is not with success then displaying error messages
                } else if (intent.getAction().equals(GCMRegistrationService.REGISTRATION_ERROR)) {
                   // Toast.makeText(getApplicationContext(), "GCM registration error!", Toast.LENGTH_LONG).show();
                } else {
                   // Toast.makeText(getApplicationContext(), "Error occurred", Toast.LENGTH_LONG).show();
                }
            }
        };

        //Checking play service is available or not
        int resultCode = GooglePlayServicesUtil.isGooglePlayServicesAvailable(getApplicationContext());

        //if play service is not available
        if (ConnectionResult.SUCCESS != resultCode) {
            //If play service is supported but not installed
            if (GooglePlayServicesUtil.isUserRecoverableError(resultCode)) {
                //Displaying message that play service is not installed
                Toast.makeText(getApplicationContext(), "Google Play Service is not install/enabled in this device!", Toast.LENGTH_LONG).show();
                GooglePlayServicesUtil.showErrorNotification(resultCode, getApplicationContext());

                //If play service is not supported
                //Displaying an error message
            } else {
                Toast.makeText(getApplicationContext(), "This device does not support for Google Play Service!", Toast.LENGTH_LONG).show();
            }

            //If play service is available
        } else {
            //Starting intent to register device
            Intent itent = new Intent(this, GCMRegistrationService.class);
            startService(itent);
        }
    }

    //Registering receiver on activity resume
    @Override
    protected void onResume() {
        super.onResume();
        Log.w("MainActivity", "onResume");
        LocalBroadcastManager.getInstance(this).registerReceiver(mRegistrationBroadcastReceiver,
                new IntentFilter(GCMRegistrationService.REGISTRATION_SUCCESS));
        LocalBroadcastManager.getInstance(this).registerReceiver(mRegistrationBroadcastReceiver,
                new IntentFilter(GCMRegistrationService.REGISTRATION_ERROR));


    }


    //Unregistering receiver on activity paused
    @Override
    protected void onPause() {
        super.onPause();
        Log.w("MainActivity", "onPause");
        LocalBroadcastManager.getInstance(this).unregisterReceiver(mRegistrationBroadcastReceiver);
    }
}

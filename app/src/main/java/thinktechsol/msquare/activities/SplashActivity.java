package thinktechsol.msquare.activities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.util.Base64;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;

import com.twitter.sdk.android.Twitter;
import com.twitter.sdk.android.core.TwitterAuthConfig;
import io.fabric.sdk.android.Fabric;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;


import thinktechsol.msquare.R;
import thinktechsol.msquare.activities.buyer.HomeActivity;
import thinktechsol.msquare.globels.globels;
import thinktechsol.msquare.services.buyer.GetBuyerDetails2;
import thinktechsol.msquare.utils.Constant;

public class SplashActivity extends Activity {

    // Note: Your consumer key and secret should be obfuscated in your source code before shipping.
    private static final String TWITTER_KEY = "6pCyVWGMEFUAOeh9sxrrfYs7r";
    private static final String TWITTER_SECRET = "9rzvyyBAGapc9tFFBdqKwohCEykmsQAmh6cfMwS9hmH5qvSnCE";


    SharedPreferences preferences;
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        TwitterAuthConfig authConfig = new TwitterAuthConfig(TWITTER_KEY, TWITTER_SECRET);
        Fabric.with(this, new Twitter(authConfig));
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash);

        getScreenSize();
        preferences = PreferenceManager.getDefaultSharedPreferences(this);
        editor = preferences.edit();

        String gcmToken = preferences.getString("token", "");
        boolean isSellerLogedIn = preferences.getBoolean("isSellerLogin", false);
        boolean isBuyerLogedIn = preferences.getBoolean("isBuyerLogin", false);

//        editor = preferences.edit();
//        editor.putBoolean("isSellerLogin", true);
//        editor.putBoolean("isBuyerLogin", true);
//        editor.putString("token", token);
//        editor.commit();

        globels.getGlobelRef().deviceToken = gcmToken;

//		boolean ranBefore = preferences.getBoolean("RanBefore", false);
//		if (!ranBefore) {
//			// first time
//			SharedPreferences.Editor editor = preferences.edit();
//			editor.putBoolean("RanBefore", true);
//			editor.commit();
//		}

        //startService(new Intent(getApplicationContext(),GCMRegistrationService.class));

//        ImageView splashImage = (ImageView) findViewById(R.id.splashImg);
//        splashImage.setBackgroundResource(R.drawable.splash_bg);

//		preferences = PreferenceManager.getDefaultSharedPreferences(this);
//		editor = preferences.edit();
//		verified = preferences.getBoolean("verification", false);

//        animater = AnimationUtils.loadAnimation(getApplicationContext(),
//                R.anim.animation_enter);
//        splashImage.startAnimation(animater);

        globels.getGlobelRef().deviceToken = preferences.getString("token", "");
//        Toast.makeText(SplashActivity.this, "token is 1=" + preferences.getString("token", ""), Toast.LENGTH_SHORT).show();
        //Toast.makeText(SplashActivity.this, "token is 2=" + globels.getGlobelRef().deviceToken, Toast.LENGTH_SHORT).show();

        Log.e("SplashActivity", "Device Token is=" + globels.getGlobelRef().deviceToken);

        if (!isSellerLogedIn && !isBuyerLogedIn)
            openUserTypeActivty();
        else if (isSellerLogedIn) {

            globels.getGlobelRef().MessageType = "1";
            globels.getGlobelRef().loginAsBuyerOrSeller = "seller";
            globels.getGlobelRef().sellerLoginId = preferences.getString("sellerLoginId", "");
            globels.getGlobelRef().sellerLoginServiceId = preferences.getString("sellerLoginServiceId", "");


            new Handler().postDelayed(new Runnable() {
                public void run() {

                    Intent sellerDeshBoard = new Intent(SplashActivity.this, SellerDeshBoardActivity.class);
                    startActivity(sellerDeshBoard);
                    overridePendingTransition(R.anim.animation_enter, R.anim.animation_leave);
                    finish();
//                    openUserTypeActivty();
                }
            }, 1 * 1000);

        } else if (isBuyerLogedIn) {

            globels.getGlobelRef().MessageType = "2";
            globels.getGlobelRef().loginAsBuyerOrSeller = "buyer";
            globels.getGlobelRef().buyerLoginId = preferences.getString("buyerLoginId", "");


//            globels.getGlobelRef().houseNo = preferences.getString("houseNo", "");
//            globels.getGlobelRef().streetNo = preferences.getString("streetNo", "");
//            globels.getGlobelRef().area = preferences.getString("area", "");
//            globels.getGlobelRef().state = preferences.getString("state", "");
//            globels.getGlobelRef().phoneNo = preferences.getString("phoneNo", "");

            new GetBuyerDetails2(SplashActivity.this, globels.getGlobelRef().buyerLoginId);

            Constant.logInAs = "customeLogin";


            new Handler().postDelayed(new Runnable() {
                public void run() {
                    startActivity(new Intent(SplashActivity.this, HomeActivity.class));
                    finish();
                }
            }, 3 * 1000);

        }
        try {
            PackageInfo info = getPackageManager().getPackageInfo("thinktechsol.msquare", PackageManager.GET_SIGNATURES);
            for (Signature signature : info.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                String hashCode  = Base64.encodeToString(md.digest(), Base64.DEFAULT);
                System.out.println("Print the hashKey for Facebook :"+hashCode);
                Log.d("KeyHash:", Base64.encodeToString(md.digest(), Base64.DEFAULT));
            }
        } catch (PackageManager.NameNotFoundException e) {

        } catch (NoSuchAlgorithmException e) {

        }

    }

    private boolean isFirstTime() {
//		SharedPreferences preferences = getPreferences(MODE_PRIVATE);
//		boolean ranBefore = preferences.getBoolean("RanBefore", false);
//		if (!ranBefore) {
//			// first time
//			SharedPreferences.Editor editor = preferences.edit();
//			editor.putBoolean("RanBefore", true);
//			editor.commit();
//		}
//		return !ranBefore;
        return false;
    }

    public boolean isNetworkAvailable() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }

    @Override
    public void onBackPressed() {

    }

    Animation animater;

    public void openUserTypeActivty() {
        new Handler().postDelayed(new Runnable() {
            public void run() {
                Intent i = new Intent(SplashActivity.this,
                        UserTypeActivity.class);
                startActivity(i);
                overridePendingTransition(R.anim.animation_enter, R.anim.animation_leave);
                finish();
            }
        }, 3 * 1000);
    }

    public void getScreenSize() {
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        Constant.screenWidth = dm.widthPixels;
        Constant.screenHeight = dm.heightPixels;

        Log.e("MainActivity", "width=" + Constant.screenWidth + " height=" + Constant.screenHeight);
    }
}

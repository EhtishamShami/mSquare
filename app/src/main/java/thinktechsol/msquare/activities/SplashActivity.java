package thinktechsol.msquare.activities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;

import thinktechsol.msquare.R;

public class SplashActivity extends Activity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash);



//        ImageView splashImage = (ImageView) findViewById(R.id.splashImg);
//        splashImage.setBackgroundResource(R.drawable.splash_bg);

//		preferences = PreferenceManager.getDefaultSharedPreferences(this);
//		editor = preferences.edit();
//		verified = preferences.getBoolean("verification", false);

//        animater = AnimationUtils.loadAnimation(getApplicationContext(),
//                R.anim.animation_enter);
//        splashImage.startAnimation(animater);

        DashBoard();
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
    public void DashBoard() {
        new Handler().postDelayed(new Runnable() {
            public void run() {
                Intent i = new Intent(SplashActivity.this,
                        UserTypeActivity.class);
                startActivity(i);
                overridePendingTransition( R.anim.animation_enter,R.anim.animation_leave);
                finish();
            }
        }, 3 * 1000);
    }
}

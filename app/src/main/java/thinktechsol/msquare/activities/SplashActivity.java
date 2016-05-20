package thinktechsol.msquare.activities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;

import thinktechsol.msquare.R;

public class SplashActivity extends Activity {


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
//		requestWindowFeature(Window.FEATURE_NO_TITLE);
//		this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		setContentView(R.layout.activity_splash);

//		preferences = PreferenceManager.getDefaultSharedPreferences(this);
//		editor = preferences.edit();
//		verified = preferences.getBoolean("verification", false);
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


	public void DashBoard() {
		new Handler().postDelayed(new Runnable() {
			public void run() {
					Intent i = new Intent(SplashActivity.this,
							UserTypeActivity.class);
					startActivity(i);
					finish();
			}
		}, 3 * 1000);
	}
}

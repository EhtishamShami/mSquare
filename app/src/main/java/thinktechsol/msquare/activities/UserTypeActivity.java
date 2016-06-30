package thinktechsol.msquare.activities;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import thinktechsol.msquare.R;
import thinktechsol.msquare.activities.buyer.BuyerLoginActivity;
import thinktechsol.msquare.utils.Constant;

public class UserTypeActivity extends Activity {

    ImageView type_seller_btn, type_buyer_btn, app_logo;
    int btnSelectorColor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_activity_user_type);
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

}

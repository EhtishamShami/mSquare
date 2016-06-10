package thinktechsol.msquare.activities;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import thinktechsol.msquare.R;
import thinktechsol.msquare.services.sellerLogIn;
import thinktechsol.msquare.utils.Constant;

public class SellerLoginActivity extends Activity {

    ImageView app_logo;
    RelativeLayout submitbg;
    ImageView btn_submit;
    EditText et_login_code;
    sellerLogIn seller;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_seller_login);

        app_logo = (ImageView) findViewById(R.id.app_logo);
        submitbg = (RelativeLayout) findViewById(R.id.submitbg);
        btn_submit = (ImageView) findViewById(R.id.btn_submit);
        et_login_code = (EditText) findViewById(R.id.et_login_code);


        app_logo.setLayoutParams(AppLayoutParam(20.83f, 45.00f, 0, 10, 0, 10, null));
        submitbg.setLayoutParams(AppLayoutParam(43.54f, 85.41f, 0, 5, 0, 10, app_logo));
        btn_submit.setLayoutParams(AppLayoutParam(10f, 44.58f, 0, 0, 0, 0, et_login_code));

        btn_submit.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View arg0, MotionEvent arg1) {
                int action = arg1.getAction();
                if (action == MotionEvent.ACTION_DOWN) {
                    Constant.makeImageAlphLowOrHigh(btn_submit, 0.3f);
                    return true;
                } else if (action == MotionEvent.ACTION_UP) {
                    Constant.makeImageAlphLowOrHigh(btn_submit, 1f);
                    new sellerLogIn(SellerLoginActivity.this,SellerLoginActivity.this, et_login_code.getText().toString());
//                    Intent sellerDeshBoard=new Intent(SellerLoginActivity.this, SellerDeshBoardActivity.class);
//                    startActivity(sellerDeshBoard);
//                    overridePendingTransition(R.anim.animation_enter,R.anim.animation_leave);
//                    finish();

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

    public int getSize(String dimension, float size) {
        float x = 0;
        if (dimension.equals("w")) {
            x = (size / 100) * Constant.screenWidth;
        } else {
            x = (size / 100) * Constant.screenWidth;
        }
        return (int) x;
    }

    public void transation() {
        Intent sellerDeshBoard = new Intent(SellerLoginActivity.this, SellerDeshBoardActivity.class);
        startActivity(sellerDeshBoard);
        overridePendingTransition(R.anim.animation_enter, R.anim.animation_leave);
        finish();
    }
}

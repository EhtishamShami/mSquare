package thinktechsol.msquare.activities;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.preference.PreferenceManager;
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

import org.w3c.dom.Text;

import thinktechsol.msquare.R;
import thinktechsol.msquare.activities.buyer.BuyerLoginActivity;
import thinktechsol.msquare.globels.globels;
import thinktechsol.msquare.services.ForgotPasswordServiceSeller;
import thinktechsol.msquare.services.buyer.ForgotPasswordService;
import thinktechsol.msquare.services.buyer.UpdateDeviceInfoService;
import thinktechsol.msquare.services.sellerLogIn;
import thinktechsol.msquare.utils.Constant;

public class SellerLoginActivity extends Activity {

    ImageView app_logo;
    RelativeLayout submitbg;
    ImageView btn_submit;
    EditText et_login_code;
    sellerLogIn seller;

    SharedPreferences preferences;
    SharedPreferences.Editor editor;

    TextView forgot_code;


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
        forgot_code = (TextView) findViewById(R.id.forgot_code);


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
//                    transation();
                    new sellerLogIn(SellerLoginActivity.this, SellerLoginActivity.this, et_login_code.getText().toString());


//                    Intent sellerDeshBoard=new Intent(SellerLoginActivity.this, ViewSellOrderDetailActivity.class);
//                    startActivity(sellerDeshBoard);
//                    overridePendingTransition(R.anim.animation_enter,R.anim.animation_leave);
//                    finish();

                    return true;
                }
                return false;
            }
        });

        preferences = PreferenceManager.getDefaultSharedPreferences(this);
        editor = preferences.edit();
        PopUpForForgotPassword();

        forgot_code.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                forgotPasswordDialog.show();
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

    public void transation(String id, String serviceId) {
        globels.getGlobelRef().MessageType = "1";
        globels.getGlobelRef().sellerLoginId = id;
        globels.getGlobelRef().sellerLoginServiceId = serviceId;
        globels.getGlobelRef().loginAsBuyerOrSeller = "seller";

        editor.putString("sellerLoginId", id);
        editor.putString("sellerLoginServiceId", serviceId);
        editor.putBoolean("isSellerLogin", true);
        editor.commit();

        new UpdateDeviceInfoService(SellerLoginActivity.this, "seller", globels.getGlobelRef().sellerLoginId, globels.getGlobelRef().deviceToken);
        //new GetSellerDeshBoardStatsService(SellerLoginActivity.this, globels.getGlobelRef().sellerlogin.id);

        Intent sellerDeshBoard = new Intent(SellerLoginActivity.this, SellerDeshBoardActivity.class);
        startActivity(sellerDeshBoard);
        overridePendingTransition(R.anim.animation_enter, R.anim.animation_leave);
        finish();
    }


    Dialog forgotPasswordDialog;

    public void PopUpForForgotPassword() {
        forgotPasswordDialog = new Dialog(SellerLoginActivity.this);
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
                new ForgotPasswordServiceSeller(SellerLoginActivity.this, SellerLoginActivity.this, email.getText().toString().trim());
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
}

package thinktechsol.msquare.activities.buyer;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Base64;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;

import thinktechsol.msquare.Location.GetLocation;
import thinktechsol.msquare.R;
import thinktechsol.msquare.activities.SellerDeshBoardActivity;
import thinktechsol.msquare.globels.globels;
import thinktechsol.msquare.model.Buyer.BuyerLogin;
import thinktechsol.msquare.model.Buyer.RegisterModel;
import thinktechsol.msquare.model.Buyer.RegisterRequestModel;
import thinktechsol.msquare.services.buyer.BuyerCustomLogin;
import thinktechsol.msquare.services.buyer.BuyerRegisteration;
import thinktechsol.msquare.services.buyer.UpdateDeviceInfoService;
import thinktechsol.msquare.utils.Constant;

public class BuyerRegisterationActivity extends Activity {

    ImageView app_logo;
    ImageView btn_registration;
    EditText fname, lname, email, password;

    RelativeLayout titlebarlayout;
    TextView title;
    ImageView backBtn, btn_menu;

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
        setContentView(R.layout.activity_buyer_registeration);

        preferences = PreferenceManager.getDefaultSharedPreferences(this);
        editor = preferences.edit();

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


        titlebarlayout = (RelativeLayout) findViewById(R.id.titlebar);
        title = (TextView) findViewById(R.id.title);
        backBtn = (ImageView) findViewById(R.id.backBtn);
        btn_menu = (ImageView) findViewById(R.id.btn_menu);

        app_logo = (ImageView) findViewById(R.id.app_logo);
        fname = (EditText) findViewById(R.id.fname);
        lname = (EditText) findViewById(R.id.lname);
        email = (EditText) findViewById(R.id.time);
        password = (EditText) findViewById(R.id.password);
        btn_registration = (ImageView) findViewById(R.id.btn_registration);


        // title bar
        backBtn.setLayoutParams(AppLayoutParam2(10f, 10f, 0, 0, 0, 0, null, "ver", 0, "null"));
        btn_menu.setLayoutParams(AppLayoutParam2(12f, 8f, 0, 0, 2, 0, null, "ver", 0, "right"));
//        btn_menu.setVisibility(View.VISIBLE);
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                globels.getGlobelRef().allSelectedServices = "";
                globels.getGlobelRef().SelectedServicesPrice = 0;
                finish();
            }
        });
        btn_menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        title.setText("Register");
        titlebarlayout.setBackgroundColor(this.getResources().getColor(R.color.buyerHomeActivityTitleBarColor));
        titlebarlayout.setLayoutParams(AppLayoutParam2(10.00f, 100f, 0, 0, 0, 0, null, "hor", 0, "null"));
        // title bar end

        //setting the sizes of the views
        app_logo.setLayoutParams(AppLayoutParam(20.83f, 45.00f, 0, 10, 0, 5, titlebarlayout));
        fname.setLayoutParams(AppLayoutParam(5f, 80f, 0, 2.2f, 3, 0, app_logo));
        lname.setLayoutParams(AppLayoutParam(5f, 80f, 0, 2.2f, 3, 0, fname));
        email.setLayoutParams(AppLayoutParam(5f, 80f, 0, 2.2f, 3, 0, lname));
        password.setLayoutParams(AppLayoutParam(5f, 80f, 0, 2.2f, 0, 0, email));
        btn_registration.setLayoutParams(AppLayoutParam(6f, 45.20f, 0, 5, 0, 0, password));


        //action listener on all the views
        btn_registration.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View arg0, MotionEvent arg1) {
                int action = arg1.getAction();
                if (action == MotionEvent.ACTION_DOWN) {
                    Constant.makeImageAlphLowOrHigh(btn_registration, 0.3f);
                    return true;
                } else if (action == MotionEvent.ACTION_UP) {
                    Constant.makeImageAlphLowOrHigh(btn_registration, 1f);

                    GetLocation location = new GetLocation(BuyerRegisterationActivity.this);
//                    location.getCurrentLatLng();
//                    Toast.makeText(BuyerRegisterationActivity.this, "latlng=" + location.getCurrentLatLng(), Toast.LENGTH_SHORT).show();


                    if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email.getText().toString()).matches()) {
                        email.setError("Invalid Email");
                        email.setFocusable(true);
                        email.requestFocus();
                    } else {
                        if (fname.getText().toString().length() > 0 && lname.getText().toString().length() > 0
                                && email.getText().toString().length() > 0
                                && password.getText().toString().length() > 0
                                ) {
                            RegisterRequestModel requestModel = new RegisterRequestModel(fname.getText().toString(), lname.getText().toString(), email.getText().toString(), password.getText().toString(), "customelogin", location.getCurrentLatLng(), "1");
                            new BuyerRegisteration(BuyerRegisterationActivity.this, BuyerRegisterationActivity.this, requestModel);
                        } else {
                            Toast.makeText(BuyerRegisterationActivity.this, "Please Enter the Values first", Toast.LENGTH_SHORT).show();
                        }
                    }


                    return true;
                }
                return false;
            }
        });
    }

    /*
    *
    * center represent CENTER_HORIZONTAL(hor) or CENTER_VERHTICAL(ver)
    * below represent that either this view be below to some other view or not, if not then pass null instead
    *
    * */
    public RelativeLayout.LayoutParams AppLayoutParam2(float height, float width, float mL, float mT, float mR, float mB, View below, String center, int toRightOf, String align) {
        RelativeLayout.LayoutParams paramName = new RelativeLayout.LayoutParams(
                Constant.getSize("w", width),
                Constant.getSize("h", height));


        if (center.equals("hor"))
            paramName.addRule(RelativeLayout.CENTER_HORIZONTAL, RelativeLayout.TRUE);
        else
            paramName.addRule(RelativeLayout.CENTER_VERTICAL, RelativeLayout.TRUE);

        if (toRightOf != 0)
            paramName.addRule(RelativeLayout.RIGHT_OF, toRightOf);

        if (align != "null") {
            if (align.equals("left"))
                paramName.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
            else
                paramName.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
        }

        if (below != null) {
            Log.e("HomeActivity", "belowing is=" + below.getId());
            paramName.addRule(RelativeLayout.BELOW, below.getId());
        }
        paramName.setMargins(Constant.getSize("h", mL), Constant.getSize("h", mT), Constant.getSize("h", mR), Constant.getSize("h", mB));
        return paramName;
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
        Intent sellerDeshBoard = new Intent(BuyerRegisterationActivity.this, SellerDeshBoardActivity.class);
        startActivity(sellerDeshBoard);
        overridePendingTransition(R.anim.animation_enter, R.anim.animation_leave);
        finish();
    }

    public void onRegistrationCompleted(ArrayList<RegisterModel> list) {
        Log.e("BuyerRegistration", "fname=" + list.get(0).fName);


        ////////////
        globels.getGlobelRef().MessageType = "2";
        globels.getGlobelRef().buyerLoginId = list.get(0).id;

        ArrayList<BuyerLogin> listBuyLogInResp = new ArrayList<BuyerLogin>();
        BuyerLogin obj = new BuyerLogin(list.get(0).location, list.get(0).status, list.get(0).state, list.get(0).lName, list.get(0).udid, list.get(0).password, list.get(0).googlePlus,
                list.get(0).fName, list.get(0).phoneNo, list.get(0).houseNo, list.get(0).id, list.get(0).twitter, list.get(0).area, list.get(0).email,
                "", "", list.get(0).facebook, list.get(0).streetNo, list.get(0).datetime, list.get(0).thumb);
        listBuyLogInResp.add(obj);

        globels.getGlobelRef().buyerLoginResponse = listBuyLogInResp;

        globels.getGlobelRef().loginAsBuyerOrSeller = "buyer";

        editor.putString("houseNo", list.get(0).houseNo);
        editor.putString("streetNo", list.get(0).streetNo);
        editor.putString("area", list.get(0).area);
        editor.putString("state", list.get(0).state);
        editor.putString("phoneNo", list.get(0).phoneNo);
        editor.putString("buyerLoginId", list.get(0).id);
        editor.putBoolean("isBuyerLogin", true);
        editor.commit();

        new UpdateDeviceInfoService(BuyerRegisterationActivity.this, "buyer", globels.getGlobelRef().buyerLoginId, globels.getGlobelRef().deviceToken);
        Constant.logInAs = "customeLogin";
        startActivity(new Intent(BuyerRegisterationActivity.this, HomeActivity.class));
        //////////////

        finish();
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.e("BuyerLoginActivity", "sign in activity Result:" + resultCode);
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

    @Override
    public void onBackPressed() {
        finish();
    }
}

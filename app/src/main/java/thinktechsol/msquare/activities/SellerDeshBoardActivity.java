package thinktechsol.msquare.activities;

import android.app.Activity;
import android.app.FragmentTransaction;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import thinktechsol.msquare.R;
import thinktechsol.msquare.fragments.SellerDashBoardProductFragment;
import thinktechsol.msquare.fragments.SellerDashBoardSettingFragment;
import thinktechsol.msquare.utils.Constant;

public class SellerDeshBoardActivity extends Activity {
    TextView title;
    RelativeLayout titlebarlayout, bottombarlayout;
    RelativeLayout fragmentLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_seller_desh_board);

        fragmentLayout = (RelativeLayout) findViewById(R.id.fragmentLayout);

        //initialization
        titlebarlayout = (RelativeLayout) findViewById(R.id.titlebarlayout);
        title = (TextView) findViewById(R.id.title);

        //initialization of bottom views
        bottombarlayout = (RelativeLayout) findViewById(R.id.bottombarlayout);
        ImageView product = (ImageView)findViewById(R.id.product);
        ImageView customer = (ImageView) findViewById(R.id.customer);
        ImageView order = (ImageView)findViewById(R.id.order);
        ImageView message = (ImageView) findViewById(R.id.message);
        ImageView setting = (ImageView) findViewById(R.id.setting);


        titlebarlayout.setBackgroundColor(this.getResources().getColor(R.color.sellerDashBoardTitleBg));
        titlebarlayout.setLayoutParams(AppLayoutParam(10.00f, 100f, 0, 0, 0, 0, null));
        fragmentLayout.setLayoutParams(AppLayoutParam(80.00f, 100f, 0, 10, 0, 0, titlebarlayout));

        title.setText("DashBoard");

        bottombarlayout.setBackgroundColor(this.getResources().getColor(R.color.bottomBarColor));
        //setting the height and width of the views by percent of the screen
        bottombarlayout.setLayoutParams(AppLayoutParam(10.00f, 100f, 0, 0, 0, 0, fragmentLayout));
        product.setLayoutParams(AppLayoutParamLinearLayout(4.75f, 8.29f, 0, 0, 0, 0));
        customer.setLayoutParams(AppLayoutParamLinearLayout(4.75f, 8.29f, 0, 0, 0, 0));
        order.setLayoutParams(AppLayoutParamLinearLayout(4.75f, 8.29f, 0, 0, 0, 0));
        message.setLayoutParams(AppLayoutParamLinearLayout(4.75f, 8.29f, 0, 0, 0, 0));
        setting.setLayoutParams(AppLayoutParamLinearLayout(4.75f, 8.29f, 0, 0, 0, 0));
        product.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SellerDashBoardSettingFragment fragobj = new SellerDashBoardSettingFragment();
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.replace(R.id.fragmentLayout, fragobj);
                transaction.commit();
            }
        });

        setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SellerDashBoardProductFragment fragobj = new SellerDashBoardProductFragment();
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.replace(R.id.fragmentLayout, fragobj);
                transaction.commit();
            }
        });

        if (savedInstanceState == null) {

            SellerDashBoardProductFragment fragobj = new SellerDashBoardProductFragment();
            getFragmentManager().beginTransaction()
                    .add(R.id.fragmentLayout, fragobj).commit();
        }
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
    public LinearLayout.LayoutParams AppLayoutParamLinearLayout(float height, float width, float mL, float mT, float mR, float mB) {
        LinearLayout.LayoutParams paramName = new LinearLayout.LayoutParams(
                getSize("w", width),
                getSize("h", height));
//        paramName.addRule(RelativeLayout.CENTER_HORIZONTAL, RelativeLayout.TRUE);
//        if (below != null)
//            paramName.addRule(RelativeLayout.BELOW, below.getId());
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
}

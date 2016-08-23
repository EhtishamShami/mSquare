package thinktechsol.msquare.activities;

import android.app.Activity;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import thinktechsol.msquare.R;
import thinktechsol.msquare.fragments.SellerCustomerFragment;
import thinktechsol.msquare.fragments.SellerDashBoardMessageFragment;
import thinktechsol.msquare.fragments.SellerDashBoardProductFragment;
import thinktechsol.msquare.fragments.SellerDashBoardSettingFragment;
import thinktechsol.msquare.globels.globels;
import thinktechsol.msquare.utils.Constant;

public class SellerDeshBoardActivity extends Activity {
    public static final String PRODUCT = "product";
    public static final String CUSTOMER = "customer";
    public static final String ORDER = "order";
//    public static final String MESSAGE = "message";
    public static final String SETTING = "setting";
    public static float IconsHeight = 6.00f;
    public static float IconsWidth = 9.55f;
    public static SellerDeshBoardActivity ContextOfActivity = new SellerDeshBoardActivity();
    static TextView title;
    static String DeshBoardTagbackButton = "DeshBoard";
    static String HomeScreenTagbackButton = "HomeScreen";
    static String CategoryScreenTagbackButton = "Category";
    static String AddProductScreenTagbackButton = "AddProduct";
    public static ImageView backBtn;
    RelativeLayout titlebarlayout, bottombarlayout;
    RelativeLayout fragmentLayout;
    static ImageView product, customer, order, message1, setting;
    static int btnSelectorColor;

    public static SellerDeshBoardActivity getContext() {
        return ContextOfActivity;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_seller_desh_board);

//        Fragment_2_items fragmentS1 = new Fragment_2_items();
//        getSupportFragmentManager().beginTransaction().replace(R.id.fragment, fragmentS1).commit();


        btnSelectorColor = getResources().getColor(R.color.buttonSelectorColor);

        fragmentLayout = (RelativeLayout) findViewById(R.id.fragmentLayout);

        //initialization
        titlebarlayout = (RelativeLayout) findViewById(R.id.titlebarlayout);
        title = (TextView) findViewById(R.id.title);
        backBtn = (ImageView) findViewById(R.id.backBtn);
        backBtn.setTag(DeshBoardTagbackButton);
        backBtn.setLayoutParams(AppLayoutParam2(10f, 10f, 0, 0, 0, 0));
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Toast.makeText(SellerDeshBoardActivity.this, ""+backBtn.getTag(), Toast.LENGTH_SHORT).show();
                if (backBtn.getTag().equals(DeshBoardTagbackButton)) {
//                    Toast.makeText(SellerDeshBoardActivity.this, "DeshBoard's Back Button is clicked", Toast.LENGTH_SHORT).show();
//                    Intent add=new Intent(SellerDeshBoardActivity.this,AddOrViewProActivity.class);
//                    startActivity(add);
                    title.setText("DashBoard");
                    //make product button selected
                    product.setColorFilter(btnSelectorColor);
                    finish();
                } else if (backBtn.getTag().equals(AddProductScreenTagbackButton)) {
//                    Toast.makeText(SellerDeshBoardActivity.this, "Category's Back Button is clicked", Toast.LENGTH_SHORT).show();
                    SellerDashBoardProductFragment fragobj = new SellerDashBoardProductFragment();
                    FragmentTransaction transaction = getFragmentManager().beginTransaction();
                    //transaction.setCustomAnimations(android.R.animator.fade_out, android.R.animator.fade_out);
                    transaction.replace(R.id.fragmentLayout, fragobj);
                    transaction.commit();

                    title.setText("DashBoard");
                    MakeItemSelected(PRODUCT);

                    backBtn.setTag(DeshBoardTagbackButton);
                }
            }
        });

        //initialization of bottom views
        bottombarlayout = (RelativeLayout) findViewById(R.id.bottombarlayout);
        product = (ImageView) findViewById(R.id.product);
        customer = (ImageView) findViewById(R.id.customer);
        order = (ImageView) findViewById(R.id.order);
//        message = (ImageView) findViewById(R.id.message);
        setting = (ImageView) findViewById(R.id.setting);


        titlebarlayout.setBackgroundColor(this.getResources().getColor(R.color.sellerDashBoardTitleBg));
        titlebarlayout.setLayoutParams(AppLayoutParam(10.00f, 100f, 0, 0, 0, 0, null));
        fragmentLayout.setLayoutParams(AppLayoutParam(80.00f, 100f, 0, 10, 0, 0, titlebarlayout));

        title.setText("DashBoard");

        bottombarlayout.setBackgroundColor(this.getResources().getColor(R.color.bottomBarColor));
        //setting the height and width of the views by percent of the screen
        bottombarlayout.setLayoutParams(AppLayoutParam(10.00f, 100f, 0, 0, 0, 0, fragmentLayout));
        product.setLayoutParams(AppLayoutParamLinearLayout(IconsHeight, IconsWidth, 0, 0, 0, 0));
        customer.setLayoutParams(AppLayoutParamLinearLayout(IconsHeight + 1f, IconsWidth + 1f, 0, 0, 0, 0));
        order.setLayoutParams(AppLayoutParamLinearLayout(IconsHeight, IconsWidth, 0, 0, 0, 0));
//        message.setLayoutParams(AppLayoutParamLinearLayout(IconsHeight, IconsWidth, 0, 0, 0, 0));
        setting.setLayoutParams(AppLayoutParamLinearLayout(IconsHeight, IconsWidth, 0, 0, 0, 0));

        //make product button selected
        product.setColorFilter(btnSelectorColor);

        product.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MakeItemSelected(PRODUCT);

                title.setText("Product");
                SellerDashBoardProductFragment fragobj = new SellerDashBoardProductFragment();
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.replace(R.id.fragmentLayout, fragobj);
                transaction.commit();
            }
        });
        customer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MakeItemSelected(CUSTOMER);
                title.setText("Customer");
                SellerCustomerFragment fragobj = new SellerCustomerFragment();
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.replace(R.id.fragmentLayout, fragobj);
                transaction.commit();
                SellerDeshBoardActivity.getContext().changeTitle("Customer");
            }
        });
        order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //title.setText("Order");
                MakeItemSelected(ORDER);

               // SellerDeshBoardActivity.getContext().changeTitle("Order");
                globels.getGlobelRef().orderType = "0";
                startActivity(new Intent(SellerDeshBoardActivity.this, SellersOrdersActivity.class));
            }
        });
//        message.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                MakeItemSelected(MESSAGE);
//
//                title.setText("Messages");
//                SellerDashBoardMessageFragment fragobj = new SellerDashBoardMessageFragment();
//                FragmentTransaction transaction = getFragmentManager().beginTransaction();
//                transaction.replace(R.id.fragmentLayout, fragobj);
//                transaction.commit();
//
//            }
//        });

        setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MakeItemSelected(SETTING);

                title.setText("Setting");
                SellerDashBoardSettingFragment fragobj = new SellerDashBoardSettingFragment();
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

    public RelativeLayout.LayoutParams AppLayoutParam2(float height, float width, float mL, float mT, float mR, float mB) {
        RelativeLayout.LayoutParams paramName = new RelativeLayout.LayoutParams(
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

    public void MakeItemSelected(String btnName) {

        product.setColorFilter(null);
        customer.setColorFilter(null);
        order.setColorFilter(null);
//        message.setColorFilter(null);
        setting.setColorFilter(null);

        switch (btnName) {
            case PRODUCT:
                product.setColorFilter(btnSelectorColor);
                break;

            case CUSTOMER:
                customer.setColorFilter(btnSelectorColor);
                break;

            case ORDER:
                order.setColorFilter(btnSelectorColor);
                break;

//            case MESSAGE:
//                message.setColorFilter(btnSelectorColor);
//                break;

            case SETTING:
                setting.setColorFilter(btnSelectorColor);
                break;
        }
    }

    public void changeTitle(String titleString) {
        title.setText("" + titleString);
    }
}

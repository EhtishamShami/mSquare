package thinktechsol.msquare.activities.buyer;

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

import java.util.ArrayList;

import thinktechsol.msquare.R;
import thinktechsol.msquare.activities.SellersOrdersActivity;
import thinktechsol.msquare.fragments.Buyer.BuyerDashBoardFragment;
import thinktechsol.msquare.fragments.Buyer.BuyerDashBoardMessageFragment;
import thinktechsol.msquare.fragments.SellerCustomerFragment;
import thinktechsol.msquare.fragments.SellerDashBoardProductFragment;
import thinktechsol.msquare.fragments.SellerDashBoardSettingFragment;
import thinktechsol.msquare.globels.globels;
import thinktechsol.msquare.model.Buyer.BuyerDeshBoardStatesModel;
import thinktechsol.msquare.services.buyer.GetBuyerDeshBoardStatesService;
import thinktechsol.msquare.utils.Constant;

public class BuyerDeshBoardActivity extends Activity {
    public static final String DESHBOARD = "deshboard_buyer";
    public static final String ORDER = "order_buyer";
    public static final String WISHLIST = "wishlist_buyer";
    public static final String MESSAGE = "message";
    public static final String SETTING = "setting";
    public static float IconsHeight = 6.00f;
    public static float IconsWidth = 9.55f;
    public static BuyerDeshBoardActivity ContextOfActivity = new BuyerDeshBoardActivity();
    static TextView title;
    static String DeshBoardTagbackButton = "DeshBoard";
    static String HomeScreenTagbackButton = "HomeScreen";
    static String CategoryScreenTagbackButton = "Category";
    static String AddProductScreenTagbackButton = "AddProduct";
    public static ImageView backBtn;
    RelativeLayout titlebarlayout, bottombarlayout;
    RelativeLayout fragmentLayout;
    static ImageView deshboard_buyer, order_buyer, wishlist_buyer, message, setting;
    static int btnSelectorColor;

    public static BuyerDeshBoardActivity getContext() {
        return ContextOfActivity;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_buyer_desh_board);

//        new GetBuyerDeshBoardStatesService(BuyerDeshBoardActivity.this, BuyerDeshBoardActivity.this, globels.getGlobelRef().buyerLoginId);
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
                    deshboard_buyer.setColorFilter(btnSelectorColor);
                    finish();
                } else if (backBtn.getTag().equals(AddProductScreenTagbackButton)) {
//                    Toast.makeText(SellerDeshBoardActivity.this, "Category's Back Button is clicked", Toast.LENGTH_SHORT).show();
                    SellerDashBoardProductFragment fragobj = new SellerDashBoardProductFragment();
                    FragmentTransaction transaction = getFragmentManager().beginTransaction();
                    //transaction.setCustomAnimations(android.R.animator.fade_out, android.R.animator.fade_out);
                    transaction.replace(R.id.fragmentLayout, fragobj);
                    transaction.commit();

                    title.setText("DashBoard");
                    MakeItemSelected(DESHBOARD);

                    backBtn.setTag(DeshBoardTagbackButton);
                }
            }
        });

        //initialization of bottom views
        bottombarlayout = (RelativeLayout) findViewById(R.id.bottombarlayout_buyer);
        deshboard_buyer = (ImageView) findViewById(R.id.deshboard_buyer);
        order_buyer = (ImageView) findViewById(R.id.order_buyer);
        wishlist_buyer = (ImageView) findViewById(R.id.wishlist_buyer);
        message = (ImageView) findViewById(R.id.message1);
        setting = (ImageView) findViewById(R.id.setting);


        titlebarlayout.setBackgroundColor(this.getResources().getColor(R.color.sellerDashBoardTitleBg));
        titlebarlayout.setLayoutParams(AppLayoutParam(10.00f, 100f, 0, 0, 0, 0, null));
        fragmentLayout.setLayoutParams(AppLayoutParam(80.00f, 100f, 0, 10, 0, 0, titlebarlayout));

        title.setText("DashBoard");

        bottombarlayout.setBackgroundColor(this.getResources().getColor(R.color.bottomBarColor));
        //setting the height and width of the views by percent of the screen
        bottombarlayout.setLayoutParams(AppLayoutParam(10.00f, 100f, 0, 0, 0, 0, fragmentLayout));
        deshboard_buyer.setLayoutParams(AppLayoutParamLinearLayout(IconsHeight, IconsWidth, 0, 0, 0, 0));
        order_buyer.setLayoutParams(AppLayoutParamLinearLayout(IconsHeight, IconsWidth, 0, 0, 0, 0));
        wishlist_buyer.setLayoutParams(AppLayoutParamLinearLayout(IconsHeight, IconsWidth, 0, 0, 0, 0));
        message.setLayoutParams(AppLayoutParamLinearLayout(IconsHeight, IconsWidth, 0, 0, 0, 0));
        setting.setLayoutParams(AppLayoutParamLinearLayout(IconsHeight, IconsWidth, 0, 0, 0, 0));

        //make product button selected
        deshboard_buyer.setColorFilter(btnSelectorColor);

        deshboard_buyer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MakeItemSelected(DESHBOARD);
                title.setText("DeshBoard");

                BuyerDashBoardFragment fragobj = new BuyerDashBoardFragment();
                getFragmentManager().beginTransaction()
                        .add(R.id.fragmentLayout, fragobj).commit();
//                SellerDashBoardProductFragment fragobj = new SellerDashBoardProductFragment();
//                FragmentTransaction transaction = getFragmentManager().beginTransaction();
//                transaction.replace(R.id.fragmentLayout, fragobj);
//                transaction.commit();
            }
        });
        order_buyer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //MakeItemSelected(ORDER);
                //title.setText("Order");
                globels.getGlobelRef().orderType = "0";
                startActivity(new Intent(BuyerDeshBoardActivity.this, BuyerOrdersActivity.class));
//                SellerCustomerFragment fragobj = new SellerCustomerFragment();
//                FragmentTransaction transaction = getFragmentManager().beginTransaction();
//                transaction.replace(R.id.fragmentLayout, fragobj);
//                transaction.commit();
//                BuyerDeshBoardActivity.getContext().changeTitle("Customer");
            }
        });
        wishlist_buyer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //title.setText("WishList");
                //MakeItemSelected(WISHLIST);
                startActivity(new Intent(BuyerDeshBoardActivity.this, BuyerWishListActivity.class));
//                globels.getGlobelRef().orderType = "0";
//                startActivity(new Intent(BuyerDeshBoardActivity.this, SellersOrdersActivity.class));
            }
        });
        message.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                title.setText("Messages");
                MakeItemSelected(MESSAGE);

                BuyerDashBoardMessageFragment fragobj = new BuyerDashBoardMessageFragment();
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.replace(R.id.fragmentLayout, fragobj);
                transaction.commit();
                BuyerDeshBoardActivity.getContext().changeTitle("Messages");
//                SellerDashBoardMessageFragment fragobj = new SellerDashBoardMessageFragment();
//                FragmentTransaction transaction = getFragmentManager().beginTransaction();
//                transaction.replace(R.id.fragmentLayout, fragobj);
//                transaction.commit();

            }
        });

        setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MakeItemSelected(SETTING);

                title.setText("Setting");
//                SellerDashBoardSettingFragment fragobj = new SellerDashBoardSettingFragment();
//                FragmentTransaction transaction = getFragmentManager().beginTransaction();
//                transaction.replace(R.id.fragmentLayout, fragobj);
//                transaction.commit();

            }
        });

        if (savedInstanceState == null) {

            BuyerDashBoardFragment fragobj = new BuyerDashBoardFragment();
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

        deshboard_buyer.setColorFilter(null);
        order_buyer.setColorFilter(null);
        wishlist_buyer.setColorFilter(null);
        message.setColorFilter(null);
        setting.setColorFilter(null);

        switch (btnName) {
            case DESHBOARD:
                deshboard_buyer.setColorFilter(btnSelectorColor);
                break;

            case ORDER:
                order_buyer.setColorFilter(btnSelectorColor);
                break;

            case WISHLIST:
                wishlist_buyer.setColorFilter(btnSelectorColor);
                break;

            case MESSAGE:
                message.setColorFilter(btnSelectorColor);
                break;

            case SETTING:
                setting.setColorFilter(btnSelectorColor);
                break;
        }
    }

    public void changeTitle(String titleString) {
        title.setText("" + titleString);
    }

    public void fill_data_to_adapter(ArrayList<BuyerDeshBoardStatesModel> list) {
        globels.getGlobelRef().buyerDeshBoardStatesList = list;
    }
}

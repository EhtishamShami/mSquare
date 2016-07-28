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
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;

import thinktechsol.msquare.R;
import thinktechsol.msquare.adapter.SellerOrdersListAdapter;
import thinktechsol.msquare.fragments.SellerDashBoardProductFragment;
import thinktechsol.msquare.fragments.SellerDashBoardSettingFragment;
import thinktechsol.msquare.globels.globels;
import thinktechsol.msquare.model.GetSellerOrdersModel;
import thinktechsol.msquare.services.getSellersOrder;
import thinktechsol.msquare.utils.Constant;

public class SellersOrdersActivity extends Activity {
    public static final String RECENT = "recent";
    public static final String COMPLETE = "complete";
    public static final String INPROCESS = "inprocess";
    public static final String DISPUTE = "dispute";

    public static float IconsHeight = 6.00f;
    public static float IconsWidth = 9.55f;
    public static SellersOrdersActivity ContextOfActivity = new SellersOrdersActivity();
    static TextView title;
    static String DeshBoardTagbackButton = "DeshBoard";
    static String HomeScreenTagbackButton = "HomeScreen";
    static String CategoryScreenTagbackButton = "Category";
    static String AddProductScreenTagbackButton = "AddProduct";
    public static ImageView backBtn;
    RelativeLayout titlebarlayout, bottombarlayout;
    RelativeLayout fragmentLayout;
    static ImageView order_recent, order_complete, order_inprocess, order_dispute;
    int btnSelectorColor;
    ListView orders_list;

    public static SellersOrdersActivity getContext() {
        return ContextOfActivity;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_seller_order);


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
                    finish();
                } else if (backBtn.getTag().equals(AddProductScreenTagbackButton)) {
//                    Toast.makeText(SellerDeshBoardActivity.this, "Category's Back Button is clicked", Toast.LENGTH_SHORT).show();
                    SellerDashBoardProductFragment fragobj = new SellerDashBoardProductFragment();
                    FragmentTransaction transaction = getFragmentManager().beginTransaction();
                    //transaction.setCustomAnimations(android.R.animator.fade_out, android.R.animator.fade_out);
                    transaction.replace(R.id.fragmentLayout, fragobj);
                    transaction.commit();
                }
            }
        });

        //initialization of bottom views
        bottombarlayout = (RelativeLayout) findViewById(R.id.bottombarlayout);
        order_recent = (ImageView) findViewById(R.id.order_recent);
        order_complete = (ImageView) findViewById(R.id.order_complete);
        order_inprocess = (ImageView) findViewById(R.id.order_inprocess);
        order_dispute = (ImageView) findViewById(R.id.order_dispute);


        titlebarlayout.setBackgroundColor(this.getResources().getColor(R.color.sellerDashBoardTitleBg));
        titlebarlayout.setLayoutParams(AppLayoutParam(10.00f, 100f, 0, 0, 0, 0, null));
        fragmentLayout.setLayoutParams(AppLayoutParam(82.00f, 100f, 0, 10, 0, 0, titlebarlayout));

        title.setText("Orders");

        bottombarlayout.setBackgroundColor(this.getResources().getColor(R.color.bottomBarColor));
        //setting the height and width of the views by percent of the screen
        bottombarlayout.setLayoutParams(AppLayoutParam(9.00f, 100f, 0, 0, 0, 0, fragmentLayout));
//        order_recent.setLayoutParams(AppLayoutParamLinearLayout(IconsHeight, IconsWidth, 0, 0, 0, 0));
//        order_complete.setLayoutParams(AppLayoutParamLinearLayout(IconsHeight + 1f, IconsWidth + 1f, 0, 0, 0, 0));
//        order_inprocess.setLayoutParams(AppLayoutParamLinearLayout(IconsHeight, IconsWidth, 0, 0, 0, 0));
//        order_dispute.setLayoutParams(AppLayoutParamLinearLayout(IconsHeight, IconsWidth, 0, 0, 0, 0));

        //make product button selected
//        order_recent.setColorFilter(btnSelectorColor);


        order_recent.setBackgroundResource(R.drawable.seller_recent_order);
        order_complete.setBackgroundResource(R.drawable.seller_complete_order);
        order_inprocess.setBackgroundResource(R.drawable.seller_inprocess_order);
        order_dispute.setBackgroundResource(R.drawable.seller_dispute_order);


        order_recent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MakeItemSelected(RECENT);
//                SellerDashBoardProductFragment fragobj = new SellerDashBoardProductFragment();
//                FragmentTransaction transaction = getFragmentManager().beginTransaction();
//                transaction.replace(R.id.fragmentLayout, fragobj);
//                transaction.commit();
            }
        });
        order_complete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MakeItemSelected(COMPLETE);

            }
        });
        order_inprocess.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                MakeItemSelected(INPROCESS);
            }
        });


        order_dispute.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MakeItemSelected(DISPUTE);

//                SellerDashBoardSettingFragment fragobj = new SellerDashBoardSettingFragment();
//                FragmentTransaction transaction = getFragmentManager().beginTransaction();
//                transaction.replace(R.id.fragmentLayout, fragobj);
//                transaction.commit();

            }
        });

        orders_list = (ListView) findViewById(R.id.orders_list);


        new getSellersOrder(this, this, globels.getGlobelRef().sellerlogin.id, globels.getGlobelRef().orderType);

        order_recent.setBackgroundResource(R.drawable.seller_recent_order);
        order_complete.setBackgroundResource(R.drawable.seller_complete_order);
        order_inprocess.setBackgroundResource(R.drawable.seller_inprocess_order);
        order_dispute.setBackgroundResource(R.drawable.seller_dispute_order);

//        if (globels.getGlobelRef().orderType == "0")
//            order_recent.setBackgroundResource(R.drawable.seller_recent_order_active);
//        else if (globels.getGlobelRef().orderType == "1")
//            order_inprocess.setBackgroundResource(R.drawable.seller_inprocess_order_active);
//        else if (globels.getGlobelRef().orderType == "2")
//            order_dispute.setBackgroundResource(R.drawable.seller_dispute_order_active);
//        else if (globels.getGlobelRef().orderType == "3")
//            order_complete.setBackgroundResource(R.drawable.seller_complete_order_active);


        if (globels.getGlobelRef().orderType == "0")
            MakeItemSelected(RECENT);
        else if (globels.getGlobelRef().orderType == "1")
            MakeItemSelected(INPROCESS);
        else if (globels.getGlobelRef().orderType == "2")
            MakeItemSelected(DISPUTE);
        else if (globels.getGlobelRef().orderType == "3")
            MakeItemSelected(COMPLETE);


//        SellerOrdersListAdapter adapter = new SellerOrdersListAdapter(this, SellersOrdersActivity.this, R.layout.seller_order_list_item, globels.getGlobelRef().productList);
//        orders_list.setAdapter(adapter);

//        if (savedInstanceState == null) {
//
//            SellerDashBoardProductFragment fragobj = new SellerDashBoardProductFragment();
//            getFragmentManager().beginTransaction()
//                    .add(R.id.fragmentLayout, fragobj).commit();
//        }
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

        order_recent.setBackgroundResource(R.drawable.seller_recent_order);
        order_complete.setBackgroundResource(R.drawable.seller_complete_order);
        order_inprocess.setBackgroundResource(R.drawable.seller_inprocess_order);
        order_dispute.setBackgroundResource(R.drawable.seller_dispute_order);

        switch (btnName) {
            case RECENT:
//                order_recent.setColorFilter(btnSelectorColor);
                globels.getGlobelRef().orderType="0";
                title.setText("Recent Orders");
                new getSellersOrder(this, this, globels.getGlobelRef().sellerlogin.id, "0");
                order_recent.setBackgroundResource(R.drawable.seller_recent_order_active);
                break;

            case COMPLETE:
//                order_complete.setColorFilter(btnSelectorColor);
                globels.getGlobelRef().orderType="3";
                title.setText("Complete Orders");
                new getSellersOrder(this, this, globels.getGlobelRef().sellerlogin.id, "3");
                order_complete.setBackgroundResource(R.drawable.seller_complete_order_active);
                break;

            case INPROCESS:
//                order_inprocess.setColorFilter(btnSelectorColor);
                globels.getGlobelRef().orderType="1";
                title.setText("In Process Orders");
                new getSellersOrder(this, this, globels.getGlobelRef().sellerlogin.id, "1");
                order_inprocess.setBackgroundResource(R.drawable.seller_inprocess_order_active);
                break;

            case DISPUTE:
//                order_dispute.setColorFilter(btnSelectorColor);
                globels.getGlobelRef().orderType="2";
                title.setText("Dispute Orders");
                new getSellersOrder(this, this, globels.getGlobelRef().sellerlogin.id, "2");
                order_dispute.setBackgroundResource(R.drawable.seller_dispute_order_active);
                break;
        }
    }

    public void changeTitle(String titleString) {
        title.setText("" + titleString);
    }

    public void fillProductListWithData(ArrayList<GetSellerOrdersModel> list) {
        SellerOrdersListAdapter adapter = new SellerOrdersListAdapter(SellersOrdersActivity.this, SellersOrdersActivity.this, R.layout.seller_order_list_item, list);
        orders_list.setAdapter(adapter);
    }
}

package thinktechsol.msquare.activities.buyer;


import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;

import thinktechsol.msquare.R;
import thinktechsol.msquare.adapter.BuyerWishListAdapter;
import thinktechsol.msquare.globels.globels;
import thinktechsol.msquare.model.Buyer.BuyerWishListModel;
import thinktechsol.msquare.utils.Constant;

public class BuyerBookingAddressActivity extends Activity {

    ListView listView;
    BuyerWishListAdapter adapter;
    ArrayList<BuyerWishListModel> productList;
    RelativeLayout titlebarlayout;
    TextView title;
    ImageView backBtn, btn_menu;
    EditText houseET, streetAddresET, areaET, stateET, phoneET;
    Button saveBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_buyer_booking_address);

        //new getBuyerWishListService(BuyerBookingAddressActivity.this, BuyerBookingAddressActivity.this, globels.getGlobelRef().buyerLoginId);


        titlebarlayout = (RelativeLayout) findViewById(R.id.titlebarlayout);
        title = (TextView) findViewById(R.id.title);
        backBtn = (ImageView) findViewById(R.id.backBtn);
        btn_menu = (ImageView) findViewById(R.id.btn_menu);
        saveBtn = (Button) findViewById(R.id.saveBtn);

        // title bar
        backBtn.setLayoutParams(AppLayoutParam(10f, 10f, 0, 0, 0, 0, null, "ver", 0, "null"));
        btn_menu.setLayoutParams(AppLayoutParam(12f, 8f, 0, 0, 2, 0, null, "ver", 0, "right"));
//        btn_menu.setVisibility(View.VISIBLE);
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        btn_menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        title.setText("Book Product");
        titlebarlayout.setBackgroundColor(this.getResources().getColor(R.color.buyerHomeActivityTitleBarColor));
        titlebarlayout.setLayoutParams(AppLayoutParam(10.00f, 100f, 0, 0, 0, 0, null, "hor", 0, "null"));

        houseET = (EditText) findViewById(R.id.houseET);
        streetAddresET = (EditText) findViewById(R.id.streetAddresET);
        areaET = (EditText) findViewById(R.id.areaET);
        stateET = (EditText) findViewById(R.id.stateET);
        phoneET = (EditText) findViewById(R.id.phoneET);


        try {
            houseET.setText("" + globels.getGlobelRef().buyerLoginResponse.get(0).houseNo);
            streetAddresET.setText("" + globels.getGlobelRef().buyerLoginResponse.get(0).streetNo);
            areaET.setText("" + globels.getGlobelRef().buyerLoginResponse.get(0).area);
            stateET.setText("" + globels.getGlobelRef().buyerLoginResponse.get(0).state);
            phoneET.setText("" + globels.getGlobelRef().buyerLoginResponse.get(0).phoneNo);
        } catch (Exception e) {

        }
        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                address = houseET.getText().toString() + " " + streetAddresET.getText().toString() + " " + areaET.getText().toString() + " " + stateET.getText().toString() + " " + phoneET.getText().toString();

                globels.getGlobelRef().address = address;
                finish();
//                new UpdateBuyerAddressService(BuyerBookingAddressActivity.this, globels.getGlobelRef().buyerLoginId, houseET.getText().toString(),
//                        streetAddresET.getText().toString(), areaET.getText().toString(), stateET.getText().toString(), phoneET.getText().toString());
            }
        });
//        adapter = new BuyerServiceSellersListAdapter(BuyerWishListActivity.this, R.layout.buyer_service_seller_list_item, globels.getGlobelRef().SellersProductDetailList);
//        listView.setAdapter(adapter);
    }

    String address;

    public void addressUpdateSuccessfully() {
        //globels.getGlobelRef().address = address;
        System.exit(0);
    }

    public void fillProductListWithData(ArrayList<BuyerWishListModel> list) {
        productList = new ArrayList<BuyerWishListModel>();

        Log.e("BuyerServiceSellersList", "list size is=" + list.size());


//        if (list.size() > 0) {
        adapter = new BuyerWishListAdapter(BuyerBookingAddressActivity.this, R.layout.buyer_wishlist_list_item, list);
        listView.setAdapter(adapter);
//        }
    }

    public RelativeLayout.LayoutParams AppLayoutParam(float height, float width, float mL, float mT, float mR, float mB, View below, String center, int toRightOf, String align) {
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

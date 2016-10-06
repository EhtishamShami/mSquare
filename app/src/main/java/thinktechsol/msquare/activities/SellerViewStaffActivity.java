package thinktechsol.msquare.activities;


import android.app.Activity;
import android.app.Dialog;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import thinktechsol.msquare.R;
import thinktechsol.msquare.globels.globels;
import thinktechsol.msquare.model.Buyer.BuyerWishListModel;
import thinktechsol.msquare.model.GetSellerStaffModel;
import thinktechsol.msquare.services.AddSellerStaffService;
import thinktechsol.msquare.services.GetSellerStaffService;
import thinktechsol.msquare.utils.Constant;

public class SellerViewStaffActivity extends Activity {

    ListView listView;
    ArrayList<BuyerWishListModel> productList;
    RelativeLayout titlebarlayout;
    TextView title;
    ImageView backBtn, btn_menu;
    Dialog addStaffDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_buyer_view_staff);

//        new getBuyerWishListService(BuyerViewStaffActivity.this, BuyerViewStaffActivity.this, globels.getGlobelRef().buyerLoginId);
        new GetSellerStaffService(this, this, globels.getGlobelRef().sellerLoginId);

        titlebarlayout = (RelativeLayout) findViewById(R.id.titlebarlayout);
        title = (TextView) findViewById(R.id.title);
        title.setTextColor(Color.BLACK);
        backBtn = (ImageView) findViewById(R.id.backBtn);
        btn_menu = (ImageView) findViewById(R.id.btn_menu);

        // title bar
        backBtn.setLayoutParams(AppLayoutParam(3f, 16f, 2, 0, 0, 0, null, "ver", 0, "null"));
        btn_menu.setLayoutParams(AppLayoutParam(7f, 12f, 0, 0, 2, 0, null, "ver", 0, "right"));

        backBtn.setImageResource(android.R.color.transparent);
        backBtn.setBackgroundResource(R.drawable.back_btn_blue);

        btn_menu.setVisibility(View.VISIBLE);
        btn_menu.setBackgroundResource(R.drawable.plus_btn);

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        btn_menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                addStaffDialog = new Dialog(SellerViewStaffActivity.this);
                addStaffDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                addStaffDialog.setCancelable(false);

                LayoutInflater inflater = (LayoutInflater) getLayoutInflater();
                View customView = inflater.inflate(R.layout.dialog_add_staff, null);

                addStaffDialog.setContentView(customView);

                int width = (int) (getResources().getDisplayMetrics().widthPixels * 0.95);
                int height = (int) (getResources().getDisplayMetrics().heightPixels * 0.3);
                addStaffDialog.getWindow().setLayout(width, height);

                final EditText staffET = (EditText) addStaffDialog.findViewById(R.id.staffET);

                Button OkBtn = (Button) addStaffDialog.findViewById(R.id.OkBtn);
                OkBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (staffET.getText().toString().length() > 0)
                            new AddSellerStaffService(SellerViewStaffActivity.this, SellerViewStaffActivity.this, globels.getGlobelRef().sellerLoginId, staffET.getText().toString().trim());
                        else
                            Toast.makeText(SellerViewStaffActivity.this, "Please Enter The Staff Name First", Toast.LENGTH_SHORT).show();
                    }
                });

                Button CancelBtn = (Button) addStaffDialog.findViewById(R.id.CancelBtn);
                CancelBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        addStaffDialog.dismiss();
                    }
                });

                addStaffDialog.show();
            }
        });
        title.setText("Staff");
        titlebarlayout.setBackgroundColor(this.getResources().getColor(R.color.color_userLayout));
        titlebarlayout.setLayoutParams(AppLayoutParam(10.00f, 100f, 0, 0, 0, 0, null, "hor", 0, "null"));

        listView = (ListView) findViewById(R.id.list);
//        adapter = new BuyerViewStaffAdapter(BuyerViewStaffActivity.this, android.R.layout.simple_list_item_1, list);
    }

    public void fillProductListWithData(ArrayList<GetSellerStaffModel> StaffDetails) {
        productList = new ArrayList<BuyerWishListModel>();

        Log.e("StaffDetails", "StaffDetails size is=" + StaffDetails.size());

        ArrayList<String> list = new ArrayList<String>();
        for (int i = 0; i < StaffDetails.size(); i++) {
            list.add(StaffDetails.get(i).name);
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(SellerViewStaffActivity.this, android.R.layout.simple_list_item_1, list);
        listView.setAdapter(adapter);

    }

    public void onStaffAdded(boolean isAdded) {
        if (isAdded) {
            addStaffDialog.dismiss();
            new GetSellerStaffService(this, this, globels.getGlobelRef().sellerLoginId);
            listView.invalidate();
        }
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

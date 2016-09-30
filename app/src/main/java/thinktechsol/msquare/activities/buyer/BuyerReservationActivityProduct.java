package thinktechsol.msquare.activities.buyer;

import android.app.Activity;
import android.content.Intent;
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
import android.widget.Toast;

import com.alamkanak.weekview.WeekView;
import com.alamkanak.weekview.WeekViewEvent;

import java.util.ArrayList;

import thinktechsol.msquare.R;
import thinktechsol.msquare.adapter.ProductListAdapterReservation;
import thinktechsol.msquare.globels.globels;
import thinktechsol.msquare.model.Buyer.BuyerGetStaffModel;
import thinktechsol.msquare.model.Buyer.BuyerGetStaffTimeMode;
import thinktechsol.msquare.model.Buyer.ConfirmBookingModel;
import thinktechsol.msquare.services.AddBuyerOrder;
import thinktechsol.msquare.utils.Constant;

public class BuyerReservationActivityProduct extends Activity {

    RelativeLayout titlebarlayout;
    TextView title, titleSeller;
    ImageView backBtn, btn_menu;

    RelativeLayout row1Layout, products_Layout, orderDetailLayout, addressLayout, mainLayout;
    static ListView productslisview;

    WeekView mWeekView;
    private static ArrayList<WeekViewEvent> mNewEvents;
    TextView userName, totalPrice, buyerAddress;
    ImageView btnUpdateAddress;
    Button confrmBookingBtn;
    EditText etDescription;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_buyer_reservation_product);

        try {
            populateData();
        } catch (Exception e) {
            Log.e("BuyerReservation", "error in populating the data=" + e);
        }


        //new GetStaffService(BuyerReservationActivityProduct.this, BuyerReservationActivityProduct.this, globels.getGlobelRef().serviceSellerId);

//        list = new ArrayList<BuyerGetStaffTimeMode>();
//        staffList = new ArrayList<BuyerGetStaffModel>();

//        titlebarlayout = (RelativeLayout) findViewById(R.id.titlebar);
//        titleSeller = (TextView) findViewById(R.id.titleSeller);
//        titleSeller.setText("" + globels.getGlobelRef().productList.get(0).sellerInfo.companyName);
//
//        backBtn = (ImageView) findViewById(R.id.backBtn);
//        btn_menu = (ImageView) findViewById(R.id.btn_menu);
//
//        row1Layout = (RelativeLayout) findViewById(R.id.row1Layout);
//        userName = (TextView) findViewById(R.id.userName);
//        title = (TextView) findViewById(R.id.title);
//
//        products_Layout = (RelativeLayout) findViewById(R.id.products_Layout);
//        productslisview = (ListView) findViewById(R.id.productslisview);
//
//        mainLayout = (RelativeLayout) findViewById(R.id.mainLayout);
//
//        ProductListAdapterReservation adapter = new ProductListAdapterReservation(this, BuyerReservationActivityProduct.this, R.layout.product_list_adapter_item_reservation, globels.getGlobelRef().selectedProductListReservation);
//        productslisview.setAdapter(adapter);
//
//        orderDetailLayout = (RelativeLayout) findViewById(R.id.orderDetailLayout);
//        totalPrice = (TextView) findViewById(R.id.totalPrice);
//        totalPrice.setText(String.valueOf(globels.getGlobelRef().SelectedServicesPrice));
//
//        addressLayout = (RelativeLayout) findViewById(R.id.addressLayout);
//        buyerAddress = (TextView) findViewById(R.id.buyerAddress);
//        btnUpdateAddress = (ImageView) findViewById(R.id.btnUpdateAddress);
//
//        buyerAddress.setText("" +
//                globels.getGlobelRef().houseNo + "" +
//                globels.getGlobelRef().streetNo + "" +
//                globels.getGlobelRef().area + "" +
//                globels.getGlobelRef().state + "" +
//                globels.getGlobelRef().phoneNo
//        );
//
//        confrmBookingBtn = (Button) findViewById(R.id.confrmBookingBtn);
//        etDescription = (EditText) findViewById(R.id.etDescription);
//
//        // title bar
//        backBtn.setLayoutParams(AppLayoutParam(10f, 10f, 0, 0, 0, 0, null, "ver", 0, "null"));
//        btn_menu.setLayoutParams(AppLayoutParam(12f, 8f, 0, 0, 2, 0, null, "ver", 0, "right"));
////        btn_menu.setVisibility(View.VISIBLE);
//        backBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                globels.getGlobelRef().allSelectedServices = "";
//                globels.getGlobelRef().SelectedServicesPrice = 0;
//                finish();
//            }
//        });
//
//        title.setText("Book Service");
//        titlebarlayout.setBackgroundColor(this.getResources().getColor(R.color.buyerHomeActivityTitleBarColor));
//        titlebarlayout.setLayoutParams(AppLayoutParam(10.00f, 100f, 0, 0, 0, 0, null, "hor", 0, "null"));
//        // title bar end
//
//        mainLayout.setLayoutParams(AppLayoutParam(80.00f, 100, 0, 0, 0, 0, titlebarlayout, "hor", 0, "null"));
//        confrmBookingBtn.setLayoutParams(AppLayoutParam(11.00f, 100f, 0, 0, 0, 0, mainLayout, "hor", 0, "null"));
//
//        row1Layout.setLayoutParams(AppLayoutParam(10.00f, 100f, 0, 0, 0, 0, null, "hor", 0, "null"));
//        row1Layout.setBackgroundColor(this.getResources().getColor(R.color.color_userLayout));
//
//        products_Layout.setLayoutParams(AppLayoutParam(23.00f, 100f, 0, 0, 0, -1, row1Layout, "hor", 0, "null"));
//
//        btnUpdateAddress.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                startActivity(new Intent(BuyerReservationActivityProduct.this, BuyerBookingAddressActivity.class));
//            }
//        });
//
//        confrmBookingBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                if (!(Constant.logInAs.equals("guest"))) {
//
//                    String sellerId = globels.getGlobelRef().productList.get(0).sellerInfo.id;
//                    String buyerId = globels.getGlobelRef().buyerLoginId;
//                    String extraRemarks = etDescription.getText().toString();
//                    String serviceRequestTime = "";
//                    String staffId = "";
//
//                    Log.e("BuyerReservationAct", "idsssss sel serId=" + globels.getGlobelRef().selectedServicesIds);
//                    Log.e("BuyerReservationAct", "idsssss sel proId=" + globels.getGlobelRef().selectedProductsIds);
//                    Log.e("BuyerReservationAct", "idsssss sel proQuan=" + globels.getGlobelRef().selectedQuantityIds);
//
//                    ConfirmBookingModel obj = new ConfirmBookingModel(sellerId, buyerId, extraRemarks, serviceRequestTime, staffId, globels.getGlobelRef().selectedServicesIds, globels.getGlobelRef().selectedProductsIds, globels.getGlobelRef().selectedQuantityIds);
//                    new AddBuyerOrder(BuyerReservationActivityProduct.this, BuyerReservationActivityProduct.this, obj);
//                } else {
//                    startActivity(new Intent(BuyerReservationActivityProduct.this, BuyerLoginActivityGuest.class));
//                }
//            }
//        });

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

    public RelativeLayout.LayoutParams AppLayoutParam2(float height, float width, float mL, float mT, float mR, float mB, View below) {
        RelativeLayout.LayoutParams paramName = new RelativeLayout.LayoutParams(
                Constant.getSize("w", width),
                Constant.getSize("h", height));

        if (below != null) {
            Log.e("HomeActivity", "belowing is=" + below.getId());
            paramName.addRule(RelativeLayout.BELOW, below.getId());
        }
        paramName.setMargins(Constant.getSize("h", mL), Constant.getSize("h", mT), Constant.getSize("h", mR), Constant.getSize("h", mB));
        return paramName;
    }

//    public void PopUpForChangeServiceProvider() {
//        LayoutInflater inflater = this.getLayoutInflater();
//        AlertDialog.Builder builder = new AlertDialog.Builder(BuyerReservationActivityProduct.this);
//        View dialogView = inflater.inflate(R.layout.change_service_provider_popup, null);
//        builder.setView(dialogView);
//
//        staffListView = (ListView) dialogView.findViewById(R.id.change_user_list);
////        for (int i = 0; i < 4; i++) {
////            staffList.add(new BuyerGetStaffModel(R.drawable.avatar, "Yasir Ahmed"));
////        }
//
////        ChangeServiceProviderListAdapter adapter = new ChangeServiceProviderListAdapter(this, BuyerReservationActivity.this, R.layout.change_service_provider_list_adapter_item, staffList);
////        staffListView.setAdapter(adapter);
//
//        changeStaff = builder.create();
//        changeStaff.setCancelable(true);
//        changeStaff.setCanceledOnTouchOutside(true);
////        changeStaff.show();
//    }

    String selectedDateForPostingToService, selectedTimeForPostingToService;


//    public void getCurrentDateAndShowOnView() {
//        DateFormat dateFormat = new SimpleDateFormat("E, yyyy MMM dd");
//        DateFormat dateFormat2 = new SimpleDateFormat("yyyy MMM dd");
//
//        DateFormat dateFormat3 = new SimpleDateFormat("hh:mm:ss");
//
//        Calendar cal = Calendar.getInstance();
//        currentDate.setText(dateFormat.format(cal.getTime()));
//
//        tvDate.setText(dateFormat2.format(cal.getTime()));
//        tvTime.setText(dateFormat3.format(cal.getTime()));
//
//        if (dateFormatForPosting != null)
//            selectedDateForPostingToService = dateFormatForPosting.format(cal.getTime());
//        selectedTimeForPostingToService = dateFormat3.format(cal.getTime());
//    }

    public void fillProductListWithData(ArrayList<BuyerGetStaffModel> list) {
//        ChangeServiceProviderListAdapter adapter = new ChangeServiceProviderListAdapter(this, BuyerReservationActivityProduct.this, R.layout.change_service_provider_list_adapter_item, list);
//        staffListView.setAdapter(adapter);
    }

    public void getStaffTime(ArrayList<BuyerGetStaffTimeMode> staffTimeList) {
//        list.add(new BuyerGetStaffTimeMode("01:00 AM"));
//        list.add(new BuyerGetStaffTimeMode("2", "01:15 AM", false));
//        list.add(new TimeListItemModel("3", "01:30 AM", false));
//        list.add(new TimeListItemModel("4", "01:45 AM", false));

//        TimeListAdapter adapter = new TimeListAdapter(this, BuyerReservationActivityProduct.this, R.layout.time_list_adapter_item, list);
//        timelisview.setAdapter(adapter);
    }

    @Override
    public void onResume() {
        super.onResume();

        if (buyerAddress != null) {
            buyerAddress.setText("" + globels.getGlobelRef().address);
        }
    }


    public void populateData() {
        titlebarlayout = (RelativeLayout) findViewById(R.id.titlebar);
        titleSeller = (TextView) findViewById(R.id.titleSeller);
        titleSeller.setText("" + globels.getGlobelRef().productList.get(0).sellerInfo.companyName);

        backBtn = (ImageView) findViewById(R.id.backBtn);
        btn_menu = (ImageView) findViewById(R.id.btn_menu);

        row1Layout = (RelativeLayout) findViewById(R.id.row1Layout);
        userName = (TextView) findViewById(R.id.userName);
        title = (TextView) findViewById(R.id.title);

        products_Layout = (RelativeLayout) findViewById(R.id.products_Layout);
        productslisview = (ListView) findViewById(R.id.productslisview);

        mainLayout = (RelativeLayout) findViewById(R.id.mainLayout);

        for (int i = 0; i < globels.getGlobelRef().selectedProductListReservation.size(); i++) {
            Log.e("BuyerService", "selected items 3=" + globels.getGlobelRef().selectedProductListReservation.get(i).proQuantity);
        }

        ProductListAdapterReservation adapter = new ProductListAdapterReservation(this, BuyerReservationActivityProduct.this, R.layout.product_list_adapter_item_reservation, globels.getGlobelRef().selectedProductListReservation);
        productslisview.setAdapter(adapter);

        orderDetailLayout = (RelativeLayout) findViewById(R.id.orderDetailLayout);
        totalPrice = (TextView) findViewById(R.id.totalPrice);
        totalPrice.setText(String.valueOf(globels.getGlobelRef().SelectedServicesPriceNew));

        addressLayout = (RelativeLayout) findViewById(R.id.addressLayout);
        buyerAddress = (TextView) findViewById(R.id.buyerAddress);
        btnUpdateAddress = (ImageView) findViewById(R.id.btnUpdateAddress);

        try {
            buyerAddress.setText("" +
                    globels.getGlobelRef().buyerLoginResponse.get(0).houseNo + "" +
                    globels.getGlobelRef().buyerLoginResponse.get(0).streetNo + "" +
                    globels.getGlobelRef().buyerLoginResponse.get(0).area + "" +
                    globels.getGlobelRef().buyerLoginResponse.get(0).state + "" +
                    globels.getGlobelRef().buyerLoginResponse.get(0).phoneNo
            );
        } catch (Exception e) {
            Log.e("BuyerReservation", "" + e);
        }

        confrmBookingBtn = (Button) findViewById(R.id.confrmBookingBtn);
        confrmBookingBtn.setBackgroundColor(this.getResources().getColor(globels.getGlobelRef().them_color));
        etDescription = (EditText) findViewById(R.id.etDescription);

        // title bar
        backBtn.setLayoutParams(AppLayoutParam(10f, 10f, 0, 0, 0, 0, null, "ver", 0, "null"));
        btn_menu.setLayoutParams(AppLayoutParam(12f, 8f, 0, 0, 2, 0, null, "ver", 0, "right"));
//        btn_menu.setVisibility(View.VISIBLE);
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                globels.getGlobelRef().allSelectedServices = "";
                globels.getGlobelRef().SelectedServicesPrice = 0;
                globels.getGlobelRef().SelectedServicesPriceNew = 0;
                finish();
            }
        });

        title.setText("Book Service");
//        titlebarlayout.setBackgroundColor(this.getResources().getColor(R.color.buyerHomeActivityTitleBarColor));
        titlebarlayout.setBackgroundColor(this.getResources().getColor(globels.getGlobelRef().them_color));
        titlebarlayout.setLayoutParams(AppLayoutParam(10.00f, 100f, 0, 0, 0, 0, null, "hor", 0, "null"));
        // title bar end

        mainLayout.setLayoutParams(AppLayoutParam(80.00f, 100, 0, 0, 0, 0, titlebarlayout, "hor", 0, "null"));
        confrmBookingBtn.setLayoutParams(AppLayoutParam(11.00f, 100f, 0, 0, 0, 0, mainLayout, "hor", 0, "null"));
        confrmBookingBtn.setBackgroundColor(this.getResources().getColor(globels.getGlobelRef().them_color));

        row1Layout.setLayoutParams(AppLayoutParam(10.00f, 100f, 0, 0, 0, 0, null, "hor", 0, "null"));
        row1Layout.setBackgroundColor(this.getResources().getColor(R.color.color_userLayout));

        products_Layout.setLayoutParams(AppLayoutParam(23.00f, 100f, 0, 0, 0, -1, row1Layout, "hor", 0, "null"));

        btnUpdateAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(BuyerReservationActivityProduct.this, BuyerBookingAddressActivity.class));
            }
        });

        confrmBookingBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!(Constant.logInAs.equals("guest"))) {

                    String sellerId = globels.getGlobelRef().productList.get(0).sellerInfo.id;
                    String buyerId = globels.getGlobelRef().buyerLoginId;
                    String extraRemarks = etDescription.getText().toString();
                    String serviceRequestTime = "";
                    String staffId = "";

                    Log.e("BuyerReservationAct", "selected items rr 2=" + globels.getGlobelRef().selectedServicesIds);
                    Log.e("BuyerReservationAct", "selected items rr 2=" + globels.getGlobelRef().selectedProductsIds);
                    Log.e("BuyerReservationAct", "selected items rr 2=" + globels.getGlobelRef().selectedQuantityIds);

                    if(buyerAddress.getText().toString()!=null || buyerAddress.getText().toString().length()<=0){
                        Toast.makeText(BuyerReservationActivityProduct.this, "Please Enter the address first", Toast.LENGTH_LONG).show();
                    }else {
                        ConfirmBookingModel obj = new ConfirmBookingModel(sellerId, buyerId, extraRemarks, serviceRequestTime, staffId, globels.getGlobelRef().selectedServicesIds, globels.getGlobelRef().selectedProductsIds, globels.getGlobelRef().selectedQuantityIds);
                        new AddBuyerOrder(BuyerReservationActivityProduct.this, BuyerReservationActivityProduct.this, null, BuyerReservationActivityProduct.this, obj);

                    }
                                   } else {
                    Toast.makeText(BuyerReservationActivityProduct.this, "Your Selection is Save! But You have to Login First", Toast.LENGTH_LONG).show();
                    startActivity(new Intent(BuyerReservationActivityProduct.this, BuyerLoginActivityGuest.class));
                }
            }
        });
    }
}

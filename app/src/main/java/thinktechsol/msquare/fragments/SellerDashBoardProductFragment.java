package thinktechsol.msquare.fragments;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;

import thinktechsol.msquare.R;
import thinktechsol.msquare.activities.AddOrViewProActivity;
import thinktechsol.msquare.activities.ProductCategoryActivity;
import thinktechsol.msquare.activities.SellerDeshBoardActivity;
import thinktechsol.msquare.activities.SellerViewProActivity;
import thinktechsol.msquare.activities.SellersOrdersActivity;
import thinktechsol.msquare.adapter.ItemAdapter2;
import thinktechsol.msquare.globels.globels;
import thinktechsol.msquare.model.Item;
import thinktechsol.msquare.model.SellerDashBoardStatsModel;
import thinktechsol.msquare.services.GetSellerDeshBoardStatsService;
import thinktechsol.msquare.utils.Constant;

public class SellerDashBoardProductFragment extends Fragment {

    TextView title;
    RelativeLayout titlebarlayout, bottombarlayout;
    static ListView simpleCustomeListView;

//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_sellerdashboard_product, container, false);

        try {
            new GetSellerDeshBoardStatsService(getActivity(), SellerDashBoardProductFragment.this, globels.getGlobelRef().sellerLoginId);
        } catch (Exception e) {

        }

//        requestWindowFeature(Window.FEATURE_NO_TITLE);
//        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
//        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
//                WindowManager.LayoutParams.FLAG_FULLSCREEN);

//        setContentView(R.layout.fragment_sellerdashboard_product);

//        //initialization
//        titlebarlayout = (RelativeLayout) v.findViewById(R.id.titlebarlayout);
//        title = (TextView) v.findViewById(R.id.title);
        simpleCustomeListView = (ListView) v.findViewById(R.id.listView);
//
//        //initialization of bottom views
//        bottombarlayout = (RelativeLayout) v.findViewById(R.id.bottombarlayout);
//        ImageView product = (ImageView) v.findViewById(R.id.product);
//        ImageView customer = (ImageView) v.findViewById(R.id.customer);
//        ImageView order = (ImageView) v.findViewById(R.id.order);
//        ImageView message = (ImageView) v.findViewById(R.id.message);
//        ImageView setting = (ImageView) v.findViewById(R.id.setting);
//
//
//        titlebarlayout.setBackgroundColor(this.getResources().getColor(R.color.sellerDashBoardTitleBg));
//        titlebarlayout.setLayoutParams(AppLayoutParam(10.00f, 100f, 0, 0, 0, 0, null));
//
//        title.setText("DashBoard");
//
//        bottombarlayout.setBackgroundColor(this.getResources().getColor(R.color.bottomBarColor));
//        //setting the height and width of the views by percent of the screen
//        bottombarlayout.setLayoutParams(AppLayoutParam(10.00f, 100f, 0, 0, 0, 0, simpleCustomeListView));
//        product.setLayoutParams(AppLayoutParamLinearLayout(4.75f, 8.29f, 0, 0, 0, 0));
//        customer.setLayoutParams(AppLayoutParamLinearLayout(4.75f, 8.29f, 0, 0, 0, 0));
//        order.setLayoutParams(AppLayoutParamLinearLayout(4.75f, 8.29f, 0, 0, 0, 0));
//        message.setLayoutParams(AppLayoutParamLinearLayout(4.75f, 8.29f, 0, 0, 0, 0));
//        setting.setLayoutParams(AppLayoutParamLinearLayout(4.75f, 8.29f, 0, 0, 0, 0));


        ArrayList<Item> m_parts = new ArrayList<Item>();

        ArrayList<SellerDashBoardStatsModel> statsList = globels.getGlobelRef().SellerDashBoardStatsModel;

        String MessageTotal = "6";//statsList.get(0).unReadMessages;
        String Customers = "9";//statsList.get(0).customers;


        int PEnabled = 6;
        int PPending = 7;
        int PDisabled = 8;
        int PBlocked = 9;

        int OCompleted = 0;
        int OinProgress = 0;
        int Orecent = 0;
        int Odispute = 0;

        m_parts.add(new Item(getResources().getString(R.string.lbl_message), Integer.parseInt(MessageTotal), R.drawable.messages, R.color.messageColor, R.drawable.message_slide1, R.drawable.message_slide2));
        m_parts.add(new Item(getResources().getString(R.string.lbl_customer), Integer.parseInt(Customers), R.drawable.customer, R.color.customerColor, R.drawable.customer_slide1, R.drawable.customer_slide2));
        m_parts.add(new Item(getResources().getString(R.string.lbl_order), OCompleted, OinProgress, Orecent, Odispute, R.drawable.order, R.color.orderColor, R.drawable.order_slide1, R.drawable.order_slide2, R.drawable.order_slide3, R.drawable.order_slide4));
        m_parts.add(new Item(getResources().getString(R.string.lbl_product), PEnabled, PPending, PDisabled, PBlocked, R.drawable.product, R.color.productColor, R.drawable.product_slide1, R.drawable.product_slide2, true));


        try {
            ItemAdapter2 m_adapter = new ItemAdapter2(getActivity(), SellerDeshBoardActivity.getContext(), R.layout.dashboard_row_item1, m_parts, this);
            simpleCustomeListView.setAdapter(m_adapter);
        } catch (Exception e) {
            Log.e("SellerDashBoardActivity", "adapter=" + e);
        }

        return v;
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

    public static final String MESSAGE = "message";
    ProgressDialog progressDialog;

    public void openFragment(int id, String position) {
        Fragment frag = null;
        String title = null;
        switch (id) {
            case 0:
                SellerDeshBoardActivity.getContext().MakeItemSelected(MESSAGE);
                if (position.equals("left")) {
                    SellerDashBoardMessageFragment fragobj = new SellerDashBoardMessageFragment();
                    title = "Messages";
                    frag = fragobj;
                } else {
                    SellerDashBoardMessageFragment fragobj = new SellerDashBoardMessageFragment();
                    title = "Messages";
                    frag = fragobj;
                }

//                SellerDeshBoardActivity.getContext().MakeItemSelected(MESSAGE);

                //title.setText("Messages");
//                SellerDashBoardMessageFragment fragobj = new SellerDashBoardMessageFragment();
//                FragmentTransaction transaction = getFragmentManager().beginTransaction();
//                transaction.replace(R.id.fragmentLayout, fragobj);
//                transaction.commit();

                break;
            case 1:
                if (position.equals("left")) {
                    SellerCustomerFragment fragobj = new SellerCustomerFragment();
                    title = "Customers";
                    frag = fragobj;
                } else {

                }
                break;
            case 2:
                if (position.equals("leftMost")) {
                    globels.getGlobelRef().orderType = "0";
                    startActivity(new Intent(getActivity(), SellersOrdersActivity.class));
                } else if (position.equals("left")) {
                    globels.getGlobelRef().orderType = "3";
                    startActivity(new Intent(getActivity(), SellersOrdersActivity.class));
                }
                if (position.equals("right")) {
                    globels.getGlobelRef().orderType = "1";
                    startActivity(new Intent(getActivity(), SellersOrdersActivity.class));
                }
                if (position.equals("rightMost")) {
                    globels.getGlobelRef().orderType = "2";
                    startActivity(new Intent(getActivity(), SellersOrdersActivity.class));
                }
                break;
            case 3:
                if (position.equals("left")) {
                    Constant.addOrViewProduct = true;

//                    SellerAddProductFragment fragobj = new SellerAddProductFragment();
//                    title = "Add Product";
//                    frag = fragobj;

                    Intent addPro = new Intent(getActivity(), ProductCategoryActivity.class);
                    getActivity().startActivity(addPro);
                } else {
                    // Constant.addOrViewProduct = false;
//                    SellerAddProductFragment fragobj = new SellerAddProductFragment();
//                    title = "Add Product";
//                    frag = fragobj;
//                    globels.getGlobelRef().IdForAddProduct = myItem.id;

//                    Intent add = new Intent(getActivity(), AddOrViewProActivity.class);
                    Intent add = new Intent(getActivity(), SellerViewProActivity.class);
                    getActivity().startActivity(add);
                }
                break;
        }




        if (frag != null) {
            FragmentTransaction transaction = getFragmentManager().beginTransaction();
            transaction.replace(R.id.fragmentLayout, frag);
            transaction.commit();
            SellerDeshBoardActivity.getContext().changeTitle(title);
        }
    }

    public void updateStats() {

        try {


            // simpleCustomeListView = (ListView) v.findViewById(R.id.listView);
            ArrayList<Item> m_parts = new ArrayList<Item>();

            ArrayList<SellerDashBoardStatsModel> statsList = globels.getGlobelRef().SellerDashBoardStatsModel;

            Log.e("SellerDashBoarddProFrag", "statsList=" + statsList.size());

            if (statsList.size() > 0) {
                String MessageTotal = statsList.get(0).unReadMessages;
                String Customers = statsList.get(0).customers;

                String PEnabled = statsList.get(0).Penable;
                String PPending = statsList.get(0).Ppending;
                String PDisabled = statsList.get(0).Pdisable;
                String PBlocked = statsList.get(0).Pblocked;

                String OCompleted = statsList.get(0).Ocomplete;
                String OinProgress = statsList.get(0).OinProcess;
                String Orecent = statsList.get(0).Orecent;
                String Odispute = statsList.get(0).Odispute;

                m_parts.add(new Item(getResources().getString(R.string.lbl_message), Integer.parseInt(MessageTotal), R.drawable.messages, R.color.messageColor, R.drawable.message_slide1, R.drawable.message_slide2));
                m_parts.add(new Item(getResources().getString(R.string.lbl_customer), Integer.parseInt(Customers), R.drawable.customer, R.color.customerColor, R.drawable.customer_slide1, R.drawable.customer_slide2));
                m_parts.add(new Item(getResources().getString(R.string.lbl_order), Integer.parseInt(OCompleted), Integer.parseInt(OinProgress), Integer.parseInt(Orecent),
                        Integer.parseInt(Odispute), R.drawable.order, R.color.orderColor, R.drawable.order_slide1, R.drawable.order_slide2, R.drawable.order_slide3, R.drawable.order_slide4));
                m_parts.add(new Item(getResources().getString(R.string.lbl_product), Integer.parseInt(PEnabled), Integer.parseInt(PPending),
                        Integer.parseInt(PDisabled), Integer.parseInt(PBlocked), R.drawable.product, R.color.productColor, R.drawable.product_slide1, R.drawable.product_slide2, true));

                try {
//                Log.e("SellerDashBoardActivity", "inside the adpater=");

//                simpleCustomeListView.setListShown(false);
//                simpleCustomeListView.setListAdapter(null);
                    simpleCustomeListView.setAdapter(null);
                    simpleCustomeListView.requestLayout();

                    ItemAdapter2 m_adapter = new ItemAdapter2(getActivity(), SellerDeshBoardActivity.getContext(), R.layout.dashboard_row_item1, m_parts, this);
                    simpleCustomeListView.setAdapter(m_adapter);

                    m_adapter.notifyDataSetChanged();

                    Log.e("SellerDashBoardActivity", "inside the adpater=" + simpleCustomeListView.getAdapter());


                } catch (Exception e) {
                    Log.e("SellerDashBoardActivity", "adapter=" + e);
                }


            }
        } catch (Exception e) {

        }
    }

}

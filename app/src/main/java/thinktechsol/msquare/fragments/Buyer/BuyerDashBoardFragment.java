package thinktechsol.msquare.fragments.Buyer;

import android.app.Fragment;
import android.app.FragmentTransaction;
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
import thinktechsol.msquare.activities.SellerDeshBoardActivity;
import thinktechsol.msquare.activities.SellersOrdersActivity;
import thinktechsol.msquare.activities.buyer.BuyerDeshBoardActivity;
import thinktechsol.msquare.activities.buyer.BuyerOrdersActivity;
import thinktechsol.msquare.adapter.ItemAdapter_Buyer;
import thinktechsol.msquare.fragments.SellerAddProductFragment;
import thinktechsol.msquare.fragments.SellerCustomerFragment;
import thinktechsol.msquare.fragments.SellerDashBoardMessageFragment;
import thinktechsol.msquare.globels.globels;
import thinktechsol.msquare.model.Buyer.BuyerDashBoardAdapterItem;
import thinktechsol.msquare.model.Item;
import thinktechsol.msquare.utils.Constant;

public class BuyerDashBoardFragment extends Fragment {

    TextView title;
    RelativeLayout titlebarlayout, bottombarlayout;

//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_sellerdashboard_product, container, false);


//        requestWindowFeature(Window.FEATURE_NO_TITLE);
//        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
//        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
//                WindowManager.LayoutParams.FLAG_FULLSCREEN);

//        setContentView(R.layout.fragment_sellerdashboard_product);

//        //initialization
//        titlebarlayout = (RelativeLayout) v.findViewById(R.id.titlebarlayout);
//        title = (TextView) v.findViewById(R.id.title);
        ListView simpleCustomeListView = (ListView) v.findViewById(R.id.listView);
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


        ArrayList<BuyerDashBoardAdapterItem> m_parts = new ArrayList<BuyerDashBoardAdapterItem>();

        m_parts.add(new BuyerDashBoardAdapterItem(getResources().getString(R.string.lbl_order), 1, R.drawable.order, R.color.orderColor, R.drawable.order_slide1, R.drawable.order_slide2, R.drawable.order_slide3, R.drawable.order_slide4));
        m_parts.add(new BuyerDashBoardAdapterItem(getResources().getString(R.string.lbl_message), 1, R.drawable.messages, R.color.messageColor, R.drawable.message_slide1, R.drawable.message_slide2));
        m_parts.add(new BuyerDashBoardAdapterItem(getResources().getString(R.string.lbl_wishlist), 1, R.drawable.order, R.color.customerColor, 0, 0));
        m_parts.add(new BuyerDashBoardAdapterItem(getResources().getString(R.string.lbl_favourite), 1, R.drawable.product, R.color.productColor, 0, 0));


        try {
            ItemAdapter_Buyer m_adapter = new ItemAdapter_Buyer(getActivity(), BuyerDeshBoardActivity.getContext(), R.layout.dashboard_row_item1_buyer, m_parts, this);
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

    public void openFragment(int id, String position) {
        Fragment frag = null;
        String title = null;
        switch (id) {
            case 0:
                if (position.equals("leftMost")) {
                    globels.getGlobelRef().orderType = "0";
                    startActivity(new Intent(getActivity(), BuyerOrdersActivity.class));
                } else if (position.equals("left")) {
                    globels.getGlobelRef().orderType = "3";
                    startActivity(new Intent(getActivity(), BuyerOrdersActivity.class));
                }
                if (position.equals("right")) {
                    globels.getGlobelRef().orderType = "1";
                    startActivity(new Intent(getActivity(), BuyerOrdersActivity.class));
                }
                if (position.equals("rightMost")) {
                    globels.getGlobelRef().orderType = "2";
                    startActivity(new Intent(getActivity(), BuyerOrdersActivity.class));
                }
                break;
            case 1:
                BuyerDeshBoardActivity.getContext().MakeItemSelected(MESSAGE);
                if (position.equals("left")) {
                    BuyerDashBoardMessageFragment fragobj = new BuyerDashBoardMessageFragment();
                    title = "Messages";
                    frag = fragobj;
                } else {
                    BuyerDashBoardMessageFragment fragobj = new BuyerDashBoardMessageFragment();
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
            case 2:
                if (position.equals("left")) {

                } else {

                }
                break;

            case 3:
                if (position.equals("left")) {
//                    Constant.addOrViewProduct = true;
//                    SellerAddProductFragment fragobj = new SellerAddProductFragment();
//                    title = "Add Product";
//                    frag = fragobj;
                } else {
//                    Constant.addOrViewProduct = false;
//                    SellerAddProductFragment fragobj = new SellerAddProductFragment();
//                    title = "Add Product";
//                    frag = fragobj;
//                    globels.getGlobelRef().IdForAddProduct = myItem.id;
//                    Intent add = new Intent(getActivity(), AddOrViewProActivity.class);
//                    getActivity().startActivity(add);
                }
                break;

        }

        if (frag != null) {
            FragmentTransaction transaction = getFragmentManager().beginTransaction();
            transaction.replace(R.id.fragmentLayout, frag);
            transaction.commit();
//            SellerDeshBoardActivity.getContext().changeTitle(title);
            BuyerDeshBoardActivity.getContext().changeTitle(title);
        }
    }

}

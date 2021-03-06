package thinktechsol.msquare.fragments;

import android.app.Fragment;
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
import thinktechsol.msquare.adapter.SellersMessagesAdapter;
import thinktechsol.msquare.model.getConversationListSellerResModel;
import thinktechsol.msquare.services.getConversationListSeller;
import thinktechsol.msquare.utils.Constant;

public class SellerDashBoardMessageFragment extends Fragment {

    TextView title;
    RelativeLayout titlebarlayout, bottombarlayout;
    ListView simpleCustomeListView;

//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_sellerdashboard_message, container, false);


        new getConversationListSeller(getActivity(), SellerDashBoardMessageFragment.this);

//        requestWindowFeature(Window.FEATURE_NO_TITLE);
//        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
//        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
//                WindowManager.LayoutParams.FLAG_FULLSCREEN);

//        setContentView(R.layout.fragment_sellerdashboard_product);

//        //initialization
//        titlebarlayout = (RelativeLayout) v.findViewById(R.id.titlebarlayout);
//        title = (TextView) v.findViewById(R.id.title);
        simpleCustomeListView = (ListView) v.findViewById(R.id.listView);
        //simpleCustomeListView.setVisibility(View.GONE);
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


//        ArrayList<Item> m_parts = new ArrayList<Item>();
//        m_parts.add(new Item(getResources().getString(R.string.lbl_message), 1, R.drawable.messages, R.color.messageColor, R.drawable.message_slide1, R.drawable.message_slide2));
//        m_parts.add(new Item(getResources().getString(R.string.lbl_customer), 1, R.drawable.customer, R.color.customerColor, R.drawable.customer_slide1, R.drawable.customer_slide2));
//        m_parts.add(new Item(getResources().getString(R.string.lbl_order), 1, R.drawable.order, R.color.orderColor, R.drawable.order_slide1, R.drawable.order_slide2, R.drawable.order_slide3, R.drawable.order_slide4));
//        m_parts.add(new Item(getResources().getString(R.string.lbl_product), 1, R.drawable.product, R.color.productColor, R.drawable.product_slide1, R.drawable.product_slide2));


//        try {
//            ItemAdapter m_adapter = new ItemAdapter(getActivity(), R.layout.dashboard_row_item1, m_parts);
//            simpleCustomeListView.setAdapter(m_adapter);
//        } catch (Exception e) {
//            Log.e("SellerDashBoardActivity", "adapter=" + e);
//        }

        return v;
    }

    public void fillListData(ArrayList<getConversationListSellerResModel> list) {
        Log.e("SellerDashBoardMessage", "getConversation list" + list.size());
        try {
            SellersMessagesAdapter m_adapter = new SellersMessagesAdapter(getActivity(), /*SellerDashBoardMessageFragment.this,*/ R.layout.seller_messages_row_item, list);
            simpleCustomeListView.setAdapter(m_adapter);
        } catch (Exception e) {
            Log.e("SellerDashBoardActivity", "adapter=" + e);
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

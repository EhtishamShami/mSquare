package thinktechsol.msquare.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;

import thinktechsol.msquare.R;
import thinktechsol.msquare.activities.SellerDeshBoardActivity;
import thinktechsol.msquare.adapter.SellerCustomerListAdapter;
import thinktechsol.msquare.globels.globels;
import thinktechsol.msquare.model.SellerCustomerModel;
import thinktechsol.msquare.services.GetSellerCustomerList;
import thinktechsol.msquare.utils.Constant;

public class SellerCustomerFragment extends Fragment {

    TextView title;
    RelativeLayout titlebarlayout, bottombarlayout;
    static String AddProductScreenTagbackButton = "AddProduct";
    ListView customerList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_sellerdashboard_product, container, false);

        SellerDeshBoardActivity.getContext().backBtn.setTag(AddProductScreenTagbackButton);
        SellerDeshBoardActivity.getContext().MakeItemSelected(SellerDeshBoardActivity.CUSTOMER);
//        int colorCode[] = {R.color.cat_item1_color, R.color.cat_item2_color, R.color.cat_item3_color, R.color.cat_item4_color};

        new GetSellerCustomerList(getActivity(), SellerCustomerFragment.this, globels.getGlobelRef().sellerLoginId);

        customerList = (ListView) v.findViewById(R.id.listView);
//        ArrayList<AddProductItem> m_parts = new ArrayList<AddProductItem>();
//        m_parts.add(new AddProductItem("a", R.drawable.messages, colorCode[0]));
//        m_parts.add(new AddProductItem("b", R.drawable.customer, R.color.customerColor));
//        m_parts.add(new AddProductItem("c", R.drawable.order, R.color.orderColor));
//        m_parts.add(new AddProductItem("d", R.drawable.product, R.color.productColor));
//        m_parts.add(new AddProductItem("a", R.drawable.messages, colorCode[0]));


//        try {
//            AddProductAdapter m_adapter = new AddProductAdapter(getActivity(), R.layout.dashboard_row_item1, m_parts);
//            customerList.setAdapter(m_adapter);
//        } catch (Exception e) {
//            Log.e("SellerAddProduct", "adapter=" + e);
//        }

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

    public void fill_data_to_adapter(ArrayList<SellerCustomerModel> list) {
        SellerCustomerListAdapter adapter = new SellerCustomerListAdapter(getActivity(), SellerCustomerFragment.this, R.layout.seller_customer_list_item, list);
        customerList.setAdapter(adapter);
    }
}

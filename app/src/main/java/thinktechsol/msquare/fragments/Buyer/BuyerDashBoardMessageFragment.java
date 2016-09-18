package thinktechsol.msquare.fragments.Buyer;

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
import thinktechsol.msquare.services.buyer.getConversationListBuyer;
import thinktechsol.msquare.utils.Constant;

public class BuyerDashBoardMessageFragment extends Fragment {

    TextView title;
    RelativeLayout titlebarlayout, bottombarlayout;
    ListView msgCustomeListView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_sellerdashboard_message, container, false);

        new getConversationListBuyer(getActivity(), BuyerDashBoardMessageFragment.this);

        msgCustomeListView = (ListView) v.findViewById(R.id.msglistView);

        return v;
    }

    public void fillListData(ArrayList<getConversationListSellerResModel> list) {
        Log.e("SellerDashBoardMessage", "getConversation list" + list.size());
        try {

            msgCustomeListView.setAdapter(null);
            SellersMessagesAdapter m_adapter = new SellersMessagesAdapter(getActivity(), /*BuyerDashBoardMessageFragment.this,*/ R.layout.seller_messages_row_item, list);
            msgCustomeListView.setAdapter(m_adapter);
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

package thinktechsol.msquare.adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import thinktechsol.msquare.R;
import thinktechsol.msquare.activities.AddOrViewProActivity;
import thinktechsol.msquare.activities.SellerDeshBoardActivity;
import thinktechsol.msquare.activities.ViewSellOrderDetailActivity;
import thinktechsol.msquare.fragments.SellerDashBoardMessageFragment;
import thinktechsol.msquare.globels.globels;
import thinktechsol.msquare.model.AddProductItem;
import thinktechsol.msquare.model.getConversationListSellerResModel;
import thinktechsol.msquare.utils.Constant;


/**
 * Created by Arshad.Iqbal on 7/26/2016.
 */
public class SellersMessagesAdapter extends ArrayAdapter<getConversationListSellerResModel> {

    private static final int Layout_items = 0;

    private static int rowHeight = 80 / 4;
//    SellerDashBoardMessageFragment ActivityContext;

    LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    Context context;
    private ArrayList<getConversationListSellerResModel> messageList;

    public SellersMessagesAdapter(Context context, /*SellerDashBoardMessageFragment ActivityContext,*/ int textViewResourceId, ArrayList<getConversationListSellerResModel> messageList) {
        super(context, textViewResourceId, messageList);
        this.messageList = messageList;
        this.context = context;
        //this.ActivityContext = ActivityContext;
    }

    @Override
    public int getViewTypeCount() {
        return 2;
    }

    @Override
    public int getItemViewType(int position) {
        return 0;
    }

    public View getView(final int position, View convertView, ViewGroup parent) {
        int viewType = this.getItemViewType(position);

        try {
            switch (viewType) {
                case Layout_items:
                    final ViewHolder holder;
                    View v = convertView;
                    if (v == null) {
                        LayoutInflater vi = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                        v = vi.inflate(R.layout.seller_messages_row_item, parent, false);

                        holder = new ViewHolder();

                        holder.name_txt = (TextView) v.findViewById(R.id.name);
                        holder.message_txt = (TextView) v.findViewById(R.id.message);


                        v.setTag(holder);
                    } else {
                        holder = (ViewHolder) v.getTag();
                    }

                    final getConversationListSellerResModel myItem = messageList.get(position);
                    if (myItem != null) {
                        if (holder.name_txt != null) {
                            holder.name_txt.setText(myItem.fName);
                        }

                        if (holder.message_txt != null) {
                            holder.message_txt.setText(myItem.messageBody);
                        }

                    }

                    v.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Toast.makeText(context, "" + myItem.orderId, Toast.LENGTH_SHORT).show();
                            globels.getGlobelRef().orderId_for_ordr_info = myItem.orderId;
                            Intent orderDetails = new Intent(context, ViewSellOrderDetailActivity.class);
                            context.startActivity(orderDetails);
                        }
                    });

                    return v;
                default:
                    return null;
            }
        } catch (Exception e) {
            Log.e("SellerDashBoardActivity", "ItemAdapter=" + e);
            return null;
        }
    }

    public RelativeLayout.LayoutParams AppLayoutParam(float height, float width, float mL, float mT, float mR, float mB, View below) {
        RelativeLayout.LayoutParams paramName = new RelativeLayout.LayoutParams(
                getSize("w", width),
                getSize("h", height));
        paramName.addRule(RelativeLayout.CENTER_HORIZONTAL, RelativeLayout.TRUE);
        paramName.addRule(RelativeLayout.CENTER_VERTICAL, RelativeLayout.TRUE);
        paramName.setMargins(getSize("h", mL), getSize("h", mT), getSize("h", mR), getSize("h", mB));
        return paramName;
    }

    public RelativeLayout.LayoutParams AppLayoutParam2(float height, float width, float mL, float mT, float mR, float mB, View toRight) {
        RelativeLayout.LayoutParams paramName = new RelativeLayout.LayoutParams(
                getSize("w", width),
                getSize("h", height));
        paramName.addRule(RelativeLayout.CENTER_HORIZONTAL, RelativeLayout.TRUE);
        paramName.addRule(RelativeLayout.CENTER_IN_PARENT, RelativeLayout.TRUE);
//        if (toRight != null)
//            paramName.addRule(RelativeLayout.RIGHT_OF, toRight.getId());
//        paramName.setMargins(getSize("h", mL), getSize("h", mT), getSize("h", mR), getSize("h", mB));
        return paramName;
    }

    public LinearLayout.LayoutParams AppLayoutParamSubItems(float height, float width, float mL, float mT, float mR, float mB, View below) {
        LinearLayout.LayoutParams paramName = new LinearLayout.LayoutParams(
                getSize("w", width),
                getSize("h", height));
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

    public static class ViewHolder {
        public TextView name_txt;
        public TextView message_txt;
    }
}

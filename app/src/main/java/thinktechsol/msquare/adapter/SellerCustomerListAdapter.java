package thinktechsol.msquare.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import thinktechsol.msquare.R;
import thinktechsol.msquare.activities.SellersOrdersActivity;
import thinktechsol.msquare.fragments.Fragment_2_items;
import thinktechsol.msquare.fragments.SellerCustomerFragment;
import thinktechsol.msquare.globels.globels;
import thinktechsol.msquare.model.GetSellerOrdersModel;
import thinktechsol.msquare.model.SellerCustomerModel;
import thinktechsol.msquare.services.UpdateOrderStatus;
import thinktechsol.msquare.utils.Constant;

//import com.daimajia.swipe.SwipeLayout;

/**
 * Created by Arshad.Iqbal on 2/28/2016.
 */
public class SellerCustomerListAdapter extends ArrayAdapter<SellerCustomerModel> {

    private static final int _row = 0;
    private static final String TAG = "SellerCustomerListAdapter";
    int itemCheckCounter = 0;
    public int SelectedServicesPrice = 0;


    Fragment_2_items TwoItemsFrag;
    SellerCustomerFragment ActivityContext;
    String CurrentOrderType = "0";

    LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    Context context;
    //    subItemClick click;
    private ArrayList<SellerCustomerModel> ordersList;
    private ArrayList<String> imgLoadedIds;


    public SellerCustomerListAdapter(Context context, SellerCustomerFragment ActivityContext, int textViewResourceId, ArrayList<SellerCustomerModel> ordersList) {
        super(context, textViewResourceId, ordersList);
        this.ordersList = ordersList;
        this.context = context;
        this.ActivityContext = ActivityContext;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public int getItemViewType(int position) {
        return 0;
    }

    public View getView(final int position, View convertView, ViewGroup parent) {
        int viewType = this.getItemViewType(position);
        try {
            switch (viewType) {
                case _row:
                    final ViewHolder holder;
                    View v = convertView;
                    if (v == null) {
                        LayoutInflater vi = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                        v = vi.inflate(R.layout.seller_customer_list_item, parent, false);

                        holder = new ViewHolder();

                        holder.lbl = (ImageView) v.findViewById(R.id.lbl);
                        holder.name = (TextView) v.findViewById(R.id.name);
                        holder.email = (TextView) v.findViewById(R.id.email);
                        holder.address = (TextView) v.findViewById(R.id.address);

                        v.setTag(holder);
                    } else {
                        holder = (ViewHolder) v.getTag();
                    }
                    final SellerCustomerModel myItem = ordersList.get(position);

                    v.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Toast.makeText(context, "hi=" + myItem.email, Toast.LENGTH_SHORT).show();
                        }
                    });


                    if (myItem != null) {

                        if (holder.lbl != null) {

                            if (myItem.thumb != null && !myItem.thumb.isEmpty() && myItem.thumb != " ") {
                                Picasso.with(context).load(Constant.imgbaseUrl + myItem.thumb).into(holder.lbl);
                            } else {
                                Picasso.with(context).load(R.drawable.dummy_user).into(holder.lbl);
                            }
                        }
                        if (holder.name != null) {
                            //holder.name.setText(myItem.products.get(0).title);
                            holder.name.setText(myItem.fName + " " + myItem.lName);
                        }
                        if (holder.email != null) {
                            //holder._selected_services.setText(myItem.products.get(0).description);
                            holder.email.setText(myItem.email);
                        }
                        if (holder.address != null) {
//                            holder._total_price.setText(myItem.products.get(0).description);
                            holder.address.setText(myItem.houseNo + " " + myItem.streetNo + " " + myItem.area + " " + myItem.state);
                        }
                    }
                    return v;
                default:
                    return null;
            }
        } catch (Exception e) {
            Log.e("BuyerServiceSellersAda", "Exection in Adapter=" + e);
            return null;
        }
    }


    public RelativeLayout.LayoutParams AppLayoutParam(float height, float width, float mL, float mT, float mR, float mB, View below) {
        RelativeLayout.LayoutParams paramName = new RelativeLayout.LayoutParams(
                getSize("w", width),
                getSize("h", height));
        paramName.addRule(RelativeLayout.CENTER_VERTICAL, RelativeLayout.TRUE);
//        if (below != null)
//            paramName.addRule(RelativeLayout.BELOW, below.getId());
        paramName.setMargins(getSize("h", mL), getSize("h", mT), getSize("h", mR), getSize("h", mB));
        return paramName;
    }

    public LinearLayout.LayoutParams AppLayoutParamSubItems(float height, float width, float mL, float mT, float mR, float mB, View below) {
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

    public static class ViewHolder {
        public ImageView lbl;
        public TextView name;
        public TextView email;
        public TextView address;
    }
}
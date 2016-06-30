package thinktechsol.msquare.adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import thinktechsol.msquare.R;
import thinktechsol.msquare.activities.AddOrViewProActivity;
import thinktechsol.msquare.activities.ViewSellProDetailActivity;
import thinktechsol.msquare.fragments.Fragment_2_items;
import thinktechsol.msquare.interfaceMine.subItemClick;
import thinktechsol.msquare.model.Buyer.getServiceSellers;
import thinktechsol.msquare.model.Response.getSellerProductsResponse;
import thinktechsol.msquare.utils.Constant;

//import com.daimajia.swipe.SwipeLayout;

/**
 * Created by Arshad.Iqbal on 2/28/2016.
 */
public class BuyerServiceSellersListAdapter extends ArrayAdapter<getServiceSellers> {

    private static final int _row = 0;
    private static final String TAG = "ServiceSellersListAdapter";


    Fragment_2_items TwoItemsFrag;
//    AddOrViewProActivity ActivityContext;

    LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    Context context;
//    subItemClick click;
    private ArrayList<getServiceSellers> productList;
    private ArrayList<String> imgLoadedIds;


    public BuyerServiceSellersListAdapter(Context context, /*AddOrViewProActivity ActivityContext,*/ int textViewResourceId, ArrayList<getServiceSellers> productList) {
        super(context, textViewResourceId, productList);
        this.productList = productList;
        this.context = context;
//        this.ActivityContext = ActivityContext;
        imgLoadedIds = new ArrayList<String>();
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
                        v = vi.inflate(R.layout.buyer_service_seller_list_item, parent, false);

                        holder = new ViewHolder();

                        holder.lbl = (ImageView) v.findViewById(R.id.lbl);
                        holder.companyName = (TextView) v.findViewById(R.id.companyName);
                        holder.description = (TextView) v.findViewById(R.id.description);
                        holder.distance = (TextView) v.findViewById(R.id.distance);
                        holder.rating = (RatingBar) v.findViewById(R.id.rating);


                        v.setTag(holder);
                    } else {
                        holder = (ViewHolder) v.getTag();
                    }

                    v.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                        }
                    });

                    getServiceSellers myItem = productList.get(position);

                    if (myItem != null) {

                        if (holder.lbl != null) {
                            //Picasso.with(context).load(myItem.productImages.image).into(holder1.lbl);
                        }
                        if (holder.companyName != null) {
                            //holder1.companyName.setText(myItem.title);
                        }
                        if (holder.description != null) {
                            //holder1.description.setText(myItem.price);
                        }
                        if (holder.distance != null) {
                            //holder1.distance.setText(myItem.price);
                        }
                        if (holder.rating != null) {
                            //holder1.rating.setText(myItem.price);
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
        public TextView companyName;
        public TextView description;
        public TextView distance;
        public RatingBar rating;
    }
}

package thinktechsol.msquare.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.LayerDrawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import thinktechsol.msquare.R;
import thinktechsol.msquare.activities.buyer.ServiceSellerDetailActivity;
import thinktechsol.msquare.fragments.Fragment_2_items;
import thinktechsol.msquare.globels.globels;
import thinktechsol.msquare.model.Buyer.BuyerFavouriteListModel;
import thinktechsol.msquare.utils.Constant;

//import com.daimajia.swipe.SwipeLayout;

/**
 * Created by Arshad.Iqbal on 2/28/2016.
 */
public class BuyerFavouriteListAdapter extends ArrayAdapter<BuyerFavouriteListModel> {

    private static final int _row = 0;
    private static final String TAG = "ServiceSellersListAdapter";


    Fragment_2_items TwoItemsFrag;
//    AddOrViewProActivity ActivityContext;

    LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    Context context;
    //    subItemClick click;
    private ArrayList<BuyerFavouriteListModel> productList;


    public BuyerFavouriteListAdapter(Context context, /*AddOrViewProActivity ActivityContext,*/ int textViewResourceId, ArrayList<BuyerFavouriteListModel> productList) {
        super(context, textViewResourceId, productList);
        this.productList = productList;
        this.context = context;
//        this.ActivityContext = ActivityContext;
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
                        v = vi.inflate(R.layout.buyer_wishlist_list_item, parent, false);

                        holder = new ViewHolder();

                        holder.lbl = (ImageView) v.findViewById(R.id.lbl);
                       // holder.loc_icon = (ImageView) v.findViewById(R.id.loc_icon);
                        holder.name = (TextView) v.findViewById(R.id.name);
                        holder.companyName = (TextView) v.findViewById(R.id.companyName);
                        //holder.distance = (TextView) v.findViewById(R.id.distance);
                        holder.rating = (RatingBar) v.findViewById(R.id.rating);


                        v.setTag(holder);
                    } else {
                        holder = (ViewHolder) v.getTag();
                    }

                    final BuyerFavouriteListModel myItem = productList.get(position);
                    v.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent serviceSellerActivity = new Intent(context, ServiceSellerDetailActivity.class);
                            globels.getGlobelRef().serviceSellerId = myItem.sellerDetial.id;
                            context.startActivity(serviceSellerActivity);
                        }
                    });


                    if (myItem != null) {

                        LayerDrawable stars = (LayerDrawable) holder.rating.getProgressDrawable();
                        stars.getDrawable(2).setColorFilter(context.getResources().getColor(R.color.rating_color), PorterDuff.Mode.SRC_ATOP);
                        stars.getDrawable(0).setColorFilter(Color.parseColor("#d5d5d5"), PorterDuff.Mode.SRC_ATOP);
                        stars.getDrawable(1).setColorFilter(Color.YELLOW, PorterDuff.Mode.SRC_ATOP);

                        if (holder.lbl != null) {
                            Picasso.with(context).load(Constant.imgbaseUrl + myItem.sellerDetial.logo).into(holder.lbl);
                        }

                        if (holder.name != null) {
                            holder.name.setText(myItem.sellerDetial.companyName);
                        }

                        if (holder.companyName != null) {
                            holder.companyName.setText(myItem.sellerDetial.fName+" "+myItem.sellerDetial.lName);
                        }


                        if (holder.rating != null) {
//                            holder.rating.setText(myItem.price);

                            if (!myItem.sellerDetial.sellerRatings.equals("not available")) {
                                float ratingNum = Float.parseFloat(myItem.sellerDetial.sellerRatings);
                                Log.e("ViewSellPro", "rating 2=" + (int) ratingNum);
                                holder.rating.setRating(1);
                                holder.rating.setRating((int) ratingNum);
                            }
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
        public TextView name;
        public RatingBar rating;
    }
}

package thinktechsol.msquare.adapter;

import android.content.Context;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.LayerDrawable;
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

import com.devsmart.android.Utils;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import thinktechsol.msquare.R;
import thinktechsol.msquare.activities.SellersOrdersActivity;
import thinktechsol.msquare.fragments.Fragment_2_items;
import thinktechsol.msquare.globels.globels;
import thinktechsol.msquare.model.Buyer.getServiceSellersProductModel;
import thinktechsol.msquare.model.GetSellerOrdersModel;
import thinktechsol.msquare.utils.Constant;

//import com.daimajia.swipe.SwipeLayout;

/**
 * Created by Arshad.Iqbal on 2/28/2016.
 */
public class SellerOrdersListAdapter extends ArrayAdapter<GetSellerOrdersModel> {

    private static final int _row = 0;
    private static final String TAG = "SellerOrdersListAdapter";
    int itemCheckCounter = 0;
    public int SelectedServicesPrice = 0;


    Fragment_2_items TwoItemsFrag;
    SellersOrdersActivity ActivityContext;

    LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    Context context;
    //    subItemClick click;
    private ArrayList<GetSellerOrdersModel> productList;
    private ArrayList<String> imgLoadedIds;


    public SellerOrdersListAdapter(Context context, SellersOrdersActivity ActivityContext, int textViewResourceId, ArrayList<GetSellerOrdersModel> productList) {
        super(context, textViewResourceId, productList);
        this.productList = productList;
        this.context = context;
        this.ActivityContext = ActivityContext;
        imgLoadedIds = new ArrayList<String>();
        selectedServicesIds = new ArrayList<String>();
        selectedProductsIds = new ArrayList<String>();
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
                        v = vi.inflate(R.layout.seller_order_list_item, parent, false);

                        holder = new ViewHolder();

                        holder.lbl = (ImageView) v.findViewById(R.id.lbl);
                        holder.name = (TextView) v.findViewById(R.id.name);
                        holder._selected_services = (TextView) v.findViewById(R.id._selected_services);
                        holder._total_price = (TextView) v.findViewById(R.id._total_price);
                        holder.order_reject = (ImageView) v.findViewById(R.id.order_reject);
                        holder.order_approve = (ImageView) v.findViewById(R.id.order_approve);


                        v.setTag(holder);
                    } else {
                        holder = (ViewHolder) v.getTag();
                    }
                    final GetSellerOrdersModel myItem = productList.get(position);

                    v.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Toast.makeText(context, "hi=" + myItem.buyerDetails.thumb, Toast.LENGTH_SHORT).show();
                        }
                    });


                    if (myItem != null) {

                        if (holder.lbl != null) {

                            if (myItem.buyerDetails.thumb != null && !myItem.buyerDetails.thumb.isEmpty() && myItem.buyerDetails.thumb != " ") {
                                Picasso.with(context).load(Constant.imgbaseUrl + myItem.buyerDetails.thumb).into(holder.lbl);
                            } else {
                                Picasso.with(context).load(R.drawable.dummy_user).into(holder.lbl);
                            }
                        }
                        if (holder.name != null) {
                            //holder.name.setText(myItem.products.get(0).title);
                            holder.name.setText(myItem.buyerDetails.fName + " " + myItem.buyerDetails.lName);
                        }
                        if (holder._selected_services != null) {
                            //holder._selected_services.setText(myItem.products.get(0).description);
                            holder._selected_services.setText(myItem.noOfServices);
                        }
                        if (holder._total_price != null) {
//                            holder._total_price.setText(myItem.products.get(0).description);
                            holder._total_price.setText(myItem.totalPrice);
                        }

                        if (holder.order_reject != null) {
                            holder.order_reject.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    Toast.makeText(context, "order_reject=" + myItem.id, Toast.LENGTH_SHORT).show();
                                }
                            });
                        }

                        if (holder.order_approve != null) {
                            holder.order_approve.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    Toast.makeText(context, "order_approve=" + myItem.id, Toast.LENGTH_SHORT).show();
                                }
                            });
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
        public TextView _selected_services;
        public TextView _total_price;
        public ImageView order_reject;
        public ImageView order_approve;
    }


    public String allSelectedServices;
    public ArrayList<String> selectedServicesIds;
    public ArrayList<String> selectedProductsIds;

//    public void addIdsToSelectedList() {
//        allSelectedServices = "";
//
//        selectedServicesIds.clear();
//        selectedProductsIds.clear();
//
//        for (int i = 0; i < productList.size(); i++) {
//            final GetSellerOrdersModel myItem = productList.get(i);
//            if (myItem.products.get(0).isChecked) {
//                if (allSelectedServices != "")
//                    allSelectedServices += ", ";
//
//                allSelectedServices += myItem.products.get(0).title;
//
//                selectedServicesIds.add(myItem.products.get(0).serviceId);
//                selectedProductsIds.add(myItem.products.get(0).id);
//            }
//        }
//    }

//    public void updateList() {
//        itemCheckCounter = 0;
//        SelectedServicesPrice = 0;
//
//        for (int i = 0; i < globels.getGlobelRef().productList.size(); i++) {
//            final getServiceSellersProductModel myItem = productList.get(i);
//            myItem.products.get(0).isChecked = false;
//        }
//
//        notifyDataSetInvalidated();
//    }

}
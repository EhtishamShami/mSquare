package thinktechsol.msquare.adapter;

import android.content.Context;
import android.content.Intent;
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
import thinktechsol.msquare.activities.ViewSellOrderDetailActivity;
import thinktechsol.msquare.activities.buyer.BuyerOrdersActivity;
import thinktechsol.msquare.fragments.Fragment_2_items;
import thinktechsol.msquare.globels.globels;
import thinktechsol.msquare.model.Buyer.GetBuyersOrdersModel;
import thinktechsol.msquare.services.buyer.UpdateBuyerOrderStatus;
import thinktechsol.msquare.utils.Constant;

//import com.daimajia.swipe.SwipeLayout;

/**
 * Created by Arshad.Iqbal on 2/28/2016.
 */
public class BuyerOrderListAdapter extends ArrayAdapter<GetBuyersOrdersModel> {

    private static final int _row = 0;
    private static final String TAG = "SellerOrdersListAdapter";
    int itemCheckCounter = 0;
    public int SelectedServicesPrice = 0;


    Fragment_2_items TwoItemsFrag;
    BuyerOrdersActivity ActivityContext;
    String CurrentOrderType = "0";

    LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    Context context;
    //    subItemClick click;
    private ArrayList<GetBuyersOrdersModel> ordersList;
    private ArrayList<String> imgLoadedIds;


    public BuyerOrderListAdapter(Context context, BuyerOrdersActivity ActivityContext, int textViewResourceId, ArrayList<GetBuyersOrdersModel> ordersList) {
        super(context, textViewResourceId, ordersList);
        this.ordersList = ordersList;
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

                        if (globels.getGlobelRef().orderType == "1" || globels.getGlobelRef().orderType == "2" || globels.getGlobelRef().orderType == "0") {
                            holder.order_reject.setVisibility(View.GONE);
                            holder.order_approve.setVisibility(View.GONE);
                        }

                        if (globels.getGlobelRef().orderType == "3") {
                            holder.order_approve.setVisibility(View.GONE);
                        }
                        if (globels.getGlobelRef().orderType == "4") {
                            holder.order_reject.setVisibility(View.GONE);
                        }

                        v.setTag(holder);
                    } else {
                        holder = (ViewHolder) v.getTag();
                    }
                    final GetBuyersOrdersModel myItem = ordersList.get(position);

                    v.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Toast.makeText(context, "hi=" + myItem.id, Toast.LENGTH_SHORT).show();
//                            new UpdateOrderStatus(context, ActivityContext, myItem.id, globels.getGlobelRef().approveRecentOrder, position);
//                            ordersList.remove(position);
//                            notifyDataSetChanged();
                            globels.getGlobelRef().orderId_for_ordr_info = myItem.id;
                            Intent orderDetails = new Intent(ActivityContext, ViewSellOrderDetailActivity.class);
                            ActivityContext.startActivity(orderDetails);
                        }
                    });


                    if (myItem != null) {

                        if (holder.lbl != null) {

//                            if (myItem.buyerDetails.thumb != null && !myItem.buyerDetails.thumb.isEmpty() && myItem.buyerDetails.thumb != " ") {
                            if (myItem.sellerDetails.logo != null && !myItem.sellerDetails.logo.isEmpty() && myItem.sellerDetails.logo != " ") {
                                Picasso.with(context).load(Constant.imgbaseUrl + myItem.sellerDetails.logo).into(holder.lbl);
                            } else {
                                Picasso.with(context).load(R.drawable.dummy_user).into(holder.lbl);
                            }
                        }
                        if (holder.name != null) {
                            //holder.name.setText(myItem.products.get(0).title);
                            holder.name.setText(myItem.sellerDetails.companyName);
                        }
                        if (holder._selected_services != null) {
                            //holder._selected_services.setText(myItem.products.get(0).description);
                            holder._selected_services.setText(myItem.noOfServices);
                        }
                        if (holder._total_price != null) {
//                            holder._total_price.setText(myItem.products.get(0).description);
                            holder._total_price.setText(myItem.totalPrice + " AED");
                        }

                        if (holder.order_reject != null) {
                            holder.order_reject.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {

                                    if (globels.getGlobelRef().orderType == "0") {

//                                        new UpdateBuyerOrderStatus(context, ActivityContext, myItem.id, globels.getGlobelRef().BuyerRejectOrder, position);
//                                        ordersList.remove(position);
//                                        notifyDataSetChanged();
                                    } else if (globels.getGlobelRef().orderType == "1") {

                                    } else if (globels.getGlobelRef().orderType == "2") {

                                    } else if (globels.getGlobelRef().orderType == "3") {
                                        Toast.makeText(context, "order_reject of Complete=", Toast.LENGTH_SHORT).show();
                                        new UpdateBuyerOrderStatus(context, ActivityContext, myItem.id, globels.getGlobelRef().BuyerRejectOrder, position);
                                        ordersList.remove(position);
                                        notifyDataSetChanged();
                                    } else if (globels.getGlobelRef().orderType == "4") {

                                    }
                                }
                            });
                        }

                        if (holder.order_approve != null) {
                            holder.order_approve.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {

                                    if (globels.getGlobelRef().orderType == "0") {
                                        new UpdateBuyerOrderStatus(context, ActivityContext, myItem.id, globels.getGlobelRef().approveRecentOrder, position);
                                        ordersList.remove(position);
                                        notifyDataSetChanged();
                                    } else if (globels.getGlobelRef().orderType == "1") {
                                        new UpdateBuyerOrderStatus(context, ActivityContext, myItem.id, globels.getGlobelRef().approveInProcessOrder, position);
                                        ordersList.remove(position);
                                        notifyDataSetChanged();
                                    } else if (globels.getGlobelRef().orderType == "2") {

                                    } else if (globels.getGlobelRef().orderType == "3") {

                                    } else if (globels.getGlobelRef().orderType == "4") {
                                        Toast.makeText(context, "order_approve of dispute=", Toast.LENGTH_SHORT).show();
                                        new UpdateBuyerOrderStatus(context, ActivityContext, myItem.id, "3", position);
                                        ordersList.remove(position);
                                        notifyDataSetChanged();
                                    }

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
//        for (int i = 0; i < ordersList.size(); i++) {
//            final GetSellerOrdersModel myItem = ordersList.get(i);
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
//        for (int i = 0; i < globels.getGlobelRef().ordersList.size(); i++) {
//            final getServiceSellersProductModel myItem = ordersList.get(i);
//            myItem.products.get(0).isChecked = false;
//        }
//
//        notifyDataSetInvalidated();
//    }

}
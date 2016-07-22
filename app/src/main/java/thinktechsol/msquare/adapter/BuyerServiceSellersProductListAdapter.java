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

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import thinktechsol.msquare.R;
import thinktechsol.msquare.fragments.Buyer.SellersServiceFragment;
import thinktechsol.msquare.fragments.Fragment_2_items;
import thinktechsol.msquare.globels.globels;
import thinktechsol.msquare.model.Buyer.getServiceSellersProductModel;
import thinktechsol.msquare.utils.Constant;

//import com.daimajia.swipe.SwipeLayout;

/**
 * Created by Arshad.Iqbal on 2/28/2016.
 */
public class BuyerServiceSellersProductListAdapter extends ArrayAdapter<getServiceSellersProductModel> {

    private static final int _row = 0;
    private static final String TAG = "ServiceSellersListAdapter";
    int itemCheckCounter = 0;
    public int SelectedServicesPrice = 0;


    Fragment_2_items TwoItemsFrag;
    SellersServiceFragment ActivityContext;

    LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    Context context;
    //    subItemClick click;
    private ArrayList<getServiceSellersProductModel> productList;
    private ArrayList<String> imgLoadedIds;


    public BuyerServiceSellersProductListAdapter(Context context, SellersServiceFragment ActivityContext, int textViewResourceId, ArrayList<getServiceSellersProductModel> productList) {
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
                        v = vi.inflate(R.layout.buyer_service_seller_product_list_item, parent, false);

                        holder = new ViewHolder();

                        holder.lbl = (ImageView) v.findViewById(R.id.lbl);
                        holder.companyName = (TextView) v.findViewById(R.id.companyName);
                        holder.description = (TextView) v.findViewById(R.id.description);
                        holder.rating = (RatingBar) v.findViewById(R.id.rating);
                        holder.CheckBox = (CheckBox) v.findViewById(R.id.isChecked);

                        v.setTag(holder);
                    } else {
                        holder = (ViewHolder) v.getTag();
                    }
                    final getServiceSellersProductModel myItem = productList.get(position);

                    v.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
//                            Intent serviceSellerActivity=new Intent(context, ServiceSellerDetailActivity.class);
//                            context.startActivity(serviceSellerActivity);
                            //Toast.makeText(context, "hi=" + myItem.products.get(0).id + "&" + myItem.products.get(0).sellerId, Toast.LENGTH_SHORT).show();
                            if (holder.CheckBox != null) {

                                if (myItem.products.get(0).isChecked == true) {
                                    myItem.products.get(0).isChecked = false;
                                    itemCheckCounter -= 1;

                                    if (SelectedServicesPrice != 0)
                                        SelectedServicesPrice -= Integer.parseInt(myItem.products.get(0).price);
                                } else {
                                    myItem.products.get(0).isChecked = true;
                                    itemCheckCounter += 1;

                                    SelectedServicesPrice += Integer.parseInt(myItem.products.get(0).price);
                                }

                                if (itemCheckCounter > 0) {
//                                    ActivityContext.reservationBtn.setVisibility(View.VISIBLE);
                                    ActivityContext.showReservationButton(true, SelectedServicesPrice);
                                } else {
//                                    ActivityContext.reservationBtn.setVisibility(View.GONE);
                                    ActivityContext.showReservationButton(false, SelectedServicesPrice);
                                }
//                                Toast.makeText(context, "product list size=" + SelectedServicesPrice, Toast.LENGTH_SHORT).show();

                                holder.CheckBox.setChecked(myItem.products.get(0).isChecked);
                            }
                        }
                    });


                    if (myItem != null) {

                        LayerDrawable stars = (LayerDrawable) holder.rating.getProgressDrawable();
                        stars.getDrawable(2).setColorFilter(context.getResources().getColor(R.color.rating_color), PorterDuff.Mode.SRC_ATOP);
                        stars.getDrawable(0).setColorFilter(Color.parseColor("#d5d5d5"), PorterDuff.Mode.SRC_ATOP);
                        stars.getDrawable(1).setColorFilter(Color.YELLOW, PorterDuff.Mode.SRC_ATOP);

                        if (holder.lbl != null) {
                            Log.e("ViewSellPro", "images of product items=" + myItem.products.get(0).productImages.get(0).image);
                            Picasso.with(context).load(myItem.products.get(0).productImages.get(0).image).into(holder.lbl);
                        }
                        if (holder.companyName != null) {
                            holder.companyName.setText(myItem.products.get(0).title);
                        }
                        if (holder.description != null) {
                            holder.description.setText(myItem.products.get(0).description);
                        }
                        if (holder.rating != null) {

                            if (!myItem.products.get(0).productRating.equals("not available")) {
                                float ratingNum = Float.parseFloat(myItem.products.get(0).productRating);
                                Log.e("ViewSellPro", "rating 2=" + (int) ratingNum);

                                holder.rating.setRating(1);
                                holder.rating.setRating((int) ratingNum);
                            }
                        }
                        if (holder.CheckBox != null) {
                            holder.CheckBox.setChecked(myItem.products.get(0).isChecked);
                        }

                        if (itemCheckCounter > 0) {
                            ActivityContext.reservationBtn.setVisibility(View.VISIBLE);
                        } else {
                            ActivityContext.reservationBtn.setVisibility(View.GONE);
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
        public RatingBar rating;
        public CheckBox CheckBox;
    }


    public String allSelectedServices;
    public ArrayList<String> selectedServicesIds;
    public ArrayList<String> selectedProductsIds;

    public void addIdsToSelectedList() {
        allSelectedServices = "";

        selectedServicesIds.clear();
        selectedProductsIds.clear();

        for (int i = 0; i < productList.size(); i++) {
            final getServiceSellersProductModel myItem = productList.get(i);
            if (myItem.products.get(0).isChecked) {
                if (allSelectedServices != "")
                    allSelectedServices += ", ";

                allSelectedServices += myItem.products.get(0).title;

                selectedServicesIds.add(myItem.products.get(0).serviceId);
                selectedProductsIds.add(myItem.products.get(0).id);
            }
        }
    }

    public void updateList() {
        itemCheckCounter = 0;
        SelectedServicesPrice = 0;

        for (int i = 0; i < globels.getGlobelRef().productList.size(); i++) {
            final getServiceSellersProductModel myItem = productList.get(i);
            myItem.products.get(0).isChecked = false;
        }

        notifyDataSetInvalidated();
    }

}
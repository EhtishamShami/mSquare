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
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.vision.text.Text;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import thinktechsol.msquare.R;
import thinktechsol.msquare.activities.buyer.ViewBuyerProDetailActivity;
import thinktechsol.msquare.fragments.Buyer.SellersServiceFragment;
import thinktechsol.msquare.fragments.Fragment_2_items;
import thinktechsol.msquare.globels.globels;
import thinktechsol.msquare.model.Buyer.Products;
import thinktechsol.msquare.model.Buyer.getServiceSellersProductModel;
import thinktechsol.msquare.utils.Constant;

//import com.daimajia.swipe.SwipeLayout;

/**
 * Created by Arshad.Iqbal on 2/28/2016.
 */
public class BuyerServiceSellersProductListAdapter extends ArrayAdapter<Products> {

    private static final int _row = 0;
    private static final String TAG = "ServiceSellersListAdapter";
    int itemCheckCounter = 0;
    public int SelectedServicesPrice = 0;


    Fragment_2_items TwoItemsFrag;
    SellersServiceFragment ActivityContext;

    LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    Context context;
    //    subItemClick click;
    private ArrayList<Products> productList;
    private ArrayList<String> imgLoadedIds;
    boolean isProduct = false;
    String isProductStr = "";


    public BuyerServiceSellersProductListAdapter(Context context, SellersServiceFragment ActivityContext, int textViewResourceId, ArrayList<Products> productList, String isProductStr) {
        super(context, textViewResourceId, productList);
        this.productList = productList;
        this.context = context;
        this.isProductStr = isProductStr;
        this.ActivityContext = ActivityContext;
        imgLoadedIds = new ArrayList<String>();
        selectedServicesIds = new ArrayList<String>();
        selectedProductsIds = new ArrayList<String>();

        if (this.isProductStr.equals("1"))
            isProduct = true;
        else
            isProduct = false;
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
                        holder.companyName = (TextView) v.findViewById(R.id.name);
                        holder.description = (TextView) v.findViewById(R.id.time);
                        holder.rating = (RatingBar) v.findViewById(R.id.rating);
                        holder.CheckBox = (CheckBox) v.findViewById(R.id.isChecked);
                        holder.addQuant = (ImageButton) v.findViewById(R.id.addQuant);
                        holder.decrQuant = (ImageButton) v.findViewById(R.id.decrQuant);
                        holder.tvQuantity = (TextView) v.findViewById(R.id.tvQuantity);
                        holder.tv_quantity = (TextView) v.findViewById(R.id.tv_quantity);

                        if (isProduct) {
                            holder.addQuant.setVisibility(View.VISIBLE);
                            holder.decrQuant.setVisibility(View.VISIBLE);
                            holder.tvQuantity.setVisibility(View.VISIBLE);
                            holder.tv_quantity.setVisibility(View.VISIBLE);
                        } else {
                            holder.addQuant.setVisibility(View.INVISIBLE);
                            holder.decrQuant.setVisibility(View.INVISIBLE);
                            holder.tvQuantity.setVisibility(View.INVISIBLE);
                            holder.tv_quantity.setVisibility(View.INVISIBLE);
                        }

                        v.setTag(holder);
                    } else {
                        holder = (ViewHolder) v.getTag();
                    }


                    final Products myItem = productList.get(position);
                    holder.companyName.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            globels.getGlobelRef().singleProductForBuyProDetail = myItem;
                            Intent serviceSellerActivity = new Intent(context, ViewBuyerProDetailActivity.class);
                            context.startActivity(serviceSellerActivity);
                        }
                    });

                    v.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            globels.getGlobelRef().singleProductForBuyProDetail = myItem;

                            if (holder.CheckBox != null) {

                                if (myItem.isChecked == true) {
                                    myItem.isChecked = false;
                                    itemCheckCounter -= 1;

                                    if (SelectedServicesPrice != 0)
                                        SelectedServicesPrice -= Integer.parseInt(myItem.price);
                                } else {
                                    myItem.isChecked = true;
                                    itemCheckCounter += 1;

                                    SelectedServicesPrice += Integer.parseInt(myItem.price);
                                }

                                if (itemCheckCounter > 0) {
                                    ActivityContext.showReservationButton(true, SelectedServicesPrice);
                                } else {
                                    ActivityContext.showReservationButton(false, SelectedServicesPrice);
                                }

                                holder.CheckBox.setChecked(myItem.isChecked);
                            }
                        }
                    });

                    holder.addQuant.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            int proQuantity = Integer.parseInt(holder.tvQuantity.getText().toString());
                            proQuantity = proQuantity + 1;

                            holder.tvQuantity.setText("" + proQuantity);
                        }
                    });

                    holder.decrQuant.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            int proQuantity = Integer.parseInt(holder.tvQuantity.getText().toString());
                            if (proQuantity > 0)
                                proQuantity = proQuantity - 1;
                            holder.tvQuantity.setText("" + proQuantity);
                        }
                    });


                    if (myItem != null) {

                        LayerDrawable stars = (LayerDrawable) holder.rating.getProgressDrawable();
                        stars.getDrawable(2).setColorFilter(context.getResources().getColor(R.color.rating_color), PorterDuff.Mode.SRC_ATOP);
                        stars.getDrawable(0).setColorFilter(Color.parseColor("#d5d5d5"), PorterDuff.Mode.SRC_ATOP);
                        stars.getDrawable(1).setColorFilter(Color.YELLOW, PorterDuff.Mode.SRC_ATOP);

                        if (holder.lbl != null) {
                            Log.e("ViewSellPro", "images of product items=" + myItem.productImages.get(0).image);
                            Picasso.with(context).load(myItem.productImages.get(0).image).into(holder.lbl);
                        }
                        if (holder.companyName != null) {
                            holder.companyName.setText(myItem.title);
                        }
                        if (holder.description != null) {
                            holder.description.setText(myItem.description);
                        }
                        if (holder.rating != null) {

                            if (!myItem.productRating.equals("not available")) {
                                float ratingNum = Float.parseFloat(myItem.productRating);
                                Log.e("ViewSellPro", "rating 2=" + (int) ratingNum);

                                holder.rating.setRating(1);
                                holder.rating.setRating((int) ratingNum);
                            }
                        }
                        if (holder.CheckBox != null) {
                            holder.CheckBox.setChecked(myItem.isChecked);
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
        public ImageButton addQuant;
        public ImageButton decrQuant;
        public TextView tvQuantity;
        public TextView tv_quantity;
    }


    public String allSelectedServices;
    public ArrayList<String> selectedServicesIds;
    public ArrayList<String> selectedProductsIds;

    public void addIdsToSelectedList() {
        allSelectedServices = "";

        selectedServicesIds.clear();
        selectedProductsIds.clear();

        for (int i = 0; i < productList.size(); i++) {
            final Products myItem = productList.get(i);
            if (myItem.isChecked) {
                if (allSelectedServices != "")
                    allSelectedServices += ", ";

                allSelectedServices += myItem.title;

                selectedServicesIds.add(myItem.serviceId);
                selectedProductsIds.add(myItem.id);
            }
        }
    }

    public void updateList() {
        itemCheckCounter = 0;
        SelectedServicesPrice = 0;

        for (int i = 0; i < globels.getGlobelRef().productList2.size(); i++) {
            final Products myItem = productList.get(i);
            myItem.isChecked = false;
        }

        notifyDataSetInvalidated();
    }


}
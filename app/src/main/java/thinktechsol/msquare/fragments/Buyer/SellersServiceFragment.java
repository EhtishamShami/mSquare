package thinktechsol.msquare.fragments.Buyer;


import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.LayerDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import thinktechsol.msquare.R;
import thinktechsol.msquare.activities.buyer.BuyerReservationActivity;
import thinktechsol.msquare.adapter.BuyerServiceSellersListAdapter;
import thinktechsol.msquare.adapter.BuyerServiceSellersProductListAdapter;
import thinktechsol.msquare.globels.globels;
import thinktechsol.msquare.model.Buyer.getServiceSellersModel;
import thinktechsol.msquare.model.Buyer.getServiceSellersProductModel;
import thinktechsol.msquare.services.buyer.getServiceSellersProduct;
import thinktechsol.msquare.services.getServiceSellers;
import thinktechsol.msquare.utils.Constant;

public class SellersServiceFragment extends Fragment {

    ListView listView;
    BuyerServiceSellersProductListAdapter adapter;
    ArrayList<getServiceSellersProductModel> productList;
    RelativeLayout pro_name_rating_price_layout, pro_name_line_layout;
    RatingBar rating;
    TextView sellers_title;
    public Button reservationBtn;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_sellers_service, container, false);


        pro_name_rating_price_layout = (RelativeLayout) v.findViewById(R.id.pro_name_rating_price_layout);
        pro_name_line_layout = (RelativeLayout) v.findViewById(R.id.pro_name_line_layout);
        sellers_title = (TextView) v.findViewById(R.id.sellers_title);
        rating = (RatingBar) v.findViewById(R.id.rating);
        reservationBtn = (Button) v.findViewById(R.id.reservationBtn);

//        new getServiceSellersProduct(getActivity(),SellersServiceFragment.this,Constant.sellerServiceId,"24.433904943494827","54.41303014755249");
        Log.e("SellersServiceFrag", "list value 2=" + globels.getGlobelRef().productList);
        listView = (ListView) v.findViewById(R.id.list);
        adapter = new BuyerServiceSellersProductListAdapter(getActivity(), SellersServiceFragment.this, R.layout.buyer_service_seller_list_item, globels.getGlobelRef().productList);
        listView.setAdapter(adapter);

        pro_name_rating_price_layout.setLayoutParams(AppLayoutParam(10.00f, 100f, 0, 0, 0, 0, null));
        pro_name_line_layout.setLayoutParams(AppLayoutParam3(10.00f, 60f, 0, 0, 0, 0, null, 0));

        LayerDrawable stars = (LayerDrawable) rating.getProgressDrawable();
        stars.getDrawable(2).setColorFilter(getActivity().getResources().getColor(R.color.rating_color), PorterDuff.Mode.SRC_ATOP);
        stars.getDrawable(0).setColorFilter(Color.parseColor("#d5d5d5"), PorterDuff.Mode.SRC_ATOP);
        stars.getDrawable(1).setColorFilter(Color.YELLOW, PorterDuff.Mode.SRC_ATOP);


        sellers_title.setText(globels.getGlobelRef().productList.get(0).sellerInfo.companyName);
//        float ratingNum = 2/*Float.parseFloat(myItem.productRating)*/;
        if (!globels.getGlobelRef().productList.get(0).sellerInfo.sellerRating.equals("not available") && !globels.getGlobelRef().productList.get(0).sellerInfo.sellerRating.equals("null")) {
            float ratingNum = Float.parseFloat(globels.getGlobelRef().productList.get(0).sellerInfo.sellerRating);
//        holder.rating.setRating(1);
            rating.setRating((int) ratingNum);
        }

        reservationBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Toast.makeText(getContext(), ""+, Toast.LENGTH_SHORT).show();
                adapter.addIdsToSelectedList();
                globels.getGlobelRef().allSelectedServices = adapter.allSelectedServices;
                Toast.makeText(getContext(), "selected list size is=" + adapter.allSelectedServices, Toast.LENGTH_SHORT).show();

                startActivity(new Intent(getActivity(), BuyerReservationActivity.class));

            }
        });

        return v;
    }

    public void fillProductListWithData(ArrayList<getServiceSellersProductModel> list) {
        productList = new ArrayList<getServiceSellersProductModel>();

        Log.e("BuyerServiceSellersList", "list size is=" + list.size());
        for (int i = 0; i < list.size(); i++) {
//            m_parts.add(new AddProductItem(list.get(i).id, list.get(i).name, Constant.imgbaseUrl + list.get(i).thumb, colorCode[colorId]));
            //productList.add(new getServiceSellersProductModel(list.get(i).mobileNo, list.get(i).logo, list.get(i).status, list.get(i).tradeNo, list.get(i).documents, list.get(i).lName, list.get(i).companyName, list.get(i).password, list.get(i).fName, list.get(i).productRating, list.get(i).id, list.get(i).phoneNo, list.get(i).distance, list.get(i).email, list.get(i).address, list.get(i).description, list.get(i).activationCode, list.get(i).service, list.get(i).longitude, list.get(i).latitude, list.get(i).datetime));
        }

        adapter = new BuyerServiceSellersProductListAdapter(getActivity(), SellersServiceFragment.this, R.layout.buyer_service_seller_list_item, list);
        listView.setAdapter(adapter);
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

    public RelativeLayout.LayoutParams AppLayoutParam3(float height, float width, float mL, float mT, float mR, float mB, View below, int toRightView) {
        RelativeLayout.LayoutParams paramName = new RelativeLayout.LayoutParams(
                Constant.getSize("w", width),
                Constant.getSize("h", height));
        paramName.addRule(RelativeLayout.CENTER_VERTICAL, RelativeLayout.TRUE);

        if (toRightView != 0)
            paramName.addRule(RelativeLayout.RIGHT_OF, toRightView);

        if (below != null)
            paramName.addRule(RelativeLayout.BELOW, below.getId());
        paramName.setMargins(Constant.getSize("h", mL), Constant.getSize("h", mT), Constant.getSize("h", mR), Constant.getSize("h", mB));
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

    public void showReservationButton(boolean flag, int price) {

        globels.getGlobelRef().SelectedServicesPrice = price;

        if (flag)
            reservationBtn.setVisibility(View.VISIBLE);
        else
            reservationBtn.setVisibility(View.GONE);
    }
}

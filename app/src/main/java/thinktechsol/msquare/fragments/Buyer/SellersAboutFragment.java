package thinktechsol.msquare.fragments.Buyer;


import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.LayerDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;

import thinktechsol.msquare.R;
import thinktechsol.msquare.adapter.BuyerServiceSellersListAdapter;
import thinktechsol.msquare.globels.globels;
import thinktechsol.msquare.model.Buyer.getServiceSellersModel;
import thinktechsol.msquare.services.buyer.AddToFavouriteBuyerService;
import thinktechsol.msquare.utils.Constant;

public class SellersAboutFragment extends Fragment {

    ListView listView;
    BuyerServiceSellersListAdapter adapter;
    ArrayList<getServiceSellersModel> productList;
    RelativeLayout pro_name_rating_price_layout, pro_name_line_layout;
    RatingBar rating;
    TextView lbl_name_detail, lbl_contact_detail, lbl_email_detail, lbl_address_detail;
    Button btnAddToFavorite;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_sellers_about, container, false);

        lbl_name_detail = (TextView) v.findViewById(R.id.lbl_name_detail);
        rating = (RatingBar) v.findViewById(R.id.rating);
        lbl_contact_detail = (TextView) v.findViewById(R.id.lbl_contact_detail);
        lbl_email_detail = (TextView) v.findViewById(R.id.lbl_email_detail);
        lbl_address_detail = (TextView) v.findViewById(R.id.lbl_address_detail);
        btnAddToFavorite = (Button) v.findViewById(R.id.btnAddToFavorite);

        btnAddToFavorite.setBackgroundColor(this.getResources().getColor(globels.getGlobelRef().them_color));



        LayerDrawable stars = (LayerDrawable) rating.getProgressDrawable();
        stars.getDrawable(2).setColorFilter(getActivity().getResources().getColor(R.color.rating_color), PorterDuff.Mode.SRC_ATOP);
        stars.getDrawable(0).setColorFilter(Color.parseColor("#d5d5d5"), PorterDuff.Mode.SRC_ATOP);
        stars.getDrawable(1).setColorFilter(Color.YELLOW, PorterDuff.Mode.SRC_ATOP);


        lbl_name_detail.setText(globels.getGlobelRef().productList.get(0).sellerInfo.companyName);

        if (!globels.getGlobelRef().productList.get(0).sellerInfo.sellerRating.equals("not available") && !globels.getGlobelRef().productList.get(0).sellerInfo.sellerRating.equals("null")) {
            float ratingNum = Float.parseFloat(globels.getGlobelRef().productList.get(0).sellerInfo.sellerRating);
            rating.setRating((int) ratingNum);
        }

        lbl_contact_detail.setText(globels.getGlobelRef().productList.get(0).sellerInfo.mobileNo);
        lbl_email_detail.setText(globels.getGlobelRef().productList.get(0).sellerInfo.email);
        lbl_address_detail.setText(globels.getGlobelRef().productList.get(0).sellerInfo.address);


        btnAddToFavorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AddToFavouriteBuyerService(getActivity(), globels.getGlobelRef().productList.get(0).sellerInfo.id, globels.getGlobelRef().buyerLoginId);
            }
        });

        return v;
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
}

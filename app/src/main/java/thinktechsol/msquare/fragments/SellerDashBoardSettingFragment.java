package thinktechsol.msquare.fragments;

import android.app.Fragment;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.LayerDrawable;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import thinktechsol.msquare.R;
import thinktechsol.msquare.activities.SellerViewStaffActivity;
import thinktechsol.msquare.activities.UserTypeActivity;
import thinktechsol.msquare.globels.globels;
import thinktechsol.msquare.model.SellerDetailsByIdModel;
import thinktechsol.msquare.services.GetSellerDetailsById;
import thinktechsol.msquare.services.buyer.UpdateDeviceInfoService;
import thinktechsol.msquare.utils.Constant;

public class SellerDashBoardSettingFragment extends Fragment {

    TextView seller_title, staffText, phoneText, addressText, logoutText, emailText;
    RelativeLayout titlebarlayout, seller_detials_layout, seller_title_layout, ratinglayout, staff_layout, seller_email_layout, phone_layout, address_layout, seller_logout_layout;
    RatingBar rating;
    ImageView userImage, btnViewStaff, logoutImg;

    SharedPreferences preferences;
    SharedPreferences.Editor editor;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_sellerdashboard_setting, container, false);

        preferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
        editor = preferences.edit();

        new GetSellerDetailsById(getActivity(), SellerDashBoardSettingFragment.this, globels.getGlobelRef().sellerLoginId);

        RelativeLayout imgs = (RelativeLayout) v.findViewById(R.id.imgs);
        imgs.setLayoutParams(AppLayoutParam(30.00f, 100f, 0, 0, 0, 0, null));

        userImage = (ImageView) v.findViewById(R.id.userImage);

        seller_detials_layout = (RelativeLayout) v.findViewById(R.id.seller_detials_layout);
        seller_detials_layout.setLayoutParams(AppLayoutParam(10.00f, 100f, 0, 0, 0, 0, imgs));

        seller_title_layout = (RelativeLayout) v.findViewById(R.id.seller_title_layout);
        seller_title_layout.setLayoutParams(AppLayoutParam3(10.00f, 70f, 0, 0, 0, 0, null, 0));

        ratinglayout = (RelativeLayout) v.findViewById(R.id.ratinglayout);
        ratinglayout.setLayoutParams(AppLayoutParam3(10.00f, 30f, 0, 0, 0, 0, null, R.id.seller_title_layout));

        staff_layout = (RelativeLayout) v.findViewById(R.id.staff_layout);
        staff_layout.setLayoutParams(AppLayoutParam(8.00f, 100f, 2, 0, 0, 0, null));

        seller_email_layout = (RelativeLayout) v.findViewById(R.id.seller_email_layout);
        seller_email_layout.setLayoutParams(AppLayoutParam(8.00f, 100f, 2, 0, 0, 0, staff_layout));

        phone_layout = (RelativeLayout) v.findViewById(R.id.phone_layout);
        phone_layout.setLayoutParams(AppLayoutParam(8.00f, 100f, 2, 0, 0, 0, seller_email_layout));

        address_layout = (RelativeLayout) v.findViewById(R.id.address_layout);
        address_layout.setLayoutParams(AppLayoutParam(8.00f, 100f, 2, 0, 0, 0, phone_layout));


        seller_logout_layout = (RelativeLayout) v.findViewById(R.id.seller_logout_layout);
        seller_logout_layout.setLayoutParams(AppLayoutParam(8.00f, 100f, 2, 0, 0, 0, address_layout));


        btnViewStaff = (ImageView) v.findViewById(R.id.btnViewStaff);
        seller_title = (TextView) v.findViewById(R.id.seller_title);
        staffText = (TextView) v.findViewById(R.id.staffText);
        emailText = (TextView) v.findViewById(R.id.emailText);
        phoneText = (TextView) v.findViewById(R.id.phoneText);
        addressText = (TextView) v.findViewById(R.id.addressText);
        logoutText = (TextView) v.findViewById(R.id.logoutText);
        logoutImg = (ImageView) v.findViewById(R.id.logoutImg);


        rating = (RatingBar) v.findViewById(R.id.rating);
        LayerDrawable stars = (LayerDrawable) rating.getProgressDrawable();
        stars.getDrawable(2).setColorFilter(getActivity().getResources().getColor(R.color.rating_color), PorterDuff.Mode.SRC_ATOP);
        stars.getDrawable(0).setColorFilter(Color.parseColor("#d5d5d5"), PorterDuff.Mode.SRC_ATOP);
        stars.getDrawable(1).setColorFilter(Color.YELLOW, PorterDuff.Mode.SRC_ATOP);


        btnViewStaff.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), SellerViewStaffActivity.class));
            }
        });

        logoutText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                new UpdateDeviceInfoService(getActivity(), "seller", globels.getGlobelRef().sellerLoginId, globels.getGlobelRef().deviceToken);

                editor.putString("sellerLoginId", "");
                editor.putBoolean("isSellerLogin", false);
                editor.putString("token", "");
                editor.commit();

                globels.getGlobelRef().deviceToken = "";

                Intent i = new Intent(getActivity(),
                        UserTypeActivity.class);
                startActivity(i);
                getActivity().finish();


            }
        });
        logoutImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                new UpdateDeviceInfoService(getActivity(), "seller", globels.getGlobelRef().sellerLoginId, globels.getGlobelRef().deviceToken);

                editor.putString("sellerLoginId", "");
                editor.putBoolean("isSellerLogin", false);
                editor.putString("token", "");
                editor.commit();

                globels.getGlobelRef().deviceToken = "";

                Intent i = new Intent(getActivity(),
                        UserTypeActivity.class);
                startActivity(i);
                getActivity().finish();
            }
        });

        return v;
    }

    public void fillProductListWithData(ArrayList<SellerDetailsByIdModel> SellerDetail) {
        try {

            seller_title.setText("" + SellerDetail.get(0).fName + " " + SellerDetail.get(0).lName);
            //staffText.setText(""+SellerDetail.get(0));
            emailText.setText("" + SellerDetail.get(0).email);
            phoneText.setText("" + SellerDetail.get(0).phoneNo);
            addressText.setText("" + SellerDetail.get(0).address);

            Picasso.with(getActivity()).load(Constant.imgbaseUrl + SellerDetail.get(0).logo).into(userImage);

            if (!SellerDetail.get(0).sellerRatings.equals("not available") && !SellerDetail.get(0).sellerRatings.equals("null")) {
                float ratingNum = Float.parseFloat(SellerDetail.get(0).sellerRatings);
                rating.setRating((int) ratingNum);
            }

        } catch (Exception e) {

        }
    }

    public RelativeLayout.LayoutParams AppLayoutParam2(float height, float width, float mL, float mT, float mR, float mB, String leftOrRight) {
        RelativeLayout.LayoutParams paramName = new RelativeLayout.LayoutParams(
                Constant.getSize("w", width),
                Constant.getSize("h", height));
        if (leftOrRight.equals("right"))
            paramName.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
        paramName.addRule(RelativeLayout.CENTER_VERTICAL, RelativeLayout.TRUE);

        paramName.setMargins(Constant.getSize("h", mL), Constant.getSize("h", mT), Constant.getSize("h", mR), Constant.getSize("h", mB));
        return paramName;
    }

    public RelativeLayout.LayoutParams AppLayoutParam(float height, float width, float mL, float mT, float mR, float mB, View below) {
        RelativeLayout.LayoutParams paramName = new RelativeLayout.LayoutParams(
                Constant.getSize("w", width),
                Constant.getSize("h", height));
        paramName.addRule(RelativeLayout.CENTER_HORIZONTAL, RelativeLayout.TRUE);
        if (below != null)
            paramName.addRule(RelativeLayout.BELOW, below.getId());
        paramName.setMargins(Constant.getSize("h", mL), Constant.getSize("h", mT), Constant.getSize("h", mR), Constant.getSize("h", mB));
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


}

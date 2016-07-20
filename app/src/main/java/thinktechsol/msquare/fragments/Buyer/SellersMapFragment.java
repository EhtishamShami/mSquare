package thinktechsol.msquare.fragments.Buyer;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import thinktechsol.msquare.R;
import thinktechsol.msquare.globels.globels;
import thinktechsol.msquare.utils.Constant;

public class SellersMapFragment extends Fragment  {

    private GoogleMap mMap;
    static View v = null;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        if (v == null) {
            v = inflater.inflate(R.layout.fragment_seller_map, container, false);

            LatLng testLatLng = new LatLng(Double.parseDouble(globels.getGlobelRef().productList.get(0).sellerInfo.latitude),
                    Double.parseDouble(globels.getGlobelRef().productList.get(0).sellerInfo.longitude));

//            try {
//                if (mMap == null) {
//                    mMap = ((MapFragment) getActivity().getFragmentManager().
//                            findFragmentById(R.id.map)).getMap();
//                }
//                mMap.setMapType(GoogleMap.MAP_TYPE_TERRAIN);
//                Marker testMarker = mMap.addMarker(new MarkerOptions().
//                        position(testLatLng).title("Address"));
//                CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(testLatLng, 10);
//                mMap.animateCamera(cameraUpdate);
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
        }



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

    public int getSize(String dimension, float size) {
        float x = 0;
        if (dimension.equals("w")) {
            x = (size / 100) * Constant.screenWidth;
        } else {
            x = (size / 100) * Constant.screenHeight;
        }
        return (int) x;
    }

//    @Override
//    public void onMapReady(GoogleMap googleMap) {
//        mMap = googleMap;
//
//        // Add a marker in Sydney and move the camera
//        LatLng sydney = new LatLng(-34, 151);
//        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
//        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
//    }


}
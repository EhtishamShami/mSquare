package thinktechsol.msquare.fragments.Buyer;


import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.DisplayMetrics;
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
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import thinktechsol.msquare.R;
import thinktechsol.msquare.globels.globels;
import thinktechsol.msquare.utils.Constant;

public class SellersMapFragment extends Fragment implements OnMapReadyCallback {

    private GoogleMap mMap;
    static View v = null;


    LatLng testLatLng;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        if (v == null) {
            v = inflater.inflate(R.layout.fragment_seller_map, container, false);


            try {
                if (mMap == null) {
//                    mMap = ((MapFragment) getActivity().getFragmentManager().
//                            findFragmentById(R.id.map)).getMap();

                    if (globels.getGlobelRef().productList.get(0).sellerInfo.latitude != null &&
                            globels.getGlobelRef().productList.get(0).sellerInfo.longitude != null) {
                        testLatLng = new LatLng(Double.parseDouble(globels.getGlobelRef().productList.get(0).sellerInfo.latitude),
                                Double.parseDouble(globels.getGlobelRef().productList.get(0).sellerInfo.longitude));
                    }

                    ((MapFragment) getActivity().getFragmentManager().findFragmentById(R.id.map)).getMapAsync(this);

//                    ((SupportMapFragment) getActivity().getSupportFragmentManager().findFragmentById(
//                            R.id.map)).getMapAsync(this);
                }
                mMap.setMapType(GoogleMap.MAP_TYPE_TERRAIN);
                if (testLatLng != null) {
                    Marker testMarker = mMap.addMarker(new MarkerOptions().
                            position(testLatLng).title(""+ globels.getGlobelRef().productList.get(0).sellerInfo.companyName).
                    snippet(""+ globels.getGlobelRef().productList.get(0).sellerInfo.fName));
                    CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(testLatLng, 10);
                    mMap.animateCamera(cameraUpdate);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
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

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        View marker = ((LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.layout, null);
        // Add a marker in Sydney and move the camera
//        LatLng sydney = new LatLng(-34, 151);
//        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
//        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));

        mMap.setMapType(GoogleMap.MAP_TYPE_TERRAIN);
        Marker testMarker = mMap.addMarker(new MarkerOptions().
                position(testLatLng).title(""+ globels.getGlobelRef().productList.get(0).sellerInfo.companyName)
                .icon(BitmapDescriptorFactory.fromBitmap(createDrawableFromView(getContext(), marker)))
                .snippet(""+ globels.getGlobelRef().productList.get(0).sellerInfo.fName)
        );
        CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(testLatLng, 10);
        mMap.animateCamera(cameraUpdate);
    }
    // Convert a view to bitmap
    public static Bitmap createDrawableFromView(Context context, View view) {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        ((Activity) context).getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        view.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        view.measure(displayMetrics.widthPixels, displayMetrics.heightPixels);
        view.layout(0, 0, displayMetrics.widthPixels, displayMetrics.heightPixels);
        view.buildDrawingCache();
        Bitmap bitmap = Bitmap.createBitmap(view.getMeasuredWidth(), view.getMeasuredHeight(), Bitmap.Config.ARGB_8888);

        Canvas canvas = new Canvas(bitmap);
        view.draw(canvas);

        return bitmap;
    }

}

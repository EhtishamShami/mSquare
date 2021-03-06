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
import android.widget.TextView;

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

public class BuyerMapFragment extends Fragment implements OnMapReadyCallback {

    private GoogleMap mMap;
    static final LatLng testLatLng = new LatLng(24.433904943494827, 54.41303014755249);

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_buyer_map, container, false);

        if (mMap == null) {
            ((MapFragment) getActivity().getFragmentManager().
                    findFragmentById(R.id.map)).getMapAsync(this);
            //mMap = ((SupportMapFragment) getActivity().getSupportFragmentManager().findFragmentById(R.id.map)).getMap();
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

        mMap.setMapType(GoogleMap.MAP_TYPE_TERRAIN);

        try {
            for (int i = 0; i < globels.getGlobelRef().SellersProductDetailList.size(); i++) {
                LatLng testLatLng = new LatLng(Double.parseDouble(globels.getGlobelRef().SellersProductDetailList.get(i).latitude),
                        Double.parseDouble(globels.getGlobelRef().SellersProductDetailList.get(i).longitude));

                try {

                    View marker = ((LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.layout, null);

                    Marker testMarker = mMap.addMarker(new MarkerOptions().
                            position(testLatLng).title("" + globels.getGlobelRef().SellersProductDetailList.get(i).companyName)
                            .snippet("" + globels.getGlobelRef().SellersProductDetailList.get(i).fName)
                            .icon(BitmapDescriptorFactory.fromBitmap(createDrawableFromView(getContext(), marker)))
                    );
                    CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(testLatLng, 10);
                    mMap.animateCamera(cameraUpdate);


                    //TextView numTxt = (TextView) marker.findViewById(R.id.num_txt);
                    //numTxt.setText("27");

//                customMarker = mMap.addMarker(new MarkerOptions()
//                        .position(markerLatLng)
//                        .title("Title")
//                        .snippet("Description")
//                        .icon(BitmapDescriptorFactory.fromBitmap(createDrawableFromView(getContext(), marker))));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
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

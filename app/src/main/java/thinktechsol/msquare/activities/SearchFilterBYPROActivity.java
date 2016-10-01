package thinktechsol.msquare.activities;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.content.res.ColorStateList;
import android.os.Bundle;
import android.os.PowerManager;
import android.preference.PreferenceManager;
import android.support.v4.content.LocalBroadcastManager;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;

import java.util.ArrayList;

import thinktechsol.msquare.R;
import thinktechsol.msquare.activities.buyer.BuyerLoginActivity;
import thinktechsol.msquare.activities.buyer.SalonDetailsActivity;
import thinktechsol.msquare.adapter.AddProductAdapter2;
import thinktechsol.msquare.gcm.GCMRegistrationService;
import thinktechsol.msquare.globels.globels;
import thinktechsol.msquare.model.AddProductItem;
import thinktechsol.msquare.model.SellerProductItem;
import thinktechsol.msquare.services.SellerProductListForSearch;
import thinktechsol.msquare.utils.Constant;

public class SearchFilterBYPROActivity extends Activity {

    ImageView type_seller_btn, type_buyer_btn, app_logo;
    int btnSelectorColor;
    protected PowerManager.WakeLock mWakeLock;
    ListView listu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);


        setContentView(R.layout.searchcustomedailog);
        listu = (ListView) findViewById(R.id.listing);
        RelativeLayout bg = (RelativeLayout) findViewById(R.id.bg);
//        bg.setBackgroundColor(this.getResources().getColor(globels.getGlobelRef().them_color));

//        bg.setBackgroundColor(getResources().getColor(globels.getGlobelRef().them_color));


        new SellerProductListForSearch(this, SearchFilterBYPROActivity.this, Constant.sellerServiceId);
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

    public void getScreenSize() {
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        Constant.screenWidth = dm.widthPixels;
        Constant.screenHeight = dm.heightPixels;

        Log.e("MainActivity", "width=" + Constant.screenWidth + " height=" + Constant.screenHeight);
    }

    public int getSize(String dimension, float size) {
        float x = 0;
        if (dimension.equals("w")) {
            x = (size / 100) * Constant.screenWidth;
        } else {
            x = (size / 100) * Constant.screenWidth;
        }
        return (int) x;
    }

    public void fill_data_to_adapter(ArrayList<SellerProductItem> list) {
        ArrayList<AddProductItem> m_parts = new ArrayList<AddProductItem>();
        int colorCode[] = {R.color.cat_item1_color, R.color.cat_item2_color, R.color.cat_item3_color, R.color.cat_item4_color};

        int colorId = 0;
        for (int i = 0; i < list.size(); i++) {
            m_parts.add(new AddProductItem(list.get(i).id, list.get(i).name, Constant.imgbaseUrl + list.get(i).thumb, colorCode[colorId]));
            colorId = (colorId < 3) ? colorId += 1 : 0;
        }

        try {
            AddProductAdapter2 m_adapter = new AddProductAdapter2(SearchFilterBYPROActivity.this, SearchFilterBYPROActivity.this, R.layout.dashboard_row_item1, m_parts);
            listu.setAdapter(m_adapter);

        } catch (Exception e) {
            Log.e("SellerAddProduct", "adapter=" + e);
        }
    }


}

package thinktechsol.msquare.activities;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;

import thinktechsol.msquare.R;
import thinktechsol.msquare.adapter.HomeAdapter;
import thinktechsol.msquare.model.HomeItem;
import thinktechsol.msquare.utils.Constant;

public class HomeActivity extends Activity {

    RelativeLayout titlebarlayout;
    TextView title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_home);

        titlebarlayout = (RelativeLayout) findViewById(R.id.titlebarlayout);
        title = (TextView) findViewById(R.id.title);
        title.setText("MSquare");
        titlebarlayout.setBackgroundColor(this.getResources().getColor(R.color.sellerDashBoardTitleBg));
        titlebarlayout.setLayoutParams(AppLayoutParam(8.20f, 100f, 0, 0, 0, 0, null));

        ListView listview = (ListView) findViewById(R.id.listView);

        ArrayList<HomeItem> m_parts = new ArrayList<HomeItem>();
        m_parts.add(new HomeItem(getResources().getString(R.string.lbl_baqala), R.drawable.baqala_icon, R.color.item1Color, 35f, getResources().getString(R.string.lbl_handmade_pro), R.drawable.handmadepro_icon, R.color.item2Color));
        m_parts.add(new HomeItem(getResources().getString(R.string.lbl_cleaning), R.drawable.cleaning_icon, R.color.item3Color, 50f, getResources().getString(R.string.lbl_salon), R.drawable.salon_icon, R.color.item4Color));
        m_parts.add(new HomeItem(getResources().getString(R.string.lbl_maintence), R.drawable.maintenance_icon, R.color.item5Color, 67.5f, getResources().getString(R.string.lbl_house_made), R.drawable.housemade_icon, R.color.item6Color));
        m_parts.add(new HomeItem(getResources().getString(R.string.lbl_laundry), R.drawable.laundry_icon, R.color.item7Color, 50f, getResources().getString(R.string.lbl_cleanmycar), R.drawable.cleanmycar_icon, R.color.item8Color));

        try {
            HomeAdapter m_adapter = new HomeAdapter(HomeActivity.this, R.layout.home_row_item, m_parts);
            listview.setAdapter(m_adapter);
        } catch (Exception e) {
            Log.e("HomeActivity", "adapter=" + e);
        }

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

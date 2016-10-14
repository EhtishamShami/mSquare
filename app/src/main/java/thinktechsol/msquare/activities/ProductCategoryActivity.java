package thinktechsol.msquare.activities;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;

import thinktechsol.msquare.R;
import thinktechsol.msquare.adapter.AddProductAdapter;
import thinktechsol.msquare.globels.globels;
import thinktechsol.msquare.model.AddProductItem;
import thinktechsol.msquare.model.SellerProductItem;
import thinktechsol.msquare.services.SellerProductList;
import thinktechsol.msquare.utils.Constant;

/**
 * Created by LENOVO on 10/14/2016.
 */

public class ProductCategoryActivity extends Activity {
    TextView title;
    public static ImageView backBtn;
    RelativeLayout titlebarlayout, bottombarlayout;
    static String AddProductScreenTagbackButton = "AddProduct";
    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_product_category);

        new SellerProductList(this, ProductCategoryActivity.this, globels.getGlobelRef().sellerLoginServiceId);

        SellerDeshBoardActivity.getContext().backBtn.setTag(AddProductScreenTagbackButton);
        titlebarlayout = (RelativeLayout) findViewById(R.id.titlebar);
        title = (TextView) findViewById(R.id.title);
        backBtn = (ImageView) findViewById(R.id.backBtn);
        // title bar
        backBtn.setLayoutParams(AppLayoutParam2(10f, 10f, 0, 0, 0, 0));
//        btn_menu.setVisibility(View.VISIBLE);
//        btn_menu.setBackgroundResource(R.drawable.edit_menu);
//        btn_menu.setLayoutParams(AppLayoutParam3(8f, 12f, 0, 0, 1, 0, "right"));
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        titlebarlayout.setBackgroundColor(this.getResources().getColor(R.color.buyerHomeActivityTitleBarColor));
        titlebarlayout.setLayoutParams(AppLayoutParam(10.00f, 100f, 0, 0, 0, 0, null));
        title.setText("Product Category");
//        // title bar end


        listView = (ListView) findViewById(R.id.listView);

    }

    public RelativeLayout.LayoutParams AppLayoutParam2(float height, float width, float mL, float mT, float mR, float mB) {
        RelativeLayout.LayoutParams paramName = new RelativeLayout.LayoutParams(
                Constant.getSize("w", width),
                Constant.getSize("h", height));
        paramName.addRule(RelativeLayout.CENTER_VERTICAL, RelativeLayout.TRUE);
//        if (below != null)
//            paramName.addRule(RelativeLayout.BELOW, below.getId());
        paramName.setMargins(Constant.getSize("h", mL), Constant.getSize("h", mT), Constant.getSize("h", mR), Constant.getSize("h", mB));
        return paramName;
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

    public void fill_data_to_adapter(ArrayList<SellerProductItem> list) {
        ArrayList<AddProductItem> m_parts = new ArrayList<AddProductItem>();
        int colorCode[] = {R.color.cat_item1_color, R.color.cat_item2_color, R.color.cat_item3_color, R.color.cat_item4_color};

        int colorId = 0;
        for (int i = 0; i < list.size(); i++) {
            m_parts.add(new AddProductItem(list.get(i).id, list.get(i).name, Constant.imgbaseUrl + list.get(i).thumb, colorCode[colorId]));
            colorId = (colorId < 3) ? colorId += 1 : 0;
        }
        try {
            AddProductAdapter m_adapter = new AddProductAdapter(this, ProductCategoryActivity.this, R.layout.dashboard_row_item1, m_parts);
            listView.setAdapter(m_adapter);
        } catch (Exception e) {
            Log.e("SellerAddProduct", "adapter=" + e);
        }
    }
}

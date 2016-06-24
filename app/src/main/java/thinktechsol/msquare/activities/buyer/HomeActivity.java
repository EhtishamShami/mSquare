package thinktechsol.msquare.activities.buyer;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;

import thinktechsol.msquare.R;
import thinktechsol.msquare.activities.AddOrViewProActivity;
import thinktechsol.msquare.adapter.HomeAdapter;
import thinktechsol.msquare.adapter.ImgSwiperAdapter;
import thinktechsol.msquare.adapter.ImgSwiperAdapterBuyerAdds;
import thinktechsol.msquare.model.HomeItem;
import thinktechsol.msquare.utils.Constant;

public class HomeActivity extends Activity {

    RelativeLayout titlebarlayout;

    TextView title;
    ImageView backBtn, btn_menu;

    //viewPager items
    RelativeLayout pager_layout;
    private ViewPager viewPager;
    private LinearLayout dotsLayout;
    private ImgSwiperAdapterBuyerAdds adapter;
    private int dotsCount;
    private TextView[] dots;
    private ArrayList<String> addsImagesList;

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
        backBtn = (ImageView) findViewById(R.id.backBtn);
        btn_menu = (ImageView) findViewById(R.id.btn_menu);
        ListView listview = (ListView) findViewById(R.id.listView);


        // title bar
        backBtn.setLayoutParams(AppLayoutParam(10f, 10f, 0, 0, 0, 0, null, "ver", 0, "null"));
        btn_menu.setLayoutParams(AppLayoutParam(12f, 12f, 0, 0, 2, 0, null, "ver", 0, "right"));
        btn_menu.setVisibility(View.VISIBLE);
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        btn_menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        title.setText("MSquare");
        titlebarlayout.setBackgroundColor(this.getResources().getColor(R.color.buyerHomeActivityTitleBarColor));
        titlebarlayout.setLayoutParams(AppLayoutParam(10.00f, 100f, 0, 0, 0, 0, null, "hor", 0, "null"));
        // title bar end

        // swiper at top of the screen showing images and next image by swiping
        addsImagesList = new ArrayList<String>();
        for (int i = 1; i <= 5; i++) {
            addsImagesList.add("testimage" + i);
        }
        pager_layout = (RelativeLayout) findViewById(R.id.pager_layout);
        viewPager = (ViewPager) findViewById(R.id.pager);
        viewPager.setLayoutParams(AppLayoutParam(30.00f, 100f, 0, 0, 0, 0, titlebarlayout, "hor", 0, "null"));
        pager_layout.setLayoutParams(AppLayoutParam(30.00f, 100f, 0, 10, 0, 0, titlebarlayout, "hor", 0, "null"));
        adapter = new ImgSwiperAdapterBuyerAdds(HomeActivity.this,
                viewPager, addsImagesList);
        viewPager.setAdapter(adapter);
        viewPager.addOnPageChangeListener(viewPagerPageChangeListener);
        setAndShowDotsOnPager();


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

    private void setAndShowDotsOnPager() {
        dotsLayout = (LinearLayout) findViewById(R.id.viewPagerCountDots);
        dotsCount = adapter.getCount();

        if (dots != null) {
            for (int i = 0; i < dots.length; i++) {
                dots[i].setVisibility(View.GONE);
            }
        }
        dots = new TextView[dotsCount];

        for (int i = 0; i < dotsCount; i++) {
            dots[i] = new TextView(this);
            dots[i].setText(Html.fromHtml("&#8226;"));
            dots[i].setTextSize(30);
            dots[i].setTextColor(getResources().getColor(android.R.color.darker_gray));
            dotsLayout.addView(dots[i]);
        }
        dots[0].setTextColor(getResources().getColor(R.color.whiteColor));
    }

    // pager listner for the handling of dots showing bottom of each image
    ViewPager.OnPageChangeListener viewPagerPageChangeListener = new ViewPager.OnPageChangeListener() {

        @Override
        public void onPageSelected(int position) {
            Log.e("AddOrViewProActivity", "Scroll listener=" + dotsCount);
            for (int i = 0; i < dotsCount; i++) {
                dots[i].setTextColor(getResources().getColor(android.R.color.darker_gray));
            }
            dots[position].setTextColor(getResources().getColor(R.color.whiteColor));
        }

        @Override
        public void onPageScrolled(int arg0, float arg1, int arg2) {
        }

        @Override
        public void onPageScrollStateChanged(int arg0) {
        }
    };

    /*
    *
    * center represent CENTER_HORIZONTAL(hor) or CENTER_VERHTICAL(ver)
    * below represent that either this view be below to some other view or not, if not then pass null instead
    *
    * */
    public RelativeLayout.LayoutParams AppLayoutParam(float height, float width, float mL, float mT, float mR, float mB, View below, String center, int toRightOf, String align) {
        RelativeLayout.LayoutParams paramName = new RelativeLayout.LayoutParams(
                getSize("w", width),
                getSize("h", height));


        if (center.equals("hor"))
            paramName.addRule(RelativeLayout.CENTER_HORIZONTAL, RelativeLayout.TRUE);
        else
            paramName.addRule(RelativeLayout.CENTER_VERTICAL, RelativeLayout.TRUE);

        if (toRightOf != 0)
            paramName.addRule(RelativeLayout.RIGHT_OF, toRightOf);

        if (align != "null") {
            if (align.equals("left"))
                paramName.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
            else
                paramName.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
        }

        if (below != null) {
            Log.e("HomeActivity", "belowing is=" + below.getId());
            paramName.addRule(RelativeLayout.BELOW, below.getId());
        }
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

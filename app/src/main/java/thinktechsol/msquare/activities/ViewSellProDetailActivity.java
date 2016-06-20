package thinktechsol.msquare.activities;

import android.app.Activity;
import android.media.Rating;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;

import thinktechsol.msquare.R;
import thinktechsol.msquare.adapter.ImgSwiperAdapterProDetail;
import thinktechsol.msquare.model.Response.ProductImages;
import thinktechsol.msquare.utils.Constant;

public class ViewSellProDetailActivity extends Activity {

    RelativeLayout titlebarlayout, bottombarlayout, pro_name_rating_price_layout, pro_name_rating_layout, pro_price_layout;
    private ViewPager viewPager;
    private ImgSwiperAdapterProDetail adapter;
    private LinearLayout dotsLayout;
    private int dotsCount;
    private TextView[] dots;
    static TextView title;
    public static ImageView backBtn;
    ArrayList<String> selectedImagePath;
    RatingBar rating;


    ArrayList<ProductImages> productImagesList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_sell_pro_detail);

//        selectedImagePath = new ArrayList<String>();

//        list of images of the seller products
        productImagesList = Constant.productImagesList;

        RelativeLayout imgs = (RelativeLayout) findViewById(R.id.imgs);
        bottombarlayout = (RelativeLayout) findViewById(R.id.bottombarlayout);
        titlebarlayout = (RelativeLayout) findViewById(R.id.titlebar);
        pro_name_rating_price_layout = (RelativeLayout) findViewById(R.id.pro_name_rating_price_layout);
        pro_name_rating_layout = (RelativeLayout) findViewById(R.id.pro_name_rating_layout);
        pro_price_layout = (RelativeLayout) findViewById(R.id.pro_price_layout);
        rating = (RatingBar) findViewById(R.id.rating);

        titlebarlayout.setBackgroundColor(this.getResources().getColor(R.color.addProductTitleBarColor));

        title = (TextView) findViewById(R.id.title);
        title.setText("Product Details");
        backBtn = (ImageView) findViewById(R.id.backBtn);
        backBtn.setLayoutParams(AppLayoutParam2(10f, 10f, 0, 0, 0, 0));
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        viewPager = (ViewPager) findViewById(R.id.pager);
        viewPager.setLayoutParams(AppLayoutParam(30.00f, 100f, 0, 0, 0, 0, null));
        imgs.setLayoutParams(AppLayoutParam(30.00f, 100f, 0, 0, 0, 0, null));
        adapter = new ImgSwiperAdapterProDetail(ViewSellProDetailActivity.this,
                viewPager, productImagesList);
        viewPager.setAdapter(adapter);
        viewPager.addOnPageChangeListener(viewPagerPageChangeListener);
        setAndShowDotsOnPager();

        pro_name_rating_price_layout.setLayoutParams(AppLayoutParam(10.00f, 100f, 0, 0, 0, 0, imgs));
        pro_name_rating_layout.setLayoutParams(AppLayoutParam3(10.00f, 70f, 0, 0, 0, 0, null,0));
        pro_price_layout.setLayoutParams(AppLayoutParam3(10.00f, 30f, 0, 0, 0, 0, null,R.id.pro_name_rating_layout));
//        rating.setLayoutParams(AppLayoutParam3(5.00f, 20f, 0, 0, 0, 0, null));

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

    ViewPager.OnPageChangeListener viewPagerPageChangeListener = new ViewPager.OnPageChangeListener() {

        @Override
        public void onPageSelected(int position) {
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

    public RelativeLayout.LayoutParams AppLayoutParam2(float height, float width, float mL, float mT, float mR, float mB) {
        RelativeLayout.LayoutParams paramName = new RelativeLayout.LayoutParams(
                Constant.getSize("w", width),
                Constant.getSize("h", height));
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

    public RelativeLayout.LayoutParams AppLayoutParam3(float height, float width, float mL, float mT, float mR, float mB, View below,int toRightView) {
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

package thinktechsol.msquare.activities.buyer;


import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import thinktechsol.msquare.R;
import thinktechsol.msquare.fragments.Buyer.SellersAboutFragment;
import thinktechsol.msquare.fragments.Buyer.SellersMapFragment;
import thinktechsol.msquare.fragments.Buyer.SellersServiceFragment;
import thinktechsol.msquare.globels.globels;
import thinktechsol.msquare.model.Buyer.getServiceSellersProductModel;
import thinktechsol.msquare.services.buyer.getServiceSellersProduct;
import thinktechsol.msquare.utils.Constant;

public class ServiceSellerDetailActivity extends FragmentActivity {

    RelativeLayout titlebarlayout;
    TextView title;
    ImageView backBtn, btn_menu;


    private ViewPager viewPager;
    private TabLayout tabLayout;

    ImageView sellersTitleImg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_service_seller_detail);

        new getServiceSellersProduct(this, ServiceSellerDetailActivity.this, Constant.sellerServiceId, globels.getGlobelRef().serviceSellerId, "24.433904943494827", "54.41303014755249");

        titlebarlayout = (RelativeLayout) findViewById(R.id.titlebarlayout);
        title = (TextView) findViewById(R.id.title);
        backBtn = (ImageView) findViewById(R.id.backBtn);
        btn_menu = (ImageView) findViewById(R.id.btn_menu);

        // title bar
        backBtn.setLayoutParams(AppLayoutParam(10f, 10f, 0, 0, 0, 0, null, "ver", 0, "null"));
        btn_menu.setLayoutParams(AppLayoutParam(12f, 8f, 0, 0, 2, 0, null, "ver", 0, "right"));
//        btn_menu.setVisibility(View.VISIBLE);
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
        title.setText("Services");
        titlebarlayout.setBackgroundColor(this.getResources().getColor(globels.getGlobelRef().them_color));
        titlebarlayout.setLayoutParams(AppLayoutParam(10.00f, 100f, 0, 0, 0, 0, null, "hor", 0, "null"));
        // title bar end

        //top image
        sellersTitleImg = (ImageView) findViewById(R.id.img);
        sellersTitleImg.setLayoutParams(AppLayoutParam(24.00f, 100f, 0, 0, 0, 0, null, "hor", 0, "null"));

//        viewPager = (ViewPager) findViewById(R.id.viewpager);
//        setupViewPager(viewPager);
//
//        tabLayout = (TabLayout) findViewById(R.id.tabs);
//        tabLayout.setupWithViewPager(viewPager);
//        setupTabIcons();


    }

    private void setupViewPager(ViewPager viewPager) {
        ServiceSellerDetailActivity.ViewPagerAdapter adapter = new ServiceSellerDetailActivity.ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFrag(new SellersServiceFragment(), getResources().getString(R.string.lbl_services));
        adapter.addFrag(new SellersAboutFragment(), getResources().getString(R.string.lbl_about));
        adapter.addFrag(new SellersMapFragment(), getResources().getString(R.string.lbl_loctation));
        viewPager.setAdapter(adapter);
    }

    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFrag(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }


    private void setupTabIcons() {


        final TextView Services = (TextView) LayoutInflater.from(this).inflate(R.layout.custom_tab2, null);
        Services.setText(getResources().getString(R.string.lbl_services));
        Services.setTextColor(ServiceSellerDetailActivity.this.getResources().getColor(R.color.selected_tab_text_color));
        tabLayout.getTabAt(0).setCustomView(Services);


        final TextView About = (TextView) LayoutInflater.from(this).inflate(R.layout.custom_tab2, null);
        About.setText(getResources().getString(R.string.lbl_about));
        tabLayout.getTabAt(1).setCustomView(About);

        final TextView Location = (TextView) LayoutInflater.from(this).inflate(R.layout.custom_tab2, null);
        Location.setText(getResources().getString(R.string.lbl_loctation));
        tabLayout.getTabAt(2).setCustomView(Location);

        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                int position = tab.getPosition();

                viewPager.setCurrentItem(position);


                if (position == 0) {
                    title.setText("Services");
                    Services.setTextColor(ServiceSellerDetailActivity.this.getResources().getColor(R.color.selected_tab_text_color));
                } else if (position == 1) {
                    title.setText("About");
                    About.setTextColor(ServiceSellerDetailActivity.this.getResources().getColor(R.color.selected_tab_text_color));
                } else {
                    title.setText("Location");
                    Location.setTextColor(ServiceSellerDetailActivity.this.getResources().getColor(R.color.selected_tab_text_color));
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                int position = tab.getPosition();

                viewPager.setCurrentItem(position);

                if (position == 0) {
                    Services.setTextColor(ServiceSellerDetailActivity.this.getResources().getColor(R.color.unselected_tab_text_color));
                } else if (position == 1) {
                    About.setTextColor(ServiceSellerDetailActivity.this.getResources().getColor(R.color.unselected_tab_text_color));
                } else {
                    Location.setTextColor(ServiceSellerDetailActivity.this.getResources().getColor(R.color.unselected_tab_text_color));
                }
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            }
        });


//        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
//            @Override
//            public void onTabSelected(TabLayout.Tab tab) {
//                viewPager.setCurrentItem(tab.getPosition());
//
//                if (tab.getPosition() == 0) {
//
////                    view1.findViewById(R.id.icon).setBackgroundResource(R.drawable.buyer_map_active);
////                    tabLayout.getTabAt(0).setCustomView(view1);
////
////                    view2.findViewById(R.id.icon).setBackgroundResource(R.drawable.buyer_list_normal);
////                    tabLayout.getTabAt(1).setCustomView(view2);
//
//                } else if (tab.getPosition() == 1) {
//
////                    view1.findViewById(R.id.icon).setBackgroundResource(R.drawable.buyer_map_normal);
////                    tabLayout.getTabAt(0).setCustomView(view1);
////
////                    view2.findViewById(R.id.icon).setBackgroundResource(R.drawable.buyer_list_active);
////                    tabLayout.getTabAt(1).setCustomView(view2);
//
//                } else if (tab.getPosition() == 2) {
//
////                    view1.findViewById(R.id.icon).setBackgroundResource(R.drawable.buyer_map_normal);
////                    tabLayout.getTabAt(0).setCustomView(view1);
////
////                    view2.findViewById(R.id.icon).setBackgroundResource(R.drawable.buyer_list_active);
////                    tabLayout.getTabAt(1).setCustomView(view2);
//
//                }
//            }
//
//            @Override
//            public void onTabUnselected(TabLayout.Tab tab) {
//            }
//
//            @Override
//            public void onTabReselected(TabLayout.Tab tab) {
//            }
//        });
    }

//    public ArrayList<getServiceSellersProductModel> SellersProductDetailList;

    public void fillProductListWithData(ArrayList<getServiceSellersProductModel> list) {
//        SellersProductDetailList = new ArrayList<getServiceSellersProductModel>();
//        SellersProductDetailList = list;
        Log.e("ServiceSellerDetial", "list value 1=" + list.size());

        globels.getGlobelRef().productList = list;

        if (list.get(0).products != null)
            globels.getGlobelRef().productList2 = list.get(0).products;

//        Log.e("ServiceSellerDetial", "list value img=" + Constant.imgbaseUrl + globels.getGlobelRef().SellersProductDetailList.get(0).sellerDetails.logo);
        Picasso.with(this).load(Constant.imgbaseUrl + globels.getGlobelRef().productList.get(0).sellerInfo.logo).into(sellersTitleImg);
//        adapter = new BuyerServiceSellersProductListAdapter(getActivity(), R.layout.buyer_service_seller_list_item, list);
//        listView.setAdapter(adapter);

        viewPager = (ViewPager) findViewById(R.id.viewpager);
        setupViewPager(viewPager);

        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);
        setupTabIcons();
    }

    public void onBackPressed() {
        //moveTaskToBack(true);
    }

    /*
    *
    * center represent CENTER_HORIZONTAL(hor) or CENTER_VERHTICAL(ver)
    * below represent that either this view be below to some other view or not, if not then pass null instead
    *
    * */
    public RelativeLayout.LayoutParams AppLayoutParam(float height, float width, float mL, float mT, float mR, float mB, View below, String center, int toRightOf, String align) {
        RelativeLayout.LayoutParams paramName = new RelativeLayout.LayoutParams(
                Constant.getSize("w", width),
                Constant.getSize("h", height));


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
        paramName.setMargins(Constant.getSize("h", mL), Constant.getSize("h", mT), Constant.getSize("h", mR), Constant.getSize("h", mB));
        return paramName;
    }
}

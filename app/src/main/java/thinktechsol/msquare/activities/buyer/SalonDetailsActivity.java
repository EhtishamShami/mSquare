package thinktechsol.msquare.activities.buyer;


import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;


import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.SearchView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import thinktechsol.msquare.R;
import thinktechsol.msquare.fragments.Buyer.BuyerServiceSellersList;
import thinktechsol.msquare.fragments.Buyer.BuyerMapFragment;
import thinktechsol.msquare.globels.globels;
import thinktechsol.msquare.interfaceMine.SearchViewInterface;
import thinktechsol.msquare.model.Buyer.FiltersModel;
import thinktechsol.msquare.model.Buyer.getServiceSellersModel;
import thinktechsol.msquare.services.buyer.GetServiceSellersSearch;
import thinktechsol.msquare.services.getServiceSellers;
import thinktechsol.msquare.utils.Constant;

public class SalonDetailsActivity extends FragmentActivity {

    RelativeLayout titlebarlayout;
    TextView title;
    ImageView backBtn, btn_menu;


    private ViewPager viewPager;
    private TabLayout tabLayout;

    SearchView searchView;
    SearchViewInterface searchViewInterface;
    BuyerServiceSellersList buyerServiceSellersListFragment;
    BuyerMapFragment buyerServiceSellersMapFragment;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_main3);

        new getServiceSellers(this, SalonDetailsActivity.this, Constant.sellerServiceId, "24.433904943494827", "54.41303014755249");

        buyerServiceSellersListFragment = new BuyerServiceSellersList();
        buyerServiceSellersMapFragment=new BuyerMapFragment();

        titlebarlayout = (RelativeLayout) findViewById(R.id.titlebarlayout);
        title = (TextView) findViewById(R.id.title);
        backBtn = (ImageView) findViewById(R.id.backBtn);
        btn_menu = (ImageView) findViewById(R.id.btn_menu);
        searchView = (SearchView) findViewById(R.id.searchView);
        searchView.setVisibility(View.VISIBLE);
        btn_menu.setVisibility(View.VISIBLE);
        title.setVisibility(View.GONE);

        FloatingActionButton filterBtn = (FloatingActionButton) findViewById(R.id.filterBtn);

        btn_menu.setBackgroundResource(R.drawable.btn_menu);

        // title bar
        backBtn.setLayoutParams(AppLayoutParam(10f, 10f, 0, 0, 0, 0, null, "ver", 0, "null"));

        btn_menu.setLayoutParams(AppLayoutParam(6f, 8f, 0, 0, 2, 0, null, "ver", 0, "right"));
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
        title.setText("Saloon");
        titlebarlayout.setBackgroundColor(this.getResources().getColor(R.color.sellerOrderDetailTitleBg));
        titlebarlayout.setLayoutParams(AppLayoutParam(10.00f, 100f, 0, 0, 0, 0, null, "hor", 0, "null"));
        // title bar end

        searchView.setQueryHint("Type your text here");
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }

            @Override
            public boolean onQueryTextSubmit(String query) {
                searchView.clearFocus();

                new GetServiceSellersSearch(SalonDetailsActivity.this, SalonDetailsActivity.this, Constant.sellerServiceId, "24.433904943494827", "54.41303014755249", query, "", "", "", "", "", "");

                return false;
            }
        });

        filterBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SalonDetailsActivity.this, BuyerFilterActivity.class));
            }
        });


    }


    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFrag(buyerServiceSellersMapFragment, ""/*getResources().getString(R.string.lbl_tab1_text)*/);
        adapter.addFrag(buyerServiceSellersListFragment, ""/* getResources().getString(R.string.lbl_tab2_text)*/);
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

    int[] tabIcons = {
            R.drawable.buyer_map_active,
            R.drawable.buyer_list_normal,
            R.drawable.buyer_map_normal,
            R.drawable.buyer_list_active
    };
    View view1, view2;

    private void setupTabIcons() {

        view1 = getLayoutInflater().inflate(R.layout.custom_tab, null);
        view1.findViewById(R.id.icon).setBackgroundResource(R.drawable.buyer_map_active);

        view2 = getLayoutInflater().inflate(R.layout.custom_tab, null);
        view2.findViewById(R.id.icon).setBackgroundResource(R.drawable.buyer_list_normal);
        tabLayout.getTabAt(0).setCustomView(view1);
        tabLayout.getTabAt(1).setCustomView(view2);


        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());

                if (tab.getPosition() == 0) {


                    view1.findViewById(R.id.icon).setBackgroundResource(R.drawable.buyer_map_active);
                    tabLayout.getTabAt(0).setCustomView(view1);

                    view2.findViewById(R.id.icon).setBackgroundResource(R.drawable.buyer_list_normal);
                    tabLayout.getTabAt(1).setCustomView(view2);

                } else if (tab.getPosition() == 1) {

                    view1.findViewById(R.id.icon).setBackgroundResource(R.drawable.buyer_map_normal);
                    tabLayout.getTabAt(0).setCustomView(view1);

                    view2.findViewById(R.id.icon).setBackgroundResource(R.drawable.buyer_list_active);
                    tabLayout.getTabAt(1).setCustomView(view2);

                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            }
        });
    }

    public void onBackPressed() {
        moveTaskToBack(true);
    }

    ArrayList<getServiceSellersModel> SellersProductDetailList;

    public void fillProductListWithData(ArrayList<getServiceSellersModel> list) {
        SellersProductDetailList = new ArrayList<getServiceSellersModel>();

        SellersProductDetailList = list;
        Log.e("BuyerServiceSellersList", "list size is 1=" + list.size());
//        for (int i = 0; i < list.size(); i++) {
//            SellersProductDetailList.add(new getServiceSellersModel(list.get(i).mobileNo, list.get(i).logo, list.get(i).status, list.get(i).tradeNo, list.get(i).documents, list.get(i).lName, list.get(i).name, list.get(i).password, list.get(i).fName, list.get(i).productRating, list.get(i).id, list.get(i).phoneNo, list.get(i).distance, list.get(i).email, list.get(i).address, list.get(i).description, list.get(i).activationCode, list.get(i).service, list.get(i).longitude, list.get(i).latitude, list.get(i).datetime));
//        }

        globels.getGlobelRef().SellersProductDetailList = SellersProductDetailList;

        Log.e("BuyerServiceSellersList", "list size is 2=" + SellersProductDetailList.size());
//        adapter = new BuyerServiceSellersListAdapter(getActivity(), R.layout.buyer_service_seller_list_item, SellersProductDetailList);
//        listView.setAdapter(adapter);

        viewPager = (ViewPager) findViewById(R.id.viewpager);
        setupViewPager(viewPager);

        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);
        viewPager.setCurrentItem(1);
        setupTabIcons();
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

    public void searchResult(ArrayList<getServiceSellersModel> list) {
        searchViewInterface = buyerServiceSellersListFragment;
        searchViewInterface.refersh(list);
    }

    @Override
    public void onResume(){
        super.onResume();
        if(globels.getGlobelRef().filteration) {
            globels.getGlobelRef().filteration=false;
            FiltersModel obj = globels.getGlobelRef().filterdDateObj;
            new GetServiceSellersSearch(SalonDetailsActivity.this, SalonDetailsActivity.this, Constant.sellerServiceId, "24.433904943494827", "54.41303014755249", "", obj.distance, obj.priceFrom, obj.priceTo, obj.fromTime, obj.toTime, obj.categories);
        }
    }
}

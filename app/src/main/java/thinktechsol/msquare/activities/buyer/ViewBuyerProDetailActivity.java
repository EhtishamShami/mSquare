package thinktechsol.msquare.activities.buyer;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.LayerDrawable;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;

import thinktechsol.msquare.R;
import thinktechsol.msquare.adapter.BuyerProReviewsAdapter;
import thinktechsol.msquare.adapter.ImgSwiperAdapterProDetailBuyer;
import thinktechsol.msquare.globels.globels;
import thinktechsol.msquare.model.Buyer.AddToWishListRequestModel;
import thinktechsol.msquare.model.Buyer.BuyerProReviewsModel;
import thinktechsol.msquare.model.Buyer.ProductImages;
import thinktechsol.msquare.model.Buyer.Products;
import thinktechsol.msquare.services.buyer.AddToWishListBuyerService;
import thinktechsol.msquare.utils.Constant;

public class ViewBuyerProDetailActivity extends Activity {

    RelativeLayout titlebarlayout, bottombarlayout, pro_name_rating_price_layout, pro_reviews_layout, pro_name_rating_layout, pro_price_layout;
    private ViewPager viewPager;
    private ImgSwiperAdapterProDetailBuyer adapter;
    private LinearLayout dotsLayout;
    private int dotsCount;
    private TextView[] dots;
    static TextView title;
    public static ImageView backBtn, btn_menu;
    ArrayList<String> selectedImagePath;
    RatingBar rating;
    ImageView add_product, view_product;
    TextView pro_name, pro_price, pro_description;
    Products selectedProduct;
    ArrayList<ProductImages> productImagesList;

    Button btnAddToFavorite;
    ListView pro_reviews_list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_buyer_pro_detail);

//        seller product single
        //getSellerProductsResponse singleProduct = Constant.singleProduct;
//        list of images of the seller products


//        globels.getGlobelRef().productList.get(0)
        selectedProduct = globels.getGlobelRef().singleProductForBuyProDetail;
        productImagesList = selectedProduct.productImages;

        Log.e("ViewBuyerProDet", "buyer pro rr review size is=" + selectedProduct.productReviews.size());
        Log.e("ViewBuyerProDet", "buyer pro rr img size is=" + selectedProduct.productImages.size());


        RelativeLayout imgs = (RelativeLayout) findViewById(R.id.imgs);
        bottombarlayout = (RelativeLayout) findViewById(R.id.bottombarlayout);
        titlebarlayout = (RelativeLayout) findViewById(R.id.titlebar);
        pro_name_rating_price_layout = (RelativeLayout) findViewById(R.id.sellers_detials_layout);
        pro_reviews_layout = (RelativeLayout) findViewById(R.id.pro_reviews_layout);
        pro_name_rating_layout = (RelativeLayout) findViewById(R.id.sellers_title_layout);
        pro_price_layout = (RelativeLayout) findViewById(R.id.rating_layout);
        pro_name = (TextView) findViewById(R.id.sellers_title);
        pro_price = (TextView) findViewById(R.id.proPrice);
        pro_description = (TextView) findViewById(R.id.pro_description);
        rating = (RatingBar) findViewById(R.id.rating);
//        btnAddToFavorite = (Button) findViewById(R.id.btnAddToFavorite);
        pro_reviews_list = (ListView) findViewById(R.id.pro_reviews_list);

        titlebarlayout.setBackgroundColor(this.getResources().getColor(R.color.buyerHomeActivityTitleBarColor));


        title = (TextView) findViewById(R.id.title);
        title.setText("Product Details");
        backBtn = (ImageView) findViewById(R.id.backBtn);
        btn_menu = (ImageView) findViewById(R.id.btn_menu);
        btn_menu.setVisibility(View.VISIBLE);
        btn_menu.setBackgroundResource(R.drawable.heart_img);
        btn_menu.setLayoutParams(AppLayoutParam2(6f, 10f, 0, 0, 1, 0, "right"));
        backBtn.setLayoutParams(AppLayoutParam2(10f, 10f, 0, 0, 0, 0, "left"));
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        btn_menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("ViewBuyerProDet", "buyer id for adding to wishlist is=" + globels.getGlobelRef().buyerLoginId);

                AddToWishListRequestModel requestModel = new AddToWishListRequestModel(selectedProduct.sellerId, selectedProduct.serviceId, selectedProduct.id, globels.getGlobelRef().buyerLoginId  /*buyerId*/);
                new AddToWishListBuyerService(ViewBuyerProDetailActivity.this, ViewBuyerProDetailActivity.this, requestModel);

            }
        });

        viewPager = (ViewPager) findViewById(R.id.pager);
        viewPager.setLayoutParams(AppLayoutParam(30.00f, 100f, 0, 0, 0, 0, null));
        imgs.setLayoutParams(AppLayoutParam(30.00f, 100f, 0, 0, 0, 0, null));

        adapter = new ImgSwiperAdapterProDetailBuyer(ViewBuyerProDetailActivity.this,
                viewPager, productImagesList);
        viewPager.setAdapter(adapter);
        viewPager.addOnPageChangeListener(viewPagerPageChangeListener);
        setAndShowDotsOnPager();

        pro_name_rating_price_layout.setLayoutParams(AppLayoutParam(10.00f, 100f, 0, 0, 0, 0, imgs));

        pro_name_rating_layout.setLayoutParams(AppLayoutParam3(10.00f, 70f, 0, 0, 0, 0, null, 0));
        pro_price_layout.setLayoutParams(AppLayoutParam3(10.00f, 30f, 0, 0, 0, 0, null, R.id.sellers_title_layout));

        pro_reviews_layout.setLayoutParams(AppLayoutParam(60.0f, 100f, 0, 0, 0, 0, pro_name_rating_price_layout));


//        globels.getGlobelRef().singleProductForBuyProDetail
        pro_name.setText(selectedProduct.title);
        pro_price.setText(selectedProduct.price + "AED");
        pro_description.setText(selectedProduct.description);
//        Log.e("ViewSellPro", "rating=" + singleProduct.productRating);
        LayerDrawable stars = (LayerDrawable) rating.getProgressDrawable();
        stars.getDrawable(2).setColorFilter(getResources().getColor(R.color.rating_color), PorterDuff.Mode.SRC_ATOP);
        stars.getDrawable(0).setColorFilter(Color.parseColor("#d5d5d5"), PorterDuff.Mode.SRC_ATOP);
        stars.getDrawable(1).setColorFilter(Color.YELLOW, PorterDuff.Mode.SRC_ATOP);
        Log.e("ViewBuyerPro", "rating pro detail=" + selectedProduct.productRating);
        if (!selectedProduct.productRating.equals("not available")) {
            float ratingNum = Float.parseFloat(selectedProduct.productRating);
            Log.e("ViewSellPro", "rating 2=" + (int) ratingNum);
            rating.setRating(1);
            rating.setRating((int) ratingNum);
        }
//        btnAddToFavorite.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Log.e("ViewBuyerProDet", "buyer id for adding to wishlist is=" + globels.getGlobelRef().buyerLoginId);
//
//                AddToWishListRequestModel requestModel = new AddToWishListRequestModel(selectedProduct.sellerId, selectedProduct.serviceId, selectedProduct.id, globels.getGlobelRef().buyerLoginId  /*buyerId*/);
//                new AddToWishListBuyerService(ViewBuyerProDetailActivity.this, ViewBuyerProDetailActivity.this, requestModel);
//            }
//        });

        ArrayList<BuyerProReviewsModel> reviews_list = new ArrayList<BuyerProReviewsModel>();
        reviews_list.clear();
        reviews_list.add(new BuyerProReviewsModel("", selectedProduct.description, false));

        for (int i = 0; i < selectedProduct.productReviews.size(); i++) {
            reviews_list.add(new BuyerProReviewsModel(selectedProduct.productReviews.get(i).reviewTitle, selectedProduct.productReviews.get(i).reviewDescription, true));
        }
//        reviews_list.add(new BuyerProReviewsModel("Title1", "Yasir Ahmed"));
//        reviews_list.add(new BuyerProReviewsModel("Title2", "Bilal"));
//        reviews_list.add(new BuyerProReviewsModel("Title3", "Zain u din"));
//        reviews_list.add(new BuyerProReviewsModel("Title4", "Maroof"));


        BuyerProReviewsAdapter adapter = new BuyerProReviewsAdapter(this, ViewBuyerProDetailActivity.this, R.layout.buyer_pro_review_list_item, reviews_list);
        pro_reviews_list.setAdapter(adapter);


        pro_name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //addReviewOfBuyer();
            }
        });
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

    public RelativeLayout.LayoutParams AppLayoutParam2(float height, float width, float mL, float mT, float mR, float mB, String leftOrRight) {
        RelativeLayout.LayoutParams paramName = new RelativeLayout.LayoutParams(
                Constant.getSize("w", width),
                Constant.getSize("h", height));
        if (leftOrRight.equals("right"))
            paramName.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
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

    public RelativeLayout.LayoutParams AppLayoutParam3(float height, float width, float mL, float mT, float mR, float mB, View below, int toRightView) {
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

//    public void addReviewOfBuyer() {
//        final Dialog addReviewDialog = new Dialog(ViewBuyerProDetailActivity.this);
//        addReviewDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
//        addReviewDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
//        addReviewDialog.setCancelable(false);
//
//        LayoutInflater inflater = (LayoutInflater) getLayoutInflater();
//        View customView = inflater.inflate(R.layout.dialog_add_review_buyer, null);
//
//        addReviewDialog.setContentView(customView);
//
//        int width = (int) (getResources().getDisplayMetrics().widthPixels * 0.95);
//        int height = (int) (getResources().getDisplayMetrics().heightPixels * 0.6);
//        addReviewDialog.getWindow().setLayout(width, height);
//
//        final ImageView cross_btn_review = (ImageView) addReviewDialog.findViewById(R.id.cross_btn_review);
//        final EditText title_et = (EditText) addReviewDialog.findViewById(R.id.title_et);
//        final EditText description_et = (EditText) addReviewDialog.findViewById(R.id.description_et);
//
//        cross_btn_review.bringToFront();
//
//        Button OkBtn = (Button) addReviewDialog.findViewById(R.id.savebtn);
//        OkBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                addReviewDialog.dismiss();
////                new BuyerAddReviewService(ViewBuyerProDetailActivity.this, globels.getGlobelRef().productList.get(0).sellerInfo.id, globels.getGlobelRef().buyerLoginId,
////                        globels.getGlobelRef().productList.get(0).products.get(0).serviceId, globels.getGlobelRef().productList.get(0).products.get(0).id,
////                        title_et.getText().toString().trim(), description_et.getText().toString().trim());
//
//                new BuyerAddReviewService(ViewBuyerProDetailActivity.this, globels.getGlobelRef().productList.get(0).sellerInfo.id, globels.getGlobelRef().buyerLoginId,
//                        selectedProduct.serviceId, selectedProduct.id,
//                        title_et.getText().toString().trim(), description_et.getText().toString().trim());
//            }
//        });
//
//
//        cross_btn_review.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                addReviewDialog.dismiss();
//            }
//        });
//
//        addReviewDialog.show();
//    }
}

package thinktechsol.msquare.activities;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.LayerDrawable;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import thinktechsol.msquare.R;
import thinktechsol.msquare.adapter.ConversationAdapter;
import thinktechsol.msquare.adapter.ImgSwiperAdapterOrderDetail;
import thinktechsol.msquare.globels.globels;
import thinktechsol.msquare.model.OrderDetails.GetOrderDetails;
import thinktechsol.msquare.model.OrderDetails.ProductImagesOrd;
import thinktechsol.msquare.model.Response.ConversationItem;
import thinktechsol.msquare.model.Response.ProductImages;
import thinktechsol.msquare.model.Response.getSellerProductsResponse;
import thinktechsol.msquare.services.GetOrderDetailsService;
import thinktechsol.msquare.utils.Constant;

public class ViewSellOrderDetailActivity extends Activity {

    public static final String ADD_PRODUCT = "addproduct";
    public static final String VIEW_PRODUCT = "viewproduct";
    RelativeLayout titlebarlayout, bottombarlayout, pro_detail_layout1, pro_name_layout, pro_price_quant_layout, pro_name_rating_price_layout, pro_name_rating_layout, pro_price_layout, typing_layout;
    private ViewPager viewPager;
    private ImgSwiperAdapterOrderDetail adapter;
    private LinearLayout dotsLayout;
    private int dotsCount;
    private TextView[] dots;
    static TextView title;
    public static ImageView backBtn;
    ArrayList<String> selectedImagePath;
    RatingBar rating;
    //    ImageView add_product, view_product;
    TextView pro_name, pro_pricee, sendMessageBtn, pro_price, pro_quantity, sellers_title, sellers_description;
    ListView conversation_list;
    ConversationAdapter conversationAdapter;
    ArrayList<ProductImagesOrd> productImagesList;
    EditText messageET;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_sell_order_detail);

//        seller product single
        getSellerProductsResponse singleProduct = Constant.singleProduct;


        new GetOrderDetailsService(ViewSellOrderDetailActivity.this, ViewSellOrderDetailActivity.this, globels.getGlobelRef().orderId_for_ordr_info);

//        list of images of the seller products
//        productImagesList = Constant.productImagesList;
        productImagesList = new ArrayList<ProductImagesOrd>();

//        add_product = (ImageView) findViewById(R.id.add_product);
//        view_product = (ImageView) findViewById(R.id.view_product);
//
//        add_product.setBackgroundResource(R.drawable.add_product_normal);
//        view_product.setBackgroundResource(R.drawable.view_product_sel);

        RelativeLayout imgs = (RelativeLayout) findViewById(R.id.imgs);
        bottombarlayout = (RelativeLayout) findViewById(R.id.bottombarlayout);
        titlebarlayout = (RelativeLayout) findViewById(R.id.titlebar);
        pro_detail_layout1 = (RelativeLayout) findViewById(R.id.pro_detail_layout1);

        pro_name_rating_price_layout = (RelativeLayout) findViewById(R.id.sellers_detials_layout);
        pro_name_rating_layout = (RelativeLayout) findViewById(R.id.sellers_title_layout);
        pro_price_layout = (RelativeLayout) findViewById(R.id.rating_layout);
        typing_layout = (RelativeLayout) findViewById(R.id.typing_layout);
        pro_name = (TextView) findViewById(R.id.sellers_title);

        messageET = (EditText) findViewById(R.id.messageET);

        sendMessageBtn = (TextView) findViewById(R.id.sendMessageBtn);


        pro_name = (TextView) findViewById(R.id.pro_name);
        pro_price = (TextView) findViewById(R.id.pro_price);
        pro_quantity = (TextView) findViewById(R.id.pro_quantity);

        sellers_title = (TextView) findViewById(R.id.sellers_title);
        sellers_description = (TextView) findViewById(R.id.sellers_description);
        rating = (RatingBar) findViewById(R.id.rating);

        conversation_list = (ListView) findViewById(R.id.conversation_list);

        titlebarlayout.setBackgroundColor(this.getResources().getColor(R.color.sellerOrderDetailTitleBg));


        title = (TextView) findViewById(R.id.title);
        title.setText("Order Details");
        backBtn = (ImageView) findViewById(R.id.backBtn);
        backBtn.setLayoutParams(AppLayoutParam2(10f, 10f, 0, 0, 0, 0));
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Constant.addOrViewProduct = false;
                finish();
            }
        });

        viewPager = (ViewPager) findViewById(R.id.pager);
        viewPager.setLayoutParams(AppLayoutParam(30.00f, 100f, 0, 0, 0, 0, null));
        imgs.setLayoutParams(AppLayoutParam(25.00f, 100f, 0, 0, 0, 0, null));


//        messageList = new ArrayList<ConversationItem>();
//        for (int i = 0; i < 3; i++) {
//            ConversationItem item = new ConversationItem("test" + i, "test" + i, "test" + i, "test" + i);
//            messageList.add(item);
//        }
//
//        conversationAdapter = new ConversationAdapter(ViewSellOrderDetailActivity.this,
//                ViewSellOrderDetailActivity.this, R.layout.my_messages_layout, messageList);
//        conversation_list.setAdapter(conversationAdapter);
        sendMessageBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (sendMessageBtn.getText().length() > 0) {
                    Toast.makeText(ViewSellOrderDetailActivity.this, "Message Sent", Toast.LENGTH_SHORT).show();
                    String newMessage = messageET.getText().toString();
                    sendNewMessage(newMessage);
                    messageET.setText("");
                    messageET.clearFocus();
                    makeKeyboardInvisible();
                } else {
                    Toast.makeText(ViewSellOrderDetailActivity.this, "Please type something", Toast.LENGTH_SHORT).show();
                }
            }
        });

//        ProductImagesOrd obj = new ProductImagesOrd("1", "1", "1");
//        productImagesList.add(obj);
//
//        adapter = new ImgSwiperAdapterOrderDetail(ViewSellOrderDetailActivity.this,
//                viewPager, productImagesList);
//        viewPager.setAdapter(adapter);
//        viewPager.addOnPageChangeListener(viewPagerPageChangeListener);
//        setAndShowDotsOnPager();

        pro_detail_layout1.setLayoutParams(AppLayoutParam(8.00f, 100f, 0, 0, 0, 0, imgs));
//        pro_name_layout.setLayoutParams(AppLayoutParam(10.00f, 70f, 0, 0, 0, 0, null));
//        pro_price_quant_layout.setLayoutParams(AppLayoutParam(10.00f, 30f, 0, 0, 0, 0, null));

        pro_name_rating_price_layout.setLayoutParams(AppLayoutParam(8.00f, 100f, 0, 0, 0, 0, pro_detail_layout1));
        pro_name_rating_layout.setLayoutParams(AppLayoutParam3(8.00f, 70f, 0, 0, 0, 0, null, 0));
        pro_price_layout.setLayoutParams(AppLayoutParam3(8.00f, 30f, 0, 0, 0, 0, null, R.id.sellers_title_layout));
        typing_layout.setLayoutParams(AppLayoutParam4(10.00f, 100f, 0, 0, 0, 0, true));


//        pro_name.setText(singleProduct.title);
//        pro_price.setText(singleProduct.price+"AED");
//        Log.e("ViewSellPro", "rating=" + singleProduct.productRating);
        LayerDrawable stars = (LayerDrawable) rating.getProgressDrawable();
        stars.getDrawable(2).setColorFilter(getResources().getColor(R.color.rating_color), PorterDuff.Mode.SRC_ATOP);
        stars.getDrawable(0).setColorFilter(Color.parseColor("#d5d5d5"), PorterDuff.Mode.SRC_ATOP);
        stars.getDrawable(1).setColorFilter(Color.YELLOW, PorterDuff.Mode.SRC_ATOP);
//        if (!singleProduct.productRating.equals("not available")) {
//            float ratingNum = Float.parseFloat(singleProduct.productRating);
//            Log.e("ViewSellPro", "rating 2=" + (int)ratingNum);
//            rating.setRating(1);
//            rating.setRating((int)ratingNum);
//        }
        messageET.clearFocus();
        makeKeyboardInvisible();
    }

    ArrayList<GetOrderDetails> list;
    ArrayList<ConversationItem> messageList;

    public void fillProductListWithData(ArrayList<GetOrderDetails> list) {
        this.list = list;

        //Log.e("ViewSellOrderDetail","size of sms list is="+list.get(0).messages.size());
        //message list in adapter
        messageList = new ArrayList<ConversationItem>();
        String nameOfMessenger;
        for (int i = 0; i < list.get(0).messages.size(); i++) {

            if (list.get(0).messages.get(i).sender.equals("1")) {
                nameOfMessenger = list.get(0).sellerDetails.fName;
            } else {
                nameOfMessenger = list.get(0).buyerDetails.fName;
            }
            ConversationItem item = new ConversationItem("empty" + i, nameOfMessenger, list.get(0).messages.get(i).dated, list.get(0).messages.get(i).messageBody);
            messageList.add(item);
        }
        conversationAdapter = new ConversationAdapter(ViewSellOrderDetailActivity.this,
                ViewSellOrderDetailActivity.this, R.layout.my_messages_layout, messageList);
        conversation_list.setAdapter(conversationAdapter);


        ///////////////
        pro_name.setText(list.get(0).orderDetails.get(0).productDetails.title);
        if (list.get(0).orderDetails.get(0).serviceDetails.categoryType.equals("0")) {
            pro_quantity.setText(list.get(0).orderDetails.get(0).productDetails.deliveryTime);
        } else {
            pro_quantity.setText(list.get(0).orderDetails.get(0).quantity);
        }

        pro_price.setText(list.get(0).orderDetails.get(0).productDetails.price + " AED");
        sellers_title.setText(list.get(0).sellerDetails.companyName);
        sellers_description.setText(list.get(0).sellerDetails.description);
        String proRating = list.get(0).sellerDetails.sellerRatings;
        Log.e("ViewSellOrderAct", "product rating=" + proRating);
        if (!proRating.equals("not available") || proRating.equals("0")) {
            float ratingNum = Float.parseFloat(proRating);
            rating.setRating(1);
            rating.setRating((int) ratingNum);
        }

        showImagesOnTopSwiper();
    }

    public void showImagesOnTopSwiper() {
        Log.e("ViewSellOrderAct", "order images list size=" + list.get(0).orderDetails.size());
        for (int i = 0; i < list.get(0).orderDetails.size(); i++) {
            String ImgId = list.get(0).orderDetails.get(i).productDetails.productImages.get(0).id;
            String ImgSellerProductId = list.get(0).orderDetails.get(i).productDetails.productImages.get(0).sellerProductId;
            String ImgPath = list.get(0).orderDetails.get(i).productDetails.productImages.get(0).image;

            ProductImagesOrd obj = new ProductImagesOrd(ImgId, ImgSellerProductId, ImgPath);
            productImagesList.add(obj);
        }

        adapter = new ImgSwiperAdapterOrderDetail(ViewSellOrderDetailActivity.this,
                viewPager, productImagesList);
        viewPager.setAdapter(adapter);
        viewPager.addOnPageChangeListener(viewPagerPageChangeListener);
        setAndShowDotsOnPager();
    }

    public void sendNewMessage(String messageBody) {
//        ConversationItem item = new ConversationItem("empty", list.get(0).sellerDetails.fName, list.get(0).messages.get(0).dated, messageBody);
        ConversationItem item = new ConversationItem("empty", "seller new", "date", messageBody);
        messageList.add(item);

        conversationAdapter = new ConversationAdapter(ViewSellOrderDetailActivity.this,
                ViewSellOrderDetailActivity.this, R.layout.my_messages_layout, messageList);
        conversation_list.setAdapter(conversationAdapter);
    }

    public void makeKeyboardInvisible() {
        View view = ViewSellOrderDetailActivity.this.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    public void MakeItemSelected(String btnName) {

//        add_product.setBackgroundResource(R.drawable.add_product_normal);
//        view_product.setBackgroundResource(R.drawable.view_product_normal);
//
//
//        switch (btnName) {
//            case ADD_PRODUCT:
//                add_product.setBackgroundResource(R.drawable.add_product_sel);
//                break;
//
//            case VIEW_PRODUCT:
//                view_product.setBackgroundResource(R.drawable.view_product_sel);
//                break;
//        }
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

            //change order name,price,quantity
            Log.e("ViewSellOrderAct", "order images page no=" + position);
            pro_name.setText(list.get(0).orderDetails.get(position).productDetails.title);
            if (list.get(0).orderDetails.get(position).serviceDetails.categoryType.equals("0")) {
                pro_quantity.setText(list.get(0).orderDetails.get(position).productDetails.deliveryTime);
            } else {
                pro_quantity.setText(list.get(0).orderDetails.get(position).quantity);
            }
            pro_price.setText(list.get(0).orderDetails.get(position).productDetails.price + " AED");
            //change order name,price,quantity
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

    public RelativeLayout.LayoutParams AppLayoutParam4(float height, float width, float mL, float mT, float mR, float mB, boolean parentBottom) {
        RelativeLayout.LayoutParams paramName = new RelativeLayout.LayoutParams(
                Constant.getSize("w", width),
                Constant.getSize("h", height));
        paramName.addRule(RelativeLayout.CENTER_HORIZONTAL, RelativeLayout.TRUE);

        if (parentBottom)
            paramName.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
        paramName.setMargins(Constant.getSize("h", mL), Constant.getSize("h", mT), Constant.getSize("h", mR), Constant.getSize("h", mB));
        return paramName;
    }
}

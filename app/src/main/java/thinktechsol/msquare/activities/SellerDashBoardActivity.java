package thinktechsol.msquare.activities;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;

import thinktechsol.msquare.R;
import thinktechsol.msquare.adapter.ItemAdapter;
import thinktechsol.msquare.model.Item;
import thinktechsol.msquare.utils.Constant;

public class SellerDashBoardActivity extends Activity {

    TextView title;
    RelativeLayout titlebarlayout, bottombarlayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_seller_dash_board);

        titlebarlayout = (RelativeLayout) findViewById(R.id.titlebarlayout);
        title = (TextView) findViewById(R.id.title);
        ListView simpleCustomeListView = (ListView) findViewById(R.id.listView);
//        bottombarlayout = (RelativeLayout) findViewById(R.id.bottombarlayout);


        titlebarlayout.setBackgroundColor(this.getResources().getColor(R.color.sellerDashBoardTitleBg));
        titlebarlayout.setLayoutParams(AppLayoutParam(10.00f, 100f, 0, 0, 0, 0, null));

        title.setText("DashBoard");

//        bottombarlayout.setBackgroundColor(this.getResources().getColor(R.color.bottomBarColor));
//        bottombarlayout.setLayoutParams(AppLayoutParam(10.00f, 100f, 0, 0, 0, 0, simpleCustomeListView));


        ArrayList<Item> m_parts = new ArrayList<Item>();
        m_parts.add(new Item(getResources().getString(R.string.lbl_message), 1, R.drawable.messages, R.color.messageColor, R.drawable.message_slide1, R.drawable.message_slide2));
        m_parts.add(new Item(getResources().getString(R.string.lbl_customer), 1, R.drawable.customer, R.color.customerColor, R.drawable.customer_slide1, R.drawable.customer_slide2));
        m_parts.add(new Item(getResources().getString(R.string.lbl_order), 1, R.drawable.order, R.color.orderColor, R.drawable.order_slide1, R.drawable.order_slide2, R.drawable.order_slide3, R.drawable.order_slide4));
        m_parts.add(new Item(getResources().getString(R.string.lbl_product), 1, R.drawable.product, R.color.productColor, R.drawable.product_slide1, R.drawable.product_slide2));





        ItemAdapter m_adapter = new ItemAdapter(this, R.layout.dashboard_row_item1, m_parts);
        simpleCustomeListView.setAdapter(m_adapter);


//        final SwipeLayout swipeLayout = (SwipeLayout) findViewById(R.id.simple1);
//        //set show mode.
//        swipeLayout.setShowMode(SwipeLayout.ShowMode.LayDown);
//        //add drag edge.(If the BottomView has 'layout_gravity' attribute, this line is unnecessary)
//        //swipeLayout.addDrag(SwipeLayout.DragEdge.Left, findViewById(R.id.bottom_wrapper));
//        swipeLayout.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                swipeLayout.open();
//            }
//        });
//        swipeLayout.addSwipeListener(new SwipeLayout.SwipeListener() {
//            @Override
//            public void onClose(SwipeLayout layout) {
//                //when the SurfaceView totally cover the BottomView.
//            }
//
//            @Override
//            public void onUpdate(SwipeLayout layout, int leftOffset, int topOffset) {
//                //you are swiping.
//            }
//
//            @Override
//            public void onStartOpen(SwipeLayout layout) {
//
//            }
//
//            @Override
//            public void onOpen(SwipeLayout layout) {
//                //when the BottomView totally show.
//            }
//
//            @Override
//            public void onStartClose(SwipeLayout layout) {
//                swipeLayout.open();
//            }
//
//            @Override
//            public void onHandRelease(SwipeLayout layout, float xvel, float yvel) {
//                //when user's hand released.
//                swipeLayout.open();
//            }
//        });
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

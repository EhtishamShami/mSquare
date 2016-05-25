package thinktechsol.msquare.activities;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

import com.daimajia.swipe.SwipeLayout;

import thinktechsol.msquare.R;

public class SellerDashBoardActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_seller_dash_board);
        setContentView(R.layout.dashboard_3rd_row);

//        ArrayList<Item> m_parts = new ArrayList<Item>();
//        m_parts.add(new Item(getResources().getString(R.string.lbl_message), 1, R.drawable.messages, R.color.messageColor));
//        m_parts.add(new Item(getResources().getString(R.string.lbl_customer), 1, R.drawable.customer, R.color.customerColor));
//        m_parts.add(new Item(getResources().getString(R.string.lbl_order), 1, R.drawable.order, R.color.orderColor));
//        m_parts.add(new Item(getResources().getString(R.string.lbl_product), 1, R.drawable.product, R.color.productColor));
//
//        ListView simpleCustomeListView = (ListView) findViewById(R.id.listView);
//        ItemAdapter2 m_adapter = new ItemAdapter2(this, R.layout.dashboard_1st_row, m_parts);
//        simpleCustomeListView.setAdapter(m_adapter);


        final SwipeLayout swipeLayout =  (SwipeLayout)findViewById(R.id.simple1);
        //set show mode.
        swipeLayout.setShowMode(SwipeLayout.ShowMode.LayDown);
        //add drag edge.(If the BottomView has 'layout_gravity' attribute, this line is unnecessary)
//        swipeLayout.addDrag(SwipeLayout.DragEdge.Left, findViewById(R.id.bottom_wrapper));

        swipeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                swipeLayout.open();
//                swipeLayout.
            }
        });
        swipeLayout.addSwipeListener(new SwipeLayout.SwipeListener() {
            @Override
            public void onClose(SwipeLayout layout) {
                //when the SurfaceView totally cover the BottomView.
            }

            @Override
            public void onUpdate(SwipeLayout layout, int leftOffset, int topOffset) {
                //you are swiping.
            }

            @Override
            public void onStartOpen(SwipeLayout layout) {

            }

            @Override
            public void onOpen(SwipeLayout layout) {
                //when the BottomView totally show.
            }

            @Override
            public void onStartClose(SwipeLayout layout) {
                swipeLayout.open();
            }

            @Override
            public void onHandRelease(SwipeLayout layout, float xvel, float yvel) {
                //when user's hand released.
                swipeLayout.open();
            }
        });
    }

}

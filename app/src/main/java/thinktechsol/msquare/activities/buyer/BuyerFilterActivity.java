package thinktechsol.msquare.activities.buyer;


import android.app.Activity;
import android.app.AlertDialog;
import android.app.TimePickerDialog;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.appyvet.rangebar.RangeBar;

import java.util.ArrayList;
import java.util.Calendar;

import thinktechsol.msquare.R;
import thinktechsol.msquare.globels.globels;
import thinktechsol.msquare.model.Buyer.BuyerWishListModel;
import thinktechsol.msquare.model.Buyer.CategoryModel;
import thinktechsol.msquare.model.Buyer.FiltersModel;
import thinktechsol.msquare.model.Buyer.GetBuyersOrdersModel;
import thinktechsol.msquare.services.buyer.GetBuyerFilterationCategory;
import thinktechsol.msquare.utils.Constant;

public class BuyerFilterActivity extends Activity implements TimePickerDialog.OnTimeSetListener {


    RelativeLayout titlebarlayout;
    RelativeLayout serviceDetLayout, distanceLayout, priceLayout, categoryLayout;
    TextView title, startTimeTv, endTimeTv;
    ImageView backBtn, btn_menu;
    String distance, priceFrom, priceTo, fromTime, toTime;
    static String categories;
    TextView startTV, endTV;
    String TimeClickedView;
    TextView distanceTV, startPriceTV, endPriceTV;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_buyer_filter);

        new GetBuyerFilterationCategory(BuyerFilterActivity.this, BuyerFilterActivity.this, Constant.sellerServiceId);

        titlebarlayout = (RelativeLayout) findViewById(R.id.titlebarlayout);
        title = (TextView) findViewById(R.id.title);
        startTV = (TextView) findViewById(R.id.startTV);
        endTV = (TextView) findViewById(R.id.endTV);
        startTimeTv = (TextView) findViewById(R.id.startTimeTv);
        endTimeTv = (TextView) findViewById(R.id.endTimeTv);

        distanceTV = (TextView) findViewById(R.id.distanceTV);
        startPriceTV = (TextView) findViewById(R.id.startPriceTV);
        endPriceTV = (TextView) findViewById(R.id.endPriceTV);

        backBtn = (ImageView) findViewById(R.id.backBtn);
        btn_menu = (ImageView) findViewById(R.id.btn_menu);


        // title bar
        backBtn.setLayoutParams(AppLayoutParam(5f, 8f, 2, 0, 0, 0, null, "ver", 0, "null"));
        btn_menu.setLayoutParams(AppLayoutParam(4f, 14f, 0, 0, 2, 0, null, "ver", 0, "right"));
        btn_menu.setVisibility(View.VISIBLE);
        backBtn.setImageResource(0);
        btn_menu.setBackgroundResource(R.drawable.menu_apply);
        backBtn.setBackgroundResource(R.drawable.cross_btn);
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        btn_menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                new GetServiceSellersSearch(BuyerFilterActivity.this, null, Constant.sellerServiceId, "24.433904943494827", "54.41303014755249", searchStr, distance, priceFrom, priceTo, fromTime, toTime, categories);
                //new GetServiceSellersSearch(BuyerFilterActivity.this, null, Constant.sellerServiceId, "24.433904943494827", "54.41303014755249", searchStr, distance, priceFrom, priceTo, fromTime, toTime, categories);


//                fromTime = startTimeTv.getText().toString().trim();
//                toTime = endTimeTv.getText().toString().trim();

                Log.e("BuyerFilterAct", "dis=" + distance);
                Log.e("BuyerFilterAct", "priceFrom=" + priceFrom);
                Log.e("BuyerFilterAct", "priceTo=" + priceTo);
                Log.e("BuyerFilterAct", "fromTime=" + fromTime);
                Log.e("BuyerFilterAct", "toTime=" + toTime);
                Log.e("BuyerFilterAct", "categories=" + categories);

                FiltersModel filterdObj = new FiltersModel(distance, priceFrom, priceTo, fromTime, toTime, categories);
                globels.getGlobelRef().filterdDateObj = filterdObj;

                globels.getGlobelRef().filteration = true;
                finish();


            }
        });
        title.setText("FILTERS");
        title.setTypeface(null, Typeface.BOLD);
        title.setTextColor(Color.BLACK);
        titlebarlayout.setBackgroundColor(this.getResources().getColor(R.color.color_filter_title_bar));
        titlebarlayout.setLayoutParams(AppLayoutParam(10.00f, 100f, 0, 0, 0, 0, null, "hor", 0, "null"));


        serviceDetLayout = (RelativeLayout) findViewById(R.id.serviceDetLayout);
        distanceLayout = (RelativeLayout) findViewById(R.id.distanceLayout);
        priceLayout = (RelativeLayout) findViewById(R.id.priceLayout);
        categoryLayout = (RelativeLayout) findViewById(R.id.categoryLayout);


        serviceDetLayout.setLayoutParams(AppLayoutParam(17.00f, 100f, 0, 0, 0, 0, titlebarlayout, "hor", 0, "null"));
        distanceLayout.setLayoutParams(AppLayoutParam(17.00f, 100f, 0, 2, 0, 0, serviceDetLayout, "hor", 0, "null"));
        priceLayout.setLayoutParams(AppLayoutParam(20.00f, 100f, 0, 2, 0, 0, distanceLayout, "hor", 0, "null"));
        categoryLayout.setLayoutParams(AppLayoutParam(19.00f, 100f, 0, 2, 0, 0, priceLayout, "hor", 0, "null"));


        RangeBar distanceRangebar = (RangeBar) findViewById(R.id.distanceRangebar);
        distanceRangebar.setOnRangeBarChangeListener(new RangeBar.OnRangeBarChangeListener() {
            @Override
            public void onRangeChangeListener(RangeBar rangeBar, int leftPinIndex,
                                              int rightPinIndex,
                                              String leftPinValue, String rightPinValue) {
                //Log.e("BuyerFilterAct", "seekbar 1" + leftPinValue + " , " + rightPinValue);

                distance = rightPinValue;
                distanceTV.setText(distance);
            }
        });

        RangeBar priceSeekbar = (RangeBar) findViewById(R.id.priceSeekbar);
        priceSeekbar.setOnRangeBarChangeListener(new RangeBar.OnRangeBarChangeListener() {
            @Override
            public void onRangeChangeListener(RangeBar rangeBar, int leftPinIndex,
                                              int rightPinIndex,
                                              String leftPinValue, String rightPinValue) {
                // Log.e("BuyerFilterAct", "seekbar 2" + leftPinValue + " , " + rightPinValue);

                priceFrom = leftPinValue;
                priceTo = rightPinValue;

                startPriceTV.setText(priceFrom);
                endPriceTV.setText(priceTo);
            }
        });


        categories = "";


//        Log.e("BuyerFilterAct","dis="+distance);
//        Log.e("BuyerFilterAct","priceFrom="+priceFrom);
//        Log.e("BuyerFilterAct","priceTo="+priceTo);
//        Log.e("BuyerFilterAct","fromTime="+fromTime);
//        Log.e("BuyerFilterAct","toTime="+toTime);
//        Log.e("BuyerFilterAct","categories="+categories);

        startTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TimeClickedView = "startTimeTv";
                show_time_picker(startTimeTv);
            }
        });

        endTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TimeClickedView = "endTimeTv";
                show_time_picker(endTimeTv);
            }
        });

        fromTime = startTimeTv.getText().toString();
        toTime = endTimeTv.getText().toString();


        grid = (GridView) findViewById(R.id.grid_view);
//        final ArrayList<String> items = new ArrayList<String>();
//        items.add("Hello12");
//        items.add("Hello22");
//        items.add("Hello22");
//        items.add("Hello22");
//        items.add("Hello22");
//        grid.setAdapter(new GridAdapter(items));
//
//        grid.setChoiceMode(GridView.CHOICE_MODE_MULTIPLE);
    }

    GridView grid;

    public void show_time_picker(View v) {

        final Calendar c = Calendar.getInstance();
        int hour = c.get(Calendar.HOUR_OF_DAY);
        int minute = c.get(Calendar.MINUTE);

        TimePickerDialog tpd = new TimePickerDialog(this, AlertDialog.THEME_DEVICE_DEFAULT_DARK
                , this, hour, minute, DateFormat.is24HourFormat(this));
        tpd.show();

    }

    public void fillProductListWithData(ArrayList<CategoryModel> list) {
        Log.e("categoryModel", "categoryModel=" + list.size());

        grid.setAdapter(new GridAdapter(list));

        grid.setChoiceMode(GridView.CHOICE_MODE_MULTIPLE);
    }

    @Override
    public void onBackPressed() {
        finish();
    }

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

    public int getSize(String dimension, float size) {
        float x = 0;
        if (dimension.equals("w")) {
            x = (size / 100) * Constant.screenWidth;
        } else {
            x = (size / 100) * Constant.screenHeight;
        }
        return (int) x;
    }


    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        if (TimeClickedView.equals("startTimeTv")) {
//            Toast.makeText(getApplicationContext(), "new time:" + hourOfDay + "-" + minute, Toast.LENGTH_LONG).show();
            fromTime = hourOfDay + ":" + minute + ":00";
            boolean isPM = (hourOfDay >= 12);
            startTimeTv.setText(String.format("%02d:%02d %s", (hourOfDay == 12 || hourOfDay == 0) ? 12 : hourOfDay % 12, minute, isPM ? "PM" : "AM"));
            //startTimeTv.setText(hourOfDay+":"+minute);
        } else {

            toTime = hourOfDay + ":" + minute + ":00";
            boolean isPM = (hourOfDay >= 12);
            endTimeTv.setText(String.format("%02d:%02d %s", (hourOfDay == 12 || hourOfDay == 0) ? 12 : hourOfDay % 12, minute, isPM ? "PM" : "AM"));
            //endTimeTv.setText(hourOfDay+":"+minute);
        }
    }

    ////gridview data

    private static final class GridAdapter extends BaseAdapter {

        final ArrayList<CategoryModel> mItems;
        final int mCount;

        /**
         * Default constructor
         *
         * @param items to fill data to
         */
        private GridAdapter(final ArrayList<CategoryModel> items) {

            mCount = items.size();
            //mItems = new ArrayList<String>();
            mItems = items;
        }

        @Override
        public int getCount() {
            return mCount;
        }

        @Override
        public Object getItem(final int position) {
            return mItems.get(position);
        }

        @Override
        public long getItemId(final int position) {
            return position;
        }

        @Override
        public View getView(final int position, final View convertView, final ViewGroup parent) {

            View view = convertView;
            TextView text = null;

            if (view == null) {
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.gridview_item, parent, false);

                text = (TextView) view.findViewById(R.id.item);
            }

            final CategoryModel myItem = mItems.get(position);

            if (text != null)
                text.setText(myItem.name);
            //text.setText(mItems.get(position));

            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //view.setItemChecked(position, true);
                    categories = myItem.id;
                    //Toast.makeText(parent.getContext(), "" + myItem.id, Toast.LENGTH_SHORT).show();
                }
            });

            return view;
        }
    }
}

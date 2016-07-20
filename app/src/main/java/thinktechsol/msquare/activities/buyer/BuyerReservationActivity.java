package thinktechsol.msquare.activities.buyer;

import android.app.Activity;
import android.graphics.RectF;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alamkanak.weekview.DateTimeInterpreter;
import com.alamkanak.weekview.MonthLoader;
import com.alamkanak.weekview.WeekView;
import com.alamkanak.weekview.WeekViewEvent;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import thinktechsol.msquare.R;
import thinktechsol.msquare.adapter.BuyerServiceSellersProductListAdapter;
import thinktechsol.msquare.adapter.TimeListAdapter;
import thinktechsol.msquare.fragments.Buyer.SellersServiceFragment;
import thinktechsol.msquare.globels.globels;
import thinktechsol.msquare.model.Buyer.TimeListItemModel;
import thinktechsol.msquare.utils.Constant;

public class BuyerReservationActivity extends Activity implements WeekView.EventClickListener, MonthLoader.MonthChangeListener, WeekView.EventLongPressListener, WeekView.EmptyViewLongPressListener
        /*implements WeekView.EventClickListener, WeekView.EventLongPressListener */ {

    RelativeLayout titlebarlayout;
    TextView title;
    ImageView backBtn, btn_menu;

    RelativeLayout peopleLayout, calenderLayout, timeLayout;
    ListView timelisview;
    ArrayList<TimeListItemModel> list;

    WeekView mWeekView;
    private static ArrayList<WeekViewEvent> mNewEvents;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buyer_reservation);

        list = new ArrayList<TimeListItemModel>();

        titlebarlayout = (RelativeLayout) findViewById(R.id.titlebar);
        title = (TextView) findViewById(R.id.title);
        backBtn = (ImageView) findViewById(R.id.backBtn);
        btn_menu = (ImageView) findViewById(R.id.btn_menu);

        peopleLayout = (RelativeLayout) findViewById(R.id.peopleLayout);
        calenderLayout = (RelativeLayout) findViewById(R.id.calenderLayout);
        timeLayout = (RelativeLayout) findViewById(R.id.timeLayout);
        timelisview = (ListView) findViewById(R.id.timelisview);

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
        title.setText("Book Service");
        titlebarlayout.setBackgroundColor(this.getResources().getColor(R.color.buyerHomeActivityTitleBarColor));
        titlebarlayout.setLayoutParams(AppLayoutParam(10.00f, 100f, 0, 0, 0, 0, null, "hor", 0, "null"));
        // title bar end


        peopleLayout.setLayoutParams(AppLayoutParam(7.00f, 100f, 0, 0, 0, 0, titlebarlayout, "hor", 0, "null"));
        peopleLayout.setBackgroundColor(this.getResources().getColor(R.color.color_userLayout));

        calenderLayout.setLayoutParams(AppLayoutParam(10.00f, 100f, 0, 0, 0, 0, peopleLayout, "hor", 0, "null"));
        timeLayout.setLayoutParams(AppLayoutParam(25.00f, 100f, 0, 0, 0, 0, calenderLayout, "hor", 0, "null"));


//        calender work out
        // Get a reference for the week view in the layout.
        mWeekView = (WeekView) findViewById(R.id.weekView);

        mNewEvents = new ArrayList<WeekViewEvent>();

        // Show a toast message about the touched event.
        mWeekView.setOnEventClickListener(this);
        // The week view has infinite scrolling horizontally. We have to provide the events of a
        // month every time the month changes on the week view.
        mWeekView.setMonthChangeListener(this);
        // Set long press listener for events.
        mWeekView.setEventLongPressListener(this);
        // Set long press listener for empty view
        mWeekView.setEmptyViewLongPressListener(this);
        // Set up a date time interpreter to interpret how the date and time will be formatted in
        // the week view. This is optional.
        setupDateTimeInterpreter(false);


//        mWeekViewType = TYPE_WEEK_VIEW;
        mWeekView.setNumberOfVisibleDays(5);

        mWeekView.setHourHeight(250);
        // Lets change some dimensions to best fit the view.
        mWeekView.setColumnGap((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 2, getResources().getDisplayMetrics()));
        mWeekView.setTextSize((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 10, getResources().getDisplayMetrics()));
        mWeekView.setEventTextSize((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 10, getResources().getDisplayMetrics()));


        list.add(new TimeListItemModel("1", "01:00 AM"));
        list.add(new TimeListItemModel("2", "01:15 AM"));
        list.add(new TimeListItemModel("3", "01:30 AM"));
        list.add(new TimeListItemModel("4", "01:45 AM"));

        TimeListAdapter adapter = new TimeListAdapter(this, BuyerReservationActivity.this, R.layout.time_list_adapter_item, list);
        timelisview.setAdapter(adapter);
    }

//    WeekView.MonthChangeListener mMonthChangeListener = new WeekView.MonthChangeListener() {
//        @Override
//        public List<WeekViewEvent> onMonthChange(int newYear, int newMonth) {
//            // Populate the week view with some events.
//            List<WeekViewEvent> events = getEvents(newYear, newMonth);
//            return events;
//        }
//    };

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

    public RelativeLayout.LayoutParams AppLayoutParam2(float height, float width, float mL, float mT, float mR, float mB, View below) {
        RelativeLayout.LayoutParams paramName = new RelativeLayout.LayoutParams(
                Constant.getSize("w", width),
                Constant.getSize("h", height));

        if (below != null) {
            Log.e("HomeActivity", "belowing is=" + below.getId());
            paramName.addRule(RelativeLayout.BELOW, below.getId());
        }
        paramName.setMargins(Constant.getSize("h", mL), Constant.getSize("h", mT), Constant.getSize("h", mR), Constant.getSize("h", mB));
        return paramName;
    }

    private void setupDateTimeInterpreter(final boolean shortDate) {
        mWeekView.setDateTimeInterpreter(new DateTimeInterpreter() {
            @Override
            public String interpretDate(Calendar date) {
                SimpleDateFormat weekdayNameFormat = new SimpleDateFormat("EEE", Locale.getDefault());
                String weekday = weekdayNameFormat.format(date.getTime());
//                SimpleDateFormat format = new SimpleDateFormat(" M/d", Locale.getDefault());
                SimpleDateFormat format = new SimpleDateFormat(" d", Locale.getDefault());

                // All android api level do not have a standard way of getting the first letter of
                // the week day name. Hence we get the first char programmatically.
                // Details: http://stackoverflow.com/questions/16959502/get-one-letter-abbreviation-of-week-day-of-a-date-in-java#answer-16959657
                if (shortDate)
                    weekday = String.valueOf(weekday.charAt(0));
                String value = format.format(date.getTime());
                return weekday.toUpperCase()+"" +
                        ""+value;
//                return weekday.toUpperCase() + format.format(date.getTime());
            }

            @Override
            public String interpretTime(int hour) {
                return "";
//                return hour > 11 ? (hour - 12) + " PM" : (hour == 0 ? "12 AM" : hour + " AM");
            }
        });
    }

//    protected String getEventTitle(Calendar time) {
//        return String.format("Event of %02d:%02d %s/%d", time.get(Calendar.HOUR_OF_DAY), time.get(Calendar.MINUTE), time.get(Calendar.MONTH) + 1, time.get(Calendar.DAY_OF_MONTH));
//        //return String.format("Event of %02d:%02d %s/%d", time.get(Calendar.HOUR_OF_DAY), time.get(Calendar.MINUTE), time.get(Calendar.MONTH) + 1, time.get(Calendar.DAY_OF_MONTH));
////        return String.format("%s %d/%02d", time.get(Calendar.DAY_OF_MONTH), time.get(Calendar.MONTH) + 1, time.get(Calendar.DAY_OF_MONTH));
//    }

    @Override
    public void onEventClick(WeekViewEvent event, RectF eventRect) {
        Toast.makeText(this, "Clicked " + event.getName(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onEventLongPress(WeekViewEvent event, RectF eventRect) {
        Toast.makeText(this, "Long pressed event: " + event.getName(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onEmptyViewLongPress(Calendar time) {
        //Toast.makeText(this, "Empty view long pressed: " + getEventTitle(time), Toast.LENGTH_SHORT).show();
    }

    public WeekView getWeekView() {
        return mWeekView;
    }

    @Override
    public List<? extends WeekViewEvent> onMonthChange(int newYear, int newMonth) {
//        List<WeekViewEvent> events = getEvents(newYear, newMonth);
//        return events;
        List<WeekViewEvent> events = new ArrayList<WeekViewEvent>();
        ArrayList<WeekViewEvent> newEvents = getNewEvents(newYear, newMonth);

        Toast.makeText(this, "onMonthChange: " + newEvents, Toast.LENGTH_SHORT).show();
        return newEvents;
    }

    private ArrayList<WeekViewEvent> getNewEvents(int year, int month) {

        // Get the starting point and ending point of the given month. We need this to find the
        // events of the given month.
        Calendar startOfMonth = Calendar.getInstance();
        startOfMonth.set(Calendar.YEAR, year);
        startOfMonth.set(Calendar.MONTH, month - 1);
        startOfMonth.set(Calendar.DAY_OF_MONTH, 1);
        startOfMonth.set(Calendar.HOUR_OF_DAY, 0);
        startOfMonth.set(Calendar.MINUTE, 0);
        startOfMonth.set(Calendar.SECOND, 0);
        startOfMonth.set(Calendar.MILLISECOND, 0);
        Calendar endOfMonth = (Calendar) startOfMonth.clone();
        endOfMonth.set(Calendar.DAY_OF_MONTH, endOfMonth.getMaximum(Calendar.DAY_OF_MONTH));
        endOfMonth.set(Calendar.HOUR_OF_DAY, 23);
        endOfMonth.set(Calendar.MINUTE, 59);
        endOfMonth.set(Calendar.SECOND, 59);

        // Find the events that were added by tapping on empty view and that occurs in the given
        // time frame.
        ArrayList<WeekViewEvent> events = new ArrayList<WeekViewEvent>();
        for (WeekViewEvent event : mNewEvents) {
            if (event.getEndTime().getTimeInMillis() > startOfMonth.getTimeInMillis() &&
                    event.getStartTime().getTimeInMillis() < endOfMonth.getTimeInMillis()) {
                events.add(event);
            }
        }
        return events;
    }
}

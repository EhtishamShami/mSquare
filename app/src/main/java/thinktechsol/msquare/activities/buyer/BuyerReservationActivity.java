package thinktechsol.msquare.activities.buyer;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.pm.ActivityInfo;
import android.graphics.RectF;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alamkanak.weekview.DateTimeInterpreter;
import com.alamkanak.weekview.MonthLoader;
import com.alamkanak.weekview.WeekView;
import com.alamkanak.weekview.WeekViewEvent;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import thinktechsol.msquare.R;
import thinktechsol.msquare.adapter.ChangeServiceProviderListAdapter;
import thinktechsol.msquare.adapter.TimeListAdapter;
import thinktechsol.msquare.globels.globels;
import thinktechsol.msquare.model.Buyer.ChangeServiceProviderListItemModel;
import thinktechsol.msquare.model.Buyer.ConfirmBookingModel;
import thinktechsol.msquare.model.Buyer.TimeListItemModel;
import thinktechsol.msquare.services.AddBuyerOrder;
import thinktechsol.msquare.utils.Constant;

public class BuyerReservationActivity extends Activity implements WeekView.EventClickListener, MonthLoader.MonthChangeListener, WeekView.EventLongPressListener, WeekView.EmptyViewLongPressListener, WeekView.ScrollListener, WeekView.EmptyViewClickListener, WeekView.OnClickListener
        /*implements WeekView.EventClickListener, WeekView.EventLongPressListener */ {

    RelativeLayout titlebarlayout;
    TextView title;
    ImageView backBtn, btn_menu;

    RelativeLayout peopleLayout, calenderOuterLayout, calenderLayout, timeLayout, serviceDetailLayout, sellersDetailLayout;
    ListView timelisview;
    ArrayList<TimeListItemModel> list;
    ArrayList<ChangeServiceProviderListItemModel> providerList;

    WeekView mWeekView;
    private static ArrayList<WeekViewEvent> mNewEvents;
    TextView userName, changeUser, tvDate, tvTime, tvServiceProvider, sellersTitle, tvPrice, serviceNames, currentDate;
    Button confrmBookingBtn;
    EditText etDescription;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_buyer_reservation);

        list = new ArrayList<TimeListItemModel>();
        providerList = new ArrayList<ChangeServiceProviderListItemModel>();

        titlebarlayout = (RelativeLayout) findViewById(R.id.titlebar);
        title = (TextView) findViewById(R.id.title);
        backBtn = (ImageView) findViewById(R.id.backBtn);
        btn_menu = (ImageView) findViewById(R.id.btn_menu);

        peopleLayout = (RelativeLayout) findViewById(R.id.peopleLayout);
        calenderLayout = (RelativeLayout) findViewById(R.id.calenderLayout);
        calenderOuterLayout = (RelativeLayout) findViewById(R.id.calenderOuterLayout);
        timeLayout = (RelativeLayout) findViewById(R.id.timeLayout);
        serviceDetailLayout = (RelativeLayout) findViewById(R.id.serviceDetailLayout);
        sellersDetailLayout = (RelativeLayout) findViewById(R.id.sellersDetailLayout);
        userName = (TextView) findViewById(R.id.userName);
        changeUser = (TextView) findViewById(R.id.changeUser);
        tvDate = (TextView) findViewById(R.id.tvDate);
        tvTime = (TextView) findViewById(R.id.tvTime);
        tvServiceProvider = (TextView) findViewById(R.id.tvServiceProvider);
        sellersTitle = (TextView) findViewById(R.id.sellersTitle);
        tvPrice = (TextView) findViewById(R.id.tvPrice);
        serviceNames = (TextView) findViewById(R.id.serviceNames);
        currentDate = (TextView) findViewById(R.id.currentDate);
        confrmBookingBtn = (Button) findViewById(R.id.confrmBookingBtn);
        etDescription = (EditText) findViewById(R.id.etDescription);


        setSellersTitle(globels.getGlobelRef().productList.get(0).sellerInfo.companyName);
        setTotalServicesPrice(String.valueOf(globels.getGlobelRef().SelectedServicesPrice));
        setSelectedServicesName(String.valueOf(globels.getGlobelRef().allSelectedServices));

        timelisview = (ListView) findViewById(R.id.timelisview);

        // title bar
        backBtn.setLayoutParams(AppLayoutParam(10f, 10f, 0, 0, 0, 0, null, "ver", 0, "null"));
        btn_menu.setLayoutParams(AppLayoutParam(12f, 8f, 0, 0, 2, 0, null, "ver", 0, "right"));
//        btn_menu.setVisibility(View.VISIBLE);
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                globels.getGlobelRef().allSelectedServices = "";
                globels.getGlobelRef().SelectedServicesPrice = 0;
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

        calenderOuterLayout.setLayoutParams(AppLayoutParam(15.00f, 100f, 0, 0, 0, 0, peopleLayout, "hor", 0, "null"));
//        calenderOuterLayout.setLayoutParams(AppLayoutParam(20.00f, 100f, 0, 0, 0, 0, peopleLayout, "hor", 0, "null"));
        timeLayout.setLayoutParams(AppLayoutParam(26.00f, 100f, 0, 0, 0, -1, calenderOuterLayout, "hor", 0, "null"));
        serviceDetailLayout.setLayoutParams(AppLayoutParam(17.00f, 100f, 0, 0, 0, 0, timeLayout, "hor", 0, "null"));
        sellersDetailLayout.setLayoutParams(AppLayoutParam(58f, 100f, 0, 0, 0, 0, serviceDetailLayout, "hor", 0, "null"));


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
        mWeekView.setEmptyViewClickListener(this);
//        mWeekView.setOnClickListener(this);

        mWeekView.setScrollListener(this);
        mWeekView.setTodayBackgroundColor(R.color.customerColor);

//        mWeekView.setOverScrollMode(7);
        // Set up a date time interpreter to interpret how the date and time will be formatted in
        // the week view. This is optional.
        setupDateTimeInterpreter(false);


        mWeekView.setNumberOfVisibleDays(5);
        mWeekView.setHourHeight(250);

//        mWeekView.setXScrollingSpeed(5);

        // Lets change some dimensions to best fit the view.
        mWeekView.setColumnGap((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 2, getResources().getDisplayMetrics()));
        mWeekView.setTextSize((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 10, getResources().getDisplayMetrics()));
        mWeekView.setEventTextSize((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 10, getResources().getDisplayMetrics()));
        //calender work end


        list.add(new TimeListItemModel("1", "01:00 AM", false));
        list.add(new TimeListItemModel("2", "01:15 AM", false));
        list.add(new TimeListItemModel("3", "01:30 AM", false));
        list.add(new TimeListItemModel("4", "01:45 AM", false));
        TimeListAdapter adapter = new TimeListAdapter(this, BuyerReservationActivity.this, R.layout.time_list_adapter_item, list);
        timelisview.setAdapter(adapter);

//        timelisview.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
//        timelisview.setSelector(android.R.color.darker_gray);


        PopUpForChangeServiceProvider();

        getCurrentDateAndShowOnView();
        changeUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeServiceProviderUser.show();
            }
        });

        confrmBookingBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(BuyerReservationActivity.this, "Confirm booking is clicked", Toast.LENGTH_SHORT).show();

                String sellerId = globels.getGlobelRef().productList.get(0).sellerInfo.id;
                String buyerId = "1";
                String extraRemarks = etDescription.getText().toString();
                String serviceRequestTime = selectedDateForPostingToService + " " + selectedTimeForPostingToService;
                String staffId = "1";

                int[] serviceId = new int[globels.getGlobelRef().selectedServicesIds.size()];
                int[] productId = new int[globels.getGlobelRef().selectedServicesIds.size()];
                int[] quantity = new int[1];

                ConfirmBookingModel obj = new ConfirmBookingModel(sellerId, buyerId, extraRemarks, serviceRequestTime, staffId, globels.getGlobelRef().selectedServicesIds, globels.getGlobelRef().selectedProductsIds, null);

                new AddBuyerOrder(BuyerReservationActivity.this, BuyerReservationActivity.this, obj);
            }
        });
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
                String dateOfMonth = format.format(date.getTime());
//                return weekday.toUpperCase();
                return weekday.toUpperCase() + "" + "" + dateOfMonth;
//                return weekday.toUpperCase() + format.format(date.getTime());
            }

            @Override
            public String interpretTime(int hour) {
                return "";
//                return hour > 11 ? (hour - 12) + " PM" : (hour == 0 ? "12 AM" : hour + " AM");
            }
        });
    }

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
//        Toast.makeText(this, "Empty view long pressed: " + getEventTitle(time), Toast.LENGTH_SHORT).show();
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

        //Toast.makeText(this, "onMonthChange: " + newEvents, Toast.LENGTH_SHORT).show();
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

    @Override
    public void onFirstVisibleDayChanged(Calendar newFirstVisibleDay, Calendar oldFirstVisibleDay) {
//        Toast.makeText(this, "scrolling: " + mWeekView.getScrollX(), Toast.LENGTH_SHORT).show();

//        mWeekView.computeScroll();
//        mWeekView.getScrollX();
    }

    @Override
    public void onEmptyViewClicked(Calendar time) {
        // Set the new event with duration one hour.
        Calendar endTime = (Calendar) time.clone();
        endTime.add(Calendar.HOUR, 1);

        // Create a new event.
//        WeekViewEvent event = new WeekViewEvent(0, "", time, endTime);
//        mNewEvents.add(event);

        // Refresh the week view. onMonthChange will be called again.
        mWeekView.notifyDatasetChanged();

//        mWeekView.setDateTimeInterpreter();

//        mWeekView.setNowLineColor(R.color.messageColor);
//        mWeekView.setShowNowLine(true);

//        String value = String.format("%02d %02d %02d", time.get(Calendar.YEAR), time.get(Calendar.MONTH) + 1, time.get(Calendar.DAY_OF_MONTH));
//        Toast.makeText(this, "selected Date: " + new SimpleDateFormat("yyyy MMM dd").format(time.getTime()), Toast.LENGTH_SHORT).show();

        dateFormatForPosting = new SimpleDateFormat("yyyy-MM-dd");
        tvDate.setText(new SimpleDateFormat("yyyy MMM dd").format(time.getTime()));
        selectedDateForPostingToService = dateFormatForPosting.format(time.getTime());
    }

    DateFormat dateFormatForPosting;

    AlertDialog changeServiceProviderUser;

    public void PopUpForChangeServiceProvider() {
        LayoutInflater inflater = this.getLayoutInflater();
        AlertDialog.Builder builder = new AlertDialog.Builder(BuyerReservationActivity.this);
        View dialogView = inflater.inflate(R.layout.change_service_provider_popup, null);
        builder.setView(dialogView);

        ListView change_user_list = (ListView) dialogView.findViewById(R.id.change_user_list);
        providerList.add(new ChangeServiceProviderListItemModel(R.drawable.avatar, "Yasir Ahmed"));
        providerList.add(new ChangeServiceProviderListItemModel(R.drawable.avatar, "Bilal"));
        providerList.add(new ChangeServiceProviderListItemModel(R.drawable.avatar, "Zain u din"));
        providerList.add(new ChangeServiceProviderListItemModel(R.drawable.avatar, "Maroof"));
        providerList.add(new ChangeServiceProviderListItemModel(0, ""));

        ChangeServiceProviderListAdapter adapter = new ChangeServiceProviderListAdapter(this, BuyerReservationActivity.this, R.layout.change_service_provider_list_adapter_item, providerList);
        change_user_list.setAdapter(adapter);

        changeServiceProviderUser = builder.create();
        changeServiceProviderUser.setCancelable(true);
        changeServiceProviderUser.setCanceledOnTouchOutside(true);
//        changeServiceProviderUser.show();
    }

    public void changeServiceProviderUser(String name) {
        userName.setText(name);
        tvServiceProvider.setText("With " + name);
        changeServiceProviderUser.dismiss();
    }

    public void changeSelectedTime(String time) {
        tvTime.setText(time);
    }

    public void changeSelectedDate(String date) {
        tvDate.setText(date);
    }

    public void setSellersTitle(String name) {
        sellersTitle.setText(name);
    }

    public void setTotalServicesPrice(String price) {
        tvPrice.setText("AED " + price);
    }

    public void setSelectedServicesName(String selectedServiceNames) {
        serviceNames.setText(selectedServiceNames);
    }

    @Override
    public void onClick(View v) {
        Toast.makeText(getApplicationContext(), "clicked", Toast.LENGTH_SHORT).show();
    }

    String selectedDateForPostingToService, selectedTimeForPostingToService;


    public void getCurrentDateAndShowOnView() {
        DateFormat dateFormat = new SimpleDateFormat("E, yyyy MMM dd");
        DateFormat dateFormat2 = new SimpleDateFormat("yyyy MMM dd");

        DateFormat dateFormat3 = new SimpleDateFormat("hh:mm:ss");

        Calendar cal = Calendar.getInstance();
        currentDate.setText(dateFormat.format(cal.getTime()));

        tvDate.setText(dateFormat2.format(cal.getTime()));
        tvTime.setText(dateFormat3.format(cal.getTime()));

        if (dateFormatForPosting != null)
            selectedDateForPostingToService = dateFormatForPosting.format(cal.getTime());
        selectedTimeForPostingToService = dateFormat3.format(cal.getTime());
    }
}

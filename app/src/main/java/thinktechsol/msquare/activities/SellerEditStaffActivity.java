package thinktechsol.msquare.activities;


import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.util.Log;
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
import android.widget.TimePicker;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import thinktechsol.msquare.R;
import thinktechsol.msquare.adapter.ViewStaffListAdapter;
import thinktechsol.msquare.globels.globels;
import thinktechsol.msquare.model.Buyer.BuyerWishListModel;
import thinktechsol.msquare.model.GetSellerStaffModel;
import thinktechsol.msquare.services.AddSellerStaffService;
import thinktechsol.msquare.services.GetSellerStaffService;
import thinktechsol.msquare.services.SellerUpdateStaffService;
import thinktechsol.msquare.utils.Constant;

public class SellerEditStaffActivity extends Activity implements TimePickerDialog.OnTimeSetListener {

    ListView listView;
    ArrayList<BuyerWishListModel> productList;
    RelativeLayout titlebarlayout;
    TextView title;
    ImageView backBtn, btn_menu;
    Dialog addStaffDialog;
    String TimeClickedView;
    EditText et_staff_name;
    TextView lbl_start_timing, lbl_end_timing, start_timing, end_timing;
    String fromTime, toTime;
    int SellerFromTime = 0, SellerToTime = 0, staffFromTimeInMin, staffToTimeInMin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_seller_edit_staff);

//        String SellerFromTime = Constant.sellerDetailsObj.fromTime;

        if (Constant.sellerDetailsObj.fromTime != null || Constant.sellerDetailsObj.fromTime.length() > 0)
            SellerFromTime = timeToMinConversion(Constant.sellerDetailsObj.fromTime);

        if (Constant.sellerDetailsObj.toTime != null || Constant.sellerDetailsObj.toTime.length() > 0)
            SellerToTime = timeToMinConversion(Constant.sellerDetailsObj.toTime);

        showPopUp();
//        String str_date="11-June-07";
//        DateFormat formatter = new SimpleDateFormat("E MMM dd HH:mm:ss zzz yyyy");
//        Date date = formatter.parse(str_date);

        String expectedPattern = "HH:mm:ss";
        SimpleDateFormat formatter = new SimpleDateFormat(expectedPattern);
        try {
            String userInput = "HH:mm:ss";
            Date date = formatter.parse(userInput);
            Log.e("SellerEditStaff", "From Time=" + userInput);
            System.out.println(date);
        } catch (ParseException e) {
            Log.e("SellerEditStaff", "excep From Time=" + e);
            e.printStackTrace();
        }

//        Constant.singleStaff
        titlebarlayout = (RelativeLayout) findViewById(R.id.titlebarlayout);
        title = (TextView) findViewById(R.id.title);

        title.setTextColor(Color.BLACK);
        backBtn = (ImageView) findViewById(R.id.backBtn);
        btn_menu = (ImageView) findViewById(R.id.btn_menu);

        // title bar
        backBtn.setLayoutParams(AppLayoutParam(3f, 16f, 2, 0, 0, 0, null, "ver", 0, "null"));
        btn_menu.setLayoutParams(AppLayoutParam(4f, 16f, 0, 0, 2, 0, null, "ver", 0, "right"));

        backBtn.setImageResource(android.R.color.transparent);
        backBtn.setBackgroundResource(R.drawable.back_btn_blue);

        btn_menu.setVisibility(View.VISIBLE);
        btn_menu.setBackgroundResource(R.drawable.savebtn);

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        btn_menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                if (Constant.sellerDetailsObj.fromTime)
                new SellerUpdateStaffService(SellerEditStaffActivity.this, SellerEditStaffActivity.this, Constant.singleStaff.id, fromTime, toTime, et_staff_name.getText().toString());
            }
        });
        title.setText("Edit Staff");
        titlebarlayout.setBackgroundColor(this.getResources().getColor(R.color.color_userLayout));
        titlebarlayout.setLayoutParams(AppLayoutParam(10.00f, 100f, 0, 0, 0, 0, null, "hor", 0, "null"));

//        Constant.singleStaff

        et_staff_name = (EditText) findViewById(R.id.et_staff_name);
        lbl_start_timing = (TextView) findViewById(R.id.lbl_start_timing);
        lbl_end_timing = (TextView) findViewById(R.id.lbl_end_timing);

        start_timing = (TextView) findViewById(R.id.start_timing);
        end_timing = (TextView) findViewById(R.id.end_timing);

        if (Constant.singleStaff.fromTime == null || Constant.singleStaff.fromTime.length() == 0)
            start_timing.setText("00:00:00");
        else
            start_timing.setText("" + ChangeMyTimeToAmPm(Constant.singleStaff.fromTime));

        if (Constant.singleStaff.fromTime == null || Constant.singleStaff.fromTime.length() == 0)
            end_timing.setText("00:00:00");
        else
            end_timing.setText("" + ChangeMyTimeToAmPm(Constant.singleStaff.toTime));

        et_staff_name.setText("" + Constant.singleStaff.name);

        lbl_start_timing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TimeClickedView = "startTimeTv";
                show_time_picker(lbl_start_timing);
            }
        });

        lbl_end_timing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TimeClickedView = "endTimeTv";
                show_time_picker(lbl_end_timing);
            }
        });
//        listView = (ListView) findViewById(R.id.list);
//        adapter = new BuyerViewStaffAdapter(BuyerViewStaffActivity.this, android.R.layout.simple_list_item_1, list);


    }

    public void show_time_picker(View v) {

        final Calendar c = Calendar.getInstance();
        int hour = c.get(Calendar.HOUR_OF_DAY);
        int minute = c.get(Calendar.MINUTE);

        TimePickerDialog tpd = new TimePickerDialog(this, AlertDialog.THEME_DEVICE_DEFAULT_DARK
                , this, hour, minute, DateFormat.is24HourFormat(this));

//        tpd.setMaxTime(6, 59, 0);
//        tpd.setMinTime(0, 10, 0);
//        tpd.setStartTime(0, 15);
        tpd.show();
    }

    int ConvertedSelectedTimeToMinuteStartTime, ConvertedSelectedTimeToMinuteEndTime;

    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        if (TimeClickedView.equals("startTimeTv")) {
//            Toast.makeText(getApplicationContext(), "new time:" + hourOfDay + "-" + minute, Toast.LENGTH_LONG).show();
            fromTime = hourOfDay + ":" + minute + ":00";
            staffFromTimeInMin = hourOfDay * 60 + minute;
            if (SellerFromTime != 0 && staffFromTimeInMin >= SellerFromTime && staffFromTimeInMin <= SellerToTime) {
                boolean isPM = (hourOfDay >= 12);
                start_timing.setText(String.format("%02d:%02d %s", (hourOfDay == 12 || hourOfDay == 0) ? 12 : hourOfDay % 12, minute, isPM ? "PM" : "AM"));
            } else {
                timeNotMatchedAlert.show();
            }

        } else {

            toTime = hourOfDay + ":" + minute + ":00";
            staffToTimeInMin = hourOfDay * 60 + minute;
            if (SellerToTime != 0 && staffToTimeInMin >= SellerFromTime && staffToTimeInMin <= SellerToTime) {
                boolean isPM = (hourOfDay >= 12);
                end_timing.setText(String.format("%02d:%02d %s", (hourOfDay == 12 || hourOfDay == 0) ? 12 : hourOfDay % 12, minute, isPM ? "PM" : "AM"));
            } else {
                timeNotMatchedAlert.show();
            }
        }
    }

    public int timeToMinConversion(String time) {
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss",
                Locale.ENGLISH);
        try {
            Date parsedDate = sdf.parse(time);

            int h = parsedDate.getHours();
            int m = parsedDate.getMinutes();
            int timeInMin = h * 60 + m;
            Log.e("SellerEditStaff", "From Time startRangeMin=" + timeInMin);

            return timeInMin;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return 0;
    }
//    public void fillProductListWithData(ArrayList<GetSellerStaffModel> StaffDetails) {
//        productList = new ArrayList<BuyerWishListModel>();
//
//        Log.e("StaffDetails", "StaffDetails size is=" + StaffDetails.size());
//
////        ArrayList<GetSellerStaffModel> list = new ArrayList<GetSellerStaffModel>();
////        for (int i = 0; i < StaffDetails.size(); i++) {
////            list.add(StaffDetails.get(i).name);
////        }
//
////        ViewStaffListAdapter m_adapter = new ViewStaffListAdapter(SellerEditStaffActivity.this, SellerEditStaffActivity.this, R.layout.view_staff_list_item, StaffDetails);
////        listView.setAdapter(m_adapter);
////        ArrayAdapter<String> adapter = new ArrayAdapter<String>(SellerViewStaffActivity.this, android.R.layout.simple_list_item_1, StaffDetails);
////        listView.setAdapter(adapter);
//
//    }

//    public void onStaffAdded(boolean isAdded) {
//        if (isAdded) {
//            addStaffDialog.dismiss();
//            new GetSellerStaffService(this, this, globels.getGlobelRef().sellerLoginId);
//            listView.invalidate();
//        }
//    }

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

    AlertDialog timeNotMatchedAlert;


    public void showPopUp() {
        AlertDialog.Builder builder = new AlertDialog.Builder(SellerEditStaffActivity.this);
        builder.setMessage("Plesae Select Time Between " + Constant.sellerDetailsObj.fromTime + " & " + Constant.sellerDetailsObj.toTime);
        builder.setTitle("Alert");
        builder.setIcon(android.R.drawable.ic_dialog_alert);

        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        });
        timeNotMatchedAlert = builder.create();
        timeNotMatchedAlert.setCancelable(false);
    }

    public String ChangeMyTimeToAmPm(String time) {
//        String input = "2014-04-25 17:03:13";
        String input = time;
        SimpleDateFormat inputFormat = new SimpleDateFormat("HH:mm:ss");
        SimpleDateFormat outputFormat = new SimpleDateFormat("KK:mm a");
//        System.out.println(outputFormat.format(inputFormat.parse(input)));
        try {
//            Log.e("SellerEditStaffAct", "changed time formater=" + outputFormat.format(inputFormat.parse(input)));
            return outputFormat.format(inputFormat.parse(input));
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return null;
    }
}

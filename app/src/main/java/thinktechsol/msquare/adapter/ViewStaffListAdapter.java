package thinktechsol.msquare.adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import thinktechsol.msquare.R;
import thinktechsol.msquare.activities.SellerEditStaffActivity;
import thinktechsol.msquare.activities.SellerViewProActivity;
import thinktechsol.msquare.activities.SellerViewStaffActivity;
import thinktechsol.msquare.activities.ViewSellProDetailActivity;
import thinktechsol.msquare.fragments.Fragment_2_items;
import thinktechsol.msquare.interfaceMine.subItemClick;
import thinktechsol.msquare.model.GetSellerStaffModel;
import thinktechsol.msquare.model.Response.getSellerProductsResponse;
import thinktechsol.msquare.services.SellerUpdateStaffService;
import thinktechsol.msquare.utils.Constant;

//import com.daimajia.swipe.SwipeLayout;

/**
 * Created by Arshad.Iqbal on 2/28/2016.
 */
public class ViewStaffListAdapter extends ArrayAdapter<GetSellerStaffModel> {

    private static final int _row = 0;
    private static final String TAG = "ViewProductListAdapter";


    Fragment_2_items TwoItemsFrag;
    SellerViewStaffActivity ActivityContext;

    LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    Context context;
    subItemClick click;
    private ArrayList<GetSellerStaffModel> staffList;
    private ArrayList<String> imgLoadedIds;


    public ViewStaffListAdapter(Context context, SellerViewStaffActivity ActivityContext, int textViewResourceId, ArrayList<GetSellerStaffModel> staffList) {
        super(context, textViewResourceId, staffList);
        this.staffList = staffList;
        this.context = context;
        this.ActivityContext = ActivityContext;
        imgLoadedIds = new ArrayList<String>();

//        Log.e(TAG, "inside the view product adapter class=" + staffList.size());
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public int getItemViewType(int position) {
        return 0;
    }

    public View getView(final int position, View convertView, ViewGroup parent) {
        int viewType = this.getItemViewType(position);
        try {
            switch (viewType) {
                case _row:
                    final Type1Holder holder1;
                    View v = convertView;
                    if (v == null) {
                        LayoutInflater vi = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                        v = vi.inflate(R.layout.view_staff_list_item, parent, false);

                        holder1 = new Type1Holder();

                        //holder1.lbl = (ImageView) v.findViewById(R.id.lbl);
                        holder1.staffName = (TextView) v.findViewById(R.id.staffName);
                        //holder1.status = (TextView) v.findViewById(R.id.status);
                        holder1.isEnabled = (CheckBox) v.findViewById(R.id.isEnabled);

                        v.setTag(holder1);
                    } else {
                        holder1 = (Type1Holder) v.getTag();
                    }

                    v.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            GetSellerStaffModel myItem = staffList.get(position);

                            Constant.singleStaff = myItem;
//                            Toast.makeText(context, "" + myItem.id + ": " + myItem, Toast.LENGTH_SHORT).show();
                            Intent viewProductDetails = new Intent(context, SellerEditStaffActivity.class);
                            context.startActivity(viewProductDetails);
                        }
                    });

                    final GetSellerStaffModel myItem = staffList.get(position);

                    if (myItem != null) {


                        Log.e(TAG, "imy item is not null=" + myItem.status);

                        if (holder1.staffName != null) {
                            holder1.staffName.setText(myItem.name);
                        }

                        if (holder1.isEnabled != null) {
                            Log.e("ViewStaffList", "status of staff=" + myItem.status);
                            if (myItem.status.equals("1"))
                                holder1.isEnabled.setChecked(true);
                            else
                                holder1.isEnabled.setChecked(false);
                        }

                        holder1.isEnabled.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                            @Override
                            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                                if (isChecked)
                                    new SellerUpdateStaffService(ActivityContext, ActivityContext, myItem.id, "1");
                                else
                                    new SellerUpdateStaffService(ActivityContext, ActivityContext, myItem.id, "0");
                            }
                        });
                    }
                    return v;
                default:
                    return null;
            }
        } catch (Exception e) {
            Log.e("SellerDashBoardActivity", "ItemAdapter=" + e);
            return null;
        }
    }


    public RelativeLayout.LayoutParams AppLayoutParam(float height, float width, float mL, float mT, float mR, float mB, View below) {
        RelativeLayout.LayoutParams paramName = new RelativeLayout.LayoutParams(
                getSize("w", width),
                getSize("h", height));
        paramName.addRule(RelativeLayout.CENTER_VERTICAL, RelativeLayout.TRUE);
//        if (below != null)
//            paramName.addRule(RelativeLayout.BELOW, below.getId());
        paramName.setMargins(getSize("h", mL), getSize("h", mT), getSize("h", mR), getSize("h", mB));
        return paramName;
    }

    public LinearLayout.LayoutParams AppLayoutParamSubItems(float height, float width, float mL, float mT, float mR, float mB, View below) {
        LinearLayout.LayoutParams paramName = new LinearLayout.LayoutParams(
                getSize("w", width),
                getSize("h", height));
//        paramName.addRule(RelativeLayout.CENTER_HORIZONTAL, RelativeLayout.TRUE);
//        if (below != null)
//            paramName.addRule(RelativeLayout.BELOW, below.getId());
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

    public static class Type1Holder {
        public TextView staffName;
        //        public TextView status;
        public CheckBox isEnabled;
    }
}

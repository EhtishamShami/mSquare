package thinktechsol.msquare.adapter;

import android.content.Context;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.LayerDrawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import thinktechsol.msquare.R;
import thinktechsol.msquare.activities.buyer.BuyerReservationActivity;
import thinktechsol.msquare.fragments.Buyer.SellersServiceFragment;
import thinktechsol.msquare.fragments.Fragment_2_items;
import thinktechsol.msquare.model.Buyer.TimeListItemModel;
import thinktechsol.msquare.model.Buyer.getServiceSellersProductModel;
import thinktechsol.msquare.utils.Constant;

//import com.daimajia.swipe.SwipeLayout;

/**
 * Created by Arshad.Iqbal on 2/28/2016.
 */
public class TimeListAdapter extends ArrayAdapter<TimeListItemModel> {

    private static final int _row = 0;
    private static final String TAG = "TimeListAdapter";
    int itemCheckCounter = 0;


    Fragment_2_items TwoItemsFrag;
    BuyerReservationActivity ActivityContext;

    LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    Context context;
    private ArrayList<TimeListItemModel> list;


    public TimeListAdapter(Context context, BuyerReservationActivity ActivityContext, int textViewResourceId, ArrayList<TimeListItemModel> list) {
        super(context, textViewResourceId, list);
        this.list = list;
        this.context = context;
        this.ActivityContext = ActivityContext;
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
                    final ViewHolder holder;
                    View v = convertView;
                    if (v == null) {
                        LayoutInflater vi = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                        v = vi.inflate(R.layout.time_list_adapter_item, parent, false);

                        holder = new ViewHolder();

                        holder.timeString = (TextView) v.findViewById(R.id.timeString);

                        v.setTag(holder);
                    } else {
                        holder = (ViewHolder) v.getTag();
                    }
                    final TimeListItemModel myItem = list.get(position);
                    v.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
//                            Intent serviceSellerActivity=new Intent(context, ServiceSellerDetailActivity.class);
//                            context.startActivity(serviceSellerActivity);
                            //Toast.makeText(context, "hi=" + myItem.products.get(0).id + "&" + myItem.products.get(0).sellerId, Toast.LENGTH_SHORT).show();
                        }
                    });


                    if (myItem != null) {


                        if (holder.timeString != null) {
                            holder.timeString.setText(myItem.time);
                        }
                    }
                    return v;
                default:
                    return null;
            }
        } catch (Exception e) {
            Log.e("TimeListAdapter", "Exection in TimeListAdapter=" + e);
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

    public static class ViewHolder {
        public TextView timeString;
    }
}

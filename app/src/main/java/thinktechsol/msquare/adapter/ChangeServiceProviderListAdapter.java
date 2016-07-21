package thinktechsol.msquare.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import thinktechsol.msquare.R;
import thinktechsol.msquare.activities.buyer.BuyerReservationActivity;
import thinktechsol.msquare.fragments.Fragment_2_items;
import thinktechsol.msquare.model.Buyer.ChangeServiceProviderListItemModel;
import thinktechsol.msquare.model.Buyer.TimeListItemModel;
import thinktechsol.msquare.utils.Constant;

//import com.daimajia.swipe.SwipeLayout;

/**
 * Created by Arshad.Iqbal on 2/28/2016.
 */
public class ChangeServiceProviderListAdapter extends ArrayAdapter<ChangeServiceProviderListItemModel> {

    private static final int _row = 0;
    private static final String TAG = "TimeListAdapter";
    int itemCheckCounter = 0;


    Fragment_2_items TwoItemsFrag;
    BuyerReservationActivity ActivityContext;

    LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    Context context;
    private ArrayList<ChangeServiceProviderListItemModel> list;


    public ChangeServiceProviderListAdapter(Context context, BuyerReservationActivity ActivityContext, int textViewResourceId, ArrayList<ChangeServiceProviderListItemModel> list) {
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
                        v = vi.inflate(R.layout.change_service_provider_list_adapter_item, parent, false);

                        holder = new ViewHolder();

                        holder.name = (TextView) v.findViewById(R.id.name);
                        holder.img = (ImageView) v.findViewById(R.id.img);

                        v.setTag(holder);
                    } else {
                        holder = (ViewHolder) v.getTag();
                    }
                    final ChangeServiceProviderListItemModel myItem = list.get(position);
                    v.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
//                            Intent serviceSellerActivity=new Intent(context, ServiceSellerDetailActivity.class);
//                            context.startActivity(serviceSellerActivity);
                            if (myItem.name != "" && myItem.name != " ")
                                ActivityContext.changeServiceProviderUser(myItem.name);
//                            Toast.makeText(context, "hi=" +myItem.name , Toast.LENGTH_SHORT).show();
                        }
                    });


                    if (myItem != null) {


                        if (holder.name != null) {
                            holder.name.setText(myItem.name);
                        }

                        if (myItem.imgSrc != 0) {
//                            holder.img.setVisibility(View.INVISIBLE);
                            Picasso.with(context).load(myItem.imgSrc).into(holder.img);
//                            holder.img.setBackgroundColor(context.getResources().getColor(myItem.imgSrc));
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
        public TextView name;
        public ImageView img;
    }
}

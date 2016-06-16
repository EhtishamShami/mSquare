package thinktechsol.msquare.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;

import thinktechsol.msquare.R;
import thinktechsol.msquare.activities.AddProActivity;
import thinktechsol.msquare.fragments.Fragment_2_items;
import thinktechsol.msquare.interfaceMine.subItemClick;
import thinktechsol.msquare.model.Response.getSellerProductsResponse;
import thinktechsol.msquare.model.ViewProductItem;
import thinktechsol.msquare.utils.Constant;

//import com.daimajia.swipe.SwipeLayout;

/**
 * Created by Arshad.Iqbal on 2/28/2016.
 */
public class ViewProductListAdapter extends ArrayAdapter<getSellerProductsResponse> {

    private static final int _row = 0;

    Fragment_2_items TwoItemsFrag;
    AddProActivity ActivityContext;

    LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    Context context;
    subItemClick click;
    private ArrayList<getSellerProductsResponse> productList;


    public ViewProductListAdapter(Context context, AddProActivity ActivityContext, int textViewResourceId, ArrayList<getSellerProductsResponse> productList) {
        super(context, textViewResourceId, productList);
        this.productList = productList;
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
                    final Type1Holder holder1;
                    View v = convertView;
                    if (v == null) {
                        LayoutInflater vi = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                        v = vi.inflate(R.layout.view_product_list_item, parent, false);

                        holder1 = new Type1Holder();

                        holder1.lbl = (ImageView) v.findViewById(R.id.lbl);
                        holder1.productName = (TextView) v.findViewById(R.id.pro_name);
                        holder1.status = (TextView) v.findViewById(R.id.status);
                        holder1.isSelected = (CheckBox) v.findViewById(R.id.isSelected);

                        v.setTag(holder1);
                    } else {
                        holder1 = (Type1Holder) v.getTag();
                    }

                    v.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
//                            if (holder1.btmWraper.getVisibility() == View.VISIBLE) {
//                                holder1.btmWraper.setVisibility(View.INVISIBLE);
//                                holder1.counterTV.setVisibility(View.VISIBLE);
//                            } else if (holder1.btmWraper.getVisibility() == View.INVISIBLE) {
//                                holder1.btmWraper.setVisibility(View.VISIBLE);
//                                holder1.counterTV.setVisibility(View.INVISIBLE);
//                            }
                        }
                    });

                    getSellerProductsResponse myItem = productList.get(position);

                    if (myItem != null) {
                        if (holder1.lbl != null) {
//                            holder1.lbl.setBackgroundResource(myItem.imgUrl);
//                            holder1.lbl.setLayoutParams(AppLayoutParamSubItems(swiperSubItemHeight, swiperSubItemWidth, 0, 0, 0, 0, null));
                        }

                        if (holder1.productName != null) {
                            holder1.productName.setText(myItem.title);
                        }
                        if (holder1.status != null) {
                            holder1.status.setText(myItem.price);
                        }
                        if (holder1.isSelected != null) {
                            holder1.isSelected.setChecked(true);
//                            holder1.isSelected.setChecked(myItem.isSelected);
//                            holder1.isSelected.setLayoutParams(AppLayoutParam(5.50f, 18.91f, 5, 0, 0, 0, null));
                        }

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
        public ImageView lbl;
        public TextView productName;
        public TextView status;
        public CheckBox isSelected;
    }
}

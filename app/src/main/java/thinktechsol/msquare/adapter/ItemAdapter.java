package thinktechsol.msquare.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.daimajia.swipe.SwipeLayout;

import java.util.ArrayList;

import thinktechsol.msquare.R;
import thinktechsol.msquare.model.Item;
import thinktechsol.msquare.utils.Constant;

//import com.daimajia.swipe.SwipeLayout;

/**
 * Created by Arshad.Iqbal on 2/28/2016.
 */
public class ItemAdapter extends ArrayAdapter<Item> {

    private static final int Message = 0;
    private static final int Customer = 1;
    private static final int Order = 2;
    private static final int Product = 3;
    private static final int Layout_2_sub_items = 0;
    private static final int Layout_4_sub_items = 1;

    private static int rowHeight = 80 / 4;
    private static float swiperSubItemWidth = 10.66f;
    private static float swiperSubItemHeight = 8.5f;

    LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    Context context;
    private ArrayList<Item> objects;


    public ItemAdapter(Context context, int textViewResourceId, ArrayList<Item> objects) {
        super(context, textViewResourceId, objects);
        this.objects = objects;
        this.context = context;
    }

    @Override
    public int getViewTypeCount() {
        return 2;
    }

    @Override
    public int getItemViewType(int position) {

        if (position == Message) {
            return Layout_2_sub_items;
        } else if (position == Customer) {
            return Layout_2_sub_items;
        } else if (position == Order) {
            return Layout_4_sub_items;
        } else if (position == Product) {
            return Layout_2_sub_items;
        }
        return 0;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        int viewType = this.getItemViewType(position);

        try {
            switch (viewType) {
                case Layout_2_sub_items:
                    Type1Holder holder1;
                    SwipeLayout swipeLayout = null;
                    View v = convertView;
                    if (v == null) {
                        LayoutInflater vi = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                        v = vi.inflate(R.layout.dashboard_row_item1, parent, false);


                        holder1 = new Type1Holder();
                        holder1.swipeLayout = (SwipeLayout) v.findViewById(R.id.simple1);
                        holder1.bglayout = (RelativeLayout) v.findViewById(R.id.bglayout);
                        holder1.lbl = (ImageView) v.findViewById(R.id.lbl);
                        holder1.lbl_txt = (TextView) v.findViewById(R.id.lbl_txt);
                        holder1.counterTV = (TextView) v.findViewById(R.id.counterTV);

                        //hidden items which shows on swipe
                        holder1.subItem1 = (ImageView) v.findViewById(R.id.subItem1);
                        holder1.subItem2 = (ImageView) v.findViewById(R.id.subItem2);

                        v.setTag(holder1);
                    } else {
                        holder1 = (Type1Holder) v.getTag();
                    }

                    Item myItem = objects.get(position);

                    if (myItem != null) {
                        if (holder1.counterTV != null) {
//                        holder1.counterTV.setText(myItem.counter);
                        }
                        if (holder1.lbl_txt != null) {
                            holder1.lbl_txt.setText(myItem.label);
                        }
                        if (holder1.lbl != null) {
                            holder1.lbl.setLayoutParams(AppLayoutParam(9.625f, 19.79f, 5, 0, 0, 0, null));
                            holder1.lbl.setBackgroundResource(myItem.icon);

                        }
                        if (holder1.bglayout != null) {
                            holder1.bglayout.setBackgroundColor(context.getResources().getColor(myItem.bgColor));
                            holder1.bglayout.setLayoutParams(AppLayoutParam(rowHeight, 100, 0, 0, 0, 0, null));
                        }

                        if (holder1.subItem1 != null) {
                            holder1.subItem1.setBackgroundResource(myItem.subItemIcon1);
                            holder1.subItem1.setLayoutParams(AppLayoutParamSubItems(swiperSubItemHeight, swiperSubItemWidth, 0, 0, 0, 0, null));
                        }
                        if (holder1.subItem2 != null) {
                            holder1.subItem2.setBackgroundResource(myItem.subItemIcon2);
                            holder1.subItem2.setLayoutParams(AppLayoutParamSubItems(swiperSubItemHeight, swiperSubItemWidth, 0, 0, 0, 0, null));
                        }
                    }
                    return v;
//

                case Layout_4_sub_items:
                    Type2Holder holder2;

                    View v2 = convertView;
                    if (v2 == null) {
                        LayoutInflater vi = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                        v2 = vi.inflate(R.layout.dashboard_row_item2, parent, false);

                        holder2 = new Type2Holder();
                        holder2.bglayout = (RelativeLayout) v2.findViewById(R.id.bglayout);
                        holder2.lbl = (ImageView) v2.findViewById(R.id.lbl);
                        holder2.lbl_txt = (TextView) v2.findViewById(R.id.lbl_txt);
                        holder2.counterTV = (TextView) v2.findViewById(R.id.counterTV);

                        //hidden items which shows on swipe
                        holder2.subItem1 = (ImageView) v2.findViewById(R.id.subItem1);
                        holder2.subItem2 = (ImageView) v2.findViewById(R.id.subItem2);
                        holder2.subItem3 = (ImageView) v2.findViewById(R.id.subItem3);
                        holder2.subItem4 = (ImageView) v2.findViewById(R.id.subItem4);

                        v2.setTag(holder2);
                    } else {
                        holder2 = (Type2Holder) v2.getTag();
                    }
                    Item myItem3 = objects.get(position);


                    if (holder2 != null) {
                        if (holder2.counterTV != null) {
//                        holder3.counterTV.setText(myItem3.counter);
                        }
                        if (holder2.lbl_txt != null) {
                            holder2.lbl_txt.setText(myItem3.label);
                        }
                        if (holder2.lbl != null) {
                            holder2.lbl.setLayoutParams(AppLayoutParam(9.625f, 19.79f, 5, 0, 0, 0, null));
                            holder2.lbl.setBackgroundResource(myItem3.icon);
                        }
                        if (holder2.bglayout != null) {
                            holder2.bglayout.setBackgroundColor(context.getResources().getColor(myItem3.bgColor));
                            holder2.bglayout.setLayoutParams(AppLayoutParam(rowHeight, 100, 0, 0, 0, 0, null));
                        }

                        if (holder2.subItem1 != null) {
                            holder2.subItem1.setBackgroundResource(myItem3.subItemIcon1);
                            holder2.subItem1.setLayoutParams(AppLayoutParamSubItems(swiperSubItemHeight, swiperSubItemWidth, 0, 0, 0, 0, null));
                        }
                        if (holder2.subItem2 != null) {
                            holder2.subItem2.setBackgroundResource(myItem3.subItemIcon2);
                            holder2.subItem2.setLayoutParams(AppLayoutParamSubItems(swiperSubItemHeight, swiperSubItemWidth, 0, 0, 0, 0, null));
                        }
                        if (holder2.subItem3 != null) {
                            holder2.subItem3.setBackgroundResource(myItem3.subItemIcon3);
                            holder2.subItem3.setLayoutParams(AppLayoutParamSubItems(swiperSubItemHeight, swiperSubItemWidth, 0, 0, 0, 0, null));
                        }
                        if (holder2.subItem4 != null) {
                            holder2.subItem4.setBackgroundResource(myItem3.subItemIcon4);
                            holder2.subItem4.setLayoutParams(AppLayoutParamSubItems(swiperSubItemHeight, swiperSubItemWidth, 0, 0, 0, 0, null));
                        }
                    }
                    return v2;
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
        paramName.addRule(RelativeLayout.CENTER_HORIZONTAL, RelativeLayout.TRUE);
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
        public SwipeLayout swipeLayout;
        public RelativeLayout bglayout;
        public ImageView lbl;
        public TextView lbl_txt;
        public TextView counterTV;
        public ImageView subItem1;
        public ImageView subItem2;
    }

    public static class Type2Holder {
        public RelativeLayout bglayout;
        public ImageView lbl;
        public TextView lbl_txt;
        public TextView counterTV;
        public ImageView subItem1;
        public ImageView subItem2;
        public ImageView subItem3;
        public ImageView subItem4;
    }
}

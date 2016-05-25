package thinktechsol.msquare.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;

import thinktechsol.msquare.R;
import thinktechsol.msquare.model.Item;
import thinktechsol.msquare.utils.Constant;

/**
 * Created by Arshad.Iqbal on 2/28/2016.
 */
public class ItemAdapter2 extends ArrayAdapter<Item> {

    private static final int Message = 0;
    private static final int Customer = 1;
    private static final int Order = 2;
    private static final int Product = 3;
    LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    Context context;
    private ArrayList<Item> objects;


    public ItemAdapter2(Context context, int textViewResourceId, ArrayList<Item> objects) {
        super(context, textViewResourceId, objects);
        this.objects = objects;
        this.context = context;
    }


    @Override
    public int getItemViewType(int position) {

        if (position == 0) {
            return Message;
        } else if (position == 1) {
            return Customer;
        } else if (position == 2) {
            return Order;
        } else if (position == 3) {
            return Product;
        }
        return 0;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        int viewType = this.getItemViewType(position);

        switch (viewType) {
            case Message:
                Type1Holder holder1;
                View v = convertView;
                if (v == null) {
                    LayoutInflater vi = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                    v = vi.inflate(R.layout.dashboard_1st_row, parent, false);

                    holder1 = new Type1Holder();
                    holder1.bglayout = (RelativeLayout) v.findViewById(R.id.bglayout);
//                    holder1.bglayout.setBackgroundColor(context.getResources().getColor(R.color.messageColor));
                    holder1.lbl = (ImageView) v.findViewById(R.id.lbl);
//                    holder1.lbl.setBackgroundResource(R.drawable.messages);
                    holder1.lbl_txt = (TextView) v.findViewById(R.id.lbl_txt);
                    holder1.counterTV = (TextView) v.findViewById(R.id.counterTV);
//                    holder1.lbl_txt.setText(context.getResources().getString(R.string.lbl_message));

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
                        holder1.lbl.setBackgroundResource(myItem.icon);
                    }
                    if (holder1.bglayout != null) {
                        holder1.bglayout.setBackgroundColor(context.getResources().getColor(myItem.bgColor));
                    }
                }
                return v;
            case Customer:
                Type2Holder holder2;

                View v2 = convertView;
                if (v2 == null) {
                    LayoutInflater vi = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                    v2 = vi.inflate(R.layout.dashboard_1st_row, parent, false);

                    holder2 = new Type2Holder();
                    holder2.bglayout = (RelativeLayout) v2.findViewById(R.id.bglayout);
                    holder2.lbl = (ImageView) v2.findViewById(R.id.lbl);
                    holder2.lbl_txt = (TextView) v2.findViewById(R.id.lbl_txt);
                    holder2.counterTV = (TextView) v2.findViewById(R.id.counterTV);

                    v2.setTag(holder2);
                } else {
                    holder2 = (Type2Holder) v2.getTag();
                }
                Item myItem2 = objects.get(position);

                if (myItem2 != null) {
                    if (holder2.counterTV != null) {
//                        holder2.counterTV.setText(myItem2.counter);
                    }
                    if (holder2.lbl_txt != null) {
                        holder2.lbl_txt.setText(myItem2.label);
                    }
                    if (holder2.lbl != null) {
                        holder2.lbl.setBackgroundResource(myItem2.icon);
                    }
                    if (holder2.bglayout != null) {
                        holder2.bglayout.setBackgroundColor(context.getResources().getColor(myItem2.bgColor));
                    }
                }

                return v2;

            case Order:
                Type2Holder holder3;

                View v3 = convertView;
                if (v3 == null) {
                    LayoutInflater vi = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                    v3 = vi.inflate(R.layout.dashboard_2nd_row, parent, false);

                    holder3 = new Type2Holder();
                    holder3.bglayout = (RelativeLayout) v3.findViewById(R.id.bglayout);
                    holder3.lbl = (ImageView) v3.findViewById(R.id.lbl);
//                    holder3.lbl.setBackgroundResource(R.drawable.order);
                    holder3.lbl_txt = (TextView) v3.findViewById(R.id.lbl_txt);
                    holder3.counterTV = (TextView) v3.findViewById(R.id.counterTV);

                    v3.setTag(holder3);
                } else {
                    holder3 = (Type2Holder) v3.getTag();
                }
                Item myItem3 = objects.get(position);


                if (holder3 != null) {
                    if (holder3.counterTV != null) {
//                        holder3.counterTV.setText(myItem3.counter);
                    }
                    if (holder3.lbl_txt != null) {
                        holder3.lbl_txt.setText(myItem3.label);
                    }
                    if (holder3.lbl != null) {
                        holder3.lbl.setBackgroundResource(myItem3.icon);
                    }
                    if (holder3.bglayout != null) {
                        holder3.bglayout.setBackgroundColor(context.getResources().getColor(myItem3.bgColor));
                    }
                }

                return v3;
            case Product:
                Type2Holder holder4 = null;

                View v4 = convertView;
                if (v4 == null) {
                    LayoutInflater vi = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                    v4 = vi.inflate(R.layout.dashboard_1st_row, parent, false);

                    holder4 = new Type2Holder();
                    holder4.bglayout = (RelativeLayout) v4.findViewById(R.id.bglayout);
                    holder4.lbl = (ImageView) v4.findViewById(R.id.lbl);
                    holder4.lbl_txt = (TextView) v4.findViewById(R.id.lbl_txt);
                    holder4.counterTV = (TextView) v4.findViewById(R.id.counterTV);

                    v4.setTag(holder4);
                } else {
                    holder4 = (Type2Holder) v4.getTag();
                }
                Item myItem4 = objects.get(position);

                if (holder4 != null) {
                    if (holder4.counterTV != null) {
                        //holder4.counterTV.setText(myItem4.counter);
                    }
                    if (holder4.lbl_txt != null) {
                        holder4.lbl_txt.setText(myItem4.label);
                    }
                    if (holder4.lbl != null) {
                        holder4.lbl.setBackgroundResource(myItem4.icon);
                    }
                    if (holder4.bglayout != null) {
                        holder4.bglayout.setBackgroundColor(context.getResources().getColor(myItem4.bgColor));
                    }
                }
                return v4;
            default:
                return null;
        }
    }

    public RelativeLayout.LayoutParams AppLayoutParam(float height, float width, float mL, float mT, float mR, float mB, View below) {
        RelativeLayout.LayoutParams paramName = new RelativeLayout.LayoutParams(
                getSize("w", width),
                getSize("h", height));
        paramName.addRule(RelativeLayout.CENTER_HORIZONTAL, RelativeLayout.TRUE);
        if (below != null)
            paramName.addRule(RelativeLayout.BELOW, below.getId());
        paramName.setMargins(getSize("h", mL), getSize("h", mT), getSize("h", mR), getSize("h", mB));
        return paramName;
    }

    public int getSize(String dimension, float size) {
        float x = 0;
        if (dimension.equals("w")) {
            x = (size / 100) * Constant.screenWidth;
        } else {
            x = (size / 100) * Constant.screenWidth;
        }
        return (int) x;
    }

    public static class Type1Holder {
        public RelativeLayout bglayout;
        public ImageView lbl;
        public TextView lbl_txt;
        public TextView counterTV;
    }

    public static class Type2Holder {
        public RelativeLayout bglayout;
        public ImageView lbl;
        public TextView lbl_txt;
        public TextView counterTV;
    }
}

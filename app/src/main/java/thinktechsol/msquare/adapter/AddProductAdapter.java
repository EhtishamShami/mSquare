package thinktechsol.msquare.adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import thinktechsol.msquare.R;
import thinktechsol.msquare.activities.AddOrViewProActivity;
import thinktechsol.msquare.fragments.SellerAddProductFragment;
import thinktechsol.msquare.globels.globels;
import thinktechsol.msquare.model.AddProductItem;
import thinktechsol.msquare.utils.Constant;


/**
 * Created by Arshad.Iqbal on 2/28/2016.
 */
public class AddProductAdapter extends ArrayAdapter<AddProductItem> {

    private static final int Layout_items = 0;

    private static int rowHeight = 80 / 4;
    SellerAddProductFragment ActivityContext;

    LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    Context context;
    private ArrayList<AddProductItem> objects;

    public AddProductAdapter(Context context, SellerAddProductFragment ActivityContext, int textViewResourceId, ArrayList<AddProductItem> objects) {
        super(context, textViewResourceId, objects);
        this.objects = objects;
        this.context = context;
        this.ActivityContext = ActivityContext;
    }

    @Override
    public int getViewTypeCount() {
        return 2;
    }

    @Override
    public int getItemViewType(int position) {

//        if (position == Message) {
//            return Layout_2_sub_items;
//        } else if (position == Customer) {
//            return Layout_2_sub_items;
//        } else if (position == Order) {
//            return Layout_4_sub_items;
//        } else if (position == Product) {
//            return Layout_2_sub_items;
//        }
        return 0;
    }

    public View getView(final int position, View convertView, ViewGroup parent) {
        int viewType = this.getItemViewType(position);

        try {
            switch (viewType) {
                case Layout_items:
                    final Type1Holder holder1;
                    View v = convertView;
                    if (v == null) {
                        LayoutInflater vi = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                        v = vi.inflate(R.layout.add_product_row_item, parent, false);

                        holder1 = new Type1Holder();

                        holder1.bglayout = (RelativeLayout) v.findViewById(R.id.bglayout);

                        holder1.item1 = (RelativeLayout) v.findViewById(R.id.item1);

                        holder1.lbl = (ImageView) v.findViewById(R.id.lbl);
                        holder1.lbl_txt = (TextView) v.findViewById(R.id.lbl_txt);


                        v.setTag(holder1);
                    } else {
                        holder1 = (Type1Holder) v.getTag();
                    }

                    final AddProductItem myItem = objects.get(position);
                    if (myItem != null) {
                        //1st item
                        if (holder1.lbl_txt != null) {
                            holder1.lbl_txt.setText(myItem.label);
                        }
                        if (holder1.lbl != null) {
                            holder1.lbl.setLayoutParams(AppLayoutParam(9.625f, 19.79f, 0, 0, 0, 0, null));
                            Picasso.with(context).load(myItem.imgUrl).into(holder1.lbl);
                        }
                        if (holder1.item1 != null) {
                            holder1.item1.setLayoutParams(AppLayoutParam2(rowHeight, 100, 0, 0, 0, 0, null));
                            holder1.item1.setBackgroundColor(context.getResources().getColor(myItem.bgColor));
                        }
                    }

                    v.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
//                            Toast.makeText(context, "" + myItem.id, Toast.LENGTH_SHORT).show();
                            globels.getGlobelRef().IdForAddProduct = myItem.id;

                            Intent add = new Intent(context, AddOrViewProActivity.class);
                            context.startActivity(add);
//                            ((Activity)context).finish();
//                            FragmentTransaction transaction = ActivityContext.getFragmentManager().beginTransaction();
//                            transaction.remove(ActivityContext).commit();
                        }
                    });

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
        paramName.addRule(RelativeLayout.CENTER_HORIZONTAL, RelativeLayout.TRUE);
        paramName.addRule(RelativeLayout.CENTER_VERTICAL, RelativeLayout.TRUE);
        paramName.setMargins(getSize("h", mL), getSize("h", mT), getSize("h", mR), getSize("h", mB));
        return paramName;
    }

    public RelativeLayout.LayoutParams AppLayoutParam2(float height, float width, float mL, float mT, float mR, float mB, View toRight) {
        RelativeLayout.LayoutParams paramName = new RelativeLayout.LayoutParams(
                getSize("w", width),
                getSize("h", height));
        paramName.addRule(RelativeLayout.CENTER_HORIZONTAL, RelativeLayout.TRUE);
        paramName.addRule(RelativeLayout.CENTER_IN_PARENT, RelativeLayout.TRUE);
//        if (toRight != null)
//            paramName.addRule(RelativeLayout.RIGHT_OF, toRight.getId());
//        paramName.setMargins(getSize("h", mL), getSize("h", mT), getSize("h", mR), getSize("h", mB));
        return paramName;
    }

    public LinearLayout.LayoutParams AppLayoutParamSubItems(float height, float width, float mL, float mT, float mR, float mB, View below) {
        LinearLayout.LayoutParams paramName = new LinearLayout.LayoutParams(
                getSize("w", width),
                getSize("h", height));
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
        public RelativeLayout bglayout;

        public RelativeLayout item1;
        public ImageView lbl;
        public TextView lbl_txt;
    }
}

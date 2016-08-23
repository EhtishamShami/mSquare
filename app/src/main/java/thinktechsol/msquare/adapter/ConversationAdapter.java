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

import java.util.ArrayList;

import thinktechsol.msquare.R;
import thinktechsol.msquare.activities.ViewSellOrderDetailActivity;
import thinktechsol.msquare.fragments.SellerDashBoardProductFragment;
import thinktechsol.msquare.interfaceMine.subItemClick;
import thinktechsol.msquare.model.Response.ConversationItem;
import thinktechsol.msquare.utils.Constant;

//import com.daimajia.swipe.SwipeLayout;

/**
 * Created by Arshad.Iqbal on 2/28/2016.
 */
public class ConversationAdapter extends ArrayAdapter<ConversationItem> {

    private static final int MyMessage = 0;
    private static final int OthersMessage = 1;

    private static final int Layout_1 = 0;
    private static final int Layout_2 = 1;

    private static int rowHeight = 10;

    ViewSellOrderDetailActivity ActivityContext;
    SellerDashBoardProductFragment fragContext;

    LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    Context context;
    subItemClick click;
    private ArrayList<ConversationItem> messageList;


    public ConversationAdapter(Context context, ViewSellOrderDetailActivity ActivityContext, int textViewResourceId, ArrayList<ConversationItem> messageList) {
        super(context, textViewResourceId, messageList);
        this.messageList = messageList;
        this.context = context;
        this.ActivityContext = ActivityContext;
        //this.fragContext = fragContext;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public int getItemViewType(int position) {

//        if (position == MyMessage) {
//            return Layout_1;
//        } else if (position == OthersMessage) {
//            return Layout_2;
//        }
        return 0;
    }

    public View getView(final int position, View convertView, ViewGroup parent) {
        int viewType = this.getItemViewType(position);

        try {
            switch (viewType) {
                case Layout_1:
                    final ViewHolderMyMessages holder1;

                    View v = convertView;
                    if (v == null) {
                        LayoutInflater vi = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                        v = vi.inflate(R.layout.my_messages_layout, parent, false);

                        holder1 = new ViewHolderMyMessages();
                        holder1.bglayout = (RelativeLayout) v.findViewById(R.id.bglayout);
                        holder1.lbl = (ImageView) v.findViewById(R.id.lbl);
                        holder1.name = (TextView) v.findViewById(R.id.name);
                        holder1.time = (TextView) v.findViewById(R.id.time);
                        holder1.message = (TextView) v.findViewById(R.id.message);

                        v.setTag(holder1);
                    } else {
                        holder1 = (ViewHolderMyMessages) v.getTag();
                    }

//                    v.setOnClickListener(new View.OnClickListener() {
//                        @Override
//                        public void onClick(View v) {
//
//                        }
//                    });

                    ConversationItem myItem = messageList.get(position);

                    if (myItem != null) {

                        if (holder1.name != null) {
                            holder1.name.setText(myItem.name);
                        }
                        if (holder1.time != null) {
                            holder1.time.setText(myItem.time);
                        }
                        if (holder1.message != null) {
                            holder1.message.setText(myItem.message);
                        }

//                        if (holder1.bglayout != null) {
//                            holder1.bglayout.setBackgroundColor(context.getResources().getColor(myItem.bgColor));
//                            holder1.bglayout.setLayoutParams(AppLayoutParam(rowHeight, 100, 0, 0, 0, 0, null));
//                        }
                    }
                    return v;
//
//                case Layout_2:
//                    final ViewHolderOthersMessages holder2;
//
//                    View v2 = convertView;
//                    if (v2 == null) {
//                        LayoutInflater vi = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//                        v2 = vi.inflate(R.layout.my_messages_layout, parent, false);
//
//                        holder2 = new ViewHolderOthersMessages();
//                        holder2.bglayout = (RelativeLayout) v2.findViewById(R.id.bglayout);
//                        holder2.icon = (ImageView) v2.findViewById(R.id.lbl);
//                        holder2.messageTxt = (TextView) v2.findViewById(R.id.lbl_txt);
//
//                        v2.setTag(holder2);
//                    } else {
//                        holder2 = (ViewHolderOthersMessages) v2.getTag();
//                    }
//
//                    v2.setOnClickListener(new View.OnClickListener() {
//                        @Override
//                        public void onClick(View v) {
//
//                        }
//                    });
//
//                    Item myItem3 = messageList.get(position);
//
//                    if (holder2 != null) {
//
//                        if (holder2.messageTxt != null) {
//                            holder2.messageTxt.setText(myItem3.label);
//                        }
//
//                        if (holder2.bglayout != null) {
//                            holder2.bglayout.setBackgroundColor(context.getResources().getColor(myItem3.bgColor));
//                            holder2.bglayout.setLayoutParams(AppLayoutParam(rowHeight, 100, 0, 0, 0, 0, null));
//                        }
//                    }
//                    return v2;
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

    public static class ViewHolderMyMessages {
        public RelativeLayout bglayout;
        public ImageView lbl;
        public TextView name;
        public TextView time;
        public TextView message;
    }

    public static class ViewHolderOthersMessages {
        public RelativeLayout bglayout;
        public ImageView lbl;
        public TextView name;
        public TextView time;
        public TextView message;
    }
}

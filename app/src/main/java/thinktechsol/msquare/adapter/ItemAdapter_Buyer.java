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
import thinktechsol.msquare.activities.buyer.BuyerDeshBoardActivity;
import thinktechsol.msquare.fragments.Buyer.BuyerDashBoardFragment;
import thinktechsol.msquare.fragments.Fragment_2_items;
import thinktechsol.msquare.interfaceMine.subItemClick;
import thinktechsol.msquare.model.Buyer.BuyerDashBoardAdapterItem;
import thinktechsol.msquare.utils.Constant;

//import com.daimajia.swipe.SwipeLayout;

/**
 * Created by Arshad.Iqbal on 2/28/2016.
 */
public class ItemAdapter_Buyer extends ArrayAdapter<BuyerDashBoardAdapterItem> {

    private static final int Order = 0;
    private static final int Message = 1;
    private static final int Wishlist = 2;
    private static final int Favourite = 3;

    private static final int Layout_2_sub_items = 0;
    private static final int Layout_4_sub_items = 1;

    private static int rowHeight = 80 / 4;
    private static float swiperSubItemWidth = 10.66f;
    private static float swiperSubItemHeight = 8.5f;
    Fragment_2_items TwoItemsFrag;
    BuyerDeshBoardActivity ActivityContext;
    BuyerDashBoardFragment fragContext;

    LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    Context context;
    subItemClick click;
    private ArrayList<BuyerDashBoardAdapterItem> objects;


    public ItemAdapter_Buyer(Context context, BuyerDeshBoardActivity ActivityContext, int textViewResourceId, ArrayList<BuyerDashBoardAdapterItem> objects, BuyerDashBoardFragment fragContext) {
        super(context, textViewResourceId, objects);
        this.objects = objects;
        this.context = context;
        this.ActivityContext = ActivityContext;
//        click = ActivityContext;
//        TwoItemsFrag = new Fragment_2_items();
        this.fragContext = fragContext;

    }

    @Override
    public int getViewTypeCount() {
        return 2;
    }

    @Override
    public int getItemViewType(int position) {

        if (position == Order) {
            return Layout_4_sub_items;
        } else if (position == Message) {
            return Layout_2_sub_items;
        } else if (position == Wishlist) {
            return Layout_2_sub_items;
        } else if (position == Favourite) {
            return Layout_2_sub_items;
        }
        return 0;
    }

    public View getView(final int position, View convertView, ViewGroup parent) {
        int viewType = this.getItemViewType(position);

        try {
            switch (viewType) {
                case Layout_2_sub_items:
                    final Type1Holder holder1;
                    SwipeLayout swipeLayout = null;
                    View v = convertView;
                    if (v == null) {
                        LayoutInflater vi = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                        v = vi.inflate(R.layout.dashboard_row_item1_test_frag, parent, false);

                        holder1 = new Type1Holder();
                        holder1.swipeLayoutt = (SwipeLayout) v.findViewById(R.id.simple1);
                        holder1.bglayout = (RelativeLayout) v.findViewById(R.id.bglayout);
                        holder1.btmWraper = (LinearLayout) v.findViewById(R.id.bottom_wrapper_2);
                        holder1.btmWraper.bringToFront();
                        holder1.btmWraper.setVisibility(View.INVISIBLE);

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

                    final BuyerDashBoardAdapterItem myItem = objects.get(position);

                    v.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (myItem.subItemIcon1 != 0) {
                                if (holder1.btmWraper.getVisibility() == View.VISIBLE) {
                                    holder1.btmWraper.setVisibility(View.INVISIBLE);
                                    holder1.counterTV.setVisibility(View.VISIBLE);
                                } else if (holder1.btmWraper.getVisibility() == View.INVISIBLE) {
                                    holder1.btmWraper.setVisibility(View.VISIBLE);
                                    holder1.counterTV.setVisibility(View.INVISIBLE);
                                }
                            } else {
//                                Toast.makeText(context, "" + position, Toast.LENGTH_SHORT).show();
//                                Toast.makeText(context, "" + holder1.counterTV, Toast.LENGTH_SHORT).show();
//                                Toast.makeText(context, "" + myItem.counter, Toast.LENGTH_SHORT).show();
//                                holder1.counterTV.setText("hello");
                                fragContext.openFragment(position, "left");
                            }
                        }
                    });

                    if (myItem != null) {
                        if (holder1.counterTV != null) {
                            holder1.counterTV.setText("" + myItem.counter);
//                            Toast.makeText(context, "" + holder1.counterTV, Toast.LENGTH_SHORT).show();
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
                            if (myItem.subItemIcon1 != 0) {
                                holder1.subItem1.setBackgroundResource(myItem.subItemIcon1);
                                holder1.subItem1.setLayoutParams(AppLayoutParamSubItems(swiperSubItemHeight, swiperSubItemWidth, 0, 0, 0, 0, null));
                            }

                        }
                        if (holder1.subItem2 != null) {
                            holder1.subItem2.setBackgroundResource(myItem.subItemIcon2);
                            holder1.subItem2.setLayoutParams(AppLayoutParamSubItems(swiperSubItemHeight, swiperSubItemWidth, 0, 0, 0, 0, null));
                        }

                        holder1.subItem1.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
//                                SellerAddProductFragment fragobj = new SellerAddProductFragment();
                                fragContext.openFragment(position, "left");
                            }
                        });


                        holder1.subItem2.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                fragContext.openFragment(position, "right");
                            }
                        });
                    }
                    return v;
//

                case Layout_4_sub_items:
                    final Type2Holder holder2;

                    View v2 = convertView;
                    if (v2 == null) {
                        LayoutInflater vi = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                        v2 = vi.inflate(R.layout.dashboard_row_item2, parent, false);

                        holder2 = new Type2Holder();
                        holder2.bglayout = (RelativeLayout) v2.findViewById(R.id.bglayout);
                        holder2.btmWraper = (LinearLayout) v2.findViewById(R.id.bottom_wrapper_2);
                        holder2.btmWraper.bringToFront();
                        holder2.btmWraper.setVisibility(View.INVISIBLE);

                        holder2.lbl = (ImageView) v2.findViewById(R.id.lbl);
                        holder2.lbl_txt = (TextView) v2.findViewById(R.id.lbl_txt);
                        holder2.counterTV = (TextView) v2.findViewById(R.id.counterTV2);

                        //hidden items which shows on swipe
                        holder2.subItem1 = (ImageView) v2.findViewById(R.id.subItem1);
                        holder2.subItem2 = (ImageView) v2.findViewById(R.id.subItem2);
                        holder2.subItem3 = (ImageView) v2.findViewById(R.id.subItem3);
                        holder2.subItem4 = (ImageView) v2.findViewById(R.id.subItem4);

                        v2.setTag(holder2);
                    } else {
                        holder2 = (Type2Holder) v2.getTag();
                    }

                    v2.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (holder2.btmWraper.getVisibility() == View.VISIBLE) {
                                holder2.btmWraper.setVisibility(View.INVISIBLE);
                                holder2.counterTV.setVisibility(View.VISIBLE);
                            } else if (holder2.btmWraper.getVisibility() == View.INVISIBLE) {
                                holder2.btmWraper.setVisibility(View.VISIBLE);
                                holder2.counterTV.setVisibility(View.INVISIBLE);
                            }
                        }
                    });

                    BuyerDashBoardAdapterItem myItem3 = objects.get(position);


                    if (holder2 != null) {
                        if (holder2.counterTV != null) {
                            holder2.counterTV.setText("" + myItem3.counter);
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


                        holder2.subItem1.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                fragContext.openFragment(position, "leftMost");
                            }
                        });
                        holder2.subItem2.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                fragContext.openFragment(position, "left");
                            }
                        });

                        holder2.subItem3.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                fragContext.openFragment(position, "right");
                            }
                        });
                        holder2.subItem4.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                fragContext.openFragment(position, "rightMost");
                            }
                        });

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
        public SwipeLayout swipeLayoutt;
        public RelativeLayout bglayout;
        public LinearLayout btmWraper;
        public ImageView lbl;
        public TextView lbl_txt;
        public TextView counterTV;
        public ImageView subItem1;
        public ImageView subItem2;
    }

    public static class Type2Holder {
        public SwipeLayout swipeLayout;
        public RelativeLayout bglayout;
        public LinearLayout btmWraper;
        public ImageView lbl;
        public TextView lbl_txt;
        public TextView counterTV;
        public ImageView subItem1;
        public ImageView subItem2;
        public ImageView subItem3;
        public ImageView subItem4;
    }
}

//package thinktechsol.msquare.adapter;
//
//import android.content.Context;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.ArrayAdapter;
//import android.widget.RelativeLayout;
//import android.widget.TextView;
//
//import java.util.ArrayList;
//
//import thinktechsol.msquare.R;
//import thinktechsol.msquare.model.Item;
//
///**
// * Created by Arshad.Iqbal on 2/28/2016.
// */
//public class ItemAdapter extends ArrayAdapter<Item> {
//
//    LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//    // declaring our ArrayList of items
//    private ArrayList<Item> objects;
//    Context context;
//
//    /* here we must override the constructor for ArrayAdapter
//    * the only variable we care about now is ArrayList<Item> objects,
//    * because it is the list of objects we want to display.;
//    */
//    public ItemAdapter(Context context, int textViewResourceId, ArrayList<Item> objects) {
//        super(context, textViewResourceId, objects);
//        this.objects = objects;
//        this.context=context;
//    }
//
//    /*
//     * we are overriding the getView method here - this is what defines how each
//     * list item will look.
//     */
//
//    @Override
//    public int getItemViewType(int position) {
//
//        if (position == 0) {
//            return 0;
//        } else if (position == 1) {
//            return 1;
//        } else if (position == 2) {
//            return 2;
//        }
//        return 3;
//    }
//    public View getView(int position, View convertView, ViewGroup parent){
//        ViewHolder holder = null;
//        // assign the view we are converting to a local variable
//        View v = convertView;
//
//
//
//
//        int type = getItemViewType(position);
//        if (convertView == null) {
//
//            holder = new ViewHolder();
//
//            switch (type) {
//                case 0: {
//                    v = inflater.inflate(R.layout.dashboard_1st_row, null);
//                    holder.textView = (TextView)convertView.findViewById(R.id.text);
//                    break;
//                }
//                case 1: {
//                    v = inflater.inflate(R.layout.dashboard_1st_row, null);
//                    break;
//                }
//                case 2: {
//                    v = inflater.inflate(R.layout.dashboard_1st_row, null);
//                    break;
//                }
//            }
//            convertView.setTag(holder);
//        } else {
//            holder = (ViewHolder)convertView.getTag();
//        }
//
//        // first check to see if the view is null. if so, we have to inflate it.
//        // to inflate it basically means to render, or show, the view.
////        if (v == null) {
////            LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
////            v = inflater.inflate(R.layout.list_item2, null);
////        }
//
//		/*
//		 * Recall that the variable position is sent in as an argument to this method.
//		 * The variable simply refers to the position of the current object in the list. (The ArrayAdapter
//		 * iterates through the list we sent it)
//		 *
//		 * Therefore, i refers to the current Item object.
//		 */
//        Item i = objects.get(position);
//
//        if (i != null) {
//
//            // This is how you obtain a reference to the TextViews.
//            // These TextViews are created in the XML files we defined.
//
//            TextView tt = (TextView) v.findViewById(R.id.tv1);
//            TextView ttd = (TextView) v.findViewById(R.id.tv2);
//            RelativeLayout bg_layout=(RelativeLayout)v.findViewById(R.id.bglayout);
//            bg_layout.setBackgroundColor(context.getResources().getColor(R.color.messageColor));
//
////            TextView mt = (TextView) v.findViewById(R.id.middletext);
////            TextView mtd = (TextView) v.findViewById(R.id.middletextdata);
////            TextView bt = (TextView) v.findViewById(R.id.bottomtext);
////            TextView btd = (TextView) v.findViewById(R.id.desctext);
//
//            // check to see if each individual textview is null.
//            // if not, assign some text!
//            if (tt != null){
//                tt.setText("Name: ");
//            }
//            if (ttd != null){
//                ttd.setText("Name: ");
//            }
////            if (mt != null){
////                mt.setText("Price: ");
////            }
////            if (mtd != null){
////                mtd.setText("$" + i.getPrice());
////            }
////            if (bt != null){
////                bt.setText("Details: ");
////            }
////            if (btd != null){
////                btd.setText(i.getDetails());
////            }
//        }
//        // the view must be returned to our activity
//        return v;
//    }
//
//    public static class ViewHolder {
//        public TextView textView;
//    }
//}

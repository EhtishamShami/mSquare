package thinktechsol.msquare.adapter;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.SimpleOnPageChangeListener;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;


import thinktechsol.msquare.R;
import thinktechsol.msquare.activities.AddProActivity;
import thinktechsol.msquare.interfaceMine.UploadImgInterface;
import thinktechsol.msquare.utils.Constant;

public class ImgSwiperAdapter extends PagerAdapter {

    private AddProActivity _activity;
    //    private ArrayList<Integer> _imagePaths;
    private LayoutInflater inflater;
    private ImageView mImaView;
    ViewPager viewPager;
    private static final int CAMERA_REQUEST = 1888;
    UploadImgInterface clicked;


    public ImgSwiperAdapter(AddProActivity activity,
                            ArrayList<Integer> imagePaths, ViewPager viewPager) {
        this._activity = activity;
        clicked = activity;
//        this._imagePaths = imagePaths;
        Bitmap dummyImg = BitmapFactory.decodeResource(_activity.getResources(), R.drawable.upload_img_bg);
        bitmapList.add(dummyImg);
        this.viewPager = viewPager;
    }

    @Override
    public int getCount() {
//        return this._imagePaths.size();
        return this.bitmapList.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == ((RelativeLayout) object);
    }

    @Override
    public Object instantiateItem(ViewGroup container, final int position) {
        inflater = (LayoutInflater) _activity
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View viewLayout = inflater.inflate(R.layout.swiper_layout, container, false);
        mImaView = (ImageView) viewLayout.findViewById(R.id.imageView1);
        mImaView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
////                _activity.startActivityForResult(cameraIntent, CAMERA_REQUEST);
//                ((Activity) _activity).startActivityForResult(cameraIntent, CAMERA_REQUEST);
                clicked.onClicked();
            }
        });
//        mImaView.setImageResource(_imagePaths.get(position));
        mImaView.setImageBitmap(bitmapList.get(position));
//        viewPager.setLayoutParams(AppLayoutParam(32.00f, 100f, 0, 0, 0, 0));
        ((ViewPager) container).addView(viewLayout);
        PageListener pageListener = new PageListener();
        viewPager.setOnPageChangeListener(pageListener);
        return viewLayout;
    }

    private class PageListener extends SimpleOnPageChangeListener {
        public void onPageSelected(int position) {
        }
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        ((ViewPager) container).removeView((RelativeLayout) object);
    }

    public ArrayList<Bitmap> bitmapList = new ArrayList<Bitmap>();

    public RelativeLayout.LayoutParams AppLayoutParam(float height, float width, float mL, float mT, float mR, float mB) {
        RelativeLayout.LayoutParams paramName = new RelativeLayout.LayoutParams(
                Constant.getSize("w", width),
                Constant.getSize("h", height));
        paramName.addRule(RelativeLayout.CENTER_HORIZONTAL, RelativeLayout.TRUE);
        paramName.setMargins(Constant.getSize("h", mL), Constant.getSize("h", mT), Constant.getSize("h", mR), Constant.getSize("h", mB));
        return paramName;
    }
}

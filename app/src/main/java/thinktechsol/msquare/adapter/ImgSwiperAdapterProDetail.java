package thinktechsol.msquare.adapter;

import android.content.ContentResolver;
import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.SimpleOnPageChangeListener;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import thinktechsol.msquare.R;
import thinktechsol.msquare.activities.ViewSellProDetailActivity;
import thinktechsol.msquare.model.Response.ProductImages;
import thinktechsol.msquare.utils.Constant;

public class ImgSwiperAdapterProDetail extends PagerAdapter {

    private ViewSellProDetailActivity _activity;
    private LayoutInflater inflater;
    private ImageView mImaView;
    ViewPager viewPager;
    private static final int CAMERA_REQUEST = 1888;
    ArrayList<ProductImages> productImagesList;

    private static final String TAG = "ImgSwiperAdapterPro";


    public ImgSwiperAdapterProDetail(ViewSellProDetailActivity activity,
                                     ViewPager viewPager, ArrayList<ProductImages> productImagesList) {
        this._activity = activity;
        this.productImagesList = productImagesList;

        Log.e(TAG, "productImagesList size=" + productImagesList.size());

        Uri uri = Uri.parse(ContentResolver.SCHEME_ANDROID_RESOURCE + "://" + _activity.getPackageName() + "/drawable/" + "upload_img_bg");
        try {
            Bitmap bitmap = MediaStore.Images.Media.getBitmap(_activity.getContentResolver(), uri);
            bitmapList.add(bitmap);
        } catch (IOException e) {
            e.printStackTrace();
        }

        this.viewPager = viewPager;
    }

    @Override
    public int getCount() {
        return this.productImagesList.size();
//        return this.bitmapList.size();
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
        mImaView.setLayoutParams(AppLayoutParam(30.00f, 100f, 0, 0, 0, 0));
        mImaView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
//        mImaView.setImageBitmap(bitmapList.get(position));
        ProductImages myItem = this.productImagesList.get(position);


        try {
            if (myItem.isLocalImg) {
                Uri uri = Uri.fromFile(new File("" + myItem.image));
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(_activity.getContentResolver(), uri);
                mImaView.setImageBitmap(bitmap);
            } else
                Picasso.with(_activity).load(myItem.image).into(mImaView);

        } catch (Exception ex) {
            ex.printStackTrace();
        }
//        Picasso.with(_activity).load(myItem.image).into(mImaView);

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

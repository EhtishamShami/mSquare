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

import java.io.IOException;
import java.util.ArrayList;

import thinktechsol.msquare.R;
import thinktechsol.msquare.activities.buyer.HomeActivity;
import thinktechsol.msquare.utils.Constant;

public class ImgSwiperAdapterBuyerAdds extends PagerAdapter {

    private HomeActivity _activity;
    private LayoutInflater inflater;
    private ImageView mImaView;
    ViewPager viewPager;
    private static final int CAMERA_REQUEST = 1888;
    ArrayList<String> addsImagesList;

    private static final String TAG = "ImgSwiperAdapterPro";


    public ImgSwiperAdapterBuyerAdds(HomeActivity activity,
                                     ViewPager viewPager, ArrayList<String> addsImagesList) {
        this._activity = activity;
        this.addsImagesList = addsImagesList;

        Log.e(TAG, "productImagesList size=" + addsImagesList.size());

        this.viewPager = viewPager;
    }

    @Override
    public int getCount() {
        return this.addsImagesList.size();
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

        Uri uri = Uri.parse(ContentResolver.SCHEME_ANDROID_RESOURCE + "://" + _activity.getPackageName() + "/drawable/" +addsImagesList.get(position) /*"upload_img_bg"*/);
        try {
            Bitmap bitmap = MediaStore.Images.Media.getBitmap(_activity.getContentResolver(), uri);
            bitmapList.add(bitmap);
        } catch (IOException e) {
            e.printStackTrace();
        }
        mImaView.setImageBitmap(bitmapList.get(position));
//        ProductImages myItem = this.productImagesList.get(position);
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

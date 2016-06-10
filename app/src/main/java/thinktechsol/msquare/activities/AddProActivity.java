package thinktechsol.msquare.activities;

import android.Manifest;
import android.app.Activity;
import android.app.FragmentTransaction;
import android.content.ComponentName;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.media.Image;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Parcelable;
import android.provider.MediaStore;
import android.provider.Settings;
import android.support.v4.view.ViewPager;
import android.text.Html;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;


import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import thinktechsol.msquare.R;
import thinktechsol.msquare.adapter.ImgSwiperAdapter;
import thinktechsol.msquare.fragments.SellerDashBoardProductFragment;
import thinktechsol.msquare.interfaceMine.UploadImgInterface;
import thinktechsol.msquare.utils.Constant;

public class AddProActivity extends Activity implements UploadImgInterface {

    public static final String ADD_PRODUCT = "addproduct";
    public static final String VIEW_PRODUCT = "viewproduct";

    private ViewPager viewPager;
    ImageView upload_img;
    private ImgSwiperAdapter adapter;
    private LinearLayout dotsLayout;
    private int dotsCount;
    private TextView[] dots;
    static TextView title;
    public static ImageView backBtn;
    RelativeLayout titlebarlayout, bottombarlayout;
    ImageView add_product, view_product, add_product_save;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
//        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
//                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_add_pro);
        btnSelectorColor = getResources().getColor(R.color.addProductSelectorColor);

        add_product = (ImageView) findViewById(R.id.add_product);
        view_product = (ImageView) findViewById(R.id.view_product);
        add_product_save = (ImageView) findViewById(R.id.add_product_save);

        RelativeLayout fields1 = (RelativeLayout) findViewById(R.id.fields1);
        RelativeLayout fields = (RelativeLayout) findViewById(R.id.fields);
        RelativeLayout imgs = (RelativeLayout) findViewById(R.id.imgs);
        add_product_save.setLayoutParams(AppLayoutParam(8.5f, 32.58f, 0, 3, 0, 0, fields));

        add_product.setBackgroundResource(R.drawable.add_product_sel);
        view_product.setBackgroundResource(R.drawable.view_product_normal);

        bottombarlayout = (RelativeLayout) findViewById(R.id.bottombarlayout);
        titlebarlayout = (RelativeLayout) findViewById(R.id.titlebarlayout);
        title = (TextView) findViewById(R.id.title);
        title.setText("Add Product");
        backBtn = (ImageView) findViewById(R.id.backBtn);
        backBtn.setLayoutParams(AppLayoutParam2(10f, 10f, 0, 0, 0, 0));
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        titlebarlayout.setBackgroundColor(this.getResources().getColor(R.color.addProductTitleBarColor));
        titlebarlayout.setLayoutParams(AppLayoutParam(10.00f, 100f, 0, 0, 0, 0,null));



        viewPager = (ViewPager) findViewById(R.id.pager);
        viewPager.setLayoutParams(AppLayoutParam(30.00f, 100f, 0, 0, 0, 0,null));
        imgs.setLayoutParams(AppLayoutParam(30.00f, 100f, 0, 0, 0, 0,null));
        adapter = new ImgSwiperAdapter(AddProActivity.this,
                getFilePaths(), viewPager);
        viewPager.setAdapter(adapter);
        viewPager.addOnPageChangeListener(viewPagerPageChangeListener);
        setAndShowDotsOnPager();

        fields1.setLayoutParams(AppLayoutParam(40.0f, 100f, 0, 5, 0, 0,imgs));
//        bottombarlayout.setLayoutParams(AppLayoutParam(10.00f, 100f, 0, 0, 0, 0,fields1));

        add_product.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MakeItemSelected(ADD_PRODUCT);
            }
        });
        view_product.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MakeItemSelected(VIEW_PRODUCT);
            }
        });
//        upload_img = (ImageView) findViewById(R.id.upload_img);
//        upload_img.setLayoutParams(AppLayoutParam(32.00f, 100f, 0, 0, 0, 0));
//        upload_img.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//            }
//        });


//        final HorizontalListView hListView = (HorizontalListView) findViewById(R.id.hlistview);
//        String mobileArray[] = new String[100];
//        for (int i = 0; i < 100; i++) {
//            mobileArray[i] = String.valueOf(i);
//        }
//        ArrayAdapter adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, mobileArray);
//        hListView.setAdapter(adapter);
//
//        int listViewHeight = hListView.getMeasuredHeight();
//        int listViewWidth = hListView.getMeasuredWidth();
//        hListView.scrollTo(3, (listViewWidth / 2));
//        hListView.smoothScrollToPositionFromTop(5, (listViewHeight / 2));

//        hListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                Toast.makeText(AddProActivity.this, ""+position+" && "+id, Toast.LENGTH_SHORT).show();
////                int listViewHeight = hListView.getMeasuredHeight();
//                int listViewWidth = hListView.getMeasuredWidth();
////                hListView.scrollTo(position, (listViewWidth / 2));
////                hListView.scrollTo(position,75);
//            }
//        });
//
//        hListView.setOnScrollChangeListener(new View.OnScrollChangeListener() {
//            @Override
//            public void onScrollChange(View v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
//
//            }
//        });
    }

    public RelativeLayout.LayoutParams AppLayoutParam(float height, float width, float mL, float mT, float mR, float mB, View below) {
        RelativeLayout.LayoutParams paramName = new RelativeLayout.LayoutParams(
                Constant.getSize("w", width),
                Constant.getSize("h", height));
        paramName.addRule(RelativeLayout.CENTER_HORIZONTAL, RelativeLayout.TRUE);
        if (below != null)
            paramName.addRule(RelativeLayout.BELOW, below.getId());
        paramName.setMargins(Constant.getSize("h", mL), Constant.getSize("h", mT), Constant.getSize("h", mR), Constant.getSize("h", mB));
        return paramName;
    }

    int[] ids;

    public ArrayList<Integer> getFilePaths() {
        ArrayList<Integer> filePaths = new ArrayList<Integer>();

        ids = new int[]{R.drawable.upload_img_bg, R.drawable.back,
                R.drawable.baqala_icon};

        for (int i = 0; i < ids.length; i++) {
            filePaths.add(ids[i]);
        }

        return filePaths;
    }

    private static final int CAMERA_REQUEST = 1888;


    @Override
    public void onClicked() {
//        Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
//        startActivityForResult(cameraIntent, CAMERA_REQUEST);
        openCameraAndCapture();
    }

    private void setAndShowDotsOnPager() {
        dotsLayout = (LinearLayout) findViewById(R.id.viewPagerCountDots);
        dotsCount = adapter.getCount();

        if (dots != null) {
            for (int i = 0; i < dots.length; i++) {
                dots[i].setVisibility(View.GONE);
            }
        }
        dots = new TextView[dotsCount];

        for (int i = 0; i < dotsCount; i++) {
            dots[i] = new TextView(this);
            dots[i].setText(Html.fromHtml("&#8226;"));
            dots[i].setTextSize(30);
            dots[i].setTextColor(getResources().getColor(android.R.color.darker_gray));
            dotsLayout.addView(dots[i]);
        }
        dots[0].setTextColor(getResources().getColor(R.color.whiteColor));
    }

    // pager listner for the handling of dots showing bottom of each image
    ViewPager.OnPageChangeListener viewPagerPageChangeListener = new ViewPager.OnPageChangeListener() {

        @Override
        public void onPageSelected(int position) {
            Log.e("AddProActivity", "Scroll listener=" + dotsCount);
            for (int i = 0; i < dotsCount; i++) {
                dots[i].setTextColor(getResources().getColor(android.R.color.darker_gray));
            }
            dots[position].setTextColor(getResources().getColor(R.color.whiteColor));
        }

        @Override
        public void onPageScrolled(int arg0, float arg1, int arg2) {
        }

        @Override
        public void onPageScrollStateChanged(int arg0) {
        }
    };

    public void openCameraAndCapture() {
        startActivityForResult(getPickImageChooserIntent(), 200);
    }

    public Intent getPickImageChooserIntent() {

        // Determine Uri of camera image to save.
        Uri outputFileUri = getCaptureImageOutputUri();

        List<Intent> allIntents = new ArrayList<>();
        PackageManager packageManager = getPackageManager();

        // collect all camera intents
        Intent captureIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
        List<ResolveInfo> listCam = packageManager.queryIntentActivities(captureIntent, 0);
        for (ResolveInfo res : listCam) {
            Intent intent = new Intent(captureIntent);
            intent.setComponent(new ComponentName(res.activityInfo.packageName, res.activityInfo.name));
            intent.setPackage(res.activityInfo.packageName);
            if (outputFileUri != null) {
                intent.putExtra(MediaStore.EXTRA_OUTPUT, outputFileUri);
            }
            allIntents.add(intent);
        }

        // collect all gallery intents
        Intent galleryIntent = new Intent(Intent.ACTION_GET_CONTENT);
        galleryIntent.setType("image/*");
        List<ResolveInfo> listGallery = packageManager.queryIntentActivities(galleryIntent, 0);
        for (ResolveInfo res : listGallery) {
            Intent intent = new Intent(galleryIntent);
            intent.setComponent(new ComponentName(res.activityInfo.packageName, res.activityInfo.name));
            intent.setPackage(res.activityInfo.packageName);
            allIntents.add(intent);
        }

        Intent mainIntent = allIntents.get(allIntents.size() - 1);
        for (Intent intent : allIntents) {
            if (intent.getComponent().getClassName().equals("com.android.documentsui.DocumentsActivity")) {
                mainIntent = intent;
                break;
            }
        }
        allIntents.remove(mainIntent);

        // Create a chooser from the main intent
        Intent chooserIntent = Intent.createChooser(mainIntent, "Select source");
        // Add all other intents
        chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, allIntents.toArray(new Parcelable[allIntents.size()]));
        return chooserIntent;
    }

    private Uri getCaptureImageOutputUri() {
        Uri outputFileUri = null;
        File getImage = getExternalCacheDir();
        if (getImage != null) {
            outputFileUri = Uri.fromFile(new File(getImage.getPath(), "pickImageResult.jpeg"));
        }
        return outputFileUri;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == Activity.RESULT_OK) {
            Uri imageUri = getPickImageResultUri(data);
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), imageUri);
                adapter.bitmapList.add(bitmap);
                setAndShowDotsOnPager();

                viewPager.getAdapter().notifyDataSetChanged();
                viewPager.invalidate();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public Uri getPickImageResultUri(Intent data) {
        boolean isCamera = true;
        if (data != null && data.getData() != null) {
            String action = data.getAction();
            isCamera = action != null && action.equals(MediaStore.ACTION_IMAGE_CAPTURE);
        }
        return isCamera ? getCaptureImageOutputUri() : data.getData();
    }

    public RelativeLayout.LayoutParams AppLayoutParam2(float height, float width, float mL, float mT, float mR, float mB) {
        RelativeLayout.LayoutParams paramName = new RelativeLayout.LayoutParams(
                Constant.getSize("w", width),
                Constant.getSize("h", height));
//        paramName.addRule(RelativeLayout.CENTER_HORIZONTAL, RelativeLayout.TRUE);
//        if (below != null)
//            paramName.addRule(RelativeLayout.BELOW, below.getId());
        paramName.setMargins(Constant.getSize("h", mL), Constant.getSize("h", mT), Constant.getSize("h", mR), Constant.getSize("h", mB));
        return paramName;
    }

    int btnSelectorColor;

    public void MakeItemSelected(String btnName) {

        add_product.setBackgroundResource(R.drawable.add_product_normal);
        view_product.setBackgroundResource(R.drawable.view_product_normal);


        switch (btnName) {
            case ADD_PRODUCT:
                add_product.setBackgroundResource(R.drawable.add_product_sel);
                break;

            case VIEW_PRODUCT:
                view_product.setBackgroundResource(R.drawable.view_product_sel);
                break;
        }
    }
}

package thinktechsol.msquare.activities;

import android.app.Activity;
import android.app.FragmentTransaction;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.provider.MediaStore;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.wdullaer.materialdatetimepicker.time.RadialPickerLayout;
import com.wdullaer.materialdatetimepicker.time.TimePickerDialog;
import com.wefika.horizontalpicker.HorizontalPicker;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;

import thinktechsol.msquare.R;
import thinktechsol.msquare.adapter.ImgSwiperAdapter;
import thinktechsol.msquare.adapter.ViewProductListAdapter;
import thinktechsol.msquare.fragments.SellerAddProductFragment;
import thinktechsol.msquare.globels.globels;
import thinktechsol.msquare.interfaceMine.UploadImgInterface;
import thinktechsol.msquare.model.Response.getSellerProductsResponse;
import thinktechsol.msquare.services.GetSellerProducts2;
import thinktechsol.msquare.services.SellerAddProduct;
import thinktechsol.msquare.services.getSellerProducts;
import thinktechsol.msquare.utils.Constant;

//import thinktechsol.msquare.services.GetSellerProducts;

public class SellerViewProActivity extends Activity implements UploadImgInterface, HorizontalPicker.OnItemSelected, HorizontalPicker.OnItemClicked, TimePickerDialog.OnTimeSetListener {

    public static final String ADD_PRODUCT = "addproduct";
    public static final String VIEW_PRODUCT = "viewproduct";

    private ViewPager viewPager;
    ImageView upload_img;
    private ImgSwiperAdapter adapter;
    private LinearLayout dotsLayout;
    private int dotsCount;
    private TextView[] dots;
    static TextView title;
    public static ImageView backBtn, btn_menu;
    RelativeLayout titlebarlayout, bottombarlayout, add_product_layout, view_product_layout, fragmentLayout;
    ImageView add_product, view_product, add_product_save;
    ArrayList<String> selectedImagePath;
    EditText pro_title_et, pro_desc_et, pro_price_et, pro_time_et;
    ListView product_list;
    //    ArrayList<ProductImages> productImagesList;
    LinearLayout scroler_textview;
    ProgressDialog progressDialog;
    HorizontalPicker picker;
//    SellerAddProductFragment fragobj;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
//        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
//                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_seller_view_product);
        btnSelectorColor = getResources().getColor(R.color.addProductSelectorColor);
//        Constant.addOrViewProduct = true;

        selectedImagePath = new ArrayList<String>();

        add_product = (ImageView) findViewById(R.id.add_product);
        view_product = (ImageView) findViewById(R.id.view_product);
//        add_product_save = (ImageView) findViewById(R.id.add_product_save);

//        pro_title_et = (EditText) findViewById(R.id.pro_title_et);
//        pro_desc_et = (EditText) findViewById(R.id.pro_desc_et);
//        pro_price_et = (EditText) findViewById(R.id.pro_price_et);
//        pro_time_et = (EditText) findViewById(R.id.pro_time_et);

//        RelativeLayout fields1 = (RelativeLayout) findViewById(R.id.fields1);
//        RelativeLayout fields = (RelativeLayout) findViewById(R.id.fields);
//        RelativeLayout imgs = (RelativeLayout) findViewById(R.id.imgs);
//        scroler_textview = (LinearLayout) findViewById(R.id.scroler_textview);
        //add_product_save.setLayoutParams(AppLayoutParam(8.5f, 32.58f, 0, 3, 0, 0, fields));


        bottombarlayout = (RelativeLayout) findViewById(R.id.bottombarlayout);
        titlebarlayout = (RelativeLayout) findViewById(R.id.titlebar);
        fragmentLayout = (RelativeLayout) findViewById(R.id.fragmentLayout);
//        add_product_layout = (RelativeLayout) findViewById(R.id.add_product_layout);
        view_product_layout = (RelativeLayout) findViewById(R.id.view_product_layout);
        backBtn = (ImageView) findViewById(R.id.backBtn);
        btn_menu = (ImageView) findViewById(R.id.btn_menu);
        title = (TextView) findViewById(R.id.title);


//        if (Constant.addOrViewProduct) {
        add_product.setBackgroundResource(R.drawable.add_product_normal);
        view_product.setBackgroundResource(R.drawable.view_product_sel);

        MakeItemSelected(VIEW_PRODUCT);
        new getSellerProducts(SellerViewProActivity.this, SellerViewProActivity.this, globels.getGlobelRef().sellerLoginId);
//        } else {
//            add_product.setBackgroundResource(R.drawable.add_product_normal);
//            view_product.setBackgroundResource(R.drawable.view_product_sel);
////            new GetSellerProducts2(AddOrViewProActivity.this, AddOrViewProActivity.this, globels.getGlobelRef().sellerlogin.id);
//            new getSellerProducts(SellerViewProActivity.this, SellerViewProActivity.this, globels.getGlobelRef().sellerLoginId);
//            MakeItemSelected(VIEW_PRODUCT);
//        }

        // title bar
        backBtn.setLayoutParams(AppLayoutParam2(10f, 10f, 0, 0, 0, 0));
//        btn_menu.setVisibility(View.VISIBLE);
//        btn_menu.setBackgroundResource(R.drawable.edit_menu);
//        btn_menu.setLayoutParams(AppLayoutParam3(8f, 12f, 0, 0, 1, 0, "right"));
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        titlebarlayout.setBackgroundColor(this.getResources().getColor(R.color.addProductTitleBarColor));
        titlebarlayout.setLayoutParams(AppLayoutParam(10.00f, 100f, 0, 0, 0, 0, null));
        // title bar end

        product_list = (ListView) findViewById(R.id.product_list);

        // swiper at top of the screen showing images and next image by swiping
        //viewPager = (ViewPager) findViewById(R.id.pager);
        //viewPager.setLayoutParams(AppLayoutParam(30.00f, 100f, 0, 0, 0, 0, null));
        //imgs.setLayoutParams(AppLayoutParam(30.00f, 100f, 0, 0, 0, 0, null));
//        adapter = new ImgSwiperAdapter(SellerViewProActivity.this,
//                getFilePaths(), viewPager);
//        viewPager.setAdapter(adapter);
//        viewPager.addOnPageChangeListener(viewPagerPageChangeListener);
//        setAndShowDotsOnPager();

//        fields1.setLayoutParams(AppLayoutParam(40.0f, 100f, 0, 5, 0, 0, imgs));

        add_product.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(SellerViewProActivity.this, AddOrViewProActivity.class));
                finish();

//                MakeItemSelected(ADD_PRODUCT);
////                add_product_layout.setVisibility(View.VISIBLE);
////                view_product_layout.setVisibility(View.GONE);
//
////                if(globels.getGlobelRef().IdForAddProduct!=null){
//                Constant.addOrViewProduct = true;
//                fragobj = new SellerAddProductFragment();
//                FragmentTransaction transaction = getFragmentManager().beginTransaction();
//                transaction.replace(R.id.fragmentLayout, fragobj);
//                transaction.commit();


//                }else {
//
//                }
//                Constant.addOrViewProduct = true;
//                SellerAddProductFragment fragobj = new SellerAddProductFragment();
//                FragmentTransaction transaction = getFragmentManager().beginTransaction();
//                transaction.replace(R.id.fragmentLayout, fragobj);
//                transaction.commit();
            }
        });
        view_product.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MakeItemSelected(VIEW_PRODUCT);
//                view_product_layout.setVisibility(View.VISIBLE);
//                add_product_layout.setVisibility(View.GONE);
//                getFragmentManager().beginTransaction().remove(fragobj).commit();
                new GetSellerProducts2(SellerViewProActivity.this, SellerViewProActivity.this, globels.getGlobelRef().sellerLoginId);
            }
        });

        progressDialog = new ProgressDialog(SellerViewProActivity.this);
        progressDialog.setMessage("Loading Please wait...");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setCancelable(true);
//        progressDialog.show();

//        load_timer_text();
//        picker = (HorizontalPicker) findViewById(R.id.picker);
//        picker.setOnItemClickedListener(this);
//        picker.setOnItemSelectedListener(this);

//        pro_time_etlayout.ad
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
//                Toast.makeText(AddOrViewProActivity.this, ""+position+" && "+id, Toast.LENGTH_SHORT).show();
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

//        add_product_save.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
////                Log.e("AddOrViewProActivity", "first path=" + selectedImagePath.size());
////                Toast.makeText(AddOrViewProActivity.this, "btn pressed=" + adapter.bitmapList.get(1), Toast.LENGTH_SHORT).show();
////                new AddImageOfProduct(AddOrViewProActivity.this, AddOrViewProActivity.this, selectedImagePath);
//                String productDetails[] = new String[4];
//                productDetails[0] = pro_title_et.getText().toString();
//                productDetails[1] = pro_desc_et.getText().toString();
//                productDetails[2] = pro_price_et.getText().toString();
//                productDetails[3] = String.valueOf(selectedTimeIndex);
////                productDetails[3] = pro_time_et.getText().toString();
//
//                if (pro_title_et.getText().toString().trim().length() > 0 && pro_desc_et.getText().toString().trim().length() > 0
//                        && pro_price_et.getText().toString().trim().length() > 0 && selectedImagePath.size() > 0 &&
//                        !pro_price_et.getText().toString().equals("0")) {
//                    if (!pro_price_et.getText().toString().equals("0")) {
//                        new SellerAddProduct(SellerViewProActivity.this, SellerViewProActivity.this, productDetails, selectedImagePath);
//                    } else {
//                        AlertDialog alertDialog = new AlertDialog.Builder(SellerViewProActivity.this).create();
//                        //alertDialog.setTitle("Alert");
//                        alertDialog.setMessage("0 is not acceptable price");
//                        alertDialog.setCancelable(false);
//                        alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
//                                new DialogInterface.OnClickListener() {
//                                    public void onClick(DialogInterface dialog, int which) {
//                                        dialog.dismiss();
//                                    }
//                                });
//                        alertDialog.show();
//
//                    }
////                if (productDetails[0].trim() != null && productDetails[1].trim() != null && productDetails[2].trim() != null && productDetails[3].trim() != null) {
//
//                } else {
//                    AlertDialog alertDialog = new AlertDialog.Builder(SellerViewProActivity.this).create();
//                    //alertDialog.setTitle("Alert");
//                    alertDialog.setMessage("Please Complete the fields first");
//                    alertDialog.setCancelable(false);
//                    alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
//                            new DialogInterface.OnClickListener() {
//                                public void onClick(DialogInterface dialog, int which) {
//                                    dialog.dismiss();
//                                }
//                            });
//                    alertDialog.show();
//
//
//                }
//            }
//        });


    }

    TextView TextViewArray[] = new TextView[100];
//    int i = 0;

//    public void load_timer_text() {
//        new Handler().postDelayed(new Runnable() {
//            public void run() {
//                for (int i = 0; i < 100; i++) {
//                    TextViewArray[i] = new TextView(SellerViewProActivity.this);
//                    TextViewArray[i].setText(" " + i);
//                    TextViewArray[i].setTag(i);
//                    LinearLayout.LayoutParams textViewLayoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
//                    textViewLayoutParams.setMargins(Constant.getSize("h", 2), Constant.getSize("h", 2), Constant.getSize("h", 2), Constant.getSize("h", 2));
//                    scroler_textview.addView(TextViewArray[i], textViewLayoutParams);
//                    TextViewArray[i].setOnClickListener(new View.OnClickListener() {
//                        @Override
//                        public void onClick(View v) {
//                            for (int j = 0; j < 100; j++) {
//                                TextViewArray[j].setTypeface(null, Typeface.NORMAL);
//                            }
//                            TextViewArray[Integer.parseInt(v.getTag().toString())].setTypeface(null, Typeface.BOLD_ITALIC);
//
//                            //Toast.makeText(AddOrViewProActivity.this, ""+v.getTag(), Toast.LENGTH_SHORT).show();
//
//                        }
//                    });
//                    dismissDailog();
//                }
//            }
//        }, 1000);
//    }

    public void dismissDailog() {
        new Handler().postDelayed(new Runnable() {
            public void run() {
                progressDialog.dismiss();
            }
        }, 2 * 1000);
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
        openChooserDialog();
    }

    public void openChooserDialog() {

        final CharSequence[] options = {"Take Photo", "Choose from Gallery", "Cancel"};

        AlertDialog.Builder builder = new AlertDialog.Builder(SellerViewProActivity.this);
//        builder.setTitle("Add Photo!");
        builder.setItems(options, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                if (options[item].equals("Take Photo")) {
                    //Capture image from camera
                    takePhoto();
                } else if (options[item].equals("Choose from Gallery")) {
                    //pick image from gallary
                    Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
                    photoPickerIntent.setType("image/*");
                    startActivityForResult(photoPickerIntent, 1);

                } else if (options[item].equals("Cancel")) {
                    dialog.dismiss();
                }
            }
        });
        builder.show();

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
            Log.e("AddOrViewProActivity", "Scroll listener=" + dotsCount);
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


//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//
//        switch (requestCode) {
//            case 1:
//                if (resultCode == RESULT_OK) {
//                    Uri photoUri = data.getData();
//                    if (photoUri != null) {
//                        String[] filePathColumn = {MediaStore.Images.Media.DATA};
//                        Cursor cursor = getContentResolver().query(photoUri, filePathColumn, null, null, null);
//                        cursor.moveToFirst();
//                        int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
//                        String filePath = cursor.getString(columnIndex);
//                        cursor.close();
//                        Log.e("AddOrViewProActivity", "Gallery File Path=====>>>" + filePath);
//
//                        AddImgToViewPager(filePath);
//                    }
//                }
//
//                break;
//            case 2:
//                if (resultCode == RESULT_OK) {
//                    AddImgToViewPager(path);
//                }
//                break;
//        }
//    }

    public void AddImgToViewPager(String ImgPath) {
        try {
            selectedImagePath.add(ImgPath);

            Uri uri = Uri.fromFile(new File("" + ImgPath));
            Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), uri);
            adapter.bitmapList.add(bitmap);
            setAndShowDotsOnPager();

            viewPager.getAdapter().notifyDataSetChanged();
            viewPager.invalidate();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public Uri getImageUri(Context inContext, Bitmap inImage) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(inContext.getContentResolver(), inImage, "Title", null);
        return Uri.parse(path);
    }


    @Override
    public void onResume() {
        super.onResume();
//        if (Constant.addOrViewProduct) {
//            add_product.setBackgroundResource(R.drawable.add_product_sel);
//            view_product.setBackgroundResource(R.drawable.view_product_normal);
//            MakeItemSelected(ADD_PRODUCT);
//        } else {
//            add_product.setBackgroundResource(R.drawable.add_product_normal);
//            view_product.setBackgroundResource(R.drawable.view_product_sel);
//            MakeItemSelected(VIEW_PRODUCT);
//        }
    }

    public RelativeLayout.LayoutParams AppLayoutParam2(float height, float width, float mL, float mT, float mR, float mB) {
        RelativeLayout.LayoutParams paramName = new RelativeLayout.LayoutParams(
                Constant.getSize("w", width),
                Constant.getSize("h", height));
        paramName.addRule(RelativeLayout.CENTER_VERTICAL, RelativeLayout.TRUE);
//        if (below != null)
//            paramName.addRule(RelativeLayout.BELOW, below.getId());
        paramName.setMargins(Constant.getSize("h", mL), Constant.getSize("h", mT), Constant.getSize("h", mR), Constant.getSize("h", mB));
        return paramName;
    }

    public RelativeLayout.LayoutParams AppLayoutParam3(float height, float width, float mL, float mT, float mR, float mB, String leftOrRight) {
        RelativeLayout.LayoutParams paramName = new RelativeLayout.LayoutParams(
                Constant.getSize("w", width),
                Constant.getSize("h", height));
        if (leftOrRight.equals("right"))
            paramName.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
        paramName.addRule(RelativeLayout.CENTER_VERTICAL, RelativeLayout.TRUE);

        paramName.setMargins(Constant.getSize("h", mL), Constant.getSize("h", mT), Constant.getSize("h", mR), Constant.getSize("h", mB));
        return paramName;
    }

    int btnSelectorColor;

    public void MakeItemSelected(String btnName) {

        add_product.setBackgroundResource(R.drawable.add_product_normal);
        view_product.setBackgroundResource(R.drawable.view_product_normal);

        switch (btnName) {
            case ADD_PRODUCT:
//                add_product.setBackgroundResource(R.drawable.add_product_sel);
//                title.setText("Add Product");
//                add_product_layout.setVisibility(View.VISIBLE);
//                view_product_layout.setVisibility(View.GONE);

//                startActivity(new Intent(SellerViewProActivity.this, SellerViewProActivity.class));
//                finish();

                break;

            case VIEW_PRODUCT:
                view_product.setBackgroundResource(R.drawable.view_product_sel);
                title.setText("Products");
                //view_product_layout.setVisibility(View.VISIBLE);
                //add_product_layout.setVisibility(View.GONE);
                break;
        }
    }

    String path;

    public void takePhoto() {
        Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
        File folder = new File(Environment.getExternalStorageDirectory() + "/" + Constant.folderNameForCapturedImage);

        if (!folder.exists()) {
            folder.mkdir();
        }
        final Calendar c = Calendar.getInstance();
        String new_Date = c.get(Calendar.DAY_OF_MONTH) + "-" + ((c.get(Calendar.MONTH)) + 1) + "-" + c.get(Calendar.YEAR) + " " + c.get(Calendar.HOUR) + "-" + c.get(Calendar.MINUTE) + "-" + c.get(Calendar.SECOND);
        path = String.format(Environment.getExternalStorageDirectory() + "/" + Constant.folderNameForCapturedImage + "/%s.png", "Product(" + new_Date + ")");
        File photo = new File(path);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(photo));
        startActivityForResult(intent, 2);
    }

    public void fillProductListWithData(ArrayList<getSellerProductsResponse> list) {

//        ArrayList<ViewProductItem> listItem = new ArrayList<ViewProductItem>();
//        listItem.add(new ViewProductItem("Test Pro", "processed", R.drawable.messages, true));
//        listItem.add(new ViewProductItem("Test Pro2", "not processed", R.drawable.messages, true));

        Constant.productList = list;
        try {
            ViewProductListAdapter m_adapter = new ViewProductListAdapter(SellerViewProActivity.this, SellerViewProActivity.this, R.layout.view_product_list_item, list);
            product_list.setAdapter(m_adapter);
        } catch (Exception e) {
            Log.e("SellerDashBoardActivity", "adapter=" + e);
        }
    }

    @Override
    public void onItemClicked(int index) {
//        Toast.makeText(this, "Item clicked"+picker.getSelectedItem(), Toast.LENGTH_SHORT).show();
    }

    int selectedTimeIndex;

    @Override
    public void onItemSelected(int index) {
        selectedTimeIndex = index + 1;
        Toast.makeText(this, "Item selected" + selectedTimeIndex, Toast.LENGTH_SHORT).show();
    }

    public void productAdditionCompleted() {
        //Toast.makeText(this, "Product is added Succeesfully", Toast.LENGTH_SHORT).show();
        pro_title_et.setText("");
        pro_desc_et.setText("");
        pro_price_et.setText("");
    }

    public static final String TIMERANGEPICKER_TAG = "timerangepicker";
    Calendar calendar;
    TimePickerDialog timePickerDialog;
    int CalendarHour, CalendarMinute;

    public void show_Time_range() {
        Calendar now = Calendar.getInstance();
        TimePickerDialog tpd = TimePickerDialog.newInstance(
                this,
                now.get(Calendar.HOUR_OF_DAY),
                now.get(Calendar.MINUTE),
                true
        );
        tpd.setTitle("TimePicker Title");
        tpd.setMaxTime(6, 59, 0);
        tpd.setMinTime(0, 10, 0);
        tpd.setStartTime(0, 15);

        tpd.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialogInterface) {
                Log.d("TimePicker", "Dialog was cancelled");
            }
        });
        tpd.show(getFragmentManager(), "Timepickerdialog");
    }

    @Override
    public void onTimeSet(RadialPickerLayout view, int hourOfDay, int minute, int second) {
        int time = hourOfDay * 60 + minute;
        selectedTimeIndex = time;
        Log.e("AddOrViewProActivity", "first path=" + time);
    }
}

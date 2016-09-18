package thinktechsol.msquare.activities;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
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

import com.wefika.horizontalpicker.HorizontalPicker;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.ArrayList;
import java.util.Calendar;

import thinktechsol.msquare.R;
import thinktechsol.msquare.adapter.ImgSwiperAdapterEditProduct;
import thinktechsol.msquare.globels.globels;
import thinktechsol.msquare.interfaceMine.UploadImgInterfaceEditPro;
import thinktechsol.msquare.model.Response.ProductImages;
import thinktechsol.msquare.model.Response.getSellerProductsResponse;
import thinktechsol.msquare.services.DeleteImageService;
import thinktechsol.msquare.services.SellerAddProductForEditImg;
import thinktechsol.msquare.utils.Constant;

public class EditProActivity extends Activity implements UploadImgInterfaceEditPro, HorizontalPicker.OnItemSelected, HorizontalPicker.OnItemClicked {

    public static final String ADD_PRODUCT = "addproduct";
    public static final String VIEW_PRODUCT = "viewproduct";

    private ViewPager viewPager;
    ImageView upload_img;
    private ImgSwiperAdapterEditProduct adapter;
    private LinearLayout dotsLayout;
    private int dotsCount;
    private TextView[] dots;
    static TextView title;
    public static ImageView backBtn;
    RelativeLayout titlebarlayout, bottombarlayout, add_product_layout, view_product_layout;
    ImageView add_productt, view_productt, update_product, cross_btn;
    ArrayList<String> selectedImagePath;
    EditText pro_title_et, pro_desc_et, pro_price_et, pro_time_et;
    ListView product_list;
    ArrayList<ProductImages> productImagesList;
    LinearLayout scroler_textview;
    ProgressDialog progressDialog;
    HorizontalPicker picker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
//        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
//                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_edit_pro);

//        seller product single
        getSellerProductsResponse singleProduct = Constant.singleProduct;
//        list of images of the seller products
        productImagesList = Constant.productImagesList;

        btnSelectorColor = getResources().getColor(R.color.addProductSelectorColor);
//        Constant.addOrViewProduct = true;

        selectedImagePath = new ArrayList<String>();
        //productImagesList.remove(0);

//        add_product = (ImageView) findViewById(R.id.add_product);
//        view_product = (ImageView) findViewById(R.id.view_product);
        update_product = (ImageView) findViewById(R.id.update_product);
        cross_btn = (ImageView) findViewById(R.id.cross_btn);

        pro_title_et = (EditText) findViewById(R.id.pro_title_et);
        pro_desc_et = (EditText) findViewById(R.id.pro_desc_et);
        pro_price_et = (EditText) findViewById(R.id.pro_price_et);
        pro_time_et = (EditText) findViewById(R.id.pro_time_et);

        pro_title_et.setText(singleProduct.title);
        pro_price_et.setText(singleProduct.price);
        pro_desc_et.setText(singleProduct.description);

        RelativeLayout fields1 = (RelativeLayout) findViewById(R.id.fields1);
        RelativeLayout fields = (RelativeLayout) findViewById(R.id.fields);
        RelativeLayout imgs = (RelativeLayout) findViewById(R.id.imgs);
//        scroler_textview = (LinearLayout) findViewById(R.id.scroler_textview);
        update_product.setLayoutParams(AppLayoutParam(8.5f, 32.58f, 0, 3, 0, 0, fields));


        bottombarlayout = (RelativeLayout) findViewById(R.id.bottombarlayout);
        titlebarlayout = (RelativeLayout) findViewById(R.id.titlebar);
        add_product_layout = (RelativeLayout) findViewById(R.id.add_product_layout);
        view_product_layout = (RelativeLayout) findViewById(R.id.view_product_layout);
        backBtn = (ImageView) findViewById(R.id.backBtn);
        title = (TextView) findViewById(R.id.title);
        title.setText("Edit Product");

//        if (Constant.addOrViewProduct) {
//            add_product.setBackgroundResource(R.drawable.add_product_sel);
//            view_product.setBackgroundResource(R.drawable.view_product_normal);
//
//            MakeItemSelected(ADD_PRODUCT);
//        } else {
//            add_product.setBackgroundResource(R.drawable.add_product_normal);
//            view_product.setBackgroundResource(R.drawable.view_product_sel);
//            new GetSellerProducts(EditProActivity.this, EditProActivity.this, globels.getGlobelRef().sellerlogin.id);
//            MakeItemSelected(VIEW_PRODUCT);
//        }

        // title bar
        backBtn.setLayoutParams(AppLayoutParam3(10f, 10f, 0, 0, 0, 0, "left"));

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(EditProActivity.this, ViewSellProDetailActivity.class));
                finish();
            }
        });
        titlebarlayout.setBackgroundColor(this.getResources().getColor(R.color.addProductTitleBarColor));
        titlebarlayout.setLayoutParams(AppLayoutParam(10.00f, 100f, 0, 0, 0, 0, null));
        // title bar end

        product_list = (ListView) findViewById(R.id.product_list);

        // swiper at top of the screen showing images and next image by swiping
        viewPager = (ViewPager) findViewById(R.id.pager);
        viewPager.setLayoutParams(AppLayoutParam(30.00f, 100f, 0, 0, 0, 0, null));
        imgs.setLayoutParams(AppLayoutParam(30.00f, 100f, 0, 0, 0, 0, null));
        adapter = new ImgSwiperAdapterEditProduct(EditProActivity.this,
                viewPager, productImagesList);
        viewPager.setAdapter(adapter);
        viewPager.addOnPageChangeListener(viewPagerPageChangeListener);
        setAndShowDotsOnPager();


        fields1.setLayoutParams(AppLayoutParam(40.0f, 100f, 0, 5, 0, 0, imgs));

        progressDialog = new ProgressDialog(EditProActivity.this);
        progressDialog.setMessage("Loading Please wait...");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setCancelable(true);
//        progressDialog.show();

//        load_timer_text();
        picker = (HorizontalPicker) findViewById(R.id.picker);
        picker.setOnItemClickedListener(this);
        picker.setOnItemSelectedListener(this);
//        Toast.makeText(this, "" + (Integer.parseInt(singleProduct.deliveryTime) - 1), Toast.LENGTH_SHORT).show();
        Log.e("EditProActivity", "deliveryTime=" + singleProduct.deliveryTime);
        if (singleProduct.deliveryTime != "" && !singleProduct.deliveryTime.equals(" ") && !singleProduct.deliveryTime.equals("") && Integer.parseInt(singleProduct.deliveryTime) > 0) {
            picker.setSelectedItem(Integer.parseInt(singleProduct.deliveryTime) - 1);
            selectedTimeIndex = (Integer.parseInt(singleProduct.deliveryTime) - 1);
        } else {
            picker.setSelectedItem(0);
            selectedTimeIndex = 0;
        }

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

        update_product.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String productDetails[] = new String[4];
                productDetails[0] = pro_title_et.getText().toString();
                productDetails[1] = pro_desc_et.getText().toString();
                productDetails[2] = pro_price_et.getText().toString();
                productDetails[3] = String.valueOf(selectedTimeIndex);
                Log.e("EditProd", "update btn is clicked=" + globels.getGlobelRef().sellerLoginId);
                if (pro_title_et.getText().toString().trim().length() > 0 && pro_desc_et.getText().toString().trim().length() > 0
                        && pro_price_et.getText().toString().trim().length() > 0 && (selectedImagePath.size() > 0 || productImagesList.size() > 0)) {
                    new SellerAddProductForEditImg(EditProActivity.this, EditProActivity.this, productDetails, selectedImagePath);
                } else {
                    AlertDialog alertDialog = new AlertDialog.Builder(EditProActivity.this).create();
                    //alertDialog.setTitle("Alert");
                    alertDialog.setMessage("Please Insert data first");
                    alertDialog.setCancelable(false);
                    alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            });
                    alertDialog.show();
                }
            }
        });

        cross_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Toast.makeText(EditProActivity.this, "" + productImagesList.get(imageSliderPosition).id, Toast.LENGTH_SHORT).show();
//                Toast.makeText(EditProActivity.this, "" + productImagesList.size(), Toast.LENGTH_SHORT).show();
                Log.e("EditProActivity", "list size is=" + productImagesList.size());
                Log.e("EditProActivity", "list size deletion value=" + imageSliderPosition);
                if (productImagesList.size() > 0) {

                    new DeleteImageService(EditProActivity.this, EditProActivity.this, productImagesList.get(imageSliderPosition).id);

                    productImagesList.remove(imageSliderPosition);
                    adapter = new ImgSwiperAdapterEditProduct(EditProActivity.this,
                            viewPager, productImagesList);
                    viewPager.setAdapter(adapter);
                    viewPager.addOnPageChangeListener(viewPagerPageChangeListener);
                    setAndShowDotsOnPager();
                }
            }
        });
    }

    int imageSliderPosition = 0;

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


    public void openChooserDialog() {

        final CharSequence[] options = {"Take Photo", "Choose from Gallery", "Cancel"};

        AlertDialog.Builder builder = new AlertDialog.Builder(EditProActivity.this);
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
        //dots[0].setTextColor(getResources().getColor(R.color.whiteColor));
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
            imageSliderPosition = position;
        }

        @Override
        public void onPageScrolled(int arg0, float arg1, int arg2) {
        }

        @Override
        public void onPageScrollStateChanged(int arg0) {
        }
    };


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        switch (requestCode) {
            case 1:
                if (resultCode == RESULT_OK) {
                    Uri photoUri = data.getData();
                    if (photoUri != null) {
                        String[] filePathColumn = {MediaStore.Images.Media.DATA};
                        Cursor cursor = getContentResolver().query(photoUri, filePathColumn, null, null, null);
                        cursor.moveToFirst();
                        int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                        String filePath = cursor.getString(columnIndex);
                        cursor.close();
                        Log.e("AddOrViewProActivity", "Gallery File Path=====>>>" + filePath);

                        AddImgToViewPager(filePath);
                    }
                }

                break;
            case 2:
                if (resultCode == RESULT_OK) {
                    AddImgToViewPager(path);
                }
                break;
        }
    }

    public void AddImgToViewPager(String ImgPath) {
        try {
            selectedImagePath.add(ImgPath);

            //  Uri uri = Uri.fromFile(new File("" + ImgPath));
            //  Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), uri);
            //adapter.bitmapList.add(bitmap);

            productImagesList.add(new ProductImages("", "", ImgPath, true));
            Constant.productImagesList = productImagesList;


            setAndShowDotsOnPager();

            viewPager.getAdapter().notifyDataSetChanged();
            viewPager.invalidate();

        } catch (Exception e) {
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

    int btnSelectorColor;

    public void MakeItemSelected(String btnName) {

//        add_product.setBackgroundResource(R.drawable.add_product_normal);
//        view_product.setBackgroundResource(R.drawable.view_product_normal);

//        switch (btnName) {
//            case ADD_PRODUCT:
//                add_product.setBackgroundResource(R.drawable.add_product_sel);
//                title.setText("Add Product");
//                add_product_layout.setVisibility(View.VISIBLE);
//                view_product_layout.setVisibility(View.GONE);
//                break;
//
//            case VIEW_PRODUCT:
//                view_product.setBackgroundResource(R.drawable.view_product_sel);
//                title.setText("Products");
//                view_product_layout.setVisibility(View.VISIBLE);
//                add_product_layout.setVisibility(View.GONE);
//                break;
//        }
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

//    public void fillProductListWithData(ArrayList<getSellerProductsResponse> list) {
//
////        ArrayList<ViewProductItem> listItem = new ArrayList<ViewProductItem>();
////        listItem.add(new ViewProductItem("Test Pro", "processed", R.drawable.messages, true));
////        listItem.add(new ViewProductItem("Test Pro2", "not processed", R.drawable.messages, true));
//
//        Constant.productList = list;
//        try {
//            ViewProductListAdapter m_adapter = new ViewProductListAdapter(EditProActivity.this, EditProActivity.this, R.layout.view_product_list_item, list);
//            product_list.setAdapter(m_adapter);
//        } catch (Exception e) {
//            Log.e("SellerDashBoardActivity", "adapter=" + e);
//        }
//    }

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

    @Override
    public void onClicked() {
        openChooserDialog();
    }
}

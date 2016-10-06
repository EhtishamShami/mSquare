package thinktechsol.msquare.fragments.Buyer;

import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.app.Fragment;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.preference.PreferenceManager;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import thinktechsol.msquare.BaseAlbumDirFactory;
import thinktechsol.msquare.R;
import thinktechsol.msquare.activities.AlbumStorageDirFactory;
import thinktechsol.msquare.activities.UserTypeActivity;
import thinktechsol.msquare.activities.buyer.BuyerLoginActivity;
import thinktechsol.msquare.globels.globels;
import thinktechsol.msquare.model.Buyer.BuyerDetailsModel;
import thinktechsol.msquare.services.buyer.BuyerAddImageSetting;
import thinktechsol.msquare.services.buyer.ChangePasswordService;
import thinktechsol.msquare.services.buyer.ForgotPasswordService;
import thinktechsol.msquare.services.buyer.GetBuyerDetails;
import thinktechsol.msquare.services.buyer.UpdateDeviceInfoService;
import thinktechsol.msquare.utils.Constant;

public class BuyerDashBoardSettingFragment extends Fragment {

    TextView buyer_title, emailText, change_password_Text;
    RelativeLayout titlebarlayout, buyer_detials_layout, buyer_title_layout, buyer_email_layout, buyer_logout_layout;
    ImageView userImage;

    TextView logoutText;
    ImageView logoutImg;

    SharedPreferences preferences;
    SharedPreferences.Editor editor;

    RelativeLayout buyer_changepassword_layout;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.activity_buyer_deshboard_setting, container, false);

        if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE}, 0);
        }

//        changePasswordDialog();
        mAlbumStorageDirFactory = new BaseAlbumDirFactory();
        preferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
        editor = preferences.edit();

        RelativeLayout imgs = (RelativeLayout) v.findViewById(R.id.imgs);
        imgs.setLayoutParams(AppLayoutParam(30.00f, 100f, 0, 0, 0, 0, null));

        userImage = (ImageView) v.findViewById(R.id.userImage);

        buyer_detials_layout = (RelativeLayout) v.findViewById(R.id.buyer_detials_layout);
        buyer_detials_layout.setLayoutParams(AppLayoutParam(10.00f, 100f, 0, 0, 0, 0, imgs));

        buyer_title_layout = (RelativeLayout) v.findViewById(R.id.buyer_title_layout);
        buyer_title_layout.setLayoutParams(AppLayoutParam3(10.00f, 70f, 0, 0, 0, 0, null, 0));

        buyer_email_layout = (RelativeLayout) v.findViewById(R.id.buyer_email_layout);
        buyer_email_layout.setLayoutParams(AppLayoutParam(10.00f, 100f, 2, 0, 0, 0, null));

        buyer_title = (TextView) v.findViewById(R.id.buyer_title);
        emailText = (TextView) v.findViewById(R.id.emailText);
        change_password_Text = (TextView) v.findViewById(R.id.change_password_Text);

        buyer_changepassword_layout = (RelativeLayout) v.findViewById(R.id.buyer_changepassword_layout);
        buyer_changepassword_layout.setLayoutParams(AppLayoutParam(10.00f, 100f, 2, 0, 0, 0, buyer_email_layout));

        buyer_logout_layout = (RelativeLayout) v.findViewById(R.id.buyer_logout_layout);
        buyer_logout_layout.setLayoutParams(AppLayoutParam(10.00f, 100f, 2, 0, 0, 0, buyer_changepassword_layout));


        logoutText = (TextView) v.findViewById(R.id.logoutText);
        logoutImg = (ImageView) v.findViewById(R.id.logoutImg);

        userImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openChooserDialog();
            }
        });

//        globels.getGlobelRef().buyerLoginId
        new GetBuyerDetails(getActivity(), BuyerDashBoardSettingFragment.this, globels.getGlobelRef().buyerLoginId);

        buyer_changepassword_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                changePasswordDialog();
            }
        });

        buyer_logout_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                new UpdateDeviceInfoService(getActivity(), "buyer", globels.getGlobelRef().sellerLoginId, globels.getGlobelRef().deviceToken);

                editor.putString("buyerLoginId", "");
                editor.putBoolean("isBuyerLogin", false);
                editor.putString("token", "");
                editor.commit();

                globels.getGlobelRef().deviceToken = "";

                Intent i = new Intent(getActivity(),
                        UserTypeActivity.class);
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(i);
                getActivity().finish();
                Toast.makeText(getActivity(), "You have successfully logged out!", Toast.LENGTH_SHORT).show();
            }
        });
//        logoutImg.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                new UpdateDeviceInfoService(getActivity(), "buyer", globels.getGlobelRef().sellerLoginId, globels.getGlobelRef().deviceToken);
//
//                editor.putString("buyerLoginId", "");
//                editor.putBoolean("isBuyerLogin", false);
//                editor.putString("token", "");
//                editor.commit();
//
//                globels.getGlobelRef().deviceToken = "";
//
//                Intent i = new Intent(getActivity(),
//                        UserTypeActivity.class);
//                startActivity(i);
//                getActivity().finish();
//                Toast.makeText(getActivity(), "You have successfully logged out!", Toast.LENGTH_SHORT).show();
//            }
//        });

        return v;
    }

    String buyerId;

    public void fillProductListWithData(ArrayList<BuyerDetailsModel> BuyerDetails) {

        try {
            buyerId = BuyerDetails.get(0).id;
            buyer_title.setText("" + BuyerDetails.get(0).fName + " " + BuyerDetails.get(0).lName);
            emailText.setText("" + BuyerDetails.get(0).email);

            if (!BuyerDetails.get(0).thumb.equals("") && BuyerDetails.get(0).thumb != null)
                Picasso.with(getActivity()).load(Constant.imgbaseUrl + BuyerDetails.get(0).thumb).into(userImage);
            else
                userImage.setBackgroundResource(R.drawable.avatar);
        } catch (Exception e) {
            Log.e("BuyerDetailsInfo", "Exception in buyer detail info=" + e);
        }
    }

    public RelativeLayout.LayoutParams AppLayoutParam2(float height, float width, float mL, float mT, float mR, float mB, String leftOrRight) {
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

    public RelativeLayout.LayoutParams AppLayoutParam3(float height, float width, float mL, float mT, float mR, float mB, View below, int toRightView) {
        RelativeLayout.LayoutParams paramName = new RelativeLayout.LayoutParams(
                Constant.getSize("w", width),
                Constant.getSize("h", height));
        paramName.addRule(RelativeLayout.CENTER_VERTICAL, RelativeLayout.TRUE);

        if (toRightView != 0)
            paramName.addRule(RelativeLayout.RIGHT_OF, toRightView);

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

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
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

    String path;
    Uri file;

    public void takePhoto() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        file = Uri.fromFile(getOutputMediaFile());
        intent.putExtra(MediaStore.EXTRA_OUTPUT, file);
        startActivityForResult(intent, 100);
    }

    private static final int ACTION_TAKE_PHOTO_B = 200;
    private String mCurrentPhotoPath;

    public void takePhoto2(int actionCode) {

        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        switch (actionCode) {
            case ACTION_TAKE_PHOTO_B:
                File f = null;

                try {
                    f = setUpPhotoFile();
                    mCurrentPhotoPath = f.getAbsolutePath();
                    takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(f));
                } catch (IOException e) {
                    e.printStackTrace();
                    f = null;
                    mCurrentPhotoPath = null;
                }
                break;

            default:
                break;
        } // switch

        startActivityForResult(takePictureIntent, actionCode);
    }

    private File setUpPhotoFile() throws IOException {

        File f = createImageFile();
        if (f.exists()) {
            mCurrentPhotoPath = f.getAbsolutePath();
        }
        return f;
    }

    int targetW, targetH;
    private static final String JPEG_FILE_PREFIX = "IMG_";
    private static final String JPEG_FILE_SUFFIX = ".jpg";
    private AlbumStorageDirFactory mAlbumStorageDirFactory = null;

    private File createImageFile() throws IOException {
        // Create an image file name
        targetW = 100;
        targetH = 100;
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = JPEG_FILE_PREFIX + timeStamp + "_";
        File albumF = getAlbumDir();
        File imageF = File.createTempFile(imageFileName, JPEG_FILE_SUFFIX, albumF);
        return imageF;
    }
    private File getAlbumDir() {
        File storageDir = null;
//        String str;

        if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())) {

            storageDir = mAlbumStorageDirFactory.getAlbumStorageDir(getAlbumName());
            //str =  Environment.getExternalStorageDirectory().getAbsolutePath();

            if (storageDir != null) {
                if (!storageDir.mkdirs()) {
                    if (!storageDir.exists()) {
                        Log.d("CameraSample", "failed to create directory");
                        return null;
                    }
                }
            }

        } else {
            Log.v(getString(R.string.app_name), "External storage is not mounted READ/WRITE.");
        }

        return storageDir;
    }
    private String getAlbumName() {
        return getString(R.string.app_name);
    }
    private static File getOutputMediaFile() {
        File mediaStorageDir = new File(Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_PICTURES), "CameraDemo");

        if (!mediaStorageDir.exists()) {
            if (!mediaStorageDir.mkdirs()) {
                return null;
            }
        }
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        return new File(mediaStorageDir.getPath() + File.separator +
                "IMG_" + timeStamp + ".jpg");
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        switch (requestCode) {
            case 1:
                if (resultCode == Activity.RESULT_OK) {
                    try {
                        Uri photoUri = data.getData();
                        if (photoUri != null) {
                            String[] filePathColumn = {MediaStore.Images.Media.DATA};
                            Cursor cursor = getActivity().getContentResolver().query(photoUri, filePathColumn, null, null, null);
                            cursor.moveToFirst();
                            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                            String filePath = cursor.getString(columnIndex);
                            cursor.close();
                            Log.e("AddOrViewProActivity", "Gallery File Path=====>>>" + filePath);

                            Bitmap bmp = BitmapFactory.decodeFile(filePath);
                            Log.e("AddOrViewProActivity", "Gallery File Path=====>>>" + bmp);
                            userImage.setImageBitmap(bmp);

                            new BuyerAddImageSetting(getActivity(), BuyerDashBoardSettingFragment.this, filePath, emailText.getText().toString().trim());
//                        AddImgToViewPager(filePath);
                        }
                    } catch (Exception e) {
                    }
                }


                break;
            case 2:
                if (resultCode == Activity.RESULT_OK) {
//                    AddImgToViewPager(path);
                }
                break;

            case 3:
                if (resultCode == Activity.RESULT_OK) {
                    Log.e("AddOrViewProActivity", "camera File Path 1=====>>>" + data.getData());
                    Uri selectedImage = data.getData();
                    // ImageView photo = (ImageView) findViewById(R.id.add_contact_label_photo);


                    if (selectedImage != null) {
                        String[] filePathColumn = {MediaStore.Images.Media.DATA};
                        Cursor cursor = getActivity().getContentResolver().query(selectedImage, filePathColumn, null, null, null);
                        cursor.moveToFirst();
                        int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                        String filePath = cursor.getString(columnIndex);
                        cursor.close();


                        Bitmap bmp = BitmapFactory.decodeFile(filePath);
//                        Log.e("AddOrViewProActivity", "Camera File Path=====>>>" + bmp);
                        userImage.setImageBitmap(bmp);
//                        try {
//                            InputStream inputStream = null;
//
////                            bmp = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), Uri.parse(mCurrentPhotoPath));
////                            mImageView.setImageBitmap(bmp);
//
//                            inputStream = getActivity().getContentResolver().openInputStream(selectedImage);
//                            Bitmap bmp = BitmapFactory.decodeStream(inputStream);
//                            userImage.setImageBitmap(bmp);
//                        } catch (FileNotFoundException e) {
//                            e.printStackTrace();
//                        }
                    }


//                    Bitmap mBitmap = null;
//                    try
//                    {
//                        mBitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), selectedImage);
//                        Log.e("AddOrViewProActivity", "camera File Path=====>>>" + mBitmap);
//                        userImage.setImageBitmap(mBitmap);
//                    }
//                    catch (IOException e)
//                    {
//                        e.printStackTrace();
//                    }
                }
                break;
            case 200:
                if (resultCode != 0) {

                    //pressedButton = (ImageButton) findViewById(getImgBtn());
                    handleBigCameraPhoto();
                    //pressedButton.setVisibility(View.VISIBLE);
                   // HscrollView.setVisibility(View.VISIBLE);
                   // closeBtns[PicsNum - 1].setVisibility(View.VISIBLE);
                    //picsUploadedNum.setVisibility(View.VISIBLE);
                    //picsUploadedNum.setText(PicsNum + "/4");

                }
            case 100:
                userImage.setImageURI(file);
//                userImage.setImageURI(file);
        }
    }

    public static String getRealPathFromUri(Context context, Uri contentUri) {
        Cursor cursor = null;
        try {
            String[] proj = {MediaStore.Images.Media.DATA};
            cursor = context.getContentResolver().query(contentUri, proj, null, null, null);
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            cursor.moveToFirst();
            return cursor.getString(column_index);
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
    }

    Dialog changePasswordDialog;

    public void changePasswordDialog() {
        changePasswordDialog = new Dialog(getActivity());
        changePasswordDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        changePasswordDialog.setCancelable(false);

        LayoutInflater inflater = (LayoutInflater) getActivity().getLayoutInflater();
        View customView = inflater.inflate(R.layout.dialog_change_password, null);

        changePasswordDialog.setContentView(customView);

        int width = (int) (getResources().getDisplayMetrics().widthPixels * 0.95);
        int height = (int) (getResources().getDisplayMetrics().heightPixels * 0.3);
        changePasswordDialog.getWindow().setLayout(width, height);

        // final EditText id = (EditText) changePasswordDialog.findViewById(R.id.id);
        final EditText password = (EditText) changePasswordDialog.findViewById(R.id.password);


        //int value = globels.getGlobelRef().buyerLoginResponse.size();

        Button OkBtn = (Button) changePasswordDialog.findViewById(R.id.OkBtn);
        OkBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new ChangePasswordService(getActivity(), BuyerDashBoardSettingFragment.this, buyerId, password.getText().toString().trim());
            }
        });

        Button CancelBtn = (Button) changePasswordDialog.findViewById(R.id.CancelBtn);
        CancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changePasswordDialog.dismiss();
            }
        });

        changePasswordDialog.show();

    }

    public void onChangePassRequestSubmitted() {
        changePasswordDialog.dismiss();
    }

    private void handleBigCameraPhoto() {

        //Setting Taken Image into Image List to send to server as base64

        setImgBtnPic();// setPic();
        galleryAddPic();

//        if (mCurrentPhotoPath != null) {
//            AttachedFile file = new AttachedFile();
//            if (!getEncoded64ImageStringFromBitmap().equals("")) {
//                file.Data = getEncoded64ImageStringFromBitmap();
//                file.MimeType = "image/jped";
//            }
//
//            if(Accident.getAccident().cVehicle.Images64 == null)
//                Accident.getAccident().cVehicle.Images64 = new ArrayList<>();
//
//
//            Accident.getAccident().cVehicle.Images64.add(file);
//            //else show error no Image selected
//            mCurrentPhotoPath = null;
//        }

    }

    private void setImgBtnPic() {

        targetW = 300;//mImageView.getWidth();
        targetH = 300;

        int orientation = getImageOrientation(mCurrentPhotoPath);

        Bitmap bitmap = BitmapFactory.decodeFile(mCurrentPhotoPath);
        Matrix m = new Matrix();
        m.postRotate(orientation);

        bitmap = Bitmap.createBitmap(bitmap , 0, 0, bitmap.getWidth(), bitmap.getHeight(), m, true);
        bitmap = bitmap.createScaledBitmap(bitmap, 300, 300, false);
        //pressedButton.setImageBitmap(bitmap);
    }

    public static int getImageOrientation(String imagePath) {
        int rotate = 0;
        try {
            File imageFile = new File(imagePath);
            ExifInterface exif = new ExifInterface(imageFile.getAbsolutePath());
            int orientation = exif.getAttributeInt(
                    ExifInterface.TAG_ORIENTATION,
                    ExifInterface.ORIENTATION_NORMAL);

            switch (orientation) {
                case ExifInterface.ORIENTATION_ROTATE_270:
                    rotate = 270;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_180:
                    rotate = 180;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_90:
                    rotate = 90;
                    break;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return rotate;
    }
//    private String mCurrentPhotoPath;
    private void galleryAddPic() {
        Intent mediaScanIntent = new Intent("android.intent.action.MEDIA_SCANNER_SCAN_FILE");
        File f = new File(mCurrentPhotoPath);
        Uri contentUri = Uri.fromFile(f);
        mediaScanIntent.setData(contentUri);
        getActivity().sendBroadcast(mediaScanIntent);
    }
}

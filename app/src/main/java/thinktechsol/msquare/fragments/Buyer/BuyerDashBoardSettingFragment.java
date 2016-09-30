package thinktechsol.msquare.fragments.Buyer;

import android.app.Activity;
import android.app.Fragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.preference.PreferenceManager;
import android.provider.MediaStore;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.io.File;
import java.util.ArrayList;
import java.util.Calendar;

import thinktechsol.msquare.R;
import thinktechsol.msquare.activities.UserTypeActivity;
import thinktechsol.msquare.globels.globels;
import thinktechsol.msquare.model.Buyer.BuyerDetailsModel;
import thinktechsol.msquare.services.buyer.BuyerAddImageSetting;
import thinktechsol.msquare.services.buyer.GetBuyerDetails;
import thinktechsol.msquare.services.buyer.UpdateDeviceInfoService;
import thinktechsol.msquare.utils.Constant;

public class BuyerDashBoardSettingFragment extends Fragment {

    TextView buyer_title, emailText;
    RelativeLayout titlebarlayout, buyer_detials_layout, buyer_title_layout, buyer_email_layout, buyer_logout_layout;
    ImageView userImage;

    TextView logoutText;
    ImageView logoutImg;

    SharedPreferences preferences;
    SharedPreferences.Editor editor;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.activity_buyer_deshboard_setting, container, false);

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
//
        buyer_logout_layout = (RelativeLayout) v.findViewById(R.id.buyer_logout_layout);
        buyer_email_layout.setLayoutParams(AppLayoutParam(10.00f, 100f, 2, 0, 0, 0, buyer_email_layout));

        buyer_title = (TextView) v.findViewById(R.id.buyer_title);
        emailText = (TextView) v.findViewById(R.id.emailText);

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

        logoutText.setOnClickListener(new View.OnClickListener() {
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
                startActivity(i);
                getActivity().finish();
                Toast.makeText(getActivity(), "You have successfully logged out!", Toast.LENGTH_SHORT).show();
            }
        });
        logoutImg.setOnClickListener(new View.OnClickListener() {
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
                startActivity(i);
                getActivity().finish();
                Toast.makeText(getActivity(), "You have successfully logged out!", Toast.LENGTH_SHORT).show();
            }
        });

        return v;
    }

    public void fillProductListWithData(ArrayList<BuyerDetailsModel> BuyerDetails) {

        try {
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
        }
    }
}

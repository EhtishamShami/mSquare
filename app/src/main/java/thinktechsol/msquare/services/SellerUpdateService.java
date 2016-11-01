package thinktechsol.msquare.services;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

import thinktechsol.msquare.activities.SellerViewStaffActivity;
import thinktechsol.msquare.utils.Constant;
//import org.json..parser.JSONParser;

public class SellerUpdateService {

    private static final String TAG_SUCCESS = "success";

    String _url = "updateSellerAccount";
    Context ctx;
    ProgressDialog progressDialog;
    AlertDialog NotFoundDialog;
    Context ref;
    private static final String TAG = "SellerUpdateService";

    String id, fromTime, toTime, fname, lname, mobileNo, phoneNo, address;
    String isStaffStatus;

    public SellerUpdateService(final Context ctx, Context ref, String id, String fromTime, String toTime, String fname,
                               String lname, String mobileNo, String phoneNo, String address) {
        this.ctx = ctx;
        this.ref = ref;

        this.id = id;
        this.fromTime = fromTime;
        this.toTime = toTime;
        this.fname = fname;
        this.lname = lname;
        this.mobileNo = mobileNo;
        this.phoneNo = phoneNo;
        this.address = address;

        progressDialog = new ProgressDialog(ctx);
        progressDialog.setMessage("Processing Please wait...");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setCancelable(false);
        progressDialog.show();

        AlertDialog.Builder builder = new AlertDialog.Builder(ctx);
        builder.setMessage("Plesae try again! Internet problem Or Unregistered Email address");
        builder.setTitle("Not Found");
        builder.setIcon(android.R.drawable.ic_dialog_alert);

        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        });
        NotFoundDialog = builder.create();
        NotFoundDialog.setCancelable(false);

//        isStaffStatus = "updateStaffDetails";
        new UpdateSellerDetail().execute("0");
    }

    /**
     * Background Async Task to fetch all services
     */
    class UpdateSellerDetail extends AsyncTask<String, String, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        protected String doInBackground(String... input) {
            try {
                URL url = new URL(Constant.baseUrl + _url);
                Log.e(TAG, "UpdateSeller url=" + url);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoInput(true);
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setReadTimeout(30000);
                OutputStream outputStream = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));

//                String post_data;
//                if (isStaffStatus.equals("updateStatus")) {
//                    post_data =
//                            URLEncoder.encode("id", "UTF-8") + "=" + URLEncoder.encode(id, "UTF-8")
//                                    + "&" + URLEncoder.encode("status", "UTF-8") + "=" + URLEncoder.encode(status, "UTF-8");
//                } else {
                String post_data =
                        URLEncoder.encode("fName", "UTF-8") + "=" + URLEncoder.encode(fname, "UTF-8")
                                + "&" + URLEncoder.encode("lName", "UTF-8") + "=" + URLEncoder.encode(lname, "UTF-8")
                                + "&" + URLEncoder.encode("mobileNo", "UTF-8") + "=" + URLEncoder.encode(mobileNo, "UTF-8")
                                + "&" + URLEncoder.encode("phoneNo", "UTF-8") + "=" + URLEncoder.encode(phoneNo, "UTF-8")
                                + "&" + URLEncoder.encode("address", "UTF-8") + "=" + URLEncoder.encode(address, "UTF-8")
                                + "&" + URLEncoder.encode("fromTime", "UTF-8") + "=" + URLEncoder.encode(fromTime, "UTF-8")
                                + "&" + URLEncoder.encode("toTime", "UTF-8") + "=" + URLEncoder.encode(toTime, "UTF-8")
                                + "&" + URLEncoder.encode("id", "UTF-8") + "=" + URLEncoder.encode(id, "UTF-8");
//                }
                bufferedWriter.write(post_data);

                bufferedWriter.close();
                outputStream.close();

                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "iso-8859-1"));

                String result = "";
                String line;
                while ((line = bufferedReader.readLine()) != null) {
                    result += line;
                }
                bufferedReader.close();
                inputStream.close();
                Log.e(TAG, "UpdateStaff result=" + result);
                return result;

            } catch (MalformedURLException e) {
                Log.e(TAG, "UpdateStaff error=" + e);
                progressDialog.dismiss();
                return null;
            } catch (Exception e) {
                Log.e(TAG, "UpdateStaff Exception=" + e);
                progressDialog.dismiss();
                return null;
            }
        }


        protected void onPostExecute(String response) {
            Log.e(TAG, "UpdateSeller PostExec" + response);
            if (response != null && response.contains("true")) {
                //ArrayList<GetSellerStaffModel> StaffDetails = returnParsedJsonObject(response);
//                returnParsedJsonObject(response);
//                Log.e(TAG, "getServiceSellers list pr1212o" + list.size());
                // ref.onStaffAdded(true);
//                Toast.makeText(ctx, "Record is Updated", Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
//                ref.onForgotPassRequestSubmitted();
            } else {
                progressDialog.dismiss();
                NotFoundDialog.show();
            }
        }
    }
}

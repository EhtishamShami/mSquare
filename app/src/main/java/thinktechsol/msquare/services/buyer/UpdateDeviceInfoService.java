package thinktechsol.msquare.services.buyer;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import thinktechsol.msquare.activities.buyer.ViewBuyerProDetailActivity;
import thinktechsol.msquare.utils.Constant;
//import org.json..parser.JSONParser;

public class UpdateDeviceInfoService {

    private static final String TAG = "UpdateDeviceInfoService";

    String _url = "updateDeviceInfo/";
    Context ctx;
    ProgressDialog progressDialog;
    AlertDialog NotFoundDialog;
    ViewBuyerProDetailActivity ref;
    String buyerOrSellerId, userType, udid;
    String deviceType = "android";

    public UpdateDeviceInfoService(final Context ctx, String userType, String buyerOrSellerId, String udid) {
        this.ctx = ctx;
        this.ref = ref;
        this.buyerOrSellerId = buyerOrSellerId;
        this.userType = userType;
        this.udid = udid;

        progressDialog = new ProgressDialog(ctx);
        progressDialog.setMessage("Please wait...");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setCancelable(false);
//        progressDialog.show();

        AlertDialog.Builder builder = new AlertDialog.Builder(ctx);
        builder.setMessage("Plesae try again! Internet problem Or Wrong keywords");
        builder.setTitle("Not Found");
        builder.setIcon(android.R.drawable.ic_dialog_alert);

        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        });
        NotFoundDialog = builder.create();
        NotFoundDialog.setCancelable(false);

        new updateDeviceInfo().execute();
    }


//    private ArrayList<RegisterModel> returnParsedJsonObject(String result) {
//        ArrayList<RegisterModel> registerationResponseList = new ArrayList<RegisterModel>();
//        try {
//            JSONObject parentObject = new JSONObject(result);
//
//            JSONObject parentJSONObjDetails = parentObject.getJSONObject("results");
//
//            String response = parentJSONObjDetails.getString("response");
//            JSONObject childJSonObj = parentJSONObjDetails.getJSONObject("data");
//
//            Log.e("BuyerRegistration", "BuyerRegistration childJSonObj=" + childJSonObj);
//
//            String location = childJSonObj.getString("location");
//            String status = childJSonObj.getString("status");
//            String googlePlus = childJSonObj.getString("googlePlus");
//            String state = childJSonObj.getString("state");
//            String lName = childJSonObj.getString("lName");
//            String udid = childJSonObj.getString("udid");
//            String password = childJSonObj.getString("password");
//            String fName = childJSonObj.getString("fName");
//            String phoneNo = childJSonObj.getString("phoneNo");
//            String houseNo = childJSonObj.getString("houseNo");
//            String id = childJSonObj.getString("id");
//            String twitter = childJSonObj.getString("twitter");
//            String area = childJSonObj.getString("area");
//            String email = childJSonObj.getString("email");
//            String facebook = childJSonObj.getString("facebook");
//            String streetNo = childJSonObj.getString("streetNo");
//            String account = childJSonObj.getString("account");
//            String datetime = childJSonObj.getString("datetime");
//            String thumb = childJSonObj.getString("thumb");
//
//            Log.e("UpdateDeviceInfoService", "UpdateDeviceInfoService Response Is=" + response);
//            RegisterModel registerModel = new RegisterModel(location, status, googlePlus, state, lName, udid, password, fName, phoneNo, houseNo, id, twitter, area, email, facebook, streetNo, account, datetime, thumb);
//            registerationResponseList.add(registerModel);
//
//        } catch (JSONException e) {
//            Log.e("UpdateDeviceInfoService", "JSONExc ParsedJsonObject=" + e);
//            e.printStackTrace();
//            NotFoundDialog.show();
//        }
//        return registerationResponseList;
//    }


    /**
     * Background Async Task to fetch all jobs
     */
    class updateDeviceInfo extends AsyncTask<String, String, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        protected String doInBackground(String... input) {
            try {

                URL url = new URL(Constant.baseUrl + _url + "/" + userType + "/" + buyerOrSellerId + "/" + udid + "/" + deviceType);
                Log.e(TAG, "updateDeviceInfo url is=" + url);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("POST");
//                httpURLConnection.setDoInput(true);
                httpURLConnection.setDoOutput(false);
                httpURLConnection.setReadTimeout(30000);

                OutputStream outputStream = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));

//                String post_data = URLEncoder.encode("sellerId", "UTF-8") + "=" + URLEncoder.encode(sellerId, "UTF-8")
//                        + "&" + URLEncoder.encode("serviceId", "UTF-8") + "=" + URLEncoder.encode(serviceId, "UTF-8")
//                        + "&" + URLEncoder.encode("buyerId", "UTF-8") + "=" + URLEncoder.encode(buyerId, "UTF-8")
//                        + "&" + URLEncoder.encode("productId", "UTF-8") + "=" + URLEncoder.encode(productId, "UTF-8")
//                        + "&" + URLEncoder.encode("reviewTitle", "UTF-8") + "=" + URLEncoder.encode(reviewTitle, "UTF-8")
//                        + "&" + URLEncoder.encode("reviewDescription", "UTF-8") + "=" + URLEncoder.encode(reviewDescription, "UTF-8");
//                bufferedWriter.write(post_data);
                bufferedWriter.close();
                outputStream.close();

                Log.e(TAG, "add to favourite list result=" + httpURLConnection.getResponseMessage());

                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "iso-8859-1"));

                String result = "";
                String line;
                while ((line = bufferedReader.readLine()) != null) {
                    result += line;
                }
                bufferedReader.close();
                inputStream.close();

                return result;

            } catch (MalformedURLException e) {
                Log.e(TAG, "MalformedURLException=" + e);
//                progressDialog.dismiss();
                return null;
            } catch (Exception e) {
                Log.e(TAG, "exception Exception=" + e);
//                progressDialog.dismiss();
                return null;
            }
        }

        protected void onPostExecute(String response) {
            if (response != null) {
                //ArrayList<RegisterModel> list = returnParsedJsonObject(response);
                Log.e(TAG, "update device info service response is=" + response);
//                progressDialog.dismiss();
//                ref.addToWishListServiceResponse(list);
                //Toast.makeText(ctx, "device info" + response, Toast.LENGTH_SHORT).show();

            } else {
               // NotFoundDialog.show();
            }
        }
    }
}

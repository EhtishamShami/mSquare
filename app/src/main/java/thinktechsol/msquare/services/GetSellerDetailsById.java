package thinktechsol.msquare.services;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import thinktechsol.msquare.fragments.SellerDashBoardSettingFragment;
import thinktechsol.msquare.model.SellerDetailsByIdModel;
import thinktechsol.msquare.utils.Constant;
//import org.json..parser.JSONParser;

public class GetSellerDetailsById {

    private static final String TAG_SUCCESS = "success";

    String _url = "getSellerDetailsById/";
    Context ctx;
    ProgressDialog progressDialog;
    AlertDialog NotFoundDialog;
    SellerDashBoardSettingFragment ref;
    private static final String TAG = "GetSellerDetailsById";
    String sellerId;

    public GetSellerDetailsById(final Context ctx, SellerDashBoardSettingFragment ref, String sellerId) {
        this.ctx = ctx;
        this.ref = ref;
        this.sellerId = sellerId;
        progressDialog = new ProgressDialog(ctx);
        progressDialog.setMessage("Fetching Records Please wait...");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setCancelable(false);
        progressDialog.show();

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


        new getSellerDetail().execute("0");
    }


    private ArrayList<SellerDetailsByIdModel> returnParsedJsonObject(String result) {

        JSONObject resultObject = null;
        JSONArray jsonArray = null;
        ArrayList<SellerDetailsByIdModel> SellerDetailsList = new ArrayList<SellerDetailsByIdModel>();

        try {
            JSONObject parentObject = new JSONObject(result);

            JSONObject parentJSONObj = parentObject.getJSONObject("results");

            boolean isResponse = parentJSONObj.getBoolean("response");
            Log.e(TAG, "getSellerDetail reponse is=" + isResponse);

            String JsonBuyerDetails = parentJSONObj.getString("data");

            if (!JsonBuyerDetails.equals("false")) {

                JSONObject sellerDetails = parentJSONObj.getJSONObject("data");

                //seller rating json object
                JSONObject JsonObjSellerDetails = sellerDetails.getJSONObject("sellerRatings");

                //getting seller details start
                String ratingObjSD = "not available";
                String ratingSD = "not available";
                ratingObjSD = JsonObjSellerDetails.getString("rating");
                if (ratingObjSD.contains("null"))
                    ratingSD = "not available";
                else
                    ratingSD = ratingObjSD;


                //String sellerRatings = sellerDetails.getString("sellerRatings");
                String logo = sellerDetails.getString("logo");
                String status = sellerDetails.getString("status");
                String tradeNo = sellerDetails.getString("tradeNo");
                String documents = sellerDetails.getString("documents");
                String lName = sellerDetails.getString("lName");
                String fromTime = sellerDetails.getString("fromTime");
                String companyName = sellerDetails.getString("companyName");
                String password = sellerDetails.getString("password");
                String toTime = sellerDetails.getString("toTime");
                String fName = sellerDetails.getString("fName");
                String id = sellerDetails.getString("id");
                String phoneNo = sellerDetails.getString("phoneNo");
                String email = sellerDetails.getString("email");
                String address = sellerDetails.getString("address");
                String deviceType = sellerDetails.getString("deviceType");
                String deviceToken = sellerDetails.getString("deviceToken");
                String description = sellerDetails.getString("description");
                String activationCode = sellerDetails.getString("activationCode");
                String service = sellerDetails.getString("service");
                String longitude = sellerDetails.getString("longitude");
                String latitude = sellerDetails.getString("latitude");
                String datetime = sellerDetails.getString("datetime");
                String mobileNo = sellerDetails.getString("mobileNo");


                Log.e(TAG, "rating of seller=" + ratingSD);
                SellerDetailsList.add(new SellerDetailsByIdModel(
                        ratingSD,  logo,  status,  tradeNo,  documents,  lName,  fromTime,  companyName,  password,  toTime,  fName,  id,  phoneNo,  email,  address,  deviceType,  deviceToken,  description,  activationCode,  service,  longitude,  latitude,  datetime,  mobileNo
                ));

            }
        } catch (JSONException e) {
            Log.e(TAG, "JSONExc getStaff_time=" + e);
            e.printStackTrace();
            NotFoundDialog.show();
        }
        return SellerDetailsList;
    }


    /**
     * Background Async Task to fetch all services
     */
    class getSellerDetail extends AsyncTask<String, String, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        protected String doInBackground(String... input) {
            try {
                URL url = new URL(Constant.baseUrl + _url + sellerId);
                Log.e(TAG, "getSellerDetails url=" + url);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("GET");
                httpURLConnection.setDoInput(true);
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setReadTimeout(30000);
                OutputStream outputStream = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
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
                Log.e(TAG, "getSellerDetail result=" + result);
                return result;

            } catch (MalformedURLException e) {
                Log.e(TAG, "MalformedURLException=" + e);
                progressDialog.dismiss();
                return null;
            } catch (Exception e) {
                Log.e(TAG, "exception Exception=" + e);
                progressDialog.dismiss();
                return null;
            }
        }


        protected void onPostExecute(String response) {
            Log.e(TAG, "getSellerDetail list PostExec" + response);
            if (response != null) {
                ArrayList<SellerDetailsByIdModel> SellerDetails = returnParsedJsonObject(response);
//                returnParsedJsonObject(response);
//                Log.e(TAG, "getServiceSellers list pr1212o" + list.size());
                ref.fillProductListWithData(SellerDetails);
                progressDialog.dismiss();
            } else {
                NotFoundDialog.show();
            }
        }
    }
}

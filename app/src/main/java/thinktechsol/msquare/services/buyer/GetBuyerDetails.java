package thinktechsol.msquare.services.buyer;

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

import thinktechsol.msquare.fragments.Buyer.BuyerDashBoardSettingFragment;
import thinktechsol.msquare.model.Buyer.BuyerDetailsModel;
import thinktechsol.msquare.utils.Constant;
//import org.json..parser.JSONParser;

public class GetBuyerDetails {

    private static final String TAG_SUCCESS = "success";

    String _url = "getBuyerDetailsById/";
    Context ctx;
    ProgressDialog progressDialog;
    AlertDialog NotFoundDialog;
    BuyerDashBoardSettingFragment ref;
    private static final String TAG = "GetBuyerDetails";
    String buyerId;

    public GetBuyerDetails(final Context ctx, BuyerDashBoardSettingFragment ref, String buyerId) {
        this.ctx = ctx;
        this.ref = ref;
        this.buyerId = buyerId;
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


        new getStaff().execute("0");
    }


    private ArrayList<BuyerDetailsModel> returnParsedJsonObject(String result) {

        JSONObject resultObject = null;
        JSONArray jsonArray = null;
        ArrayList<BuyerDetailsModel> BuyerDetailsList = new ArrayList<BuyerDetailsModel>();

        try {
            JSONObject parentObject = new JSONObject(result);

            JSONObject parentJSONObj = parentObject.getJSONObject("results");

            boolean isResponse = parentJSONObj.getBoolean("response");
            Log.e(TAG, "getStaff_time reponse is=" + isResponse);

            String JsonBuyerDetails = parentJSONObj.getString("data");

            if (!JsonBuyerDetails.equals("false")) {

                JSONObject buyerDetails = parentJSONObj.getJSONObject("data");


                String location = buyerDetails.getString("location");
                String status = buyerDetails.getString("status");
                String state = buyerDetails.getString("state");
                String lName = buyerDetails.getString("lName");
                String udid = buyerDetails.getString("udid");
                String password = buyerDetails.getString("password");
                String googlePlus = buyerDetails.getString("googlePlus");
                String fName = buyerDetails.getString("fName");
                String phoneNo = buyerDetails.getString("phoneNo");
                String houseNo = buyerDetails.getString("houseNo");
                String id = buyerDetails.getString("id");
                String twitter = buyerDetails.getString("twitter");
                String area = buyerDetails.getString("area");
                String email = buyerDetails.getString("email");
                String deviceType = buyerDetails.getString("deviceType");
                String deviceToken = buyerDetails.getString("deviceToken");
                String facebook = buyerDetails.getString("facebook");
                String streetNo = buyerDetails.getString("streetNo");
                String datetime = buyerDetails.getString("datetime");
                String thumb = buyerDetails.getString("thumb");


                BuyerDetailsList.add(new BuyerDetailsModel(location, status, state, lName, udid, password, googlePlus, fName, phoneNo, houseNo, id, twitter, area, email, deviceType, deviceToken, facebook, streetNo, datetime, thumb
                ));

            }
        } catch (JSONException e) {
            Log.e(TAG, "JSONExc getStaff_time=" + e);
            e.printStackTrace();
            NotFoundDialog.show();
        }
        return BuyerDetailsList;
    }


    /**
     * Background Async Task to fetch all services
     */
    class getStaff extends AsyncTask<String, String, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        protected String doInBackground(String... input) {
            try {
                URL url = new URL(Constant.baseUrl + _url + buyerId);
                Log.e(TAG, "getBuyerDetails url=" + url);
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
                Log.e(TAG, "getBuyerDetails result=" + result);
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
            Log.e(TAG, "getBuyerDetails list PostExec" + response);
            if (response != null) {
                ArrayList<BuyerDetailsModel> BuyerDetails = returnParsedJsonObject(response);
//                returnParsedJsonObject(response);
//                Log.e(TAG, "getServiceSellers list pr1212o" + list.size());
                ref.fillProductListWithData(BuyerDetails);
                progressDialog.dismiss();
            } else {
                NotFoundDialog.show();
            }
        }
    }
}

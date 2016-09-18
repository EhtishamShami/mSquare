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

import thinktechsol.msquare.activities.SellerViewStaffActivity;
import thinktechsol.msquare.model.GetSellerStaffModel;
import thinktechsol.msquare.utils.Constant;
//import org.json..parser.JSONParser;

public class GetSellerStaffService {

    private static final String TAG_SUCCESS = "success";

    String _url = "getStaff/";
    Context ctx;
    ProgressDialog progressDialog;
    AlertDialog NotFoundDialog;
    SellerViewStaffActivity ref;
    private static final String TAG = "GetSellerStaffService";
    String sellerId;

    public GetSellerStaffService(final Context ctx, SellerViewStaffActivity ref, String sellerId) {
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


        new getStaffDetail().execute("0");
    }


    private ArrayList<GetSellerStaffModel> returnParsedJsonObject(String result) {

        JSONObject resultObject = null;
        JSONArray jsonArray = null;

        ArrayList<GetSellerStaffModel> StaffDetailsList = new ArrayList<GetSellerStaffModel>();

        try {
            JSONObject parentObject = new JSONObject(result);

            JSONObject parentJSONObj = parentObject.getJSONObject("results");

            boolean isResponse = parentJSONObj.getBoolean("response");
            Log.e(TAG, "getSellerDetail reponse is=" + isResponse);

            String JsonBuyerDetails = parentJSONObj.getString("data");

            if (!JsonBuyerDetails.equals("false")) {

//                JSONObject staffDetails = parentJSONObj.getJSONObject("data");

                JSONArray staffDetailsArray = parentJSONObj.getJSONArray("data");

                for (int i = 0; i < staffDetailsArray.length(); i++) {
                    JSONObject staffDetailsJSONObj = (JSONObject) staffDetailsArray.get(i);

                    String id = staffDetailsJSONObj.getString("id");
                    String name = staffDetailsJSONObj.getString("name");
                    String sellerId = staffDetailsJSONObj.getString("sellerId");

                    StaffDetailsList.add(new GetSellerStaffModel(id, sellerId, name));
                }

            }
        } catch (JSONException e) {
            Log.e(TAG, "JSONExc getStaff_time=" + e);
            e.printStackTrace();
            NotFoundDialog.show();
        }
        return StaffDetailsList;
    }


    /**
     * Background Async Task to fetch all services
     */
    class getStaffDetail extends AsyncTask<String, String, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        protected String doInBackground(String... input) {
            try {
                URL url = new URL(Constant.baseUrl + _url + sellerId);
                Log.e(TAG, "getStaffDetail url=" + url);
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
                Log.e(TAG, "getStaffDetail result=" + result);
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
            Log.e(TAG, "getStaffDetail list PostExec" + response);
            if (response != null) {
                ArrayList<GetSellerStaffModel> StaffDetails = returnParsedJsonObject(response);
//                returnParsedJsonObject(response);
//                Log.e(TAG, "getServiceSellers list pr1212o" + list.size());
                ref.fillProductListWithData(StaffDetails);
                progressDialog.dismiss();
            } else {
                NotFoundDialog.show();
            }
        }
    }
}

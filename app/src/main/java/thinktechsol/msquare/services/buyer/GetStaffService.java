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

import thinktechsol.msquare.R;
import thinktechsol.msquare.activities.buyer.BuyerReservationActivity;
import thinktechsol.msquare.model.Buyer.BuyerGetStaffModel;
import thinktechsol.msquare.utils.Constant;
//import org.json..parser.JSONParser;

public class GetStaffService {

    private static final String TAG_SUCCESS = "success";

    String _url = "getStaff/";
    Context ctx;
    ProgressDialog progressDialog;
    AlertDialog NotFoundDialog;
    BuyerReservationActivity ref;
    private static final String TAG = "GetStaffService";
    String sellerId;

    public GetStaffService(final Context ctx, BuyerReservationActivity ref, String sellerId) {
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


        new getStaff().execute("0");
    }


    private ArrayList<BuyerGetStaffModel> returnParsedJsonObject(String result) {

        JSONObject resultObject = null;
        JSONArray jsonArray = null;
        ArrayList<BuyerGetStaffModel> staffList = new ArrayList<BuyerGetStaffModel>();

        try {
            JSONObject parentObject = new JSONObject(result);

            JSONObject parentJSONObj = parentObject.getJSONObject("results");

            boolean isResponse = parentJSONObj.getBoolean("response");
            Log.e(TAG, "getStaff_time reponse is=" + isResponse);

            String JsonStringProduct = parentJSONObj.getString("data");

            if (!JsonStringProduct.equals("false")) {

                JSONArray JsonArrayProduct = parentJSONObj.getJSONArray("data");
                for (int i = 0; i < JsonArrayProduct.length(); i++) {
                    JSONObject childJsonObj = (JSONObject) JsonArrayProduct.get(i);

                    String id = childJsonObj.getString("id");
                    String name = childJsonObj.getString("name");
                    String sellerId = childJsonObj.getString("sellerId");

                    staffList.add(new BuyerGetStaffModel(R.drawable.avatar, id, name, sellerId));
                }
            }
        } catch (JSONException e) {
            Log.e(TAG, "JSONExc getStaff_time=" + e);
            e.printStackTrace();
            NotFoundDialog.show();
        }
        return staffList;
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
                URL url = new URL(Constant.baseUrl + _url + sellerId);
                Log.e(TAG, "getStaff_time url=" + url);
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
                Log.e(TAG, "getStaff_time result=" + result);
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
            if (response != null) {
                ArrayList<BuyerGetStaffModel> list = returnParsedJsonObject(response);
//                returnParsedJsonObject(response);
//                Log.e(TAG, "getServiceSellers list pr1212o" + list.size());
                ref.fillProductListWithData(list);
                progressDialog.dismiss();
            } else {
                NotFoundDialog.show();
            }
        }
    }
}

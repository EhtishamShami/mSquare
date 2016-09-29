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

import thinktechsol.msquare.activities.buyer.BuyerFilterActivity;
import thinktechsol.msquare.fragments.Buyer.BuyerDashBoardSettingFragment;
import thinktechsol.msquare.model.Buyer.BuyerDetailsModel;
import thinktechsol.msquare.model.Buyer.CategoryModel;
import thinktechsol.msquare.utils.Constant;
//import org.json..parser.JSONParser;

public class GetBuyerFilterationCategory {

    private static final String TAG_SUCCESS = "success";

    String _url = "getServices/";
    Context ctx;
    ProgressDialog progressDialog;
    AlertDialog NotFoundDialog;
    BuyerFilterActivity ref;
    private static final String TAG = "GetBuyerDetails";
    String id;

    public GetBuyerFilterationCategory(final Context ctx, BuyerFilterActivity ref, String id) {
        this.ctx = ctx;
        this.ref = ref;
        this.id = id;
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


    private ArrayList<CategoryModel> returnParsedJsonObject(String result) {

        JSONObject resultObject = null;
        JSONArray jsonArray = null;
        ArrayList<CategoryModel> BuyerDetailsList = new ArrayList<CategoryModel>();

        try {
            JSONObject parentObject = new JSONObject(result);

            JSONObject parentJSONObj = parentObject.getJSONObject("results");

            boolean isResponse = parentJSONObj.getBoolean("response");
            Log.e(TAG, "getStaff_time reponse is=" + isResponse);

            String JsonBuyerDetails = parentJSONObj.getString("data");

            if (!JsonBuyerDetails.equals("false")) {

                JSONArray buyerDetails = parentJSONObj.getJSONArray("data");
                for (int i = 0; i < buyerDetails.length(); i++) {
                    JSONObject childJsonObj = (JSONObject) buyerDetails.get(i);

                    String id = childJsonObj.getString("id");
                    String name = childJsonObj.getString("name");

                    BuyerDetailsList.add(new CategoryModel(id, name));
                }
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
                URL url = new URL(Constant.baseUrl + _url + id);
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
            Log.e(TAG, "get filteration list PostExec" + response);
            if (response != null) {
                ArrayList<CategoryModel> BuyerDetails = returnParsedJsonObject(response);
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

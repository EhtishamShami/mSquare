package thinktechsol.msquare.services;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Context;
import android.content.DialogInterface;
import android.net.Uri;
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

import thinktechsol.msquare.activities.AddOrViewProActivity;
import thinktechsol.msquare.activities.buyer.HomeActivity;
import thinktechsol.msquare.model.Buyer.GetServicesModel;
import thinktechsol.msquare.model.Response.ProductImages;
import thinktechsol.msquare.model.Response.getSellerProductsResponse;
import thinktechsol.msquare.model.SellerLogInResponse;
import thinktechsol.msquare.utils.Constant;
//import org.json..parser.JSONParser;

public class getServices {

    private static final String TAG_SUCCESS = "success";

    String _url = "getServices/";
    Context ctx;
    ProgressDialog progressDialog;
    AlertDialog NotFoundDialog;
    HomeActivity ref;
    private static final String TAG = "GetServicesModel";

    //    globels.getGlobelRef().sellerlogin.id
    public getServices(final Context ctx, HomeActivity ref) {
        this.ctx = ctx;
        this.ref = ref;

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


        new getAllServicesDetialAsync().execute("0");
    }


    private ArrayList<GetServicesModel> returnParsedJsonObject(String result) {

        JSONObject resultObject = null;
        JSONArray jsonArray = null;
        ArrayList<GetServicesModel> allServices = new ArrayList<GetServicesModel>();


        try {
            JSONObject parentObject = new JSONObject(result);

            JSONObject parentJSONObjDetails = parentObject.getJSONObject("results");
            JSONArray productArray = parentJSONObjDetails.getJSONArray("data");
            Log.e(TAG, "JSONArray lenght" + productArray.length());
            for (int i = 0; i < productArray.length(); i++) {
                JSONObject childJsonObj = (JSONObject) productArray.get(i);
                String id = childJsonObj.getString("id");
                String sellerId = childJsonObj.getString("status");
                String serviceId = childJsonObj.getString("description");
                String description = childJsonObj.getString("name");
                String title = childJsonObj.getString("parent");
                String price = childJsonObj.getString("thumb");
                String deliveryTime = childJsonObj.getString("categoryType");

                allServices.add(new GetServicesModel(id, sellerId, serviceId, description, title, price, deliveryTime));
            }

        } catch (JSONException e) {
            Log.e(TAG, "JSONExc ParsedJsonObject=" + e);
            e.printStackTrace();
            NotFoundDialog.show();
        }
        return allServices;
    }


    /**
     * Background Async Task to fetch all services
     */
    class getAllServicesDetialAsync extends AsyncTask<String, String, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        protected String doInBackground(String... input) {
            try {
                //String sellerId = input[0];
                URL url = new URL(Constant.baseUrl + _url);
                Log.e(TAG, "GetServicesModel url=" + url);
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
                Log.e(TAG, "GetServicesModel result is=" + result);
                return result;

            } catch (MalformedURLException e) {
                Log.e("sellerLogIn", "MalformedURLException=" + e);
                progressDialog.dismiss();
                return null;
            } catch (Exception e) {
                Log.e("sellerLogIn", "exception Exception=" + e);
                progressDialog.dismiss();
                return null;
            }
        }

        protected void onPostExecute(String response) {
            if (response != null) {
                ArrayList<GetServicesModel> list = returnParsedJsonObject(response);
                Log.e(TAG, "getService list" + list.size());
                ref.fillProductListWithData(list);
                progressDialog.dismiss();
            } else {
                NotFoundDialog.show();
            }
        }
    }
}

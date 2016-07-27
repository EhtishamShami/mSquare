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

import thinktechsol.msquare.activities.buyer.HomeActivity;
import thinktechsol.msquare.activities.buyer.SalonDetailsActivity;
import thinktechsol.msquare.fragments.Buyer.BuyerServiceSellersList;
import thinktechsol.msquare.model.Buyer.GetServicesModel;
import thinktechsol.msquare.model.Buyer.getServiceSellersModel;
import thinktechsol.msquare.utils.Constant;
//import org.json..parser.JSONParser;

public class getServiceSellers {

    private static final String TAG_SUCCESS = "success";

    String _url = "getServiceSellers/";
    Context ctx;
    ProgressDialog progressDialog;
    AlertDialog NotFoundDialog;
    SalonDetailsActivity ref;
    private static final String TAG = "getServiceSellers";
    String latitude;
    String longitude;
    String sellerServiceId;

    //    globels.getGlobelRef().sellerlogin.id
    public getServiceSellers(final Context ctx, SalonDetailsActivity ref, String sellerServiceId, String latitude, String longitude) {
        this.ctx = ctx;
        this.ref = ref;
        this.sellerServiceId = sellerServiceId;
        this.latitude = latitude;
        this.longitude = longitude;
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


        new getSellerServiceDetialAsync().execute("0");
    }


    private ArrayList<getServiceSellersModel> returnParsedJsonObject(String result) {

        JSONObject resultObject = null;
        JSONArray jsonArray = null;
        ArrayList<getServiceSellersModel> serviceSellers = new ArrayList<getServiceSellersModel>();

        try {
            JSONObject parentObject = new JSONObject(result);

            JSONObject parentJSONObjDetails = parentObject.getJSONObject("results");
            JSONArray productArray = parentJSONObjDetails.getJSONArray("data");
            Log.e(TAG, "JSONArray lenght" + productArray.length());
            for (int i = 0; i < productArray.length(); i++) {
                JSONObject childJsonObj = (JSONObject) productArray.get(i);

                String mobileNo = childJsonObj.getString("mobileNo");
                String logo = childJsonObj.getString("logo");
                String status = childJsonObj.getString("status");
                String tradeNo = childJsonObj.getString("tradeNo");
                String documents = childJsonObj.getString("documents");
                String lName = childJsonObj.getString("lName");
                String companyName = childJsonObj.getString("name");
                String password = childJsonObj.getString("password");
                String fName = childJsonObj.getString("fName");

                //String productRating = childJsonObj.getString("productRating");

//                String ratingObj = "null";
//                String rating = "not available";
//                //ratingObj = childJsonObj.getString("productRating");
//                if(!childJsonObj.isNull("productRating")){
//                    JSONObject rateJsonObj = new JSONObject(ratingObj);
//                    rating = rateJsonObj.getString("rating");
//
//                    Log.e(TAG,"productRating rr="+rating);
//
////                    if(rating.contains("null"))
////                        rating = "not available";
//                }


                //getting ratting from service
                String ratingObj = "null";
                String rating = "not available";
                ratingObj = childJsonObj.getString("sellerRating");
                if (!ratingObj.contains("null")) {
                    JSONObject rateJsonObj = new JSONObject(ratingObj);
                    rating = rateJsonObj.getString("rating");
                    if(rating.contains("null"))
                        rating = "not available";
                }
                Log.e(TAG,"productRating rr="+rating);
                //getting ratting end

                String id = childJsonObj.getString("id");
                String phoneNo = childJsonObj.getString("phoneNo");
                String distance = childJsonObj.getString("distance");
                String email = childJsonObj.getString("email");
                String address = childJsonObj.getString("address");
                String description = childJsonObj.getString("description");
                String activationCode = childJsonObj.getString("activationCode");
                String service = childJsonObj.getString("service");
                String longitude = childJsonObj.getString("longitude");
                String latitude = childJsonObj.getString("latitude");
                String datetime = childJsonObj.getString("datetime");

                serviceSellers.add(new getServiceSellersModel(mobileNo,  logo,  status,  tradeNo,  documents,  lName,  companyName,  password,  fName,  rating,  id,  phoneNo,  distance,  email,  address,  description,  activationCode,  service,  longitude,  latitude,  datetime));
            }

        } catch (JSONException e) {
            Log.e(TAG, "JSONExc ParsedJsonObject=" + e);
            e.printStackTrace();
            NotFoundDialog.show();
        }
        return serviceSellers;
    }


    /**
     * Background Async Task to fetch all services
     */
    class getSellerServiceDetialAsync extends AsyncTask<String, String, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        protected String doInBackground(String... input) {
            try {
                //String sellerId = input[0];
                URL url = new URL(Constant.baseUrl + _url + sellerServiceId +"/"+ latitude +"/"+ longitude);
                Log.e(TAG, "getServiceSellers url=" + url);
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
                Log.e(TAG, "getServiceSellers result is=" + result);
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
                ArrayList<getServiceSellersModel> list = returnParsedJsonObject(response);
                Log.e(TAG, "getServiceSellers list" + list.size());
                ref.fillProductListWithData(list);
                progressDialog.dismiss();
            } else {
                NotFoundDialog.show();
            }
        }
    }
}

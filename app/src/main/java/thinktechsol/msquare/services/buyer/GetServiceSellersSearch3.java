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
import java.net.URLEncoder;
import java.util.ArrayList;

import thinktechsol.msquare.activities.buyer.SalonDetailsActivity;
import thinktechsol.msquare.model.Buyer.getServiceSellersModel;
import thinktechsol.msquare.utils.Constant;
//import org.json..parser.JSONParser;

public class GetServiceSellersSearch3 {

    private static final String TAG_SUCCESS = "success";

    String _url = "getServiceSellers/";
    Context ctx;
    ProgressDialog progressDialog;
    AlertDialog NotFoundDialog;
    SalonDetailsActivity ref;
    private static final String TAG = "getServiceSellersSearch";
    String latitude;
    String longitude;
    String sellerServiceId;
    String searchStrings;
    String searchStr, distance, priceFrom, priceTo, fromTime, toTime, categories;

    //    globels.getGlobelRef().sellerlogin.id
    public GetServiceSellersSearch3(final Context ctx, SalonDetailsActivity ref, String sellerServiceId, String latitude, String longitude, String searchStrings,
                                    String distance, String priceFrom, String priceTo, String fromTime, String toTime, String categories) {
        this.ctx = ctx;
        this.ref = ref;
        this.distance = distance;
        this.priceFrom = priceFrom;
        this.priceTo = priceTo;
        this.fromTime = fromTime;
        this.toTime = toTime;
        this.categories = categories;
        this.searchStrings = searchStrings;
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
            String productArrayStr = parentJSONObjDetails.getString("data");
            if (!productArrayStr.equals("false")) {
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
                    String companyName = childJsonObj.getString("companyName");
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
                        if (rating.contains("null"))
                            rating = "not available";
                    }
                    Log.e(TAG, "productRating rr=" + rating);
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

                    serviceSellers.add(new getServiceSellersModel(mobileNo, logo, status, tradeNo, documents, lName, companyName, password, fName, rating, id, phoneNo, distance, email, address, description, activationCode, service, longitude, latitude, datetime));
                }
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
//                URL url = new URL(Constant.baseUrl + _url + sellerServiceId + "/" + latitude + "/" + longitude);
                URL url = new URL(Constant.baseUrl + _url + sellerServiceId+ "/" + latitude + "/" + longitude);
                Log.e(TAG, "getServiceSellersSearch Search url=" + url);
                //Log.e(TAG, "search parameters are=" + searchStrings + " , " + distance + " , " + priceFrom + " , " + priceTo + " , " + fromTime + " , " + toTime + " , " + categories);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoInput(true);
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setReadTimeout(30000);
                OutputStream outputStream = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));

                Log.e(TAG, "getServiceSellersSearch Search url=" + distance);
                String post_data;
//                if (searchStrings.length() > 0) {
//                    post_data =
//                            URLEncoder.encode("search", "UTF-8") + "=" + URLEncoder.encode(searchStrings, "UTF-8")
//                    ;
//                } else {
                    post_data =

                            URLEncoder.encode("distance", "UTF-8") + "=" + URLEncoder.encode(""+distance, "UTF-8")
                                    + "&" + URLEncoder.encode("priceFrom", "UTF-8") + "=" + URLEncoder.encode(""+priceFrom, "UTF-8")
                                    + "&" + URLEncoder.encode("toPrice", "UTF-8") + "=" + URLEncoder.encode(""+priceTo, "UTF-8")
                                    + "&" + URLEncoder.encode("toTime", "UTF-8") + "=" + URLEncoder.encode(""+toTime, "UTF-8")
                                    + "&" + URLEncoder.encode("fromTime", "UTF-8") + "=" + URLEncoder.encode(""+fromTime, "UTF-8")
                                     + "&" + URLEncoder.encode("categories", "UTF-8") + "=" + URLEncoder.encode(""+categories, "UTF-8")
                    ;
//                }
//
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
                Log.e(TAG, "getServiceSellersSearch result is=" + result);
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
            Log.e(TAG, "arshad post execute" + response);
            Log.e(TAG, "arshad post execute" + Constant.baseUrl + _url + sellerServiceId + "/" + latitude + "/" + longitude);
            if (response != null) {
                ArrayList<getServiceSellersModel> list = returnParsedJsonObject(response);
                Log.e(TAG, "getServiceSellersSearch list size" + list.size());
                if (ref != null)
                    ref.searchResult(list);
                progressDialog.dismiss();
            } else {
                NotFoundDialog.show();
            }
        }
    }
}

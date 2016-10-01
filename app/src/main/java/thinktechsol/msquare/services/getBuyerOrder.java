package thinktechsol.msquare.services;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

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

import thinktechsol.msquare.activities.buyer.BuyerOrdersActivity;
import thinktechsol.msquare.model.Buyer.GetBuyersOrdersModel;
import thinktechsol.msquare.model.BuyerDetailsForOrder;
import thinktechsol.msquare.model.SellerDetailsForOrder;
import thinktechsol.msquare.utils.Constant;
//import org.json..parser.JSONParser;

public class getBuyerOrder {

    private static final String TAG_SUCCESS = "success";

    String _url = "getBuyerOrders/";
    Context ctx;
    ProgressDialog progressDialog;
    AlertDialog NotFoundDialog;
    BuyerOrdersActivity ref;
    private static final String TAG = "getSellerOrders";
    String buyerId;
    String orderType;


    public getBuyerOrder(final Context ctx, BuyerOrdersActivity ref, String buyerId, String orderType) {
        this.ctx = ctx;
        this.ref = ref;
        this.buyerId = buyerId;
        this.orderType = orderType;
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

        new getBuyersOrdersAsync().execute("0");
    }


    private ArrayList<GetBuyersOrdersModel> returnParsedJsonObject(String result) {

        JSONObject resultObject = null;
        JSONArray jsonArray = null;
        ArrayList<GetBuyersOrdersModel> sellerOrdersList = new ArrayList<GetBuyersOrdersModel>();

        try {
            JSONObject parentObject = new JSONObject(result);

            JSONObject parentJSONObj = parentObject.getJSONObject("results");

            boolean isResponse = parentJSONObj.getBoolean("response");
            Log.e(TAG, "isResponse=" + isResponse);
//            JSONObject JsonDataObject = parentJSONObj.getJSONObject("data");

            if (isResponse == true) {
                JSONArray JsonArrayOrders = parentJSONObj.getJSONArray("data");
//          getting product details start
                for (int i = 0; i < JsonArrayOrders.length(); i++) {
                    JSONObject childJsonObj = (JSONObject) JsonArrayOrders.get(i);

                    String serviceRequestTime = childJsonObj.getString("serviceRequestTime");
                    String staffId = childJsonObj.getString("staffId");
                    String lastModified = childJsonObj.getString("lastModified");
                    String status = childJsonObj.getString("status");
                    String adminStatus = childJsonObj.getString("adminStatus");
                    String categoryType = childJsonObj.getString("categoryType");
                    String dated = childJsonObj.getString("dated");
                    String buyerId = childJsonObj.getString("buyerId");
                    String id = childJsonObj.getString("id");
                    String sellerId = childJsonObj.getString("sellerId");
                    String noOfServices = childJsonObj.getString("noOfServices");
                    String extraRemarks = childJsonObj.getString("extraRemarks");
                    String totalPrice = childJsonObj.getString("totalPrice");

                    // sellers details
                    String sellerDetails = childJsonObj.getString("sellerDetails");
                    JSONObject sellerDetailsJsonObj = new JSONObject(sellerDetails);

                    String logo = sellerDetailsJsonObj.getString("logo");
                    String statusS = sellerDetailsJsonObj.getString("status");
                    String tradeNo = sellerDetailsJsonObj.getString("tradeNo");
                    String documents = sellerDetailsJsonObj.getString("documents");
                    String lName = sellerDetailsJsonObj.getString("lName");
                    String fromTime = sellerDetailsJsonObj.getString("fromTime");
                    String companyName = sellerDetailsJsonObj.getString("companyName");
                    String password = sellerDetailsJsonObj.getString("password");
                    String toTime = sellerDetailsJsonObj.getString("toTime");
                    String fName = sellerDetailsJsonObj.getString("fName");
                    String idS = sellerDetailsJsonObj.getString("id");
                    String phoneNo = sellerDetailsJsonObj.getString("phoneNo");
                    String email = sellerDetailsJsonObj.getString("email");
                    String address = sellerDetailsJsonObj.getString("address");
                    String description = sellerDetailsJsonObj.getString("description");
                    String activationCode = sellerDetailsJsonObj.getString("activationCode");
                    String service = sellerDetailsJsonObj.getString("service");
                    String longitude = sellerDetailsJsonObj.getString("longitude");
                    String latitude = sellerDetailsJsonObj.getString("latitude");
                    String datetime = sellerDetailsJsonObj.getString("datetime");
                    String mobileNo = sellerDetailsJsonObj.getString("mobileNo");

                    //getting ratting from service
                    String ratingObj = "null";
                    String rating = "not available";
                    ratingObj = sellerDetailsJsonObj.getString("sellerRatings");
                    if (!ratingObj.contains("null")) {
                        JSONObject rateJsonObj = new JSONObject(ratingObj);
                        rating = rateJsonObj.getString("rating");
                        if (rating.contains("null"))
                            rating = "not available";
                    }
                    //getting ratting from service end

                    SellerDetailsForOrder objSeller = new SellerDetailsForOrder(rating, logo, statusS, tradeNo, documents, lName, fromTime, companyName, password, toTime, fName, idS, phoneNo, email, address, description, activationCode, service, longitude, latitude, datetime, mobileNo);
                    // sellers details

                    // buyer details
                    String buyerDetails = childJsonObj.getString("buyerDetails");
                    JSONObject buyerDetailsJsonObj = new JSONObject(buyerDetails);

                    String locationB = buyerDetailsJsonObj.getString("location");
                    String statusB = buyerDetailsJsonObj.getString("status");
                    String stateB = buyerDetailsJsonObj.getString("state");
                    String lNameB = buyerDetailsJsonObj.getString("lName");
                    String udidB = buyerDetailsJsonObj.getString("udid");
                    String passwordB = buyerDetailsJsonObj.getString("password");
                    String googlePlusB = buyerDetailsJsonObj.getString("googlePlus");
                    String fNameB = buyerDetailsJsonObj.getString("fName");
                    String phoneNoB = buyerDetailsJsonObj.getString("phoneNo");
                    String houseNoB = buyerDetailsJsonObj.getString("houseNo");
                    String idB = buyerDetailsJsonObj.getString("id");
                    String twitterB = buyerDetailsJsonObj.getString("twitter");
                    String areaB = buyerDetailsJsonObj.getString("area");
                    String emailB = buyerDetailsJsonObj.getString("email");
                    String facebookB = buyerDetailsJsonObj.getString("facebook");
                    String streetNoB = buyerDetailsJsonObj.getString("streetNo");
                    String datetimeB = buyerDetailsJsonObj.getString("datetime");
                    String thumbB = buyerDetailsJsonObj.getString("thumb");

                    BuyerDetailsForOrder objBuyer = new BuyerDetailsForOrder(locationB, statusB, stateB, lNameB, udidB, passwordB, googlePlusB, fNameB, phoneNoB, houseNoB, idB, twitterB, areaB, emailB, facebookB, streetNoB, datetimeB, thumbB);
                    // buyer details

                    GetBuyersOrdersModel buyerOrder = new GetBuyersOrdersModel(serviceRequestTime, staffId, lastModified, status, adminStatus, categoryType, dated, buyerId, id, sellerId, noOfServices, extraRemarks, totalPrice, objSeller, objBuyer);
                    sellerOrdersList.add(buyerOrder);
                }
            } else {
                Toast.makeText(ctx, "No Orders Found", Toast.LENGTH_SHORT).show();
            }

        } catch (JSONException e) {
            Log.e(TAG, "JSONExc ParsedJsonObject=" + e);
//            e.printStackTrace();
//            NotFoundDialog.show();

        }
        return sellerOrdersList;
    }


    /**
     * Background Async Task to fetch all services
     */
    class getBuyersOrdersAsync extends AsyncTask<String, String, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        protected String doInBackground(String... input) {
            try {
                //String sellerId = input[0];
//                URL url = new URL(Constant.baseUrl + _url + sellerServiceId + "/" + serviceSellerId + "/" + latitude + "/" + longitude);
                URL url = new URL(Constant.baseUrl + _url + buyerId + "/" + orderType);
                Log.e(TAG, "getSellerOrders url=" + url);
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
                Log.e(TAG, "getServiceSellers result pro=" + result);
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
                ArrayList<GetBuyersOrdersModel> list = returnParsedJsonObject(response);
                Log.e(TAG, "getServiceSellers list=" + list.size());
                ref.fillProductListWithData(list);
                progressDialog.dismiss();
            } else {
                NotFoundDialog.show();
            }
        }
    }
}

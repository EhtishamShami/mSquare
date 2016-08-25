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

import thinktechsol.msquare.activities.buyer.BuyerFavouriteActivity;
import thinktechsol.msquare.activities.buyer.BuyerWishListActivity;
import thinktechsol.msquare.model.Buyer.BuyerFavouriteListModel;
import thinktechsol.msquare.model.Buyer.BuyerWishListModel;
import thinktechsol.msquare.model.Buyer.ProductReviews;
import thinktechsol.msquare.model.Buyer.SellerDetailsFav;
import thinktechsol.msquare.model.Buyer.SellerDetailsWL;
import thinktechsol.msquare.model.Buyer.getServiceSellersProductModel;
import thinktechsol.msquare.utils.Constant;
//import org.json..parser.JSONParser;

public class getBuyerFavouriteService {

    private static final String TAG_SUCCESS = "success";

    String _url = "getFavourites/";
    Context ctx;
    ProgressDialog progressDialog;
    AlertDialog NotFoundDialog;
    BuyerFavouriteActivity ref;
    private static final String TAG = "getBuyerFavouriteSer";
    String latitude;
    String longitude;
    String sellerServiceId;
    String serviceSellerProductId;

    public getBuyerFavouriteService(final Context ctx, BuyerFavouriteActivity ref, String sellerServiceId) {
        this.ctx = ctx;
        this.ref = ref;
        this.sellerServiceId = sellerServiceId;

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


        new getBuyerWishlist().execute("0");
    }


    private ArrayList<BuyerFavouriteListModel> returnParsedJsonObject(String result) {

        JSONObject resultObject = null;
        JSONArray jsonArray = null;
        ArrayList<getServiceSellersProductModel> serviceSellersProduct = new ArrayList<getServiceSellersProductModel>();
        ArrayList<BuyerFavouriteListModel> productsList = new ArrayList<BuyerFavouriteListModel>();
        ArrayList<ProductReviews> productReviewsList = new ArrayList<ProductReviews>();


        try {
            JSONObject parentObject = new JSONObject(result);

            JSONObject parentJSONObj = parentObject.getJSONObject("results");

            boolean isResponse = parentJSONObj.getBoolean("response");
            Log.e(TAG, "getServiceSellers is reponse is=" + isResponse);

            String JsonStringProduct = parentJSONObj.getString("data");

            if (!JsonStringProduct.equals("false")) {

                JSONArray JsonArrayProduct = parentJSONObj.getJSONArray("data");
                for (int i = 0; i < JsonArrayProduct.length(); i++) {
                    JSONObject childJsonObj = (JSONObject) JsonArrayProduct.get(i);

                    String id = childJsonObj.getString("id");
                    String sellerId = childJsonObj.getString("buyerId");
//                    String serviceId = childJsonObj.getString("serviceId");
//                    String productId = childJsonObj.getString("productId");
                    String buyerId = childJsonObj.getString("buyerId");

                    JSONObject JsonObjSellerDetails = childJsonObj.getJSONObject("sellerDetails");

                    String logo = JsonObjSellerDetails.getString("logo");
                    String status = JsonObjSellerDetails.getString("status");
                    String tradeNo = JsonObjSellerDetails.getString("tradeNo");
                    String documents = JsonObjSellerDetails.getString("documents");
                    String lName = JsonObjSellerDetails.getString("lName");
                    //String fromTime = JsonObjSellerDetails.getString("fromTime");
                    String companyName = JsonObjSellerDetails.getString("companyName");
                    String password = JsonObjSellerDetails.getString("password");
                   // String toTime = JsonObjSellerDetails.getString("toTime");
                    String fName = JsonObjSellerDetails.getString("fName");
                    String idSD = JsonObjSellerDetails.getString("id");
                    String phoneNo = JsonObjSellerDetails.getString("phoneNo");
                    String email = JsonObjSellerDetails.getString("email");
                    String address = JsonObjSellerDetails.getString("address");
                    String description = JsonObjSellerDetails.getString("description");
                    String activationCode = JsonObjSellerDetails.getString("activationCode");
                    String service = JsonObjSellerDetails.getString("service");
                    String longitude = JsonObjSellerDetails.getString("longitude");
                    String latitude = JsonObjSellerDetails.getString("latitude");
                    String datetime = JsonObjSellerDetails.getString("datetime");
                    String mobileNo = JsonObjSellerDetails.getString("mobileNo");

                    //getting ratting from service
                    String ratingObj = "null";
                    String rating = "not available";
                    ratingObj = JsonObjSellerDetails.getString("sellerRatings");
                    if (!ratingObj.contains("null")) {
                        JSONObject rateJsonObj = new JSONObject(ratingObj);
                        rating = rateJsonObj.getString("rating");
                        if (rating.contains("null"))
                            rating = "not available";
                    }

                    SellerDetailsFav sellerDetailsObj = new SellerDetailsFav( rating,  logo,  status,  tradeNo,  documents,  lName,  companyName,  password,  fName,  idSD,  phoneNo,  email,  address,
                            description,  activationCode,  service,  longitude,  latitude,  datetime,  mobileNo);

                    //Log.e(TAG, "Review of each product ="+i+" == " + productReviewsListInsidePro.size());
                    productsList.add(new BuyerFavouriteListModel(id,  sellerId,  buyerId, sellerDetailsObj));
                }
            }
        } catch (JSONException e) {
            Log.e(TAG, "JSONExc ParsedJsonObject=" + e);
            e.printStackTrace();
            NotFoundDialog.show();
        }
        return productsList;
    }


    /**
     * Background Async Task to fetch all services
     */
    class getBuyerWishlist extends AsyncTask<String, String, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        protected String doInBackground(String... input) {
            try {
                //String buyerId = input[0];
//                URL url = new URL(Constant.baseUrl + _url + sellerServiceId + "/" + serviceSellerId + "/" + latitude + "/" + longitude);
                URL url = new URL(Constant.baseUrl + _url + sellerServiceId);
                Log.e(TAG, "getServiceSellers proooo url=" + url);
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
                ArrayList<BuyerFavouriteListModel> list = returnParsedJsonObject(response);
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

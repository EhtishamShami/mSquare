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
import thinktechsol.msquare.model.Response.ProductImages;
import thinktechsol.msquare.model.Response.getSellerProductsResponse;
import thinktechsol.msquare.model.SellerLogInResponse;
import thinktechsol.msquare.utils.Constant;
//import org.json..parser.JSONParser;

public class GetSellerProducts {

    private static final String TAG_SUCCESS = "success";

    String _url = "getSellerProducts/";
    Context ctx;
    ProgressDialog progressDialog;
    String sellerId;
    AlertDialog NotFoundDialog;
    AddOrViewProActivity ref;
    private static final String TAG = "GetSellerProducts";

    //    globels.getGlobelRef().sellerlogin.id
    public GetSellerProducts(final Context ctx, AddOrViewProActivity ref, String sellerId) {
        this.ctx = ctx;
        this.ref = ref;
        this.sellerId = sellerId;
        progressDialog = new ProgressDialog(ctx);
        progressDialog.setMessage("Searching Please wait...");
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

        //registerBuyer(sellerId);
        Log.e("GetSellerProducts", "sellerId=" + sellerId);
        new getSellerProductDetialAsync().execute(sellerId);
    }


    private ArrayList<getSellerProductsResponse> returnParsedJsonObject(String result) {

//        List<SellerLogInResponse> jsonObject = new ArrayList<SellerLogInResponse>();
        JSONObject resultObject = null;
        JSONArray jsonArray = null;
        SellerLogInResponse sellerLogInResponse = null;
        ArrayList<getSellerProductsResponse> product = new ArrayList<getSellerProductsResponse>();


        try {
            JSONObject parentObject = new JSONObject(result);
            Log.e(TAG, "JSON parentObject" + parentObject.length());
            JSONObject parentJSONObjDetails = parentObject.getJSONObject("results");
            JSONArray productArray = parentJSONObjDetails.getJSONArray("data");
            Log.e(TAG, "JSONArray lenght" + productArray.length());
            for (int i = 0; i < productArray.length(); i++) {
                JSONObject childJsonObj = (JSONObject) productArray.get(i);
                String id = childJsonObj.getString("id");
                String sellerId = childJsonObj.getString("sellerId");
                String serviceId = childJsonObj.getString("serviceId");
                String description = childJsonObj.getString("description");
                String title = childJsonObj.getString("title");
                String price = childJsonObj.getString("price");
                String deliveryTime = childJsonObj.getString("deliveryTime");
                String dateTime = childJsonObj.getString("dateTime");
                String status = childJsonObj.getString("status");

                Log.e(TAG, "inside loop test" + title);

                //getting ratting from service
                String ratingObj = "null";
                String rating = "not available";
                ratingObj = childJsonObj.getString("productRating");
                if (!ratingObj.contains("null")) {
                    JSONObject rateJsonObj = new JSONObject(ratingObj);
                    rating = rateJsonObj.getString("rating");
                    if(rating.contains("null"))
                        rating = "not available";
                }
                //getting ratting end

                //getting images from service
                String productImages = childJsonObj.getString("productImages");
                ProductImages imgesObj = null;
                ArrayList<ProductImages> productImagesList = new ArrayList<ProductImages>();

                if (productImages.equals("false")) {
                    Uri uri = Uri.parse(ContentResolver.SCHEME_ANDROID_RESOURCE + "://" + ctx.getPackageName() + "/drawable/" + "pro_title");
                    imgesObj = new ProductImages("" + uri);
                    productImagesList.add(new ProductImages("" + uri));
                } else {
                    JSONArray productImagesArray = childJsonObj.getJSONArray("productImages");

                    for (int img = 0; img < productImagesArray.length(); img++) {
                        JSONObject ImgJsonObj = (JSONObject) productImagesArray.get(img);

                        String ImgId = ImgJsonObj.getString("id");
                        String sellerProductId = ImgJsonObj.getString("sellerProductId");
                        String image = ImgJsonObj.getString("image");

                        imgesObj = new ProductImages(ImgId, sellerProductId, Constant.imgbaseUrl + image);
                        productImagesList.add(new ProductImages(ImgId, sellerProductId, Constant.imgbaseUrl + image));
                    }
                }
                //getting images from service end

//                String productReviews = childJsonObj.getString("productReviews");


                product.add(new getSellerProductsResponse(id, sellerId, serviceId, description, title, price, deliveryTime, dateTime, status, imgesObj, productImagesList, rating/*, productReviews, productRating*/));
            }

        } catch (JSONException e) {
            Log.e(TAG, "JSONExc ParsedJsonObject=" + e);
            e.printStackTrace();
            NotFoundDialog.show();
        }
        return product;
    }


    /**
     * Background Async Task to fetch all jobs
     */
    class getSellerProductDetialAsync extends AsyncTask<String, String, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        protected String doInBackground(String... input) {
            try {
                String sellerId = input[0];
                URL url = new URL(Constant.baseUrl + _url + sellerId + "");
                Log.e("getSellerProducts url", "getSellerProducts url=" + url);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("POST");
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
                Log.e("sellerLogIn", "result is=" + result);
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
                ArrayList<getSellerProductsResponse> list = returnParsedJsonObject(response);
                Log.e(TAG, "sellerProduct list" + list.size());
                ref.fillProductListWithData(list);
                progressDialog.dismiss();
            } else {
                NotFoundDialog.show();
            }
        }
    }
}

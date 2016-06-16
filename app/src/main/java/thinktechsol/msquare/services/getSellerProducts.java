package thinktechsol.msquare.services;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.nfc.Tag;
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
import java.util.List;

import thinktechsol.msquare.activities.AddProActivity;
import thinktechsol.msquare.fragments.SellerAddProductFragment;
import thinktechsol.msquare.globels.globels;
import thinktechsol.msquare.model.Response.ProductImages;
import thinktechsol.msquare.model.Response.ProductRating;
import thinktechsol.msquare.model.Response.ProductReviews;
import thinktechsol.msquare.model.Response.getSellerProductsResponse;
import thinktechsol.msquare.model.SellerLogInResponse;
import thinktechsol.msquare.model.SellerProductItem;
import thinktechsol.msquare.utils.Constant;
//import org.json..parser.JSONParser;

public class GetSellerProducts {

    private static final String TAG_SUCCESS = "success";

    String _url = "getSellerProducts/";
    Context ctx;
    ProgressDialog progressDialog;
    String sellerId;
    AlertDialog NotFoundDialog;
    AddProActivity ref;
    private static final String TAG = "GetSellerProducts";

    //    globels.getGlobelRef().sellerlogin.id
    public GetSellerProducts(final Context ctx, AddProActivity ref, String sellerId) {
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

        //getSellerProductDetail(sellerId);
        Log.e("GetSellerProducts", "sellerId=" + sellerId);
        new getSellerProductDetialAsync().execute(sellerId);
    }

//    public void getSellerProductDetail(String sellerId) {
//        this.sellerId = sellerId;
//        Log.e("sellerLogIn", "code=" + sellerId);
//
//    }

    private ArrayList<getSellerProductsResponse> returnParsedJsonObject(String result) {

//        List<SellerLogInResponse> jsonObject = new ArrayList<SellerLogInResponse>();
        JSONObject resultObject = null;
        JSONArray jsonArray = null;
        SellerLogInResponse sellerLogInResponse = null;
        ArrayList<getSellerProductsResponse> product = new ArrayList<getSellerProductsResponse>();

        try {
            JSONObject parentObject = new JSONObject(result);

            JSONObject parentJSONObjDetails = parentObject.getJSONObject("results");
            JSONArray productArray = parentJSONObjDetails.getJSONArray("data");
            Log.e(TAG,"JSONArray lenght"+productArray.length());
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

                String productImages = childJsonObj.getString("productImages");
                if(productImages.equals("false")){
                    Log.e(TAG, "productImages in parsing object t=" + productImages);
                }else {
                    JSONArray productImagesArray = childJsonObj.getJSONArray("productImages");
                    for (int img = 0; img < productImagesArray.length(); img++) {
                        JSONObject ImgJsonObj = (JSONObject) productArray.get(img);
                        String ImgId = childJsonObj.getString("id");
                        String sellerProductId = childJsonObj.getString("sellerProductId");
                        String image = childJsonObj.getString("image");
//                        new ProductImages();
                    }
                }

//                String productImages = childJsonObj.getString("productImages");
//                String productReviews = childJsonObj.getString("productReviews");
//                String productRating = childJsonObj.getString("productRating");

                product.add(new getSellerProductsResponse(id, sellerId, serviceId, description, title, price, deliveryTime, dateTime, status /*, productImages, productReviews, productRating*/ ));
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
                Log.e(TAG,"sellerProduct list"+list.size());
                ref.fillProductListWithData(list);
                progressDialog.dismiss();
            } else {
                NotFoundDialog.show();
            }
        }
    }
}

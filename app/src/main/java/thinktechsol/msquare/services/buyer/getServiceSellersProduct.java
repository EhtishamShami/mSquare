package thinktechsol.msquare.services.buyer;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Context;
import android.content.DialogInterface;
import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;

import com.google.android.gms.analytics.ecommerce.Product;

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

import thinktechsol.msquare.activities.buyer.ServiceSellerDetailActivity;
import thinktechsol.msquare.model.Buyer.ProductImages;
import thinktechsol.msquare.model.Buyer.ProductReviews;
import thinktechsol.msquare.model.Buyer.Products;
import thinktechsol.msquare.model.Buyer.SellerInfo;
import thinktechsol.msquare.model.Buyer.ServiceDetails;
import thinktechsol.msquare.model.Buyer.getServiceSellersProductModel;
import thinktechsol.msquare.utils.Constant;
//import org.json..parser.JSONParser;

public class getServiceSellersProduct {

    private static final String TAG_SUCCESS = "success";

    String _url = "getServiceSellerProducts/";
    Context ctx;
    ProgressDialog progressDialog;
    AlertDialog NotFoundDialog;
    ServiceSellerDetailActivity ref;
    private static final String TAG = "getServiceSellersPro";
    String latitude;
    String longitude;
    String sellerServiceId;
    String serviceSellerProductId;

    public getServiceSellersProduct(final Context ctx, ServiceSellerDetailActivity ref, String sellerServiceId, String serviceSellerProductId, String latitude, String longitude) {
        this.ctx = ctx;
        this.ref = ref;
        this.sellerServiceId = sellerServiceId;
        this.serviceSellerProductId = serviceSellerProductId;
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


    private ArrayList<getServiceSellersProductModel> returnParsedJsonObject(String result) {

        JSONObject resultObject = null;
        JSONArray jsonArray = null;
        ArrayList<getServiceSellersProductModel> serviceSellersProduct = new ArrayList<getServiceSellersProductModel>();

        try {
            JSONObject parentObject = new JSONObject(result);

            JSONObject parentJSONObj = parentObject.getJSONObject("results");

            boolean isResponse = parentJSONObj.getBoolean("response");

            JSONObject JsonDataObject = parentJSONObj.getJSONObject("data");

            //seller details json object
            JSONObject JsonObjSellerDetails = JsonDataObject.getJSONObject("sellerInfo");
            //product details json array
            JSONArray JsonArrayProduct = JsonDataObject.getJSONArray("products");
            Log.e(TAG, "JSONArray lenght 1" + JsonArrayProduct);

            //getting seller details start
            String ratingObjSD = "not available";
            String ratingSD = "not available";
            ratingObjSD = JsonObjSellerDetails.getString("rating");
            if (ratingSD.contains("null"))
                ratingSD = "not available";
            else
                ratingSD = ratingObjSD;

            String statusD = JsonObjSellerDetails.getString("status");
            String logoD = JsonObjSellerDetails.getString("logo");
            String tradeNoD = JsonObjSellerDetails.getString("tradeNo");
            String documentsD = JsonObjSellerDetails.getString("documents");
            String categoryTypeD = JsonObjSellerDetails.getString("categoryType");
            String lNameD = JsonObjSellerDetails.getString("lName");
            String toTimeD = JsonObjSellerDetails.getString("toTime");
            String companyNameD = JsonObjSellerDetails.getString("companyName");
            String passwordD = JsonObjSellerDetails.getString("password");
            String fromTimeD = JsonObjSellerDetails.getString("fromTime");
            String fNameD = JsonObjSellerDetails.getString("fName");
            String idD = JsonObjSellerDetails.getString("id");
            String phoneNoD = JsonObjSellerDetails.getString("phoneNo");
            String emailD = JsonObjSellerDetails.getString("email");
            String addressD = JsonObjSellerDetails.getString("address");
            String descriptionD = JsonObjSellerDetails.getString("description");
            String nameD = JsonObjSellerDetails.getString("name");
            String activationCodeD = JsonObjSellerDetails.getString("activationCode");
            String serviceD = JsonObjSellerDetails.getString("service");
            String longitudeD = JsonObjSellerDetails.getString("longitude");
            String latitudeD = JsonObjSellerDetails.getString("latitude");
            String datetimeD = JsonObjSellerDetails.getString("datetime");
            String thumbD = JsonObjSellerDetails.getString("thumb");
            String mobileNoD = JsonObjSellerDetails.getString("mobileNo");

            SellerInfo sellerDetailsObj = new SellerInfo(statusD, logoD, tradeNoD, documentsD, categoryTypeD, lNameD, fromTimeD, companyNameD, passwordD, toTimeD, ratingSD, fNameD, idD, phoneNoD, emailD, addressD, descriptionD, nameD, activationCodeD, serviceD, longitudeD, latitudeD, datetimeD, thumbD, mobileNoD);
            //getting seller details end

//            getting product details start
            for (int i = 0; i < JsonArrayProduct.length(); i++) {
                ArrayList<Products> productsList = new ArrayList<Products>();
                JSONObject childJsonObj = (JSONObject) JsonArrayProduct.get(i);

                String id = childJsonObj.getString("id");
                String sellerId = childJsonObj.getString("sellerId");
                String serviceId = childJsonObj.getString("serviceId");
                String description = childJsonObj.getString("description");
                String title = childJsonObj.getString("title");
                String price = childJsonObj.getString("price");
                String deliveryTime = childJsonObj.getString("deliveryTime");
                String dateTime = childJsonObj.getString("dateTime");
                String status = childJsonObj.getString("status");


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

                //getting ratting from service
                String ratingObj = "null";
                String rating = "not available";
                ratingObj = childJsonObj.getString("productRating");
                if (!ratingObj.contains("null")) {
                    JSONObject rateJsonObj = new JSONObject(ratingObj);
                    rating = rateJsonObj.getString("rating");
                    if (rating.contains("null"))
                        rating = "not available";
                }

                //getting images from service
                String productReviews = childJsonObj.getString("productReviews");
                ProductReviews reviewsObj = null;
                ArrayList<ProductReviews> productReviewsList = new ArrayList<ProductReviews>();

                if (productReviews.equals("false")) {
//                    Uri uri = Uri.parse(ContentResolver.SCHEME_ANDROID_RESOURCE + "://" + ctx.getPackageName() + "/drawable/" + "pro_title");
//                    imgesObj = new ProductImages("" + uri);
//                    productImagesList.add(new ProductImages("" + uri));
                } else {
                    JSONArray productReviewsArray = childJsonObj.getJSONArray("productReviews");

                    for (int rev = 0; rev < productReviewsArray.length(); rev++) {
                        JSONObject ReviewJsonObj = (JSONObject) productReviewsArray.get(rev);


                        String idR = ReviewJsonObj.getString("id");
                        String reviewTitleR = ReviewJsonObj.getString("reviewTitle");
                        String serviceIdR = ReviewJsonObj.getString("serviceId");
                        String statusR = ReviewJsonObj.getString("status");
                        String sellerIdR = ReviewJsonObj.getString("sellerId");
                        String buyerIdR = ReviewJsonObj.getString("buyerId");
                        String reviewDescriptionR = ReviewJsonObj.getString("reviewDescription");
                        String productIdR = ReviewJsonObj.getString("productId");


                        productReviewsList.add(new ProductReviews(idR, reviewTitleR, serviceIdR, statusR, sellerIdR, buyerIdR, reviewDescriptionR, productIdR));
                    }
                }
                //getting images from service end


                Products productsDetailsObject = new Products(id, rating, productImagesList, title, price, deliveryTime,
                        serviceId, status, dateTime, description, sellerId, productReviewsList,false);
                productsList.add(new Products(id, rating, productImagesList, title, price, deliveryTime, serviceId, status, dateTime, description, sellerId, productReviewsList,false));

                serviceSellersProduct.add(new getServiceSellersProductModel(sellerDetailsObj, productsList));
            }

        } catch (JSONException e) {
            Log.e(TAG, "JSONExc ParsedJsonObject=" + e);
            e.printStackTrace();
            NotFoundDialog.show();
        }
        return serviceSellersProduct;
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
//                URL url = new URL(Constant.baseUrl + _url + sellerServiceId + "/" + serviceSellerProductId + "/" + latitude + "/" + longitude);
                URL url = new URL(Constant.baseUrl + _url + serviceSellerProductId);
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
                ArrayList<getServiceSellersProductModel> list = returnParsedJsonObject(response);
                Log.e(TAG, "getServiceSellers list pr1212o" + list.size());
                ref.fillProductListWithData(list);
                progressDialog.dismiss();
            } else {
                NotFoundDialog.show();
            }
        }
    }
}

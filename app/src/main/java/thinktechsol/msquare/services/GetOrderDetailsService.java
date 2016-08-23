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
import thinktechsol.msquare.activities.ViewSellOrderDetailActivity;
import thinktechsol.msquare.model.OrderDetails.BuyerDetails;
import thinktechsol.msquare.model.OrderDetails.GetOrderDetails;
import thinktechsol.msquare.model.OrderDetails.Messages;
import thinktechsol.msquare.model.OrderDetails.OrderDetails;
import thinktechsol.msquare.model.OrderDetails.ProductDetails;
import thinktechsol.msquare.model.OrderDetails.ProductImagesOrd;
import thinktechsol.msquare.model.OrderDetails.SellerDetails;
import thinktechsol.msquare.model.OrderDetails.ServiceDetails;
import thinktechsol.msquare.model.Response.ProductImages;
import thinktechsol.msquare.model.Response.getSellerProductsResponse;
import thinktechsol.msquare.model.SellerLogInResponse;
import thinktechsol.msquare.utils.Constant;

public class GetOrderDetailsService {

    private static final String TAG_SUCCESS = "success";

    String _url = "getOrderDetails/";
    Context ctx;
    ProgressDialog progressDialog;
    String sellerId;
    AlertDialog NotFoundDialog;
    ViewSellOrderDetailActivity ref;
    private static final String TAG = "GetOrderDetailsService";

    public GetOrderDetailsService(final Context ctx, ViewSellOrderDetailActivity ref, String orderId) {
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

        Log.e(TAG, "OrderId=" + orderId);
        new getOrderDetialAsync().execute(orderId);
    }


    private ArrayList<GetOrderDetails> returnParsedJsonObject(String result) {

//        List<SellerLogInResponse> jsonObject = new ArrayList<SellerLogInResponse>();
        JSONObject resultObject = null;
        JSONArray jsonArray = null;
        SellerLogInResponse sellerLogInResponse = null;
        ArrayList<GetOrderDetails> orderDetial = new ArrayList<GetOrderDetails>();


        try {
            JSONObject parentObject = new JSONObject(result);
            Log.e(TAG, "JSON parentObject==" + parentObject.length());
            JSONObject parentJSONObjDetails = parentObject.getJSONObject("results");

            String dataObject = parentJSONObjDetails.getString("data");
            JSONObject childJSONObjDetails = new JSONObject(dataObject);
            Log.e(TAG, "JSON dataObject" + childJSONObjDetails);

            String serviceRequestTime = childJSONObjDetails.getString("serviceRequestTime");
            String staffId = childJSONObjDetails.getString("staffId");
            String lastModified = childJSONObjDetails.getString("lastModified");
            String status = childJSONObjDetails.getString("status");
            String adminStatus = childJSONObjDetails.getString("adminStatus");
            String dated = childJSONObjDetails.getString("dated");
            String buyerId = childJSONObjDetails.getString("buyerId");
            String id = childJSONObjDetails.getString("id");
            String sellerId = childJSONObjDetails.getString("sellerId");
            String extraRemarks = childJSONObjDetails.getString("extraRemarks");

            //SellerDetails start/////////////////////////////////////////////////
            JSONObject JsonObjSellerDetails = childJSONObjDetails.getJSONObject("sellerDetails");

            //getting seller ratting start
            String ratingObj = "null";
            String rating = "not available";
            ratingObj = JsonObjSellerDetails.getString("sellerRatings");
            if (!ratingObj.contains("null")) {
                JSONObject rateJsonObj = new JSONObject(ratingObj);
                rating = rateJsonObj.getString("rating");
                if (rating.contains("null"))
                    rating = "not available";
            }
            //getting seller ratting end


            String logo = JsonObjSellerDetails.getString("logo");
            String statusSD = JsonObjSellerDetails.getString("status");
            String tradeNo = JsonObjSellerDetails.getString("tradeNo");
            String documents = JsonObjSellerDetails.getString("documents");
            String lName = JsonObjSellerDetails.getString("lName");
            String fromTime = JsonObjSellerDetails.getString("fromTime");
            String companyName = JsonObjSellerDetails.getString("companyName");
            String password = JsonObjSellerDetails.getString("password");
            String toTime = JsonObjSellerDetails.getString("toTime");
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

            SellerDetails sellerDetailsObj = new SellerDetails(rating, logo, statusSD, tradeNo, documents, lName, fromTime, companyName, password, toTime, fName, idSD, phoneNo, email,
                    address, description, activationCode, service, longitude, latitude, datetime, mobileNo);
            //SellerDetails end////////////////////////////////////////////////


            //buyerDetails start///////////////////////////////////////////////
            JSONObject JsonObjBuyerDetails = childJSONObjDetails.getJSONObject("buyerDetails");

            String location = JsonObjBuyerDetails.getString("location");
            String statusBD = JsonObjBuyerDetails.getString("status");
            String state = JsonObjBuyerDetails.getString("state");
            String lNameBD = JsonObjBuyerDetails.getString("lName");
            String udid = JsonObjBuyerDetails.getString("udid");
            String passwordBD = JsonObjBuyerDetails.getString("password");
            String googlePlus = JsonObjBuyerDetails.getString("googlePlus");
            String fNameBD = JsonObjBuyerDetails.getString("fName");
            String phoneNoBD = JsonObjBuyerDetails.getString("phoneNo");
            String houseNo = JsonObjBuyerDetails.getString("houseNo");
            String idBD = JsonObjBuyerDetails.getString("id");
            String twitter = JsonObjBuyerDetails.getString("twitter");
            String area = JsonObjBuyerDetails.getString("area");
            String emailBD = JsonObjBuyerDetails.getString("email");
            String facebook = JsonObjBuyerDetails.getString("facebook");
            String streetNo = JsonObjBuyerDetails.getString("streetNo");
            String datetimeBD = JsonObjBuyerDetails.getString("datetime");
            String thumb = JsonObjBuyerDetails.getString("thumb");

            BuyerDetails buyerDetailObj = new BuyerDetails(location, statusBD, state, lNameBD, udid, passwordBD, googlePlus, fNameBD,
                    phoneNoBD, houseNo, idBD, twitter, area, emailBD, facebook, streetNo, datetimeBD, thumb);
            //buyerDetails end//////////////////////////////////////////////////


            // OrderDetails start//////////////////////////////////////////////////
            JSONArray JsonArrayOrder = childJSONObjDetails.getJSONArray("orderDetails");
            ArrayList<OrderDetails> orderDetailsList = new ArrayList<OrderDetails>();
            for (int i = 0; i < JsonArrayOrder.length(); i++) {
                JSONObject jsonObjOrder = (JSONObject) JsonArrayOrder.get(i);

                // Service details
                JSONObject JsonObjServiceDetails = jsonObjOrder.getJSONObject("serviceDetails");
                String idOD = JsonObjServiceDetails.getString("id");
                String statusOD = JsonObjServiceDetails.getString("status");
                String descriptionOD = JsonObjServiceDetails.getString("description");
                String categoryType = JsonObjServiceDetails.getString("categoryType");
                String name = JsonObjServiceDetails.getString("name");
                String parent = JsonObjServiceDetails.getString("parent");
                String thumbOD = JsonObjServiceDetails.getString("thumb");
                ServiceDetails serviceDetail = new ServiceDetails(idOD, statusOD, descriptionOD, categoryType, name, parent, thumbOD);

                // Product details
                JSONObject JsonObjProductDetails = jsonObjOrder.getJSONObject("productDetails");
                String idPD = JsonObjProductDetails.getString("id");
                String productRating = "0";//JsonObjProductDetails.getString("productRating");
                String title = JsonObjProductDetails.getString("title");
                String price = JsonObjProductDetails.getString("price");
                String deliveryTime = JsonObjProductDetails.getString("deliveryTime");
                String serviceId = JsonObjProductDetails.getString("serviceId");
                String statusPD = JsonObjProductDetails.getString("status");
                String dateTime = JsonObjProductDetails.getString("dateTime");
                String descriptionPD = JsonObjProductDetails.getString("description");
                String sellerIdPD = JsonObjProductDetails.getString("sellerId");

                String productImagesStr = JsonObjProductDetails.getString("productImages");
                ArrayList<ProductImagesOrd> productImages = new ArrayList<ProductImagesOrd>();
                Log.e(TAG, "productImagesStr=" + productImagesStr);
                if (!productImagesStr.equals("false")) {
                    JSONArray JsonArrayProImages = JsonObjProductDetails.getJSONArray("productImages");
                    Log.e(TAG, "productImagesStr length=" + JsonArrayProImages.length());
                    for (int j = 0; j < JsonArrayProImages.length(); j++) {
                        JSONObject jsonObjProImg = (JSONObject) JsonArrayProImages.get(j);

                        String idPIMG = jsonObjProImg.getString("id");
                        String sellerProductId = jsonObjProImg.getString("sellerProductId");
                        String image = jsonObjProImg.getString("image");

                        ProductImagesOrd proImages = new ProductImagesOrd(idPIMG, sellerProductId, Constant.imgbaseUrl + image);
                        productImages.add(proImages);
                    }
                } else {
                    Uri uri = Uri.parse(ContentResolver.SCHEME_ANDROID_RESOURCE + "://" + ref.getPackageName() + "/drawable/" + "dummy_user");
                    ProductImagesOrd proImages = new ProductImagesOrd("000", "000", uri.toString());
                    productImages.add(proImages);
                }
// else {
//                    productImages.add(new ProductImagesOrd("empty", "empty", "empty"));
//                }

                ProductDetails proDetail = new ProductDetails(idPD, productRating, productImages, title,
                        price, deliveryTime, serviceId, statusPD, dateTime, descriptionPD, sellerIdPD);

                //3rd element quantity
                String quantity = jsonObjOrder.getString("quantity");

                OrderDetails orderObj = new OrderDetails(serviceDetail, proDetail, quantity);

                orderDetailsList.add(orderObj);
            }
            // OrderDetails end////////////////////////////////////////////////////

            // Message start//////////////////////////////////////////////////
            String messagesStr = childJSONObjDetails.getString("messages");
            ArrayList<Messages> messagesList = new ArrayList<Messages>();
            if (!messagesStr.equals("false")) {
                JSONArray JsonArrayMessages = childJSONObjDetails.getJSONArray("messages");

                if (JsonArrayMessages.length() > 0) {
                    for (int i = 0; i < JsonArrayMessages.length(); i++) {
                        JSONObject jsonObjMessage = (JSONObject) JsonArrayMessages.get(i);
                        String messageBody = jsonObjMessage.getString("messageBody");
                        String sender = jsonObjMessage.getString("sender");
                        String idMsg = jsonObjMessage.getString("id");
                        String datedMsg = jsonObjMessage.getString("dated");
                        String orderId = jsonObjMessage.getString("orderId");

                        Messages messageObj = new Messages(messageBody, sender, idMsg, datedMsg, orderId);
                        messagesList.add(messageObj);
                    }
                }
            }
            // Message end//////////////////////////////////////////////////


            GetOrderDetails orderDetailObj = new GetOrderDetails(serviceRequestTime, orderDetailsList, staffId, lastModified, status, adminStatus,
                    sellerDetailsObj, buyerDetailObj, dated, buyerId, messagesList, id, sellerId, extraRemarks);
            orderDetial.add(orderDetailObj);

            return orderDetial;

        } catch (JSONException e) {
            Log.e(TAG, "JSONExc ParsedJsonObject=" + e);
            e.printStackTrace();
            NotFoundDialog.show();
        }
        return orderDetial;
    }


    /**
     * Background Async Task to fetch all jobs
     */
    class getOrderDetialAsync extends AsyncTask<String, String, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        protected String doInBackground(String... input) {
            try {
                String orderId = input[0];
//                String orderId = "100";
                URL url = new URL(Constant.baseUrl + _url + orderId + "");
                Log.e(TAG, "getOrderDetails url=" + url);
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
                Log.e(TAG, "result is=" + result);
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
                ArrayList<GetOrderDetails> list = returnParsedJsonObject(response);
                // Log.e(TAG, "Order list size=" + list.size());
                ref.fillProductListWithData(list);
                progressDialog.dismiss();
            } else {
                NotFoundDialog.show();
            }
        }
    }
}

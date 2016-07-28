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
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import thinktechsol.msquare.activities.SellerLoginActivity;
import thinktechsol.msquare.activities.SellersOrdersActivity;
import thinktechsol.msquare.globels.globels;
import thinktechsol.msquare.model.GetSellerOrdersModel;
import thinktechsol.msquare.model.SellerLogInResponse;
import thinktechsol.msquare.utils.Constant;
//import org.json..parser.JSONParser;

public class UpdateOrderStatus {

    private static final String TAG_SUCCESS = "success";

    String _url = "updateOrderStatus/";
    Context ctx;
    ProgressDialog progressDialog;
    String orderId;
    SellerLogInResponse parsedObject;
    String OrderStatus;
    SellersOrdersActivity ref;
    AlertDialog NotFoundDialog;
    int selectedListPosition = 0;

    public UpdateOrderStatus(final Context ctx, SellersOrdersActivity ref, String orderId, String OrderStatus, int selectedListPosition) {
        this.ctx = ctx;
        this.ref = ref;
        this.OrderStatus = OrderStatus;
        this.selectedListPosition = selectedListPosition;
        progressDialog = new ProgressDialog(ctx);
        progressDialog.setMessage("Searching Please wait...");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setCancelable(false);
        progressDialog.show();

        AlertDialog.Builder builder = new AlertDialog.Builder(ctx);
        builder.setMessage("Plesae try again! Internet problem or Wrong Code");
        builder.setTitle("Not Found");
        builder.setIcon(android.R.drawable.ic_dialog_alert);

        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        });
        NotFoundDialog = builder.create();
        NotFoundDialog.setCancelable(false);

        new doUpdateOrderStatusAsync().execute(orderId);
    }

    private SellerLogInResponse returnParsedJsonObject(String result) {

        List<SellerLogInResponse> jsonObject = new ArrayList<SellerLogInResponse>();
        JSONObject resultObject = null;
        JSONArray jsonArray = null;
        SellerLogInResponse sellerLogInResponse = null;

        try {
            JSONObject parentObject = new JSONObject(result);

            JSONObject parentJSONObjDetails = parentObject.getJSONObject("results");

            String isLoginSuccessful = parentJSONObjDetails.getString("response");

            Log.e("sellerLogIn", "isLoginSuccessful code=" + isLoginSuccessful);

            if (isLoginSuccessful.equals("true")) {
                String childObject = parentJSONObjDetails.getString("data");

                JSONObject childJSONObjDetails = new JSONObject(childObject);

                String logo = childJSONObjDetails.getString("logo");
                String status = childJSONObjDetails.getString("status");
                String documents = childJSONObjDetails.getString("documents");
                String tradeNo = childJSONObjDetails.getString("tradeNo");
                String lName = childJSONObjDetails.getString("lName");
                String companyName = childJSONObjDetails.getString("companyName");
                String password = childJSONObjDetails.getString("password");
                String fName = childJSONObjDetails.getString("fName");
                String phoneNo = childJSONObjDetails.getString("phoneNo");
                String id = childJSONObjDetails.getString("id");
                String email = childJSONObjDetails.getString("email");
                String address = childJSONObjDetails.getString("address");
                String description = childJSONObjDetails.getString("description");
                String activationCode = childJSONObjDetails.getString("activationCode");
                String service = childJSONObjDetails.getString("service");
                String longitude = childJSONObjDetails.getString("longitude");
                String latitude = childJSONObjDetails.getString("latitude");
                String datetime = childJSONObjDetails.getString("datetime");
                String mobileNo = childJSONObjDetails.getString("mobileNo");

                sellerLogInResponse = new SellerLogInResponse(logo, status, documents, tradeNo, lName, companyName, password, fName, phoneNo, id, email, address, description, activationCode, service, longitude, latitude, datetime, mobileNo);
                Log.e("sellerLogIn", "respons code=" + sellerLogInResponse.service);
            } else {
                NotFoundDialog.show();
                return null;
            }
        } catch (JSONException e) {
            Log.e("sellerLogIn", "JSONExc ParsedJsonObject=" + e);
            e.printStackTrace();
            NotFoundDialog.show();
        }

        return sellerLogInResponse;
    }

    public SellerLogInResponse getJsonArray() {
        return parsedObject;
    }


    /**
     * Background Async Task to fetch all jobs
     */
    class doUpdateOrderStatusAsync extends AsyncTask<String, String, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        protected String doInBackground(String... input) {
            try {
                String code = input[0];
                URL url = new URL(Constant.baseUrl + _url + code + "/" + OrderStatus);
                Log.e("UpdateOrderStatus", "update order status url=" + url);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoInput(true);
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setReadTimeout(30000);
                OutputStream outputStream = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
                String post_data = URLEncoder.encode("keyword", "UTF-8") + "=" + URLEncoder.encode(code, "UTF-8");
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
            Log.e("sellerLogIn", "onPostExecuter=" + response);
            if (response != null) {
                Log.e("sellerLogIn", "onPostExecuter 1=" + response);
                if (globels.getGlobelRef().orderType == "0")
                    Toast.makeText(ref, "Order In Process Now", Toast.LENGTH_SHORT).show();
                else if (globels.getGlobelRef().orderType == "1")
                    Toast.makeText(ref, "Order Completed", Toast.LENGTH_SHORT).show();
                else if (globels.getGlobelRef().orderType == "2")
                    Toast.makeText(ref, "Order Dispute Clicked", Toast.LENGTH_SHORT).show();
                else if (globels.getGlobelRef().orderType == "3")
                    Toast.makeText(ref, "Order Complete Clicked", Toast.LENGTH_SHORT).show();

                progressDialog.dismiss();
            } else {
                progressDialog.dismiss();
                NotFoundDialog.show();
            }
        }
    }
}

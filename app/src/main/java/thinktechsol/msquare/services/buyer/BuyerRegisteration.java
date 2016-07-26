package thinktechsol.msquare.services.buyer;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.util.Log;

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

import thinktechsol.msquare.activities.buyer.BuyerRegisterationActivity;
import thinktechsol.msquare.globels.globels;
import thinktechsol.msquare.model.Buyer.RegisterModel;
import thinktechsol.msquare.model.Buyer.RegisterRequestModel;
import thinktechsol.msquare.model.Response.getSellerProductsResponse;
import thinktechsol.msquare.utils.Constant;
//import org.json..parser.JSONParser;

public class BuyerRegisteration {

    private static final String TAG = "BuyerRegistration";

    String _url = "registerBuyer/";
    Context ctx;
    ProgressDialog progressDialog;
    AlertDialog NotFoundDialog;
    BuyerRegisterationActivity ref;
    ArrayList<String> selectedImagePath;
    RegisterRequestModel requestModel;

    String productDetails[];

    public BuyerRegisteration(final Context ctx, BuyerRegisterationActivity ref, RegisterRequestModel requestModel) {
        this.ctx = ctx;
        this.ref = ref;
        this.requestModel = requestModel;
        progressDialog = new ProgressDialog(ctx);
        progressDialog.setMessage("Saving Please wait...");
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

//        registerBuyer();
        new registerBuyer().execute();
    }


    private ArrayList<RegisterModel> returnParsedJsonObject(String result) {
        ArrayList<RegisterModel> registerationResponseList = new ArrayList<RegisterModel>();
        try {
            JSONObject parentObject = new JSONObject(result);

            JSONObject parentJSONObjDetails = parentObject.getJSONObject("results");

            String response = parentJSONObjDetails.getString("response");
            JSONObject childJSonObj = parentJSONObjDetails.getJSONObject("data");

            Log.e("BuyerRegistration", "BuyerRegistration childJSonObj=" + childJSonObj);

            String location = childJSonObj.getString("location");
            String status= childJSonObj.getString("status");
            String googlePlus= childJSonObj.getString("googlePlus");
            String state= childJSonObj.getString("state");
            String lName= childJSonObj.getString("lName");
            String udid= childJSonObj.getString("udid");
            String password= childJSonObj.getString("password");
            String fName= childJSonObj.getString("fName");
            String phoneNo= childJSonObj.getString("phoneNo");
            String houseNo= childJSonObj.getString("houseNo");
            String id= childJSonObj.getString("id");
            String twitter= childJSonObj.getString("twitter");
            String area= childJSonObj.getString("area");
            String email= childJSonObj.getString("email");
            String facebook= childJSonObj.getString("facebook");
            String streetNo= childJSonObj.getString("streetNo");
            String account= childJSonObj.getString("account");
            String datetime= childJSonObj.getString("datetime");
            String thumb= childJSonObj.getString("thumb");

            Log.e("BuyerRegistration", "BuyerRegistration Response Is=" + response);
            RegisterModel registerModel=new RegisterModel(location,  status,  googlePlus,  state,  lName,  udid,  password,  fName,  phoneNo,  houseNo,  id,  twitter,  area,  email,  facebook,  streetNo,  account,  datetime,  thumb);
            registerationResponseList.add(registerModel);

        } catch (JSONException e) {
            Log.e("BuyerRegistration", "JSONExc ParsedJsonObject=" + e);
            e.printStackTrace();
            NotFoundDialog.show();
        }
        return registerationResponseList;
    }


    /**
     * Background Async Task to fetch all jobs
     */
    class registerBuyer extends AsyncTask<String, String, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        protected String doInBackground(String... input) {
            try {

                URL url = new URL(Constant.baseUrl + _url);
                Log.e(TAG, "url is=" + url);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("POST");
//                httpURLConnection.setDoInput(true);
                httpURLConnection.setDoOutput(false);
                httpURLConnection.setReadTimeout(30000);

                OutputStream outputStream = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
//                String post_data = URLEncoder.encode("sellerId", "UTF-8") + "=" + URLEncoder.encode(globels.getGlobelRef().sellerlogin.id, "UTF-8")
//                        + "&" + URLEncoder.encode("serviceId", "UTF-8") + "=" + URLEncoder.encode(globels.getGlobelRef().IdForAddProduct, "UTF-8")
//                        + "&" + URLEncoder.encode("title", "UTF-8") + "=" + URLEncoder.encode("test test", "UTF-8")
//                        + "&" + URLEncoder.encode("description", "UTF-8") + "=" + URLEncoder.encode("test test", "UTF-8")
//                        + "&" + URLEncoder.encode("price", "UTF-8") + "=" + URLEncoder.encode("122", "UTF-8")
//                        + "&" + URLEncoder.encode("deliveryTime", "UTF-8") + "=" + URLEncoder.encode("50", "UTF-8");

//                Log.e("sellerLogIn", "input data is=" + globels.getGlobelRef().sellerlogin.id + " , " + globels.getGlobelRef().IdForAddProduct + " , " + productDetails[0] + " , " + productDetails[1] + " , " + productDetails[2] + " , " + productDetails[3]);

                String post_data = URLEncoder.encode("fName", "UTF-8") + "=" + URLEncoder.encode(requestModel.fName, "UTF-8")
                        + "&" + URLEncoder.encode("lName", "UTF-8") + "=" + URLEncoder.encode(requestModel.lName, "UTF-8")
                        + "&" + URLEncoder.encode("email", "UTF-8") + "=" + URLEncoder.encode(requestModel.email, "UTF-8")
                        + "&" + URLEncoder.encode("password", "UTF-8") + "=" + URLEncoder.encode(requestModel.password, "UTF-8")
                        + "&" + URLEncoder.encode("type", "UTF-8") + "=" + URLEncoder.encode(requestModel.type, "UTF-8")
                        + "&" + URLEncoder.encode("location", "UTF-8") + "=" + URLEncoder.encode(requestModel.location, "UTF-8")
                        + "&" + URLEncoder.encode("udid", "UTF-8") + "=" + URLEncoder.encode(requestModel.udid, "UTF-8");

                bufferedWriter.write(post_data);
                bufferedWriter.close();
                outputStream.close();

                Log.e(TAG, "data save result is=" + httpURLConnection.getResponseMessage());

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
                ArrayList<RegisterModel> list = returnParsedJsonObject(response);
                progressDialog.dismiss();
                ref.onRegistrationCompleted(list);

            } else {
                NotFoundDialog.show();
            }
        }
    }
}

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
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import thinktechsol.msquare.fragments.SellerAddProductFragment;
import thinktechsol.msquare.fragments.SellerCustomerFragment;
import thinktechsol.msquare.model.SellerCustomerModel;
import thinktechsol.msquare.model.SellerLogInResponse;
import thinktechsol.msquare.model.SellerProductItem;
import thinktechsol.msquare.utils.Constant;
//import org.json..parser.JSONParser;

public class GetSellerCustomerList {

    private static final String TAG_SUCCESS = "success";

    String _url = "getCustomerList/";
    Context ctx;
    ProgressDialog progressDialog;
    String sellerId;
    AlertDialog NotFoundDialog;
    SellerCustomerFragment ref;

    public GetSellerCustomerList(final Context ctx, SellerCustomerFragment ref, String sellerId) {
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

        new getSellerCustomerDetialAsync().execute(sellerId);
    }

    private ArrayList<SellerCustomerModel> returnParsedJsonObject(String result) {

//        List<SellerLogInResponse> jsonObject = new ArrayList<SellerLogInResponse>();
        JSONObject resultObject = null;
        JSONArray jsonArray = null;
        SellerLogInResponse sellerLogInResponse = null;
        ArrayList<SellerCustomerModel> customer = new ArrayList<SellerCustomerModel>();

        try {
            JSONObject parentObject = new JSONObject(result);

            JSONObject parentJSONObjDetails = parentObject.getJSONObject("results");
            JSONArray productArray = parentJSONObjDetails.getJSONArray("data");

            for (int i = 0; i < productArray.length(); i++) {
                JSONObject feedObj = (JSONObject) productArray.get(i);

                String location = feedObj.getString("location");
                String status = feedObj.getString("status");
                String state = feedObj.getString("state");
                String lName = feedObj.getString("lName");
                String udid = feedObj.getString("udid");
                String password = feedObj.getString("password");
                String googlePlus = feedObj.getString("googlePlus");
                String fName = feedObj.getString("fName");
                String phoneNo = feedObj.getString("phoneNo");
                String houseNo = feedObj.getString("houseNo");
                String id = feedObj.getString("id");
                String twitter = feedObj.getString("twitter");
                String area = feedObj.getString("area");
                String email = feedObj.getString("email");
                String facebook = feedObj.getString("facebook");
                String streetNo = feedObj.getString("streetNo");
                String datetime = feedObj.getString("datetime");
                String thumb = feedObj.getString("thumb");

                SellerCustomerModel obj = new SellerCustomerModel(location, status, state, lName, udid, password, googlePlus, fName, phoneNo, houseNo, id, twitter, area, email, facebook, streetNo, datetime, thumb);

                customer.add(obj);
            }

        } catch (JSONException e) {
            Log.e("sellerLogIn", "JSONExc ParsedJsonObject=" + e);
            e.printStackTrace();
            NotFoundDialog.show();
        }
        return customer;
    }


    /**
     * Background Async Task to fetch all jobs
     */
    class getSellerCustomerDetialAsync extends AsyncTask<String, String, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        protected String doInBackground(String... input) {
            try {
                String sellerId = input[0];
                URL url = new URL(Constant.baseUrl + _url + sellerId + "");
                Log.e("seller get product list", "url is=" + url);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoInput(true);
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setReadTimeout(30000);
                OutputStream outputStream = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
                String post_data = URLEncoder.encode("keyword", "UTF-8") + "=" + URLEncoder.encode(sellerId, "UTF-8");
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
                ArrayList<SellerCustomerModel> list = returnParsedJsonObject(response);
                ref.fill_data_to_adapter(list);
                progressDialog.dismiss();
            } else {
                NotFoundDialog.show();
            }
        }
    }
}

package thinktechsol.msquare.services.buyer;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

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

import thinktechsol.msquare.activities.buyer.BuyerLoginActivity;
import thinktechsol.msquare.activities.buyer.BuyerRegisterationActivity;
import thinktechsol.msquare.model.Buyer.BuyerLogin;
import thinktechsol.msquare.model.Buyer.RegisterModel;
import thinktechsol.msquare.model.Buyer.RegisterRequestModel;
import thinktechsol.msquare.utils.Constant;
//import org.json..parser.JSONParser;

public class BuyerCustomLogin {

    private static final String TAG = "BuyerCustomLogin";

    String _url = "loginBuyer/";
    Context ctx;
    ProgressDialog progressDialog;
    AlertDialog NotFoundDialog;
    BuyerLoginActivity ref;
    ArrayList<String> selectedImagePath;
    String email;
    String password;

    String productDetails[];

    public BuyerCustomLogin(final Context ctx, BuyerLoginActivity ref, String email, String password) {
        this.ctx = ctx;
        this.ref = ref;
        this.email = email;
        this.password = password;
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

        new customeLogin().execute();
    }


    private ArrayList<BuyerLogin> returnParsedJsonObject(String result) {
        ArrayList<BuyerLogin> logInResponseList = new ArrayList<BuyerLogin>();
        try {
            JSONObject parentObject = new JSONObject(result);

            JSONObject parentJSONObjDetails = parentObject.getJSONObject("results");

            String response = parentJSONObjDetails.getString("response");
            Log.e("BuyerCustomelog", "Buyerlogin response=" + response);

            if (response.equals("true")) {
                JSONObject childJSonObj = parentJSONObjDetails.getJSONObject("data");

                Log.e("BuyerRegistration", "BuyerRegistration childJSonObj=" + childJSonObj);

                String location = childJSonObj.getString("location");
                String status = childJSonObj.getString("status");
                String linkedin = childJSonObj.getString("googlePlus");
                String lName = childJSonObj.getString("lName");
                String udid = childJSonObj.getString("udid");
                String password = childJSonObj.getString("password");
                String fName = childJSonObj.getString("fName");
                String id = childJSonObj.getString("id");
                String twitter = childJSonObj.getString("twitter");
                String email = childJSonObj.getString("email");
                String facebook = childJSonObj.getString("facebook");
                String datetime = childJSonObj.getString("datetime");
                String thumb = childJSonObj.getString("thumb");

                Log.e("BuyerRegistration", "BuyerRegistration Response Is=" + response);
                BuyerLogin loginModel = new BuyerLogin(location, status, linkedin, lName, udid, password, fName, id, twitter, email, facebook, datetime, thumb);
                logInResponseList.add(loginModel);
            } else {
                return null;
            }

        } catch (JSONException e) {
            Log.e("BuyerRegistration", "JSONExc ParsedJsonObject=" + e);
            e.printStackTrace();
            NotFoundDialog.show();
        }
        return logInResponseList;
    }


    /**
     * Background Async Task to fetch all jobs
     */
    class customeLogin extends AsyncTask<String, String, String> {

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

                String post_data = URLEncoder.encode("email", "UTF-8") + "=" + URLEncoder.encode(email, "UTF-8")
                        + "&" + URLEncoder.encode("password", "UTF-8") + "=" + URLEncoder.encode(password, "UTF-8");

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
                ArrayList<BuyerLogin> list = returnParsedJsonObject(response);

                progressDialog.dismiss();
                if (list != null)
                    ref.onLoginCompleted(list);
                else
                    Toast.makeText(ctx, "Wrong Email or Password", Toast.LENGTH_SHORT).show();

            } else {
                NotFoundDialog.show();
            }
        }
    }
}

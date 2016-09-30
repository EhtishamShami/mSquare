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

import thinktechsol.msquare.activities.SearchFilterBYPROActivity;
import thinktechsol.msquare.activities.buyer.SalonDetailsActivity;
import thinktechsol.msquare.fragments.SellerAddProductFragment;
//import thinktechsol.msquare.fragments.SellerAddProductFragment2;
import thinktechsol.msquare.model.SellerLogInResponse;
import thinktechsol.msquare.model.SellerProductItem;
import thinktechsol.msquare.utils.Constant;
//import org.json..parser.JSONParser;

public class SellerProductListForSearch {

    private static final String TAG_SUCCESS = "success";

//    String _url = "GetServicesModel/";
    String _url = "getServices/";
    Context ctx;
    ProgressDialog progressDialog;
    String serviceid;
    AlertDialog NotFoundDialog;
    SearchFilterBYPROActivity ref;

    public SellerProductListForSearch(final Context ctx, SearchFilterBYPROActivity ref, String serviceid) {
        this.ctx = ctx;
        this.ref = ref;
        this.serviceid = serviceid;
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

        getSellerProductDetail(serviceid);
    }

    public void getSellerProductDetail(String serviceid) {
        this.serviceid = serviceid;
        Log.e("sellerLogIn", "ImageId=" + serviceid);
        new getSellerProductDetialAsync().execute(serviceid);
    }

    private ArrayList<SellerProductItem> returnParsedJsonObject(String result) {

        List<SellerLogInResponse> jsonObject = new ArrayList<SellerLogInResponse>();
        JSONObject resultObject = null;
        JSONArray jsonArray = null;
        SellerLogInResponse sellerLogInResponse = null;
        ArrayList<SellerProductItem> product = new ArrayList<SellerProductItem>();

        try {
            JSONObject parentObject = new JSONObject(result);

            JSONObject parentJSONObjDetails = parentObject.getJSONObject("results");
            JSONArray productArray = parentJSONObjDetails.getJSONArray("data");

            for (int i = 0; i < productArray.length(); i++) {
                JSONObject feedObj = (JSONObject) productArray.get(i);
                String id = feedObj.getString("id");
                String status = feedObj.getString("status");
                String description = feedObj.getString("description");
                String name = feedObj.getString("name");
                String parent = feedObj.getString("parent");
                String thumb = feedObj.getString("thumb");
                product.add(new SellerProductItem(id, status, description, name, parent, thumb));
            }

        } catch (JSONException e) {
            Log.e("sellerLogIn", "JSONExc ParsedJsonObject=" + e);
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
                String serviceid = input[0];
                URL url = new URL(Constant.baseUrl + _url + serviceid + "");
                Log.e("seller get product list", "url is=" + url);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoInput(true);
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setReadTimeout(30000);
                OutputStream outputStream = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
                String post_data = URLEncoder.encode("keyword", "UTF-8") + "=" + URLEncoder.encode(serviceid, "UTF-8");
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
                ArrayList<SellerProductItem> list = returnParsedJsonObject(response);
                ref.fill_data_to_adapter(list);
                progressDialog.dismiss();
            } else {
                NotFoundDialog.show();
            }
        }
    }
}

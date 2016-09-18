package thinktechsol.msquare.services;

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

import thinktechsol.msquare.activities.EditProActivity;
import thinktechsol.msquare.utils.Constant;
//import org.json..parser.JSONParser;

public class SellerAddProductForEditImg {

    private static final String TAG_SUCCESS = "success";

    String _url = "updateProduct/";
    Context ctx;
    ProgressDialog progressDialog;
    AlertDialog NotFoundDialog;
    EditProActivity ref;
    ArrayList<String> selectedImagePath;

    String productDetails[];

    public SellerAddProductForEditImg(final Context ctx, EditProActivity ref, String productDetails[], ArrayList<String> selectedImagePath) {
        this.ctx = ctx;
        this.ref = ref;
        this.productDetails = productDetails;
        this.selectedImagePath = selectedImagePath;
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

        getSellerProductDetail();
    }

    public void getSellerProductDetail() {
        new addSellerProductDetialAsync().execute();
    }

    private String returnParsedJsonObject(String result) {
        String id = "";
        try {
            JSONObject parentObject = new JSONObject(result);

            JSONObject parentJSONObjDetails = parentObject.getJSONObject("results");

            JSONObject childJSonObj = parentJSONObjDetails.getJSONObject("data");
            id = childJSonObj.getString("id");
            Log.e("SellerAddProduct", "sellerAddPro id is=" + id);

        } catch (JSONException e) {
            Log.e("sellerLogIn", "JSONExc ParsedJsonObject=" + e);
            e.printStackTrace();
            NotFoundDialog.show();
        }
        return id;
    }


    /**
     * Background Async Task to fetch all jobs
     */
    class addSellerProductDetialAsync extends AsyncTask<String, String, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        protected String doInBackground(String... input) {
            try {

                URL url = new URL(Constant.baseUrl + _url + Constant.singleProduct.id);
                Log.e("seller add product", "url is=" + url);
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

                String id = Constant.singleProduct.id;

                Log.e("sellerLogIn", "product id is=" + id);
                Log.e("sellerLogIn", "input data is=" + id + " , " + productDetails[0] + " , " + productDetails[1] + " , " + productDetails[2] + " , " + productDetails[3]);

//                + "&" + URLEncoder.encode("serviceId", "UTF-8") + "=" + URLEncoder.encode(globels.getGlobelRef().IdForAddProduct, "UTF-8")
//                URLEncoder.encode("sellerId", "UTF-8") + "=" + URLEncoder.encode(globels.getGlobelRef().sellerlogin.id, "UTF-8")
                String post_data =
                        URLEncoder.encode("title", "UTF-8") + "=" + URLEncoder.encode(productDetails[0], "UTF-8")
                                + "&" + URLEncoder.encode("description", "UTF-8") + "=" + URLEncoder.encode(productDetails[1], "UTF-8")
                                + "&" + URLEncoder.encode("price", "UTF-8") + "=" + URLEncoder.encode(productDetails[2], "UTF-8")
                                + "&" + URLEncoder.encode("deliveryTime", "UTF-8") + "=" + URLEncoder.encode(productDetails[3], "UTF-8");

                bufferedWriter.write(post_data);
                bufferedWriter.close();
                outputStream.close();

                Log.e("sellerLogIn", "data save result is=" + httpURLConnection.getResponseMessage());

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
            Log.e("SellerAddProductFro", "update product response is=" + response);
            if (response != null) {
                //String id = returnParsedJsonObject(response);
                String id = Constant.singleProduct.id;
                progressDialog.dismiss();

                // new GetSellerProducts2(ref, ref, globels.getGlobelRef().sellerlogin.id);

                if (selectedImagePath != null && selectedImagePath.size() > 0) {
                    for (int i = 0; i < selectedImagePath.size(); i++) {
                        new AddImageOfProduct(ref, ref, selectedImagePath.get(i), id);
                    }
                    //ref.productAdditionCompleted();
                }
            } else {
                NotFoundDialog.show();
            }
        }
    }
}

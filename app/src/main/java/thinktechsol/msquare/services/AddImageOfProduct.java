package thinktechsol.msquare.services;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.util.Log;

import com.android.volley.RequestQueue;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import java.io.File;
import java.io.IOException;

import thinktechsol.msquare.activities.AddOrViewProActivity;
import thinktechsol.msquare.utils.Constant;
//import org.json..parser.JSONParser;

public class AddImageOfProduct {

    private static final String TAG_SUCCESS = "success";

    String _url = "addProductImages/";
    Context ctx;
    ProgressDialog progressDialog;
    AlertDialog NotFoundDialog;
    Context ref;
    String bitmap;
    String idAgainstImagesUpload;
    ProgressDialog pd;

    public AddImageOfProduct(final Context ctx, Context ref, String bitmap, String idAgainstImagesUpload) {
        this.ctx = ctx;
        this.ref = ref;
        this.idAgainstImagesUpload = idAgainstImagesUpload;

        this.bitmap = bitmap;
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

        addSellerProductImages();
    }

    public void addSellerProductImages() {
        new addSellerProductImagesAsync().execute();
    }

    private RequestQueue mRequestQueue;

    /**
     * Background Async Task to fetch all jobs
     */
    class addSellerProductImagesAsync extends AsyncTask<String, String, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        protected String doInBackground(String... input) {
            try {

                String urlServer = Constant.baseUrl + _url;//"http://192.168.1.1/handle_upload.php";
                String path = bitmap.toString();
                String response= uploadFile(urlServer, path);

                return response;

            }
//            catch (MalformedURLException e) {
//                Log.e("sellerLogIn", "MalformedURLException=" + e);
//                progressDialog.dismiss();
//                return null;
//            }
            catch (Exception e) {
                Log.e("sellerLogIn", "exception Exception=" + e);
                progressDialog.dismiss();
                return null;
            }
        }

        protected void onPostExecute(String response) {
            if (response != null) {
                progressDialog.dismiss();
//                ref.productAdditionCompleted();
            } else {
                NotFoundDialog.show();
            }
        }
    }

    private String uploadFile(String url, String imagePath) {
        String responseString = null;

        HttpClient httpclient = new DefaultHttpClient();
        HttpPost httppost = new HttpPost(url);

        try {
            AndroidMultiPartEntity entity = new AndroidMultiPartEntity(
                    new AndroidMultiPartEntity.ProgressListener() {

                        @Override
                        public void transferred(long num) {
//                             publishProgress((int) ((num / (float)
//                             totalSize) * 100));
//                            Log.e("AddImageOfProduct","progress is="+num);
                        }
                    });

            File sourceFile = new File(imagePath);
            // Adding file data to http body
            entity.addPart("image", new FileBody(sourceFile));
            entity.addPart("sellerProductId", new StringBody(idAgainstImagesUpload));
//            entity.addPart("activity", new StringBody("updateAvatar"));
            Log.e("AddImageOfProduct", "id against which image need to be uploaded=" + idAgainstImagesUpload + " & " + sourceFile);

            // totalSize = entity.getContentLength();
            httppost.setEntity(entity);

            // Making server call
            HttpResponse response = httpclient.execute(httppost);
            HttpEntity r_entity = response.getEntity();


            int statusCode = response.getStatusLine().getStatusCode();
            Log.e("AddImageOfProduct", "status ImageId for image upload=" + statusCode);
            if (statusCode == 200) {
                // Server response
                responseString = EntityUtils.toString(r_entity);
                Log.e("", "http response is=" + responseString);
            } else {
                responseString = "Error occurred! Http Status Code: "
                        + statusCode;
            }
        } catch (ClientProtocolException e) {
            responseString = e.toString();
            Log.e("AddImageOfProduct", "http ClientProtocolException is=" + e);
        } catch (IOException e) {
            responseString = e.toString();
            Log.e("AddImageOfProduct", "http IOException is=" + e);
        }
        Log.e("responseString", "responseString=" + responseString);
        return responseString;
    }
}

package thinktechsol.msquare.services;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import thinktechsol.msquare.activities.EditProActivity;
import thinktechsol.msquare.activities.SellerLoginActivity;
import thinktechsol.msquare.model.SellerLogInResponse;
import thinktechsol.msquare.utils.Constant;
//import org.json..parser.JSONParser;

public class DeleteImageService {

    private static final String TAG_SUCCESS = "success";

    String _url = "deleteProductImage/";
    Context ctx;
    ProgressDialog progressDialog;
    String ImageId;
    EditProActivity ref;
    AlertDialog NotFoundDialog;

    public DeleteImageService(final Context ctx, EditProActivity ref, String ImageId) {
        this.ctx = ctx;
        this.ref = ref;
        progressDialog = new ProgressDialog(ctx);
        progressDialog.setMessage("Deleting Image Please wait...");
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

        deleteImageFromDb(ImageId);
    }

    public void deleteImageFromDb(String ImageId) {
        this.ImageId = ImageId;
        Log.e("DeleteImage", "ImageId=" + ImageId);
        if (ImageId != null)
            new deleteImageAsync().execute(ImageId);
        else
            progressDialog.dismiss();
    }

    /**
     * Background Async Task to fetch all jobs
     */
    class deleteImageAsync extends AsyncTask<String, String, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        protected String doInBackground(String... input) {
            try {
                String ImageId = input[0];
                URL url = new URL(Constant.baseUrl + _url + ImageId + "");
                Log.e("DeleteImage", "url is=" + url);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoInput(true);
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setReadTimeout(30000);
                OutputStream outputStream = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
                // String post_data = URLEncoder.encode("keyword", "UTF-8") + "=" + URLEncoder.encode(ImageId, "UTF-8");
                //bufferedWriter.write(post_data);
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
                Log.e("DeleteImage", "MalformedURLException=" + e);
                progressDialog.dismiss();
                return null;
            } catch (Exception e) {
                Log.e("DeleteImage", "exception Exception=" + e);
                progressDialog.dismiss();
                return null;
            }
        }

        protected void onPostExecute(String response) {
            if (response != null) {
                Log.e("DeleteImage", "image deletion response=" + response);
//                parsedObject = returnParsedJsonObject(response);
//                if (parsedObject != null) {
                //globels.getGlobelRef().sellerlogin = parsedObject;
                //ref.transation();
                progressDialog.dismiss();
//                } else {
//                    progressDialog.dismiss();
//                    NotFoundDialog.show();
//                }
            } else {
                NotFoundDialog.show();
            }
        }
    }

}

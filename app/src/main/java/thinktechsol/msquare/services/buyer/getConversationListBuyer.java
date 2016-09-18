package thinktechsol.msquare.services.buyer;

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
import java.util.ArrayList;

import thinktechsol.msquare.fragments.Buyer.BuyerDashBoardMessageFragment;
import thinktechsol.msquare.globels.globels;
import thinktechsol.msquare.model.getConversationListSellerResModel;
import thinktechsol.msquare.utils.Constant;
//import org.json..parser.JSONParser;

public class getConversationListBuyer {

    private static final String TAG_SUCCESS = "success";

    String _url = "getConversationListBuyer/";
    Context ctx;
    ProgressDialog progressDialog;
    AlertDialog NotFoundDialog;
    BuyerDashBoardMessageFragment ref;
    private static final String TAG = "getConversationList";

    //    globels.getGlobelRef().sellerlogin.id
    public getConversationListBuyer(final Context ctx, BuyerDashBoardMessageFragment ref) {
        this.ctx = ctx;
        this.ref = ref;

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


        new getConversationList().execute("0");
    }


    private ArrayList<getConversationListSellerResModel> returnParsedJsonObject(String result) {

        JSONObject resultObject = null;
        JSONArray jsonArray = null;
        ArrayList<getConversationListSellerResModel> allMessages = new ArrayList<getConversationListSellerResModel>();


        try {
            JSONObject parentObject = new JSONObject(result);

            JSONObject parentJSONObjDetails = parentObject.getJSONObject("results");
            String response = parentJSONObjDetails.getString("response");

            Log.e(TAG, "Parsed Response is=" + response);

            if(response.equals("true")) {
                JSONArray messagesList = parentJSONObjDetails.getJSONArray("data");

                Log.e(TAG, "JSONArray lenght" + messagesList.length());
                for (int i = 0; i < messagesList.length(); i++) {
                    JSONObject childJsonObj = (JSONObject) messagesList.get(i);
                    String messageBody = childJsonObj.getString("messageBody");
                    String sender = childJsonObj.getString("sender");
                    String id = childJsonObj.getString("id");
                    String dated = childJsonObj.getString("dated");
                    String lName = childJsonObj.getString("lName");
                    String thumb = childJsonObj.getString("logo");
                    String orderId = childJsonObj.getString("orderId");
                    String fName = childJsonObj.getString("fName");

                    getConversationListSellerResModel obj = new getConversationListSellerResModel(messageBody, sender, id, dated, lName, thumb, orderId, fName);
                    allMessages.add(obj);
                }
            }

        } catch (JSONException e) {
            Log.e(TAG, "JSONExc ParsedJsonObject=" + e);
            e.printStackTrace();
            NotFoundDialog.show();
        }
        return allMessages;
    }


    /**
     * Background Async Task to fetch all services
     */
    class getConversationList extends AsyncTask<String, String, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        protected String doInBackground(String... input) {
            try {
                //String buyerId = input[0];
                URL url = new URL(Constant.baseUrl + _url + globels.getGlobelRef().buyerLoginId);
                Log.e(TAG, "getConversationList url=" + url);
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
                Log.e(TAG, "getConversationList result is=" + result);
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
                ArrayList<getConversationListSellerResModel> list = returnParsedJsonObject(response);
//                Log.e(TAG, "getConversation list" + list.size());
                ref.fillListData(list);
                progressDialog.dismiss();
            } else {
                NotFoundDialog.show();
            }
        }
    }
}

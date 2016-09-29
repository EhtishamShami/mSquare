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
import java.net.URLEncoder;
import java.util.ArrayList;

import thinktechsol.msquare.activities.buyer.BuyerDeshBoardActivity;
import thinktechsol.msquare.globels.globels;
import thinktechsol.msquare.model.Buyer.BuyerDeshBoardStatesModel;
import thinktechsol.msquare.model.Buyer.BuyerOrderStatesModel;
import thinktechsol.msquare.model.SellerLogInResponse;
import thinktechsol.msquare.utils.Constant;
//import org.json..parser.JSONParser;

public class GetBuyerDeshBoardStatesService {

    private static final String TAG_SUCCESS = "success";

    String _url = "dashBoardStatsBuyer/";
    Context ctx;
    ProgressDialog progressDialog;
    String buyerId;
    AlertDialog NotFoundDialog;
    BuyerDeshBoardActivity ref;

    public GetBuyerDeshBoardStatesService(final Context ctx,BuyerDeshBoardActivity ref, String buyerId) {
        this.ctx = ctx;
         this.ref = ref;
        this.buyerId = buyerId;
        progressDialog = new ProgressDialog(ctx);
        progressDialog.setMessage("Fetching Please wait...");
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

        new getSellerCustomerDetialAsync().execute(buyerId);
    }

    private ArrayList<BuyerDeshBoardStatesModel> returnParsedJsonObject(String result) {

//        List<SellerLogInResponse> jsonObject = new ArrayList<SellerLogInResponse>();
        JSONObject resultObject = null;
        JSONArray jsonArray = null;
        SellerLogInResponse sellerLogInResponse = null;
        ArrayList<BuyerDeshBoardStatesModel> states = new ArrayList<BuyerDeshBoardStatesModel>();

        try {
            JSONObject parentObject = new JSONObject(result);

            JSONObject parentJSONObjDetails = parentObject.getJSONObject("results");
            JSONObject childJSonObj = parentJSONObjDetails.getJSONObject("data");

            String unReadMessages = childJSonObj.getString("unReadMessages");
            /////////////////////
            JSONObject JsonObjOrderDetails = childJSonObj.getJSONObject("orders");
            String recent = JsonObjOrderDetails.getString("recent");
            String inProcess = JsonObjOrderDetails.getString("inProcess");
            String reject = JsonObjOrderDetails.getString("reject");
            String complete = JsonObjOrderDetails.getString("complete");
            String dispute = JsonObjOrderDetails.getString("dispute");
            BuyerOrderStatesModel orderStatesObj = new BuyerOrderStatesModel(recent, inProcess, reject, complete, dispute);
            /////////////////////
            String wishlist = childJSonObj.getString("wishlist");
            String favourites = childJSonObj.getString("favourites");

            BuyerDeshBoardStatesModel deshboardStates = new BuyerDeshBoardStatesModel(unReadMessages, orderStatesObj, wishlist, favourites);
            states.add(deshboardStates);


        } catch (JSONException e) {
            Log.e("sellerLogIn", "JSONExc ParsedJsonObject=" + e);
            e.printStackTrace();
//            NotFoundDialog.show();
        }
        return states;
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
                String buyerId = input[0];
                URL url = new URL(Constant.baseUrl + _url + buyerId + "");
                Log.e("seller get product list", "url is=" + url);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoInput(true);
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setReadTimeout(30000);
                OutputStream outputStream = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
                String post_data = URLEncoder.encode("keyword", "UTF-8") + "=" + URLEncoder.encode(buyerId, "UTF-8");
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
                ArrayList<BuyerDeshBoardStatesModel> list = returnParsedJsonObject(response);

                globels.getGlobelRef().buyerDeshBoardStatesList = list;
                ref.fill_data_to_adapter(list);

                progressDialog.dismiss();
            } else {
                NotFoundDialog.show();
            }
        }
    }
}

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
import java.util.ArrayList;

import thinktechsol.msquare.fragments.SellerDashBoardProductFragment;
import thinktechsol.msquare.globels.globels;
import thinktechsol.msquare.model.SellerDashBoardStatsModel;
import thinktechsol.msquare.model.SellerLogInResponse;
import thinktechsol.msquare.utils.Constant;
//import org.json..parser.JSONParser;

public class GetSellerDeshBoardStatsService {

    private static final String TAG_SUCCESS = "success";

    String _url = "dashBoardStatsSeller/";
    Context ctx;
    ProgressDialog progressDialog;
    String sellerId;
    AlertDialog NotFoundDialog;
    SellerDashBoardProductFragment activityContext;

    public GetSellerDeshBoardStatsService(final Context ctx, SellerDashBoardProductFragment activityContext, String sellerId) {
        this.ctx = ctx;
        // this.ref = ref;
        this.activityContext = activityContext;
        this.sellerId = sellerId;
        progressDialog = new ProgressDialog(ctx);
        progressDialog.setMessage("Fetching Please wait...");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setCancelable(false);
//        progressDialog.show();

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

        new getSellerDeshBoardStatsAsync().execute(sellerId);
    }

    private ArrayList<SellerDashBoardStatsModel> returnParsedJsonObject(String result) {

//        List<SellerLogInResponse> jsonObject = new ArrayList<SellerLogInResponse>();
        JSONObject resultObject = null;
        JSONArray jsonArray = null;
        SellerLogInResponse sellerLogInResponse = null;
        ArrayList<SellerDashBoardStatsModel> states = new ArrayList<SellerDashBoardStatsModel>();

        try {
            JSONObject parentObject = new JSONObject(result);

            JSONObject parentJSONObjDetails = parentObject.getJSONObject("results");
            JSONObject childJSonObj = parentJSONObjDetails.getJSONObject("data");

            String unReadMessages = childJSonObj.getString("unReadMessages");
            String customers = childJSonObj.getString("customers");
            String staff = childJSonObj.getString("staff");
            /////////////////////
            JSONObject JsonObjOrderDetails = childJSonObj.getJSONObject("orders");
            String Orecent = JsonObjOrderDetails.getString("recent");
            String OinProcess = JsonObjOrderDetails.getString("inProcess");
            String Oreject = JsonObjOrderDetails.getString("reject");
            String Ocomplete = JsonObjOrderDetails.getString("complete");
            String Odispute = JsonObjOrderDetails.getString("dispute");
            //BuyerOrderStatesModel orderStatesObj = new BuyerOrderStatesModel(recent, inProcess, reject, complete, dispute);
            /////////////////////

            /////////////////////
            JSONObject JsonObjProDetailDetails = childJSonObj.getJSONObject("products");
            String Ppending = JsonObjProDetailDetails.getString("pending");
            String Penable = JsonObjProDetailDetails.getString("enable");
            String Pdisable = JsonObjProDetailDetails.getString("disable");
            String Pblocked = JsonObjProDetailDetails.getString("blocked");

//            BuyerDeshBoardStatesModel deshboardStates = new BuyerDeshBoardStatesModel(unReadMessages, orderStatesObj, wishlist, favourites);
//            states.add(deshboardStates);
            SellerDashBoardStatsModel dashBoardStats = new SellerDashBoardStatsModel(unReadMessages, customers, staff,
                    Orecent, OinProcess, Oreject, Ocomplete, Odispute, Ppending, Penable, Pdisable, Pblocked
            );

            states.add(dashBoardStats);


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
    class getSellerDeshBoardStatsAsync extends AsyncTask<String, String, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        protected String doInBackground(String... input) {
            try {
                String sellerId = input[0];
                URL url = new URL(Constant.baseUrl + _url + sellerId + "");
                Log.e("SellerDeshBoardStats", "url is=" + url);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoInput(true);
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setReadTimeout(30000);
                OutputStream outputStream = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
//                String post_data = URLEncoder.encode("keyword", "UTF-8") + "=" + URLEncoder.encode(sellerId, "UTF-8");
//                bufferedWriter.write(post_data);
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
                Log.e("SellerDeshBoardStats", "result is=" + result);
                return result;

            } catch (MalformedURLException e) {
                Log.e("SellerDeshBoardStats", "MalformedURLException=" + e);
                //progressDialog.dismiss();
                return null;
            } catch (Exception e) {
                Log.e("SellerDeshBoardStats", "exception Exception=" + e);
                //progressDialog.dismiss();
                return null;
            }
        }

        protected void onPostExecute(String response) {
            if (response != null) {

                ArrayList<SellerDashBoardStatsModel> list = returnParsedJsonObject(response);
                Log.e("SellerDeshBoardStats", "response=" + list.size());
                //ref.fill_data_to_adapter(list);
                globels.getGlobelRef().SellerDashBoardStatsModel = list;
                activityContext.updateStats();
                //progressDialog.dismiss();
            } else {
                //NotFoundDialog.show();
            }
        }
    }
}

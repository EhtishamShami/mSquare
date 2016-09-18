package thinktechsol.msquare.services;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.util.Log;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;

import thinktechsol.msquare.model.Buyer.ConfirmBookingModel;
import thinktechsol.msquare.utils.Constant;
//import org.json..parser.JSONParser;

public class AddBuyerOrder {

    private static final String TAG_SUCCESS = "success";

//    String _url = "http://smartit.ae/api/addBuyerOrder";
    String _url = "addBuyerOrder/";
    Context ctx;
    ProgressDialog progressDialog;
    AlertDialog NotFoundDialog;
    String keywords, city;
    Context ActivityContext;
    ConfirmBookingModel dataObj;

    public AddBuyerOrder(final Context ctx, Context ActivityContext, ConfirmBookingModel dataObj) {
        this.ctx = ctx;
        this.ActivityContext = ActivityContext;
        this.dataObj = dataObj;
        progressDialog = new ProgressDialog(ctx);
        progressDialog.setMessage("Confirming Please wait...");
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
//        dataObj.sellerId;
//        dataObj.buyerId;
//        dataObj.extraRemarks;
//        dataObj.serviceRequestTime;
//        dataObj.staffId;
//        public ArrayList<String> serviceId;
//        public ArrayList<String> productId;
//        public ArrayList<String> quantity;
        Log.e("AddBuyerOrder", "post data is=" + dataObj.sellerId+" , "+dataObj.buyerId+" , "+dataObj.extraRemarks+" , "+dataObj.serviceRequestTime+" , "+
                dataObj.staffId+" , "+dataObj.serviceId+" , "+dataObj.productId+" , "+dataObj.quantity);



        new postOrderAsync().execute(keywords, city);
    }

//    public List<JobsItem> getJsonArray() {
//        return parsedObject;
//    }

//    private List<JobsItem> returnParsedJsonObject(String result) {
//
//        List<JobsItem> jsonObject = new ArrayList<JobsItem>();
//        JSONObject resultObject = null;
//        JSONArray jsonArray = null;
//        JobsItem newItemObject = null;
//
//        try {
//            jsonArray = new JSONArray(result);
////            resultObject = new JSONObject(result);
//            Log.e("AddBuyerOrder", "JsonObject is" + " && " + jsonArray);
////            jsonArray = resultObject.optJSONArray("data");
//            for (int i = 0; i < jsonArray.length(); i++) {
//                JSONObject jsonChildNode = null;
//                try {
//                    jsonChildNode = jsonArray.getJSONObject(i);
//                    String title = jsonChildNode.getString("title");
//                    String job_location = jsonChildNode.getString("job_location");
//                    String company_name = jsonChildNode.getString("company_name");
//                    String posted_on = jsonChildNode.getString("posted_on");
//                    String portal = jsonChildNode.getString("portal");
//                    String link = jsonChildNode.getString("link");
//                    newItemObject = new JobsItem(title, job_location, company_name, posted_on, portal, link);
//                    jsonObject.add(newItemObject);
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
//            }
//        } catch (JSONException e) {
//            Log.e("AddBuyerOrder", "JSONExc ParsedJsonObject=" + e);
//            e.printStackTrace();
//            NotFoundDialog.show();
//        }
//
//        return jsonObject;
//    }

    /**
     * Background Async Task to fetch all jobs
     */
    class postOrderAsync extends AsyncTask<String, String, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        protected String doInBackground(String... input) {
            try {
                String keyword = input[0];
                String city = input[1];

//                URL url = new URL(_url);
//                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
//                httpURLConnection.setRequestMethod("POST");
//                httpURLConnection.setDoInput(true);
//                httpURLConnection.setDoOutput(true);
//                httpURLConnection.setReadTimeout(30000);
//                OutputStream outputStream = httpURLConnection.getOutputStream();
//
//                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
//                String post_data = URLEncoder.encode("sellerId", "UTF-8") + "=" + URLEncoder.encode(keyword, "UTF-8")
//                           + "&" + URLEncoder.encode("buyerId", "UTF-8") + "=" + URLEncoder.encode(city, "UTF-8")
//                           + "&" + URLEncoder.encode("extraRemarks", "UTF-8") + "=" + URLEncoder.encode(city, "UTF-8")
//                           + "&" + URLEncoder.encode("serviceRequestTime", "UTF-8") + "=" + URLEncoder.encode(city, "UTF-8")
//                           + "&" + URLEncoder.encode("staffId", "UTF-8") + "=" + URLEncoder.encode(city, "UTF-8")
//                           + "&" + URLEncoder.encode("serviceId[]", "UTF-8") + "=" + URLEncoder.encode(city, "UTF-8")
//                           + "&" + URLEncoder.encode("productId[]", "UTF-8") + "=" + URLEncoder.encode(city, "UTF-8")
//                           + "&" + URLEncoder.encode("quantity[]", "UTF-8") + "=" + URLEncoder.encode(city, "UTF-8")
//                           + "&" + URLEncoder.encode("quantity[]", "UTF-8") + "=" + URLEncoder.encode(new JSONArray(Arrays.asList(array)), "UTF-8")
//                           + "&" + URLEncoder.encode("quantity[]", "UTF-8") + "=" + URLEncoder.encode(new JSONArray(Arrays.asList(array)), "UTF-8")


                HttpClient httpclient = new DefaultHttpClient();
                HttpPost httppost = new HttpPost(Constant.baseUrl + _url);
                Log.e("AddBuyerOrder", "Orders Url is=" + Constant.baseUrl + _url);
//                JSONObject jsonobj = new JSONObject();
//                jsonobj.put("sellerId", "1");
//                jsonobj.put("buyerId", "1");
//                jsonobj.put("extraRemarks", "test");
//                jsonobj.put("serviceRequestTime", "2016-05-23 12:12:12");
//                jsonobj.put("staffId", "1");

                List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
                nameValuePairs.add(new BasicNameValuePair("sellerId", dataObj.sellerId));
                nameValuePairs.add(new BasicNameValuePair("buyerId", dataObj.buyerId));
                nameValuePairs.add(new BasicNameValuePair("extraRemarks", dataObj.extraRemarks));
                nameValuePairs.add(new BasicNameValuePair("serviceRequestTime", dataObj.serviceRequestTime));
                nameValuePairs.add(new BasicNameValuePair("staffId", dataObj.staffId));

                for (int i = 0; i < dataObj.serviceId.size(); i++) {
                    nameValuePairs.add(new BasicNameValuePair("serviceId[]", dataObj.serviceId.get(i)));
                    nameValuePairs.add(new BasicNameValuePair("productId[]", dataObj.productId.get(i)));
                    nameValuePairs.add(new BasicNameValuePair("quantity[]", dataObj.quantity.get(i)));
//                    nameValuePairs.add(new BasicNameValuePair("quantity[]", dataObj.quantity.get(i)));
                }

//                nameValuePairs.add(new BasicNameValuePair("quantity[]", "0"));

                // Use UrlEncodedFormEntity to send in proper format which we need
                httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));

                // Execute HTTP Post Request
                HttpResponse response = httpclient.execute(httppost);
                InputStream inputStream = response.getEntity().getContent();


//                bufferedWriter.write(post_data);
//                bufferedWriter.close();
//                outputStream.close();

//                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "iso-8859-1"));

                String result = "";
                String line;
                while ((line = bufferedReader.readLine()) != null) {
                    result += line;
                }
                bufferedReader.close();
                inputStream.close();

                JSONArray jsonArray = null;

                return result;

            } catch (MalformedURLException e) {
                Log.e("AddBuyerOrder", "MalformedURLException=" + e);
                progressDialog.dismiss();
                return null;
            } catch (Exception e) {
                Log.e("AddBuyerOrder", "exception Exception=" + e);
                progressDialog.dismiss();
                return null;
            }
            //return null;
        }

        protected void onPostExecute(String response) {
            if (response != null) {
                Log.e("AddBuyerOrder", "Rresponse is=" + response);
//                parsedObject = returnParsedJsonObject(response);
//                ItemAdapter myAdapter = new ItemAdapter(ctx, R.layout.jobs_item, parsedObject);
//                listView.setAdapter(myAdapter);
//                myAdapter.notifyDataSetChanged();
                progressDialog.dismiss();
//                jobsAct.makeKeyboardInvisible();
            } else {
                NotFoundDialog.show();
            }

        }
    }


}

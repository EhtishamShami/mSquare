package thinktechsol.msquare.gcm;

import android.app.IntentService;
import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import com.google.android.gms.gcm.GoogleCloudMessaging;
import com.google.android.gms.iid.InstanceID;

import java.io.IOException;

import thinktechsol.msquare.R;

/**
 * Created by arshadiqbal on 10/09/16.
 */
public class GCMRegistrationService extends IntentService {

    public static final String REGISTRATION_SUCCESS = "RegistrationSuccess";
    public static final String REGISTRATION_ERROR = "RegistrationError";

    //Class constructor
    public GCMRegistrationService() {
        super("");
    }


    @Override
    protected void onHandleIntent(Intent intent) {
        //Registering gcm to the device
        registerGCM();
    }

    private void registerGCM() {
        //Registration complete intent initially null
        Intent registrationComplete = null;

        //Register token is also null
        //we will get the token on successfull registration
        String token = null;
        try {
            //Creating an instanceid
            InstanceID instanceID = InstanceID.getInstance(getApplicationContext());

            //Getting the token from the instance id
            token = instanceID.getToken(getString(R.string.gcm_defaultSenderId), GoogleCloudMessaging.INSTANCE_ID_SCOPE, null);

            //Displaying the token in the log so that we can copy it to send push notification
            //You can also extend the app by storing the token in to your server
            Log.e("GCMRegIntentService", "token:" + token);

            //on registration complete creating intent with success
            registrationComplete = new Intent(REGISTRATION_SUCCESS);

            //Putting the token to the intent
            registrationComplete.putExtra("token", token);
        } catch (Exception e) {
            //If any error occurred
            Log.e("GCMRegIntentService", "Registration error");
            registrationComplete = new Intent(REGISTRATION_ERROR);
        }

        //Sending the broadcast that registration is completed
        LocalBroadcastManager.getInstance(this).sendBroadcast(registrationComplete);
    }

//    private static final String TAG = "RegIntentService";
//    private static final String[] TOPICS = {"global"};
//
//    public GCMRegistrationService() {
//        super(TAG);
//    }

//    @Override
//    protected void onHandleIntent(Intent intent) {
//        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
//
//        Log.e(TAG, " in GCMRegistrationService: " );
//        try {
//            // [START register_for_gcm]
//            // Initially this call goes out to the network to retrieve the token, subsequent calls
//            // are local.
//            // R.string.gcm_defaultSenderId (the Sender ID) is typically derived from google-services.json.
//            // See https://developers.google.com/cloud-messaging/android/start for details on this file.
//            // [START get_token]
//            InstanceID instanceID = InstanceID.getInstance(this);
//            String token = instanceID.getToken(getString(R.string.gcm_defaultSenderId),
//                    GoogleCloudMessaging.INSTANCE_ID_SCOPE, null);
//            // [END get_token]
//            Log.e(TAG, "GCM Registration Token: " + token);
//
//            // TODO: Implement this method to send any registration to your app's servers.
//            sendRegistrationToServer(token);
//
//            // Subscribe to topic channels
//            subscribeTopics(token);
//
//            // You should store a boolean that indicates whether the generated token has been
//            // sent to your server. If the boolean is false, send the token to your server,
//            // otherwise your server should have already received the token.
//            sharedPreferences.edit().putBoolean("SENT_TOKEN_TO_SERVER", true).apply();
//            // [END register_for_gcm]
//        } catch (Exception e) {
//            Log.e(TAG, "Failed to complete token refresh", e);
//            // If an exception happens while fetching the new token or updating our registration data
//            // on a third-party server, this ensures that we'll attempt the update at a later time.
//            sharedPreferences.edit().putBoolean("SENT_TOKEN_TO_SERVER", false).apply();
//        }
//        // Notify UI that registration has completed, so the progress indicator can be hidden.
//        Intent registrationComplete = new Intent("REGISTRATION_COMPLETE");
//        LocalBroadcastManager.getInstance(this).sendBroadcast(registrationComplete);
//    }

    /**
     * Persist registration to third-party servers.
     * <p/>
     * Modify this method to associate the user's GCM registration token with any server-side account
     * maintained by your application.
     *
     * @param token The new token.
     */

//    private void sendRegistrationToServer(String token) {
//        // Add custom implementation, as needed.
//    }

    /**
     * Subscribe to any GCM topics of interest, as defined by the TOPICS constant.
     *
     * @param token GCM token
     * @throws IOException if unable to reach the GCM PubSub service
     */
    // [START subscribe_topics]
//    private void subscribeTopics(String token) throws IOException {
//        GcmPubSub pubSub = GcmPubSub.getInstance(this);
//        for (String topic : TOPICS) {
//            pubSub.subscribe(token, "/topics/" + topic, null);
//        }
//    }
    // [END subscribe_topics]


    /**
     * Registers the application with GCM servers asynchronously.
     * <p>
     * Stores the registration id, app versionCode, and expiration time in the
     * application's shared preferences.
     */
//    private GoogleCloudMessaging gcm;
//    private static final String TAG = "RegistrationIntSer";
//
//    private static final int MAX_ATTEMPTS = 5;
//    private void registerBackground() {
//        new AsyncTask<Void, Void, Boolean>() {
//            @Override
//            protected Boolean doInBackground(Void... params) {
//                //long backoff = BACKOFF_MILLI_SECONDS + random.nextInt(1000);
//                for (int i = 1; i <= MAX_ATTEMPTS; i++) {
//                    Log.d(TAG, "Attempt #" + i + " to register");
//                    try {
//                        if (gcm == null) {
//                            gcm = GoogleCloudMessaging.getInstance(ctx);
//                        }
//                        String regid = gcm.register("716486474878");
//
//                        // You should send the registration ID to your server
//                        // over HTTP,
//                        // so it can use GCM/HTTP or CCS to send messages to
//                        // your app.
//
////                        ServerUtilities.register(CommonMap.getPreferredEmail(),
////                                regid);
//
//                        // Save the regid - no need to register again.
//                        setRegistrationId(regid);
//                        return Boolean.TRUE;
//
//                    } catch (IOException ex) {
//                        Log.e(TAG, "Failed to register on attempt " + i + ":"
//                                + ex);
//                        if (i == MAX_ATTEMPTS) {
//                            break;
//                        }
//                        try {
//                            // Log.d(TAG, "Sleeping for " + backoff +
//                            // " ms before retry");
//                            Thread.sleep(backoff);
//                        } catch (InterruptedException e1) {
//                            // Activity finished before we complete - exit.
//                            // Log.d(TAG,
//                            // "Thread interrupted: abort remaining retries!");
//                            Thread.currentThread().interrupt();
//                        }
//                        // increase backoff exponentially
//                        backoff *= 2;
//                    }
//                }
//                return Boolean.FALSE;
//            }
//
//            @Override
//            protected void onPostExecute(Boolean status) {
//                broadcastStatus(status);
//            }
//        }.execute(null, null, null);
//    }
}

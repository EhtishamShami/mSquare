package thinktechsol.msquare.gcm;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.google.android.gms.gcm.GcmListenerService;

import thinktechsol.msquare.R;
import thinktechsol.msquare.activities.SellerDeshBoardActivity;
import thinktechsol.msquare.activities.buyer.BuyerDeshBoardActivity;
import thinktechsol.msquare.globels.globels;

/**
 * Created by arshadiqbal on 10/09/16.
 */
public class GCMPushReceiverService extends GcmListenerService {

    //This method will be called on every new message received
    private Uri defaultSoundUri;

    SharedPreferences preferences;
    SharedPreferences.Editor editor;

    @Override
    public void onMessageReceived(String from, Bundle data) {
        //Getting the message from the bundle
        String message = data.getString("message");
        //Displaying a notiffication with the message

        Log.e("GCMPushReceiver", "message is=" + data);

        preferences = PreferenceManager.getDefaultSharedPreferences(this);
        editor = preferences.edit();

        sendNotification(message);
    }

    //This method is generating a notification and displaying the notification
    private void sendNotification(String message) {

        boolean isSellerLogedIn = preferences.getBoolean("isSellerLogin", false);
        boolean isBuyerLogedIn = preferences.getBoolean("isBuyerLogin", false);

        globels.getGlobelRef().isNotification = "Notification";
        Intent intent = null;
        if(isBuyerLogedIn) {
             intent = new Intent(this, BuyerDeshBoardActivity.class);
        }else {
            intent = new Intent(this, SellerDeshBoardActivity.class);
        }
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        int requestCode = 0;
        PendingIntent pendingIntent = PendingIntent.getActivity(this, requestCode, intent, PendingIntent.FLAG_ONE_SHOT);
        Uri sound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);


        defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder noBuilder = new NotificationCompat.Builder(this)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentText(message)
                .setAutoCancel(true)
                .setSound(defaultSoundUri)
                .setContentIntent(pendingIntent);

        //Toast.makeText(GCMPushReceiverService.this, "gcm message: "+message, Toast.LENGTH_SHORT).show();

        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(0, noBuilder.build()); //0 = ID of notification
    }
}
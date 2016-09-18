package thinktechsol.msquare.gcm;

import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.iid.InstanceIDListenerService;

/**
 * Created by arshadiqbal on 10/09/16.
 */
public class GCMTokenRefreshListenerService extends InstanceIDListenerService {
    @Override
    public void onTokenRefresh() {

        Toast.makeText(GCMTokenRefreshListenerService.this, "here we go", Toast.LENGTH_SHORT).show();
        Log.e("GCMInstanceIDListener", "GCM GCMTokenRefreshListenerService ");
        Intent intent = new Intent(this, GCMRegistrationService.class);
        startService(intent);



    }

    @Override
    public void onCreate() {
        super.onCreate();
        Toast.makeText(GCMTokenRefreshListenerService.this, "here we go", Toast.LENGTH_SHORT).show();
        Log.e("GCMInstanceIDListener", "GCM GCMTokenRefreshListenerService ");

        Intent intent = new Intent(this, GCMRegistrationService.class);
        startService(intent);

    }
}

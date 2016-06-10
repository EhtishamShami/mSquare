package thinktechsol.msquare.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import thinktechsol.msquare.R;

/**
 * Created by arshad.iqbal on 6/1/2016.
 */
public class Fragment_2_items extends Fragment {

    String value;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle extras = getArguments();
        if (extras != null && extras.containsKey("key")) {
            value = extras.getString("key", "value");
        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.frag_2items, container, false);
        Log.e("SellerDashBoardt", "SellerDash data=" + value);
        return v;
    }
}

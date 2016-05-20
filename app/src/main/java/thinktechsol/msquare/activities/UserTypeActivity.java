package thinktechsol.msquare.activities;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;

import thinktechsol.msquare.R;

public class UserTypeActivity extends Activity {

    ImageView type_seller_btn, type_buyer_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_activity_user_type);

        type_seller_btn = (ImageView) findViewById(R.id.type_seller_btn);
        type_buyer_btn = (ImageView) findViewById(R.id.type_buyer_btn);

        type_seller_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }
}

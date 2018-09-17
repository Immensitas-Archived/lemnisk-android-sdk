package co.lemnisk.app.android.sample;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.firebase.iid.FirebaseInstanceId;
import co.lemnisk.app.android.AttributeBuilder;
import co.lemnisk.app.android.LemniskHelper;

public class MainActivity extends AppCompatActivity {

    private static String TAG = "Lemnisk";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        checkPlayServices();
        LemniskHelper.getInstance(getApplicationContext()).init();

        Button eventButton = findViewById(R.id.eventButton);
        eventButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                exampleAppEvent();
            }
        });

        Button logTokenButton = findViewById(R.id.logTokenButton);
        logTokenButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG,"gcm token is " + FirebaseInstanceId.getInstance().getToken());
            }
        });
    }

    public boolean checkPlayServices() {
        GoogleApiAvailability apiAvailability = GoogleApiAvailability.getInstance();
        int resultCode = apiAvailability.isGooglePlayServicesAvailable(getApplicationContext());
        if (resultCode != ConnectionResult.SUCCESS) {
            if (apiAvailability.isUserResolvableError(resultCode)) {
                apiAvailability.getErrorDialog(this, resultCode, 2404).show();
                Log.i(TAG,"Google play services error. Is user resolvable with resultcode " + resultCode);
            } else {
                Log.e(TAG, "This device is not supported.");
            }
            return false;
        }
        return true;
    }

    /**
     * example method to show how to create an attributeBuilder object and
     * call logEvent with the event name that you want to pass and the created
     * attributeBuilder associated with the event
     */
    private void exampleAppEvent() {
        AttributeBuilder builder = new AttributeBuilder.Builder()
                .addAttribute("pageType", "personal-loan-product")
                .addAttribute("category", "loans")
                .addAttribute("subCategory","personal-loan")
                .build();

        LemniskHelper.getInstance(getApplicationContext()).logEvent("productPage", builder);
    }
}

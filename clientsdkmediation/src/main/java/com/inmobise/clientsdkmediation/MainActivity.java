package com.inmobise.clientsdkmediation;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.aerserv.sdk.AerServBanner;
import com.aerserv.sdk.AerServConfig;
import com.aerserv.sdk.AerServInterstitial;
import com.aerserv.sdk.AerServSdk;
import com.inmobi.sdk.SdkInitializationListener;

public class MainActivity extends AppCompatActivity {

    private static final String APP_ID = "1022221";
    private static final String TAG = "Sample";

    private static final String BANNER_PLACEMENT = "1069144";
    private static final String MREC_PLACEMENT = "1069145";
    private static final String INT_PLACEMENT = "1069146";

    AerServBanner banner;
    AerServBanner mrec;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        AerServSdk.init(this, APP_ID,new SdkInitializationListener() {
            @Override
            public void onInitializationComplete(@Nullable Error error) {
                if (null != error) {
                    Log.e(TAG, "AerServ Init Failed with error message: " + error.getMessage());
                } else {
                    Log.d(TAG, "AerServ Init Successful");

                    loadBanner();
                    loadMrec();
                    loadInterstitial();
                }
            }
        });

    }

    void loadBanner(){
        AerServConfig config = new AerServConfig(this, BANNER_PLACEMENT);
        banner = (AerServBanner) findViewById(R.id.banner);
        banner.configure(config).show();
    }

    void loadMrec(){
        AerServConfig config = new AerServConfig(this, MREC_PLACEMENT);
        mrec = (AerServBanner) findViewById(R.id.mrec);
        mrec.configure(config).show();
    }


    void loadInterstitial(){
        AerServConfig config = new AerServConfig(this, INT_PLACEMENT);
        AerServInterstitial interstitial = new AerServInterstitial(config);
        interstitial.show();
    }
}

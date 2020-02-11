package com.inmobise.androidMediationSampleApp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.res.Resources;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.aerserv.sdk.AerServBanner;
import com.aerserv.sdk.AerServConfig;
import com.aerserv.sdk.AerServEvent;
import com.aerserv.sdk.AerServEventListener;
import com.aerserv.sdk.AerServSdk;
import com.inmobi.sdk.InMobiSdk;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private AerServBanner banner;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initializeSDK();
        // loadBannerAd();
    }


    public void initializeSDK() {

        /*
        Initialize the InMobi SDK as early as possible.
        Context:    You can use the application context as well
        SiteID:     This value should be available on your publisher platform UI
        */

        // For Media Module:
        InMobiSdk.init(this,"");

        // For Mediation Module:
        AerServSdk.init(this, "1022221");
    }


    public void loadBannerAd() {

        AerServEventListener bannerListener = new AerServEventListener() {
            @Override
            public void onAerServEvent(AerServEvent event, List params) {
                switch (event) {
                    case AD_LOADED:
                        // Execute some code when AD_LOADED event occurs.
                        break;
                    case AD_DISMISSED:
                        // Execute some code when AD_DISMISSED event occurs.
                        break;
                    case AD_FAILED:
                        // Execute some code when AD_FAILED event occurs.
                        break;
                }
            }
        };

        AerServConfig config = new AerServConfig(this, "1069149");
        config.setEventListener(bannerListener);
        config.setDebug(true);

        banner = new AerServBanner(this);

        ViewGroup viewGroup = findViewById(R.id.bannerView);
        viewGroup.addView(banner);

        banner.configure(config).show();

    }


    @Override
    public void onDestroy() {
        super.onDestroy();

        if(banner != null) {
            banner.kill();
        }
    }

}

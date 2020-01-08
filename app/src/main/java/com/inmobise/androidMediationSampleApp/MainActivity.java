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
import com.aerserv.sdk.AerServSdk;

public class MainActivity extends AppCompatActivity {

    private AerServBanner banner;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /*
        Initialize the InMobi SDK as early as possible.
        Context:    You can use the application context as well
        SiteID:     This value should be available on your publisher platform UI
        */
        AerServSdk.init(this, "1022221");

        loadBannerAd();
    }


    public void loadBannerAd() {

        AerServConfig config = new AerServConfig(this, "1069149");
        banner = new AerServBanner(this);

        ViewGroup viewGroup = findViewById(R.id.bannerView);
        viewGroup.addView(banner);

        banner.configure(config).show();
    }


}

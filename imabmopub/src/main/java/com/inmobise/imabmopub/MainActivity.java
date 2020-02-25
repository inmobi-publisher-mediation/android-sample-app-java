package com.inmobise.imabmopub;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.inmobi.ads.InMobiAudienceBidder;
import com.inmobi.sdk.InMobiSdk;
import com.mopub.common.MoPub;
import com.mopub.common.SdkConfiguration;
import com.mopub.common.SdkInitializationListener;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initializeAdSDK();
        getDisplaySDKVersions();
    }

    private SdkInitializationListener initSdkListener() {
        return new SdkInitializationListener() {
            @Override
            public void onInitializationFinished() {
                //  MoPub SDK initialized.
            }
        };
    }

    private com.aerserv.sdk.SdkInitializationListener initASSdkListener() {
        return new com.aerserv.sdk.SdkInitializationListener() {
            @Override
            public void onInitializationComplete(String s) {
                // hello there
            }
        };
    }

    public void initializeAdSDK() {

        SdkConfiguration sdkConfiguration = new SdkConfiguration.Builder(Constants.MP_BannerAdUnitID).build();
        MoPub.initializeSdk(this, sdkConfiguration, initSdkListener());
//        InMobiAudienceBidder.initialize(this, Constants.IMAB_APPID);
        InMobiAudienceBidder.initialize(this,Constants.IMAB_APPID, initASSdkListener());

    }


    public void getDisplaySDKVersions() {
        TextView mpv = findViewById(R.id.MPSdkVersion);
        mpv.setText("MoPub SDK Version:" + MoPub.SDK_VERSION);

        TextView imv = findViewById(R.id.IMSdkVersion);
        imv.setText("IM SDK Version:" + InMobiSdk.getVersion());
    }


    public void showBannerKeywordActivity(View view){
        Intent intent = new Intent(this, BannerKeywordExample.class);
        startActivity(intent);

    }


}

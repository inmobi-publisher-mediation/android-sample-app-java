package com.inmobise.imabmopub;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.inmobi.plugin.mopub.IMAudienceBidder;
import com.mopub.mobileads.MoPubErrorCode;
import com.mopub.mobileads.MoPubView;

import java.util.HashMap;
import java.util.Map;


public class BannerKeywordExample extends AppCompatActivity implements MoPubView.BannerAdListener  {

    private MoPubView moPubView;
    private IMAudienceBidder inMobiAudienceBidder;

    public Boolean bannerLoaded = false;                                        // Use a Boolean to keep track so we don't call loadAd on the banner repeatedly.
    private IMAudienceBidder.BidToken bannerBidToken;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_banner_keyword_example);
        ((TextView) findViewById(R.id.bannerKWTextView)).setText(Constants.IMAB_KW_Banner_Explanation);



    }


    public void setupMRECBanner(View view){
        bannerBidToken = null;
        bannerBidToken = configureBannerBid(Constants.IMAB_BannerPLC, Constants.MP_MrecWidth, Constants.MP_MrecHeight, Constants.MP_MRECAdUnitID);
        bannerBidToken.updateBid();
    }


    public void setup320x50Banner(View view){
        bannerBidToken = null;
        bannerBidToken = configureBannerBid(Constants.IMAB_BannerPLC, Constants.MP_BannerWidth, Constants.MP_BannerHeight, Constants.MP_BannerAdUnitID);
        bannerBidToken.updateBid();
    }


    public void injectBannerIntoView(View view){

        if(moPubView != null && moPubView.getParent() != null) {
            ((ViewGroup)moPubView.getParent()).removeView(moPubView);
        }

        ((ViewGroup)findViewById(R.id.banner_kw_container)).addView(moPubView);

        Toast.makeText(BannerKeywordExample.this, "injecting banner into view", Toast.LENGTH_SHORT).show();


    }


    public void destroyMoPubBanner(View view){

        bannerLoaded = false;

        if (moPubView != null) {
            moPubView.destroy();
            moPubView = null;

            Toast.makeText(BannerKeywordExample.this, "destroy banner", Toast.LENGTH_SHORT).show();


        }
    }


    // Configure the banner and IMAB bid token
    public IMAudienceBidder.BidToken configureBannerBid(String placement, int width, int height, String mopubPlacement) {

        moPubView = new MoPubView(this);
        moPubView.setBannerAdListener(this);
        moPubView.setAutorefreshEnabled(false);
        moPubView.setAdUnitId(mopubPlacement);

        // NOTE: This MUST be set upon creation of the banner! Not doing so will cause keyword targeting to fail
        Map<String, Object> localExtras = new HashMap();
        localExtras.put(IMAudienceBidder.AD_KEY, Constants.IMAB_BannerPLC);
        moPubView.setLocalExtras(localExtras);

        inMobiAudienceBidder = IMAudienceBidder.getInstance();

        bannerBidToken = inMobiAudienceBidder.createBidToken(this, placement, width, height,
                new IMAudienceBidder.IMAudienceBidderBannerKeywordListener() {
                    @Override
                    public void onBidReceived(IMAudienceBidder.IMABBidResponse imabBidResponse) {

                        Toast.makeText(BannerKeywordExample.this, "onBidReceived", Toast.LENGTH_SHORT).show();

                        // Parse the imaBidResponse for any additional information you need, and set them here as desired.
                        moPubView.setKeywords(imabBidResponse.getBidKeyword());

                        // If the banner has not yet been loaded, call loadAd to load the ad into the view
                        if (!bannerLoaded) {
                            moPubView.loadAd();
                        }

                    }

                    @Override
                    public void onBidFailed(Error error) {
                        // No Bid received from InMobi Audience Bidder. Call loadAd on your MoPub view once you are ready.

                        Toast.makeText(BannerKeywordExample.this, "onBidFailed", Toast.LENGTH_SHORT).show();

                        // Error handling here as desired

                        // If the banner has not yet been loaded, call loadAd to load the ad into the view
                        if (!bannerLoaded) {
                            moPubView.loadAd();
                        }



                    }
                });

        return bannerBidToken;

    }



    public void onBannerLoaded(MoPubView banner) {

        // Ensure that we do not call loadAd again on the MoPubView
        bannerLoaded = true;

        // Update the bid for the next MoPub refresh ad call
        bannerBidToken.updateBid();

    }



    public void onBannerFailed(MoPubView banner, MoPubErrorCode errorCode) {

        // Ensure that we do not call loadAd again on the MoPubView
        bannerLoaded = true;

        // Update the bid for the next MoPub refresh ad call
        bannerBidToken.updateBid();

    }



    public void onBannerClicked(MoPubView banner) {
        // Banner clicked
    }

    public void onBannerExpanded(MoPubView banner) {
        // Banner expanded
    }

    public void onBannerCollapsed(MoPubView banner) {
        // Banner collapsed
    }


    @Override
    protected void onDestroy() {
        moPubView.destroy();
        super.onDestroy();
    }


}

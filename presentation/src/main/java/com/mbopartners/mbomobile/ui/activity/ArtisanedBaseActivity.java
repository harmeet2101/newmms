package com.mbopartners.mbomobile.ui.activity;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;

import com.artisan.activity.ArtisanActivity;
import com.artisan.services.ArtisanBoundActivity;
import com.artisan.services.ArtisanService;
import com.mbopartners.mbomobile.rest.ui.MdBaseActivity;

public class ArtisanedBaseActivity extends MdBaseActivity implements ArtisanBoundActivity {

    @Override
    public ArtisanService getArtisanService() {
        return ArtisanActivity._getArtisanService();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Call Artisan method AFTER super.onCreate
       // ArtisanActivity.artisanOnCreate(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        // Call Artisan method AFTER super.onStart
        //ArtisanActivity.artisanOnStart(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        // Call Artisan method AFTER super.onStop
        //ArtisanActivity.artisanOnStop(this);

    }


    @Override
    protected void onDestroy() {
        // Call Artisan method BEFORE super.onDestroy
        //ArtisanActivity.artisanOnDestroy();
        super.onDestroy();
    }

    /**
     * This is the version of setContentView that most people use.
     * You only need to implement this if you are using this version
     * of setContentView in this Activity or its subclasses.
     * If you are using it it looks something like this:
     * setContentView(R.layout.activity_main);
     */
    @Override
    public void setContentView(int layoutResID) {
        View contentView = ArtisanActivity.artisanGetContentView(layoutResID, this);
        super.setContentView(contentView);
    }

    /**
     * You only need to implement this if you are using this version
     * of setContentView in this Activity or its subclasses.
     */
    @Override
    public void setContentView(View view) {
        View contentView = ArtisanActivity.artisanGetContentView(view, this);
        super.setContentView(contentView);
    }

    /**
     * You only need to implement this if you are using this version
     * of setContentView in this Activity or its subclasses.
     */
    @Override
    public void setContentView(View view, ViewGroup.LayoutParams params) {
        View contentView = ArtisanActivity.artisanGetContentView(view, params, this);
        super.setContentView(contentView);
    }

}

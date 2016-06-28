package com.mbopartners.mbomobile.ui.activity;

import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;

import com.mbopartners.mbomobile.ui.R;

public class MboBaseActivity extends ArtisanedBaseActivity {

    @Override
    public void setContentView(int layoutResID) {
        super.setContentView(layoutResID);
        setupAppBarIfPossible();
    }

    public Toolbar getToolbar() {
        return (Toolbar) findViewById(R.id.toolbar);
    }

    private void setupAppBarIfPossible() {
        Toolbar toolbar = getToolbar();
        if (toolbar != null) {
            setSupportActionBar(toolbar);
        }
    }

    protected void setupUpAppTabButtonIfPossible() {
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setDisplayShowHomeEnabled(true);
        }
    }
}

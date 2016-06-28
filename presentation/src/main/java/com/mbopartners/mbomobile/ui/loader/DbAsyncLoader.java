package com.mbopartners.mbomobile.ui.loader;

import android.app.Application;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.content.AsyncTaskLoader;

import java.util.List;

public class DbAsyncLoader<T> extends AsyncTaskLoader<T> {
    private Application application;
    private Bundle params;
    private IDataSource<T> dataSource;

    public DbAsyncLoader(Application application, Bundle params) {
        super((Context) application);
        this.application = application;
        this.params = params;
    }

    public void setDataSource(IDataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public T loadInBackground() {
        if (dataSource != null) {
            return dataSource.loadData(application, params);
        } else {
            return null;
        }
    }
}

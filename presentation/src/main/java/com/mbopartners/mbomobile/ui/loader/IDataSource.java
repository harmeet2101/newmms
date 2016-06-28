package com.mbopartners.mbomobile.ui.loader;

import android.app.Application;
import android.os.Bundle;

public interface IDataSource<T> {
    T loadData(Application application, Bundle params);
}

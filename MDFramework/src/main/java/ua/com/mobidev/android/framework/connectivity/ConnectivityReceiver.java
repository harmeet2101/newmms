package ua.com.mobidev.android.framework.connectivity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class ConnectivityReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        if (ConnectivityUtils.isInternetConnected(context)) {
            // TODO Notify that we have Internet connection
            // context.startService(null);
        }
    }
}

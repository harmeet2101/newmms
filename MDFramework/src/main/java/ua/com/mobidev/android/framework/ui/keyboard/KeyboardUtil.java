package ua.com.mobidev.android.framework.ui.keyboard;

import android.app.Activity;
import android.content.Context;
import android.os.IBinder;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

public class KeyboardUtil {

    public static void htryToHideKeyboard(Activity activity) {
        if (activity != null) {
            final InputMethodManager imm = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
            View focusedView = activity.getCurrentFocus();
            IBinder windowToken = null;
            if (focusedView != null) {
                windowToken = focusedView.getWindowToken();
            }
            if (imm != null && windowToken != null) {
                imm.hideSoftInputFromWindow(windowToken, 0);
            }
        }
    }
}

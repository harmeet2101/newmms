package com.mbopartners.mbomobile.ui.toast;

import android.app.Activity;
import android.content.Context;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.mbopartners.mbomobile.ui.R;

public class ActivityToaster {
    public static void showToastLongMessage (Activity activity, String text) {
        if (text != null) {
            DisplayMetrics displayMetrics = activity.getResources().getDisplayMetrics();
            float density = displayMetrics.density;
            Toast toast = Toast.makeText(activity, text,  Toast.LENGTH_LONG);
            toast.setGravity(Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, (int)(83 * density));
            toast.show();
        }
    }
    public static void showToastLongMessage (Activity activity, int id) {
        DisplayMetrics displayMetrics = activity.getResources().getDisplayMetrics();
        float density = displayMetrics.density;
        Toast toast = Toast.makeText(activity, id, Toast.LENGTH_LONG);
        toast.setGravity(Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, (int)(83 * density));
        toast.show();
    }

    public static void showCustomToastMessage(Activity activity,String text)
    {
        if(text!=null)
        {
            DisplayMetrics displayMetrics = activity.getResources().getDisplayMetrics();
            float density = displayMetrics.density;
            Context context=activity.getApplicationContext();
            LayoutInflater inflater=(LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View view=inflater.inflate(R.layout.layout_custom_toast_view,null);
            TextView textView=(TextView)view.findViewById(R.id.textView);
            Toast toast=new Toast(context);
            toast.setGravity(Gravity.BOTTOM|Gravity.LEFT, 0, (int) (83 * density));
            toast.setView(view);
            //textView.setText(text);
            toast.show();

        }
    }


}

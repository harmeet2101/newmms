package com.mbopartners.mbomobile.ui.util;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.mbopartners.mbomobile.ui.R;

/**
 * Created by vinove on 5/5/16.
 */
public class WorkTrackingUiUtils {



    public static void disableEnableViewTree(boolean value, View view,Context context){
        if (view instanceof ViewGroup) {
            ViewGroup vg = (ViewGroup) view;
            for (int i = 0; i < vg.getChildCount(); i++){
                View child = vg.getChildAt(i);
                if(i==0)
                {
                    TextView textView=(TextView)child;
                    textView.setTextColor(context.getResources().getColor(R.color.mbo_light_grey_weekly));
                    textView.setBackgroundResource(R.drawable.light_grey_round_shape);
                }
                disableEnableViewTree(value, child, context);
            }
        }
        view.setEnabled(value);
    }
    public static void disableEnableTreeLeafs(boolean value, View view,Context context){
        if (view instanceof ViewGroup) {
            ViewGroup vg = (ViewGroup) view;
            vg.setEnabled(true);
            for (int i = 0; i < vg.getChildCount(); i++){
                View child = vg.getChildAt(i);
                if(i==0)
                {
                    TextView textView=(TextView)child;
                    textView.setTextColor(context.getResources().getColor(R.color.mbo_theme_blue_primary));
                    textView.setBackgroundResource(R.drawable.light_blue_shape);
                }
                disableEnableTreeLeafs(value, child, context);
            }
        } else {
            view.setEnabled(value);
        }
    }
}

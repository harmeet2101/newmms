package ua.com.mobidev.android.framework.util;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import ua.com.mobidev.android.framework.R;


/**
 *
 */
public class UiUtils {
    public static void hideActionBar(AppCompatActivity appCompatActivity) {
        ActionBar actionBar = appCompatActivity.getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }
    }

    /**
     * Set disabled/enabled all members of viewTree
     * @param value
     * @param view
     */
    public static void disableEnableViewTree(boolean value, View view){
        if (view instanceof ViewGroup) {
            ViewGroup vg = (ViewGroup) view;
            for (int i = 0; i < vg.getChildCount(); i++){
                View child = vg.getChildAt(i);
                disableEnableViewTree(value, child);
            }
        }
        view.setEnabled(value);
    }

    /**
     * Set disabled/enabled only child members of viewTree that do not contain other child (Leaf's)
     * @param value
     * @param view
     */
    public static void disableEnableTreeLeafs(boolean value, View view){
        if (view instanceof ViewGroup) {
            ViewGroup vg = (ViewGroup) view;
            vg.setEnabled(true);
            for (int i = 0; i < vg.getChildCount(); i++){
                View child = vg.getChildAt(i);
                disableEnableTreeLeafs(value, child);
            }
        } else {
            view.setEnabled(value);
        }
    }


}

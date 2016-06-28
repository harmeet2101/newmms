package com.mbopartners.mbomobile.ui.util;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Pair;

import com.mbopartners.mbomobile.ui.activity.passcodelock.AppLockManager;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Communicator {
    public static void startSendEmail(Context context, String email, String subject, String text, CharSequence chooserTitle) {
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.putExtra(Intent.EXTRA_EMAIL, new String[]{email});
        intent.putExtra(Intent.EXTRA_SUBJECT, subject);
        intent.putExtra(Intent.EXTRA_TEXT, text);
        // need this to prompts email client only
        intent.setType("message/rfc822");
        AppLockManager.getInstance().setExtendedTimeout();
        context.startActivity(Intent.createChooser(intent, chooserTitle));


    }

    public static Pair<Intent, Uri> getCameraIntent(Context context) {
        Intent galleryIntent = new Intent(Intent.ACTION_GET_CONTENT, null);
        galleryIntent.setType("image/*");

        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        ContentValues values = new ContentValues();
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        values.put(MediaStore.Images.Media.TITLE, "IMG_" + timeStamp + ".jpg");
        Uri fileUri = context.getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);

        Bundle newExtras = new Bundle();
        if (fileUri != null) {
            newExtras.putParcelable(MediaStore.EXTRA_OUTPUT, fileUri);
            newExtras.putString("fileName",timeStamp);
        } else {
            newExtras.putBoolean("return-data", true);
        }
        cameraIntent.putExtras(newExtras);

//        cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);
//        cameraIntent.putExtra("return-data", false);
        Intent chooser = new Intent(Intent.ACTION_CHOOSER);
        chooser.putExtra(Intent.EXTRA_INTENT, galleryIntent);

        chooser.putExtra(Intent.EXTRA_TITLE, "Take picture");
        Intent[] intentArray = {cameraIntent};
        chooser.putExtra(Intent.EXTRA_INITIAL_INTENTS, intentArray);
        return new Pair<>(cameraIntent, fileUri);
    }

    public static Intent getCameraIntent_01(Context context)
    {
        Intent galleryIntent = new Intent(Intent.ACTION_GET_CONTENT, null);
        galleryIntent.setType("image/*");

        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        ContentValues values = new ContentValues();
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        values.put(MediaStore.Images.Media.TITLE, "IMG_" + timeStamp + ".jpg");
        Uri fileUri = context.getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);

        Bundle newExtras = new Bundle();
        if (fileUri != null) {
            newExtras.putParcelable(MediaStore.EXTRA_OUTPUT, fileUri);
            newExtras.putString("fileName",timeStamp);
        } else {
            newExtras.putBoolean("return-data", true);
        }
        cameraIntent.putExtras(newExtras);

//        cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);
//        cameraIntent.putExtra("return-data", false);
        Intent chooser = new Intent(Intent.ACTION_CHOOSER);
        chooser.putExtra(Intent.EXTRA_INTENT, galleryIntent);

        chooser.putExtra(Intent.EXTRA_TITLE, "Take picture");
        Intent[] intentArray = {cameraIntent};
        chooser.putExtra(Intent.EXTRA_INITIAL_INTENTS, intentArray);
        return cameraIntent;
    }
}

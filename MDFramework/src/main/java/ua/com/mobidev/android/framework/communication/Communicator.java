package ua.com.mobidev.android.framework.communication;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;

public class Communicator {
    public static void startSendEmail(Context context, String email, String subject, String text) {
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.putExtra(Intent.EXTRA_EMAIL, new String[] { email });
        intent.putExtra(Intent.EXTRA_SUBJECT, subject);
        intent.putExtra(Intent.EXTRA_TEXT, text);
        // need this to prompts email client only
        intent.setType("message/rfc822");
        context.startActivity(Intent.createChooser(intent, "Send Email"));
    }

    public static void startExternalBrowser(Context context, String uri) {
        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
        context.startActivity(Intent.createChooser(browserIntent, "Choose browser"));
    }
}

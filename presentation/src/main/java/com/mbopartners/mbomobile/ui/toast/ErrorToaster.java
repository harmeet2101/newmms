package com.mbopartners.mbomobile.ui.toast;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.WindowManager;
import android.widget.Toast;

import com.mbopartners.mbomobile.ui.R;

import java.util.HashMap;
import java.util.Map;

public class ErrorToaster {
    Map<Integer, String> codes = new HashMap<>(120);

    {
        codes.put(100, "Continue");
        codes.put(101, "Switching Protocols");
        codes.put(102, "Processing(WebDAV; RFC 2518)");
        codes.put(200, "OK");
        codes.put(201, "Created");
        codes.put(202, "Accepted");
        codes.put(203, "Non-Authoritative Information (since HTTP/1.1)");
        codes.put(204, "No Content");
        codes.put(205, "Reset Content");
        codes.put(206, "Partial Content (RFC 7233)");
        codes.put(207, "Multi-Status (WebDAV; RFC 4918)");
        codes.put(208, "Already Reported (WebDAV; RFC 5842)");
        codes.put(226, "IM Used (RFC 3229)");
        codes.put(300, "Multiple Choices");
        codes.put(301, "Moved Permanently");
        codes.put(302, "Found");
        codes.put(303, "See Other (since HTTP/1.1)");
        codes.put(304, "Not Modified (RFC 7232)");
        codes.put(305, "Use Proxy (since HTTP/1.1)");
        codes.put(306, "Switch Proxy");
        codes.put(307, "Temporary Redirect (since HTTP/1.1)");
        codes.put(308, "Permanent Redirect (RFC 7538)");
        codes.put(400, "Bad Request");
        codes.put(401, "Unauthorized (RFC 7235)");
        codes.put(402, "Payment Required");
        codes.put(403, "Forbidden");
        codes.put(404, "Not Found");
        codes.put(405, "Method Not Allowed");
        codes.put(406, "Not Acceptable");
        codes.put(407, "Proxy Authentication Required (RFC 7235)");
        codes.put(408, "Request Timeout");
        codes.put(409, "Conflict");
        codes.put(410, "Gone");
        codes.put(411, "Length Required");
        codes.put(412, "Precondition Failed (RFC 7232)");
        codes.put(413, "Request Entity Too Large");
        codes.put(414, "Request-URI Too Long");
        codes.put(415, "Unsupported Media Type");
        codes.put(416, "Requested Range Not Satisfiable (RFC 7233)");
        codes.put(417, "Expectation Failed");
        codes.put(418, "I'm a teapot (RFC 2324)");
        codes.put(419, "Authentication Timeout (not in RFC 2616)");
        codes.put(420, "Method Failure (Spring Framework)");
        codes.put(420, "Enhance Your Calm(Twitter)");
        codes.put(421, "Misdirected Request(HTTP/2)");
        codes.put(422, "Unprocessable Entity(WebDAV; RFC 4918)");
        codes.put(423, "Locked(WebDAV; RFC 4918)");
        codes.put(424, "Failed Dependency (WebDAV; RFC 4918)");
        codes.put(426, "Upgrade Required");
        codes.put(428, "Precondition Required (RFC 6585)");
        codes.put(429, "Too Many Requests(RFC 6585)");
        codes.put(431, "Request Header Fields Too Large (RFC 6585)");
        codes.put(440, "Login Timeout (Microsoft)");
        codes.put(444, "No Response(Nginx)");
        codes.put(449, "Retry With(Microsoft)");
        codes.put(450, "Blocked by Windows Parental Controls(Microsoft)");
        codes.put(451, "Unavailable For Legal Reasons(Internet draft)");
        codes.put(451, "Redirect(Microsoft)");
        codes.put(494, "Request Header Too Large(Nginx)");
        codes.put(495, "Cert Error(Nginx)");
        codes.put(496, "No Cert(Nginx)");
        codes.put(497, "HTTP to HTTPS(Nginx)");
        codes.put(498, "Token expired/invalid (Esri)");
        codes.put(499, "Client Closed Request(Nginx)");
        codes.put(499, "Token required(Esri)");
        codes.put(500, "Internal Server Error");
        codes.put(501, "Not Implemented");
        codes.put(502, "Bad Gateway");
        codes.put(503, "Service Unavailable");
        codes.put(504, "Gateway Timeout");
        codes.put(505, "HTTP Version Not Supported");
        codes.put(506, "Variant Also Negotiates (RFC 2295)");
        codes.put(507, "Insufficient Storage(WebDAV; RFC 4918)");
        codes.put(508, "Loop Detected (WebDAV; RFC 5842)");
        codes.put(509, "Bandwidth Limit Exceeded (Apache bw/limited extension)[31]");
        codes.put(510, "Not Extended (RFC 2774)");
        codes.put(511, "Network Authentication Required(RFC 6585)");
        codes.put(520, "Unknown Error");
        codes.put(598, "Network read timeout error (Unknown)");
        codes.put(599, "Network connect timeout error (Unknown)");
    }

    public void showHttpStatusMessage (Activity activity, int httpErrorCode, String details) {
        String httpErrorText = codes.get(httpErrorCode);
        StringBuilder text = new StringBuilder();
        if (httpErrorText == null) {
            text.append("Http Error. Code : " + httpErrorCode);
        } else {
            text.append("Http Error "  + httpErrorCode + " " + httpErrorText);
        }

        if (details != null) {
            text.append("Details : \n" + details);
        }

        int duration = Toast.LENGTH_LONG;
        DisplayMetrics displayMetrics = activity.getResources().getDisplayMetrics();
        float density = displayMetrics.density;
        Toast toast = Toast.makeText(activity, text, duration);
        toast.setGravity(Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, (int)(83 * density));
        toast.show();
    }

    public void showErrorDetailedDialog(Context context, String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("API error");
        builder.setMessage(message);
        builder.setPositiveButton(R.string.mbo_LogExpense_Save_Error_dialog_Button_OK, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
            }
        });
        builder.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
            }
        });

        AlertDialog dialog = builder.create();
        dialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        dialog.show();
    }

}

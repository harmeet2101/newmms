package com.mbopartners.mbomobile.ui.dialog;

import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Typeface;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import com.mbopartners.mbomobile.ui.R;
import com.mbopartners.mbomobile.ui.util.FontController;

public class ApiErrorDialog{

    public static void showApi_NoInternetConnectionError(Context context) {
        showApiErrorDialog(
                context,
                R.string.mbo_Error_dialog__No_Internet_connection__title,
                R.string.mbo_Error_dialog__No_Internet_connection__message,
                R.string.mbo_Error_dialog__General__Button_OK
        );
    }

    public static void showApi_FailedToCreateAnExpenseError(Context context) {
        showApiErrorDialog(
                context,
                R.string.mbo_Error_dialog__General__title,
                R.string.mbo_Error_dialog__Failed_ToCreate_Expense__message,
                R.string.mbo_Error_dialog__General__Button_OK
        );
    }

    public static void showApi_FailedToUpdateError(Context context) {
        showApiErrorDialog(
                context,
                R.string.mbo_Error_dialog__General__title,
                R.string.mbo_Error_dialog__Failed_To_Update__message,
                R.string.mbo_Error_dialog__General__Button_OK
        );
    }
    public static void showApi_FailedToSubmitTimeError(Context context) {
        showApiErrorDialog(
                context,
                R.string.mbo_Error_dialog__General__title,
                R.string.mbo_Error_dialog__Failed_To_Submit_Time__message,
                R.string.mbo_Error_dialog__General__Button_OK
        );
    }

    public static void showApi_GeneralServerError(Context context) {
        showApiErrorDialog(
                context,
                R.string.mbo_Error_dialog__General__title,
                R.string.mbo_Error_dialog__General_Server_Error__message,
                R.string.mbo_Error_dialog__General__Button_OK
        );
    }

    public static void showApi_ExpiredCredentialsError(Context context) {
        showApiErrorDialog(
                context,
                R.string.mbo_Error_dialog__General__title,
                R.string.mbo_Error_dialog__Invalid_Username_Or_Password__message,
                R.string.mbo_Error_dialog__General__Button_OK
        );
    }

    public static void showApi_DataValidationNotPassedError(Context context) {
        showApiErrorDialog(
                context,
                R.string.mbo_Error_dialog__General__title,
                R.string.mbo_Error_dialog__Data_Validation_Not_Passed_Error__message,
                R.string.mbo_Error_dialog__General__Button_OK
        );
    }

    private static void showApiErrorDialog(Context context, int titleId, int messageId, int buttonTextId) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(titleId);
        builder.setMessage(messageId);
        builder.setPositiveButton(buttonTextId, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
            }
        });
        builder.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
            }
        });

        AlertDialog errorDialog = builder.create();
        errorDialog.setCanceledOnTouchOutside(false);
//        activity.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        errorDialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        errorDialog.show();
    }


    public static void showApiDialog(final Context context,String...args)
    {
        iSaveexpenseListener=(ISaveexpenseListener)context;
        android.app.AlertDialog.Builder builder=new android.app.AlertDialog.Builder(context,R.style.mboAlertTheme);
            /*LayoutInflater layoutInflater=(LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View view=layoutInflater.inflate(R.layout.layout_expense_submit_error_dialog, null);
            TextView cancelTextview=(TextView)view.findViewById(R.id.canceltext);
            TextView submitTextview=(TextView)view.findViewById(R.id.submittext);
            builder.setView(view);

            TextView messageTextview=(TextView)view.findViewById(R.id.msg);
            messageTextview.setText(args[0]+" "+args[2]+"$"+args[1]+ "transmission failed.");
            //dialog_submitTextview=(TextView)view.findViewById(R.id.submittext);
            //dialog_cancelTextview=(TextView)view.findViewById(R.id.canceltext);
            TextView dialog_heading=(TextView)view.findViewById(R.id.heading);
            setFont(context,new View[]{(View)dialog_heading,(View)cancelTextview,(View)submitTextview,(View)messageTextview});
            cancelTextview.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    alertDialog.dismiss();
                }
            });
            submitTextview.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {;
                    iSaveexpenseListener.saveExpense();
                    alertDialog.dismiss();
                }
            });*/
        builder.setTitle("Transmission Failed");
        builder.setMessage(args[0]+" "+args[2]+" $"+args[1]+ " transmission has failed.");
        builder.setCancelable(true);

        builder.setPositiveButton("RETRY", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                iSaveexpenseListener.saveExpense();
                alertDialog.dismiss();
            }
        }).setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                alertDialog.dismiss();
            }
        });

        alertDialog=builder.create();
        alertDialog.setCancelable(true);
        alertDialog.show();
    }

    private static ISaveexpenseListener iSaveexpenseListener;
    private static android.app.AlertDialog alertDialog;
    public interface ISaveexpenseListener{
        void saveExpense();
    }


    public static void setFont(Context context,View... args) {
        TextView headingtextView1=(TextView)args[0];
        TextView canceltextView1=(TextView)args[1];
        TextView submittextView1=(TextView)args[2];
        TextView msgTextview=(TextView)args[3];
        Typeface typeface_regular= Typeface.createFromAsset(context.getAssets(), "font/Roboto-Regular.ttf");
        Typeface typeface_bold=Typeface.createFromAsset(context.getAssets(), "font/Roboto-Bold.ttf");
        canceltextView1.setTypeface(typeface_regular);
        submittextView1.setTypeface(typeface_regular);
        headingtextView1.setTypeface(typeface_bold);
        msgTextview.setTypeface(typeface_regular);
    }
}

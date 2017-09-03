package apps.cherry.cherryappsblog.util.simple_dialog;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.util.Log;

import apps.cherry.cherryappsblog.R;

/**
 * Created by anel.calvo on 20/02/17.
 */
public class SimpleDialog {
    //private static final String TAG = getClass().getSimpleName();
    private static final String TAG = "Dialog";

    private Activity activity;

    public SimpleDialog(Activity activity) {
        this.activity=activity;
    }



    public static void showDecisionDialogListener(final String confirmMessage, Context context, final DecisionDialogListener listener) {
        Log.d(TAG, "Error message: " + confirmMessage);
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Atención");
        builder.setMessage(confirmMessage);
        builder.setPositiveButton(context.getString(R.string.done), new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                listener.responseFromDecisionDialog(confirmMessage, "DONE");
            }
        });
        builder.setNegativeButton(context.getString(R.string.cancel), new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                listener.responseFromDecisionDialog(confirmMessage, "CANCEL");
            }
        });
        AlertDialog alert = builder.create();
        alert.show();
    }


    public void showConfirmationDialogWithListener(final String confirmMessage, Context context, final ConfirmationDialogListener listener) {
        Log.d(TAG, "Error message: " + confirmMessage);
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Atención");
        builder.setMessage(confirmMessage);
        builder.setNeutralButton(R.string.done, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                listener.okFromConfirmationDialog(confirmMessage);
            }
        });
        AlertDialog alert = builder.create();
        alert.show();
    }
}

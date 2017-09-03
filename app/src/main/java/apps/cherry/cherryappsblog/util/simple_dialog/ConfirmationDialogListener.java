package apps.cherry.cherryappsblog.util.simple_dialog;

/**
 * @author Created by anel.calvo on 14/02/15.
 * This interface is used for communication between ConfirmationDialogListener and RequestManager. It is to show and hear information in DialogFragment.
 */
public interface ConfirmationDialogListener {
    public void okFromConfirmationDialog(String message);
}

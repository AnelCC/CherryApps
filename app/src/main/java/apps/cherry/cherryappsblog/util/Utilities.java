package apps.cherry.cherryappsblog.util;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import apps.cherry.cherryappsblog.R;


/**
 * this class is usedd to utilities.
 */
public class Utilities {

    public static boolean flag = true;
    public static int position = 0;

    /**
     * This method is used to describe device configuration information that can impact the resources the application retrieves.
     * @param context it is activity of app.
     * @return value indicating the screen is at least approximately.
     */
    public static boolean isHandset(Context context) {
        return (context.getResources().getConfiguration().screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK)
                < Configuration.SCREENLAYOUT_SIZE_LARGE;
    }

    /**
     * This method is used to should be called as a result of the user doing some actually than fairly explicitly requests to have the input window hidden.
     * @param context
     * @param editField
     */
    public static void hideKeyboard(Context context, View editField) {

        InputMethodManager imm = (InputMethodManager)context.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(editField.getWindowToken(), 0);
    }

    /**
     * @deprecated
     * @param encodedString
     * @return
     */
    public static Bitmap stringToBitmap(String encodedString) {
        try{
            byte [] encodeByte = Base64.decode(encodedString, Base64.DEFAULT);
            return BitmapFactory.decodeByteArray(encodeByte, 0, encodeByte.length);
        }catch(Exception e){
            e.getMessage();
            return null;
        }
    }

    /**
     * This method is used to iterator of object and return a map whit information returned.
     * @param obj
     * @return map of information.
     * @throws JSONException
     */
    public static Map<String,String> jsonToMap(JSONObject obj) throws JSONException {
        Map<String, String> map = new HashMap<String, String>();
        Iterator<?> keys        = obj.keys();

        while( keys.hasNext() ){
            String key = (String)keys.next();
            String value = obj.getString(key);
            map.put(key, value);
        }
        return  map;
    }

    /**
     * This method is used to accessing and modifying preference data.
     * @param key
     * @param value
     * @param activity
     */
    public static void saveInPreferencesKeyAndValue(String key, String value, Activity activity){
        SharedPreferences.Editor editor = (activity.getSharedPreferences("Tracker_Preferences", Context.MODE_PRIVATE)).edit();
        editor.putString(key, value);
        editor.commit();
    }

    /**
     * This method is used to modifying preference data.
     * @param key
     * @param activity
     * @return
     */
    public static String getPreferencesValueForKey(String key, Activity activity){
        SharedPreferences sharedPref        = activity.getSharedPreferences("Tracker_Preferences", Context.MODE_PRIVATE);
        return sharedPref.getString(key,"CLEAR");
    }

    /**
     * @deprecated
     * @param context
     * @param email
     * @param view
     * @return
     */
    public static boolean validateEmail(Context context, String email, EditText view){
        String emailPattern = "[a-zA-Z0-9._-]+@[a-zA-Z]+\\.+[a-z]+";
        email=email.trim();
        if (email.isEmpty()){
            view.setError(context.getResources().getText(R.string.error_empty_field).toString());
            view.requestFocus();
            return false;
        }

        if (email.matches(emailPattern))
        {
            return true;
        }
        else{
            view.setError(context.getResources().getText(R.string.error_invalid_email_address).toString());
            view.requestFocus();
            return false;
        }

    }

    /**
     * @deprecated
     * @param context
     * @param field
     * @param view
     * @return
     */
    public static boolean validateCommonField(Context context, String field, EditText view){
        if(field.isEmpty()){
            view.setError(context.getResources().getText(R.string.error_empty_field).toString());
            view.requestFocus();
            return false;
        }
        return true;
    }

    /**
     * @deprecated
     * @param code
     * @return
     */
    public static boolean validatedigits(String code){
        String pattern = "[0-9]+";
        if (code.matches(pattern))
            return true;
        return false;
    }

    /**
     * @deprecated
     * @param code
     * @return
     */
    public static boolean validatetext(String code){
        String pattern = "[a-zA-Z0-9]+";
        if (code.matches(pattern))
            return true;
        return false;
    }

    /**
     * @deprecated
     * @param cadena
     * @return
     */
    public static double getSaveString(String cadena){
        double saveString = 0;

        try{
            Double d = Double.parseDouble(""+cadena);
            saveString = d;

        }catch (Exception e){
            return saveString;
        }

        return saveString;
    }

    /**
     * @deprecated
     * @param amount
     * @param strLength
     * @return
     */
    public static String setReceiptMoneyNumberFormat (double amount, int strLength){

        DecimalFormatSymbols simbolo=new DecimalFormatSymbols();
        simbolo.setDecimalSeparator('.');
        simbolo.setGroupingSeparator(',');

        DecimalFormat form  = new DecimalFormat("###,###.##",simbolo);
        String strAmount    = form.format(amount);
        while (strAmount.length()<strLength){
            strAmount = " "+strAmount;
        }
        strAmount = "$"+strAmount;

        return strAmount;
    }

    /**
     * @deprecated
     * @param errorMessage
     * @param activity
     */
    //Mi mensaje de error..... PARA MI DIALOG
    //Si no hay internet y un servicio web no puede recuperarse este dialogo aparecera
    public static void showErrorDialog(String errorMessage, final Activity activity){
        Log.d("ShowErrorDialog", "Error message: " + errorMessage);
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder.setTitle("Error");
        builder.setMessage(errorMessage);
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
            }
        });
        AlertDialog alert = builder.create();
        alert.show();
    }

    /**
     * This method is used to return a date format.
     * @param timeStamp
     * @return
     */
    public static String stringDateFromTimeStamp(long timeStamp){
        try{
            DateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
            Date netDate = (new Date(timeStamp));
            return sdf.format(netDate);
        }
        catch(Exception ex){
            return "xx";
        }
    }

    /**
     * This method is used to standardize the used of special characters.
     * @param input
     * @return string normalized.
     */
    public static String normalizeString(String input){

        if (input.length() > 0){
            while (input.charAt(0) == ' '){
                input = input.substring(1,input.length());
                if (input.length() <= 0)
                    break;
            }
        }
        String forReplace = "ÁÀÄÉÈËÍÌÏÓÒÖÚÙÜáéíóúÑñ";
        String toReplace   = "AAAEEEIIIOOOUUUaeiouNn";
        for (int i=0; i<forReplace.length(); i++) {
            input = input.replace(forReplace.charAt(i), toReplace.charAt(i));
        }//for i
        if (input.length() > 0){
            while (input.charAt(input.length()-1) == ' '){
                input = input.substring(0,input.length()-1);
                if (input.length() <= 0)
                    break;
            }
        }
        return input;
    }


}
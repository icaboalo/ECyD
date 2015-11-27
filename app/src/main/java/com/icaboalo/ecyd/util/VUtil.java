package com.icaboalo.ecyd.util;

import android.content.Context;
import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.widget.EditText;

/**
 * Created by icaboalo on 11/24/2015.
 */
public class VUtil {
    public static String extractEditText(EditText editText){
        return editText.getText().toString();
    }

    public static void showMessage(Context context, int StringId, View container){
        String message = context.getString(StringId);
        Snackbar.make(container, message, Snackbar.LENGTH_LONG).show();
    }

    public static void showMessage(String message, View container){
        Snackbar.make(container, message, Snackbar.LENGTH_LONG).show();
    }

    public static void goToActivity(Context context, Class classToGo){
        Intent intent = new Intent(context, classToGo);
        context.startActivity(intent);
    }

    public static View inflateView(Context context, int layout){
        return View.inflate(context, layout, null);
    }
}

package shen.da.ye.imay.utils;

import android.widget.Toast;

import shen.da.ye.imay.Application;


/**
 * Created by cy on 15-9-17.
 */
public class ToastUtils {
    private static Toast toast;

    public static void showToast(String text) {
        if (toast == null) {
            toast = Toast.makeText(Application.sharedInstance, "", Toast.LENGTH_SHORT);
        }
        toast.setText(text);
        toast.show();
    }
}

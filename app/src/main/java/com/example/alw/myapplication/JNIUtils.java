package com.example.alw.myapplication;

import android.util.Log;

/**
 * Created by David on 2017/5/11.
 * Des :
 */

public class JNIUtils {


    public static native int gaussBlur(Object bitmap, int radius);

    public static native int aaa();

    public static native int ccc();

    // Used to load the 'native-lib' library on application startup.
    static {
        System.loadLibrary("advanced_test");
        System.loadLibrary("opencv_java3");
    }

    public void helloFromJava() {
        Log.e("eee", "helloFromJava");
    }

}

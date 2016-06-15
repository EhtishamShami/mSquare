package thinktechsol.msquare.utils;

import android.view.View;
import android.view.animation.AlphaAnimation;

/**
 * Created by Arshad.Iqbal on 5/17/2016.
 */
public class Constant {

    public static String baseUrl = "http://weblinelab.com/demo/msquare/api/";
    public static String imgbaseUrl = "http://weblinelab.com/demo/msquare/";
    public static int screenHeight;
    public static int screenWidth;

    public static String folderNameForCapturedImage = "MSquare";

    public static void makeImageAlphLowOrHigh(View v, float alphaValue) {
        AlphaAnimation alpha = new AlphaAnimation(alphaValue, alphaValue);
        alpha.setFillAfter(true);
        v.startAnimation(alpha);
    }

    public static int getSize(String dimension, float size) {
        float x = 0;
        if (dimension.equals("w")) {
            x = (size / 100) * Constant.screenWidth;
        } else {
            x = (size / 100) * Constant.screenHeight;
        }
        return (int) x;
    }
}

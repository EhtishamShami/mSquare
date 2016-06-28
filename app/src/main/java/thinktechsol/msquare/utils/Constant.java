package thinktechsol.msquare.utils;

import android.view.View;
import android.view.animation.AlphaAnimation;

import java.util.ArrayList;

import thinktechsol.msquare.model.Response.ProductImages;
import thinktechsol.msquare.model.Response.getSellerProductsResponse;

/**
 * Created by Arshad.Iqbal on 5/17/2016.
 */
public class Constant {

    //    weblinelab url
//    public static String baseUrl = "http://weblinelab.com/demo/msquare/api/";
//    public static String imgbaseUrl = "http://weblinelab.com/demo/msquare/";

    //    original url
    public static String baseUrl =    "http://smartit.ae/api/";
    public static String imgbaseUrl = "http://smartit.ae/";
    public static int screenHeight;
    public static int screenWidth;

    public static String folderNameForCapturedImage = "MSquare";

    //    list of sellers products
    public static ArrayList<getSellerProductsResponse> productList;
    //    seller product single
    public static getSellerProductsResponse singleProduct;
    //    list of images of the seller products
    public static ArrayList<ProductImages> productImagesList;

    //    add or view product selection switch
    public static boolean addOrViewProduct;

    public static String logInAs;

    // buttons clicked (making btn as selected)
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

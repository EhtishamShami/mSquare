package thinktechsol.msquare.globels;

/**
 * Created by Arshad Iqbal on 07/06/2016.
 */

import java.util.ArrayList;

import thinktechsol.msquare.model.Buyer.getServiceSellersModel;
import thinktechsol.msquare.model.Buyer.getServiceSellersProductModel;
import thinktechsol.msquare.model.SellerLogInResponse;
import thinktechsol.msquare.services.sellerLogIn;


public class globels {
    public static globels globelObject = new globels();

    public static globels getGlobelRef() {
        return globelObject;
    }

    public SellerLogInResponse sellerlogin;
    public String IdForAddProduct;

    //seller product list
    public static ArrayList<getServiceSellersModel> SellersProductDetailList;

    //buyer's view (product list)
    public static ArrayList<getServiceSellersProductModel> productList;
    public static String serviceSellerProductId;
    public int SelectedServicesPrice;
    public String allSelectedServices = "";
    public ArrayList<String> selectedServicesIds;
    public ArrayList<String> selectedProductsIds;

    public static String orderType = "0";

}

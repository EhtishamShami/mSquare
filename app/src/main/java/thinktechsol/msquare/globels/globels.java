package thinktechsol.msquare.globels;

/**
 * Created by Arshad Iqbal on 07/06/2016.
 */

import java.util.ArrayList;

import thinktechsol.msquare.model.Buyer.Products;
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
    public static ArrayList<Products> productList2;
    public static Products singleProductForBuyProDetail;
    public static String serviceSellerProductId;
    public int SelectedServicesPrice;
    public String allSelectedServices = "";
    public ArrayList<String> selectedServicesIds;
    public ArrayList<String> selectedProductsIds;

    public static String orderType = "0";
    public static String approveRecentOrder = "1";
    public static String approveInProcessOrder = "3";
    public static String rejectOrder = "2";
    public static String BuyerRejectOrder = "4";


    public static String orderId_for_ordr_info = null;
    public static String buyerLoginId = null;

}

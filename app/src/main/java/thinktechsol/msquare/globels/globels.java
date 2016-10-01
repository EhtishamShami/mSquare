package thinktechsol.msquare.globels;

/**
 * Created by Arshad Iqbal on 07/06/2016.
 */

import java.util.ArrayList;

import thinktechsol.msquare.R;
import thinktechsol.msquare.model.Buyer.BuyerDeshBoardStatesModel;
import thinktechsol.msquare.model.Buyer.BuyerLogin;
import thinktechsol.msquare.model.Buyer.BuyerReservationListModel;
import thinktechsol.msquare.model.Buyer.FiltersModel;
import thinktechsol.msquare.model.Buyer.Products;
import thinktechsol.msquare.model.Buyer.getServiceSellersModel;
import thinktechsol.msquare.model.Buyer.getServiceSellersProductModel;
import thinktechsol.msquare.model.SellerDashBoardStatsModel;
import thinktechsol.msquare.model.SellerLogInResponse;


public class globels {
    public static globels globelObject = new globels();

    public static globels getGlobelRef() {
        return globelObject;
    }

    public static SellerLogInResponse sellerlogin;
    public String IdForAddProduct;
    public String IDFORSEARCH;

    //seller product list
    public static ArrayList<getServiceSellersModel> SellersProductDetailList;

    //buyer's view (product list)
    public static ArrayList<getServiceSellersProductModel> productList;
    public static ArrayList<Products> productList2;

    public static Products singleProductForBuyProDetail;
    public static String serviceSellerId;
    public int SelectedServicesPrice;
    public int SelectedServicesPriceNew;
    public String SelectedServicesDeliveryTime = "0";
    public String allSelectedServices = "";
    public ArrayList<String> selectedServicesIds;
    public ArrayList<String> selectedProductsIds;
    public ArrayList<String> selectedQuantityIds;

    public static String orderType = "0";
    public static String approveRecentOrder = "1";
    public static String approveInProcessOrder = "3";
    public static String rejectOrder = "2";
    public static String BuyerRejectOrder = "4";


    public static String orderId_for_ordr_info = null;
    public static String buyerLoginId = null;
    public static String sellerLoginServiceId = null;
    public static String sellerLoginId = null;
    public ArrayList<BuyerLogin> buyerLoginResponse;
    //buyer address details
    public static String houseNo;
    public static String streetNo;
    public static String area;
    public static String state;
    public static String phoneNo;


    public static ArrayList<BuyerDeshBoardStatesModel> buyerDeshBoardStatesList;
    public static ArrayList<BuyerReservationListModel> selectedProductListReservation;
    public ArrayList<SellerDashBoardStatsModel> SellerDashBoardStatsModel;


    public String MessageType = "1";
    public static String address = "";
    public static FiltersModel filterdDateObj;
    public boolean filteration = false;
    public boolean filteration2 = false;
    public static String deviceToken = "";
    public static String loginAsBuyerOrSeller = "";
    public static String isNotification = "Empty";
    public static int them_color = R.color.sellerOrderDetailTitleBg;
    public static String them_color2;
    public static String buyerEmailId;

}

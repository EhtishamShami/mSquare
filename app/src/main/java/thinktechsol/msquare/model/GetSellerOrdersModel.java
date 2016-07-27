package thinktechsol.msquare.model;

/**
 * Created by LENOVO on 7/27/2016.
 */

public class GetSellerOrdersModel {
    public String serviceRequestTime;
    public String staffId;
    public String lastModified;
    public String status;
    public String adminStatus;
    public SellerDetailsForOrder sellerDetails;
    public BuyerDetailsForOrder buyerDetails;
    public String categoryType;
    public String dated;
    public String buyerId;
    public String id;
    public String sellerId;
    public String noOfServices;
    public String extraRemarks;
    public String totalPrice;

    public GetSellerOrdersModel(String serviceRequestTime, String staffId, String lastModified, String status, String adminStatus, String categoryType, String dated, String buyerId, String id, String sellerId, String noOfServices, String extraRemarks, String totalPrice, SellerDetailsForOrder sellerDetails, BuyerDetailsForOrder buyerDetails) {
        this.serviceRequestTime = serviceRequestTime;
        this.staffId = staffId;
        this.lastModified = lastModified;
        this.status = status;
        this.adminStatus = adminStatus;
        this.sellerDetails = sellerDetails;
        this.buyerDetails = buyerDetails;
        this.categoryType = categoryType;
        this.dated = dated;
        this.buyerId = buyerId;
        this.id = id;
        this.sellerId = sellerId;
        this.noOfServices = noOfServices;
        this.extraRemarks = extraRemarks;
        this.totalPrice = totalPrice;
    }
}

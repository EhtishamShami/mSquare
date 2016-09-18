package thinktechsol.msquare.model.Buyer;

import java.util.ArrayList;

/**
 * Created by Arshad Iqbal on 30/06/2016.
 */

public class getServiceSellersProductModel {

    public SellerInfo sellerInfo;
    public ArrayList<ProductReviews> roductReviews;
    public ArrayList<Products> products;

    public getServiceSellersProductModel(SellerInfo sellerInfo, ArrayList<ProductReviews> roductReviews, ArrayList<Products> products) {
        this.sellerInfo = sellerInfo;
        this.roductReviews = roductReviews;
        this.products = products;
    }

//    public String id;
//    public String productRating;
//    public ArrayList<ProductImages> productImages;
//    public String title;
//    public String price;
//    public String deliveryTime;
//    public String serviceId;
//    public String status;
//    public String dateTime;
//    public String description;
//    public String sellerId;
//    public String productReviews;

//    public String distance;
//    public ServiceDetails serviceDetails;
//    public SellerInfo sellerDetails;

//    public getServiceSellersProductModel(String id, String sellerId, String serviceId, String description, String title, String price, String deliveryTime, String dateTime, String status, String distance, ArrayList<ProductImages> productImages, ServiceDetails serviceDetails, String productReviews, String productRating, SellerInfo sellerDetails) {
//        this.id = id;
//        this.sellerId = sellerId;
//        this.serviceId = serviceId;
//        this.description = description;
//        this.title = title;
//        this.price = price;
//        this.deliveryTime = deliveryTime;
//        this.dateTime = dateTime;
//        this.status = status;
//        this.distance = distance;
//        this.productImages = productImages;
//        this.serviceDetails = serviceDetails;
//        this.productReviews = productReviews;
//        this.productRating = productRating;
//        this.sellerDetails = sellerDetails;
//    }
}

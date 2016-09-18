package thinktechsol.msquare.model.Buyer;

import java.util.ArrayList;

import thinktechsol.msquare.model.OrderDetails.ProductImagesOrd;

/**
 * Created by Arshad Iqbal on 8/15/2016.
 */

public class ProductDetailsWL {
    public String id;
    public String productRating;
    public ArrayList<ProductImagesOrd> productImages;
    public String title;
    public String price;
    public String deliveryTime;
    public String serviceId;
    public String status;
    public String dateTime;
    public String description;
    public String sellerId;
//    public ArrayList<ProductReviews> productReviews;

    public ProductDetailsWL(String id, String productRating, ArrayList<ProductImagesOrd> productImages, String title, String price, String deliveryTime, String serviceId, String status, String dateTime, String description, String sellerId/*, ArrayList<ProductReviews> productReviews*/) {
        this.id = id;
        this.productRating = productRating;
        this.productImages = productImages;
        this.title = title;
        this.price = price;
        this.deliveryTime = deliveryTime;
        this.serviceId = serviceId;
        this.status = status;
        this.dateTime = dateTime;
        this.description = description;
        this.sellerId = sellerId;
        //this.productReviews = productReviews;
    }
}

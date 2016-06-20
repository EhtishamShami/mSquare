package thinktechsol.msquare.model.Response;

import java.util.ArrayList;

/**
 * Created by Arshad.Iqbal on 6/2/2016.
 */
public class getSellerProductsResponse {
    public String id;
    public String sellerId;
    public String serviceId;
    public String description;
    public String title;
    public String price;
    public String deliveryTime;
    public String dateTime;
    public String status;
    public ProductImages productImages;
    public ArrayList<ProductImages> productImagesList;
    public ProductReviews productReviews;
    public ProductRating productRating;

    public getSellerProductsResponse(String id, String sellerId, String serviceId, String description, String title, String price, String deliveryTime, String dateTime, String status, ProductImages productImages, ArrayList<ProductImages> productImagesList/*, ProductReviews productReviews, ProductRating productRating*/) {
        this.id = id;
        this.sellerId = sellerId;
        this.serviceId = serviceId;
        this.description = description;
        this.title = title;
        this.price = price;
        this.deliveryTime = deliveryTime;
        this.dateTime = dateTime;
        this.status = status;
        this.productImages = productImages;
        this.productImagesList = productImagesList;
//        this.productReviews = productReviews;
//        this.productRating = productRating;
    }
}

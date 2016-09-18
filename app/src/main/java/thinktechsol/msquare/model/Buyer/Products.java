package thinktechsol.msquare.model.Buyer;

import java.util.ArrayList;

/**
 * Created by Arshad Iqbal on 7/19/2016.
 */

public class Products {
    public String id;
    public String productRating;
    public ArrayList<ProductImages> productImages;
    public String title;
    public String price;
    public String deliveryTime;
    public String serviceId;
    public String status;
    public String dateTime;
    public String description;
    public String sellerId;
    public boolean isChecked;
    public String proQuantity;
    public ArrayList<ProductReviews> productReviews;

    public Products(String id, String productRating, ArrayList<ProductImages> productImages, String title, String price, String deliveryTime, String serviceId, String status, String dateTime, String description, String sellerId, boolean isChecked, ArrayList<ProductReviews> productReviews, String proQuantity) {
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
        this.isChecked = isChecked;
        this.productReviews = productReviews;
        this.proQuantity = proQuantity;
    }
}

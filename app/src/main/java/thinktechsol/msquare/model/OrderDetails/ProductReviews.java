package thinktechsol.msquare.model.OrderDetails;

/**
 * Created by Arshad Iqbal on 8/15/2016.
 */

public class ProductReviews {
    public String id;
    public String reviewTitle;
    public String serviceId;
    public String status;
    public String sellerId;
    public String buyerId;
    public String reviewDescription;
    public String productId;

    public ProductReviews(String id, String reviewTitle, String serviceId, String status, String sellerId, String buyerId, String reviewDescription, String productId) {
        this.id = id;
        this.reviewTitle = reviewTitle;
        this.serviceId = serviceId;
        this.status = status;
        this.sellerId = sellerId;
        this.buyerId = buyerId;
        this.reviewDescription = reviewDescription;
        this.productId = productId;
    }
}

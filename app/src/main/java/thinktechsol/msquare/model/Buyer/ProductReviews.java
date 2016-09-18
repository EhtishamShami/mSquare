package thinktechsol.msquare.model.Buyer;

/**
 * Created by Arshad Iqbal on 16/06/2016.
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

//    public String id;
//    public String sellerProductId;
//    public String image;
//
//    public ProductReviews(String id, String sellerProductId, String image) {
//        this.id = id;
//        this.sellerProductId = sellerProductId;
//        this.image = image;
//    }
//    public ProductReviews(String image) {
//        this.image = image;
//    }
}

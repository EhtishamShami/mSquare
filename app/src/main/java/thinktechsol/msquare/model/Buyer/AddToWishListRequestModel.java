package thinktechsol.msquare.model.Buyer;

/**
 * Created by Arshad Iqbal on 7/19/2016.
 */

public class AddToWishListRequestModel {
    public String sellerId;
    public String serviceId;
    public String productId;
    public String buyerId;

    public AddToWishListRequestModel(String sellerId, String serviceId, String productId, String buyerId) {
        this.sellerId = sellerId;
        this.serviceId = serviceId;
        this.productId = productId;
        this.buyerId = buyerId;
    }
}

package thinktechsol.msquare.model.Buyer;

/**
 * Created by Arshad.Iqbal on 5/16/2016.
 */
public class BuyerWishListModel {

    public String id;
    public String sellerId;
    public String serviceId;
    public String productId;
    public String buyerId;
    public SellerDetailsWL sellerDetial;
    public ServiceDetailsWL serviceDetial;
    public ProductDetailsWL productDetial;

    public BuyerWishListModel(String id, String sellerId, String serviceId, String productId, String buyerId, SellerDetailsWL sellerDetial, ServiceDetailsWL serviceDetial, ProductDetailsWL productDetial) {
        this.id = id;
        this.sellerId = sellerId;
        this.serviceId = serviceId;
        this.productId = productId;
        this.buyerId = buyerId;
        this.sellerDetial = sellerDetial;
        this.serviceDetial = serviceDetial;
        this.productDetial = productDetial;
    }
}

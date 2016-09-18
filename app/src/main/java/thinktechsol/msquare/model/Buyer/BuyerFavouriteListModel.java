package thinktechsol.msquare.model.Buyer;

/**
 * Created by Arshad.Iqbal on 5/16/2016.
 */
public class BuyerFavouriteListModel {

    public String id;
    public String sellerId;
    public String buyerId;
    public SellerDetailsFav sellerDetial;

    public BuyerFavouriteListModel(String id, String sellerId, String buyerId, SellerDetailsFav sellerDetial) {
        this.id = id;
        this.sellerId = sellerId;
        this.buyerId = buyerId;

        this.sellerDetial = sellerDetial;
    }
}

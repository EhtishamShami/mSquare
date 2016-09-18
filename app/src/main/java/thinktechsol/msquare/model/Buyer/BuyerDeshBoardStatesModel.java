package thinktechsol.msquare.model.Buyer;

/**
 * Created by Arshad Iqbal on 16/06/2016.
 */

public class BuyerDeshBoardStatesModel {
    public String unReadMessages;
    public BuyerOrderStatesModel orderStates;
    public String wishlist;
    public String favourites;

    public BuyerDeshBoardStatesModel(String unReadMessages, BuyerOrderStatesModel orderStates, String wishlist, String favourites) {
        this.unReadMessages = unReadMessages;
        this.orderStates = orderStates;
        this.wishlist = wishlist;
        this.favourites = favourites;
    }
}

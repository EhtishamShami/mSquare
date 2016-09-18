package thinktechsol.msquare.model.Buyer;

/**
 * Created by Arshad Iqbal on 7/19/2016.
 */

public class BuyerProReviewsModel {
    public boolean isLbl;
    public String reviewTitle;
    public String reviewDescription;

    public BuyerProReviewsModel(String reviewTitle, String reviewDescription, boolean isLbl) {
        this.isLbl = isLbl;
        this.reviewTitle = reviewTitle;
        this.reviewDescription = reviewDescription;
    }
}

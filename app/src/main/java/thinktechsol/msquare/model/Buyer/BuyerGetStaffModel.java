package thinktechsol.msquare.model.Buyer;

/**
 * Created by LENOVO on 8/26/2016.
 */

public class BuyerGetStaffModel {
    public int imgSrc;

    public String id;
    public String name;
    public String sellerId;


    public BuyerGetStaffModel(int imgSrc, String id, String name, String sellerId) {
        this.imgSrc = imgSrc;
        this.id = id;
        this.name = name;
        this.sellerId = sellerId;
    }
}

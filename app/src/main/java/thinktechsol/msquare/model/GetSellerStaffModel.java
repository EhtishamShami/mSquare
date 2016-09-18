package thinktechsol.msquare.model;

/**
 * Created by arshadiqbal on 06/09/16.
 */
public class GetSellerStaffModel {
    public String id;
    public String name;
    public String sellerId;

    public GetSellerStaffModel(String id, String sellerId, String name) {
        this.id = id;
        this.sellerId = sellerId;
        this.name = name;
    }
}

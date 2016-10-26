package thinktechsol.msquare.model;

/**
 * Created by arshadiqbal on 06/09/16.
 */
public class GetSellerStaffModel {
    public String id;
    public String name;
    public String sellerId;
    public String fromTime;
    public String toTime;
    public String status;

    public GetSellerStaffModel(String id, String sellerId, String name, String fromTime, String toTime, String status) {
        this.id = id;
        this.sellerId = sellerId;
        this.name = name;
        this.fromTime = fromTime;
        this.toTime = toTime;
        this.status = status;
    }
}

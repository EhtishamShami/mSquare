package thinktechsol.msquare.model.OrderDetails;

/**
 * Created by Arshad Iqbal on 8/16/2016.
 */

public class ProductImagesOrd {
    public String id;
    public String sellerProductId;
    public String image;

    public ProductImagesOrd(String id, String sellerProductId, String image) {
        this.id = id;
        this.sellerProductId = sellerProductId;
        this.image = image;
    }
}

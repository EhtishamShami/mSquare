package thinktechsol.msquare.model.Response;

/**
 * Created by Arshad Iqbal on 16/06/2016.
 */

public class ProductImages {
    public String id;
    public String sellerProductId;
    public String image;

    public ProductImages(String id, String sellerProductId, String image) {
        this.id = id;
        this.sellerProductId = sellerProductId;
        this.image = image;
    }
}
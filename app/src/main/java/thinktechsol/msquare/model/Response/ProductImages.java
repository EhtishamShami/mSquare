package thinktechsol.msquare.model.Response;

/**
 * Created by Arshad Iqbal on 16/06/2016.
 */

public class ProductImages {
    public String id;
    public String sellerProductId;
    public String image;
    public boolean isLocalImg;

    public ProductImages(String id, String sellerProductId, String image,boolean local) {
        this.id = id;
        this.sellerProductId = sellerProductId;
        this.image = image;

        isLocalImg=local;
    }
    public ProductImages(String image) {
        this.image = image;
    }
}

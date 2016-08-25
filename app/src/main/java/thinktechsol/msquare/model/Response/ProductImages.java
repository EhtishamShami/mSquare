package thinktechsol.msquare.model.Response;

import android.graphics.Bitmap;

/**
 * Created by Arshad Iqbal on 16/06/2016.
 */

public class ProductImages {
    public String id;
    public String sellerProductId;
    public String image;
    public boolean isLocalImg;
    public Bitmap localImgBMP;

    public ProductImages(String id, String sellerProductId, String image,boolean local,Bitmap bmp) {
        this.id = id;
        this.sellerProductId = sellerProductId;
        this.image = image;

        isLocalImg=local;
        if(local)
            localImgBMP = bmp;
    }
    public ProductImages(String image) {
        this.image = image;
    }
}

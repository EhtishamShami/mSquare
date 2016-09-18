package thinktechsol.msquare.model;

/**
 * Created by Arshad.Iqbal on 6/2/2016.
 */
public class AddProductItem {
    public String label;
    public String imgUrl;
    public int bgColor;
    public String id;

    public AddProductItem(String id, String label, String imgUrl, int bgColor) {
        this.id = id;
        this.label = label;
        this.imgUrl = imgUrl;
        this.bgColor = bgColor;
    }
}

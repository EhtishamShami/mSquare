package thinktechsol.msquare.model;

/**
 * Created by Arshad.Iqbal on 6/2/2016.
 */
public class ViewProductItem {
    public String proName;
    public String status;
    public int imgUrl;
    public boolean isSelected;

    public ViewProductItem(String proName, String status, int imgUrl, boolean isSelected) {
        this.proName = proName;
        this.status = status;
        this.imgUrl = imgUrl;
        this.isSelected = isSelected;
    }
}

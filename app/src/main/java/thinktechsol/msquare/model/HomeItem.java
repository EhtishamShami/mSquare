package thinktechsol.msquare.model;

/**
 * Created by Arshad.Iqbal on 5/16/2016.
 */
public class HomeItem {
    public String label;
    public int icon;
    public int bgColor;

    public String label2;
    public int icon2;
    public int bgColor2;

    public float width;

    public HomeItem(String label, int icon, int bgColor, float width, String label2, int icon2, int bgColor2) {
        this.label = label;
        this.icon = icon;
        this.bgColor = bgColor;
        this.width = width;

        this.label2 = label2;
        this.icon2 = icon2;
        this.bgColor2 = bgColor2;
    }
}

package thinktechsol.msquare.model;

/**
 * Created by Arshad.Iqbal on 5/16/2016.
 */
public class Item {
    public String label;
    public int counter;
    public int icon;
    public int bgColor;

    public Item(String label, int counter, int icon, int bgColor) {
        this.label = label;
        this.counter = counter;
        this.icon = icon;
        this.bgColor = bgColor;
    }
}

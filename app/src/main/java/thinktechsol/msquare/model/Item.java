package thinktechsol.msquare.model;

/**
 * Created by Arshad.Iqbal on 5/16/2016.
 */
public class Item {
    public String label;
    public int counter, counter3, counter4, counter5;
    public int icon;
    public int bgColor;
    public int subItemIcon1;
    public int subItemIcon2;
    public int subItemIcon3;
    public int subItemIcon4;
    public boolean FourStats;

    public Item(String label, int counter, int icon, int bgColor, int subItemIcon1, int subItemIcon2) {
        this.label = label;
        this.counter = counter;
        this.icon = icon;
        this.bgColor = bgColor;
        this.subItemIcon1 = subItemIcon1;
        this.subItemIcon2 = subItemIcon2;
    }

    public Item(String label, int counter, int icon, int bgColor, int subItemIcon1, int subItemIcon2, int subItemIcon3, int subItemIcon4) {
        this.label = label;
        this.counter = counter;
        this.icon = icon;
        this.bgColor = bgColor;
        this.subItemIcon1 = subItemIcon1;
        this.subItemIcon2 = subItemIcon2;
        this.subItemIcon3 = subItemIcon3;
        this.subItemIcon4 = subItemIcon4;
    }

    public Item(String label, int counter, int counter3, int counter4, int counter5, int icon, int bgColor, int subItemIcon1, int subItemIcon2, boolean FourStats) {
        this.label = label;
        this.counter = counter;
        this.counter3 = counter3;
        this.counter4 = counter4;
        this.counter5 = counter5;
        this.icon = icon;
        this.bgColor = bgColor;
        this.subItemIcon1 = subItemIcon1;
        this.subItemIcon2 = subItemIcon2;
        this.FourStats = FourStats;
    }

    public Item(String label, int counter, int counter3, int counter4, int counter5, int icon, int bgColor, int subItemIcon1, int subItemIcon2, int subItemIcon3, int subItemIcon4) {
        this.label = label;
        this.counter = counter;
        this.counter3 = counter3;
        this.counter4 = counter4;
        this.counter5 = counter5;
        this.icon = icon;
        this.bgColor = bgColor;
        this.subItemIcon1 = subItemIcon1;
        this.subItemIcon2 = subItemIcon2;
        this.subItemIcon3 = subItemIcon3;
        this.subItemIcon4 = subItemIcon4;
    }
}

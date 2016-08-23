package thinktechsol.msquare.model.Buyer;

/**
 * Created by Arshad.Iqbal on 5/16/2016.
 */
public class BuyerDashBoardAdapterItem {
    public String label;
    public int counter;
    public int icon;
    public int bgColor;
    public int subItemIcon1;
    public int subItemIcon2;
    public int subItemIcon3;
    public int subItemIcon4;

    public BuyerDashBoardAdapterItem(String label, int counter, int icon, int bgColor, int subItemIcon1, int subItemIcon2) {
        this.label = label;
        this.counter = counter;
        this.icon = icon;
        this.bgColor = bgColor;
        this.subItemIcon1 = subItemIcon1;
        this.subItemIcon2 = subItemIcon2;
    }

    public BuyerDashBoardAdapterItem(String label, int counter, int icon, int bgColor, int subItemIcon1, int subItemIcon2, int subItemIcon3, int subItemIcon4) {
        this.label = label;
        this.counter = counter;
        this.icon = icon;
        this.bgColor = bgColor;
        this.subItemIcon1 = subItemIcon1;
        this.subItemIcon2 = subItemIcon2;
        this.subItemIcon3 = subItemIcon3;
        this.subItemIcon4 = subItemIcon4;
    }
}

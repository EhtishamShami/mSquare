package thinktechsol.msquare.model.Buyer;

/**
 * Created by LENOVO on 7/19/2016.
 */

public class TimeListItemModel {
    public String id;
    public String time;
    public boolean selected;

    public TimeListItemModel(String id, String time, boolean selected) {
        this.id = id;
        this.time = time;
        this.selected = selected;
    }
}

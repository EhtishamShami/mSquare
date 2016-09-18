package thinktechsol.msquare.model.Buyer;

/**
 * Created by Arshad.Iqbal on 5/16/2016.
 */
public class HomeItem {

    public String id;
    public String status;
    public String description;
    public String name;
    public String parent;
    public String thumb;
    public String categoryType;
    public int bgColor;


    public String id2;
    public String status2;
    public String description2;
    public String name2;
    public String parent2;
    public String thumb2;
    public String categoryType2;
    public int bgColor2;

    public int width;

    public HomeItem() {
    }

    public HomeItem(String id, String status, String description, String name, String parent, String thumb, String categoryType, int bgColor,
                    String id2, String status2, String description2, String name2, String parent2, String thumb2, String categoryType2, int bgColor2, int width) {
        this.id = id;
        this.status = status;
        this.description = description;
        this.name = name;
        this.parent = parent;
        this.thumb = thumb;
        this.categoryType = categoryType;
        this.bgColor = bgColor;
        this.id2 = id2;
        this.status2 = status2;
        this.description2 = description2;
        this.name2 = name2;
        this.parent2 = parent2;
        this.thumb2 = thumb2;
        this.categoryType2 = categoryType2;
        this.bgColor2 = bgColor2;
        this.width = width;
    }


    public void clearObject() {
        this.id = "";
        this.status = "";
        this.description = "";
        this.name = "";
        this.parent = "";
        this.thumb = "empty";
        this.categoryType = "";
        this.bgColor = 0;
        this.id2 = "";
        this.status2 = "";
        this.description2 = "";
        this.name2 = "";
        this.parent2 = "";
        this.thumb2 = "empty";
        this.categoryType2 = "";
        this.bgColor2 = 0;
        this.width = 0;
    }
}

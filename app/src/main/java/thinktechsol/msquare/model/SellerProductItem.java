package thinktechsol.msquare.model;

/**
 * Created by Arshad Iqbal on 07/06/2016.
 */

public class SellerProductItem {
    public String id;
    public String status;
    public String description;
    public String name;
    public String parent;
    public String thumb;

    public SellerProductItem(String id, String status, String description, String name, String parent, String thumb) {
        this.id = id;
        this.status = status;
        this.description = description;
        this.name = name;
        this.parent = parent;
        this.thumb = thumb;
    }
}

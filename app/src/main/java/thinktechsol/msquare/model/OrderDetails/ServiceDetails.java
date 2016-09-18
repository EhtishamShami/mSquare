package thinktechsol.msquare.model.OrderDetails;

/**
 * Created by  Arshad Iqbal on 8/15/2016.
 */

public class ServiceDetails {
    public String id;
    public String status;
    public String description;
    public String categoryType;
    public String name;
    public String parent;
    public String thumb;

    public ServiceDetails(String id, String status, String description, String categoryType, String name, String parent, String thumb) {
        this.id = id;
        this.status = status;
        this.description = description;
        this.categoryType = categoryType;
        this.name = name;
        this.parent = parent;
        this.thumb = thumb;
    }
}

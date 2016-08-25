package thinktechsol.msquare.model.Buyer;

/**
 * Created by Arshad Iqbal on 16/06/2016.
 */

public class ServiceDetailsWL {
    public String id;
    public String parent;
    public String name;
    public String description;
    public String status;
    public String thumb;
    public String categoryType;

    public ServiceDetailsWL(String id, String parent, String name, String description, String status, String thumb, String categoryType) {
        this.id = id;
        this.parent = parent;
        this.name = name;
        this.description = description;
        this.status = status;
        this.thumb = thumb;
        this.categoryType = categoryType;
    }
}

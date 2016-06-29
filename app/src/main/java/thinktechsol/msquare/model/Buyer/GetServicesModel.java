package thinktechsol.msquare.model.Buyer;

/**
 * Created by Arshad Iqbal on 28/06/2016.
 */

public class GetServicesModel {
    public String id;
    public String status;
    public String description;
    public String name;
    public String parent;
    public String thumb;
    public String categoryType;

    public GetServicesModel(String id, String status, String description, String name, String parent, String thumb, String categoryType) {
        this.id = id;
        this.status = status;
        this.description = description;
        this.name = name;
        this.parent = parent;
        this.thumb = thumb;
        this.categoryType = categoryType;
    }
}

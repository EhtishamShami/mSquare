package thinktechsol.msquare.model.Buyer;

/**
 * Created by arshadiqbal on 09/09/16.
 */
public class FiltersModel {
    public String distance;
    public String priceFrom;
    public String priceTo;
    public String fromTime;
    public String toTime;
    public String categories;

    public FiltersModel(String distance, String priceFrom, String priceTo, String fromTime, String toTime, String categories) {
        this.distance = distance;
        this.priceFrom = priceFrom;
        this.priceTo = priceTo;
        this.fromTime = fromTime;
        this.toTime = toTime;
        this.categories = categories;
    }
}

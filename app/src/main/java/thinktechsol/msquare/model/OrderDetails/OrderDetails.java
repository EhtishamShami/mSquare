package thinktechsol.msquare.model.OrderDetails;

/**
 * Created by Arshad Iqbal on 8/15/2016.
 */

public class OrderDetails {
    public ServiceDetails serviceDetails;
    public ProductDetails productDetails;
    public String quantity;

    public OrderDetails(ServiceDetails serviceDetails, ProductDetails productDetails, String quantity) {
        this.serviceDetails = serviceDetails;
        this.productDetails = productDetails;
        this.quantity = quantity;
    }
}

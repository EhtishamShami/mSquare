package thinktechsol.msquare.model.OrderDetails;

/**
 * Created by Arshad Iqbal on 8/15/2016.
 */

public class Messages {
    public String messageBody;
    public String sender;
    public String id;
    public String dated;
    public String orderId;

    public Messages(String messageBody, String sender, String id, String dated, String orderId) {
        this.messageBody = messageBody;
        this.sender = sender;
        this.id = id;
        this.dated = dated;
        this.orderId = orderId;
    }
}

package thinktechsol.msquare.model;

/**
 * Created by Arshad.Iqbal on 7/26/2016.
 */
public class getConversationListSellerResModel {
    public String messageBody;
    public String sender;
    public String id;
    public String dated;
    public String lName;
    public String thumb;
    public String orderId;
    public String fName;

    public getConversationListSellerResModel(String messageBody, String sender, String id, String dated, String lName, String thumb, String orderId, String fName) {
        this.messageBody = messageBody;
        this.sender = sender;
        this.id = id;
        this.dated = dated;
        this.lName = lName;
        this.thumb = thumb;
        this.orderId = orderId;
        this.fName = fName;
    }
}

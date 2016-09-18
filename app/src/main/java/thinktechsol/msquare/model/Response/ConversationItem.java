package thinktechsol.msquare.model.Response;

/**
 * Created by Arshad.Iqbal on 12/8/2016.
 */
public class ConversationItem {

    public String imgUrl;
    public String name;
    public String time;
    public String message;

    public ConversationItem(String imgUrl, String name, String time, String message) {
        this.imgUrl = imgUrl;
        this.name = name;
        this.time = time;
        this.message = message;
    }
}

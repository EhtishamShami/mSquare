package thinktechsol.msquare.model.Buyer;

/**
 * Created by Arshad Iqbal on 16/06/2016.
 */

public class BuyerOrderStatesModel {
    public String recent;
    public String inProcess;
    public String reject;
    public String complete;
    public String dispute;

    public BuyerOrderStatesModel(String recent, String inProcess, String reject, String complete, String dispute) {
        this.recent = recent;
        this.inProcess = inProcess;
        this.reject = reject;
        this.complete = complete;
        this.dispute = dispute;
    }
}

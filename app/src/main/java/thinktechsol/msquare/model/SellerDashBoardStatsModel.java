package thinktechsol.msquare.model;

/**
 * Created by arshadiqbal on 07/09/16.
 */
public class SellerDashBoardStatsModel {
    public String unReadMessages;
    public String customers;
    public String staff;

    public String Orecent;
    public String OinProcess;
    public String Oreject;
    public String Ocomplete;
    public String Odispute;

    public String Ppending;
    public String Penable;
    public String Pdisable;
    public String Pblocked;

    public SellerDashBoardStatsModel(String unReadMessages, String customers, String staff, String orecent, String oinProcess, String oreject, String ocomplete, String odispute, String ppending, String penable, String pdisable, String pblocked) {
        this.unReadMessages = unReadMessages;
        this.customers = customers;
        this.staff = staff;
        Orecent = orecent;
        OinProcess = oinProcess;
        Oreject = oreject;
        Ocomplete = ocomplete;
        Odispute = odispute;
        Ppending = ppending;
        Penable = penable;
        Pdisable = pdisable;
        Pblocked = pblocked;
    }
}

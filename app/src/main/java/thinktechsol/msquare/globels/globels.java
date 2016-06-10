package thinktechsol.msquare.globels;

/**
 * Created by Arshad Iqbal on 07/06/2016.
 */

import thinktechsol.msquare.model.SellerLogInResponse;
import thinktechsol.msquare.services.sellerLogIn;


public class globels {
    public static globels globelObject = new globels();

    public static globels getGlobelRef() {
        return globelObject;
    }

    public SellerLogInResponse sellerlogin;

}

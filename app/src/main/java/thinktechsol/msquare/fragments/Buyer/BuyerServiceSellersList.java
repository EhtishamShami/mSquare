package thinktechsol.msquare.fragments.Buyer;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;

import java.util.ArrayList;

import thinktechsol.msquare.R;
import thinktechsol.msquare.adapter.BuyerServiceSellersListAdapter;
import thinktechsol.msquare.globels.globels;
import thinktechsol.msquare.interfaceMine.SearchViewInterface;
import thinktechsol.msquare.model.Buyer.getServiceSellersModel;
import thinktechsol.msquare.utils.Constant;

public class BuyerServiceSellersList extends Fragment implements SearchViewInterface {

    ListView listView;
    BuyerServiceSellersListAdapter adapter;
    ArrayList<getServiceSellersModel> productList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_buyer_service_seller_list, container, false);

//        new getServiceSellers(getActivity(),BuyerServiceSellersList.this,Constant.sellerServiceId,"24.433904943494827","54.41303014755249");

        listView = (ListView) v.findViewById(R.id.list);
        if(globels.getGlobelRef().SellersProductDetailList!=null) {
            adapter = new BuyerServiceSellersListAdapter(getActivity(), R.layout.buyer_service_seller_list_item, globels.getGlobelRef().SellersProductDetailList);
            listView.setAdapter(adapter);
        }

        return v;
    }

    public void fillProductListWithData(ArrayList<getServiceSellersModel> list){
        productList = new ArrayList<getServiceSellersModel>();

        Log.e("BuyerServiceSellersList","list size is="+list.size());
        for (int i = 0; i < list.size(); i++) {
//            m_parts.add(new AddProductItem(list.get(i).id, list.get(i).name, Constant.imgbaseUrl + list.get(i).thumb, colorCode[colorId]));
            productList.add(new getServiceSellersModel(list.get(i).mobileNo, list.get(i).logo, list.get(i).status, list.get(i).tradeNo, list.get(i).documents, list.get(i).lName, list.get(i).companyName, list.get(i).password, list.get(i).fName, list.get(i).productRating, list.get(i).id, list.get(i).phoneNo, list.get(i).distance, list.get(i).email, list.get(i).address, list.get(i).description, list.get(i).activationCode, list.get(i).service, list.get(i).longitude, list.get(i).latitude, list.get(i).datetime));
        }

        adapter = new BuyerServiceSellersListAdapter(getActivity(), R.layout.buyer_service_seller_list_item, productList);
        listView.setAdapter(adapter);
    }
    public RelativeLayout.LayoutParams AppLayoutParam(float height, float width, float mL, float mT, float mR, float mB, View below) {
        RelativeLayout.LayoutParams paramName = new RelativeLayout.LayoutParams(
                getSize("w", width),
                getSize("h", height));
        paramName.addRule(RelativeLayout.CENTER_HORIZONTAL, RelativeLayout.TRUE);
        if (below != null)
            paramName.addRule(RelativeLayout.BELOW, below.getId());
        paramName.setMargins(getSize("h", mL), getSize("h", mT), getSize("h", mR), getSize("h", mB));
        return paramName;
    }

    public LinearLayout.LayoutParams AppLayoutParamLinearLayout(float height, float width, float mL, float mT, float mR, float mB) {
        LinearLayout.LayoutParams paramName = new LinearLayout.LayoutParams(
                getSize("w", width),
                getSize("h", height));
//        paramName.addRule(RelativeLayout.CENTER_HORIZONTAL, RelativeLayout.TRUE);
//        if (below != null)
//            paramName.addRule(RelativeLayout.BELOW, below.getId());
        paramName.setMargins(getSize("h", mL), getSize("h", mT), getSize("h", mR), getSize("h", mB));
        return paramName;
    }

    public int getSize(String dimension, float size) {
        float x = 0;
        if (dimension.equals("w")) {
            x = (size / 100) * Constant.screenWidth;
        } else {
            x = (size / 100) * Constant.screenHeight;
        }
        return (int) x;
    }

    @Override
    public void refersh(ArrayList<getServiceSellersModel> list) {
        if(list!=null) {
            adapter = new BuyerServiceSellersListAdapter(getActivity(), R.layout.buyer_service_seller_list_item, list);
            listView.setAdapter(adapter);
        }
    }
}

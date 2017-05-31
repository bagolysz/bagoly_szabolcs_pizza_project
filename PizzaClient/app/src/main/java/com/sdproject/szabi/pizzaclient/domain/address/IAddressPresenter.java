package com.sdproject.szabi.pizzaclient.domain.address;

import com.sdproject.szabi.pizzaclient.datamodel.DeliveryAddress;

/**
 * Created by Szabi on 5/24/2017.
 */

public interface IAddressPresenter {

    void populateAddressList();

    void addNewAddress(String city, String street, String number, String phone);

    void deleteItemFromList(DeliveryAddress item);

    void onItemSelected(DeliveryAddress current, int mode);
}

package com.sdproject.szabi.pizzaclient.ui.address;

import com.sdproject.szabi.pizzaclient.datamodel.DeliveryAddress;

import java.util.List;

/**
 * Created by Szabi on 5/24/2017.
 */

public interface IAddressView {

    void populateAddressList(List<DeliveryAddress> list);

    void finishScreenWithResult(DeliveryAddress current);
}

package com.sdproject.szabi.pizzaclient.ui.orders;

import com.sdproject.szabi.pizzaclient.datamodel.Order;

import java.util.List;

/**
 * Created by Szabi on 5/24/2017.
 */

public interface IOrdersView {
    void populateOrderList(List<Order> list);
}

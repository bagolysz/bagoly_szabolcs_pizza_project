package com.sdproject.szabi.pizzaclient.ui.orders;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.sdproject.szabi.pizzaclient.R;
import com.sdproject.szabi.pizzaclient.datamodel.Order;
import com.sdproject.szabi.pizzaclient.utils.Constants;

import java.text.DateFormat;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Szabi on 5/28/2017.
 */

class OrdersAdapter extends RecyclerView.Adapter<OrdersAdapter.OrdersViewHolder> {


    private List<Order> items;

    OrdersAdapter() {
    }

    public OrdersAdapter(List<Order> items) {
        this.items = items;
    }

    void setItems(List<Order> items) {
        this.items = items;
    }

    @Override
    public OrdersViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new OrdersViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_order, parent, false));
    }

    @Override
    public int getItemCount() {
        return items != null ? items.size() : 0;
    }

    @Override
    public void onBindViewHolder(OrdersViewHolder holder, int position) {
        holder.bind(items.get(position), position);
    }

    static class OrdersViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.item_order_date)
        TextView date;
        @BindView(R.id.item_order_total)
        TextView total;
        @BindView(R.id.item_order_status)
        TextView status;
        @BindView(R.id.item_order_counter)
        TextView counter;
        private View itemView;

        private OrdersViewHolder(View itemView) {
            super(itemView);
            this.itemView = itemView;
            ButterKnife.bind(this, itemView);
        }

        private void bind(final Order current, final int position) {
            counter.setText(String.format(itemView.getContext().getString(R.string.orders_counter), position + 1));
            total.setText(String.format(itemView.getContext().getString(R.string.product_price), current.total));
            status.setText(Constants.getOrderStatus(current.status));
            date.setText(DateFormat.getDateTimeInstance().format(current.datetime));
        }

    }
}

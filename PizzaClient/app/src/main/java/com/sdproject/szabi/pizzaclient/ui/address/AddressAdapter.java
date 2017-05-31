package com.sdproject.szabi.pizzaclient.ui.address;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.sdproject.szabi.pizzaclient.R;
import com.sdproject.szabi.pizzaclient.datamodel.DeliveryAddress;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Szabi on 5/26/2017.
 */

class AddressAdapter extends RecyclerView.Adapter<AddressAdapter.AddressViewHolder> {

    interface OnAddressItemClickListener {
        void onDeleteAddressClick(DeliveryAddress current);
        void onItemClick(DeliveryAddress current);
    }

    private List<DeliveryAddress> items;
    private OnAddressItemClickListener listener;

    AddressAdapter(OnAddressItemClickListener listener) {
        this.listener = listener;
    }

    public AddressAdapter(List<DeliveryAddress> items, OnAddressItemClickListener listener) {
        this.items = items;
        this.listener = listener;
    }

    void setItems(List<DeliveryAddress> items) {
        this.items = items;
    }

    @Override
    public AddressViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new AddressViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_address, parent, false));
    }

    @Override
    public int getItemCount() {
        return items != null ? items.size() : 0;
    }

    @Override
    public void onBindViewHolder(AddressViewHolder holder, int position) {
        holder.bind(items.get(position), position, listener);
    }

    static class AddressViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.item_address_city)
        TextView city;
        @BindView(R.id.item_address_street)
        TextView street;
        @BindView(R.id.item_address_number)
        TextView number;
        @BindView(R.id.item_address_phone)
        TextView phone;
        @BindView(R.id.item_address_delete)
        ImageView delete;

        private View itemView;

        private AddressViewHolder(View itemView) {
            super(itemView);
            this.itemView = itemView;
            ButterKnife.bind(this, itemView);
        }

        private void bind(final DeliveryAddress current, final int position, final OnAddressItemClickListener listener) {
            city.setText(current.city);
            street.setText(current.street);
            number.setText(current.number);
            phone.setText(current.phoneNumber);
            // set an onClickListener for the current view
            // the listener will be handled in the Adapters Activity
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onItemClick(current);
                }
            });

            delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onDeleteAddressClick(current);
                }
            });
        }

    }
}

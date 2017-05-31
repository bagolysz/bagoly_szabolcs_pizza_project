package com.sdproject.szabi.pizzaclient.ui.shoppingcart;

import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.sdproject.szabi.pizzaclient.R;
import com.sdproject.szabi.pizzaclient.datamodel.ShoppingCartItem;

import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Szabi on 5/23/2017.
 */

class CartAdapter extends RecyclerView.Adapter<CartAdapter.CartViewHolder> {

    interface OnCartItemClickListener {
        void onDeleteItemClick(ShoppingCartItem item);

        void onQuantityUpClick(ShoppingCartItem item);

        void onQuantityDownClick(ShoppingCartItem item);
    }

    private List<ShoppingCartItem> items;
    private OnCartItemClickListener listener;


    CartAdapter(OnCartItemClickListener listener) {
        this.listener = listener;
    }

    public CartAdapter(List<ShoppingCartItem> items, OnCartItemClickListener listener) {
        this.items = items;
        this.listener = listener;
    }

    void setItems(List<ShoppingCartItem> items) {
        this.items = items;
    }

    @Override
    public CartViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new CartViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_cart, parent, false));
    }

    @Override
    public int getItemCount() {
        return items != null ? items.size() : 0;
    }

    @Override
    public void onBindViewHolder(CartViewHolder holder, int position) {
        holder.bind(items.get(position), listener);
    }

    static class CartViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.cart_image)
        ImageView productImage;
        @BindView(R.id.cart_title)
        TextView productTitle;
        @BindView(R.id.cart_price)
        TextView productPrice;
        @BindView(R.id.cart_quantity)
        TextView productQuantity;
        @BindView(R.id.cart_delete)
        ImageView delete;
        @BindView(R.id.cart_quantity_up)
        ImageView up;
        @BindView(R.id.cart_quantity_down)
        ImageView down;

        private View itemView;

        private CartViewHolder(View itemView) {
            super(itemView);
            this.itemView = itemView;
            ButterKnife.bind(this, itemView);
        }

        private void bind(final ShoppingCartItem current, final OnCartItemClickListener listener) {
            // get the corresponding string resource and attach the corresponding value to it
            productPrice.setText(String.format(itemView.getContext().getString(R.string.product_price), current.product.price));
            productTitle.setText(current.product.title);
            productQuantity.setText(String.format(Locale.ENGLISH, "%d", current.quantity));

            Glide.with(itemView.getContext())
                    .load(current.product.imageLocation)
                    .error(ContextCompat.getDrawable(itemView.getContext(), R.drawable.icon_food))
                    .centerCrop()
                    .into(productImage);

            // set an onClickListener for the current view
            // the listener will be handled in the Adapters Activity
            delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onDeleteItemClick(current);
                }
            });
            down.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onQuantityDownClick(current);
                }
            });
            up.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onQuantityUpClick(current);
                }
            });
        }

    }
}
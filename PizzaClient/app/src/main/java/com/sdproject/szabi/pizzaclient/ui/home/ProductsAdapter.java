package com.sdproject.szabi.pizzaclient.ui.home;

import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.sdproject.szabi.pizzaclient.R;
import com.sdproject.szabi.pizzaclient.datamodel.products.Product;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Szabi on 5/21/2017.
 */

class ProductsAdapter extends RecyclerView.Adapter<ProductsAdapter.OffersViewHolder> {

    interface OnProductItemClickListener {
        void onProductItemClick(Product item);
    }

    private List<Product> items;
    private OnProductItemClickListener listener;


    ProductsAdapter(OnProductItemClickListener listener) {
        this.listener = listener;
    }

    public ProductsAdapter(List<Product> items, OnProductItemClickListener listener) {
        this.items = items;
        this.listener = listener;
    }

    void setItems(List<Product> items) {
        this.items = items;
    }

    @Override
    public OffersViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new OffersViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_product, parent, false));
    }

    @Override
    public int getItemCount() {
        return items != null ? items.size() : 0;
    }

    @Override
    public void onBindViewHolder(OffersViewHolder holder, int position) {
        holder.bind(items.get(position), listener);
    }

    static class OffersViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.product_image)
        ImageView productImage;
        @BindView(R.id.product_title)
        TextView productTitle;
        @BindView(R.id.product_price)
        TextView productPrice;

        private View itemView;

        private OffersViewHolder(View itemView) {
            super(itemView);
            this.itemView = itemView;
            ButterKnife.bind(this, itemView);
        }

        private void bind(final Product current, final OnProductItemClickListener listener) {
            // get the corresponding string resource and attach the corresponding value to it
            productPrice.setText(String.format(itemView.getContext().getString(R.string.product_price), current.price));
            productTitle.setText(current.title);

            Glide.with(itemView.getContext())
                    .load(current.imageLocation)
                    .error(ContextCompat.getDrawable(itemView.getContext(), R.drawable.icon_food))
                    .centerCrop()
                    .into(productImage);

            // set an onClickListener for the current view
            // the listener will be handled in the Adapters Activity
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onProductItemClick(current);
                }
            });
        }

    }
}

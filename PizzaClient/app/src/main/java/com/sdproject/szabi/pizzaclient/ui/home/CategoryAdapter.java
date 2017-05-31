package com.sdproject.szabi.pizzaclient.ui.home;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.sdproject.szabi.pizzaclient.R;
import com.sdproject.szabi.pizzaclient.datamodel.Category;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Szabi on 5/21/2017.
 */

class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder> {

    interface OnCategoryItemClickListener {
        void onCategoryItemClick(Category productCategory);
    }

    private List<Category> items;
    private OnCategoryItemClickListener listener;

    CategoryAdapter(OnCategoryItemClickListener listener) {
        this.listener = listener;
    }

    public CategoryAdapter(List<Category> items, OnCategoryItemClickListener listener) {
        this.items = items;
        this.listener = listener;
    }

    void setItems(List<Category> items) {
        this.items = items;
    }

    @Override
    public CategoryViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new CategoryViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_category, parent, false));
    }

    @Override
    public int getItemCount() {
        return items != null ? items.size() : 0;
    }

    @Override
    public void onBindViewHolder(CategoryViewHolder holder, int position) {
        holder.bind(items.get(position), position, listener);
    }

    static class CategoryViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.category_item_title)
        TextView title;
        private View itemView;

        private CategoryViewHolder(View itemView) {
            super(itemView);
            this.itemView = itemView;
            ButterKnife.bind(this, itemView);
        }

        private void bind(final Category current, final int position, final OnCategoryItemClickListener listener) {
            title.setText(current.title);

            // set an onClickListener for the current view
            // the listener will be handled in the Adapters Activity
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onCategoryItemClick(current);
                }
            });
        }

    }
}

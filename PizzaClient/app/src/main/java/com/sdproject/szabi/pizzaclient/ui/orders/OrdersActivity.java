package com.sdproject.szabi.pizzaclient.ui.orders;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.sdproject.szabi.pizzaclient.R;
import com.sdproject.szabi.pizzaclient.datamodel.Order;
import com.sdproject.szabi.pizzaclient.domain.orders.OrdersPresenter;
import com.sdproject.szabi.pizzaclient.ui.DividerItemDecoration;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Szabi on 5/24/2017.
 */

public class OrdersActivity extends AppCompatActivity implements IOrdersView {

    @BindView(R.id.orders_toolbar)
    Toolbar toolbar;
    @BindView(R.id.orders_list)
    RecyclerView list;

    private OrdersAdapter adapter;
    private OrdersPresenter presenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_orders);
        ButterKnife.bind(this);

        setupToolbar();
        setupRecyclerView();

        presenter = new OrdersPresenter(this);
        presenter.retrieveOrders();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                finish();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void setupToolbar(){
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(getString(R.string.orders_title));
    }

    private void setupRecyclerView(){
        adapter = new OrdersAdapter();
        list.setAdapter(adapter);
        list.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        list.setItemAnimator(new DefaultItemAnimator());
        list.addItemDecoration(new DividerItemDecoration(this, true));
    }

    @Override
    public void populateOrderList(List<Order> list) {
        adapter.setItems(list);
        adapter.notifyDataSetChanged();
    }
}

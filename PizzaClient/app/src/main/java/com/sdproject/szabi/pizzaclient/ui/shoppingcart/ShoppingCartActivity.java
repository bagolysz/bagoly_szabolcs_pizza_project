package com.sdproject.szabi.pizzaclient.ui.shoppingcart;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.widget.TextView;

import com.sdproject.szabi.pizzaclient.R;
import com.sdproject.szabi.pizzaclient.datamodel.DeliveryAddress;
import com.sdproject.szabi.pizzaclient.datamodel.ShoppingCartItem;
import com.sdproject.szabi.pizzaclient.domain.shoppingcart.IShoppingCartPresenter;
import com.sdproject.szabi.pizzaclient.domain.shoppingcart.ShoppingCartPresenter;
import com.sdproject.szabi.pizzaclient.ui.OrderUpdateService;
import com.sdproject.szabi.pizzaclient.ui.address.AddressActivity;
import com.sdproject.szabi.pizzaclient.utils.Constants;

import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Szabi on 5/23/2017.
 */

public class ShoppingCartActivity extends AppCompatActivity implements IShoppingCartView,
        CartAdapter.OnCartItemClickListener {

    private static final int ADDRESS_REQUEST = 5;

    @BindView(R.id.cart_base_layout)
    CoordinatorLayout baseLayout;
    @BindView(R.id.cart_toolbar)
    Toolbar toolbar;
    @BindView(R.id.cart_total)
    TextView totalText;
    @BindView(R.id.cart_product_list)
    RecyclerView cartContainer;
    @BindView(R.id.cart_address_city)
    TextView city;
    @BindView(R.id.cart_address_street)
    TextView street;
    @BindView(R.id.cart_address_number)
    TextView number;
    @BindView(R.id.cart_address_phone)
    TextView phone;

    private CartAdapter cartAdapter;
    private IShoppingCartPresenter presenter;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopping_cart);
        ButterKnife.bind(this);

        setupToolbar();
        setupCartRecyclerView();

        presenter = new ShoppingCartPresenter(this);
        presenter.retrieveCartItems();
        presenter.setTotal();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == ADDRESS_REQUEST) {
            if (resultCode == Activity.RESULT_OK) {
                DeliveryAddress address = data.getParcelableExtra("RESULT");
                presenter.onAddressSelected(address);
            }
        }
    }

    @OnClick(R.id.cart_fab)
    void onCartCompletedClick() {
        presenter.onCartCompleted();
    }

    @OnClick(R.id.cart_address_text)
    void onAddressTextClick() {
        presenter.addressModification();
    }

    private void setupToolbar() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(getResources().getString(R.string.cart_toolbar_title));
    }

    private void setupCartRecyclerView() {
        cartAdapter = new CartAdapter(this);
        cartContainer.setAdapter(cartAdapter);
        cartContainer.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        cartContainer.setItemAnimator(new DefaultItemAnimator());
    }

    // SECTION implement OnCartItemClickListener interface methods

    @Override
    public void onDeleteItemClick(ShoppingCartItem item) {
        presenter.deleteItemFromCart(item);
        presenter.retrieveCartItems();
    }

    @Override
    public void onQuantityUpClick(ShoppingCartItem item) {
        presenter.quantityUp(item);
        presenter.retrieveCartItems();
    }

    @Override
    public void onQuantityDownClick(ShoppingCartItem item) {
        presenter.quantityDown(item);
        presenter.retrieveCartItems();
    }

    // END SECTION

    // SECTION IView interface implementation

    @Override
    public void populateCartList(List<ShoppingCartItem> products) {
        cartAdapter.setItems(products);
        cartAdapter.notifyDataSetChanged();
    }

    @Override
    public void setTotal(double total) {
        totalText.setText(String.format(Locale.ENGLISH, "%.2f", total));
    }

    @Override
    public void startDeliverAddressSelection() {
        startActivityForResult(AddressActivity.getActivityIntent(this, Constants.MODE_SELECT), ADDRESS_REQUEST);
    }

    @Override
    public void finishOrder() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Confirm order");
        builder.setMessage("Do you want to place the current order?");
        builder.setCancelable(true);

        builder.setPositiveButton(
                "Yes",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        presenter.confirmOrder();
                    }
                });

        builder.setNegativeButton(
                "No",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });

        builder.create().show();
    }

    @Override
    public void showAddress(DeliveryAddress address) {
        city.setText(address.city);
        street.setText(address.street);
        number.setText(address.number);
        phone.setText(address.phoneNumber);
    }

    @Override
    public void showMessage(String msg) {
        showSnackBar(msg);
    }

    @Override
    public void closeView() {
        finish();
    }

    @Override
    public void startListeningForOrderStatusChange(String id) {
        Intent intent = new Intent(getBaseContext(), OrderUpdateService.class);
        intent.putExtra("orderId", id);
        startService(intent);
    }

    // END SECTION

    private void showSnackBar(String msg) {
        Snackbar.make(baseLayout, msg, Snackbar.LENGTH_SHORT).show();
    }
}

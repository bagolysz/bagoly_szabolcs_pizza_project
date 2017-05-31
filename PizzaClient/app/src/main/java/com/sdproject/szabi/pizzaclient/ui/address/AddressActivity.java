package com.sdproject.szabi.pizzaclient.ui.address;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;

import com.sdproject.szabi.pizzaclient.R;
import com.sdproject.szabi.pizzaclient.datamodel.DeliveryAddress;
import com.sdproject.szabi.pizzaclient.domain.address.AddressPresenter;
import com.sdproject.szabi.pizzaclient.domain.address.IAddressPresenter;
import com.sdproject.szabi.pizzaclient.ui.DividerItemDecoration;
import com.sdproject.szabi.pizzaclient.utils.Constants;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Szabi on 5/24/2017.
 */

public class AddressActivity extends AppCompatActivity implements IAddressView, AddressAdapter.OnAddressItemClickListener {

    @BindView(R.id.address_base_layout)
    CoordinatorLayout baseLayout;
    @BindView(R.id.address_toolbar)
    Toolbar toolbar;
    @BindView(R.id.address_city)
    AppCompatEditText city;
    @BindView(R.id.address_street)
    AppCompatEditText street;
    @BindView(R.id.address_number)
    AppCompatEditText number;
    @BindView(R.id.address_phone)
    AppCompatEditText phone;
    @BindView(R.id.address_list)
    RecyclerView list;

    private IAddressPresenter presenter;
    private AddressAdapter adapter;
    private int mode;

    private static String MODE_CHOICE = "modeChoice";

    public static Intent getActivityIntent(Activity activity, int mode) {
        Intent intent = new Intent(activity, AddressActivity.class);
        intent.putExtra(MODE_CHOICE, mode);
        return intent;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_address);
        ButterKnife.bind(this);

        mode = getIntent().getIntExtra(MODE_CHOICE, Constants.MODE_VIEW);

        setupToolbar();
        setupRecyclerView();
        hideKeyboard();
        presenter = new AddressPresenter(this);
        presenter.populateAddressList();
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

    private void setupToolbar() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(mode == Constants.MODE_VIEW ? R.string.address_title : R.string.address_select);
    }

    private void setupRecyclerView() {
        adapter = new AddressAdapter(this);
        list.setAdapter(adapter);
        list.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        list.setItemAnimator(new DefaultItemAnimator());
        list.addItemDecoration(new DividerItemDecoration(this, true));
    }

    @OnClick(R.id.address_fab)
    void onAddressFabClick() {
        if (city.getText().toString().equals("") ||
                street.getText().toString().equals("") ||
                number.getText().toString().equals("") ||
                phone.getText().toString().equals("")) {
            showSnackBar("All fields all obligatory!");
        } else {
            presenter.addNewAddress(city.getText().toString(), street.getText().toString(), number.getText().toString(), phone.getText().toString());
            clearFields();
            hideKeyboard();
        }
    }

    // SECTION IAddressView implementation

    @Override
    public void populateAddressList(List<DeliveryAddress> list) {
        adapter.setItems(list);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void finishScreenWithResult(DeliveryAddress current) {
        Intent returnIntent = new Intent();
        returnIntent.putExtra("RESULT", current);
        setResult(Activity.RESULT_OK, returnIntent);
        finish();
    }

    // END SECTION

    // SECTION OnAddressItemClickListener interface implementation

    @Override
    public void onDeleteAddressClick(DeliveryAddress current) {
        presenter.deleteItemFromList(current);
    }

    @Override
    public void onItemClick(DeliveryAddress current) {
        presenter.onItemSelected(current, mode);
    }

    // END SECTION

    private void hideKeyboard() {
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    private void showSnackBar(String msg) {
        Snackbar.make(baseLayout, msg, Snackbar.LENGTH_SHORT).show();
    }

    private void clearFields() {
        city.setText("");
        street.setText("");
        number.setText("");
        phone.setText("");
    }
}

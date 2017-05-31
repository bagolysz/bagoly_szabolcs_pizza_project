package com.sdproject.szabi.pizzaclient.ui.home;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.sdproject.szabi.pizzaclient.R;
import com.sdproject.szabi.pizzaclient.datamodel.Category;
import com.sdproject.szabi.pizzaclient.datamodel.User;
import com.sdproject.szabi.pizzaclient.datamodel.products.Product;
import com.sdproject.szabi.pizzaclient.domain.home.HomePresenter;
import com.sdproject.szabi.pizzaclient.domain.home.IHomePresenter;
import com.sdproject.szabi.pizzaclient.ui.OrderUpdateService;
import com.sdproject.szabi.pizzaclient.ui.address.AddressActivity;
import com.sdproject.szabi.pizzaclient.ui.detailedprod.ProductActivity;
import com.sdproject.szabi.pizzaclient.ui.orders.OrdersActivity;
import com.sdproject.szabi.pizzaclient.ui.shoppingcart.ShoppingCartActivity;
import com.sdproject.szabi.pizzaclient.utils.Constants;
import com.sdproject.szabi.pizzaclient.utils.PreferencesManager;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class HomeActivity extends AppCompatActivity implements IHomeView,
        ProductsAdapter.OnProductItemClickListener, NavigationView.OnNavigationItemSelectedListener,
        CategoryAdapter.OnCategoryItemClickListener {

    public static final String PRODUCT_NAME = "productName";

    @BindView(R.id.home_toolbar)
    Toolbar toolbar;
    @BindView(R.id.home_base_layout)
    CoordinatorLayout coordinatorLayout;
    @BindView(R.id.offer_list)
    RecyclerView offersView;
    @BindView(R.id.category_list)
    RecyclerView categoryList;
    @BindView(R.id.home_drawer)
    DrawerLayout drawerLayout;
    @BindView(R.id.home_navigation_view)
    NavigationView navigationView;
    private IHomePresenter presenter;
    private ProductsAdapter productsAdapter;
    private CategoryAdapter categoryAdapter;
    private GoogleApiClient googleApiClient;

    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        ButterKnife.bind(this);

        user = PreferencesManager.getInstance(this).getUser();

        setupToolbar();
        setupNavigationView();
        setupOffersRecyclerView();
        setupCategoryRecyclerView();

        presenter = new HomePresenter(this);
        presenter.retrieveCategories();
        presenter.retrieveOffers();


    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        stopService(new Intent(getBaseContext(), OrderUpdateService.class));
    }

    @Override
    protected void onStart() {
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        googleApiClient = new GoogleApiClient.Builder(this)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();
        googleApiClient.connect();
        super.onStart();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_home, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.home_shopping_cart:
                startActivity(new Intent(this, ShoppingCartActivity.class));
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        closeDrawer();

        switch (item.getItemId()) {
            case R.id.nav_menu_orders:
                startActivity(new Intent(this, OrdersActivity.class));
                return true;
            case R.id.nav_menu_delivery:
                startActivity(AddressActivity.getActivityIntent(this, Constants.MODE_VIEW));
                return true;
            case R.id.nav_menu_shopping_cart:
                startActivity(new Intent(this, ShoppingCartActivity.class));
                return true;
            case R.id.nav_menu_logout:
                Auth.GoogleSignInApi.signOut(googleApiClient).setResultCallback(new ResultCallback<Status>() {
                    @Override
                    public void onResult(@NonNull Status status) {

                    }
                });
                presenter.clearUser();
                finish();
        }

        return false;
    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            closeDrawer();
        } else {
            moveTaskToBack(true);
        }
    }

    private void setupToolbar() {
        setSupportActionBar(toolbar);
    }

    private void setupNavigationView() {
        ActionBarDrawerToggle drawerToggle = new ActionBarDrawerToggle(
                this,
                drawerLayout,
                toolbar,
                R.string.drawer_open,
                R.string.drawer_close
        );

        drawerLayout.addDrawerListener(drawerToggle);
        drawerToggle.syncState();

        navigationView.setNavigationItemSelectedListener(this);

        View navHeader = navigationView.getHeaderView(0);
        ((TextView) navHeader.findViewById(R.id.nav_header_name)).setText(user.name);
        ((TextView) navHeader.findViewById(R.id.nav_header_email)).setText(user.email);
        // TODO: update name and email fields
    }

    private void setupOffersRecyclerView() {
        productsAdapter = new ProductsAdapter(this);
        offersView.setAdapter(productsAdapter);
        offersView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        offersView.setItemAnimator(new DefaultItemAnimator());
    }

    private void setupCategoryRecyclerView() {
        categoryAdapter = new CategoryAdapter(this);
        categoryList.setAdapter(categoryAdapter);
        categoryList.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        categoryList.setItemAnimator(new DefaultItemAnimator());
    }

    private void closeDrawer() {
        drawerLayout.closeDrawer(GravityCompat.START);
    }

    //SECTION OnClickListenerFinished implementations

    @Override
    public void onProductItemClick(Product item) {
        Intent intent = new Intent(this, ProductActivity.class);
        intent.putExtra(PRODUCT_NAME, item);
        startActivity(intent);
    }

    @Override
    public void onCategoryItemClick(Category productCategory) {
        getSupportActionBar().setTitle(productCategory.title);
        presenter.retrieveOffers(productCategory.category);
    }

    @Override
    public void populateProductsList(List<Product> products) {
        productsAdapter.setItems(products);
        productsAdapter.notifyDataSetChanged();
    }

    @Override
    public void populateCategoryList(List<Category> categories) {
        categoryAdapter.setItems(categories);
        categoryAdapter.notifyDataSetChanged();
    }

    //ENDSECTION
}

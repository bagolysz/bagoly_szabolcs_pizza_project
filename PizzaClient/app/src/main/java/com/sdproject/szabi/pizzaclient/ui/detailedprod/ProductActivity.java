package com.sdproject.szabi.pizzaclient.ui.detailedprod;

import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.sdproject.szabi.pizzaclient.R;
import com.sdproject.szabi.pizzaclient.datamodel.products.Product;
import com.sdproject.szabi.pizzaclient.domain.detailedprod.IProductPresenter;
import com.sdproject.szabi.pizzaclient.domain.detailedprod.ProductPresenter;
import com.sdproject.szabi.pizzaclient.ui.home.HomeActivity;
import com.sdproject.szabi.pizzaclient.utils.PreferencesManager;

import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Szabi on 5/22/2017.
 */

public class ProductActivity extends AppCompatActivity implements IProductView {

    @BindView(R.id.detailed_toolbar)
    Toolbar toolbar;
    @BindView(R.id.detailed_collapsing_toolbar)
    CollapsingToolbarLayout collapsingToolbarLayout;
    @BindView(R.id.detailed_collapsing_toolbar_image)
    ImageView collapsingToolbarImage;
    @BindView(R.id.detailed_quantity)
    TextView quantityText;
    @BindView(R.id.detailed_total)
    TextView totalText;
    @BindView(R.id.detailed_ingredients)
    TextView ingredients;

    private IProductPresenter presenter;
    private Product product;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product);
        ButterKnife.bind(this);

        product = getIntent().getParcelableExtra(HomeActivity.PRODUCT_NAME);
        presenter = new ProductPresenter(this, product);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
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

    @OnClick(R.id.detailed_quantity_up)
    void onQuantityUp(){
        presenter.quantityUp();
    }

    @OnClick(R.id.detailed_quantity_down)
    void onQuantityDown(){
        presenter.quantityDown();
    }

    @OnClick(R.id.detailed_fab)
    void onAddProduct(){
        presenter.addProductToShoppingCart();
        finish();
    }

    // SECTION interface implementation

    @Override
    public void updateFields(int quantity, double total){
        quantityText.setText(String.format(Locale.ENGLISH,"%d", quantity));
        totalText.setText(String.format(Locale.ENGLISH, "%.2f", total));
    }

    @Override
    public void setImage(String imageLocation) {
        Glide.with(this)
                .load(imageLocation)
                .centerCrop()
                .into(collapsingToolbarImage);
    }

    @Override
    public void setDescription(String description){
        ingredients.setText(description);
    }

    @Override
    public void setTitle(String title){
        collapsingToolbarLayout.setTitle(title);
    }

    // END SECTION

}

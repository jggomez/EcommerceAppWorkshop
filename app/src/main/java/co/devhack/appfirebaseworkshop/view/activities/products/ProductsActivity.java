package co.devhack.appfirebaseworkshop.view.activities.products;

import android.arch.lifecycle.ViewModelProviders;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;

import butterknife.BindView;
import co.devhack.appfirebaseworkshop.R;
import co.devhack.appfirebaseworkshop.repository.product.ProductsLiveData;
import co.devhack.appfirebaseworkshop.view.activities.adapter.ProductAdapter;
import co.devhack.appfirebaseworkshop.view.activities.base.BaseActivity;
import co.devhack.appfirebaseworkshop.view.viewmodel.ProductsViewModel;

public class ProductsActivity extends BaseActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    @BindView(R.id.drawer_layout)
    DrawerLayout drawer;

    @BindView(R.id.nav_view)
    NavigationView navigationView;

    @BindView(R.id.rvProducts)
    RecyclerView rvProducts;

    private ProductAdapter adapter;

    @Override
    public void initView() {
        super.initView();

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        navigationView.setNavigationItemSelectedListener(this);

        initializeAdaper();
        initializeRecyclerView();

        ProductsViewModel viewModel = ViewModelProviders.of(this).get(ProductsViewModel.class);
        ProductsLiveData productsLiveData = viewModel.getProductsLiveData();

        productsLiveData.observe(this, products -> {
            if (products != null) {
                adapter.addAllItems(products);
                adapter.notifyDataSetChanged();
            }
        });

    }

    private void initializeAdaper() {
        adapter = new ProductAdapter();
    }

    private void initializeRecyclerView() {
        rvProducts.setLayoutManager(
                new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        rvProducts.addItemDecoration(
                new android.support.v7.widget.DividerItemDecoration(this,
                        DividerItemDecoration.VERTICAL));
        rvProducts.setHasFixedSize(true);
        rvProducts.setAdapter(adapter);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_products;
    }

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.products, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}

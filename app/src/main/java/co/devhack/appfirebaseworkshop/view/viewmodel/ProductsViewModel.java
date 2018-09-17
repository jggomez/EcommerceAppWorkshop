package co.devhack.appfirebaseworkshop.view.viewmodel;

import android.arch.lifecycle.ViewModel;

import co.devhack.appfirebaseworkshop.domain.products.GetProducts;
import co.devhack.appfirebaseworkshop.repository.product.FirebaseProductDatasource;
import co.devhack.appfirebaseworkshop.repository.product.ProductRepository;
import co.devhack.appfirebaseworkshop.repository.product.ProductsLiveData;

public class ProductsViewModel extends ViewModel {

    private final GetProducts getProducts = new GetProducts(
            new ProductRepository(
                    new FirebaseProductDatasource()
            )
    );

    public ProductsLiveData getProductsLiveData() {
        return getProducts.getProducts();
    }

}

package co.devhack.appfirebaseworkshop.view.viewmodel;

import android.arch.lifecycle.ViewModel;

import co.devhack.appfirebaseworkshop.domain.model.Product;
import co.devhack.appfirebaseworkshop.domain.products.AddProduct;
import co.devhack.appfirebaseworkshop.repository.product.FirebaseProductDatasource;
import co.devhack.appfirebaseworkshop.repository.product.ProductRepository;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

public class ProductViewModel extends ViewModel {

    private final AddProduct addProduct = new AddProduct(
            Schedulers.io(),
            AndroidSchedulers.mainThread(),
            new ProductRepository(
                    new FirebaseProductDatasource()
            )
    );

    private Product product;

    public void setProduct(Product product) {
        this.product = product;
    }

    public void addProduct(DisposableObserver<String> observer) {
        addProduct.setProduct(this.product);
        addProduct.execute(observer);
    }


}

package co.devhack.appfirebaseworkshop.domain.repository;


import co.devhack.appfirebaseworkshop.domain.model.Product;
import co.devhack.appfirebaseworkshop.repository.product.ProductsLiveData;
import io.reactivex.Observable;

public interface IProductRepository {

    ProductsLiveData getProducts();

    Observable<String> addProduct(Product product);

    Observable<Product> getProductById(String id);

}

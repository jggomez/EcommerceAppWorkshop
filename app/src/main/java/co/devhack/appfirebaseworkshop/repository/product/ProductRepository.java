package co.devhack.appfirebaseworkshop.repository.product;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;

import co.devhack.appfirebaseworkshop.domain.model.Product;
import co.devhack.appfirebaseworkshop.domain.repository.IProductRepository;
import io.reactivex.Observable;

public class ProductRepository implements IProductRepository {

    private final FirebaseProductDatasource firebaseProductDatasource;

    public ProductRepository(FirebaseProductDatasource firebaseProductDatasource) {
        this.firebaseProductDatasource = firebaseProductDatasource;
    }

    @Override
    public ProductsLiveData getProducts() {
        FirebaseFirestore firestore = FirebaseFirestore.getInstance();
        CollectionReference collectionReference = firestore.collection("products");
        return new ProductsLiveData(collectionReference);
    }

    @Override
    public Observable<String> addProduct(Product product) {
        return firebaseProductDatasource.addProduct(product);
    }

    @Override
    public Observable<Product> getProductById(String id) {
        return firebaseProductDatasource.getProductById(id);
    }
}

package co.devhack.appfirebaseworkshop.domain.products;

import co.devhack.appfirebaseworkshop.domain.UseCase;
import co.devhack.appfirebaseworkshop.domain.model.Product;
import co.devhack.appfirebaseworkshop.domain.repository.IProductRepository;
import io.reactivex.Observable;
import io.reactivex.Scheduler;

public class GetProductById extends UseCase<Product> {

    private final IProductRepository productRepository;
    private String idProduct;

    public GetProductById(Scheduler executorThread,
                          Scheduler uiThread,
                          IProductRepository productRepository) {
        super(executorThread, uiThread);
        this.productRepository = productRepository;
    }

    public void setIdProduct(String idProduct) {
        this.idProduct = idProduct;
    }

    @Override
    protected Observable<Product> crearObservableCasoUso() {
        return productRepository.getProductById(this.idProduct);
    }
}

package co.devhack.appfirebaseworkshop.domain.products;

import co.devhack.appfirebaseworkshop.domain.UseCase;
import co.devhack.appfirebaseworkshop.domain.model.Product;
import co.devhack.appfirebaseworkshop.domain.repository.IProductRepository;
import io.reactivex.Observable;
import io.reactivex.Scheduler;

public class AddProduct extends UseCase<String> {

    private final IProductRepository productRepository;
    private Product product;

    public AddProduct(Scheduler executorThread,
                      Scheduler uiThread,
                      IProductRepository productRepository) {
        super(executorThread, uiThread);
        this.productRepository = productRepository;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    @Override
    protected Observable<String> crearObservableCasoUso() {
        return productRepository.addProduct(this.product);
    }
}

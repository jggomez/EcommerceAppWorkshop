package co.devhack.appfirebaseworkshop.domain.products;

import co.devhack.appfirebaseworkshop.domain.repository.IProductRepository;
import co.devhack.appfirebaseworkshop.repository.product.ProductsLiveData;

public class GetProducts {

    private final IProductRepository productRepository;

    public GetProducts(IProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public ProductsLiveData getProducts() {
        return productRepository.getProducts();
    }

}

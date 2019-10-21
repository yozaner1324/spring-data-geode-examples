package examples.springdata.geode.functions.cascading.client.services;

import examples.springdata.geode.domain.Product;
import examples.springdata.geode.functions.cascading.client.repo.ProductRepository;
import org.springframework.stereotype.Service;

@Service
public class ProductService {

    private ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public void save(Product product) {
        productRepository.save(product);
    }

    public Product findById(Long productId) {
        return productRepository.findById(productId).get();
    }
}

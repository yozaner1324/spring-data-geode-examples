package example.springdata.geode.functions.cascading.client.services;

import example.springdata.geode.functions.cascading.client.repo.ProductRepository;
import example.springdata.geode.domain.Product;
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

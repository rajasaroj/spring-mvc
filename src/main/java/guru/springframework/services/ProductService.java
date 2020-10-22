package guru.springframework.services;

import guru.springframework.domain.Product;

import java.util.List;

public interface ProductService {

    public List<Product> getAllProducts();
    public Product getProduct(int id);
    public Product saveOrUpdateProduct(Product product);
    public String deleteProduct(int id);
}

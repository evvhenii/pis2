package dao.product;

import entity.Product;

import java.util.List;

public interface ProductDao {

    void save(Product product);

    List<Product> findAll();

    Product findByCode(String code);
}

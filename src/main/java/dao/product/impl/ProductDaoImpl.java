package dao.product.impl;

import dao.product.ProductDao;
import entity.Product;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ProductDaoImpl implements ProductDao {
    Connection connection;
    static String CREATING_QUERY = "INSERT INTO product (id, name, code, quantity, price) VALUES(?,?,?,?,?);";
    static String FIND_ALL_QUERY = "SELECT * FROM product;";
    static String FIND_BY_CODE_QUERY = "SELECT * FROM product WHERE code =?;";

    @Override
    public void save(Product product) {
        try (PreparedStatement preparedStatement = connection.prepareStatement(CREATING_QUERY)) {
            preparedStatement.setLong(1, product.getId());
            preparedStatement.setString(2, product.getName());
            preparedStatement.setString(3, product.getCode());
            preparedStatement.setInt(4, product.getQuantity());
            preparedStatement.setInt(5, product.getPrice());
            preparedStatement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Product> findAll() {
        List<Product> productsList = new ArrayList<>();
        try (Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery(FIND_ALL_QUERY);

            while (resultSet.next()) {
                Product product = new Product(
                        resultSet.getLong("id"),
                        resultSet.getString("name"),
                        resultSet.getString("code"),
                        resultSet.getInt("quantity"),
                        resultSet.getInt("price")
                );
                productsList.add(product);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return productsList;
    }

    public Product findByCode(String code) {
        try (PreparedStatement preparedStatement = connection.prepareStatement(FIND_BY_CODE_QUERY)) {
            preparedStatement.setString(1, code);
            ResultSet rs = preparedStatement.executeQuery();
            if (rs.next()) {
                return new Product(
                        rs.getLong("id"),
                        rs.getString("name"),
                        rs.getString("code"),
                        rs.getInt("quantity"),
                        rs.getInt("price")
                );
            }
            return null;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
}

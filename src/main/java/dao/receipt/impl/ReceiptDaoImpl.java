package dao.receipt.impl;

import dao.receipt.ReceiptDao;
import entity.Product;
import entity.Receipt;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ReceiptDaoImpl implements ReceiptDao {
    Connection connection;
    static String CREATING_QUERY = "INSERT INTO receipt (id, total_price, cashier_id, date_time) VALUES(?,?,?,?);";
    static String FIND_ALL_QUERY = "SELECT * FROM receipt;";
    static String FIND_BY_ID_QUERY = "SELECT * FROM receipt WHERE id =?;";

    @Override
    public void save(Receipt receipt) {
        try (PreparedStatement preparedStatement = connection.prepareStatement(CREATING_QUERY)) {
            preparedStatement.setLong(1, receipt.getId());
            preparedStatement.setLong(2, receipt.getTotalPrice());
            preparedStatement.setLong(3, receipt.getCashierId());
            preparedStatement.setDate(4, receipt.getDateTime());
            preparedStatement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Receipt> findAll() {
        List<Receipt> productsList = new ArrayList<>();
        try (Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery(FIND_ALL_QUERY);

            while (resultSet.next()) {
                Receipt receipt = new Receipt(
                        resultSet.getLong("id"),
                        resultSet.getLong("total_price"),
                        resultSet.getLong("cashier_id"),
                        resultSet.getDate("date_time")
                );
                productsList.add(receipt);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return productsList;
    }

    public Receipt findById(Long id) {
        try (PreparedStatement preparedStatement = connection.prepareStatement(FIND_BY_ID_QUERY)) {
            preparedStatement.setLong(1, id);
            ResultSet rs = preparedStatement.executeQuery();
            if (rs.next()) {
                return new Receipt(
                        rs.getLong("id"),
                        rs.getLong("total_price"),
                        rs.getLong("cashier_id"),
                        rs.getDate("date_time")
                );
            }
            return null;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
}

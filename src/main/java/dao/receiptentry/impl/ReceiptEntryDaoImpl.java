package dao.receiptentry.impl;

import dao.receiptentry.ReceiptEntryDao;
import entity.Product;
import entity.Receipt;
import entity.ReceiptEntry;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ReceiptEntryDaoImpl implements ReceiptEntryDao {
    Connection connection;
    static String CREATING_QUERY = "INSERT INTO receipt_entry (id, receipt_id, product_id, quantity, sum) VALUES(?,?,?,?,?);";
    static String FIND_ALL_QUERY = "SELECT * FROM receipt_entry;";
    static String FIND_BY_ID_QUERY = "SELECT * FROM receipt_entry WHERE id =?;";


    @Override
    public void save(ReceiptEntry receiptEntry) {
        try (PreparedStatement preparedStatement = connection.prepareStatement(CREATING_QUERY)) {
            preparedStatement.setLong(1, receiptEntry.getId());
            preparedStatement.setLong(2, receiptEntry.getReceiptId());
            preparedStatement.setLong(3, receiptEntry.getProductId());
            preparedStatement.setInt(4, receiptEntry.getQuantity());
            preparedStatement.setLong(5, receiptEntry.getSum());
            preparedStatement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<ReceiptEntry> findAll() {
        List<ReceiptEntry> receiptsEntriesList = new ArrayList<>();
        try (Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery(FIND_ALL_QUERY);

            while (resultSet.next()) {
                ReceiptEntry receiptEntry = new ReceiptEntry(
                        resultSet.getLong("id"),
                        resultSet.getLong("receopt_id"),
                        resultSet.getLong("product_id"),
                        resultSet.getInt("quantity"),
                        resultSet.getLong("sum")
                );
                receiptsEntriesList.add(receiptEntry);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return receiptsEntriesList;
    }

    public ReceiptEntry findById(Long id) {
        try (PreparedStatement preparedStatement = connection.prepareStatement(FIND_BY_ID_QUERY)) {
            preparedStatement.setLong(1, id);
            ResultSet rs = preparedStatement.executeQuery();
            if (rs.next()) {
                return new ReceiptEntry(
                        rs.getLong("id"),
                        rs.getLong("receopt_id"),
                        rs.getLong("product_id"),
                        rs.getInt("quantity"),
                        rs.getLong("sum")
                );
            }
            return null;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
}

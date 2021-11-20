package dao.receipt;

import entity.Receipt;

import java.util.List;

public interface ReceiptDao {

    void save(Receipt receipt);

    List<Receipt> findAll();

    Receipt findById(Long id);
}

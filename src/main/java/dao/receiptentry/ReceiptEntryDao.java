package dao.receiptentry;

import entity.ReceiptEntry;

import java.util.List;

public interface ReceiptEntryDao {

    void save(ReceiptEntry receiptEntry);

    List<ReceiptEntry> findAll();

    ReceiptEntry findById(Long id);
}

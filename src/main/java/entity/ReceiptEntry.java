package entity;

import lombok.AllArgsConstructor;
import lombok.Value;

@Value
@AllArgsConstructor
public class ReceiptEntry {
    Long id;
    Long receiptId;
    Long productId;
    Integer quantity;
    Long sum;
}

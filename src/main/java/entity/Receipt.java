package entity;

import lombok.AllArgsConstructor;
import lombok.Setter;
import lombok.Value;

import java.sql.Date;
import java.time.LocalDateTime;

@Value
@Setter
@AllArgsConstructor
public class Receipt {
    Long id;
    Long totalPrice;
    Long cashierId;
    Date dateTime;
}

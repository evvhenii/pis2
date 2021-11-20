package entity;

import lombok.AllArgsConstructor;
import lombok.Value;

@Value
@AllArgsConstructor
public class Product {
    Long id;
    String name;
    String code;
    Integer quantity;
    Integer price;
}

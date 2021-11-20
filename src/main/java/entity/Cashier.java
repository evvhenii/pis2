package entity;

import lombok.*;

@Data
@Builder
public class Cashier {
    Long id;
    String name;
    Integer age;
    RoleEnum roleEnum;
    String password;
}

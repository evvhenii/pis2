package entity;

import lombok.AllArgsConstructor;
import lombok.Value;

@Value
@AllArgsConstructor
public class Role {
    Long id;
    RoleEnum roleEnum;
    Long cashierId;
}

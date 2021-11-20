package entity;

import lombok.AllArgsConstructor;
import lombok.Value;

@Value
@AllArgsConstructor
public class Person {
    Long id;
    String name;
    Integer age;
}

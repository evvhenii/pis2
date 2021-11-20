package dao;

import entity.Person;

import java.util.List;

public interface PersonDao {

    void save(Person person);

    List<Person> findAll();

    public Person findById(Long id);
}

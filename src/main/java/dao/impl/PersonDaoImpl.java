package dao.impl;

import dao.PersonDao;
import entity.Person;
import lombok.RequiredArgsConstructor;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
public class PersonDaoImpl implements PersonDao {
    private final Connection connection;
    private static final String CREATING_QUERY = "INSERT INTO person (id, name, age) VALUES(?,?,?);";
    private static final String FIND_ALL_QUERY = "SELECT * FROM person;";
    private static final String FIND_BY_ID_QUERY = "SELECT * FROM person WHERE id =?;";

    public void save(Person person) {
        try (PreparedStatement preparedStatement = connection.prepareStatement(CREATING_QUERY)) {
            preparedStatement.setLong(1, person.getId());
            preparedStatement.setString(2, person.getName());
            preparedStatement.setInt(3, person.getAge());
            preparedStatement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Person> findAll() {
        List<Person> personList = new ArrayList<>();
        try (Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery(FIND_ALL_QUERY);

            while (resultSet.next()) {
                Person person = new Person(
                        resultSet.getLong("id"),
                        resultSet.getString("name"),
                        resultSet.getInt("age")
                );
                personList.add(person);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return personList;
    }

    public Person findById(Long id) {
        try (PreparedStatement preparedStatement = connection.prepareStatement(FIND_BY_ID_QUERY)) {
            preparedStatement.setLong(1, id);
            ResultSet rs = preparedStatement.executeQuery();
            if (rs.next()) {
                return new Person(
                        rs.getLong("id"),
                        rs.getString("name"),
                        rs.getInt("age")
                );
            }
            return null;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
}

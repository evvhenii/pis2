package dao.cashier.impl;

import dao.cashier.CashierDao;
import entity.Cashier;
import entity.RoleEnum;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class CashierDaoImpl implements CashierDao {
    Connection connection;
    static String CREATING_QUERY = "INSERT INTO person (id, name, age) VALUES(?,?,?);";
    static String FIND_ALL_QUERY = "SELECT * FROM person;";
    static String FIND_BY_ID_QUERY = "SELECT * FROM person WHERE id =?;";
    static String FIND_ROLE_BY_ID_QUERY = "SELECT role_enum FROM role WHERE cashier_id =?;";

    public void save(Cashier cashier) {
        try (PreparedStatement preparedStatement = connection.prepareStatement(CREATING_QUERY)) {
            preparedStatement.setLong(1, cashier.getId());
            preparedStatement.setString(2, cashier.getName());
            preparedStatement.setInt(3, cashier.getAge());
            preparedStatement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

//    public List<Cashier> findAll() {
//        List<Cashier> cashierList = new ArrayList<>();
//        try (Statement statement = connection.createStatement()) {
//            ResultSet resultSet = statement.executeQuery(FIND_ALL_QUERY);
//
//            while (resultSet.next()) {
//                Cashier cashier = new Cashier(
//                        resultSet.getLong("id"),
//                        resultSet.getString("name"),
//                        resultSet.getInt("age")
//                );
//                cashierList.add(cashier);
//            }
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//        return cashierList;
//    }

    public Cashier findById(Long id) {
        try (PreparedStatement preparedStatement = connection.prepareStatement(FIND_BY_ID_QUERY)) {
            preparedStatement.setLong(1, id);
            ResultSet rs = preparedStatement.executeQuery();
            Cashier cashier = null;
            if (rs.next()) {
                 cashier = Cashier.builder()
                        .id(rs.getLong("id"))
                        .age(rs.getInt("age"))
                        .name(rs.getString("name"))
                        .password(rs.getString("password"))
                        .build();
            }
            try (PreparedStatement preparedStatementForRole = connection.prepareStatement(FIND_ROLE_BY_ID_QUERY)) {
                preparedStatementForRole.setLong(1, id);
                ResultSet rs1 = preparedStatementForRole.executeQuery();
                if (rs1.next()) {
                    cashier.setRoleEnum(RoleEnum.valueOf(rs1.getString("role_enum")));

                }
                return cashier;
            } catch (SQLException e) {
                e.printStackTrace();
                return null;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
}

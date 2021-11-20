package dao.cashier;

import entity.Cashier;

import java.util.List;

public interface CashierDao {

    void save(Cashier cashier);

//    List<Cashier> findAll();


    Cashier findById(Long id);
}

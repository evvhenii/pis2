import conf.ConnectionProvider;
import dao.product.ProductDao;
import dao.product.impl.ProductDaoImpl;
import entity.Product;

import java.sql.SQLException;
import java.util.Random;
import java.util.UUID;

public class Main {
    public static void main(String[] args) {
//        try {
//            CashierDao cashierDao = new CashierDaoImpl(
//                    ConnectionProvider.getConnection()
//            );
//
//            Cashier cashier = new Cashier(
//                    Math.abs(new Random().nextLong()),
//                    "Tom",
//                    16
//            );
//            System.out.println("Cashier that is being saved ");
//            System.out.println(cashier);
//            cashierDao.save(cashier);
//
//            System.out.println("All saved cashiers");
//            cashierDao.findAll()
//                    .forEach(System.out::println);
//
//            System.out.println("Get cashier by id 3829973416288398015");
//            System.out.println(cashierDao.findById(3829973416288398015L));


//
//            ProductDao productDao = new ProductDaoImpl(
//                    ConnectionProvider.getConnection()
//            );
//
//            Product product = new Product(
//                    Math.abs(new Random().nextLong()),
//                    "Banana",
//                    UUID.randomUUID().toString(),
//                    721
//            );
//            System.out.println("Product that is being saved ");
//            System.out.println(product);
//            productDao.save(product);
//
//            System.out.println("All saved products");
//            productDao.findAll()
//                    .forEach(System.out::println);
//
//            System.out.println("Get product by id 3829973416288398015");
//            System.out.println(productDao.findByCode(3829973416288398015L));
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }

    }
}

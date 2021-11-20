package servlets;

import conf.ConnectionProvider;
import dao.product.ProductDao;
import dao.product.impl.ProductDaoImpl;
import dao.receipt.ReceiptDao;
import dao.receipt.impl.ReceiptDaoImpl;
import dao.receiptentry.ReceiptEntryDao;
import dao.receiptentry.impl.ReceiptEntryDaoImpl;
import entity.*;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Date;
import java.sql.SQLException;
import java.util.*;

public class ReceiptHelper {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Cashier cashier = (Cashier) request.getSession().getAttribute("user");
        if (RoleEnum.CASHIER != cashier.getRoleEnum()) {
            RequestDispatcher rd = request.getRequestDispatcher("templates/login-not-authorized.jsp");
            try {
                rd.include(request, response);
                return;
            } catch (ServletException e) {
                e.printStackTrace();
            }
        }

        RequestDispatcher dispatcher = request.getRequestDispatcher(
                "templates/receipt-creating.jsp"
        );
        try {
            dispatcher.forward(request, response);
        } catch (ServletException e) {
            e.printStackTrace();
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String[] codesAndQuantities = request.getParameterMap().get("product_code_and_quantity");
        Cashier cashier = (Cashier) request.getSession().getAttribute("user");

        ProductDao productDao = null;
        try {
            productDao = new ProductDaoImpl(ConnectionProvider.getConnection());
        } catch (SQLException e) {
            e.printStackTrace();
        }

        Long receiptId = Math.abs(new Random().nextLong());

        List<ReceiptEntry> receiptEntryList = new ArrayList<>();
        for (int i = 0; i < codesAndQuantities.length - 1; i += 2) {
            Product product = productDao.findByCode(codesAndQuantities[i]);
            int quantity = Integer.valueOf(codesAndQuantities[i + 1]);

            receiptEntryList.add(new ReceiptEntry(
                    Math.abs(new Random().nextLong()),
                    receiptId,
                    product.getId(),
                    quantity,
                    (long) product.getPrice() * quantity
            ));
        }

        Receipt receipt = new Receipt(
                receiptId,
                receiptEntryList.stream()
                        .map(ReceiptEntry::getSum)
                        .reduce(0L, Long::sum),
                cashier.getId(),
                new Date(Calendar.getInstance().getTime().getTime())
        );

        ReceiptDao receiptDao = null;
        try {
            receiptDao = new ReceiptDaoImpl(ConnectionProvider.getConnection()) {
            };
        } catch (SQLException e) {
            e.printStackTrace();
        }

        receiptDao.save(receipt);


        ReceiptEntryDao receiptEntryDao = null;
        try {
            receiptEntryDao = new ReceiptEntryDaoImpl(ConnectionProvider.getConnection()) {
            };
        } catch (SQLException e) {
            e.printStackTrace();
        }

        receiptEntryList.stream().forEach(receiptEntryDao::save);

        RequestDispatcher dispatcher = request.getRequestDispatcher(
                "/templates/receipt-creating.jsp");
        try {
            dispatcher.forward(request, response);
        } catch (ServletException e) {
            e.printStackTrace();
        }

    }
}

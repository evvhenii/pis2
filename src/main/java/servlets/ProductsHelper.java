package servlets;

import conf.ConnectionProvider;
import dao.product.ProductDao;
import dao.product.impl.ProductDaoImpl;
import entity.Cashier;
import entity.Product;
import entity.RoleEnum;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.UUID;

public class ProductsHelper {
    protected void doGet(HttpServletRequest request, HttpServletResponse response, ServletContext servletContext) throws IOException {
        Cashier cashier = (Cashier) request.getSession().getAttribute("user");
        if (cashier == null || RoleEnum.ADMINISTRATOR != cashier.getRoleEnum()) {
            RequestDispatcher rd = request.getRequestDispatcher("templates/login-not-authorized.jsp");
            try {
                rd.include(request, response);
                return;
            } catch (ServletException e) {
                e.printStackTrace();
            }
        }

        ProductDao productDao = null;
        try {
            productDao = new ProductDaoImpl(ConnectionProvider.getConnection());
        } catch (SQLException e) {
            e.printStackTrace();
        }

        List<Product> products = productDao.findAll();
        request.setAttribute("productRecords", products);

        RequestDispatcher dispatcher = servletContext.getRequestDispatcher(
                "/templates/product-records.jsp");
        try {
            dispatcher.forward(request, response);
        } catch (ServletException e) {
            e.printStackTrace();
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response, ServletContext servletContext) throws IOException {
        String name = request.getParameter("name");
        Integer quantity = Integer.parseInt(request.getParameter("quantity"));
        Integer price = Integer.parseInt(request.getParameter("price"));
        ProductDao productDao = null;
        try {
            productDao = new ProductDaoImpl(
                    ConnectionProvider.getConnection()
            );
        } catch (SQLException e) {
            e.printStackTrace();
        }

        Product product = new Product(
                Math.abs(new Random().nextLong()),
                name,
                UUID.randomUUID().toString(),
                quantity,
                price
        );
        productDao.save(product);

        List<Product> products = productDao.findAll();
        request.setAttribute("productRecords", products);

        RequestDispatcher dispatcher = servletContext.getRequestDispatcher(
                "/templates/product-records.jsp");
        try {
            dispatcher.forward(request, response);
        } catch (ServletException e) {
            e.printStackTrace();
        }
    }
}
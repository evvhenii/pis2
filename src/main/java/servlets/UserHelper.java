package servlets;

import conf.ConnectionProvider;
import dao.cashier.CashierDao;
import dao.cashier.impl.CashierDaoImpl;
import entity.Cashier;
import entity.RoleEnum;
import helpers.PostToGetRequestWrapper;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import jakarta.xml.bind.DatatypeConverter;

import javax.crypto.spec.SecretKeySpec;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.security.Key;
import java.sql.SQLException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class UserHelper {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        RequestDispatcher dispatcher = request.getRequestDispatcher("templates/login.jsp");
        try {
            dispatcher.forward(request, response);
        } catch (ServletException e) {
            e.printStackTrace();
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response, ServletContext servletContext) throws ServletException, IOException {
        Long id = Long.valueOf(request.getParameter("username"));
        String password = request.getParameter("password");
        HttpSession session = request.getSession();

//        for(String name: new String[]{ "Authorization", "custom-id", "custom-role"}) {
//            Cookie cookie = new Cookie(name, "");
//            cookie.setMaxAge(0);
//            response.addCookie(cookie);
//        }

        Cashier cashier = getUser(id, password);
        if(cashier == null) {
            RequestDispatcher rd = request.getRequestDispatcher("templates/login-with-error.jsp");
            rd.include(request, response);
            return;
        }
        session.setAttribute("user", cashier);

//        Map<String, Object> claims = new HashMap<>();
//        claims.put("id", id);
//        claims.put("role", role);
//        String jwt = createJWT(100000000L, claims);
//
//        response.setHeader("Authorization", "Bearer " + jwt);




        if(cashier.getRoleEnum() == RoleEnum.ADMINISTRATOR) {
            RequestDispatcher dispatcher = servletContext
                    .getRequestDispatcher("/products");
            dispatcher.forward(new PostToGetRequestWrapper(request), response);
//            response.sendRedirect("/receipts");

            return;
        }

        if(cashier.getRoleEnum() == RoleEnum.CASHIER) {
            RequestDispatcher dispatcher = servletContext
                    .getRequestDispatcher("/receipts");
            dispatcher.forward(new PostToGetRequestWrapper(request), response);

//            response.sendRedirect("/receipts");
            return;
        }
    }

    private Cashier getUser(Long id, String password) {
        CashierDao cashierDao = null;
        try {
            cashierDao = new CashierDaoImpl(ConnectionProvider.getConnection());
        } catch (SQLException e) {
            e.printStackTrace();
        }

        Cashier cashier = cashierDao.findById(id);
        if(cashier == null) return null;
        if(!password.equals(cashier.getPassword())) return null;
        return cashier;
    }

    public static String createJWT(long ttlMillis, Map<String, Object> claims) {
        final String SECRET_KEY = "this is jwt token key";

        SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;

        long nowMillis = System.currentTimeMillis();
        Date now = new Date(nowMillis);

        byte[] apiKeySecretBytes = DatatypeConverter.parseBase64Binary(SECRET_KEY);
        Key signingKey = new SecretKeySpec(apiKeySecretBytes, signatureAlgorithm.getJcaName());

        JwtBuilder builder = Jwts.builder()
                .setIssuedAt(now)
                .setClaims(claims)
                .signWith(signatureAlgorithm, signingKey);

        if (ttlMillis > 0) {
            long expMillis = nowMillis + ttlMillis;
            Date exp = new Date(expMillis);
            builder.setExpiration(exp);
        }

        return builder.compact();
    }
}
package filters;

import entity.Cashier;
import io.jsonwebtoken.Jwts;

import javax.servlet.*;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.xml.bind.DatatypeConverter;
import java.io.IOException;
import java.util.Map;

public class AuthenticationFilter implements Filter {
    @Override
    public void init(FilterConfig arg0) throws ServletException {
    }

    @Override
    public void doFilter(
            ServletRequest request,
            ServletResponse response,
            FilterChain chain
    ) throws IOException, ServletException {


        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;

        HttpSession session = httpRequest.getSession();

        if(session.getAttribute("user") == null) {
            RequestDispatcher rd = request.getRequestDispatcher("templates/login-with-error.jsp");
            rd.include(request, response);
        }

//        final String SECRET_KEY = "this is jwt token key";
//
////        request.getC
////        httpResponse.ge
//        Cookie[] cookies = httpRequest.getCookies();
//
//        String headerWithAuthorization = httpResponse.getHeader("Authorization");
//
//        System.out.println("Header in filter " + headerWithAuthorization);
//        System.out.println("Header in filter  request " + httpResponse.getHeader("Authorization"));
//        String jwt = headerWithAuthorization.substring(7);
//
//        Map<String, Object> claims = null;
//        try {
//            claims = Jwts.parser()
//                    .setSigningKey(DatatypeConverter.parseBase64Binary(SECRET_KEY))
//                    .parseClaimsJws(jwt).getBody();
//        } catch (Exception e) {
//            RequestDispatcher rd = request.getRequestDispatcher("templates/login-with-error.jsp");
//            rd.include(request, response);
//        }
//
//        httpResponse.setHeader("custom-id", String.valueOf(claims.get("id")));
//        httpResponse.setHeader("custom-role", String.valueOf(claims.get("role")));

        chain.doFilter(httpRequest, httpResponse);
    }

    @Override
    public void destroy() {
    }
}

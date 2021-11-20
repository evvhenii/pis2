package helpers;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

public class PostToGetRequestWrapper extends HttpServletRequestWrapper {
    public PostToGetRequestWrapper(HttpServletRequest request) {
        super(request);
    }

    public String getMethod(){ return "GET"; }
}

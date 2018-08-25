package com.songsy.core.web.filter;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

/**
 * @author songshuiyang
 * @date 2018/2/28 21:14
 */
public class CharacterEncodingFilter implements Filter {
    private String encode = "UTF-8";

    @Override
    public void init(FilterConfig arg0) throws ServletException {
        String encoding = arg0.getInitParameter("encode");
        if (encoding != null) {
            encode = encoding;
        }
    }

    @Override
    public void doFilter(ServletRequest arg0, ServletResponse arg1, FilterChain arg2) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) arg0;
        HttpServletResponse response = (HttpServletResponse) arg1; // post方式设置编码格式：
        request.setCharacterEncoding(encode);
        response.setContentType("text/html;charset=" + encode);
        arg2.doFilter(new CharacterEncodingRequest(request), arg1);
    }

    @Override
    public void destroy() {
    }
}

/**
 * 对Get方式传递的请求参数进行编码
 */
class CharacterEncodingRequest extends HttpServletRequestWrapper {
    private HttpServletRequest request;

    public CharacterEncodingRequest(HttpServletRequest request) {
        super(request);
        this.request = request;
    }

    /**
     * 对参数重新编码
     */
    @Override
    public String getParameter(String name) {
        String value = super.getParameter(name);
        if (value == null) {
            return null;
        }
        String method = request.getMethod();
        if ("Get".equalsIgnoreCase(method)) {
            try {
                value = new String(value.getBytes("ISO8859-1"), request.getCharacterEncoding());
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }
        return value;
    }
}
package com.zkzong.framework.core.filter;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * @Author: zong
 * @Date: 2021/8/20
 */
@WebFilter(urlPatterns = "/*")
public class DleteFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        Filter.super.init(filterConfig);
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {

        HttpServletRequestWrapper requestWrapper;
        if (servletRequest instanceof HttpServletRequestWrapper) {
            requestWrapper = (HttpServletRequestWrapper) servletRequest;
        } else {
            requestWrapper = new HttpServletRequestWrapper((HttpServletRequest) servletRequest);
        }

        StringBuilder sb = new StringBuilder("使用拦截器统计Delete方法[");

        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        sb.append(now.format(formatter)).append("|");

        //servletRequest.

        filterChain.doFilter(servletRequest, servletResponse);
    }

    @Override
    public void destroy() {
        Filter.super.destroy();
    }
}

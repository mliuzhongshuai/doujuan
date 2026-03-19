package com.lingjing.doujuan.infrastructure.config;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;

import java.io.IOException;
import java.util.UUID;

@Slf4j
@WebFilter(filterName = "MdcFilter", urlPatterns = "/*")
public class MdcFilter implements Filter {


    // 自定义字段名
    private static final String CUSTOM_FIELD = "traceId";


    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        try {
            String traceId = UUID.randomUUID().toString();
            MDC.put(CUSTOM_FIELD, traceId);
            request.setAttribute(CUSTOM_FIELD, traceId);
            chain.doFilter(request, response);
        } finally {
            // 请求响应完成后移除自定义字段
            MDC.remove(CUSTOM_FIELD);
        }
    }
}

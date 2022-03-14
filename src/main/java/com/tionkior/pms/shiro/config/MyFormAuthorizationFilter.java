package com.tionkior.pms.shiro.config;

import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;
import org.apache.shiro.web.util.WebUtils;
import org.springframework.context.annotation.Configuration;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

@Configuration
public class MyFormAuthorizationFilter extends FormAuthenticationFilter {

    @Override
    protected boolean isAccessAllowed(ServletRequest servletRequest, ServletResponse servletResponse, Object o) {
        HttpServletRequest httpServletRequest = WebUtils.toHttp(servletRequest);
        if ("OPTIONS".equals(httpServletRequest.getMethod())) {
            return true;
        }
        return super.isAccessAllowed(servletRequest, servletResponse, o);
    }

}
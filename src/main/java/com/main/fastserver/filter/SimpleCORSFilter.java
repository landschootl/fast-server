package com.main.fastserver.filter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;

@Component
public class SimpleCORSFilter extends GenericFilterBean {

    /**
     * The Logger for this class.
     */
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Value("{URL_FRONT}")
    private String allowOrigin;

    @Override
    public void doFilter(ServletRequest req, ServletResponse resp,
                         FilterChain chain) throws IOException, ServletException {
        HttpServletResponse response = (HttpServletResponse) resp;

        //String allowOrigin = System.getenv("URL_FRONT");

        if(! StringUtils.isEmpty(allowOrigin) ) {
            response.setHeader("Access-Control-Allow-Origin", allowOrigin);

            response.setHeader("Access-Control-Allow-Methods", "POST, PUT, GET, OPTIONS, DELETE");
            response.setHeader("Access-Control-Max-Age", "3600");
            response.setHeader("Access-Control-Allow-Headers", "Authorization, Content-Type");
            //response.setHeader("Access-Control-Allow-Credentials", "true");
        }

        chain.doFilter(req, resp);
    }
}
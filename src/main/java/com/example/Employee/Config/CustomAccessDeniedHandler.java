package com.example.Employee.Config;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.access.AccessDeniedHandlerImpl;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@Component
public  class CustomAccessDeniedHandler implements AccessDeniedHandler {

    @Override
    public void handle(jakarta.servlet.http.HttpServletRequest request, jakarta.servlet.http.HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException {
        String errorMessage = "Access denied. You do not have permission to access this resource.";
        response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        response.setContentType("application/json");
        PrintWriter writer = response.getWriter();
        writer.print("{ \"error\": \"" + errorMessage + "\" }");
        writer.flush();
    }
}
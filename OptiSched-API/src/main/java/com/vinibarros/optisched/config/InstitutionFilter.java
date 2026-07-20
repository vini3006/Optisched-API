package com.vinibarros.optisched.config;

import jakarta.servlet.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Component;
import java.io.IOException;

@Component
public class InstitutionFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null && authentication.getPrincipal() instanceof Jwt jwt) {

            Long institutionId = jwt.getClaim("institution_id");
            Long professorId = jwt.getClaim("professor_id");

            if (institutionId != null) {
                InstitutionContext.setCurrentInstitution(institutionId);

                request.setAttribute("institutionIdAdmin", institutionId);
                request.setAttribute("institutionIdProfessor", institutionId);
            }

            if (professorId != null) {
                request.setAttribute("authenticatedProfessorId", professorId);
            }
        }

        try {
            chain.doFilter(request, response);
        } finally {
            InstitutionContext.clear();
        }
    }
}

package com.multitenant.multitenancy.config.security;

import com.multitenant.multitenancy.config.multitenancy.TenantConnectionProvider;
import com.multitenant.multitenancy.config.multitenancy.TenantContext;
import com.multitenant.multitenancy.meta.user.UserDetailsServiceImpl;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;

@RequiredArgsConstructor
@Log4j2
@Component
public class AuthTokenFilter extends OncePerRequestFilter {
  public static final String TENANT_REQUEST_HEADER = "XTenantID";

  private final JwtUtils jwtUtils;

  private final UserDetailsServiceImpl userDetailsService;

  private final TenantConnectionProvider tenantConnectionProvider;

  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
      throws ServletException, IOException {

    String tenantUUID = request.getHeader(TENANT_REQUEST_HEADER);
    if (tenantUUID == null) {
      //response.getWriter().write(TENANT_REQUEST_HEADER + "not present in the Request Header");
      response.setStatus(400);
    }

    try {
      String jwt = parseJwt(request);
      if (jwt != null && jwtUtils.validateJwtToken(jwt)) {
        String username = jwtUtils.getUserNameFromJwtToken(jwt);

        UserDetails userDetails = userDetailsService.loadUserByUsername(username);
        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
            userDetails, null, userDetails.getAuthorities());
        authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

        SecurityContextHolder.getContext().setAuthentication(authentication);

        Connection connection = tenantConnectionProvider.getConnection(tenantUUID);

        TenantContext.setCurrentTenant(tenantUUID);
      }
    } catch (Exception e) {
      logger.error("Cannot set user authentication: {}", e);
    }

    filterChain.doFilter(request, response);
  }

  private String parseJwt(HttpServletRequest request) {
    String headerAuth = request.getHeader("Authorization");

    if (StringUtils.hasText(headerAuth) && headerAuth.startsWith("Bearer ")) {
      return headerAuth.substring(7, headerAuth.length());
    }

    return null;
  }
}

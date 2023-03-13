package com.iunetworks.security;

import com.iunetworks.util.security.JwtTokenUtil;
import io.jsonwebtoken.ExpiredJwtException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


@Component
public class JWTAuthenticationTokenFilter extends OncePerRequestFilter {

  final Logger log = LoggerFactory.getLogger(JWTAuthenticationTokenFilter.class);


  @Autowired
  private JwtTokenUtil tokenUtil;

  @Autowired
  private UserDetailsService userDetailsService;

  private final String str = "Bearer ";
  // JWT Token is in the form "Bearer token". Remove Bearer word and get
  // only the Token


  @Override
  protected void doFilterInternal(HttpServletRequest httpServletRequest,
                                  HttpServletResponse httpServletResponse,
                                  FilterChain filterChain) throws ServletException, IOException {
    String requestHeader = httpServletRequest.getHeader("Authorization");

    String username = null;
    String authToken = null;

    if (requestHeader != null && requestHeader.startsWith(str)) {
      authToken = requestHeader.substring(str.length());
      try {
        username = tokenUtil.getUsernameFromToken(authToken);
      } catch (IllegalArgumentException e) {
        logger.error("Unable to get JWT Token");
        httpServletResponse.setHeader("error", e.getMessage());
      } catch (ExpiredJwtException e) {
        logger.error("JWT token is expired");
        httpServletResponse.setHeader("error", e.getMessage());
      }
    } else {
      log.warn("JWT Token does not begin with Bearer String");
    }

    // we have token, now we need to validate it


    if (username != null) {

      UserDetails userDetails = this.userDetailsService.loadUserByUsername(username);

      // validate token and configure Spring Security to manually set authentication

      if (Boolean.TRUE.equals(tokenUtil.validateToken(authToken, userDetails.getUsername()))) {
        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
        authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(httpServletRequest));
        log.info("authenticated user:{}", username);

        // After setting the Authentication in the context, we specify
        // that the current user is authenticated. So it passes the
        // Spring Security Configurations successfully.

        SecurityContextHolder.getContext().setAuthentication(authentication);
      }
    }

    filterChain.doFilter(httpServletRequest, httpServletResponse);

  }

}

package com.iunetworks.util.security;

import com.iunetworks.dtos.userdtos.BankUserDto;
import com.iunetworks.dtos.userdtos.CustomerUserDto;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.function.Function;

@Component

public class JwtTokenUtil {

  @Value("${jwt.secret}")
  private String secret;
  @Value("${jwt.expiration}")
  private Long expiration;

  final Logger log = LoggerFactory.getLogger(JwtTokenUtil.class);


  public String getUsernameFromToken(String token) {
    return getClaimFromToken(token, Claims::getSubject);
  }

  public UserType getUserType(String token) {
    return UserType.from(getClaimFromToken(token, Claims::getIssuer));
  }

  public Date getExpirationDateFromToken(String token) {
    return getClaimFromToken(token, Claims::getExpiration);
  }

  public <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
    final Claims claims = getAllClaimsFromToken(token);
    return claimsResolver.apply(claims);
  }

  private Claims getAllClaimsFromToken(String token) {
    return Jwts.parser()
      .setSigningKey(secret)
      .parseClaimsJws(token)
      .getBody();
  }

  private Boolean isTokenExpired(String token) {
    final Date expiration = getExpirationDateFromToken(token);
    return expiration.before(new Date());
  }

  public Map<String, String> generateBankUserToken(BankUserDto user, Collection<String> permissions) {
    return doGenerateToken(user.getEmail(), UserType.ADMIN, user.getId(), permissions);
  }

  public Map<String, String> generateCustomerUserToken(CustomerUserDto user, Collection<String> permissions) {
    return doGenerateToken(user.getEmail(), UserType.CUSTOMER, user.getId(), permissions);
  }

  public Set<String> permissionsFromToken(String token) {
    return getClaimFromToken(token, new Function<Claims, Set<String>>() {
      @Override
      public Set<String> apply(Claims claims) {
        return claims.get("permissions", Set.class);
      }
    });
  }

  private Map<String, String> doGenerateToken(String subject, UserType type, UUID id, Collection<String> permissions) {

    Map<String, String> map = new HashMap<>();
    final Date createdDate = new Date();
    final Date expirationDate = calculateExpirationDate(createdDate);
    Claims claims = Jwts.claims().setSubject(subject);
    final Date refreshExpirationDate = calculateRefreshTokenExpirationDate(createdDate);


    claims.put("permissions", permissions);

    claims.put("id", id);
    String accessToken = Jwts.builder()
      .setClaims(claims)
      .setSubject(subject)
      .setIssuedAt(createdDate)
      .setIssuer(type.name())
      .setIssuer(claims.getId())
      .setExpiration(expirationDate)
      .signWith(SignatureAlgorithm.HS512, secret)
      .compact();

    String refreshToken = Jwts.builder()
      .setClaims(claims)
      .setSubject(subject)
      .setIssuedAt(createdDate)
      .setExpiration(refreshExpirationDate)
      .signWith(SignatureAlgorithm.HS256, secret)
      .compact();

    map.put("access_token", accessToken);
    map.put("refersh_token", refreshToken);
    return map;
  }

  public Boolean validateToken(String token, String username) {
    final String usernameToken = getUsernameFromToken(token);
    return (
      usernameToken.equals(username)
        && !isTokenExpired(token));
  }

  private Date calculateExpirationDate(Date createdDate) {
    return new Date(createdDate.getTime() + expiration * 2000);
  }


  private Date calculateRefreshTokenExpirationDate(Date createdDate) {
    return new Date(createdDate.getTime() + expiration * 20000000);
  }


  public enum UserType {
    CUSTOMER, ADMIN;

    public static UserType from(final String strType) {
      return Arrays.stream(values())
        .filter(t -> t.name().equalsIgnoreCase(strType))
        .findFirst()
        .orElseThrow(
          () -> new RuntimeException("Unrecognized user type")
        );
    }
  }
}


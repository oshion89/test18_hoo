package com.hoo.test18_hoo.controller.common.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.crypto.spec.SecretKeySpec;
import javax.servlet.http.HttpServletRequest;
import java.security.Key;
import java.util.Date;
import java.util.Map;

@RequiredArgsConstructor
@Component
public class JwtTokenProvider {

  public static final String AUTHORIZATION_HEADER = "Authorization";
  public static final String BEARER_PREFIX = "Bearer ";


  // accessToken 만료시간
  @Value("${config.jwt.token-expire}")
  private long tokenExpire;

  // 토큰 secretKey
  @Value("${config.jwt.secret-key}")
  private String secretKey;

  // payload에 저장될 유저정보 키 값
  @Value("${config.jwt.payload-key}")
  private String payloadKey;

  // 토큰 유효시간 1분
//  private long tokenValidTime = 60 * 1000L;
  // private long tokenValidTime = 20 * 1000L; // 20초

  // JWT 토큰 생성
  // @param member : 사용자 정보 Object
  // @param minutes : 토큰 유지시간(분 단위)
  public String createToken(Object member) {
    // claim : JWT payload 에 저장되는 정보단위
    // 정보는 key / value 쌍으로 저장
    Claims claims = Jwts.claims();
    claims.put(payloadKey, member);
    Date now = new Date();

    String token = Jwts.builder().setClaims(claims) // 정보 저장
        .setIssuedAt(now) // 토큰 발행 시간 정보
//        .setExpiration(new Date(now.getTime() + (tokenValidTime * minutes))) // set Expire Time
        .setExpiration(new Date(now.getTime() + (1000 * tokenExpire))) // set Expire Time
        .signWith(SignatureAlgorithm.HS256, createSigningKey()) // 사용할 암호화 알고리즘과 signature에 들어갈 secret값 세팅
        .compact();
    return token;
  }

  // signingKey 생성
  private Key createSigningKey() {
    byte[] apiKeySecretBytes = secretKey.getBytes();
    return new SecretKeySpec(apiKeySecretBytes, SignatureAlgorithm.HS256.getJcaName());
  }

  // 토큰 정보 추출
  public Claims getTokenBody(String token) {
    return Jwts.parser().setSigningKey(createSigningKey()).parseClaimsJws(token).getBody();
  }

  // 토큰 정보 추출
  @SuppressWarnings("unchecked")
  public Map<String, Object> getTokenBody(HttpServletRequest request) {

    // Request Header 에서 토큰을 꺼냄
    String jwt = resolveToken(request);

    // 토큰 유효성, 만료일자 체크
    if(validateToken(jwt)) {
      // 토큰 복호화
      Claims claims = getTokenBody(jwt);
      return (Map<String, Object>) claims.get(payloadKey);
    } else {
      return null;
    }
  }

  // 토큰 유효성, 만료일자 체크
  public boolean validateToken(String jwtToken) {
    //minsang.shin제거, JWT Exception 추가로 인해
//    try {
    //minsang.shin제거, JWT Exception 추가로 인해
      Jws<Claims> claims = Jwts.parser().setSigningKey(createSigningKey()).parseClaimsJws(jwtToken);
      return !claims.getBody().getExpiration().before(new Date());
    //minsang.shin제거, JWT Exception 추가로 인해
//    } catch (Exception e) {
//      return false;
//    }
    //minsang.shin제거, JWT Exception 추가로 인해
  }

  // Request Header 에서 토큰 정보를 꺼내오기
  public String resolveToken(HttpServletRequest request) {
    String bearerToken = request.getHeader(AUTHORIZATION_HEADER);
    if (StringUtils.hasText(bearerToken) && bearerToken.startsWith(BEARER_PREFIX)) {
        return bearerToken.substring(7);
    }
    return null;
}
  
}

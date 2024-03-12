package com.heima.admin.gateway.filter;


import com.heima.admin.gateway.utils.AppJwtUtil;
import io.jsonwebtoken.Claims;
import org.apache.commons.lang.StringUtils;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Component
public class AuthorizeFilter implements GlobalFilter, Ordered {

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        // 获取request、response
        ServerHttpRequest request = exchange.getRequest();
        ServerHttpResponse response = exchange.getResponse();
        // 判断是否是登录请求
        if(request.getURI().getPath().contains("/login")){
            return chain.filter(exchange);
        }
        // 获取token并判断
        String token = request.getHeaders().getFirst("token");
        if(StringUtils.isBlank(token)){
            response.setStatusCode(HttpStatus.UNAUTHORIZED);
            return response.setComplete();
        }
        // 解析，判断token是否有效
        try{
            Claims claims = AppJwtUtil.getClaimsBody(token);
            int res = AppJwtUtil.verifyToken(claims);
            if(claims == null || res == 1 || res == 2){  //  -1：有效，0：有效，1：过期，2：过期
                response.setStatusCode(HttpStatus.UNAUTHORIZED);
                return response.setComplete();
            }
            // filter --(userId)--> interceptor
            String userId = claims.get("id").toString();
            ServerHttpRequest serverHttpRequest = request.mutate().headers(httpHeaders -> {
                        httpHeaders.add("userId", userId);
            }).build();
            //重置header
            exchange.mutate()
                    .request(serverHttpRequest)
                    .build();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return chain.filter(exchange);
    }

    @Override
    public int getOrder() {
        return 0;
    }
}

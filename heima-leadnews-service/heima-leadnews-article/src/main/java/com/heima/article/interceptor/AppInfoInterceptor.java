package com.heima.article.interceptor;

import com.heima.model.user.pojos.ApUser;
import com.heima.utils.thread.ApThreadLocalUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Slf4j
public class AppInfoInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //得到header中的信息
        String userId = request.getHeader("userId");
        if(StringUtils.isNotBlank(userId)){
            //把用户id存入 ThreadLocal 中
            ApUser apUser = new ApUser();
            apUser.setId((int) Long.parseLong(userId));
            ApThreadLocalUtils.setUser(apUser);
            log.info("AppInfoInterceptor设置 userId = {}", userId);
        }
        return true;  // 此interceptor只负责获取userId的线程上下文信息，不校验token
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        ApThreadLocalUtils.clear();
    }
}
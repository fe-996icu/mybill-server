package com.icu.mybill.interceptors;

import com.icu.mybill.dto.TokenUserDTO;
import com.icu.mybill.exception.TokenInvalidException;
import com.icu.mybill.util.ThreadLocalHelper;
import com.icu.mybill.util.TokenHelper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.HandlerExecutionChain;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.HandlerMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

@Slf4j
@Service // 添加@Service注解，方便依赖注入
public class TokenInterceptor implements HandlerInterceptor {
    @Autowired
    private TokenHelper tokenHelper;

    @Autowired
    private ApplicationContext applicationContext;

    public HandlerMapping getHandlerMapping() {
        return applicationContext.getBean("requestMappingHandlerMapping", RequestMappingHandlerMapping.class);
    }

    @Override
    // 目标资源方法执行前执行，返回true：放行，返回false：不放行
    public boolean preHandle(HttpServletRequest req, HttpServletResponse resp, Object handler) throws Exception {
        log.info("TokenInterceptor拦截器执行-[preHandle]：{}", req.getRequestURI());

        // 检查 URI 是否有对应的 Controller（404检查）
        HandlerMapping handlerMapping = getHandlerMapping();
        HandlerExecutionChain chain = handlerMapping.getHandler(req);
        if (chain == null) {
            resp.sendError(HttpServletResponse.SC_NOT_FOUND, "Not Found");
            return false; // 直接返回 404，不进行 Token 校验
        }

        String token = req.getHeader("token");
        if(token == null || token.isEmpty()){
            log.info("token为空");
            throw new TokenInvalidException("token为空");
        }

        try {
            TokenUserDTO tokenUserDTO = tokenHelper.parseToken(token);
            ThreadLocalHelper.set(tokenUserDTO);
            log.info("token有效-[{}]： {}", req.getRequestURI(), tokenUserDTO);
            return true;
        }catch (Exception ex){
            log.info("token无效 [{}]:", token);
            throw new TokenInvalidException("token无效");
        }
    }

    @Override
    // 目标资源方法执行后执行
    public void postHandle(HttpServletRequest req, HttpServletResponse resp, Object handler, ModelAndView modelAndView) throws Exception {
        log.info("TokenInterceptor拦截器执行-[postHandle]：{}", req.getRequestURI());
        // 请求结束，清除ThreadLocal
        ThreadLocalHelper.remove();
    }

    @Override
    // 视图渲染完毕后执行，最后执行
    public void afterCompletion(HttpServletRequest req, HttpServletResponse resp, Object handler, Exception ex) throws Exception {
        log.info("TokenInterceptor拦截器执行-[afterCompletion]：{}", req.getRequestURI());
    }
}

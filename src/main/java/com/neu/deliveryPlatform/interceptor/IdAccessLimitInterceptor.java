package com.neu.deliveryPlatform.interceptor;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.neu.deliveryPlatform.annotion.IdAccessLimit;
import com.neu.deliveryPlatform.filter.JwtAuthenticationTokenFilter;
import com.neu.deliveryPlatform.properties.ErrorCode;
import com.neu.deliveryPlatform.util.Response;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.http.MediaType;
import org.springframework.scripting.support.ResourceScriptSource;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;

/**
 * @author JiuYou
 * {@code @program:} deliveryPlatform
 * {@code @description:} id限流拦截器,使用lua脚本实现,防止高并发下的超卖问题
 * {@code @create:} 2021-06-10 21:00
 */
@Component
public class IdAccessLimitInterceptor implements HandlerInterceptor {
    public static final Logger log = LoggerFactory.getLogger(IdAccessLimitInterceptor.class);
    @Autowired
    StringRedisTemplate redisTemplate;

    private DefaultRedisScript<Long> getRedisScript;

    /**
     * 初始化lua脚本
     */
    @PostConstruct
    public void init() {
        getRedisScript = new DefaultRedisScript<>();
        getRedisScript.setResultType(Long.class);
        getRedisScript.setScriptSource(new ResourceScriptSource(new ClassPathResource("lua/idAccessLimit.lua")));
        log.info("lua脚本初始化完成");
    }

    @Override
    public boolean preHandle(@NotNull HttpServletRequest request, @NotNull HttpServletResponse response, @NotNull Object handler) {
        try {
            if (handler instanceof HandlerMethod) {
                HandlerMethod handlerMethod = (HandlerMethod) handler;
                IdAccessLimit idAccessLimit = handlerMethod.getMethodAnnotation(IdAccessLimit.class);
                //如果没有注解,直接通行
                if (null == idAccessLimit) {
                    return true;
                }
                //获取注解上的参数
                int seconds = idAccessLimit.seconds();
                int maxCount = idAccessLimit.maxCount();
                String limitKey = idAccessLimit.key();
                Long id = JwtAuthenticationTokenFilter.threadLocal.get();

                String servletPath = request.getServletPath();
                String key = limitKey + "id:" + id + servletPath;
                // 已经访问的次数(不包括这次访问)
                String count = redisTemplate.opsForValue().get(key);
                log.info("(id限流请求次数) id:{} 接口名:{} 已访问次数:{}", id, servletPath, count == null ? 0 : count);
                //执行lua脚本
                ArrayList<String> keyList = new ArrayList<>();
                keyList.add(key);
                //调用脚本
                Long execute = redisTemplate.execute(getRedisScript, keyList, String.valueOf(seconds), String.valueOf(maxCount));
                if (execute != null && execute == 0) {
                    log.info("(id限流请求次数) id:{} 接口名:{} 超过访问次数:{}", id, servletPath, maxCount);
                    response(response);
                    return false;
                }
                return true;
            }
        } catch (Exception e) {
            log.error("(id限流请求次数) 请求异常 ex:{}", e.getMessage());
        }
        return true;
    }

    /**
     * 拦截器异常响应
     */
    public void response(HttpServletResponse response) throws IOException {
        response.setCharacterEncoding("UTF-8");
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        ObjectMapper objectMapper = new ObjectMapper();
        // R.fail 是接口响应统一封装返回
        response.getWriter().println(objectMapper.writeValueAsString(Response.error(ErrorCode.REQUEST_FREQUENT)));
    }
}

package com.tencent.wxcloudrun.config.Interceptor;

import com.tencent.wxcloudrun.config.Interceptor.annotation.LoginUser;
import com.tencent.wxcloudrun.service.UserTokenManager;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

/**
 * @author cf
 * @date 2022/2/22 11:27
 */
public class TokenInterceptor implements HandlerMethodArgumentResolver {
    public static final String LOGIN_TOKEN_KEY = "Wx-Token";
    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.getParameterType().isAssignableFrom(String.class)&&parameter.hasParameterAnnotation(LoginUser.class);
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer container,
                                  NativeWebRequest request, WebDataBinderFactory factory) throws Exception {

        String token = request.getHeader(LOGIN_TOKEN_KEY);
        if(token == null || token.isEmpty()){
            return null;
        }
        //token时效校验，未过期就重续时间
        return UserTokenManager.getOpenId(token);
    }
}

package kr.co.antoon.oauth.config;

import kr.co.antoon.oauth.dto.AuthInfo;
import kr.co.antoon.user.domain.vo.Role;
import org.springframework.core.MethodParameter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Component
public class LoginUserArgumentResolver implements HandlerMethodArgumentResolver {

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        boolean isLoginUserAnnotation = parameter.getParameterAnnotation(AuthUser.class) != null;
        boolean isAuthInfo = parameter.getParameterType().equals(AuthInfo.class);
        return isLoginUserAnnotation && isAuthInfo;
    }

    @Override
    public Object resolveArgument(MethodParameter parameter,
                                  ModelAndViewContainer mavContainer,
                                  NativeWebRequest webRequest,
                                  WebDataBinderFactory binderFactory) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (Objects.equals(authentication.getPrincipal(), "anonymousUser")) {
            return null;
        }
        return new AuthInfo(
                Long.valueOf(String.valueOf(authentication.getPrincipal())),
                RolesFromAuthorities(authentication.getAuthorities())
        );
    }

    private List<Role> RolesFromAuthorities(Collection<? extends GrantedAuthority> authorities) {
        return authorities.stream()
                .map(authority -> Role.of(authority.getAuthority()))
                .collect(Collectors.toList());
    }
}
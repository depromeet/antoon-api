package kr.co.antoon.oauth.config;

import java.util.Collection;
import java.util.List;
import java.util.Objects;
import kr.co.antoon.error.exception.oauth.NotExistsOauthInfoException;
import kr.co.antoon.oauth.dto.AuthInfo;
import kr.co.antoon.user.domain.vo.Role;
import org.springframework.core.MethodParameter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

@Component
public class LoginUserArgumentResolver implements HandlerMethodArgumentResolver {

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        var isLoginUserAnnotation = parameter.getParameterAnnotation(AuthUser.class) != null;
        var isAuthInfo = parameter.getParameterType().equals(AuthInfo.class);

        return isLoginUserAnnotation && isAuthInfo;
    }

    /**
     * User should can get data from whitelist path with no authentication.
     *
     * @see kr.co.antoon.character.presentation.CharacterController#getCharacterTopUpper(type,
     *      info)
     */
    @Override
    public Object resolveArgument(
        MethodParameter parameter,
        ModelAndViewContainer mavContainer,
        NativeWebRequest webRequest,
        WebDataBinderFactory binderFactory
    ) {
        var authentication = SecurityContextHolder.getContext().getAuthentication();

        Boolean isAnonymousUser = Objects.equals(authentication.getPrincipal(), "anonymousUser");

        /* @TODO Whitelist check should be refactor for scale out the whitelists. */
        String whiteListPattern = ".+/top-ranks/characters$";
        Boolean isWhiteList = webRequest.getDescription(false).matches(whiteListPattern);

        if (Boolean.TRUE.equals(isAnonymousUser) && Boolean.TRUE.equals(isWhiteList)) {
            return null;
        }

        if (Boolean.TRUE.equals(isAnonymousUser)) {
            throw new NotExistsOauthInfoException();
        }

        return new AuthInfo(
            Long.valueOf(String.valueOf(authentication.getPrincipal())),
            rolesFromAuthorities(authentication.getAuthorities())
        );
    }

    private List<Role> rolesFromAuthorities(Collection<? extends GrantedAuthority> authorities) {
        return authorities.stream().map(authority -> Role.of(authority.getAuthority())).toList();
    }
}

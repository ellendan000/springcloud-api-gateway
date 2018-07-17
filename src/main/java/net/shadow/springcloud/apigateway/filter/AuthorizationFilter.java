package net.shadow.springcloud.apigateway.filter;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import lombok.extern.slf4j.Slf4j;
import net.shadow.springcloud.apigateway.domain.service.ApiAuthorizationService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

import static com.google.common.collect.Lists.newArrayList;
import static java.util.Collections.emptyList;

@Slf4j
@Component
public class AuthorizationFilter extends ZuulFilter {
    private static final int FILTER_ORDER = 0;
    private static final String PRIVILEGE_KEY_IN_HEADER = "privileges";
    private static final String PRIVILEGE_SEPARATOR = ",";

    @Autowired
    private ApiAuthorizationService apiAuthorizationService;

    @Override
    public String filterType() {
        return "pre";
    }

    @Override
    public int filterOrder() {
        return FILTER_ORDER;
    }

    @Override
    public boolean shouldFilter() {
        return true;
    }

    @Override
    public Object run() {
        verifyAuthorization(getPrivileges());
        return null;
    }

    private void verifyAuthorization(List<String> privileges) {
        RequestContext ctx = RequestContext.getCurrentContext();
        HttpServletRequest request = ctx.getRequest();

        log.info("{} request to {}", request.getMethod(), request.getRequestURL());
        boolean isPassed = apiAuthorizationService.verify(privileges, request);
        if (!isPassed) {
            ctx.setSendZuulResponse(false);
            ctx.setResponseStatusCode(HttpStatus.FORBIDDEN.value());
        }
    }

    private List<String> getPrivileges() {
        RequestContext ctx = RequestContext.getCurrentContext();
        String privileges = ctx.getRequest().getHeader(PRIVILEGE_KEY_IN_HEADER);
        return StringUtils.isBlank(privileges) ? emptyList() : newArrayList(privileges.split(PRIVILEGE_SEPARATOR));
    }
}

package net.shadow.springcloud.apigateway.domain.service;

import lombok.extern.slf4j.Slf4j;
import net.shadow.springcloud.apigateway.domain.entity.ApiAuthorization;
import net.shadow.springcloud.apigateway.domain.repository.ApiAuthorizationRepositoryWrapper;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;

import javax.servlet.http.HttpServletRequest;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
@Slf4j
public class ApiAuthorizationService {
    @Autowired
    private ApiAuthorizationRepositoryWrapper repositoryWrapper;

    public boolean verify(List<String> privileges, HttpServletRequest request) {
        ApiAuthorization apiAuthorization = getMatch(request);
        if (apiAuthorization == null) {
            log.info("No exists configuration for api authorization");
            return false;
        }
        log.info("Matched api authorization configuration id: {}", apiAuthorization.getId());
        if (CollectionUtils.isEmpty(apiAuthorization.getAuthorities())) {
            return true;
        }

        Collection intersection = CollectionUtils.intersection(
                apiAuthorization.getAuthorities(), privileges
        );
        if (CollectionUtils.isNotEmpty(intersection)) {
            return true;
        }

        log.warn("Current user has not permission. {} not in {}", privileges,
                apiAuthorization.getAuthorities());
        return false;
    }

    private ApiAuthorization getMatch(HttpServletRequest request) {
        Map<RequestMappingInfo, ApiAuthorization> configurationItems = repositoryWrapper.getConfigurations();
        List<RequestMappingInfo> matches = configurationItems.keySet().stream()
                .filter(item -> item.getMatchingCondition(request) != null)
                .collect(Collectors.toList());

        if (CollectionUtils.isEmpty(matches)) {
            return null;
        }

        matches.sort(getMappingComparator(request));
        return configurationItems.get(matches.get(0));
    }

    private Comparator<RequestMappingInfo> getMappingComparator(final HttpServletRequest request) {
        return (info1, info2) -> info1.compareTo(info2, request);
    }
}

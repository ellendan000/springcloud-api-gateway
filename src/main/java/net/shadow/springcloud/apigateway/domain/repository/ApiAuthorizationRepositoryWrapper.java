package net.shadow.springcloud.apigateway.domain.repository;

import net.shadow.springcloud.apigateway.domain.entity.ApiAuthorization;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import static net.shadow.springcloud.apigateway.configuration.CachingConfiguration.ALL_KEY;
import static net.shadow.springcloud.apigateway.configuration.CachingConfiguration.API_AUTHORITIES_CACHE_NAME;

@Component
public class ApiAuthorizationRepositoryWrapper {
    @Autowired
    private ApiAuthorizationRepository apiAuthorizationRepository;

    @Cacheable(value = API_AUTHORITIES_CACHE_NAME, key = "'" + ALL_KEY + "'")
    public Map<RequestMappingInfo, ApiAuthorization> getConfigurations() {
        List<ApiAuthorization> configuration = apiAuthorizationRepository.findAll();
        if (CollectionUtils.isEmpty(configuration)) {
            return Collections.emptyMap();
        }

        return configuration.stream()
                .collect(Collectors.toMap(
                        covertToMapping(),
                        Function.identity()
                ));
    }

    @CacheEvict(value = API_AUTHORITIES_CACHE_NAME, allEntries = true)
    public void removeConfigurations() {
    }

    private Function<ApiAuthorization, RequestMappingInfo> covertToMapping() {
        return apiAuthorization -> {
            RequestMappingInfo.Builder builder = RequestMappingInfo
                    .paths(apiAuthorization.getPath());

            if (apiAuthorization.getMethod() != null) {
                builder.methods(apiAuthorization.getMethod());
            }

            if (CollectionUtils.isNotEmpty(apiAuthorization.getParams())) {
                builder.params(apiAuthorization.getParams().toArray(new String[0]));
            }

            if (CollectionUtils.isNotEmpty(apiAuthorization.getHeaders())) {
                builder.headers(apiAuthorization.getHeaders().toArray(new String[0]));
            }

            return builder.build();
        };
    }
}

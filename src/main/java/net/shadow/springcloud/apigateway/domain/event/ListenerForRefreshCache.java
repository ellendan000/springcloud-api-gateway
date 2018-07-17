package net.shadow.springcloud.apigateway.domain.event;

import net.shadow.springcloud.apigateway.domain.repository.ApiAuthorizationRepositoryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.cloud.context.scope.refresh.RefreshScopeRefreshedEvent;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

@Component
public class ListenerForRefreshCache implements ApplicationListener {

    @Autowired
    private ApiAuthorizationRepositoryWrapper apiAuthorizationRepositoryWrapper;

    @Override
    public void onApplicationEvent(ApplicationEvent event) {
        if (event instanceof ApplicationReadyEvent
                || event instanceof RefreshScopeRefreshedEvent) {
            apiAuthorizationRepositoryWrapper.removeConfigurations();
            apiAuthorizationRepositoryWrapper.getConfigurations();
        }
    }
}

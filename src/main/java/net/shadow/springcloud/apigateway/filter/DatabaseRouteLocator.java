package net.shadow.springcloud.apigateway.filter;

import net.shadow.springcloud.apigateway.domain.entity.Route;
import net.shadow.springcloud.apigateway.domain.repository.RouteRepository;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.web.ServerProperties;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.netflix.zuul.filters.ZuulProperties;
import org.springframework.cloud.netflix.zuul.filters.discovery.DiscoveryClientRouteLocator;
import org.springframework.cloud.netflix.zuul.filters.discovery.ServiceRouteMapper;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import java.util.LinkedHashMap;
import java.util.List;

import static com.google.common.collect.Maps.newLinkedHashMap;

@Component
public class DatabaseRouteLocator extends DiscoveryClientRouteLocator implements ApplicationContextAware {
    private ApplicationContext applicationContext;

    @SuppressWarnings("PMD")
    @Autowired
    public DatabaseRouteLocator(ServerProperties serverProperties,
                                DiscoveryClient discovery,
                                ZuulProperties zuulProperties,
                                ServiceRouteMapper serviceRouteMapper) {
        super(serverProperties.getServletPrefix(), discovery, zuulProperties, serviceRouteMapper);
    }

    @Override
    protected LinkedHashMap<String, ZuulProperties.ZuulRoute> locateRoutes() {
        LinkedHashMap<String, ZuulProperties.ZuulRoute> routesMap = newLinkedHashMap(super.locateRoutes());

        RouteRepository zuulRouteRepository = applicationContext.getBean(RouteRepository.class);
        List<Route> zuulRoutes = zuulRouteRepository.findAll();
        zuulRoutes.forEach(route ->
                routesMap.put(route.getPath(), route)
        );
        return routesMap;
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
}

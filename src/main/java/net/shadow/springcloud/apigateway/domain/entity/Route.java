package net.shadow.springcloud.apigateway.domain.entity;

import lombok.NoArgsConstructor;
import org.springframework.cloud.netflix.zuul.filters.ZuulProperties;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Set;

@NoArgsConstructor
@Entity
@Table(name = "route")
public class Route extends ZuulProperties.ZuulRoute {

    public Route(String id, String path, String serviceId, String url,
                 boolean stripPrefix, Boolean retryable, Set<String> sensitiveHeaders) {
        super(id, path, serviceId, url, stripPrefix, retryable, sensitiveHeaders);
    }

    @Id
    public String getId() {
        return super.getId();
    }

    @Column(name = "path")
    public String getPath() {
        return super.getPath();
    }

    @Column(name = "service_id")
    public String getServiceId() {
        return super.getServiceId();
    }

    @Column(name = "url")
    public String getUrl() {
        return super.getUrl();
    }

    @Column(name = "strip_prefix")
    public boolean isStripPrefix() {
        return super.isStripPrefix();
    }

    @Column(name = "retryable")
    public Boolean getRetryable() {
        return super.getRetryable();
    }
}

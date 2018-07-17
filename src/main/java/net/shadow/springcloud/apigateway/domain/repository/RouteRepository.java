package net.shadow.springcloud.apigateway.domain.repository;

import net.shadow.springcloud.apigateway.domain.entity.Route;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RouteRepository extends JpaRepository<Route, String> {
}

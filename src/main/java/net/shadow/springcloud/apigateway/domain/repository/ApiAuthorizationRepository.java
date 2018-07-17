package net.shadow.springcloud.apigateway.domain.repository;

import net.shadow.springcloud.apigateway.domain.entity.ApiAuthorization;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ApiAuthorizationRepository extends JpaRepository<ApiAuthorization, Integer> {
}

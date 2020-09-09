package com.hx.webflux.reposiroty;

import com.hx.webflux.domain.entity.PmRole;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

/**
 * 角色dao
 *
 * @author hx
 */
public interface PmRoleRepository extends ReactiveCrudRepository<PmRole, Long> {
}

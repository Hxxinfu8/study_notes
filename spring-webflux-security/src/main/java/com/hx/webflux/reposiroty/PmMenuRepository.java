package com.hx.webflux.reposiroty;

import com.hx.webflux.domain.entity.PmMenu;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

/**
 * 菜单dao
 *
 * @author hx
 */
public interface PmMenuRepository extends ReactiveCrudRepository<PmMenu, Long> {
}

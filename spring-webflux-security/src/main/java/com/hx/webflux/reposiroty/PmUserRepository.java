package com.hx.webflux.reposiroty;

import com.hx.webflux.domain.entity.PmUser;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * 用户dao
 *
 * @author hx
 */
public interface PmUserRepository extends ReactiveCrudRepository<PmUser, Long> {
    /**
     * 根据账号查询
     *
     * @return pmUser
     */
    Mono<PmUser> findByAccount(String account);

    /**
     * 根据用户id查询所有权限
     *
     * @param userId 用户id
     * @return 权限集合
     */
    @Query("SELECT m.menu_url FROM pm_menu m LEFT JOIN pm_role r ON JSON_CONTAINS(r.menu_id, JSON_ARRAY(m.id)) LEFT JOIN pm_user u ON u.role_id = r.id WHERE r.is_deleted = 0 AND m.is_deleted = 0 AND u.id = :userId")
    Flux<String> getMenusByUserId(@Param("userId") Long userId);
}

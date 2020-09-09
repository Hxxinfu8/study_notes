package com.hx.webflux.reposiroty;

import com.hx.webflux.domain.entity.PmDept;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

/**
 * 部门dao
 *
 * @author hx
 */
public interface PmDeptRepository extends ReactiveCrudRepository<PmDept, Long> {

}

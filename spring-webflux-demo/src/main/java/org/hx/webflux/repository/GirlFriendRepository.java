package org.hx.webflux.repository;

import org.hx.webflux.entity.GirlFriend;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Mono;

/**
 * @author hx
 */
public interface GirlFriendRepository extends ReactiveMongoRepository<GirlFriend, String> {
    /**
     * 根据名字查询
     * @param name
     * @return
     */
    Mono<GirlFriend> findByName(String name);


    /**
     * 根据名字删除
     * @param name
     * @return
     */
    Mono<Long> deleteByName(String name);
}

package org.hx.webflux.service;

import org.hx.webflux.entity.GirlFriend;
import org.hx.webflux.repository.GirlFriendRepository;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.annotation.Resource;
import java.time.LocalDateTime;

/**
 * @author hx
 */
@Service
public class GirlFriendService {
    @Resource
    private GirlFriendRepository girlFriendRepository;


    public Flux<GirlFriend> findAll() {
        return girlFriendRepository.findAll();
    }

    public Mono<GirlFriend> findByName(String name) {
        return girlFriendRepository.findByName(name);
    }

    public Mono<Long> deleteByName(String name) {
        return girlFriendRepository.deleteByName(name);
    }

    public Mono<GirlFriend> add(GirlFriend friend) {
        friend.setBirthday(LocalDateTime.now());
        return girlFriendRepository.save(friend)
                .onErrorResume(e -> girlFriendRepository.findByName(friend.getName())
                .flatMap(originalFriend -> {
                    friend.setId(originalFriend.getId());
                    return girlFriendRepository.save(friend);
                }));
    }

}

package org.hx.webflux.controller;

import org.hx.webflux.entity.GirlFriend;
import org.hx.webflux.service.GirlFriendService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;

/**
 * @author hx
 */
@RestController
@RequestMapping("/girlFriend")
public class GirlFriendController {
    @Autowired
    private GirlFriendService girlFriendService;

    @GetMapping(value = "", produces = MediaType.APPLICATION_STREAM_JSON_VALUE)
    public Flux<GirlFriend> findAll() {
        return girlFriendService.findAll().delayElements(Duration.ofSeconds(1));
    }

    @GetMapping("/{name}")
    public Mono<GirlFriend> getByName(@PathVariable String name) {
        return girlFriendService.findByName(name);
    }

    @PostMapping("")
    public Mono<GirlFriend> add(GirlFriend girlFriend) {
        return girlFriendService.add(girlFriend);
    }

    @DeleteMapping("/{name}")
    public Mono<Long> deleteByName(@PathVariable String name) {
        return this.girlFriendService.deleteByName(name);
    }

}

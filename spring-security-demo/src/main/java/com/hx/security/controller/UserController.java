package com.hx.security.controller;


import com.hx.security.domain.dto.UserDTO;
import com.hx.security.domain.entity.User;
import com.hx.security.domain.vo.ResultVO;
import com.hx.security.service.IUserService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

/**
 * <p>
 * 用户前端控制器
 * </p>
 *
 * @author hx
 * @since 2020-08-31
 */
@RestController
@RequestMapping("/user")
public class UserController {
    private final IUserService iUserService;

    private final PasswordEncoder passwordEncoder;

    public UserController(IUserService iUserService, PasswordEncoder passwordEncoder) {
        this.iUserService = iUserService;
        this.passwordEncoder = passwordEncoder;
    }

    @PostMapping("/register")
    public ResultVO register(@RequestBody UserDTO userDTO) {
        User user = new User();
        user.setUsername(userDTO.getUsername());
        user.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        user.setCreateTime(LocalDateTime.now());
        user.setIsDeleted(false);
        return ResultVO.success(iUserService.save(user));
    }

    @PreAuthorize("hasAuthority('user:list')")
    @GetMapping("/list")
    public ResultVO listUsers() {
        return ResultVO.success(iUserService.list());
    }
}

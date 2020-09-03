package com.hx.security.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.hx.security.domain.entity.User;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author hx
 * @since 2020-08-31
 */
public interface IUserService extends IService<User> {
    /**
     * 根据姓名查询
     * @param username
     * @return
     */
    User getUserByUsername(String username);
}

package com.hx.security.service;

import com.hx.security.domain.entity.Menu;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Set;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author hx
 * @since 2020-08-31
 */
public interface IMenuService extends IService<Menu> {
    /**
     * 查询用户所有权限
     * @param userId
     * @return
     */
    Set<String> getMenusByUserId(Long userId);
}

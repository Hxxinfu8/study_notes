package com.hx.security.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hx.security.domain.entity.Menu;

import java.util.Set;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author hx
 * @since 2020-08-31
 */
public interface MenuMapper extends BaseMapper<Menu> {
    /**
     * 查询用户所有权限
     * @param userId 用户id
     * @return 权限列表
     */
    Set<String> selectMenuByUserId(Long userId);
}

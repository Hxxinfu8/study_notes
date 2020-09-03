package com.hx.security.mapper;

import com.hx.security.domain.entity.User;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author hx
 * @since 2020-08-31
 */
public interface UserMapper extends BaseMapper<User> {
    /**
     * 根据用户id获取所属角色名
     * @param userId
     * @return
     */
    List<String> getRoleNameByUserId(@Param("userId") Integer userId);
}

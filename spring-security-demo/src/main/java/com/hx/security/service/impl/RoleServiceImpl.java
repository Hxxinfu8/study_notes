package com.hx.security.service.impl;

import com.hx.security.domain.entity.Role;
import com.hx.security.mapper.RoleMapper;
import com.hx.security.service.IRoleService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author hx
 * @since 2020-08-31
 */
@Service
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements IRoleService {

}

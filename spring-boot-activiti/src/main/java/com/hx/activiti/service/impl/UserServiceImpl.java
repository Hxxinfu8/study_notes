package com.hx.activiti.service.impl;

import com.hx.activiti.domain.entity.User;
import com.hx.activiti.mapper.UserMapper;
import com.hx.activiti.service.IUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author hx
 * @since 2020-09-28
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {

}

package com.hx.security.service.impl;

import com.hx.security.domain.entity.Menu;
import com.hx.security.mapper.MenuMapper;
import com.hx.security.service.IMenuService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.Set;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author hx
 * @since 2020-08-31
 */
@Service
public class MenuServiceImpl extends ServiceImpl<MenuMapper, Menu> implements IMenuService {

    @Override
    public Set<String> getMenusByUserId(Long userId) {
        return this.baseMapper.selectMenuByUserId(userId);
    }
}

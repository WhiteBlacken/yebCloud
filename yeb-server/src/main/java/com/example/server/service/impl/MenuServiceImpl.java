package com.example.server.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.server.mapper.MenuMapper;
import com.example.server.pojo.Admin;
import com.example.server.pojo.Menu;
import com.example.server.service.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.xml.bind.ValidationEvent;
import java.util.Collections;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author qxy
 * @since 2022-03-20
 */
@Service
public class MenuServiceImpl extends ServiceImpl<MenuMapper, Menu> implements MenuService {

    @Autowired
    private MenuMapper menuMapper;


    @Autowired
    private RedisTemplate<String,Object> redisTemplate;
    /**
     * 通过用户id查询菜单列表
     * @return
     */
    @Override
    public List<Menu> getMenusByAdminId() {
        Integer id = ((Admin) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getId();
        //减少访问menu的访问数据库次数，第一次查出时放入redis缓存
        ValueOperations<String,Object> valueOperations = redisTemplate.opsForValue();
        List<Menu> menus  = (List<Menu>) valueOperations.get("menu_" + id);
        if (CollectionUtils.isEmpty(menus)){
            menus = menuMapper.getMenusByAdminId(id);
            valueOperations.set("menu_" + id,menus);
        }
        return menus;
    }

    /**
     * 角色-菜单列表匹配项
     * @return
     */
    @Override
    public List<Menu> getMenusWithRole() {
        System.out.println("执行了getMenusWithRole");
        System.out.println("执行结果为："+menuMapper.getMenusWithRole());
        return menuMapper.getMenusWithRole();
    }
}

package com.example.server.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.server.pojo.Role;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author qxy
 * @since 2022-03-20
 */
public interface RoleMapper extends BaseMapper<Role> {

    List<Role> getRolesByAdminId(Integer adminId);
}

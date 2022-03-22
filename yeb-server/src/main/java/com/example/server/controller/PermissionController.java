package com.example.server.controller;


import com.example.server.pojo.RespBean;
import com.example.server.pojo.Role;
import com.example.server.service.RoleService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

/**
 * @Author qxy
 * @Date 2022/3/21 20:10
 * @Version 1.0
 */
@RestController
@RequestMapping("/system/basic/permission")
public class PermissionController {

    @Autowired
    private RoleService roleService;

    @ApiOperation(value = "获取所有角色")
    @GetMapping("/")
    public List<Role> getAllRoles(){
        return roleService.list();
    }

    @ApiOperation(value = "新增角色")
    @PostMapping("/")
    public RespBean addRole(@RequestBody Role role){
        //判断是否以ROLE_开头
        if(!role.getName().startsWith("ROLE_")){
            role.setName("ROLE_"+role.getName());
        }
        if(roleService.save(role)){
            return RespBean.success("新增成功");
        }
        return RespBean.error("新增失败");
    }

    @ApiOperation(value = "修改角色")
    @PutMapping("/")
    public RespBean updateRole(@RequestBody Role role){
        if(roleService.updateById(role)){
            return RespBean.success("修改成功");
        }
        return RespBean.error("修改失败");
    }

    @ApiOperation(value = "删除角色")
    @DeleteMapping("/{id}")
    public RespBean deleteRole(@PathVariable Integer id){
        if(roleService.removeById(id)){
            return RespBean.success("删除成功");
        }
        return RespBean.error("删除失败");
    }

    @ApiOperation(value = "批量删除角色")
    @DeleteMapping("/")
    public RespBean deleteRoles(Integer[] ids){
        if(roleService.removeByIds(Arrays.asList(ids))){
            return RespBean.success("删除成功");
        }
        return RespBean.error("删除失败");
    }



}

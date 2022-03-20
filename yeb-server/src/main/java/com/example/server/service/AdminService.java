package com.example.server.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.server.pojo.Admin;
import com.example.server.pojo.RespBean;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author qxy
 * @since 2022-03-20
 */
public interface AdminService extends IService<Admin> {

    RespBean login(String username, String password, HttpServletRequest request);

    Admin getAdminByUsername(String username);
}

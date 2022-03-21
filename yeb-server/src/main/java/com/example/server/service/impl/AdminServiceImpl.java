package com.example.server.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.server.config.security.Component.JwtTokenUtil;
import com.example.server.mapper.AdminMapper;
import com.example.server.mapper.RoleMapper;
import com.example.server.pojo.Admin;
import com.example.server.pojo.RespBean;
import com.example.server.pojo.Role;
import com.example.server.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author qxy
 * @since 2022-03-20
 */
@Service
public class AdminServiceImpl extends ServiceImpl<AdminMapper, Admin> implements AdminService {

    //spring security里的东西
    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Value("${jwt.tokenHead}")
    private String tokenHead;

    @Autowired
    private AdminMapper adminMapper;

    @Autowired
    private RoleMapper roleMapper;

    /**
     * 登录
     *
     * @param username
     * @param password
     * @param code
     * @param request
     * @return
     */
    @Override
    public RespBean login(String username, String password, String code,HttpServletRequest request) {
        //用户的验证码与session中进行比较
        String captcha = (String) request.getSession().getAttribute("captcha");
        if(StringUtils.isEmpty(code)||!captcha.equalsIgnoreCase(code)){
            return RespBean.error("验证码输入错误,请重新输入");
        }
        //判断登录是否合法
        UserDetails details = userDetailsService.loadUserByUsername(username);//需要重写，去数据库里查
        System.out.println("detail:"+details);
        System.out.println("密码是否相符："+passwordEncoder.matches(password, details.getPassword()));
        if (null == details || !passwordEncoder.matches(password, details.getPassword())) {
            return RespBean.error("用户名或密码不正确");
        }
        if (!details.isEnabled()) {
            return RespBean.error("账号被禁用，请联系管理员!");
        }
        //登录成功后将对象放在spring security全文中，便于之后使用
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(details, null, details.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        //生成jwt token给前端进行使用
        String token = jwtTokenUtil.generateToken(details);
        Map<String, String> tokenMap = new HashMap<>();
        tokenMap.put("token", token);
        tokenMap.put("tokenHead", tokenHead);
        return RespBean.success("登录成功", tokenMap);
    }

    /**
     * 根据用户名获取用户
     *
     * @param username
     * @return
     */
    @Override
    public Admin getAdminByUsername(String username) {

        return adminMapper.selectOne(new QueryWrapper<Admin>().eq("username", username)
                .eq("enabled", true));
    }

    /**
     * 根据用户id查询角色
     * @param adminId
     * @return
     */
    @Override
    public List<Role> getRoles(Integer adminId) {

        return roleMapper.getRolesByAdminId(adminId);
    }
}

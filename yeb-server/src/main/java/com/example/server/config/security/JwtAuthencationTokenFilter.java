package com.example.server.config.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * jwt登录授权过滤器
 *
 * @Author qxy
 * @Date 2022/3/20 23:10
 * @Version 1.0
 */

public class JwtAuthencationTokenFilter extends OncePerRequestFilter {
    @Value("${jwt.tokenHeader}")
    private String tokenHeader;

    @Value("${jwt.tokenHead}")
    private String tokenHead;
    //使用注入和导包有什么区别
    @Autowired
    private JwtTokenUtil jwtTokenUtil;
    @Autowired
    private UserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        System.out.println("tokenHeader:"+tokenHeader);
        String authHeader = request.getHeader(tokenHeader);
        System.out.println("authHeader:"+authHeader);
        System.out.println("这里会执行1");
        //token存在，且以head开头
        if (null != authHeader && authHeader.startsWith(tokenHead)) {
            System.out.println("这里会执行2");
            String authToken = authHeader.substring(tokenHead.length());
            String username = jwtTokenUtil.getUserNameFromToken(authToken);
            //token存在用户名，但是未登录
            if(null!=username&&null== SecurityContextHolder.getContext().getAuthentication()){
                System.out.println("token存在用户名，但是未登录");
                //登录
                UserDetails userDetails = userDetailsService.loadUserByUsername(username);
                //验证token是否有效
                if(jwtTokenUtil.validateToken(authToken,userDetails)){
                    UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userDetails,null,userDetails.getAuthorities());
                    //为什么比登录多了这一步
                    authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(authenticationToken);

                }

            }
        }
        //实际上就做了一件事，当token中携带信息，但是spring security未认为登录，给他自动登陆了
        filterChain.doFilter(request,response);
    }
}

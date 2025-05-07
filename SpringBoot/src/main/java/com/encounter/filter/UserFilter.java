package com.encounter.filter;


import com.alibaba.druid.util.StringUtils;
import com.encounter.bean.BsUser;
import com.encounter.service.UserService;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletRequestWrapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;


@WebFilter("/auth/*")
@Slf4j
public class UserFilter implements Filter
    {
        
        @Autowired
        private StringRedisTemplate redisTemplate;
        
        @Autowired
        private UserService userService;
        
        @Override
        public void init(FilterConfig var1) throws ServletException
            {
            
            }
        
        @Override
        public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException
            {
                //log.info("走了过滤器。。。。。。。");
                String token = servletRequest.getParameter("token");
                if (StringUtils.isEmpty(token))
                    {
                        //log.info("token为空");
                        PrintWriter writer = null;
                        try
                            {
                                writer = servletResponse.getWriter();
                                writer.print("-1");
                            }
                        catch (IOException e)
                            {
                            
                            }
                        finally
                            {
                                if (writer != null)
                                    {
                                        
                                        writer.close();
                                    }
                            }
                        return;
                        
                    }
                String username = userService.getUserNameByToken(token);
                
                //        String username = redisTemplate.opsForValue().get("user_token:" + token);
                if (StringUtils.isEmpty(token) || StringUtils.isEmpty(username))
                    {
                        log.info("获取用户数据失败，登录失败，获取到的token：" + token + ",redis查出来的数据：" + username);
                        PrintWriter writer = null;
                        try
                            {
                                writer = servletResponse.getWriter();
                                writer.print("-1");
                            }
                        catch (IOException e)
                            {
                            
                            }
                        finally
                            {
                                if (writer != null)
                                    {
                                        
                                        writer.close();
                                    }
                            }
                        return;
                    }
                List<BsUser> userList = userService.getUserByUname(username);
                
                
                HashMap m = new HashMap(servletRequest.getParameterMap());
                m.put("uname", username);
                if (userList != null && userList.size() > 0)
                    {
                        m.put("uid", userList.get(0).getId());
                    }
                HttpServletRequest req = (HttpServletRequest) servletRequest;
                ParameterRequestWrapper wrapRequest = new ParameterRequestWrapper(req, m);
                servletRequest = wrapRequest;
                filterChain.doFilter(servletRequest, servletResponse);
            }
        
        @Override
        public void destroy()
            {
            
            }
        
        @SuppressWarnings("unchecked")
        public class ParameterRequestWrapper extends HttpServletRequestWrapper
            {
                private final Map params;
                
                public ParameterRequestWrapper(HttpServletRequest request, Map newParams)
                    {
                        super(request);
                        this.params = newParams;
                    }
                
                @Override
                public Map getParameterMap()
                    {
                        return params;
                    }
                
                @Override
                public Enumeration getParameterNames()
                    {
                        Vector l = new Vector(params.keySet());
                        return l.elements();
                    }
                
                @Override
                public String[] getParameterValues(String name)
                    {
                        Object v = params.get(name);
                        if (v == null)
                            {
                                return null;
                            }
                        else if (v instanceof String[])
                            {
                                return (String[]) v;
                            }
                        else if (v instanceof String)
                            {
                                return new String[]{(String) v};
                            }
                        else
                            {
                                return new String[]{v.toString()};
                            }
                    }
            }
    }

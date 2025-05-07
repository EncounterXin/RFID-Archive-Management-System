package com.encounter.controller;

import com.alibaba.druid.util.StringUtils;
import com.encounter.bean.BsPhone;
import com.encounter.bean.BsScore;
import com.encounter.bean.BsUser;
import com.encounter.bean.ResultBean;
import com.encounter.service.UserService;
import com.github.pagehelper.PageInfo;
import jakarta.annotation.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


/**
 * 用户控制器
 *
 * @author Encounter
 * @since 2025/05/07
 */
@RestController
public class UserController
    {
        @Resource
        UserService userService;
        @Autowired
        private StringRedisTemplate redisTemplate;
        
        /**
         * 登录
         *
         * @param uname uname
         * @param pwd   pwd
         * @return {@link ResultBean }
         */
        @RequestMapping("/user/login")
        public ResultBean login(String uname, String pwd)
            {
                
                if (StringUtils.isEmpty(uname) || StringUtils.isEmpty(pwd))
                    {
                        return ResultBean.returnError().pushMessage("请将用户名和密码输入完整");
                    }
                
                List<BsUser> userList = userService.getUserByUname(uname);
                if (userList == null || userList.isEmpty())
                    {
                        return ResultBean.returnError().pushMessage("用户不存在");
                    }
                
                if (!userList.get(0).getPwd().equals(pwd))
                    {
                        return ResultBean.returnError().pushMessage("用户名或密码不正确");
                    }
                String uuid = UUID.randomUUID().toString();
                
                
                //        redisTemplate.opsForValue().set("user_token:" + uuid, uname);
                userService.loginToken(uuid, userList.get(0).getId());
                
                return ResultBean.returnOk().pushData("token", uuid);
            }
        
        /**
         * 注册
         *
         * @param uname uname
         * @param pwd   pwd
         * @param age   年龄
         * @param sex   性
         * @param major 主要
         * @param grade 年级
         * @param email 电子邮件
         * @param phone 电话
         * @return {@link ResultBean }
         */
        @RequestMapping("/user/webRegister")
        public ResultBean register(String uname, String pwd, String age, String sex, String major, String grade, String email, String phone)
            {
                
                if (StringUtils.isEmpty(phone))
                    {
                        return ResultBean.returnError("请输入手机号");
                    }
                try
                    {
                        Integer.parseInt(age);
                    }
                catch (NumberFormatException e)
                    {
                        return ResultBean.returnError("年龄必须为数字");
                    }
                //校验用户名是否存在
                List<BsUser> userByUname = userService.getUserByUname(uname);
                if (userByUname != null && !userByUname.isEmpty())
                    {
                        return ResultBean.returnError().pushMessage("用户名已经存在，请重新输入");
                    }
                //插入用户表
                BsUser user = new BsUser();
                user.setPwd(pwd);
                user.setUname(uname);
                user.setIdentity("1");
                user.setAge(Integer.parseInt(age));
                user.setSex(sex);
                user.setMajor(major);
                user.setGrade(grade);
                user.setEmail(email);
                user.setBookCount(0);
                Integer uid = userService.insertUserBean(user);
                
                //        Integer uid = userService.insertUser(uname,pwd);
                //插入token表
                String token = UUID.randomUUID().toString();
                userService.insertToken(uid, token);
                
                //插入手机号表
                BsPhone bsPhone = new BsPhone();
                bsPhone.setUid(uid);
                bsPhone.setPhone(phone);
                userService.insertPhone(bsPhone);
                
                //插入信誉分表
                userService.insertScore(uid);
                return ResultBean.returnOk().pushData("token", token).pushMessage("注册成功");
            }
        
        /**
         * 选择用户列表
         *
         * @param page  页
         * @param limit 限制
         * @return {@link ResultBean }
         */
        @RequestMapping("/auth/selectUserList")
        public ResultBean selectUserList(Integer page, Integer limit)
            {
                
                if (page == null)
                    {
                        page = 1;
                    }
                if (limit == null)
                    {
                        limit = 10;
                    }
                
                PageInfo<BsUser> pageInfo = userService.selectUserListByPage(page, limit);
                long total = pageInfo.getTotal();
                List<BsUser> list = pageInfo.getList();
                
                List<BsUser> returnList = new ArrayList<>();
                //获取信誉分
                for (BsUser user : list)
                    {
                        List<BsScore> scores = userService.selectUserScoreByUid(user.getId());
                        if (scores != null && !scores.isEmpty())
                            {
                                
                                user.setScore(scores.get(0).getScore());
                                returnList.add(user);
                            }
                    }
                
                return ResultBean.returnOk().pushData("total", total).pushData("list", returnList);
            }
        
        /**
         * 删除用户
         *
         * @param delUid del uid
         * @return {@link ResultBean }
         */
        @RequestMapping("/auth/deleteUser")
        public ResultBean deleteUser(Integer delUid)
            {
                
                userService.deleteUserById(delUid);
                
                return ResultBean.returnOk().pushMessage("删除成功");
            }
        
        /**
         * 通过 UID 更新 pwd
         *
         * @param uid uid
         * @param pwd pwd
         * @return {@link ResultBean }
         */
        @RequestMapping("/auth/updatePwdByUid")
        public ResultBean updatePwdByUid(Integer uid, String pwd)
            {
                
                BsUser bsUser = userService.selectUserByUid(uid);
                if (bsUser == null)
                    {
                        return ResultBean.returnError().pushMessage("用户不存在");
                    }
                userService.updatePwdByUid(uid, pwd);
                
                return ResultBean.returnOk().pushMessage("修改成功");
            }
        
        /**
         * 更改 pwd
         *
         * @param token 令 牌
         * @return {@link ResultBean }
         */
        @RequestMapping("/auth/info")
        public ResultBean changePwd(String token)
            {
                BsUser user = userService.getUserByToken(token);
                if (user == null)
                    {
                        return ResultBean.returnError("用户不存在");
                    }
                
                String role = "";
                
                if ("0".equals(user.getIdentity()))
                    {
                        role = "man";
                    }
                else
                    {
                        role = "pan";
                    }
                
                return ResultBean.returnOk().pushData("name", user.getUname()).pushData("avatar", "https://scwtest20200706.oss-cn-beijing.aliyuncs.com/jd.jpg")
                        .pushData("roles", role);
            }
        
        
        /**
         * 按条件获取用户列表
         *
         * @param username 用户名
         * @param email    电子邮件
         * @param major    主要
         * @param page     页
         * @param limit    限制
         * @return {@link ResultBean }
         */
        @RequestMapping("/auth/getUserListByCondition")
        public ResultBean getUserListByCondition(String username, String email, String major, Integer page, Integer limit)
            {
                if (page == null)
                    {
                        page = 1;
                    }
                if (limit == null)
                    {
                        limit = 10;
                    }
                
                PageInfo<BsUser> pageInfo = userService.getUserListByCondition(username, email, major, page, limit);
                long total = pageInfo.getTotal();
                List<BsUser> list = pageInfo.getList();
                
                return ResultBean.returnOk().pushData("total", total).pushData("list", list);
            }
        
        /**
         * 通过 uid 获取用户信息
         *
         * @param userUId 用户 uid
         * @return {@link ResultBean }
         */
        @RequestMapping("/auth/getUserInfoByUid")
        public ResultBean getUserInfoByUid(Integer userUId)
            {
                BsUser userById = userService.getUserById(userUId);
                return ResultBean.returnOk().pushData("data", userById);
            }
        
        /**
         * 更新用户所有信息
         *
         * @param changeId 更改 ID
         * @param username 用户名
         * @param pwd      密码
         * @param age      年龄
         * @param sex      性别
         * @param major    主要
         * @param grade    年级
         * @param email    电子邮件
         * @return {@link ResultBean }
         */
        @RequestMapping("/auth/updateUserAllInfo")
        public ResultBean updateUserAllInfo(Integer changeId, String username, String pwd, String age, String sex, String major, String grade, String email)
            {
                
                try
                    {
                        Integer.parseInt(age);
                    }
                catch (NumberFormatException e)
                    {
                        return ResultBean.returnError("年龄必须为数字");
                    }
                
                BsUser user = new BsUser();
                user.setId(changeId);
                user.setPwd(pwd);
                user.setUname(username);
                user.setAge(Integer.parseInt(age));
                user.setSex(sex);
                user.setMajor(major);
                user.setGrade(grade);
                user.setEmail(email);
                Integer line = userService.updateUserAllInfo(user);
                if (line > 0)
                    {
                        return ResultBean.returnOk();
                    }
                return ResultBean.returnError();
            }
        
        
        /**
         * 按 Token 获取用户信息
         *
         * @param uid uid
         * @return {@link ResultBean }
         */
        @RequestMapping("/auth/getUserInfoByToken")
        public ResultBean getUserInfoByToken(Integer uid)
            {
                
                BsUser userById = userService.getUserById(uid);
                return ResultBean.returnOk().pushData("data", userById);
            }
    }

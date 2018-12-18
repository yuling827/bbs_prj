package com.zx.bbsprj.controller;

import com.zx.bbsprj.service.LoginService;
import org.apache.catalina.servlet4preview.http.HttpServletRequest;
import org.jasypt.encryption.StringEncryptor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

@Controller
public class LoginController {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private LoginService loginService;

    //jaspyt加密框架
    @Autowired
    private StringEncryptor stringEncryptor;

    /**
     * 新博客注册页面
     * @return
     */
    @RequestMapping("/register")
    public String toRegisterPage() {
        return "register";
    }

    /**
     * 新博客注册
     * 判断注册的账号是否已经存在
     * 已存在-->提示注册失败，返回注册页面
     * 否则-->注册成功，跳转到登录页面
     * @return
     */
    @PostMapping("/register")
    @ResponseBody
    public Map<String,String> register(HttpServletRequest request, HttpSession session) {
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String nickname = request.getParameter("nickname");

        com.zx.bbsprj.entity.User user = new com.zx.bbsprj.entity.User();
        user.setUsername(username);
        user.setNickname(nickname);
        user.setPassword(password);

        Map<String,String> map = new HashMap<>();
        if (loginService.registerUser(user)) {
            //注册成功
            session.setAttribute("info","注册成功");
            session.setAttribute("username",username);
            map.put("flag","1");
        } else {
            //注册失败
            session.setAttribute("info","注册失败");
            map.put("flag","0");
        }

        return map;

    }

    /**
     * 注册成功
     * @return
     */
    @RequestMapping("/successRegist")
    public String successRegist(HttpSession session, Model model,HttpServletRequest request) {

        StringBuffer requestURL = request.getRequestURL();
        int index = requestURL.lastIndexOf("/");
        String url = requestURL.substring(0, index + 1);

        String info = (String) session.getAttribute("info");
        String username = (String) session.getAttribute("username");
        model.addAttribute("info",info);
        model.addAttribute("username",username);
        model.addAttribute("url",url);
        return "success";
    }

    /**
     * 注册时校验账号的唯一性
     * @param request
     * @return
     */
    @PostMapping("/checkLegal")
    @ResponseBody
    public Map<String,String> checkLegal(HttpServletRequest request) {
        String username = request.getParameter("username");
        Map<String,String> map = new HashMap<>();
        //校验账号的唯一性
        Long num = loginService.countByUsername(username);
        if (num==0) {
            //唯一
            map.put("rst","1");
        } else {
            //账号已存在
            map.put("rst","0");
        }
        return map;
    }

    /**
     * 登录操作--注册时，暂时没作密码的明文加密(session存储时用jaspyt安全框架作加密)
     * flag:
     * 1-->登录成功
     * 2-->账号不存在
     * 3-->密码错误
     * @param request
     * @return
     */
    @PostMapping("/login")
    @ResponseBody
    public Map<String, String> login(HttpServletRequest request,HttpSession session) {
        //用户名
        String username = request.getParameter("username");
        //密码
        String password = request.getParameter("password");

        Map<String,String> map = new HashMap<>();
        com.zx.bbsprj.entity.User user = loginService.loginCheck(username,password);

        if (user==null) {
            //账号密码不存在
            //对账号作判断
            long result = loginService.countByUsername(username);
            if (result==0) {
                //账号不存在
                map.put("flag","2");
            } else {
                //密码错误
                map.put("flag","3");
            }
        } else {
            //账号存在，密码匹配
            //将账号密码密文保存到session中
            try {
                //加密后的账号、密码
                String jaspytname = stringEncryptor.encrypt(URLEncoder.encode(username,"utf-8"));
                String jaspytpwd = stringEncryptor.encrypt(password);
                String jaspytInfo = jaspytname + "#" + jaspytpwd;
                //将登录信息保存到session内
                session.setAttribute("jaspytInfo",jaspytInfo);
            } catch (UnsupportedEncodingException e) {
                logger.error("登录时账号编码发生错误："+e.getMessage());
                e.printStackTrace();
            }

            String nickname = user.getNickname();
            session.setAttribute("loginInfo","登录成功");
            session.setAttribute("nickname",nickname);
            session.setAttribute("username",username);
            map.put("flag","1");
        }
        return map;
    }

    /**
     * 登录成功后的页面跳转
     * @param session
     * @param model
     * @return
     */
    @RequestMapping("/toLogin")
    public String toLogin(HttpSession session,Model model,HttpServletRequest request) {

        StringBuffer requestURL = request.getRequestURL();
        int index = requestURL.lastIndexOf("/");
        String url = requestURL.substring(0, index + 1);
        url = url+"qrySiteArticle";

        String info = (String) session.getAttribute("loginInfo");
        String nickname = (String) session.getAttribute("nickname");

//        String url1 = "http://upincar.oss-cn-shanghai.aliyuncs.com/followUp/OR201804020345447012018071313381701.jpg";

        model.addAttribute("info",info);
        model.addAttribute("nickname",nickname);
        model.addAttribute("url",url);
//        model.addAttribute("url1",url1);
        return "loginSkip";
    }

    /**
     * 退出操作
     * @param session
     * @return
     */
    @RequestMapping("/logout")
    public String logout(HttpSession session,Model model,HttpServletRequest request) {
        //删除session内username值--登录成功会存入username值
        session.removeAttribute("username");
        //删除session内jaspytInfo值--登录成功后存入jaspytInfo值，用于请求拦截
        session.removeAttribute("jaspytInfo");

        StringBuffer requestURL = request.getRequestURL();
        int index = requestURL.lastIndexOf("/");
        String url = requestURL.substring(0, index + 1);
        url = url+"qrySiteArticle";

        model.addAttribute("url",url);

        return "logoutSkip";
    }









}

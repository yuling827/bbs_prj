package com.zx.bbsprj.component;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 用来作登录请求的拦截器-->没有登录的用户不能访问后台主页
 */
public class LoginHandlerInterceptor implements HandlerInterceptor {

    //拦截用户的登录请求
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //拦截请求，从session中获取登录用户信息(加密后的账号和密码)
        Object jaspytInfo = request.getSession().getAttribute("jaspytInfo");
        if (jaspytInfo!=null) {
            //登录信息不为null，说明已登录
            return true;
        } else {
            //尚未登录
            //session没有保存登录后的信息，非登录状态想要访问登录后的主页
            //通过forward()将请求转发到登录页
            request.setAttribute("msg","没有权限访问，请先登录");
            request.getRequestDispatcher("/").forward(request,response);
            return false;
        }

    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }
}

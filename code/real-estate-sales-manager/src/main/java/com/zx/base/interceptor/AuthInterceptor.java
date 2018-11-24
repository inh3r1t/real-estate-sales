package com.zx.base.interceptor;


import com.zx.base.annotation.AuthorizeIgnore;
import com.zx.lib.web.CookieUtil;
import com.zx.system.model.SysUserlogin;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.List;


/**
 * 权限控制拦截器
 *
 * @author V.E.
 * @version 2017/12/4
 */
public class AuthInterceptor extends HandlerInterceptorAdapter {

    private List<String> allModules;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        HttpSession session = request.getSession(true);
        SysUserlogin userLogin = (SysUserlogin) session.getAttribute("currentUserLogin");
        if (handler.getClass().isAssignableFrom(HandlerMethod.class)) {
            AuthorizeIgnore authorizeIgnore = ((HandlerMethod) handler).getMethodAnnotation(AuthorizeIgnore.class);
            //声明不验证权限
            if (authorizeIgnore != null) {
                return true;
            } else {
                if (userLogin == null) {
                    response.getWriter().write("<script>top.location.href='/login'</script>");
                    return false;
                } else {
                    Cookie token = CookieUtil.getCookieByName(request, "token");
                    if (token != null && token.getValue() != "" && token.getValue().equals(userLogin.getLogintoken())) {
                        CookieUtil.addCookie(response, "token", token.getValue(), 0);
                    } else {
                        response.getWriter().write("<script>top.location.href='/login'</script>");
                        return false;
                    }
                }
            }
            /******退出的时候记得清除cookie中的内容,如果用户已经登录********/
        }
        if (this.allModules == null) {
            try {
                this.allModules = (List<String>) session.getAttribute("allModules");
            } catch (Exception ex) {
                response.getWriter().write("<script>top.location.href='/login'</script>");
            }
        }
        //验证菜单是否有权限
        if (this.allModules.contains(request.getServletPath())) {
            if (userLogin.getIssuper()) {
                return true;
            } else if (!userLogin.getModules().stream().anyMatch(l ->
                    l.getMurl().contains(request.getServletPath()))) {
                //没有权限
                response.sendRedirect(request.getContextPath() + "/error");
                return false;
            }
        }
        return true;
    }

}
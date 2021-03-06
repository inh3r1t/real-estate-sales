package com.zx.base.interceptor;


import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.Verification;
import com.zx.base.annotation.AuthorizeIgnore;
import com.zx.base.annotation.WechatAuthorize;
import com.zx.base.common.Const;
import com.zx.business.common.DataStore;
import com.zx.business.model.BusUser;
import com.zx.business.service.BusUserService;
import com.zx.lib.web.CookieUtil;
import com.zx.system.model.SysUserlogin;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * 权限控制拦截器
 *
 * @author V.E.
 * @version 2017/12/4
 */
public class AuthInterceptor extends HandlerInterceptorAdapter {

    private List<String> allModules;

    @Resource
    BusUserService busUserService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        HttpSession session = request.getSession(true);
        SysUserlogin userLogin = (SysUserlogin) session.getAttribute("currentUserLogin");
        if (handler.getClass().isAssignableFrom(HandlerMethod.class)) {
            // 验证微信小程序接口
            WechatAuthorize wechatAuthorize = ((HandlerMethod) handler).getMethodAnnotation(WechatAuthorize.class);
            if (wechatAuthorize != null) {
                // 验证token
                try {
                    String token = request.getHeader("token");
                    if (token == null) {
                        request.setAttribute("ERROR_CODE", Const.TOKEN_NULL);
                        return true;
                    }
                    BusUser busUser = busUserService.getUserFromToken(token);
                    if (busUser == null) {
                        request.setAttribute("ERROR_CODE", Const.NO_EXIST_USER);
                        return true;
                    }
                    JWTVerifier jwtVerifier = JWT.require(Algorithm.HMAC256(busUser.getPasswd())).build();
                    jwtVerifier.verify(token);

                    // 检查session是否过期:7days
                    Long lastTime = DataStore.getInstance().expireInfo.get(busUser.getId());
                    if (lastTime + 7 * 24 * 60 * 60 * 1000 < System.currentTimeMillis()) {
                        // delete expired account
                        DataStore.getInstance().expireInfo.remove(busUser.getId());
                        request.setAttribute("ERROR_CODE", Const.SESSION_EXPIRE);
                        return true;
                    }
                    // update last access time
                    DataStore.getInstance().expireInfo.put(busUser.getId(), System.currentTimeMillis());
                } catch (JWTVerificationException e) {
                    request.setAttribute("ERROR_CODE", Const.TOKEN_ERROR);
                } finally {
                    return true;
                }
            }

            AuthorizeIgnore authorizeIgnore = ((HandlerMethod) handler).getMethodAnnotation(AuthorizeIgnore.class);
            //声明不验证权限
            if (authorizeIgnore != null) {
                return true;
            }

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

            /******退出的时候记得清除cookie中的内容,如果用户已经登录********/
            if (this.allModules == null) {
                try {
                    this.allModules = (List<String>) session.getAttribute("allModules");
                } catch (Exception ex) {
                    response.getWriter().write("<script>top.location.href='/login'</script>");
                }
            }
            //验证菜单是否有权限
            if (this.allModules != null && this.allModules.contains(request.getServletPath())) {
                if (userLogin.getIssuper()) {
                    return true;
                } else if (!userLogin.getModules().stream().anyMatch(l ->
                        l.getMurl().contains(request.getServletPath()))) {
                    //没有权限
                    response.sendRedirect(request.getContextPath() + "/error");
                    return false;
                }
            }
        }

        return true;
    }

}
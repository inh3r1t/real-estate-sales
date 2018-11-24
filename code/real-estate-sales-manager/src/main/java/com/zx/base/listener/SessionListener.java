package com.zx.base.listener;

import com.zx.lib.utils.LogUtil;
import com.zx.system.model.SysUser;
import com.zx.system.model.SysUserlogin;
import com.zx.system.service.UserService;
import com.zx.system.service.impl.UserServiceImpl;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

/**
 * ${DESCRIPTION}
 *
 * @author Xiafl.
 * @version 2017/12/18
 */
@WebListener
public class SessionListener implements HttpSessionListener {

    @Override
    public void sessionCreated(HttpSessionEvent httpSessionEvent) {
    }

    @Override
    public void sessionDestroyed(HttpSessionEvent httpSessionEvent) {
        HttpSession session = httpSessionEvent.getSession();
        Object object = session.getAttribute("currentUserLogin");
        if (object == null){
            return;
        }
        else if (object instanceof SysUserlogin){
            SysUserlogin userlogin = (SysUserlogin) object;
            WebApplicationContext application= WebApplicationContextUtils.getWebApplicationContext(session.getServletContext());
            UserService userService =  application.getBean(UserService.class);
            userService.deleteUserLoginByToken(userlogin.getLogintoken());
            /// 记录上次登录Ip
            /// 记录上次登录时间
            SysUser user = userService.getByID(userlogin.getUserid());
            user.setIp(userlogin.getClientip());
            user.setPrelogintime(userlogin.getCreatetime());
            userService.save(user);
        }
    }

}

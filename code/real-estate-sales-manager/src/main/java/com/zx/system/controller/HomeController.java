package com.zx.system.controller;

import com.zx.base.annotation.FormReSubmitValidation;
import com.zx.base.common.CaptchaUtil;
import com.zx.base.common.IterateDirUtil;
import com.zx.base.controller.BaseController;
import com.zx.base.enums.TypeEnums;
import com.zx.base.exception.BusinessException;
import com.zx.base.annotation.AuthorizeIgnore;
import com.zx.base.model.DirBean;
import com.zx.base.model.FileBean;
import com.zx.base.model.ReturnModel;
import com.zx.base.model.TreeJsonEntity;
import com.zx.lib.utils.DateUtil;
import com.zx.lib.utils.StringUtil;
import com.zx.lib.web.CookieUtil;
import com.zx.system.model.SysLog;
import com.zx.system.model.SysModule;
import com.zx.system.model.SysUser;
import com.zx.system.model.SysUserlogin;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.UnknownHostException;
import java.util.*;
import java.util.stream.Collectors;


/**
 * @author V.E.
 */
@Controller
public class HomeController extends BaseController {
    /**
     * 系统主页
     *
     * @param model
     * @return
     * @throws UnknownHostException
     */
    @RequestMapping("/")
    public String index(Model model) throws UnknownHostException {
        model.addAttribute("currentUserLogin", currentUserLogin());
        model.addAttribute("SYSTEM_NAME", SYSTEM_NAME);
        List<SysModule> list = currentUserLogin().getModules();
        if (list != null) {
            List<TreeJsonEntity> treeList = modules2TreeJsonEntity(list.stream()
                            .filter(l -> StringUtil.isEmpty(l.getParentcode()))
                            .collect(Collectors.toList()),
                    list);
            model.addAttribute("treeList", treeList);
        }
        String httpUrl = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + "/ws";
        String wsUrl = "ws://" + request.getServerName() + ":" + request.getServerPort() + "/ws";
        model.addAttribute("webSocketUrl", httpUrl + "," + wsUrl);
        return "index";
    }

    /**
     * 系统首页仪表盘
     */
    @RequestMapping("/dashboard")
    public String dashboard() {
        return "system/home/dashboard";
    }


    /**
     * 系统登录页
     */
    //@FormReSubmitValidation
    @AuthorizeIgnore
    @RequestMapping("/login")
    public String login(Model model) {
        model.addAttribute("SYSTEM_VERSION", SYSTEM_VERSION);
        model.addAttribute("SYSTEM_NAME", SYSTEM_NAME);
        model.addAttribute("isCaptcha", isCaptcha);
        if (isOnline()) {
            return "redirect:/";
        }
        return "system/home/login";
    }

    /**
     * 登录 密码MD5加密
     *
     * @param httpRequest
     * @param httpResponse
     * @param httpSession
     * @return
     */
    //@FormReSubmitValidation(true)
    @AuthorizeIgnore
    @RequestMapping(value = "/loginSubmit", method = RequestMethod.POST)
    @ResponseBody
    public Object loginSubmit(HttpServletRequest httpRequest, HttpServletResponse httpResponse, HttpSession httpSession) {
        ReturnModel rm = new ReturnModel();
        //验证码校验
        if (isCaptcha) {
            String verificationCode = httpRequest.getParameter("VerificationCode");
            String validateCode = (String) session.getAttribute(CaptchaUtil.RANDOMCODEKEY);
            if (!StringUtil.isEmpty(validateCode) && !verificationCode.toUpperCase().equals(validateCode.toUpperCase())) {
                rm.setInfo(false, "验证码错误，请重新输入！");
                return rm;
            }
        }

        String loginid = httpRequest.getParameter("username");
        String psd = httpRequest.getParameter("password");
        try {
            SysUser user = userService.getByUserName(loginid);
            Date now = new Date();
            if (user != null
                    && user.getLoginerrorcount() != null
                    && user.getLoginerrorcount() >= 5
                    && user.getLastloginerrortime() != null
                    && DateUtil.isSameDay(user.getLastloginerrortime(), now)) {
                //当天登录错误超过五次
                rm.setInfo(false, "今日密码错误超过5次,请明天再试或联系管理员解锁!");
                return rm;
            }

            SysUserlogin userlogin = userService.login(loginid, psd);
            if (userlogin.getUserid() != null
                    && userlogin.getUserid() > 0
                    && userlogin.getUser().getState() == TypeEnums.State.正常.getValue()) {

                if (user != null && user.getDeadline() != null && user.getDeadline().compareTo(DateUtil.getDateNow()) <= 0) {
                    rm.setInfo(false, "账号已过期，请联系管理员");
                    return rm;
                }

                userlogin.setClientip(getClientIp());
                userlogin.setCreatetime(new Date());
                userService.addUserLogin(userlogin);
                rm.setInfo(true, "登录成功，即将跳转至首页...");
                httpSession.setAttribute("allModules", moduleService.selectList());
                //将userLogin放到session中
                httpSession.setAttribute("currentUserLogin", userlogin);
                CookieUtil.addCookie(httpResponse, "token", userlogin.getLogintoken(), 0);
                addLogInfo(userlogin.getUsername() + " 登录成功", SysLog.LogType.其他);
                if (user != null) {
                    //错误次数归0
                    user.setLoginerrorcount(0);
                    userService.save(user);

                }
            } else if (userlogin.getUser() == null) {
                if (user == null) {
                    rm.setInfo(false, "用户名不存在");
                } else {
                    //错误次数为空时初始化
                    if (user.getLoginerrorcount() == null || user.getLoginerrorcount() >= 5) {
                        user.setLoginerrorcount(0);
                    }
                    user.setLoginerrorcount(user.getLoginerrorcount() + 1);
                    if (user.getLoginerrorcount() == 5) {
                        rm.setInfo(false, "今日密码错误超过5次,请明天再试,或联系管理员解锁!");
                    } else {
                        rm.setInfo(false, "密码错误，您还有" + (5 - user.getLoginerrorcount()) + "次机会");
                    }


                    user.setLastloginerrortime(now);
                    userService.save(user);
                }

                addLogInfo(loginid + " 登录失败：用户名或密码错误", SysLog.LogType.其他);
            } else if (userlogin.getUser() != null && userlogin.getUser().getState() == TypeEnums.State.禁用.getValue()) {
                rm.setInfo(false, "账号已禁用，请联系管理员");
                addLogInfo(loginid + " 登录失败：账号已禁用，请联系管理员", SysLog.LogType.其他);
            }
            return rm;
        } catch (Exception ex) {
            addLogError(loginid + " 登录异常", "", SysLog.LogType.其他, ex);
            throw new BusinessException(ex);
        }
    }

    /**
     * 注销用户
     */
    @AuthorizeIgnore
    @RequestMapping("/logout")
    @ResponseBody
    public Object logout(HttpServletResponse httpResponse, HttpSession session) {
        ReturnModel returnModel = new ReturnModel();
        try {
            if (currentUserLogin() != null) {
                String currentLoginUserId = currentUserLogin().getUsername();
                userService.deleteUserLoginByToken(currentUserLogin().getLogintoken());
                addLogInfo(currentLoginUserId + "退出系统", SysLog.LogType.其他);
                session.invalidate();
                // 清除Cookie
                CookieUtil.addCookie(httpResponse, "token", null, 0);
            }
            returnModel.setState(true);
            returnModel.setMessage("成功退出系统");
            return returnModel;
        } catch (Exception e) {
            addLogWarn("用户退出系统失败", "", SysLog.LogType.其他, e);
            throw new BusinessException("退出系统失败", e);
        }
    }


    /**
     * 备份数据库页面
     *
     * @return
     */
    @RequestMapping("/backup")
    public String backup() {
        return "system/home/backup";
    }

    /**
     * 获取备份过的数据库备份文件
     *
     * @param path
     * @return
     */
    @RequestMapping("/getBackupFiles")
    @ResponseBody
    public Object getBackupFiles(@Value("${custom.directory-path}") String path) {
        ReturnModel rm = new ReturnModel();
        List<FileBean> list = new ArrayList<>();
        try {
            DirBean dirBean = IterateDirUtil.getFiles(path + "/backup", getBasePath());
            list = dirBean.getFiles();
            //排序 ： 最后编辑倒序
            Collections.sort(list, (o1, o2) -> {
                // 强制转换
                FileBean p1 = o1;
                FileBean p2 = o2;
                return p2.getFileLastModifiedTime().compareTo(p1.getFileLastModifiedTime());
            });

            rm.setState(true);
        } catch (Exception ex) {
            rm.setState(false);
        }
        rm.setModel(list);
        return rm;
    }


    /**
     * 通过shell备份数据库
     *
     * @param shellPath
     * @return
     */
    @RequestMapping("/backupByShell")
    @ResponseBody
    public Object backupByShell(@Value("${custom.backup-database-shell-path}") String shellPath) {
        ReturnModel msg = new ReturnModel();
        InputStreamReader stdISR = null;
        InputStreamReader errISR = null;
        Process process = null;
        String command = shellPath;
        try {
            Runtime.getRuntime().exec(command).waitFor();
            msg.setState(true);
            msg.setMessage("数据库备份成功");
            addLogInfo("数据库备份成功", "", SysLog.LogType.其他);
        } catch (IOException | InterruptedException e) {
            msg.setState(false);
            msg.setMessage("数据库备份失败");
            addLogError("数据库备份失败", SysLog.LogType.其他, e);
        } finally {
            try {
                if (stdISR != null) {
                    stdISR.close();
                }
                if (errISR != null) {
                    errISR.close();
                }
                if (process != null) {
                    process.destroy();
                }
            } catch (IOException e) {
                msg.setState(false);
                msg.setMessage("数据库备份失败");
                addLogError("正式执行命令：" + command + "有IO异常", SysLog.LogType.其他, e);
            }
        }

        return msg;
    }
}

package com.zx.base.controller;

import com.zx.lib.utils.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.web.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

/**
 * 错误控制类
 *
 * @author V.E.
 * @version 2017/12/06
 */
@Controller
@RequestMapping(value = "/error")
public class BaseErrorController implements ErrorController {
    /**
     * 系统名称
     */
    @Value("${custom.system-name}")
    public String SYSTEM_NAME;

    @Autowired
    public HttpServletRequest request;

    @Override
    public String getErrorPath() {
        return "error";
    }


    @RequestMapping
    public String error(Model model) {
        String errorMsg = request.getParameter("errorMsg");
        model.addAttribute("msg", StringUtils.isEmpty(errorMsg) ? " 您访问的页面出现错误，请稍后再试。" : errorMsg);
        model.addAttribute("SYSTEM_NAME", SYSTEM_NAME);
        return getErrorPath();
    }

}

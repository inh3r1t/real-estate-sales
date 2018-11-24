package com.zx.system.controller;

import com.zx.base.common.AuthUtil;
import com.zx.base.config.WebSocketConfig;
import com.zx.base.controller.BaseController;
import com.zx.base.enums.TypeEnums;
import com.zx.base.exception.BusinessException;
import com.zx.base.model.PagerModel;
import com.zx.system.model.SysUser;
import com.zx.system.model.SysUserlogin;
import com.zx.system.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.net.MalformedURLException;
import java.util.*;
import java.util.stream.Collectors;


/**
 * 在线用户
 *
 * @author Xiafl.
 * @version 2017/12/13
 */
@RequestMapping("/monitor")
@Controller
public class MonitorController extends BaseController {

    @Autowired
    private UserService userService;

    @RequestMapping(value = "/home")
    public String home() {
        return "system/monitor/home";
    }

    /**
     * 获取在线用户列表
     **/
    @RequestMapping(value = "/list", method = RequestMethod.POST)
    @ResponseBody
    public Object list(Integer pageSize, Integer page) {
        if (pageSize == null) {
            pageSize = PAGE_SIZE;
        }
        if (page == null) {
            page = 1;
        }
        try {
            // 在线用户集合
            List<String> loginTokens = new ArrayList<>(WebSocketConfig.Clients.values());
            List tokens = page(loginTokens, page, pageSize);
            List userloginList = userService.selectByToken(tokens);
            return new PagerModel<>(pageSize, page, userloginList.size(), userloginList);
        } catch (Exception e) {
            throw new BusinessException(e);
        }
    }

    private List page(List collect, int page, int pageSize) {
        // 从哪里开始截取
        int fromIndex = 0;
        // 截取几个
        int toIndex = 0;
        if (collect == null || collect.size() == 0) {
            return new ArrayList<>();
        }
        int pageNum = collect.size() % pageSize == 0 ? collect.size() / pageSize : (collect.size() / pageSize) + 1;
        // 当前页小于或等于总页数时执行
        if (page <= pageNum && page != 0) {
            fromIndex = (page - 1) * pageSize;
            if (page == pageNum) {
                toIndex = collect.size();
            } else {
                toIndex = page * pageSize;
            }
        }
        return collect.subList(fromIndex, toIndex);
    }

}

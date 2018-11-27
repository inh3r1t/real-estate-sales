package com.zx.business.controller;

import com.zx.base.annotation.AuthorizeIgnore;
import com.zx.business.model.BusUser;
import com.zx.business.service.BusUserService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping("/busUser")
public class BusUserController {

    @Resource
    private BusUserService busUserService;

    @AuthorizeIgnore
    @RequestMapping(value = "/addBusUser", method = RequestMethod.POST)
    @ResponseBody
    public String addBusUser(@RequestBody BusUser busUser) {
        int id = busUserService.addBusUser(busUser);
        return String.valueOf(id);
    }
}

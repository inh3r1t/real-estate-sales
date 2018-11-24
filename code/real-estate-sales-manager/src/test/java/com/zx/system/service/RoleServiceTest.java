package com.zx.system.service;

import com.zx.SpringBootStart;
import com.zx.system.service.impl.RoleServiceImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import static org.junit.Assert.*;

/**
 * ${DESCRIPTION}
 *
 * @author Xiafl.
 * @version 2018/02/27
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = SpringBootStart.class)
public class RoleServiceTest {
    @Autowired
    RoleService roleService;

    @Test
    public void roleIsExisted() throws Exception {
        String i = String.format("用户%s", 10);
        int s = 0;
    }
}
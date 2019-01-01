package com.zx.business.service;

import com.zx.base.model.PagerModel;
import com.zx.business.dao.BusNotifyMsgMapper;
import com.zx.business.model.BusNotifyMsg;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Author: ytxu3
 * @Description:
 * @Date: 2018/12/30 14:07
 */
@Service
public class BusNotifyMsgService {

    @Resource
    private BusNotifyMsgMapper busNotifyMsgMapper;

    public BusNotifyMsg addOrUpdate(BusNotifyMsg condition) {
        if (condition.getId() != null && busNotifyMsgMapper.selectByPrimaryKey(condition.getId()) != null) {
            busNotifyMsgMapper.updateByPrimaryKeySelective(condition);
        } else {
            busNotifyMsgMapper.insertSelective(condition);
        }
        return condition;
    }

    public void deleteById(Integer id) {
        busNotifyMsgMapper.deleteByPrimaryKey(id);
    }

    public List<BusNotifyMsg> getList(BusNotifyMsg condition) {
        return busNotifyMsgMapper.selectByPage(null, null, null, null, condition);
    }

    public PagerModel<BusNotifyMsg> getPage(int page, int pageSize, BusNotifyMsg condition) {
        Long count = busNotifyMsgMapper.countByModel(condition);
        int start = (page - 1) * pageSize;
        List<BusNotifyMsg> busNotifyMsgs = busNotifyMsgMapper.selectByPage(start, pageSize, "update_time", "desc", condition);
        return new PagerModel<>(pageSize, page, count.intValue(), busNotifyMsgs);
    }
}

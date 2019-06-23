package com.zx.business.controller;

import com.alibaba.fastjson.JSON;
import com.zx.base.annotation.WechatAuthorize;
import com.zx.base.common.Const;
import com.zx.base.controller.BaseController;
import com.zx.base.model.PagerModel;
import com.zx.base.model.ResultData;
import com.zx.base.model.ReturnModel;
import com.zx.business.enums.PhotoType;
import com.zx.business.model.BusAgentCompany;
import com.zx.business.model.BusRealEstate;
import com.zx.business.model.BusUser;
import com.zx.business.service.BusAgentCompanyService;
import com.zx.business.service.BusRealEstateService;
import com.zx.business.service.BusUserService;
import com.zx.lib.http.kit.HttpKit;
import com.zx.lib.utils.DateUtil;
import com.zx.lib.utils.StringUtil;
import com.zx.lib.web.HtmlUtil;
import com.zx.system.model.FileInfo;
import com.zx.system.model.SysCategory;
import com.zx.system.model.SysLog;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.protocol.HTTP;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.io.*;
import java.util.*;

/**
 * @Author: ytxu3
 * @Description:
 * @Date: 2018/11/28 18:50
 */
@Controller
@RequestMapping("/busRealEstate")
public class BusRealEstateController extends BaseController {

    private static Logger logger = LoggerFactory.getLogger(BusRealEstateController.class);

    @Resource
    private BusRealEstateService busRealStateService;
    @Resource
    private BusUserService busUserService;
    @Resource
    private BusAgentCompanyService busAgentCompanyService;

    @Value("${custom.wechat.appid}")
    public String appid;

    @Value("${custom.wechat.secret}")
    public String secret;

    @RequestMapping(value = "/getPage", method = RequestMethod.POST)
    @ResponseBody
    @WechatAuthorize
    public ResultData getPage(@RequestBody BusRealEstate busRealEstate) {
        ResultData resultData = new ResultData(Const.SUCCESS_CODE, "获取楼盘列表成功！");
        try {
            PagerModel<BusRealEstate> busRealEstatePage =
                    busRealStateService.getPage(busRealEstate.getPage(), busRealEstate.getPageSize(), busRealEstate);
            resultData.setData(busRealEstatePage);
        } catch (Exception e) {
            resultData.setResultCode(Const.FAILED_CODE);
            resultData.setMsg("获取楼盘列表失败！");
            logger.error(e.getMessage(), e);
        }
        return resultData;
    }

    @RequestMapping(value = "/getList", method = RequestMethod.POST)
    @ResponseBody
    public Object getList(BusRealEstate busRealEstate) {
        try {
            if (busRealEstate.getPage() == null) {
                busRealEstate.setPage(1);
            }
            if (busRealEstate.getPageSize() == null) {
                busRealEstate.setPageSize(PAGE_SIZE);
            }
            return busRealStateService.getPage(busRealEstate.getPage(), busRealEstate.getPageSize(), busRealEstate);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return new PagerModel<>(busRealEstate.getPage(), busRealEstate.getPageSize(), 0, new ArrayList<>());
        }
    }

    @RequestMapping(value = "/getById/{id}", method = RequestMethod.GET)
    @ResponseBody
    @WechatAuthorize
    public ResultData getById(@PathVariable Integer id) {
        ResultData resultData = new ResultData(Const.SUCCESS_CODE, "获取楼盘详细信息成功！");
        try {
            BusRealEstate busRealEstate = busRealStateService.getById(id);
            resultData.setData(busRealEstate);
        } catch (Exception e) {
            resultData.setResultCode(Const.FAILED_CODE);
            resultData.setMsg("获取楼盘详细信息失败！");
            logger.error(e.getMessage(), e);
        }
        return resultData;
    }

    @RequestMapping(value = "/category", method = RequestMethod.GET)
    @ResponseBody
    @WechatAuthorize
    public ResultData category() {
        ResultData resultData = new ResultData(Const.SUCCESS_CODE, "获取楼盘区域信息成功！");
        try {
            List<SysCategory> categories = sysCategoryService.selectListByType(0);
            resultData.setData(categories);
        } catch (Exception e) {
            resultData.setResultCode(Const.FAILED_CODE);
            resultData.setMsg("获取楼盘区域信息失败！");
            logger.error(e.getMessage(), e);
        }
        return resultData;
    }

    @RequestMapping("/list")
    public String list() {
        return "business/busRealEstate/list";
    }


    @RequestMapping("/edit")
    public String edit(Model model, Integer id) {
        BusRealEstate estate = new BusRealEstate();
        if (id != null && !id.equals(0)) {
            estate = busRealStateService.getById(id);
        }
        List<BusUser> managerList = new ArrayList<>();
        managerList = busUserService.getListByRoleType(0);
        model.addAttribute("model", estate);
        model.addAttribute("managerList", managerList);
        List<SysCategory> categories = sysCategoryService.selectListByType(0);
        model.addAttribute("categories", categories);
        return "business/busRealEstate/edit";
    }

    @RequestMapping("/notice")
    public String notice(Model model, Integer id) {
        BusRealEstate estate = new BusRealEstate();
        if (id != null && !id.equals(0)) {
            estate = busRealStateService.getById(id);
        }
        BusAgentCompany busAgentCompany = new BusAgentCompany();
        List<BusAgentCompany> busAgentCompanies = busAgentCompanyService.getList(busAgentCompany);

        for (BusAgentCompany item : busAgentCompanies) {
            BusUser busUser = new BusUser();
            busUser.setCompanyId(item.getId());
            List<BusUser> busUsers = busUserService.getList(busUser);
            item.setBusUsers(busUsers);
        }
        model.addAttribute("busAgentCompanies", busAgentCompanies);
        model.addAttribute("model", estate);
        return "business/busRealEstate/notice";
    }

    @RequestMapping(value = "/submit")
    @ResponseBody
    public Object submit(BusRealEstate estate, Boolean needFullPhone) {
        boolean insertAction = estate.getId() == null || estate.getId() == 0;
        String actionName = insertAction ? "添加" : "更新";
        estate.setDetail(HtmlUtil.htmlUnescape(estate.getDetail()));
        estate.setCommission(estate.getCommission().replaceAll("%2B", "+"));
        if (estate.getIsListRecommend() == null) {
            estate.setIsListRecommend(false);
        }
        if (estate.getIsTopRecommend() == null) {
            estate.setIsTopRecommend(false);
        }
        estate.setExtend1(needFullPhone != null && needFullPhone ? "1" : "0");
        try {
            if (insertAction) {
                estate.setCreateTime(DateUtil.getDateNow());
                busRealStateService.add(estate);
            } else {
                busRealStateService.update(estate);
                fileInfoService.deleteByGroupId(estate.getId());
            }
            //处理图片
            List<FileInfo> fileInfos = new ArrayList<>();
            FileInfo fileInfo;
            //滚动图
            if (estate.getImages() != null) {
                for (String url : estate.getImages()) {
                    fileInfo = new FileInfo();
                    fileInfo.setGroupid(estate.getId());
                    fileInfo.setPath(url);
                    fileInfo.setRemark(PhotoType.DETAIL_TOP.code());
                    fileInfo.setState(0);
                    fileInfos.add(fileInfo);
                }
            }
            //缩略图
            if (StringUtil.isNotEmpty(estate.getThumbnail())) {
                fileInfo = new FileInfo();
                fileInfo.setGroupid(estate.getId());
                fileInfo.setPath(estate.getThumbnail());
                fileInfo.setRemark(PhotoType.THUMBNAIL.code());
                fileInfo.setState(0);
                fileInfos.add(fileInfo);
            }
            //推荐图
            if (StringUtil.isNotEmpty(estate.getTopRecommendImage())) {
                fileInfo = new FileInfo();
                fileInfo.setGroupid(estate.getId());
                fileInfo.setPath(estate.getTopRecommendImage());
                fileInfo.setRemark(PhotoType.MAIN_PAGE_TOP.code());
                fileInfo.setState(0);
                fileInfos.add(fileInfo);
            }
            if (fileInfos.size() > 0) {
                fileInfoService.insertBatch(fileInfos);
            }
            addLogInfo(String.format("%s楼盘%s成功", actionName, estate.getName()), "", insertAction ? SysLog.LogType.新增 : SysLog.LogType.修改);
            return new ReturnModel(true, String.format("%s成功", actionName));
        } catch (Exception e) {
            addLogError(String.format("%s楼盘%s失败", actionName, estate.getName()), "", insertAction ? SysLog.LogType.新增 : SysLog.LogType.修改, e, estate);
            return new ReturnModel(false, String.format("%s失败", actionName));
        }
    }

    @RequestMapping(value = "/delete", method = RequestMethod.GET)
    @ResponseBody
    public Object delete(Integer id) {
        ReturnModel rm = new ReturnModel();
        try {
            busRealStateService.delete(id);
            rm.setInfo(true, "删除成功");
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            rm.setInfo(false, "删除失败");
        }
        return rm;
    }

    @RequestMapping(value = "/noticeSubmit")
    @ResponseBody
    public Object noticeSubmit(Integer id, Integer type, String[] userIds) {
        // String[] userIds = request.getParameterValues("userIds");

        ReturnModel rm = new ReturnModel();
        try {
            if (userIds == null || userIds.length <= 0) {
                rm.setInfo(false, "至少选择一个通知用户");
            } else {
                busRealStateService.notice(id, type, Arrays.asList(userIds));
                rm.setInfo(true, "发送完成");
            }
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            rm.setInfo(false, "发送失败");
        }
        return rm;
    }


    /**
     * 获取小程序码
     *
     * @param page  路径
     * @param scene 参数
     * @return
     */
    @RequestMapping("/gainMiniProgramCode")
    @ResponseBody
    @WechatAuthorize
    public Object gainMiniProgramCode(String page, String scene) {
        ResultData resultData = new ResultData(Const.SUCCESS_CODE, "获取小程序码成功！");
        try {
            String fileName = "qrCode." + scene + "-" + System.currentTimeMillis() + ".jpg";
            //图片文件存放地址
            String filePath = "/qrcode/";
            filePath = getAbsolutePath(filePath);
            String relativePath = filePath + fileName;
            // File file = new File(relativePath);
            // if (!file.exists()) {
            String accessToken = JSON.parseObject(HttpKit.get(String.format(accessTokenApiUrl, "client_credential", appid, secret)).getHtml()).get("access_token").toString();
            if (StringUtils.isNotBlank(accessToken)) {
                String url = "https://api.weixin.qq.com/wxa/getwxacodeunlimit?access_token=" + accessToken;
                Map<String, Object> data = new HashMap<>();
                data.put("scene", scene);
                data.put("page", page);
                // data.put("width", 430);
                // data.put("auto_color", false);
                // data.put("line_color", null);
                // data.put("is_hyaline", false);
                InputStream inputStream = tempHttpClientUtil(url, data);
                if (inputStream != null) {
                    saveToImgByInputStream(inputStream, filePath, fileName.replaceAll("/", ""));
                }
            }
            // }
            if (relativePath.startsWith(getBasePath().replace("\\", "/"))) {
                relativePath = relativePath.substring(getBasePath().length());
            }
            resultData.setData(SYSTEM_URL + "/" + UPLOAD_NODE + relativePath);
        } catch (Exception ex) {
            resultData.setResultCode(Const.FAILED_CODE);
            resultData.setMsg("获取小程序码失败！");
        }

        return resultData;
    }

    public InputStream tempHttpClientUtil(String url, Map<String, Object> data) {
        CloseableHttpClient httpClient = HttpClientBuilder.create().build();
        HttpPost httpPost = new HttpPost(url);
        httpPost.addHeader(HTTP.CONTENT_TYPE, "application/json");
        String body = JSON.toJSONString(data);
        StringEntity entity = null;
        try {
            entity = new StringEntity(body);
            entity.setContentType("image/png");
            httpPost.setEntity(entity);
            HttpResponse response = httpClient.execute(httpPost);
            InputStream inputStream = response.getEntity().getContent();
            return inputStream;
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 保存图片
     *
     * @param instreams
     * @param imgPath
     * @param imgName
     * @return
     */
    public int saveToImgByInputStream(InputStream instreams, String imgPath, String imgName) {
        int stateInt = 1;
        if (instreams != null) {
            try {
                //可以是任何图片格式.jpg,.png等
                File file = new File(imgPath, imgName);
                if (!file.getParentFile().exists()) {
                    file.getParentFile().mkdirs();
                }
                FileOutputStream fos = new FileOutputStream(file);
                byte[] b = new byte[1024];
                int nRead = 0;
                while ((nRead = instreams.read(b)) != -1) {
                    fos.write(b, 0, nRead);
                }
                fos.flush();
                fos.close();
            } catch (Exception e) {
                stateInt = 0;
                e.printStackTrace();
            } finally {
            }
        }
        return stateInt;
    }

}

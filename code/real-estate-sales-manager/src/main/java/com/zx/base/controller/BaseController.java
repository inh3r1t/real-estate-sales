package com.zx.base.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.sun.jndi.toolkit.url.Uri;
import com.zx.base.common.CaptchaUtil;
import com.zx.base.common.IterateDirUtil;
import com.zx.base.common.UploadUtil;
import com.zx.base.enums.TypeEnums;
import com.zx.base.exception.BusinessException;
import com.zx.base.annotation.AuthorizeIgnore;
import com.zx.base.model.ReturnModel;
import com.zx.base.model.TreeJsonEntity;
import com.zx.lib.http.common.HttpConst;
import com.zx.lib.http.kit.HttpKit;
import com.zx.lib.json.JsonUtil;
import com.zx.lib.utils.*;
import com.zx.lib.utils.encrypt.Md5Util;
import com.zx.lib.web.CookieUtil;
import com.zx.system.model.*;
import com.zx.system.service.*;
import nl.bitwalker.useragentutils.UserAgent;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.ServletContext;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.*;
import java.net.URLDecoder;
import java.nio.charset.Charset;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author V.E.
 */
public class BaseController {
    /**
     * 文件管理的根目录
     */
    @Value("${custom.directory-path}")
    public String basePath;
    private static final String ENCODING = "utf-8";
    /**
     * 分页的每页条数
     */
    @Value("${custom.page-size}")
    public int PAGE_SIZE;
    /**
     * 文件上传的最大上限
     */
    @Value("${spring.http.multipart.max-file-size}")
    public String MAX_FILE_SIZE;
    /**
     * 系统名称
     */
    @Value("${custom.system-name}")
    public String SYSTEM_NAME;
    /**
     * 系统的版本号
     */
    @Value("${custom.version}")
    public String SYSTEM_VERSION;
    @Value("${custom.captcha}")
    public boolean isCaptcha;

    @Autowired
    public HttpSession session;
    @Autowired
    public HttpServletRequest request;
    public HttpServletResponse response;

    @Value("${custom.wechat.openid.api.url}")
    String openIdApiUrl;

    @Value("${custom.wechat.appid}")
    String appid;

    @Value("${custom.wechat.secret}")
    String secret;

    @Value("${custom.wechat.grant_type}")
    String grant_type;
    //图片上传绝对路径的基础地址
    @Value("${custom.system-url}")
    public String SYSTEM_URL;

    @Resource
    public RoleService roleService;
    @Resource
    public DeptService deptService;
    @Resource
    public LogService logService;
    @Resource
    public SysCategoryService sysCategoryService;
    @Resource
    public FileInfoService fileInfoService;
    @Resource
    public UserService userService;
    @Resource
    public ModuleService moduleService;
    @Resource
    public UserRoleService userRoleService;

    /**
     * 当前用户的登录信息
     *
     * @return SysUserlogin
     */
    public SysUserlogin currentUserLogin() {
        SysUserlogin current = (SysUserlogin) session.getAttribute("currentUserLogin");
        return current;
    }

    /**
     * 获取文件的绝对路径
     */
    public String getAbsolutePath(String path) throws UnsupportedEncodingException {
        path = path.replaceAll("%(?![0-9a-fA-F]{2})", "%25");
        return URLDecoder.decode(basePath + path, "UTF-8");
    }


    @RequestMapping("/upload")
    @ResponseBody
    public Object upload(String path, MultipartFile file) {
        ReturnModel msg = new ReturnModel();
        try {
            String destPath = getAbsolutePath(path);
            String fileName = file.getOriginalFilename();
            //判断是否存在
            File tempFile = new File(destPath + fileName);
            //如果覆盖则先删除否则直接返回
            if (tempFile.exists()) {
                msg.setState(false);
                msg.setMessage("文件已存在");
            } else {
                FileInfo f = uploadFile(file, destPath + fileName);
                if (f != null) {
                    msg.setState(true);
                    msg.setMessage("上传成功");
                } else {
                    msg.setState(false);
                    msg.setMessage("上传失败");
                }
            }
        } catch (Exception e) {
            msg.setState(false);
            msg.setMessage("上传失败");
            throw new BusinessException(e);
        }
        return msg;
    }

    /**
     * 文件上传
     *
     * @param file
     * @param path
     * @return
     * @throws IOException
     */
    public FileInfo uploadFile(MultipartFile file, String path) throws IOException {
        String fileName = file.getOriginalFilename();
        File tempFile = new File(path);
        if (!tempFile.getParentFile().exists()) {
            tempFile.getParentFile().mkdirs();
        }
        if (tempFile.exists()) {
            tempFile.delete();
        }

        tempFile.createNewFile();

        file.transferTo(tempFile);

        String relativePath = path.replace("\\", "/");
        if (relativePath.startsWith(basePath)) {
            relativePath = relativePath.substring(basePath.length());
        }

        FileInfo fileInfo = new FileInfo();
        fileInfo.setCreatetime(new Date());
        fileInfo.setRemark("");
        fileInfo.setCreaterid(currentUserLogin().getUserid());
        fileInfo.setState(TypeEnums.State.正常.getValue());
        fileInfo.setMd5(Md5Util.getFileMd5(path));
        fileInfo.setName(fileName);
        fileInfo.setPath(relativePath);
        fileInfo.setSize((double) file.getSize());
        fileInfo.setSuffix(IterateDirUtil.getFileType(fileName));
        fileInfoService.insert(fileInfo);
        return fileInfo;
    }

    /**
     * ckeditor图片上传
     *
     * @param request
     * @param response
     * @Title imageUpload
     */
    @RequestMapping("/uploadImageByCK")
    @ResponseBody
    public Object uploadImageByCK(HttpServletRequest request, HttpServletResponse response) {
        String json = "";
        try {
            String path = "/images/" + DateUtil.toDateString(DateUtil.getDateNow(), "yyyy-MM-dd");
            String fileName = UploadUtil.upload(request, basePath + path);
            //上传成功后，我们还需要返回Json格式的响应
            json = "{\n" +
                    "    \"uploaded\": 1,\n" +
                    "    \"fileName\": \"" + fileName + "\",\n" +
                    "    \"url\": \"" + SYSTEM_URL + path + "/" + fileName + "\"\n" +
                    "}";
        } catch (Exception e) {
            json = "{\n" +
                    "    \"uploaded\": 0,\n" +
                    "    \"error\": {\n" +
                    "        \"message\": \"上传失败，请重新上传\"\n" +
                    "    }\n" +
                    "}";
        }
        return json;
    }

    /**
     * 根据文件路径下载文件
     *
     * @param path
     * @return
     * @throws UnsupportedEncodingException
     * @throws IllegalStateException
     */
    @RequestMapping("/download")
    @ResponseBody
    public String download(String path, String filename) throws UnsupportedEncodingException, IllegalStateException {
        String fullPath = getAbsolutePath(path);
        // Get your file stream from wherever.
        File downloadFile = new File(fullPath);
        ServletContext context = request.getServletContext();
        // get MIME type of the file
        String mimeType = context.getMimeType(fullPath);
        if (mimeType == null) {
            // set to binary type if MIME mapping not found
            mimeType = "application/octet-stream";
            // System.out.println("context getMimeType is null");
        }

        // set content attributes for the response
        response.setContentType(mimeType);
        response.setContentLength((int) downloadFile.length());

        // set headers for the response
        String headerKey = "Content-Disposition";
        if (filename == null || "".equals(filename)) {
            filename = downloadFile.getName();
        }
        String userAgent = request.getHeader("User-Agent");
        // 针对IE或者以IE为内核的浏览器：
        if (userAgent.contains("MSIE") || userAgent.contains("Trident")) {
            filename = java.net.URLEncoder.encode(filename, "UTF-8");
        } else {
            // 非IE浏览器的处理：
            filename = new String(filename.getBytes("UTF-8"), "ISO-8859-1");
        }
        String headerValue = String.format("attachment; filename=\"%s\"", filename);
        response.setHeader(headerKey, headerValue);

        // Copy the stream to the response's output stream.
        try {
            InputStream myStream = new FileInputStream(fullPath);

            IOUtils.copy(myStream, response.getOutputStream());
            response.flushBuffer();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    @RequestMapping("/deleteFile")
    @ResponseBody
    public Object deleteFile(String path) throws UnsupportedEncodingException {
        ReturnModel msg = new ReturnModel();
        if (!StringUtil.isEmpty(path)) {
            try {
                String fullPath = getAbsolutePath(path);
                String fileName = IterateDirUtil.getFileName(fullPath);
                if (FileUtil.isExsit(fullPath)) {
                    if (FileUtil.delete(fullPath)) {
                        msg.setState(true);
                        msg.setMessage("文件《" + fileName + "》删除成功");
                    } else {
                        msg.setState(false);
                        msg.setMessage("文件被占用，稍后再试");
                    }
                }
            } catch (Exception ex) {
                msg.setState(false);
                msg.setMessage("系统异常，稍后再试");
            }
        } else {
            msg.setState(false);
            msg.setMessage("没有找到删除的文件");
        }
        return msg;
    }

    @RequestMapping("/deleteDir")
    @ResponseBody
    public Object deleteDir(String path) throws UnsupportedEncodingException {
        ReturnModel msg = new ReturnModel();
        if (!StringUtil.isEmpty(path)) {
            try {
                String fullPath = getAbsolutePath(path);
                String fileName = IterateDirUtil.getFileName(fullPath);
                if (DirectoryUtil.isExsit(fullPath)) {
                    if (DirectoryUtil.delete(fullPath)) {
                        msg.setState(true);
                        msg.setMessage("文件夹《" + fileName + "》删除成功");
                    }
                }
            } catch (Exception ex) {
                msg.setState(false);
                msg.setMessage("系统异常，稍后再试");
            }
        } else {
            msg.setState(false);
            msg.setMessage("没有找到删除的文件夹");
        }
        return msg;
    }

    /**
     * 根据文件路径获取文件名
     *
     * @param pathAndName
     * @return
     */
    public String getFileName(String pathAndName) {
        pathAndName = pathAndName.replace("\\", "/");
        int start = pathAndName.lastIndexOf("/");

        if (start != -1) {
            return pathAndName.substring(start + 1);
        } else {
            return pathAndName;
        }
    }

    /**
     * 添加信息日志
     *
     * @param text 信息内容
     * @param type 日志类型
     */

    protected void addLogInfo(String text, SysLog.LogType type) {
        addLog(text, null, null, SysLog.LogLevel.信息, type, null);
    }

    /**
     * 添加信息日志
     *
     * @param text  信息内容
     * @param alias 模块别名
     * @param type  日志类型
     */
    protected void addLogInfo(String text, String alias, SysLog.LogType type) {
        addLog(text, alias, null, SysLog.LogLevel.信息, type, null);
    }

    /**
     * 添加信息日志
     *
     * @param text  信息内容
     * @param alias 模块别名
     * @param type  日志类型
     * @param args  对象
     */
    protected void addLogInfo(String text, String alias, SysLog.LogType type, Object args) {
        addLog(text, alias, null, SysLog.LogLevel.信息, type, args);
    }


    /**
     * 添加警告日志
     *
     * @param text 信息内容
     * @param type 日志类型
     */
    protected void addLogWarn(String text, SysLog.LogType type) {
        addLog(text, null, null, SysLog.LogLevel.警告, type, null);
    }

    /**
     * 添加警告日志
     *
     * @param text  信息内容
     * @param alias 模块别名
     * @param type  日志类型
     */
    protected void addLogWarn(String text, String alias, SysLog.LogType type) {
        addLog(text, alias, null, SysLog.LogLevel.警告, type, null);
    }

    /**
     * 添加警告日志
     *
     * @param text  信息内容
     * @param alias 模块别名
     * @param type  日志类型
     * @param args  参数
     */
    protected void addLogWarn(String text, String alias, SysLog.LogType type, Object args) {
        addLog(text, alias, null, SysLog.LogLevel.警告, type, args);
    }

    /**
     * 添加错误日志
     *
     * @param text 信息内容
     * @param type 日志类型
     * @param ex   错误异常
     */
    protected void addLogError(String text, SysLog.LogType type, Exception ex) {
        addLog(text, null, (ex != null) ? ex.getMessage() : null, SysLog.LogLevel.错误, type, null);
    }


    /**
     * 添加错误日志
     *
     * @param text  信息内容
     * @param alias 模块别名
     * @param type  日志类型
     * @param ex    错误异常
     */
    protected void addLogError(String text, String alias, SysLog.LogType type, Exception ex) {
        addLog(text, alias, (ex != null) ? ex.getMessage() : null, SysLog.LogLevel.错误, type, null);
    }

    /**
     * 添加错误日志
     *
     * @param text  信息内容
     * @param alias 模块别名
     * @param type  日志类型
     * @param ex    错误异常
     * @param args  请求参数
     */
    protected void addLogError(String text, String alias, SysLog.LogType type, Exception ex, Object args) {
        addLog(text, alias, (ex != null) ? ex.getMessage() : null, SysLog.LogLevel.错误, type, args);
    }

    private void addLog(String text, String alias, String details, SysLog.LogLevel logLevel, SysLog.LogType logType, Object args) {
        SysLog log = new SysLog();
        log.setUrl(request.getRequestURI());
        log.setDescription(text);
        log.setDetails(details);
        log.setLogargs((args != null) ? JsonUtil.getJsonString(args) : null);

        log.setLoglevel(logLevel.getValue());
        log.setLogtype(logType.getValue());

        log.setClientip(getClientIp());

        UserAgent userAgent = UserAgent.parseUserAgentString(request.getHeader("User-Agent"));
        log.setClientbrowser(userAgent.getBrowser().getName());
        log.setClientos(userAgent.getOperatingSystem().getName());

        String url = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getServletPath();
        if (request.getQueryString() != null) {
            url += "?" + request.getQueryString();
        }
        log.setUrl(url);
        try {
            if (request.getCookies().length > 0) {
                Optional<Cookie> cookie = Arrays.stream(request.getCookies()).filter(c -> "_ctid".equals(c.getName())).findFirst();
                if (cookie.isPresent()) {
                    String moduleId = cookie.get().getValue();
                    List<SysModule> moduleList = moduleService.selectList();
                    Optional<SysModule> module = moduleList.stream().filter(m -> m.getId() == Integer.parseInt(moduleId)).findFirst();
                    module.ifPresent(sysModule -> log.setMcode(sysModule.getMcode()));
                }
            }
            // 未能根据MId找到模块
            if (StringUtils.isEmpty(log.getMcode())) {
                List<SysModule> moduleList = moduleService.selectList();
                if (!StringUtils.isEmpty(alias)) {
                    Optional<SysModule> module = moduleList.stream().filter(m -> Objects.equals(m.getAlias(), alias)).findFirst();
                    module.ifPresent(sysModule -> log.setMcode(sysModule.getMcode()));
                } else {
                    String path = request.getServletPath();
                    Optional<SysModule> moduleItem = moduleList.stream().filter(module -> module.getMurl().equals(path)).findFirst();
                    if (!moduleItem.isPresent()) {
                        Uri uri = new Uri(request.getHeader("referer"));
                        moduleItem = moduleList.stream().filter(module -> module.getMurl().equals(uri.getPath())).findFirst();
                    }
                    moduleItem.ifPresent(sysModule -> log.setMcode(sysModule.getMcode()));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        log.setCreatetime(new Date());
        log.setCreaterid(currentUserLogin() == null ? "" : currentUserLogin().getUserid().toString());
        log.setCreater(currentUserLogin() == null ? "" : currentUserLogin().getUsername());
        try {
            logService.insert(log);
        } catch (Exception e) {
            LogUtil.warn("日志写入错误", e);
        }
    }

    public String getClientIp() {
        String ip = request.getHeader("x-forwarded-for");
        if ((ip == null) || (ip.length() == 0) || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if ((ip == null) || (ip.length() == 0) || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if ((ip == null) || (ip.length() == 0) || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        ip = "0:0:0:0:0:0:0:1".equals(ip) ? "127.0.0.1" : ip;
        return ip;
    }

    /**
     * 根据当前部门code生成下级部门code
     *
     * @param code
     * @return
     */
    public Map.Entry<Boolean, String> generateDeptSubCode(String code) {
        HashMap map = new HashMap();
        List<SysDepartment> subList = deptService.getSubDepartments(code);
        Integer currentMax = 0;
        List<Integer> subCodeList = new ArrayList<>();

        if (subList.size() > 0) {
            for (SysDepartment sysDepartment : subList) {
                subCodeList.add(Integer.parseInt(sysDepartment.getDcode()
                        .substring(sysDepartment.getDcode().length() - 3,
                                sysDepartment.getDcode().length())));
            }

            Collections.sort(subCodeList, Comparator.reverseOrder());
            currentMax = subCodeList.get(0);
        }

        if (currentMax >= 999) {
            map.put(false, "部门数量已到上限，不可以继续创建");
        } else {
            map.put(true, code + String.format("%03d", currentMax + 1));
        }

        java.util.Iterator it = map.entrySet().iterator();

        if (it.hasNext()) {
            return (java.util.Map.Entry) it.next();
        }

        return null;
    }

    /**
     * 查询当前code所有子节点
     *
     * @param code
     * @return
     */
    public List<SysDepartment> getDepartmentList(String code) {
        return deptService.getSubsetsBranchByCode(code);
    }

    /**
     * 包含空格
     *
     * @param code
     * @return
     */
    public List<SysDepartment> getDepartmentListIsOffSet(String code) {
        List<SysDepartment> list = deptService.getSubsetsBranchByCode(code);
        for (SysDepartment sysDepartment : list
                ) {
            int leave = sysDepartment.getDcode().length() / 3;
            String dName = sysDepartment.getDname();
            if (leave > 1) {
                for (int i = 1; i < leave; i++) {
                    dName = "　" + dName;
                }

            }
            sysDepartment.setDname(dName);

        }
        return list;
    }

    /**
     * 部门对象转成树形对象
     *
     * @param nodes
     * @param all
     * @return
     */
    public List<TreeJsonEntity> modules2TreeJsonEntity(List<SysModule> nodes, List<SysModule> all) {
        List<TreeJsonEntity> returnList = new ArrayList<>();

        for (SysModule item : nodes) {
            TreeJsonEntity entity = new TreeJsonEntity();

            entity.id = item.getId();
            entity.code = item.getMcode();
            entity.text = item.getMname();
            entity.value = item.getId().toString();
            entity.complete = true;
            entity.hasChildren = all.stream().filter(l -> l.getParentcode().equals(item.getMcode())).count() > 0;
            entity.childNodes = modules2TreeJsonEntity(all.stream()
                            .filter(l -> l.getParentcode().equals(item.getMcode()))
                            .collect(Collectors.toList()),
                    all);
            entity.img = ((item.getMicon() == null) || item.getMicon().isEmpty())
                    ? "angle-right"
                    : item.getMicon().trim();
            entity.extra = new ModuleExtraObject((StringUtil.isEmpty(item.getMurl()) ? "无" : item.getMurl()),
                    item.getState(),
                    item.getSortnum(),
                    item.getRemark(),
                    item.getMtype());
            returnList.add(entity);
        }

        return returnList;
    }

    /**
     * Class description
     *
     * @author V.E.
     * @version 1.0, 17/12/7
     */
    class ModuleExtraObject {

        public ModuleExtraObject(String murl, Integer state, Integer sortNum, String remark, String moduleType) {
            this.murl = murl;
            this.state = state;
            this.remark = remark;
            this.moduleType = moduleType;
            this.sortNum = sortNum;
        }

        public String murl;

        public Integer state;

        public String remark;

        public String moduleType;

        public Integer sortNum;
    }

    /**
     * 包含空格
     *
     * @param code
     * @return
     */
    public List<SysCategory> getCategoryListIsOffSet(String code, TypeEnums.CategoryType categoryType) {
        List<SysCategory> list = sysCategoryService.getSubsetsBranchByCode(code, categoryType.getValue());
        for (SysCategory sysCategory : list
                ) {
            int leave = sysCategory.getCode().length() / 3;
            String dName = sysCategory.getName();
            if (leave > 1) {
                for (int i = 1; i < leave; i++) {
                    dName = "　" + dName;
                }
            }
            sysCategory.setName(dName);

        }
        return list;
    }


    /**
     * 根据日期long获取日期字符串
     *
     * @return
     */
    public static String toDateString(long l) {
        Calendar ca = Calendar.getInstance();
        ca.setTimeInMillis(l);
        SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return fmt.format(ca.getTime());
    }


    /**
     * format
     *
     * @param logType
     * @param menuInfo
     * @param keyName
     * @param isSuccess
     * @return
     */
    public String logFormat(SysLog.LogType logType, String menuInfo, String keyName, TypeEnums.IsSuccess isSuccess) {
        return currentUserLogin().getUsername().concat(logType.toString()).concat(menuInfo).concat("'").concat(keyName).concat("'").concat(isSuccess.toString());
    }


    /**
     * 获取验证码图片
     *
     * @return
     */
    @AuthorizeIgnore
    @GetMapping("/getKaptchaImage")
    public void getKaptchaImage(HttpServletRequest request,
                                HttpServletResponse response) throws Exception {
        new CaptchaUtil().getRandcode(request, response);
    }

    /**
     * 判断当前用户是否在线
     *
     * @return
     */
    public boolean isOnline() {
        HttpSession session = request.getSession(true);
        SysUserlogin userLogin = (SysUserlogin) session.getAttribute("currentUserLogin");
        if (userLogin != null) {
            Cookie token = CookieUtil.getCookieByName(request, "token");
            if (token != null && token.getValue() != "" && token.getValue().equals(userLogin.getLogintoken())) {
                return true;
            } else {
                return false;
            }
        }
        return false;
    }

    /**
     * 根据js_code获取openid
     *
     * @return
     */
    public String getOpenid(String js_code) {
        if (js_code == null || js_code.equals(""))
            throw new BusinessException("js_code不能为空！");
        try {
            Map<String, String> header = new HashMap<>();
            header.put(HttpConst.CONTENT_TYPE, "application/json");
            Map<String, String> data = new HashMap<>();
            data.put("appid", appid);
            data.put("secret", secret);
            data.put("js_code", js_code);
            data.put("grant_type", grant_type);
            String text = HttpKit.get(openIdApiUrl, data, Charset.defaultCharset()).getHtml();
            String openid = JSON.parseObject(text).get("openid").toString();
            return openid;
        } catch (Exception e) {
            throw new BusinessException("获取openid失败！");
        }
    }
}

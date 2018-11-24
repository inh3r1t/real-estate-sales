package com.zx.system.controller;

import com.zx.base.annotation.FormReSubmitValidation;
import com.zx.base.common.IterateDirUtil;
import com.zx.base.common.ValidateUtil;
import com.zx.base.controller.BaseController;
import com.zx.base.model.DirBean;
import com.zx.base.model.ReturnModel;
import com.zx.base.model.RootBean;
import com.zx.lib.json.JsonUtil;
import com.zx.lib.utils.DirectoryUtil;
import com.zx.lib.utils.StringUtil;
import com.zx.system.model.SysLog;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 文件管理
 *
 * @author V.E.
 * @version 2017/12/11
 */
@Controller
@RequestMapping("/file")
public class FileController extends BaseController {

    /**
     * (\d*)(\w*) 匹配文件大小（正则表达式预编译）
     */
    private Pattern sizePattern = Pattern.compile("(\\d*)(\\w*)", Pattern.CASE_INSENSITIVE);

    /**
     * 文件管理页
     */
    @RequestMapping("/index")
    public String index(Model model) throws Exception {
        File file = new File(basePath);

        double total = file.getTotalSpace() / (double) (1024 * 1024 * 1024);
        double usedSpace = (file.getTotalSpace() - file.getFreeSpace()) / (double) (1024 * 1024 * 1024);
        double freeSpace = file.getFreeSpace() / (double) (1024 * 1024 * 1024);

        String percent = String.format("%.2f", (usedSpace / total) * 100);
        model.addAttribute("totalSpace", String.format("%.2f", total) + "G");
        model.addAttribute("usedSpace", String.format("%.2f", usedSpace) + "G");
        model.addAttribute("freeSpace", String.format("%.2f", freeSpace) + "G");
        model.addAttribute("percent", percent + "%");
        return "system/file/index";
    }


    /**
     * 编辑文件夹
     *
     * @param path 当前文件夹路径
     * @return
     */
    @FormReSubmitValidation
    @RequestMapping("/editDir")
    public String editDir(Model model, String path) {
        model.addAttribute("path", path);
        return "system/file/edit_dir";
    }

    /**
     * 编辑文件夹
     *
     * @param path 文件夹所在路径
     * @param name 文件夹名称
     * @return
     */
    @FormReSubmitValidation(true)
    @RequestMapping(value = "submitDir", method = RequestMethod.POST)
    @ResponseBody
    public Object submit(String path, String name) {
        ReturnModel rm = new ReturnModel();
        // 对表单进行验证
        if (!ValidateUtil.charString(name)) {
            rm.setState(false);
            rm.setMessage("文件夹名只能中文、英文、数字、下划线等字符");
            return rm;
        }
        if (!StringUtil.isEmpty(path) && !StringUtil.isEmpty(name)) {
            try {
                String p = basePath + path;
                if (!p.endsWith(("/"))) {
                    p = p + "/";
                }
                String newPath = p + name;
                if (!DirectoryUtil.isExsit(newPath)) {
                    if (DirectoryUtil.create(newPath)) {
                        rm.setState(true);
                        rm.setMessage("文件夹《" + name + "》创建成功");
                    } else {
                        rm.setState(false);
                        rm.setMessage("文件夹创建失败");
                    }
                } else {
                    rm.setState(false);
                    rm.setMessage("文件夹名称已经存在");
                }

            } catch (Exception ex) {
                rm.setState(false);
                rm.setMessage("文件夹创建失败");
            }
        } else {
            rm.setState(false);
            rm.setMessage("文件夹路径有误");
        }
        return rm;
    }

    /**
     * 遍历磁盘
     */
    @RequestMapping("/iterateRoot")
    public void iterateRoot(HttpServletResponse response) throws IOException {
        List<RootBean> roots = IterateDirUtil.getDiskInfo();
        if (roots != null) {
            String jsonArray = JsonUtil.getJsonString(roots);
            response.setHeader("Content-type", "text/html;charset=UTF-8");
            response.setCharacterEncoding("UTF-8");
            response.getWriter().print(jsonArray);
        }
    }

    /**
     * 遍历指定文件夹
     */
    @RequestMapping("/iterateDir")
    public void iterateDir(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String requestPath = request.getParameter("path").trim();
        response.setHeader("Content-type", "text/html;charset=UTF-8");
        response.setCharacterEncoding("UTF-8");
        if (!StringUtil.isEmpty(requestPath)) {
            String nowPath = basePath + requestPath;
            if (!DirectoryUtil.isExsit(nowPath)) {
                DirectoryUtil.create(nowPath);
            }
            if (nowPath.endsWith("/")) {
                nowPath = nowPath.substring(0, nowPath.length() - 1);
            }
            try {
                DirBean dirBean = IterateDirUtil.getFiles(nowPath, basePath);
                String jsonArray = JsonUtil.getJsonString(dirBean);
                PrintWriter out = response.getWriter();
                out.print(jsonArray);
                //释放资源
                out.flush();
                out.close();
            } catch (Exception e) {
                addLogError("获取分类列表异常", SysLog.LogType.其他, e);
            }
        }
    }


    /**
     * 文件管理 - 上传
     *
     * @param model
     * @param path
     * @return
     */
    @RequestMapping("/uploadPage")
    public String uploadPage(Model model, String path) {
        model.addAttribute("path", path);
        Matcher m = sizePattern.matcher(MAX_FILE_SIZE);
        // 默认50Mb
        float size = 0;
        String unit;
        if (m.find()) {
            size = Integer.parseInt(m.group(1));
            unit = m.group(2);
            if ("KB".equals(unit.toUpperCase())) {
            } else if ("MB".equals(unit.toUpperCase())) {
                size = size * 1024;
            } else if ("GB".equals(unit.toUpperCase())) {
                size = size * 1024 * 1024;
            } else {
                size = 50 * 1024;
            }
        }
        model.addAttribute("MAX_FILE_SIZE", size);
        return "system/file/upload_page";
    }


    /**
     * 展示磁盘文件用 - 便于选择磁盘文件
     *
     * @param model
     * @param frameName
     * @return
     * @throws Exception
     */
    @RequestMapping("/viewDisk")
    public String viewDisk(Model model, String frameName) throws Exception {
        model.addAttribute("frameName", frameName);
        return "system/file/view_disk";
    }
}

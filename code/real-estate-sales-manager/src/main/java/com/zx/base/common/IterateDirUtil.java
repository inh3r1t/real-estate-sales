package com.zx.base.common;

import com.zx.base.model.DirBean;
import com.zx.base.model.FileBean;
import com.zx.base.model.RootBean;
import com.zx.lib.utils.DateUtil;

import java.io.File;
import java.io.FileInputStream;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.LinkedList;
import java.util.List;

/**
 * 循环文件夹工具类
 *
 * @author V.E.
 * @version 2017/12/11
 */

public class IterateDirUtil {
    /**
     * 获取根目录信息
     */
    public static List<RootBean> getDiskInfo() {
        //获取盘符
        File[] files = File.listRoots();
        List<RootBean> roots = new ArrayList<RootBean>();
        for (File file : files) {
            if (file.getTotalSpace() != 0) {
                RootBean rootBean = new RootBean();
                rootBean.setDiskPath(file.getAbsolutePath().replace("\\", "/"));
                rootBean.setDiskName(file.getAbsolutePath().charAt(0) + "");
                rootBean.setDiskSize(formatFileSize(file.getTotalSpace()));
                rootBean.setAvilableSize(formatFileSize(file.getFreeSpace()));
                roots.add(rootBean);
            }
        }
        return roots;
    }

    /**
     * 通过递归得到某一路径下所有的目录及其文件
     */
    public static DirBean getFiles(String dirPath, String basePath) throws Exception {
        File root = new File(dirPath);
        DirBean dirBean = null;
        if (root.exists()) {
            dirBean = new DirBean();
            String dirSize = "";
            int dirCount = 0;
            List<FileBean> filelist = new LinkedList<FileBean>();
            if (root.isDirectory()) {
                File[] files = root.listFiles();
                for (File file : files) {
                    FileBean fileBean = new FileBean();
                    String realPath = file.getAbsolutePath();
                    String fileName = getFileName(realPath);
                    fileBean.setFileName(fileName);

                    // TODO : long转日期类型
                    Calendar cal = Calendar.getInstance();
                    cal.setTimeInMillis(file.lastModified());
                    fileBean.setFileLastModifiedTime(
                            DateUtil.toDateString(cal));
                    realPath = realPath.replace("\\", "/");
                    if (realPath.startsWith(basePath)) {
                        realPath = realPath.substring(basePath.length());
                    }
                    fileBean.setFilePath(realPath);
                    if (file.isDirectory()) {
                        fileBean.setFileType("DIR");
                        fileBean.setFileSize("");
                    } else {
                        fileBean.setFileType(getFileType(fileName));
                        fileBean.setFileSize(formatFileSize(getFileSizes(file)));
                    }
                    filelist.add(fileBean);
                }
            } else {
                dirSize = formatFileSize(getFileSizes(root));
            }
            dirBean.setDirCount(dirCount);
            dirBean.setDirSize(dirSize);
            if (dirPath.startsWith(basePath)) {
                dirPath = dirPath.substring(basePath.length());
            }
            dirBean.setDirPath(dirPath);
            dirBean.setFiles(filelist);
            if (!dirPath.equals(basePath)) {
                String prevPath = dirPath.substring(0, dirPath.lastIndexOf("/") + 1);
                dirBean.setPrevPath(prevPath);
            }

        }
        return dirBean;
    }

    public static String getFileType(String fileName) {
        return fileName.substring(fileName.lastIndexOf(".") + 1).toUpperCase();
    }

    /**
     * 获取文件名
     *
     */
    public static String getFileName(String filePath) {
        filePath = filePath.replace("/", "\\");
        String[] fileItems = filePath.split("\\\\");
        return fileItems[fileItems.length - 1];
    }

    /**
     * 取得文件大小
     *
     */
    public static long getFileSizes(File f) throws Exception {
        long s = 0;
        if (f.exists()) {
            s = f.length();
        }
        return s;
    }

    /**
     * 取得文件夹大小
     *
     */
    public static long getFileSize(File f) throws Exception {
        long size = 0;
        File[] flist = f.listFiles();
        for (int i = 0; i < flist.length; i++) {
            if (flist[i].isDirectory()) {
                size = size + getFileSize(flist[i]);
            } else {
                size = size + flist[i].length();
            }
        }
        return size;
    }

    public static String formatFileSize(long fileS) {//转换文件大小
        DecimalFormat df = new DecimalFormat("#.00");
        String fileSizeString = "";
        if (fileS < 1024) {
            fileSizeString = df.format((double) fileS) + "B";
        } else if (fileS < 1048576) {
            fileSizeString = df.format((double) fileS / 1024) + "KB";
        } else if (fileS < 1073741824) {
            fileSizeString = df.format((double) fileS / 1048576) + "M";
        } else {
            fileSizeString = df.format((double) fileS / 1073741824) + "G";
        }
        return fileSizeString;
    }

    public static long getlist(File f) {//递归求取目录文件个数
        long size = 0;
        File[] flist = f.listFiles();
        size = flist.length;
        for (int i = 0; i < flist.length; i++) {
            if (flist[i].isDirectory()) {
                size = size + getlist(flist[i]);
                size--;
            }
        }
        return size;

    }
}

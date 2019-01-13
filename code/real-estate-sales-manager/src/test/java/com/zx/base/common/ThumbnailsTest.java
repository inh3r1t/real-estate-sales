package com.zx.base.common;

import com.zx.lib.utils.DirectoryUtil;
import net.coobird.thumbnailator.Thumbnails;
import org.junit.Test;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

public class ThumbnailsTest {
    @Test
    public void thumbnail() {
        String path = "C:\\Users\\Weiyi\\Desktop\\xiaochengxuyasuo\\upload_files";
        try {
            List<File> files = DirectoryUtil.getFiles(path, false);
            List<File> files2 = new ArrayList<>();
            for (File item : files) {
                if (item.isDirectory()) {
                    files2.addAll(Arrays.asList(item.listFiles()));
                } else {
                    files2.add(item);
                }
            }
            for (File item : files2) {
                System.out.println("--旧-->" + item.getPath());
                String thumPath = item.getPath().replace("upload_files", "upload_files2").replace(".png", "");
                File tempFile = new File(thumPath);
                if (!tempFile.getParentFile().exists()) {
                    tempFile.getParentFile().mkdirs();
                }
                if (item.length() > 5 * 1024 * 1024) {
                    Thumbnails.of(item.getPath())
                            .scale(1f)
                            .outputQuality(0.2f)
                            .outputFormat("jpg")
                            .toFile(thumPath);
                    System.out.println("--新-->" + thumPath + " ---> 0.2f");
                } else if (item.length() > 3 * 1024 * 1024) {
                    Thumbnails.of(item.getPath())
                            .scale(1f)
                            .outputQuality(0.3f)
                            .outputFormat("jpg")
                            .toFile(thumPath);
                    System.out.println("--新-->" + thumPath + " ---> 0.3f");
                } else if (item.length() > 1 * 1024 * 1024) {
                    Thumbnails.of(item.getPath())
                            .scale(1f)
                            .outputQuality(0.5f)
                            .outputFormat("jpg")
                            .toFile(thumPath);
                    System.out.println("--新-->" + thumPath + " ---> 0.5f");
                } else if (item.length() > 0.5 * 1024 * 1024) {
                    Thumbnails.of(item.getPath())
                            .scale(1f)
                            .outputQuality(0.8f)
                            .outputFormat("jpg")
                            .toFile(thumPath);
                    System.out.println("--新-->" + thumPath + " ---> 0.8f");
                } else {
                    // copyFile(item.getPath(), item.getPath().replace("upload_files", "upload_files2"));
                    Thumbnails.of(item.getPath())
                            .scale(1f)
                            .outputQuality(1f)
                            .outputFormat("jpg")
                            .toFile(thumPath);
                    System.out.println("--新-->" + thumPath + " ---> 1.0f");
                }
                // Thumbnails.of(item.getPath())
                //         .scale(1f)
                //         .outputQuality(1f)
                //         .outputFormat("jpg")
                //         .toFile(item.getParent() + "/1.0-thumbnail." + item.getName());
                // Thumbnails.of(item.getPath())
                //         .scale(1f)
                //         .outputQuality(0.9f)
                //         .outputFormat("jpg")
                //         .toFile(item.getParent() + "/0.9-thumbnail." + item.getName());
                // Thumbnails.of(item.getPath())
                //         .scale(1f)
                //         .outputQuality(0.8f)
                //         .outputFormat("jpg")
                //         .toFile(item.getParent() + "/0.8-thumbnail." + item.getName());
                // Thumbnails.of(item.getPath())
                //         .scale(1f)
                //         .outputQuality(0.7f)
                //         .outputFormat("jpg")
                //         .toFile(item.getParent() + "/0.7-thumbnail." + item.getName());
                // Thumbnails.of(item.getPath())
                //         .scale(1f)
                //         .outputQuality(0.6f)
                //         .outputFormat("jpg")
                //         .toFile(item.getParent() + "/0.6-thumbnail." + item.getName());
                // Thumbnails.of(item.getPath())
                //         .scale(1f)
                //         .outputQuality(0.5f)
                //         .outputFormat("jpg")
                //         .toFile(item.getParent() + "/0.5-thumbnail." + item.getName());
                // Thumbnails.of(item.getPath())
                //         .scale(1f)
                //         .outputQuality(0.4f)
                //         .outputFormat("jpg")
                //         .toFile(item.getParent() + "/0.4-thumbnail." + item.getName());
                // Thumbnails.of(item.getPath())
                //         .scale(1f)
                //         .outputQuality(0.3f)
                //         .outputFormat("jpg")
                //         .toFile(item.getParent() + "/0.3-thumbnail." + item.getName());
                // Thumbnails.of(item.getPath())
                //         .scale(1f)
                //         .outputQuality(0.2f)
                //         .outputFormat("jpg")
                //         .toFile(item.getParent() + "/0.2-thumbnail." + item.getName());
                // Thumbnails.of(item.getPath())
                //         .scale(1f)
                //         .outputQuality(0.1f)
                //         .outputFormat("jpg")
                //         .toFile(item.getParent() + "/0.1-thumbnail." + item.getName());

            }
        } catch (Exception ex) {
            System.out.printf("异常");
        }
        System.out.println("完成");
    }

    public void copyFile(String oldPath, String newPath) {
        try {
            int bytesum = 0;
            int byteread = 0;
            File oldfile = new File(oldPath);
            if (oldfile.exists()) { //文件存在时
                InputStream inStream = new FileInputStream(oldPath); //读入原文件
                FileOutputStream fs = new FileOutputStream(newPath);
                byte[] buffer = new byte[1444];
                int length;
                while ((byteread = inStream.read(buffer)) != -1) {
                    bytesum += byteread; //字节数 文件大小
                    // System.out.println(bytesum);
                    fs.write(buffer, 0, byteread);
                }
                inStream.close();
            }
        } catch (Exception e) {
            System.out.println("复制单个文件操作出错");
            e.printStackTrace();

        }

    }
}

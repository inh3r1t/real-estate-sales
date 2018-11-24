package com.zx.base.common;

import java.io.*;

/**
 * 数据库操作类
 *
 * @author V.E.
 * @version 2017/12/15
 */
public class DatabaseUtil {

    /**
     * 备份数据库 ,控制台执行命令格式 "mysql的bin目录/mysqldump --databases  -h主机ip -P端口  -u用户名 -p密码  数据库名
     *
     * @param mysqldumpPath mysqldump路径
     * @param mysqlIp       mysql主机ip
     * @param mysqlPort     端口
     * @param userName      用户名
     * @param password      密码
     * @param database      数据库名
     * @param resultFile    备份文件全路径
     */
    public static void backupMySql(
            String mysqldumpPath,
            String mysqlIp,
            String mysqlPort,
            String userName,
            String password,
            String database,
            String resultFile) {
        File tempFile = new File(resultFile);
        if (!tempFile.getParentFile().exists()) {
            tempFile.getParentFile().mkdirs();
        }
        InputStream in = null;
        InputStreamReader isr = null;
        BufferedReader br = null;
        FileOutputStream fout = null;
        OutputStreamWriter writer = null;
        try {
            Runtime rt = Runtime.getRuntime();

            // System.out.println("/bin/sh -c " + mysqldumpPath + " mysqldump -h " + mysqlIp
            //         + " -P " + mysqlPort + " -u " + userName + " "
            //         + database + " | gzip >" + resultFile);
            // 调用mysql的安装目录的命令
            // mysql配置文件[mysqldump]添加用户密码，这里密码不需要
            Process process = rt.exec("/bin/sh -c " + mysqldumpPath + " mysqldump -h " + mysqlIp
                    + " -P " + mysqlPort + " -u " + userName + " "
                    + database + " | gzip >" + resultFile);
            // 设置导出编码为utf-8。这里必须是utf-8
            // 控制台的输出信息作为输入流
            in = process.getInputStream();
            // ErrorStreamThread errStream = new ErrorStreamThread(process.getErrorStream()); //错误流另开线程，不然会阻塞
            // errStream.start();
            // 设置输出流编码为utf8。这里必须是utf8，否则从流中读入的是乱码
            isr = new InputStreamReader(in, "utf8");

            String inStr;
            StringBuffer sb = new StringBuffer("");
            String outStr;
            // 组合控制台输出信息字符串
            br = new BufferedReader(isr);
            while ((inStr = br.readLine()) != null) {
                sb.append(inStr + "\r\n");
            }
            outStr = sb.toString();

            // 要用来做导入用的sql目标文件：
            fout = new FileOutputStream(resultFile);
            writer = new OutputStreamWriter(fout, "utf8");
            writer.write(outStr);
            // 注：这里如果用缓冲方式写入文件的话，会导致中文乱码，用flush()方法则可以避免
            writer.flush();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (writer != null) {
                    writer.close();
                }
                if (fout != null) {
                    fout.close();
                }
                if (br != null) {
                    br.close();
                }
                if (isr != null) {
                    isr.close();
                }
                if (in != null) {
                    in.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}

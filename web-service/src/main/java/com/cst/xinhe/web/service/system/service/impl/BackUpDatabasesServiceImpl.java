package com.cst.xinhe.web.service.system.service.impl;

import com.cst.xinhe.system.service.service.BackUpDatabasesService;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;

/**
 * @author wangshuwen
 * @Description:
 * @Date 2019/6/11/16:49
 */
@Service
public class BackUpDatabasesServiceImpl implements BackUpDatabasesService{

    @Override
    public boolean backup(String hostIP, String userName, String password, String savePath, String fileName, String databaseName) {
        fileName +=".sql";
        File saveFile = new File(savePath);
        if (!saveFile.exists()) {// 如果目录不存在
            saveFile.mkdirs();// 创建文件夹
        }
      /*  if (!savePath.endsWith(File.separator)) {
            savePath = savePath + File.separator;
        }*/

        File saveFile1 = new File(savePath + fileName);
        if (!saveFile1.exists()) {// 如果目录不存在
            try {
                saveFile1.createNewFile();// 创建文件夹
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        //拼接命令行的命令
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("mysqldump").append(" --opt").append(" -h").append(hostIP);
        stringBuilder.append(" --user=").append(userName).append(" --password=").append(password)
                .append(" --lock-all-tables=true");
        stringBuilder.append(" --result-file=").append(savePath + fileName).append(" --default-character-set=utf8 ")
                .append(databaseName);
        try {
            //调用外部执行exe文件的javaAPI
            Process process = Runtime.getRuntime().exec(stringBuilder.toString());
            if (process.waitFor() == 0) {// 0 表示线程正常终止。
                return true;
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean recover(String filepath, String ip, String database, String userName, String password) {
        String stmt1 = "mysqladmin -h "+ip+" -u "+userName+" -p"+password+" create "+database;

        String stmt2 = "mysql -h "+ip+" -u "+userName+" -p "+password+" "+database+" < " + filepath;

        String[] cmd = { "cmd", "/c", stmt2 };

        try {
            Runtime.getRuntime().exec(stmt1);
            Runtime.getRuntime().exec(cmd);
            System.out.println("数据已从 " + filepath + " 导入到数据库中");
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }
}

package com.cst.xinhe.web.service.system.service;

/**
 * @author wangshuwen
 * @Description:
 * @Date 2019/6/11/16:49
 */
public interface BackUpDatabasesService {
      boolean backup(String hostIP, String userName, String password, String savePath, String fileName,
                     String databaseName);

      boolean recover(String filepath, String ip, String database, String userName, String password);
}

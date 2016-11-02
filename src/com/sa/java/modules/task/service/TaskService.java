package com.sa.java.modules.task.service;

import com.sa.java.api.service.ShowService;
import com.sa.java.modules.model.Config;

import java.util.TimerTask;

/**
 * Created by sa
 * Date: 2016/7/5 11:55
 */
public class TaskService extends TimerTask {
    ShowService showService = new ShowService();

    /**
     * 定时清理展播文件
     */
    @Override
    public void run() {
        System.out.println("-------------------开始清理展播文件-------------------");
        Config config = Config.dao.findById(1);
        showService.clearFile(config.getShowFilePath());
    }
}

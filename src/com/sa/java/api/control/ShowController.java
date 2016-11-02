package com.sa.java.api.control;

import com.jfinal.core.Controller;
import com.sa.java.api.service.ShowService;
import com.sa.java.modules.monitor.entity.MyScenery;
import com.sa.java.modules.monitor.entity.MyShow;

import java.util.List;


/**
 * Created by sa
 * Date: 2016/04/05 15:30
 */
public class ShowController extends Controller {
    ShowService showService = new ShowService();

    public void index() {
        //接收参数(showId)
        String id = getPara();

        //不带参数默认获取展播列表
        if (id == null) {
            List<MyScenery> sceneryList = showService.getSceneryList();
            renderJson(sceneryList);
        }

        //带参数获取具体展播信息
        else if (id != null && showService.isInteger(id)) {
            MyShow ms = showService.getShowById(id);
            if (ms != null) {
                renderJson(ms);
            } else {
                renderJson("error", "id not find");
            }

        } else
            renderJson("error", "id not find");


    }


//    public void clear() {
//        showService.clearFile("d:/test");
//        renderNull();
//    }


}

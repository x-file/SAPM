package com.sa.java.modules.task.control;

import com.jfinal.aop.Before;
import com.jfinal.core.Controller;
import com.jfinal.plugin.ehcache.CacheInterceptor;
import com.jfinal.plugin.ehcache.CacheName;
import com.jfinal.plugin.ehcache.EvictInterceptor;
import com.sa.java.modules.model.Task;


/**
 * Created by sa
 * Date: 2016/2/16 15:30
 * 在 ControllerController ControllerController中提供了 getPara  、getModel系列方法 setAttr 方法以及render系列方法供  Action 使用 。
 */
//@Before(TaskInterceptor.class)
public class TaskController extends Controller {
    @Before(CacheInterceptor.class)
    @CacheName("sampleCache1")  //先从缓存中取数据，如果去到数据直接render(测试缓存)
    public void index(){
        setAttr("taskPage", Task.dao.paginate(getParaToInt(0, 1), 20));
        render("task.html");
    }
    public void add(){
    }

    @Before({TaskValidator.class,EvictInterceptor.class})
    @CacheName("sampleCache1")
    public void save(){
        getModel(Task.class).save();
        redirect("/task");
    }

    public void edit(){
        setAttr("task",Task.dao.findById(getParaToInt()));
        //renderJson(Task.dao.findById(getParaToInt()));渲染为JSON数据
    }

    @Before(TaskValidator.class)
    public void update(){
        getModel(Task.class).update();
        redirect("/task");
    }

    @Before(EvictInterceptor.class) // 清除缓存
    @CacheName("sampleCache1")
    public void del(){
        Task.dao.deleteById(getParaToInt());
        redirect("/task");
    }

}

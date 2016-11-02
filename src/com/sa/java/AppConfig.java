package com.sa.java;

import com.jfinal.config.*;
import com.jfinal.kit.PropKit;
import com.jfinal.plugin.activerecord.ActiveRecordPlugin;
import com.jfinal.plugin.c3p0.C3p0Plugin;
import com.jfinal.plugin.ehcache.EhCachePlugin;
import com.jfinal.plugin.redis.RedisPlugin;
import com.jfinal.render.ViewType;
import com.sa.java.api.control.LiveController;
import com.sa.java.api.control.SceneryController;
import com.sa.java.api.control.ShowController;
import com.sa.java.api.control.StatusController;
import com.sa.java.modules.model._MappingKit;
import com.sa.java.modules.monitor.control.MonitorController;
import com.sa.java.modules.sys.control.SysController;
import com.sa.java.modules.task.service.TaskService;

import java.util.Timer;

/**
 * API引导式配置
 * Created by sa
 * Date: 2016/1/13 17:57
 */
public class AppConfig extends JFinalConfig {

    /**
     * 配置常量
     * @param me
     */
    @Override
    public void configConstant(Constants me) {
        // 加载少量必要配置，随后可用PropKit.get(...)获取值
        PropKit.use("com/sa/java/common/config/config.properties");
        //开发模式(JFinal会对每次请求输出报告，如输出本次请求的Controller、Method及请求所携带的参数。)
        me.setDevMode(PropKit.getBoolean("devMode"));
        //视图渲染方式(默认为freeMarker)
        me.setViewType(ViewType.FREE_MARKER);
    }

    /**
     * 配置路由
     * @param me
     */
    @Override
    public void configRoute(Routes me) {
        me.add("/", SysController.class,"/WEB-INF/view/modules/sys");
        //me.add("/task", TaskController.class,"/WEB-INF/view/modules/task");
        //me.add("/cms", FrontController.class,"/WEB-INF/view/modules/cms");
        me.add("/monitor", MonitorController.class,"/WEB-INF/view/modules/monitor");
        me.add("/status", StatusController.class);
        me.add("/api/scenery", SceneryController.class);
        me.add("/api/show", ShowController.class);
        me.add("/api/live", LiveController.class);
    }



    /**
     * 配置插件
     * @param me
     */
    @Override
    public void configPlugin(Plugins me) {
        //数据库连接池
        C3p0Plugin c3p0Plugin = createC3p0Plugin();
        me.add(c3p0Plugin);

        // 配置ActiveRecord插件
        ActiveRecordPlugin arp = new ActiveRecordPlugin(c3p0Plugin);
        me.add(arp);

        //控制台输出SQL
        arp.setShowSql(true);

        //配置缓存Cache
        me.add(new EhCachePlugin());

        //使用Redis
        RedisPlugin bbsRedis = new RedisPlugin("bbs","192.168.1.138");
        me.add(bbsRedis);

        // 所有配置在 MappingKit 中搞定
        _MappingKit.mapping(arp);
    }

    /**
     * 配置全局拦截器
     * @param me
     */
    @Override
    public void configInterceptor(Interceptors me) {

    }

    /**
     * 配置处理器
     * @param me
     */
    @Override
    public void configHandler(Handlers me) {

    }


    /**
     * 数据库连接
     * @return
     */
    public static C3p0Plugin createC3p0Plugin() {
        return new C3p0Plugin(PropKit.get("jdbcUrl"), PropKit.get("user"), PropKit.get("password").trim());
    }


    /**
     * 定时任务
     */
    private Timer timer  = new Timer();
    @Override
    public void afterJFinalStart() {
        super.afterJFinalStart();
        //程序启动5分钟后开始执行第一次任务，之后每隔2小时执行一次
        timer.schedule(new TaskService(), 5*60*1000, 2*60*60*1000);
    }

    @Override
    public void beforeJFinalStop() {
        super.beforeJFinalStop();
        System.out.println("-------------------文件清理任务结束-------------------");
        timer.cancel();
    }

}

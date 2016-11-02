package com.sa.java.api.control;

import com.jfinal.core.Controller;
import com.jfinal.plugin.ehcache.CacheKit;
import com.sa.java.modules.model.SceneryDict;
import com.sa.java.modules.monitor.entity.MySceneryDict;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by sa
 * Date: 2016/04/05 15:30
 */
public class SceneryController extends Controller {

    /**
     * 无参数，则获取全部景区
     * 有参数(景区ID),则获取相应的景区信息
     * @Before(CacheInterceptor.class)  将action中的数据缓存起来
     * @CacheName("sampleCache1")   默认为actionKey,注解后为：sampleCache1
     */
    //@Before(CacheInterceptor.class)
    // @CacheName("sampleCache1")
    public void index() {
        getResponse().setHeader("Access-Control-Allow-Origin", "*");
        String id = getPara();//景区ID
        if (id != null && !id.equals("")) {
            SceneryDict sd = SceneryDict.dao.findById(id);
            if(sd!=null) {
                setAttr("scenery", new MySceneryDict(sd.getZoneId().toString(), sd.getScenery()));
            }
        } else {
            List<MySceneryDict>mySceneryList = new ArrayList<MySceneryDict>();
            List<SceneryDict> sceneryList = CacheKit.get("sampleCache1", "sceneryList");//先从缓存中取数据
            if (sceneryList == null) {
                sceneryList = SceneryDict.dao.find("select * from scenery_dict");
                CacheKit.put("sampleCache1", "sceneryList", sceneryList);
            }
            for (SceneryDict dict : sceneryList) {
                mySceneryList.add(new MySceneryDict(dict.getZoneId().toString(), dict.getScenery()));
            }
            setAttr("sceneryList", mySceneryList);
        }

        renderJson();
    }


}

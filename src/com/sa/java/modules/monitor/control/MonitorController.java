package com.sa.java.modules.monitor.control;

import com.jfinal.aop.Before;
import com.jfinal.core.Controller;
import com.jfinal.plugin.ehcache.CacheInterceptor;
import com.jfinal.plugin.ehcache.CacheName;
import com.jfinal.plugin.redis.Cache;
import com.jfinal.plugin.redis.Redis;
import com.sa.java.api.service.ShowService;
import com.sa.java.common.util.FileList;
import com.sa.java.modules.model.SceneryHost;
import com.sa.java.modules.monitor.entity.MyCam;
import com.sa.java.modules.monitor.entity.MyScenery;
import com.sa.java.modules.monitor.entity.MyShow;
import com.sa.java.modules.monitor.service.MonitorService;

import java.util.*;


/**
 * Created by sa
 * Date: 2016/04/05 15:30
 */
public class MonitorController extends Controller {
    MonitorService ms = new MonitorService();

    @Before(CacheInterceptor.class)
    @CacheName("sampleCache1")
    public void index() {
        //景区列表(景区名称从scenery_dict表中取)
        //setAttr("resorts", Resort.dao.find("select * from resort"));
        setAttr("scenerys", SceneryHost.dao.find("select * from scenery_host sh ,scenery_dict sd where sh.scenery_id = sd.zoneId"));
        render("index.html");
    }

    /**
     * 获取景区下的摄像点
     * 参数：景区ID
     */
    public void cam() {
        String id = getPara("sceneryId");
        String cams = ms.getCams(id);
        setAttr("cams", cams);
        renderJson();
    }

    /**
     * 获取摄像机能力
     * 视频流、是否可控
     * 参数：camName
     */
    //@CacheName("sampleCache1")
    public void state() {
        MyCam cam = ms.getState(getPara("camName"));
//        //推流后返回输出流
//        String rtmp = Ffmpeg.pushRtsp(cam.getRtmp());
//        //更新输出流
//        cam.setRtmp(rtmp);

        setAttr("cam", cam);
        renderJson();
    }

    public void file() throws Exception {
        Map<String, List<String>> map = new HashMap<String, List<String>>();
        Map<String, List<String>> myMap = new HashMap<String, List<String>>();
        //景区列表
        List<MyScenery> sceneryList = new ArrayList<MyScenery>();

        //获取全部文件列表
        //FileList fl = new FileList("d:/test");                  //windows
        FileList fl = new FileList(ShowService.config.getShowFilePath());
        List<String> files = fl.getAllFiles(ShowService.config.getShowFilePath());


        //map:key为 “景区_展播”，value为所有视频
        for (String s : files) {
            String[] f = s.split("_");
            String scenery = f[1];
            String spot = f[2];
            String key = scenery + "_" + spot;
            List<String> list = map.get(key);
            if (list == null) {
                list = new ArrayList<String>();
            }
            list.add(s);
            map.put(key, list);
        }

        //myMap: key为“景区” value为最新视频
        Iterator<String> iter = map.keySet().iterator();
        while (iter.hasNext()) {
            String key = iter.next();
            List<String> values = map.get(key);
            values.sort(null);
            //最新文件
            String value = values.get(values.size() - 1);
            //System.out.println("key:"+key+"   value:"+value);

            String[] val = value.split("_");
            String myKey = val[1];
            List<String> list = myMap.get(myKey);
            if (list == null) {
                list = new ArrayList<String>();
            }
            list.add(value);
            myMap.put(myKey, list);

        }
        //System.out.println(myMap);


        //循环myMap:key为"展播",将文件列表层次化，方便输出json
        Iterator<String> it = myMap.keySet().iterator();
        while (it.hasNext()) {
            String key = it.next();
            List<String> values = myMap.get(key);
            List<MyShow> showList = new ArrayList<MyShow>();//创建Show对象
            for (String s : values) {
                String[] str = s.split("_");

                MyShow show = new MyShow();
                show.setShowId(str[2]);
                show.setShowName("展播" + str[2]);
                show.setShowUrl(str[2] + "/" + s);
                showList.add(show);
            }
            MyScenery scenery = new MyScenery();
            scenery.setSceneryId(key);
            scenery.setSceneryName("景区" + key);
            scenery.setShowList(showList);
            sceneryList.add(scenery);

        }

        //System.out.println("key:"+key+"   values:"+values);
        renderJson("sceneryList", sceneryList);


        //renderJson("files",files);
    }

    //选择摄像点
    public void choose(){
        String sceneryId = getPara("sceneryId");
        //String camName = getPara("camName");
        ms.setHost(sceneryId);
        renderNull();
    }

    //控制摄像机运动
    public void ctrl() {
        String sceneryId = getPara("sceneryId");
        String camName = getPara("camName");
        String command = getPara("command").replaceAll("ctrl_", "");
        //执行控制命令

        ms.ctrlCam(sceneryId,camName, command);
        renderNull();
    }

    //关闭直播
    public void close() {
        ms.closeStream();
        renderNull();
    }


    public void redisDemo(){
        Cache cache = Redis.use("bbs");
        cache.select(2);
        cache.set("test","helloRedis");

        String test = cache.get("test");

        renderJson(test);
    }

//        Jedis jedis = new Jedis("192.168.1.138",6379);
//        jedis.set("addr","beijing");
//        String addr = jedis.get("addr");





}

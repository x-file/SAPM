package com.sa.java.api.service;

import com.jfinal.plugin.ehcache.CacheKit;
import com.sa.java.common.util.FileList;
import com.sa.java.modules.model.Config;
import com.sa.java.modules.model.SceneryDict;
import com.sa.java.modules.model.SceneryShow;
import com.sa.java.modules.monitor.entity.MyScenery;
import com.sa.java.modules.monitor.entity.MyShow;

import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.regex.Pattern;

/**
 * Created by sa
 * Date: 2016/5/5 15:52
 */
public class ShowService {

    public static Config config = Config.dao.findById(1);

    /**
     * 清理展播文件
     */
    public void clearFile(String path) {
//        String path = config.getShowFilePath();//展播文件存储位置
        try {
            FileList fl = new FileList(path);//文件处理工具类
            List<String> folderlist = fl.getFolders();//所有景区的文件夹(一个景区一个文件夹)
            //循环全部景区
            for (String folder : folderlist) {
                List<String> sceneryFiles = fl.getFiles(folder);//一个景区的全部展播文件

                HashSet<String> hashsSet = new HashSet<String>();
                //循环单个景区
                for (String sceneryFile : sceneryFiles) {
                    String[] str = sceneryFile.split("_");//展播文件格式：20160705074712_1_2_1.flv (时间_景区_景点_分辨率.flv)
                    hashsSet.add(str[2]);//(景点)展播ID放入HashSet去重复
                }

                //循环景点(展播)hashSet
                Iterator<String> it = hashsSet.iterator();
                while (it.hasNext()) {
                    String showId = it.next();
                    List<String> showFiles = new ArrayList<String>();
                    //再次循环单个景区，取出单个景点的全部展播文件，存入showFiles
                    for (String showFile : sceneryFiles) {
                        String[] str = showFile.split("_");//展播文件格式：20160705074712_1_2_1.flv (时间_景区_景点_分辨率.flv)
                        if (showId.equals(str[2])) {
                            showFiles.add(showFile);
                        }
                    }

                    //对一个景点的展播列表进行排序
                    Collections.sort(showFiles);
                    // 同一个展播如果多于3个，则保留最近3条展播
                    if (showFiles != null && showFiles.size() > 0) {
                        if (showFiles.size() > 3) {
                            for (int i = 0; i < showFiles.size() - 3; i++) {
                                System.out.println("删除：" + folder + showFiles.get(i));
                                deleteFile(folder + showFiles.get(i));
                            }
                        }
                    }

                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    /**
     * 获取最新的展播文件
     * @param SceneryId
     * @param showId
     * @return
     * @throws IOException
     */
    public String getLatestFile(String SceneryId, String showId) throws IOException {
        //展播文件列表
        List<String> showList = new ArrayList<String>();

        //获取全部文件列表
//        String path = "d:/test/" + SceneryId;                 //windows
        String path = config.getShowFilePath() + SceneryId + "/";            //linux
        File file = new File(path);
        //文件夹不存在
        if (!file.exists() && !file.isDirectory()) {
            return null;
        }
        FileList fl = new FileList(path);
        List<String> files = fl.getAllFiles(path);

        //map:key为 “景区_展播”，value为所有视频
        for (String s : files) {
            String[] f = s.split("_");
            String sId = f[2];
            if (sId.equals(showId)) {
                showList.add(s);
            }
        }

        //list排序
        Collections.sort(showList);
        // 展播文件如果存在则返回最新的展播
        if (showList != null && showList.size() > 0) {
            return showList.get(showList.size() - 1);
        } else {
            return null;
        }


    }


    /**
     * 判断是否为整数
     *
     * @param str 传入的字符串
     * @return 是整数返回true, 否则返回false
     */
    public static boolean isInteger(String str) {
        Pattern pattern = Pattern.compile("^[-\\+]?[\\d]*$");
        return pattern.matcher(str).matches();
    }


    /**
     * 删除文件
     *
     * @param sPath 文件路径
     * @return
     */
    public static boolean deleteFile(String sPath) {
        boolean flag = false;
        File file = new File(sPath);
        // 路径为文件且不为空则进行删除
        if (file.isFile() && file.exists()) {
            file.delete();
            flag = true;
        }
        return flag;
    }


    //获取全部(景区)展播
    public List<MyScenery> getSceneryList() {
        //先从缓存中取数据
        //CacheKit.get(String cacheName, Object key), cacheName为ehcache.xml中的<cache name="">
        //CacheKit.put(String cacheName, Object key, Object value)
        List<SceneryShow> sceneryShowList = CacheKit.get("sampleCache1", "sceneryShowList");
        if (sceneryShowList == null) {
            sceneryShowList = SceneryShow.dao.find("select * from scenery_show");
            CacheKit.put("sampleCache1", "sceneryShowList", sceneryShowList);
        }
        //List<SceneryShow> list = SceneryShow.dao.find("select * from scenery_show");
        List<MyScenery> sceneryList = new ArrayList<MyScenery>();
        HashSet<String> hs = new HashSet<String>();
        //统计景区(使用HashSet去重复)
        for (SceneryShow ss : sceneryShowList) {
            hs.add(ss.getZoneId());
        }
        //根据景区ID获取景区信息
        for (String sceneryId : hs) {
            //获取景区对象
            SceneryDict sd = SceneryDict.dao.findById(sceneryId);
            MyScenery scenery = new MyScenery();
            //设置景区属性
            scenery.setSceneryId(sceneryId);
            scenery.setSceneryName(sd.getScenery());
            List<MyShow> showList = new ArrayList<MyShow>();
            for (SceneryShow ss : sceneryShowList) {
                if (ss.getZoneId().equals(sceneryId)) {
                    MyShow ms = new MyShow(ss.getId().toString(), ss.getShowName(), "");
                    showList.add(ms);
                }
            }
            scenery.setShowList(showList);
            sceneryList.add(scenery);
        }

        return sceneryList;
    }

    //根据ID获取展播详情
    public MyShow getShowById(String id) {
        MyShow ms = new MyShow();
        SceneryShow ss = SceneryShow.dao.findById(id);
        if (ss != null) {
            String SceneryId = ss.getZoneId().toString();
            String showId = ss.getShowId();
            String showUrl = null;

            try {
                showUrl = this.getLatestFile(SceneryId, showId);
                ms.setShowId(id);
                ms.setShowName(ss.getShowName());
                ms.setShowUrl("http://" + config.getStreamMediaServerHost() + "/file/" + SceneryId + "/" + showUrl);

            } catch (IOException e) {
                e.printStackTrace();
            }
            return ms;
        }
        return null;
    }


}

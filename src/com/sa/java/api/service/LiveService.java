package com.sa.java.api.service;

import com.sa.java.common.util.SocketClient;
import com.sa.java.common.util.SocketMsg;
import com.sa.java.modules.model.SceneryDict;
import com.sa.java.modules.model.SceneryHost;
import com.sa.java.modules.monitor.entity.MyCam;
import com.sa.java.modules.monitor.entity.MySceneryCam;
import com.sa.java.modules.monitor.service.MonitorService;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by sa
 * Date: 2016/5/17 15:41
 */
public class LiveService {
    MonitorService ms = new MonitorService();

    //获取景区信息(包括直播流、摄像机能力)
    public MySceneryCam getLive(String sceneryId, String sceneryName) throws UnsupportedEncodingException {
        MySceneryCam msc = null;
        List<MyCam> camList = new ArrayList<MyCam>();
        String msg = "";
        if (sceneryId != null && !sceneryId.equals("")) {
            //获取景区的主机信息
            //SceneryHost host = SceneryHost.dao.findById(sceneryId);
            SceneryHost host = SceneryHost.dao.findFirst("select * from scenery_host where scenery_id= '" + sceneryId + "'");
            if (host != null) {
                //获取景区信息
                //SceneryDict sd = SceneryDict.dao.findById(host.getSceneryId());
                SceneryDict sd = SceneryDict.dao.findById(sceneryId);
                //景区内所有直播的信息
                if (sceneryName != null && !sceneryName.equals("")) {
                    sceneryName = new String(sceneryName.getBytes("iso-8859-1"), "utf-8");
                    sceneryName = URLDecoder.decode(sceneryName, "utf-8");
                    msg = SocketClient.send(host.getIp(), host.getPort(), SocketMsg.START_MONITOR_AND_GET_CAM_STATE + sceneryName.getBytes("GBK").length + ";" + sceneryName + ";");
                } else {
                    msg = SocketClient.send(host.getIp(), host.getPort(), SocketMsg.START_MONITOR_AND_GET_CAM_STATE);
                }
                //去除约定字符串
                msg = msg.replaceAll(SocketMsg.START_MONITOR_AND_GET_SUCCESS_CAM_STATE, "");

                String msgs[] = msg.split(";");
                msg = msg.replaceAll(msgs[0] + ";", "");
                msgs = msg.split(",;");
                for (String s : msgs) {
                    if (!s.equals("")) {
                        MyCam mc = new MyCam();
                        String str[] = s.split(",");
                        mc.setCamName(s.split(":")[0]);
                        mc.setRtmp(str[0].replaceAll(s.split(":")[0] + ":", ""));
                        mc.setPt(str[1].split(":")[1]);
                        mc.setZoom(str[2].split(":")[1]);
                        mc.setFocus(str[3].split(":")[1]);
                        mc.setIris(str[4].split(":")[1]);
                        camList.add(mc);
                    }
                }
                msc = new MySceneryCam(sd.getZoneId().toString(), sd.getScenery(), camList);
            }
        }
        return msc;
    }


    /**
     * 控制摄像机运动
     * @param sceneryId 景区ID
     * @param sceneryName   景点/摄像点名称
     * @param command   命令
     * @return
     */
    public String ctrlCam(String sceneryId, String sceneryName, String command) {

        if(sceneryId==null||sceneryId.equals("")){
            return "sceneryId not found";
        }
        if(sceneryName==null||sceneryName.equals("")){
            return "sceneryName not found";
        }
        if(command==null||command.equals("")){
            return "command not found";
        }

        //返回消息
        String msg = "";

        try {
            //中文转码
            sceneryName = new String(sceneryName.getBytes("iso-8859-1"), "utf-8");
            sceneryName = URLDecoder.decode(sceneryName, "utf-8");
            msg = ms.ctrlCam(sceneryId,sceneryName,command);


        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return msg;
    }

    /**
     * 关闭直播
     */
    public void closeStream(String sceneryId) {
        String msg = "success";
        //获取景区的主机信息
        SceneryHost host = SceneryHost.dao.findFirst("select * from scenery_host where scenery_id= '" + sceneryId + "'");
        //告诉景区的中心服务器关闭直播(并启动展播)
        try {
            SocketClient.onlySend(host.getIp(), host.getPort(), SocketMsg.STOP_MONITOR);
        } catch (Exception e) {
            msg = "socket error";
            e.printStackTrace();
        }


        //关闭流媒体服务器推流
//        List<Process> list = Ffmpeg.map.get(sceneryId);
//        for (Process p : list) {
//            p.destroy();
//        }


    }
}

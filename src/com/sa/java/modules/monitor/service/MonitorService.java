package com.sa.java.modules.monitor.service;

import com.sa.java.common.util.SocketClient;
import com.sa.java.common.util.SocketMsg;
import com.sa.java.modules.model.SceneryHost;
import com.sa.java.modules.monitor.entity.MyCam;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

/**
 * Created by sa
 * Date: 2016/4/27 9:42
 */
public class MonitorService {

    //获取景区列表
    public List<String> getSceneryList(List<String> files) {
        List<String> list = new ArrayList<String>();
        if (null != files && files.size() > 0) {
            for (String s : files) {
                //从固定的文件格式中取出"景区"ID
                String str[] = s.split("_");
                list.add(str[1]);
                System.out.println("景区ID：" + str[1]);
            }
        }

        HashSet hs = new HashSet(list);
        list.clear();
        list.addAll(hs);
        System.out.println(list);
        return list;
    }

    //获取景区下的展播列表
    public List<String> getShowList(List<String> files) {

        return files;
    }


    //记录当前的主机信息
    public void setHost(String sceneryId) {
        //获取景区的主机信息
        //System.out.println("开始更新主机信息，切换到： "+sceneryId);
//        SceneryHost host = SceneryHost.dao.findById(sceneryId);
//        SocketMsg.HOST = host.getIp();
//        SocketMsg.PORT = host.getPort();
        //System.out.println("主机信息更新完毕！");

        SceneryHost host = null;
        List<SceneryHost> hostList = SceneryHost.dao.find("select * from scenery_host where scenery_id = " + sceneryId);
        if (hostList != null) {
            host = hostList.get(0);
            SocketMsg.HOST = host.getIp();
            SocketMsg.PORT = host.getPort();
        }
    }

    //根据景区ID获取景区下的摄像机列表
    public String getCams(String sceneryId) {
        this.setHost(sceneryId);
        //通过socket获取摄像点
        String msg = SocketClient.send(SocketMsg.HOST, SocketMsg.PORT, SocketMsg.GET_CAM_LIST);
        //String msg = SocketClient.send(resort.getIp(), resort.getPort(), SocketMsg.GET_CAM_LIST);
        String cams = "";
        if (msg != null && msg.contains(SocketMsg.GET_SUCCESS_CAM_LIST)) {
            //去除约定字符串
            msg = msg.replaceAll(SocketMsg.GET_SUCCESS_CAM_LIST, "");
            String str[] = msg.split(";");
            cams = str[1];
        }
        return cams;
    }

    /**
     * 获取摄像机能力
     *
     * @param camName
     * @return
     */
    public MyCam getState(String camName) {
        MyCam mc = new MyCam();
        try {
            String msg = SocketClient.send(SocketMsg.HOST, SocketMsg.PORT, SocketMsg.START_MONITOR_AND_GET_CAM_STATE + camName.getBytes("GBK").length + ";" + camName + ";");
            //去除约定字符串
            msg = msg.replaceAll(SocketMsg.START_MONITOR_AND_GET_SUCCESS_CAM_STATE, "");
            msg = msg.split(camName + ":")[1];//只取有用的字符串(这里我真不想再看了，头疼！)
            String str[] = msg.split(",");
            //封装成对象

            mc.setCamName(camName);
            mc.setRtmp(str[0]);
            mc.setPt(str[1].split(":")[1]);
            mc.setZoom(str[2].split(":")[1]);
            mc.setFocus(str[3].split(":")[1]);
            mc.setIris(str[4].split(":")[1]);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return mc;
    }


    /**
     * 发送消息控制摄像机
     *
     * @param camName,command
     */
    public String ctrlCam(String sceneryId, String camName, String command) {
        String str = "";

        SceneryHost host = null;
        List<SceneryHost> hostList = SceneryHost.dao.find("select * from scenery_host where scenery_id = " + sceneryId);
        if (hostList != null) {
            host = hostList.get(0);
        } else {
            str = "sceneryId does not exist";
        }
        System.out.println("ctrlCam 已经更新主机信息: IP：" + host.getIp() + "  端口：" + host.getPort());

        if (command.equals("left_top")) {
            command = ":1;1;0;0;0;";//左上
        } else if (command.equals("top")) {
            command = ":0;1;0;0;0;";//上
        } else if (command.equals("right_top")) {
            command = ":2;1;0;0;0;";//右上
        } else if (command.equals("left")) {
            command = ":1;0;0;0;0;";//左
        } else if (command.equals("center")) {
            command = ":0;0;0;0;0;";//中
        } else if (command.equals("right")) {
            command = ":2;0;0;0;0;";//右
        } else if (command.equals("left_bottom")) {
            command = ":1;2;0;0;0;";//左下
        } else if (command.equals("bottom")) {
            command = ":0;2;0;0;0;";//下
        } else if (command.equals("right_bottom")) {
            command = ":2;2;0;0;0;";//右下
        } else if (command.equals("zoom_left")) {
            command = ":0;0;1;0;0;";//zoom+
        } else if (command.equals("zoom_right")) {
            command = ":0;0;2;0;0;";//zoom-
        } else if (command.equals("focus_left")) {
            command = ":0;0;0;1;0;";//focus+
        } else if (command.equals("focus_right")) {
            command = ":0;0;0;2;0;";//focus-
        } else if (command.equals("iris_left")) {
            command = ":0;0;0;0;1;";//iris+
        } else if (command.equals("iris_left")) {
            command = ":0;0;0;0;2;";//iris-
        } else if (command.equals("stop")) {
            command = ":3;0;0;0;0;";
        }
        String msg = camName + command;
        try {
            SocketClient.onlySend(host.getIp(), host.getPort(), SocketMsg.CTRL_CAM + msg.getBytes("GBK").length + ";" + msg);
            str = "success";
        } catch (UnsupportedEncodingException e) {
            str = "error";
            e.printStackTrace();
        }
        return str;
    }


    /**
     * 关闭直播
     */
    public void closeStream() {
        SocketClient.onlySend(SocketMsg.HOST, SocketMsg.PORT, SocketMsg.STOP_MONITOR);
    }
}

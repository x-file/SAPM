package com.sa.java.common.util;


import java.io.UnsupportedEncodingException;

import static com.sa.java.common.util.SocketClient.send;

public class SocketMsg {

    /**
     * 服务端要连接的服务端对应的地址、IP地址
     */
    public static String HOST = "";
    
    /**
     * 要连接的服务端对应的监听端口
     */
    public static int PORT = 0;
    
    /**
     * 获取直播地址(所约定的数据格式)
     * web-->server
     */
    public static String GET_LIVE_URL = "STKNET_DATA;STK_WEBSYS;STKMOD_NETVIDEO;STKNETMSG_LIVETV_GETRES;";
    
    /**
     * 正常返回直播地址(所预定的数据格式)
     * server-->web
     * 用于判断返回的数据是否正确（正确则包含直播地址）
     */
    public static String GET_SUCCESS_LIVE_URL = "STKNET_DATA;STK_SERVER;STK_WEBSYS;STKNETMSG_LIVETV_RES;";
    
    
    /**
     * 获取景区中的摄像点
     * web-->server
     * 已放弃使用
     */
    public static String GET_CAM_LIST = "STKNET_DATA;STK_WEBSYS;STKMOD_NETVIDEO;STKNETMSG_GET_CAM_LIST;";
    
    /**
     * 返回景区中的摄像点
     * 用于判断返回的数据是否正确（正确则包含摄像点列表数据）
     * server-->web
     * 已放弃使用
     */
    public static String GET_SUCCESS_CAM_LIST = "STKNET_DATA;STK_SERVER;STK_WEBSYS;STKNETMSG_CAM_LIST;";
    
    
    /**
     * 启动监控并获取摄像机的状态(焦距、是否可控等)
     * web-->server
     */
    public static String START_MONITOR_AND_GET_CAM_STATE= "STKNET_DATA;STK_WEBSYS;STKMOD_NETVIDEO;STKNETMSG_CAMMON_ON;";
    //public static String START_MONITOR_AND_GET_CAM_STATE= "STKNET_DATA;STK_WEBSYS;STKMOD_NETVIDEO;STKNETMSG_CAMMON_ON;5;景点1;5;景点2;5;景点3;5;景点4;5;测试1;";
    //public static String START_MONITOR_AND_GET_CAM_STATE= "STKNET_DATA;STK_WEBSYS;STKMOD_NETVIDEO;STKNETMSG_GET_CAM_CAP;数据长度;数据";

    /**
     * 启动监控并获取摄像机的状态(约定字符串)
     */
    public static String START_MONITOR_AND_GET_SUCCESS_CAM_STATE= "STKNET_DATA;STK_SERVER;STK_WEBSYS;STKNETMSG_CAM_CAP;";

    /**
     * 结束直播
     * web-->server
     */
    public static String STOP_MONITOR = "STKNET_DATA;STK_WEBSYS;STKMOD_NETVIDEO;STKNETMSG_MONITOR_OFF;";


//    public static String Ctrl_Cam = "STKNET_DATA;STK_WEBSYS;STKMOD_NETVIDEO;STKNETMSG_CAMCNTRL;数据长度;数据;";
    public static String CTRL_CAM = "STKNET_DATA;STK_WEBSYS;STKMOD_NETVIDEO;STKNETMSG_CAMCNTRL;";


    public static void main(String[] args) throws UnsupportedEncodingException {
            String str = START_MONITOR_AND_GET_CAM_STATE;
            String msg = send("192.168.1.150", 9996, str+"4;大门;");
//        String a = "http://pic.bandaonews.com/PicView.aspx?id=37219";
//        String b = "http://tech.163.com/05/0829/09/1SAIIRG8000915BD.html";
//        String c = "白云山";
//        System.out.println("--"+a.hashCode()+"---"+b.hashCode()+"---"+c.hashCode());



    }





}

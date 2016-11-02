package com.sa.java.api.control;

import com.jfinal.core.Controller;
import com.jfinal.plugin.ehcache.CacheKit;
import com.sa.java.modules.model.SceneryHost;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;
import java.util.List;

/**
 * Created by sa
 * Date: 2016/7/27 11:32
 */
public class StatusController extends Controller {

    /**
     * 获取服务器状态
     */
    public void index() throws IOException {
        List<SceneryHost> hostList = CacheKit.get("sampleCache1", "hostList");
        if(hostList==null){
            hostList = SceneryHost.dao.find("select * from scenery_host sh ,scenery_dict sd where sh.scenery_id = sd.zoneId");
            CacheKit.put("sampleCache1", "hostList", hostList);
        }
        String msg = "";

        for (SceneryHost host : hostList) {
            Socket socket = new Socket();//实例化Sokcet
            msg = msg + host.get("scenery") + ": " + host.getIp() + ", " + host.getPort()+", ";
            try {
                SocketAddress socketAddress = new InetSocketAddress(host.getIp(), host.getPort());//获取SocketAddress对象
                socket.connect(socketAddress, 3000);//连接socket并设置3秒超时
                msg = msg + "服务正常..." + "\r\n";
            } catch (Exception e) {
                //e.printStackTrace();
                msg = msg + "服务异常!!!" + "\r\n";
            } finally {
                socket.close();
            }
        }
        renderJson(msg);
    }
}

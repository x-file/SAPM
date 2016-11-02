package com.sa.java.api.control;

import com.jfinal.core.Controller;
import com.sa.java.api.service.LiveService;
import com.sa.java.modules.monitor.entity.MySceneryCam;

import java.io.UnsupportedEncodingException;


/**
 * Created by sa
 * Date: 2016/04/05 15:30
 */
public class LiveController extends Controller {

    LiveService ls = new LiveService();

    //获取景区的直播信息：视频流地址、视频是否可控
    //参数：景区ID
    public void index() throws UnsupportedEncodingException {
        getResponse().setHeader("Access-Control-Allow-Origin", "*");//允许跨域
        String sceneryId = getPara(0);
        String sceneryName = getPara(1);
        MySceneryCam msc = ls.getLive(sceneryId,sceneryName);
        if(msc!=null) {
            //msc = Ffmpeg.pushRtsp(msc);
            if(sceneryName!=null&&!sceneryName.equals("")){
                renderJson(msc.getCamList().get(0));
            }else{
                renderJson(msc);
            }
        }else{
            renderJson("error","id not found");
        }
    }


    //摄像机控制
    public void ctrl(){
        getResponse().setHeader("Access-Control-Allow-Origin", "*");//允许跨域
        String sceneryId = getPara(0);
        String sceneryName = getPara(1);
        String command = getPara(2);
        String msg = ls.ctrlCam(sceneryId,sceneryName,command);
        renderJson("msg",msg);
    }


    //关闭直播
    public void close(){
        getResponse().setHeader("Access-Control-Allow-Origin", "*");//允许跨域
        //关闭流媒体服务器的直播流
        ls.closeStream(getPara());
        renderJson("msg", "success");
    }


}

package com.sa.java.common.util;

import com.sa.java.modules.model.Config;
import com.sa.java.modules.monitor.entity.MyCam;
import com.sa.java.modules.monitor.entity.MySceneryCam;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by sa
 * Date: 2016/5/18 10:10
 */
public class Ffmpeg {
    //存放ffmpeg进程对象
    public static Map<String, List<Process>> map = new HashMap<String, List<Process>>();

    private static String host = Config.dao.findById(1).getStreamMediaServerHost();

    /**
     * 推流(整个景区推流)
     *
     * @param msc 自定义景区对象，包含景区的所有摄像点
     * @return 返回新的(RTMP)流地址
     */
    public static MySceneryCam pushRtsp(MySceneryCam msc) {
        List<Process> pList = new ArrayList<Process>();
        if (msc != null && msc.getCamList() != null && msc.getCamList().size() > 0) {
            List<MyCam> mcList = new ArrayList<MyCam>();
            for (MyCam mc : msc.getCamList()) {
                //for (int i = 0; i < 3; i++) {

                String newRtmp = "rtmp://" + host + "/live/" + msc.getSceneryId() + "_" + mc.getCamName().hashCode();
                List<String> commend = new ArrayList<String>();
                //commend.add("d:/ffmpeg/bin/ffmpeg.exe");  //windows
                commend.add("/usr/local/srs/trunk/objs/ffmpeg/bin/ffmpeg"); //linux
                commend.add("-i");
                //commend.add("rtmp://live.hkstv.hk.lxdns.com/live/hks");
                commend.add("rtsp://192.168.1.4:5504/channel=0;stream=1;user=system;pass=system;");
                //commend.add(mc.getRtmp());
                commend.add("-vcodec");
                commend.add("copy");
                commend.add("-acodec");
                commend.add("copy");
                commend.add("-f");
                commend.add("flv");
                commend.add(newRtmp);       //输出流

                //更新推流后的视频流地址
                mc.setRtmp(newRtmp);
                mcList.add(mc);

                try {
                    //预处理进程
                    ProcessBuilder builder = new ProcessBuilder();
                    builder.command(commend);
                    builder.redirectErrorStream(true);
                    Process p = builder.start();

                    final InputStream is = p.getInputStream();
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            BufferedReader br = new BufferedReader(new InputStreamReader(is));
                            String line = null;
                            try {
                                while ((line = br.readLine()) != null) {
                                    System.out.println(line);
                                }
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }).start();

                    //保存进程对象
                    pList.add(p);

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            msc.setCamList(mcList);
            map.put(msc.getSceneryId(), pList);//保存进程对象
        }
        return msc;
    }

    /**
     * 推流(推一路流）
     *
     * @param rtsp 输入流（rtsp://192.168.1.4:5504/channel=0;stream=1;user=system;pass=system;）
     * @return rtmp 输出流
     */
    public static String pushRtsp(String rtsp) {
        String rtmp = "rtmp://" + host + "/live/" + rtsp.hashCode();

        List<String> commend = new ArrayList<String>();
        //commend.add("d:/ffmpeg/bin/ffmpeg.exe");  //windows
        commend.add("/usr/local/srs/trunk/objs/ffmpeg/bin/ffmpeg"); //linux
        commend.add("-i");
        //commend.add("rtmp://live.hkstv.hk.lxdns.com/live/hks");
        commend.add(rtsp);
        commend.add("-vcodec");
        commend.add("copy");
        commend.add("-acodec");
        commend.add("copy");
        commend.add("-f");
        commend.add("flv");
        commend.add(rtmp);   //输出流(使用哈希值标识唯一)


        try {
            //预处理进程
            ProcessBuilder builder = new ProcessBuilder();
            builder.command(commend);
            builder.redirectErrorStream(true);
            Process p = builder.start();

            final InputStream is = p.getInputStream();
            new Thread(new Runnable() {
                @Override
                public void run() {
                    BufferedReader br = new BufferedReader(new InputStreamReader(is));
                    String line = null;
                    try {
                        while ((line = br.readLine()) != null) {
                            System.out.println(line);
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }).start();


        } catch (Exception e) {
            rtmp = "input stream error";
            e.printStackTrace();
        }
        return rtmp;
    }


    //推流测试
    public static void testPush() {
        //for (int i = 0; i < 3; i++) {

        List<String> commend = new ArrayList<String>();
        //commend.add("d:/ffmpeg/bin/ffmpeg.exe");  //windows
        commend.add("/usr/local/srs/trunk/objs/ffmpeg/bin/ffmpeg"); //linux
        commend.add("-i");
        //commend.add("rtmp://live.hkstv.hk.lxdns.com/live/hks");
        commend.add("rtsp://192.168.1.4:5504/channel=0;stream=1;user=system;pass=system;");
        commend.add("-vcodec");
        commend.add("copy");
        commend.add("-acodec");
        commend.add("copy");
        commend.add("-f");
        commend.add("flv");
        commend.add("rtmp://" + host + "/live/sa_0");   //输出流


        try {
            //预处理进程
            ProcessBuilder builder = new ProcessBuilder();
            builder.command(commend);
            builder.redirectErrorStream(true);
            Process p = builder.start();

            final InputStream is = p.getInputStream();
            new Thread(new Runnable() {
                @Override
                public void run() {
                    BufferedReader br = new BufferedReader(new InputStreamReader(is));
                    String line = null;
                    try {
                        while ((line = br.readLine()) != null) {
                            System.out.println(line);
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }).start();


        } catch (Exception e) {
            e.printStackTrace();
        }


//        }


    }


}

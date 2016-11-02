package com.sa.java.modules.monitor.entity;

/**
 * 景区摄像点
 * Created by sa
 * Date: 2016/5/12 16:53
 */
public class MyCam {
    private String camName;     //景点(摄像点)名称
    private String rtmp;        //视频流
    private String pt;          //云台信息(0:无云台控制（定点）,1:PAN、TILT可控,2:PAN可控、Tilt不可控)
    private String zoom;        //变倍 (0:不可控, 1:可控)
    private String focus;       //变焦 (0:不可控, 1:可控)
    private String iris;        //光圈 (0:不可控, 1:可控)

    public MyCam(){}
    public MyCam(String camName,String rtmp,String pt,String zoom,String focus,String iris){
        this.camName = camName;
        this.rtmp = rtmp;
        this.pt = pt;
        this.zoom = zoom;
        this.focus = focus;
        this.iris = iris;
    }

    public String getCamName() {
        return camName;
    }

    public void setCamName(String camName) {
        this.camName = camName;
    }

    public String getRtmp() {
        return rtmp;
    }

    public void setRtmp(String rtmp) {
        this.rtmp = rtmp;
    }

    public String getPt() {
        return pt;
    }

    public void setPt(String pt) {
        this.pt = pt;
    }

    public String getZoom() {
        return zoom;
    }

    public void setZoom(String zoom) {
        this.zoom = zoom;
    }

    public String getFocus() {
        return focus;
    }

    public void setFocus(String focus) {
        this.focus = focus;
    }

    public String getIris() {
        return iris;
    }

    public void setIris(String iris) {
        this.iris = iris;
    }
}

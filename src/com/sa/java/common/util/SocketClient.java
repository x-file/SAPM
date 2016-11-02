package com.sa.java.common.util;

/**
 * Created by sa
 * Date: 2016/4/18 9:38
 */

import java.io.*;
import java.net.*;

public class SocketClient {

    /**
     * 发送socket消息到服务器，并接受返回数据
     * @param host  IP
     * @param port  端口
     * @param msg   消息内容
     * @return
     */
    public static String send(String host,int port,String msg){
        System.out.println("准备创建Socket连接，IP:"+host);
        String msgFromServer="";
        try {
            Socket clientSocket = new Socket(host, port);
            System.out.println("Client1:" + clientSocket);

            DataInputStream dataIS = new DataInputStream(clientSocket.getInputStream());
            InputStreamReader inSR = new InputStreamReader(dataIS, "GBK");
            BufferedReader br = new BufferedReader(inSR);

            DataOutputStream dataOS = new DataOutputStream(clientSocket.getOutputStream());
            OutputStreamWriter outSW = new OutputStreamWriter(dataOS, "GBK");
            BufferedWriter bw = new BufferedWriter(outSW);

            //输入信息

            //发送数据
            //bw.write(msg + "\r\n");		//加上分行符，以便服务器按行读取
            bw.write(msg);		//加上分行符，以便服务器按行读取
            System.out.println("toServer: "+msg);
            bw.flush();

            //接收数据
//            System.out.println(br.readLine());
//            msgFromServer = br.readLine();
            while((msgFromServer = br.readLine()) != null) {
                msgFromServer = msgFromServer.trim();
                System.out.println("服务器回复：" + msgFromServer);
                break;
            }

            //关闭连接
            inSR.close();
            dataIS.close();
            dataOS.close();
            clientSocket.close();
        } catch(UnknownHostException uhe) {
            System.out.println("Error:" + uhe.getMessage());
        } catch(ConnectException ce) {
            System.out.println("Error:" + ce.getMessage());
        } catch(IOException ioe) {
            System.out.println("Error:" + ioe.getMessage());
        } finally {
        }
        return msgFromServer;
    }



    /**
     * 仅发送socket消息到服务器，不接受返回数据
     * @param host  IP
     * @param port  端口
     * @param msg   消息内容
     * @return
     */
    public static void onlySend(String host,int port,String msg){
        try {
            Socket clientSocket = new Socket(host, port);
            System.out.println("Send data only: " + clientSocket);

            DataInputStream dataIS = new DataInputStream(clientSocket.getInputStream());
            InputStreamReader inSR = new InputStreamReader(dataIS, "GBK");
            BufferedReader br = new BufferedReader(inSR);

            DataOutputStream dataOS = new DataOutputStream(clientSocket.getOutputStream());
            OutputStreamWriter outSW = new OutputStreamWriter(dataOS, "GBK");
            BufferedWriter bw = new BufferedWriter(outSW);

            //输入信息

            //发送数据
            //bw.write(msg + "\r\n");		//加上分行符，以便服务器按行读取
            bw.write(msg);		//加上分行符，以便服务器按行读取
            System.out.println("toServer: "+msg);
            bw.flush();


            //关闭连接
            inSR.close();
            dataIS.close();
            dataOS.close();
            clientSocket.close();
        } catch(UnknownHostException uhe) {
            System.out.println("Error:" + uhe.getMessage());
        } catch(ConnectException ce) {
            System.out.println("Error:" + ce.getMessage());
        } catch(IOException ioe) {
            System.out.println("Error:" + ioe.getMessage());
        } finally {
        }
    }

    public static void main(String[] args) throws UnsupportedEncodingException {
        //获取景点列表
        try {
            Socket clientSocket = new Socket("218.28.96.169", 9995);
            System.out.println(clientSocket.getLocalPort()+"-- "+clientSocket.getPort());
        } catch (IOException e) {
            e.printStackTrace();
        }

        //启动监控并获取摄像机的状态





//        String str = "英";
//        System.out.println("字符:  "+str.length());
//        System.out.println("默认:  "+str.getBytes().length);
//        System.out.println("ISO-8859-1:  "+str.getBytes("ISO-8859-1").length);
//        System.out.println("GBK:  "+str.getBytes("GBK").length);
//        System.out.println("UTF-8:  "+str.getBytes("UTF-8").length);




    }
}

package com.sa.java.common.util;

/**
 * Created by sa
 * Date: 2016/4/18 9:36
 */
import java.io.*;
import java.net.*;

public class SocketServer {
    public static void main(String args[]) {
        System.out.println("Server");

        ServerSocket serverSocket = null;
        Socket clientSocket = null;
        int connects = 0;
        try {
            serverSocket = new ServerSocket(8889, 5);		//端口：82，最大链接数：5

            //最多连接10次
            while(connects < 10) {
                connects++;
                System.out.println("--------------------等待连接--------------------------");
                clientSocket = serverSocket.accept();	//等待连接
                System.out.println("第 " + connects + " 次连接");
                ServiceClient(clientSocket);
            }

            serverSocket.close();
        } catch(IOException ioe) {
            System.out.println("Error: " + ioe);
        }
    }

    public static void ServiceClient(Socket client) throws IOException {
        System.out.println("已链接");

        InputStreamReader inSR = null;
        OutputStreamWriter outSW = null;
        try {
            //读取数据
            inSR = new InputStreamReader(client.getInputStream(), "GBK");
            BufferedReader br = new BufferedReader(inSR);

            outSW = new OutputStreamWriter(client.getOutputStream(), "GBK");
            BufferedWriter bw = new BufferedWriter(outSW);

            String str = "";
            while((str = br.readLine()) != null) {
                str = str.trim();
                System.out.println("收到客户端消息：" + str);

                bw.write("已收到信息：" + str + " \r\n");	//向客户端反馈消息，加上分行符以便客户端接收
                bw.flush();
            }

        } finally {
            //System.out.println("Cleaning up connection: " + client);
            inSR.close();
            outSW.close();
            client.close();
        }
        System.out.println("已断开");
    }
}

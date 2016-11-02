package com.sa.java.common.util;
import java.io.*;
import java.util.*;

/**
 * Created by sa
 * Date: 2016/4/17 10:49
 */
public class FileList {
    private String dir_name=null;
    private String list_name=null;
    private BufferedWriter out = null;
    Vector<String> ver=null;

    public FileList(){}
    public FileList(String dir_name,String list_name) throws IOException{
        this.dir_name=dir_name;    //文件夹地址
        this.list_name=list_name;    //保存文件列表的文件地址
        ver=new Vector<String>();    //用做堆栈
    }
    public FileList(String dir_name) throws IOException{
        this.dir_name=dir_name;    //文件夹地址
        ver=new Vector<String>();    //用做堆栈
    }

    //获取文件列表，并将列表写到一个文档保存
    public void getList() throws Exception{
        out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(list_name, false)));    //true:以追加的方式写入到指定的文件
        ver.add(dir_name);
        while(ver.size()>0){
            File[] files = new File(ver.get(0).toString()).listFiles();    //获取该文件夹下所有的文件(夹)名
            ver.remove(0);

            int len=files.length;
            for(int i=0;i<len;i++){
                String tmp=files[i].getAbsolutePath();
                if(files[i].isDirectory()) {    //如果是目录，则加入队列。以便进行后续处理
                    ver.add(tmp);
                    //System.out.println("文件夹：" + tmp);
                }
                else {
                    out.write(tmp + "\r\n");        //如果是文件，则直接输出文件名到指定的文件。
                    //System.out.println("文件："+tmp);
                }
            }
        }
        out.close();
    }

    //获取目录下的所有文件
    public List<String> getAllFiles(String path){
        List<String>list = new ArrayList<String>();
        ver.add(dir_name);
        while(ver.size()>0){
            File[] files = new File(ver.get(0).toString()).listFiles();    //获取该文件夹下所有的文件(夹)名
            ver.remove(0);

            int len=files.length;
            for(int i=0;i<len;i++){
                String tmp=files[i].getAbsolutePath();
                if(files[i].isDirectory()) {    //如果是目录，则加入队列。以便进行后续处理
                    ver.add(tmp);
                    //System.out.println("文件夹：" + tmp);
                }
                else {
                    list.add(tmp.replaceAll(path,""));
                    //System.out.println("文件："+tmp);
                }
            }
        }

        return list;
    }

    /**
     * 获取目录下的所有文件夹
     * @param
     * @return
     */
    public List<String> getFolders(){
        List<String>list = new ArrayList<String>();
        File[] files = new File(dir_name).listFiles();    //获取该文件夹下所有的文件、文件夹
        for(int i=0;i<files.length;i++){
            String tmp=files[i].getAbsolutePath();
            if(files[i].isDirectory()) {
                list.add(tmp);
            }
        }
        return list;
    }

    /**
     * 获取目录下的所有文件
     * @param path
     * @return
     */
    public List<String> getFiles(String path){
        List<String>list = new ArrayList<String>();
        File[] files = new File(path).listFiles();    //获取该文件夹下所有的文件名
        int len=files.length;
        for(int i=0;i<len;i++){
            String tmp=files[i].getAbsolutePath();
            if(files[i].isDirectory()) {
                System.out.println("文件夹：" + tmp +"不处理");
            }
            else {
                list.add(tmp.replaceAll(path,""));
                System.out.println("展播文件："+tmp);
            }
        }

        return list;
    }





    /**
     * 以行为单位读取文件，常用于读面向行的格式化文件
     */
    public void readFileByLines(String fileName) {
        File file = new File(fileName);
        BufferedReader reader = null;
        try {
            System.out.println("以行为单位读取文件内容，一次读一整行：");
            reader = new BufferedReader(new FileReader(file));
            String tempString = null;
            int line = 1;
            // 一次读入一行，直到读入null为文件结束
            while ((tempString = reader.readLine()) != null) {
                // 显示行号
               // System.out.println("line " + line + ": " + tempString);
                line++;
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e1) {
                }
            }
        }
    }




    public static void main(String[] args) throws Exception {
//        FileList fl = new FileList("d:/test");
//        fl.getFolders();
    }
}

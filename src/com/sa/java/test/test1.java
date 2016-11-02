package com.sa.java.test;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class test1 {




    public static void main(String[] args) {
        List<String>files = new ArrayList<String>();
        List<String>fileList = new ArrayList<String>();
        files.add("d:\test\1\20160426114624_1_1_1.flv");
        files.add("d:\test\1\20160426114624_1_2_1.flv");
        files.add("d:\test\1\20160426134624_1_1_1.flv");
        files.add("d:\test\1\20160426154624_3_1_1.flv");
        files.add("d:\test\1\20160426174624_1_1_1.flv");
        files.add("d:\test\2\20160426114624_2_1_1.flv");
        files.add("d:\test\2\20160426114624_2_2_1.flv");

        for(String s:files){
            String a[] = s.split("\\^");
            System.out.println(s);
            System.out.println("d:\test\2\20160426114624_2_2_1.flv");
        }

        for(int i=0;i<files.size();i++){
            String path = files.get(i).split("_")[0];
            //int time = path.split("");
            String scenery = files.get(i).split("_")[1];
            String spot = files.get(i).split("_")[2];
            System.out.println(scenery+"--");
            for(int j=0;j<files.size();j++){
                String scenery2 = files.get(j).split("_")[1];
                String spot2 = files.get(j).split("_")[2];
                if(scenery.equals(scenery2) && spot.equals(spot2)){

                }

            }
        }



    }
}

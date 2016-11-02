package com.sa.java.modules.sys.control;

import com.jfinal.core.Controller;


/**
 * Created by sa
 * Date: 2016/2/16 15:30
 */
public class SysController extends Controller {
    public void index(){
        //render("index.html");
        redirect("/api/scenery");
    }


}

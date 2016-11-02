package com.sa.java.modules.cms;

import com.jfinal.core.Controller;
import com.sa.java.modules.model.Task;


/**
 * Created by sa
 * Date: 2016/2/16 15:30
 */
//@Before(TaskInterceptor.class)
public class FrontController extends Controller {
    public void index(){
       // setAttr("taskPage", Task.dao.paginate(getParaToInt(0, 1), 20));
        render("index.html");
    }


}

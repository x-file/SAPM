package com.sa.java.modules.task.control;

import com.jfinal.core.Controller;
import com.jfinal.validate.Validator;
import com.sa.java.modules.model.Task;

/**
 * BlogValidator.
 */
public class TaskValidator extends Validator {
	
	protected void validate(Controller controller) {
		validateRequiredString("task.name", "nameMsg", "请输入任务内容!");
		validateRequiredString("task.person", "personMsg", "请输入负责人!");
	}
	
	protected void handleError(Controller controller) {
		controller.keepModel(Task.class);
		
		String actionKey = getActionKey();
		if (actionKey.equals("/task/save"))
			controller.render("add.html");
		else if (actionKey.equals("/task/update"))
			controller.render("edit.html");
	}
}

package com.sa.java.modules.model;

import com.jfinal.plugin.activerecord.Page;
import com.sa.java.modules.model.base.BaseTask;

/**
 * Generated by JFinal.
 */
@SuppressWarnings("serial")
public class Task extends BaseTask<Task> {
	public static final Task dao = new Task();

	public Page<Task> paginate(int pageNumber, int pageSize) {
		return paginate(pageNumber, pageSize, "select *", "from task order by id desc");
	}

}
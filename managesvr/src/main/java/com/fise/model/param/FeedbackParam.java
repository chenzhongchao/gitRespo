package com.fise.model.param;

import java.io.Serializable;

import com.fise.framework.annotation.MaxLength;
import com.fise.framework.annotation.NotEmpty;
import com.fise.utils.JsonUtil;

/** 
 * @author 大表哥
 * @email xingyuanzuo@gmail.com
 * @date 2016-8-13
 * @desc 意见反馈参数对象
 */

public class FeedbackParam implements Serializable {
	private static final long serialVersionUID = 1L;

	@NotEmpty
	@MaxLength(value = 600)
	private String content;

	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	
	@Override
	public String toString() {
		return JsonUtil.toJson(this);
	}
}

package com.fise.service.comment;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.fise.base.Page;
import com.fise.model.entity.Comment;

public interface ICommentService {
	// 发表评论
	public void insertComment(Comment comment);
	
	// 获取商户评论列表
	public List<Comment> getGymCommentList(Integer gymId, Page<Comment> pageParam);
	
	// 获取商户评分（平均分）
	public Float selectAvgScore(Integer gymId);
}

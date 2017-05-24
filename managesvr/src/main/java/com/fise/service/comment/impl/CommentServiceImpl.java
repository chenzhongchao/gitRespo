package com.fise.service.comment.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fise.base.Page;
import com.fise.base.Response;
import com.fise.dao.CommentMapper;
import com.fise.model.entity.Comment;
import com.fise.service.comment.ICommentService;

@Service
public class CommentServiceImpl implements ICommentService {
	@Autowired
	private CommentMapper commentDao;
	
	@Override
	public void insertComment(Comment comment) {	
		commentDao.insertComment(comment);
		
		return ;
	}

	@Override
	public List<Comment> getGymCommentList(Integer gymId, Page<Comment> pageParam) {
		return commentDao.selectGymComment(gymId, pageParam);
	}

	@Override
	public Float selectAvgScore(Integer gymId) {
		return commentDao.selectAvgScore(gymId);
	}
}

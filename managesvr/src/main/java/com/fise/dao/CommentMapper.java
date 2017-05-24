package com.fise.dao;

import java.util.List;
import org.apache.ibatis.annotations.Param;

import com.fise.base.Page;
import com.fise.model.entity.Comment;
import com.fise.model.entity.CommentExample;

public interface CommentMapper {
    int countByExample(CommentExample example);

    int deleteByExample(CommentExample example);
    
    int deleteByPrimaryKey(Long commentId);

    int insert(Comment record);

    int insertSelective(Comment record);

    List<Comment> selectByExampleWithBLOBs(CommentExample example);

    List<Comment> selectByExample(CommentExample example);

    Comment selectByPrimaryKey(Long commentId);

    int updateByExampleSelective(@Param("record") Comment record, @Param("example") CommentExample example);

    int updateByExampleWithBLOBs(@Param("record") Comment record, @Param("example") CommentExample example);

    int updateByExample(@Param("record") Comment record, @Param("example") CommentExample example);

    int updateByPrimaryKeySelective(Comment record);

    int updateByPrimaryKeyWithBLOBs(Comment record);

    int updateByPrimaryKey(Comment record);
    
    // 发表评论
    public int insertComment(Comment record);
    
    // 获取商户的评论列表
    public List<Comment> selectGymComment(@Param("gymId") Integer gymId, @Param("page") Page<Comment> page);
    
    // 获取商户评分（平均分）
    public Float selectAvgScore(@Param("gymId") Integer gymId);
}
package com.fise.dao;

import java.util.List;
import org.apache.ibatis.annotations.Param;

import com.fise.model.entity.Suggestion;

public interface SuggestionMapper {
    int deleteByPrimaryKey(Integer sugId);
    int insert(Suggestion record);
    int insertSelective(Suggestion record);
    Suggestion selectByPrimaryKey(Integer sugId);
    int updateByPrimaryKeySelective(Suggestion record);
    int updateByPrimaryKey(Suggestion record);
}
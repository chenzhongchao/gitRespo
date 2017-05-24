package com.fise.dao;

import java.util.List;
import org.apache.ibatis.annotations.Param;

import com.fise.model.entity.MemberThirdparty;
import com.fise.model.entity.MemberThirdpartyExample;

public interface MemberThirdpartyMapper {

    int countByExample(MemberThirdpartyExample example);

    int deleteByExample(MemberThirdpartyExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(MemberThirdparty record);

    int insertSelective(MemberThirdparty record);

    List<MemberThirdparty> selectByExample(MemberThirdpartyExample example);

    MemberThirdparty selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") MemberThirdparty record, @Param("example") MemberThirdpartyExample example);

    int updateByExample(@Param("record") MemberThirdparty record, @Param("example") MemberThirdpartyExample example);

    int updateByPrimaryKeySelective(MemberThirdparty record);

    int updateByPrimaryKey(MemberThirdparty record);
    
    // 根据条件查询记录
    public MemberThirdparty selectByMemberThirdpartySelective(@Param("record") MemberThirdparty record);
}
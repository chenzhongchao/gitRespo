package com.fise.framework.typehandler;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.TypeHandler;

/** 
 * @author 大表哥
 * @email xingyuanzuo@gmail.com
 * @date 2016-7-23
 * @desc Mybatis Long null值的处理
 */

public class LongNullTypeHandler implements TypeHandler<Long> {

	@Override
	public Long getResult(ResultSet resultSet, String columnName) throws SQLException {
		Long result = resultSet.getLong(columnName);
		
		return result;
	}

	@Override
	public Long getResult(ResultSet resultSet, int columnIndex) throws SQLException {
		Long result = resultSet.getLong(columnIndex);
		
		return result;
	}

	@Override
	public Long getResult(CallableStatement callableStatement, int columnIndex) throws SQLException {
		Long result = callableStatement.getLong(columnIndex);
		
		return result;
	}

	@Override
	public void setParameter(PreparedStatement preparedStatement, int index, Long value, JdbcType jdbcType) throws SQLException {
		if (value == null && jdbcType == JdbcType.BIGINT) {
			preparedStatement.setLong(index, 0L);
		} else {
			preparedStatement.setLong(index, value);
		}
	}

}

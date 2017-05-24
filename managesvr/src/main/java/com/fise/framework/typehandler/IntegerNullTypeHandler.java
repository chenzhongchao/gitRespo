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
 * @desc Mybatis Integer null值的处理
 */

public class IntegerNullTypeHandler implements TypeHandler<Integer> {

	@Override
	public Integer getResult(ResultSet resultSet, String columnName) throws SQLException {
		Integer result = resultSet.getInt(columnName);
		
		return result;
	}

	@Override
	public Integer getResult(ResultSet resultSet, int columnIndex) throws SQLException {
		Integer result = resultSet.getInt(columnIndex);
		
		return result;
	}

	@Override
	public Integer getResult(CallableStatement callableStatement, int columnIndex) throws SQLException {
		Integer result = callableStatement.getInt(columnIndex);
		
		return result;
	}

	@Override
	public void setParameter(PreparedStatement preparedStatement, int index, Integer value, JdbcType jdbcType) throws SQLException {
		if (value == null && (jdbcType == JdbcType.INTEGER || jdbcType == JdbcType.SMALLINT || jdbcType == JdbcType.TINYINT)) {
			preparedStatement.setInt(index, 0);
		} else {
			preparedStatement.setInt(index, value);
		}
	}

}

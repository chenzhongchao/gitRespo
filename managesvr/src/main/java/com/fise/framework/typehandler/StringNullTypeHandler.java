package com.fise.framework.typehandler;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.TypeHandler;;

/** 
 * @author 大表哥
 * @email xingyuanzuo@gmail.com
 * @date 2016-7-23
 * @desc Mybatis String null值的处理
 */

public class StringNullTypeHandler implements TypeHandler<String> {

	@Override
	public String getResult(ResultSet resultSet, String columnName) throws SQLException {
		String result = resultSet.getString(columnName);
		
		return result;
	}

	@Override
	public String getResult(ResultSet resultSet, int columnIndex) throws SQLException {
		String result = resultSet.getString(columnIndex);
		
		return result;
	}

	@Override
	public String getResult(CallableStatement callableStatement, int columnIndex)
			throws SQLException {
		String result = callableStatement.getString(columnIndex);
		
		return result;
	}

	@Override
	public void setParameter(PreparedStatement preparedStatement, int index, String value, JdbcType jdbcType) throws SQLException {
		if (value == null && (jdbcType == JdbcType.VARCHAR || jdbcType == JdbcType.LONGVARCHAR)) {
			preparedStatement.setString(index, "");
		} else {
			preparedStatement.setString(index, value);
		}
	}

}

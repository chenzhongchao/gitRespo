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

public class ShortNullTypeHandler implements TypeHandler<Short> {

	@Override
	public Short getResult(ResultSet resultSet, String columnName) throws SQLException {
		Short result = resultSet.getShort(columnName);
		
		return result;
	}

	@Override
	public Short getResult(ResultSet resultSet, int columnIndex) throws SQLException {
		Short result = resultSet.getShort(columnIndex);
		
		return result;
	}

	@Override
	public Short getResult(CallableStatement callableStatement, int columnIndex) throws SQLException {
		Short result = callableStatement.getShort(columnIndex);
		
		return result;
	}

	@Override
	public void setParameter(PreparedStatement preparedStatement, int index, Short value, JdbcType jdbcType) throws SQLException {
		if (value == null && jdbcType == JdbcType.SMALLINT) {
			preparedStatement.setShort(index, (short)0);
		} else {
			preparedStatement.setShort(index, value);
		}
	}

}

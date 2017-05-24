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
 * @date 2016-7-28
 * @desc Mybatis Double null值的处理
 */

public class DoubleNullTypeHandler implements TypeHandler<Double> {

	@Override
	public Double getResult(ResultSet resultSet, String columnName) throws SQLException {
		Double result = resultSet.getDouble(columnName);
		
		return result;
	}

	@Override
	public Double getResult(ResultSet resultSet, int columnIndex) throws SQLException {
		Double result = resultSet.getDouble(columnIndex);
		
		return result;
	}

	@Override
	public Double getResult(CallableStatement callableStatement, int columnIndex) throws SQLException {
		Double result = callableStatement.getDouble(columnIndex);
		
		return result;
	}

	@Override
	public void setParameter(PreparedStatement preparedStatement, int index, Double value, JdbcType jdbcType) throws SQLException {
		if (value == null && (jdbcType == JdbcType.DOUBLE || jdbcType == JdbcType.DECIMAL)) {
			preparedStatement.setDouble(index, 0.00);
		} else {
			preparedStatement.setDouble(index, value);
		}
	}

}

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

public class ByteNullTypeHandler implements TypeHandler<Byte> {

	@Override
	public Byte getResult(ResultSet resultSet, String columnName) throws SQLException {
		Byte result = resultSet.getByte(columnName);
		
		return result;
	}

	@Override
	public Byte getResult(ResultSet resultSet, int columnIndex) throws SQLException {
		Byte result = resultSet.getByte(columnIndex);
		
		return result;
	}

	@Override
	public Byte getResult(CallableStatement callableStatement, int columnIndex) throws SQLException {
		Byte result = callableStatement.getByte(columnIndex);
		
		return result;
	}

	@Override
	public void setParameter(PreparedStatement preparedStatement, int index, Byte value, JdbcType jdbcType) throws SQLException {
		if (value == null && jdbcType == JdbcType.TINYINT) {
			preparedStatement.setByte(index, (byte)0);
		} else {
			preparedStatement.setByte(index, value);
		}
	}

}

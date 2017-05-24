package com.fise.framework.typehandler;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.TypeHandler;;

/** 
 * @author 大表哥
 * @email xingyuanzuo@gmail.com
 * @date 2016-7-23
 * @desc Mybatis date/datetime null值的处理
 */

public class DateNullTypeHandler implements TypeHandler<Date> {

	@Override
	public Date getResult(ResultSet resultSet, String columnName) throws SQLException {
		Date result = resultSet.getDate(columnName);
		
		return result;
	}

	@Override
	public Date getResult(ResultSet resultSet, int columnIndex) throws SQLException {
		Date result = resultSet.getDate(columnIndex);
		
		return result;
	}

	@Override
	public Date getResult(CallableStatement callableStatement, int columnIndex)
			throws SQLException {
		Date result = callableStatement.getDate(columnIndex);
		
		return result;
	}

	@Override
	public void setParameter(PreparedStatement preparedStatement, int index, Date value, JdbcType jdbcType) throws SQLException {
		if (value == null) {
			if (jdbcType == JdbcType.DATE) {
				SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
				try {
					java.util.Date utilDate = format.parse("1900-01-01");
					java.sql.Date sqlDate = new java.sql.Date(utilDate.getTime()); 
					preparedStatement.setDate(index, sqlDate);
				} catch (ParseException e) {
					e.printStackTrace();
				}
				
			} else if (jdbcType == JdbcType.TIMESTAMP) {
				SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				try {
					java.util.Date utilDate = format.parse("0001-01-01 00:00:00");
					java.sql.Timestamp sqlDate = new java.sql.Timestamp(utilDate.getTime());
					preparedStatement.setTimestamp(index, sqlDate);
				} catch (ParseException e) {
					e.printStackTrace();
				}
			} else {
				// TODO 做好日志提醒
			}
		} else {
			preparedStatement.setTimestamp(index, new java.sql.Timestamp(value.getTime()));
		}
	}


}

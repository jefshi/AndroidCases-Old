package com.csp.eclipselibrary.com.csp.eclipselibrary.android.model.util;

import java.sql.SQLException;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.support.DatabaseConnection;

public class OrmliteUtil {
	/**
	 * 
	 * @param tableName
	 * @param dao
	 * @param sql
	 * @return
	 * @throws SQLException
	 */
	@SuppressWarnings("rawtypes")
	public static int executeByTransaction(String tableName, Dao dao, String sql) throws SQLException {
		int result = 0;
		ConnectionSource cSource = null;
		DatabaseConnection connection = null;
		try {
			cSource = dao.getConnectionSource();
			connection = cSource.getReadWriteConnection(tableName);
			connection.setAutoCommit(false); // 设置为不自动提交，因为自动提交效率很低

			result = dao.executeRawNoArgs(sql);

			connection.commit(null);
		} catch (SQLException e) {
			result = 0;
			connection.rollback(null);
			e.printStackTrace();
			throw e;
		} finally {
			// 要释放连接，否则事务执行完了之后没有释放连接导致下个事务无法获取到连接而不能执行，会报错
			if (connection != null) {
				cSource.releaseConnection(connection);
			}
		}
		return result;
	}
}

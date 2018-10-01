package com.main.dao;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import au.com.bytecode.opencsv.CSVReader;

public class ThirdRyeDaoImpl {

	public Date getLoadDate()
	{
		String sql ="SELECT * FROM "
				+ "(SELECT LOAD_DATE FROM W_ETL_LOAD_DATES_TEMP WHERE LOAD_STATUS='SUCCESS' ORDER BY LOAD_DATE) "
				+ "WHERE ROWNUM = 1";
		Statement stmt = null;
		Date loadDate = null;
		try
		{
			Connection conn = connectToDatabase();
			stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(sql);

			while (rs.next()) {
				loadDate = rs.getDate(0);
			}
			conn.close();
		} 
		catch (ClassNotFoundException | SQLException e) 
		{
			e.printStackTrace();
		}
		return loadDate;
	}

	public Connection connectToDatabase() throws SQLException, ClassNotFoundException
	{
		String dbURL = "jdbc:oracle:thin:@(DESCRIPTION=(ADDRESS_LIST=(ADDRESS=(PROTOCOL=TCP)(HOST=129.150.199.179)(PORT=1521)))(CONNECT_DATA=(SERVICE_NAME=PIKEDEV1.600227912.oraclecloud.internal)))";
		String strUserID = "PIKE_DW";
		String strPassword = "PIKE_DW";
		Connection connObj=DriverManager.getConnection(dbURL,strUserID,strPassword);		
		return connObj;
	}

	@SuppressWarnings("resource")
	public void bulkInsertCSVtoOracle(InputStream files) throws SQLException, ClassNotFoundException, IOException
	{                
		try {
			Connection conn = connectToDatabase();
			PreparedStatement ps = null;
			String jdbc_insert_sql = "INSERT INTO W_PICKUP_TRUCK_RAW_TEMP"
					+ "(TIME, DIVISION ,TRUCK# ,DRIVER ,SPEED ,IDLING ,LATITUDE ,LONGITUDE ,ADDRESS ,TYPE ,DESCRIPTION ,CREATEDDATA) VALUES"
					+ "(?,?,?,?,?,?,?,?,?,?,?,?)";
			ps = conn.prepareStatement(jdbc_insert_sql);
			final int batchSize = 1000;
			int count = 0;

			CSVReader reader = new CSVReader(new InputStreamReader(files));
			String [] nextLine; 

			while ((nextLine = reader.readNext()) != null) {

				ps.setString(1, nextLine[0]);
				ps.setString(2,nextLine[1]);
				ps.setString(3,nextLine[2]);
				ps.setString(4,nextLine[3]);
				ps.setString(5,nextLine[4]);
				ps.setString(6,nextLine[5]);
				ps.setString(7,nextLine[6]);
				ps.setString(8,nextLine[7]);
				ps.setString(9,nextLine[8]);
				ps.setString(10,nextLine[9]);
				ps.setString(11,null);
				ps.setString(12,null);

				ps.addBatch();

				if(++count % batchSize == 0) {
					ps.executeBatch();
				}
			}                     
			ps.executeBatch(); // insert remaining records
			ps.close();

			conn.commit();
			conn.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}




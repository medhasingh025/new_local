package com.main.dao;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.main.data.AssetData;

public class AssetDaoImpl {

	public void saveAssetData(List<AssetData> data) {
		try
		{	
			long startTime = System.currentTimeMillis();
			String sql ="INSERT INTO TICKDATAV6 (ID, FLEET, LAT, LONGITUDE, TIME, CURRENTDATE, ESTIMATETIME, SPEED, HEADING, REASONS, LOG_REASON, DISTANCE_TRAVELED, ODOMETER, LOADTS, ACCEL, STATUS, FUEL_COUNTER, FUEL_UNITS, ADDRESS, ZONE, AIR_TEMP, AVG_RPM, AVG_LOAD, FUEL_LEVEL, OUTSIDE_PRESSURE, PEAK_RPM, AVERAGE_TORQUE, BOOST_PRESSURE, FFILTER_DPRESSURE, AFILTER_DPRESSURE, DPF_DPRESSURE, REGEN_STATUS, STARTDATE, ENDDATE, TRAVEL_TIME, TYPE_LOCATION)"
					+ " values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
			Connection conn = connectToDatabase();
			PreparedStatement ps = conn.prepareStatement(sql);

			final int batchSize = 1000;
			int count = 0;

			for (AssetData assets: data) {

				ps.setInt(1, assets.getId());
				ps.setInt(2, assets.getFleet());
				ps.setBigDecimal(3, assets.getLat());
				ps.setBigDecimal(4, assets.getLong());
				ps.setString(5, null);
				ps.setString(6, null);
				ps.setString(7, null);
				ps.setBigDecimal(8, assets.getSpeed());
				ps.setString(9, assets.getHeading());
				ps.setString(10, assets.getReasons());
				ps.setString(11, null);
				ps.setBigDecimal(12, assets.getDistance_traveled());
				ps.setBigDecimal(13, assets.getOdometer());
				ps.setString(14, assets.getLoadts());
				ps.setInt(15, assets.getAccel());
				ps.setString(16, assets.getStatus());
				ps.setBigDecimal(17, assets.getFuel_counter());
				ps.setString(18, assets.getFuel_units());
				ps.setString(19, assets.getAddress());
				ps.setString(20, assets.getZone());
				ps.setInt(21, assets.getAir_temp());
				ps.setInt(22, assets.getAvg_rpm());
				ps.setInt(23, assets.getAvg_load());
				ps.setInt(24, assets.getFuel_level());
				ps.setInt(25, assets.getOutside_pressure());
				ps.setInt(26, assets.getPeak_rpm());
				ps.setInt(27, assets.getAverage_torque());
				ps.setInt(28, assets.getBoost_pressure());
				ps.setInt(29, assets.getFfilter_dpressure());
				ps.setInt(30, assets.getAfilter_dpressure());
				ps.setInt(31, assets.getDpf_dpressure());
				ps.setInt(32, assets.getRegen_status());
				ps.setString(33, null);
				ps.setString(34, null);
				ps.setString(35, null);
				ps.setString(36, null);

				ps.addBatch();

				if(++count % batchSize == 0) {
					ps.executeBatch();
				}
			}
			ps.executeBatch(); // insert remaining records
			ps.close();

			long endTime   = System.currentTimeMillis();
			long totalTime = endTime - startTime;
			System.out.println("TOTAL TIME TAKEN TO INSERT DATA FROM XML INTO DB IS :" +totalTime + "ms");			
			conn.close();

		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	public Connection connectToDatabase() throws SQLException, ClassNotFoundException
	{
		Class.forName("oracle.jdbc.OracleDriver");
		Connection connObj=DriverManager.
				getConnection("jdbc:oracle:thin:@129.150.199.179:1521:PIKEDEV","C##PIKEDEV_DW","Admin123");

		return connObj;
	}

	public List<String> getFleetIds() 
	{
		long startTime = System.currentTimeMillis();
		String sql ="select DISTINCT fleet from W_ASSET_RAW_MASTER_STG where fleet is not null ";
		Statement stmt = null;
		ArrayList<String> fleetId = null;
		try
		{
			Connection conn = connectToDatabase();
			stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(sql);

			//Stores properties of a ResultSet object, including column count
			ResultSetMetaData rsmd = rs.getMetaData(); 
			int columnCount = rsmd.getColumnCount();

			fleetId = new ArrayList<>(columnCount); 
			while (rs.next()) {              
				int i = 1;
				while(i <= columnCount) {
					fleetId.add(rs.getString(i++));
				}
			}
			conn.close();
		} 
		catch (ClassNotFoundException | SQLException e) 
		{
			e.printStackTrace();
		}
		return fleetId;

	}
}

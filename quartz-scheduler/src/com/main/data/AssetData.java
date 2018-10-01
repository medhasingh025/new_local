package com.main.data;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.util.Date;


public class AssetData 
{
	private Integer id;
	private Integer fleet;
	private BigDecimal lat;
	private BigDecimal Long;
	private Date time;
	private BigDecimal speed;
	private String heading;
	private String reasons;
	private BigDecimal distance_traveled;
	private BigDecimal odometer;
	private String loadts;
	private Integer accel;
	private String status;
	private BigDecimal fuel_counter;
	private String fuel_units;	
	private String address;
	private String zone;
	private Integer air_temp;
	private Integer avg_rpm;
	private Integer avg_load;
	private Integer fuel_level;
	private Integer outside_pressure;
	private Integer peak_rpm;
	private Integer average_torque;
	private Integer boost_pressure;
	private Integer ffilter_dpressure;
	private Integer afilter_dpressure;
	private Integer dpf_dpressure;
	private Integer regen_status;
	
	public static final String 
	TIME="time";
	
	public AssetData()
	{
		
	}
	
	public AssetData(Integer id, Integer fleet, BigDecimal lat, BigDecimal l, Date time, BigDecimal speed, String heading,
			String reasons, BigDecimal distance_traveled, BigDecimal odometer, String loadts, Integer accel, String status,
			BigDecimal fuel_counter, String fuel_units, String address, String zone, Integer air_temp, Integer avg_rpm,
			Integer avg_load, Integer fuel_level, Integer outside_pressure, Integer peak_rpm, Integer average_torque,
			Integer boost_pressure, Integer ffilter_dpressure, Integer afilter_dpressure, Integer dpf_dpressure,
			Integer regen_status) {
		super();
		this.id = id;
		this.fleet = fleet;
		this.lat = lat;
		Long = l;
		this.time = time;
		this.speed = speed;
		this.heading = heading;
		this.reasons = reasons;
		this.distance_traveled = distance_traveled;
		this.odometer = odometer;
		this.loadts = loadts;
		this.accel = accel;
		this.status = status;
		this.fuel_counter = fuel_counter;
		this.fuel_units = fuel_units;
		this.address = address;
		this.zone = zone;
		this.air_temp = air_temp;
		this.avg_rpm = avg_rpm;
		this.avg_load = avg_load;
		this.fuel_level = fuel_level;
		this.outside_pressure = outside_pressure;
		this.peak_rpm = peak_rpm;
		this.average_torque = average_torque;
		this.boost_pressure = boost_pressure;
		this.ffilter_dpressure = ffilter_dpressure;
		this.afilter_dpressure = afilter_dpressure;
		this.dpf_dpressure = dpf_dpressure;
		this.regen_status = regen_status;
	}

	@Override
	public String toString() {
		DateFormat df = DateFormat.getDateInstance(DateFormat.LONG);
		return "AssetData [id=" + id + ", fleet=" + fleet + ", lat=" + lat + ", Long=" + Long + ", time=" + df.format(this.time)
				+ ", speed=" + speed + ", heading=" + heading + ", reasons=" + reasons + ", distance_traveled="
				+ distance_traveled + ", odometer=" + odometer + ", loadts=" + loadts + ", accel=" + accel + ", status="
				+ status + ", fuel_counter=" + fuel_counter + ", fuel_units=" + fuel_units + ", address=" + address
				+ ", zone=" + zone + ", air_temp=" + air_temp + ", avg_rpm=" + avg_rpm + ", avg_load=" + avg_load
				+ ", fuel_level=" + fuel_level + ", outside_pressure=" + outside_pressure + ", peak_rpm=" + peak_rpm
				+ ", average_torque=" + average_torque + ", boost_pressure=" + boost_pressure + ", ffilter_dpressure="
				+ ffilter_dpressure + ", afilter_dpressure=" + afilter_dpressure + ", dpf_dpressure=" + dpf_dpressure
				+ ", regen_status=" + regen_status + "]";
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getFleet() {
		return fleet;
	}

	public void setFleet(Integer fleet) {
		this.fleet = fleet;
	}

	public BigDecimal getLat() {
		return lat;
	}

	public void setLat(BigDecimal lat) {
		this.lat = lat;
	}

	public BigDecimal getLong() {
		return Long;
	}

	public void setLong(BigDecimal l) {
		Long = l;
	}

	public Date getTime() {
		return time;
	}

	public void setTime(Date date) {
		this.time = date;
	}

	public BigDecimal getSpeed() {
		return speed;
	}

	public void setSpeed(BigDecimal speed) {
		this.speed = speed;
	}

	public String getHeading() {
		return heading;
	}

	public void setHeading(String heading) {
		this.heading = heading;
	}

	public String getReasons() {
		return reasons;
	}

	public void setReasons(String reasons) {
		this.reasons = reasons;
	}

	public BigDecimal getDistance_traveled() {
		return distance_traveled;
	}

	public void setDistance_traveled(BigDecimal distance_traveled) {
		this.distance_traveled = distance_traveled;
	}

	public BigDecimal getOdometer() {
		return odometer;
	}

	public void setOdometer(BigDecimal odometer) {
		this.odometer = odometer;
	}

	public String getLoadts() {
		return loadts;
	}

	public void setLoadts(String loadts) {
		this.loadts = loadts;
	}

	public Integer getAccel() {
		return accel;
	}

	public void setAccel(Integer accel) {
		this.accel = accel;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public BigDecimal getFuel_counter() {
		return fuel_counter;
	}

	public void setFuel_counter(BigDecimal fuel_counter) {
		this.fuel_counter = fuel_counter;
	}

	public String getFuel_units() {
		return fuel_units;
	}

	public void setFuel_units(String fuel_units) {
		this.fuel_units = fuel_units;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getZone() {
		return zone;
	}

	public void setZone(String zone) {
		this.zone = zone;
	}

	public Integer getAir_temp() {
		return air_temp;
	}

	public void setAir_temp(Integer air_temp) {
		this.air_temp = air_temp;
	}

	public Integer getAvg_rpm() {
		return avg_rpm;
	}

	public void setAvg_rpm(Integer avg_rpm) {
		this.avg_rpm = avg_rpm;
	}

	public Integer getAvg_load() {
		return avg_load;
	}

	public void setAvg_load(Integer avg_load) {
		this.avg_load = avg_load;
	}

	public Integer getFuel_level() {
		return fuel_level;
	}

	public void setFuel_level(Integer fuel_level) {
		this.fuel_level = fuel_level;
	}

	public Integer getOutside_pressure() {
		return outside_pressure;
	}

	public void setOutside_pressure(Integer outside_pressure) {
		this.outside_pressure = outside_pressure;
	}

	public Integer getPeak_rpm() {
		return peak_rpm;
	}

	public void setPeak_rpm(Integer peak_rpm) {
		this.peak_rpm = peak_rpm;
	}

	public Integer getAverage_torque() {
		return average_torque;
	}

	public void setAverage_torque(Integer average_torque) {
		this.average_torque = average_torque;
	}

	public Integer getBoost_pressure() {
		return boost_pressure;
	}

	public void setBoost_pressure(Integer boost_pressure) {
		this.boost_pressure = boost_pressure;
	}

	public Integer getFfilter_dpressure() {
		return ffilter_dpressure;
	}

	public void setFfilter_dpressure(Integer ffilter_dpressure) {
		this.ffilter_dpressure = ffilter_dpressure;
	}

	public Integer getAfilter_dpressure() {
		return afilter_dpressure;
	}

	public void setAfilter_dpressure(Integer afilter_dpressure) {
		this.afilter_dpressure = afilter_dpressure;
	}

	public Integer getDpf_dpressure() {
		return dpf_dpressure;
	}

	public void setDpf_dpressure(Integer dpf_dpressure) {
		this.dpf_dpressure = dpf_dpressure;
	}

	public Integer getRegen_status() {
		return regen_status;
	}

	public void setRegen_status(Integer regen_status) {
		this.regen_status = regen_status;
	}
	
	}
package com.meep.challenge.dto;

import lombok.Data;

import java.math.BigDecimal;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@Entity
@Table(name = "vehicle")
public class VehicleEntity {
    @Id
    @GeneratedValue
    private Integer id;
    
    private String vehicleId;
	private String name;
	private Double x;
	private Double y;
	private String licencePlate;
	private Integer range;
	private Double batteryLevel;
	private Integer seats;
	private String model;
	private String resourceImageId;
	private BigDecimal pricePerMinuteParking;
	private BigDecimal pricePerMinuteDriving;
	private Boolean realTimeData;
	private String engineType;
	private String resourceType;
	private Integer companyZoneId;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getVehicleId() {
		return vehicleId;
	}
	public void setVehicleId(String vehicleId) {
		this.vehicleId = vehicleId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Double getX() {
		return x;
	}
	public void setX(Double x) {
		this.x = x;
	}
	public Double getY() {
		return y;
	}
	public void setY(Double y) {
		this.y = y;
	}
	public String getLicencePlate() {
		return licencePlate;
	}
	public void setLicencePlate(String licencePlate) {
		this.licencePlate = licencePlate;
	}
	public Integer getRange() {
		return range;
	}
	public void setRange(Integer range) {
		this.range = range;
	}
	public Double getBatteryLevel() {
		return batteryLevel;
	}
	public void setBatteryLevel(Double batteryLevel) {
		this.batteryLevel = batteryLevel;
	}
	public Integer getSeats() {
		return seats;
	}
	public void setSeats(Integer seats) {
		this.seats = seats;
	}
	public String getModel() {
		return model;
	}
	public void setModel(String model) {
		this.model = model;
	}
	public String getResourceImageId() {
		return resourceImageId;
	}
	public void setResourceImageId(String resourceImageId) {
		this.resourceImageId = resourceImageId;
	}
	public BigDecimal getPricePerMinuteParking() {
		return pricePerMinuteParking;
	}
	public void setPricePerMinuteParking(BigDecimal pricePerMinuteParking) {
		this.pricePerMinuteParking = pricePerMinuteParking;
	}
	public BigDecimal getPricePerMinuteDriving() {
		return pricePerMinuteDriving;
	}
	public void setPricePerMinuteDriving(BigDecimal pricePerMinuteDriving) {
		this.pricePerMinuteDriving = pricePerMinuteDriving;
	}
	public Boolean getRealTimeData() {
		return realTimeData;
	}
	public void setRealTimeData(Boolean realTimeData) {
		this.realTimeData = realTimeData;
	}
	public String getEngineType() {
		return engineType;
	}
	public void setEngineType(String engineType) {
		this.engineType = engineType;
	}
	public String getResourceType() {
		return resourceType;
	}
	public void setResourceType(String resourceType) {
		this.resourceType = resourceType;
	}
	public Integer getCompanyZoneId() {
		return companyZoneId;
	}
	public void setCompanyZoneId(Integer companyZoneId) {
		this.companyZoneId = companyZoneId;
	}
	
	
}

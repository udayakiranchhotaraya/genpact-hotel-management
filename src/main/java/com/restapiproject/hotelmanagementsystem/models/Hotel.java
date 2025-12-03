package com.restapiproject.hotelmanagementsystem.models;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class Hotel {

	public Hotel(Long id, String name, String address, Integer totalRooms, Integer availableRooms,
			BigDecimal pricePerNight) {
		this.id = id;
		this.name = name;
		this.address = address;
		this.totalRooms = totalRooms;
		this.availableRooms = availableRooms;
		this.pricePerNight = pricePerNight;
	}

	public Hotel() {
	}
	
	public Hotel(Long id, String name, String address, Integer total_rooms, Integer available_rooms,
			BigDecimal price_per_night, LocalDateTime created_at, LocalDateTime updated_at) {
		this.id = id;
		this.name = name;
		this.address = address;
		this.totalRooms = total_rooms;
		this.availableRooms = available_rooms;
		this.pricePerNight = price_per_night;
		this.createdAt = created_at;
		this.updatedAt = updated_at;
	}

	private Long id;
	private String name;
	private String address;
	private Integer totalRooms;
	private Integer availableRooms;
	private BigDecimal pricePerNight;
	private LocalDateTime createdAt;
	private LocalDateTime updatedAt;
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public Integer getTotalRooms() {
		return totalRooms;
	}

	public void setTotalRooms(Integer total_rooms) {
		this.totalRooms = total_rooms;
	}

	public Integer getAvailableRooms() {
		return availableRooms;
	}

	public void setAvailableRooms(Integer available_rooms) {
		this.availableRooms = available_rooms;
	}

	public BigDecimal getPricePerNight() {
		return pricePerNight;
	}

	public void setPricePerNight(BigDecimal price_per_night) {
		this.pricePerNight = price_per_night;
	}

	public LocalDateTime getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(LocalDateTime created_at) {
		this.createdAt = created_at;
	}

	public LocalDateTime getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(LocalDateTime updated_at) {
		this.updatedAt = updated_at;
	}
}

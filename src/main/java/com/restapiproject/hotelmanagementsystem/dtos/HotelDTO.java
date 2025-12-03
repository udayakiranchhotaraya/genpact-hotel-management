package com.restapiproject.hotelmanagementsystem.dtos;

import java.math.BigDecimal;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class HotelDTO {

	public HotelDTO() {
	}

	public HotelDTO(Long id, @NotBlank(message = "name is required") String name,
			@NotBlank(message = "address is required") String address,
			@NotNull(message = "total_rooms is required") @Min(value = 1, message = "total_rooms must be >= 1") Integer total_rooms,
			@NotNull(message = "available_rooms is required") @Min(value = 0, message = "available_rooms must be >= 0") Integer available_rooms,
			@NotNull(message = "price_per_night is required") @DecimalMin(value = "0.0", message = "price_per_night must be >= 0") BigDecimal price_per_night) {
		this.id = id;
		this.name = name;
		this.address = address;
		this.totalRooms = total_rooms;
		this.availableRooms = available_rooms;
		this.pricePerNight = price_per_night;
	}

	private Long id;
	
	@NotBlank(message = "name is required")
	private String name;
	
	@NotBlank(message = "address is required")
	private String address;
	
	@NotNull(message = "total_rooms is required")
	@Min(value = 1, message = "total_rooms must be >= 1")
	private Integer totalRooms;
	
	@NotNull(message = "available_rooms is required")
	@Min(value = 0, message = "available_rooms must be >= 0")
	private Integer availableRooms;
	
	@NotNull(message = "price_per_night is required")
	@DecimalMin(value = "0.0", message = "price_per_night must be >= 0")
	private BigDecimal pricePerNight;

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
	
}

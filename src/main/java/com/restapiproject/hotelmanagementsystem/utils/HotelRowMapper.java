package com.restapiproject.hotelmanagementsystem.utils;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.restapiproject.hotelmanagementsystem.models.Hotel;

public class HotelRowMapper implements RowMapper<Hotel> {

	@Override
	public Hotel mapRow(ResultSet rs, int rowNum) throws SQLException {
		// TODO Auto-generated method stub
		
		Hotel hotel = new Hotel();
		hotel.setId(rs.getLong("id"));
		hotel.setName(rs.getString("name"));
		hotel.setAddress(rs.getString("address"));
		hotel.setTotalRooms(rs.getInt("total_rooms"));
		hotel.setAvailableRooms(rs.getInt("available_rooms"));
		hotel.setPricePerNight(rs.getBigDecimal("price_per_night"));
		java.sql.Timestamp createdAt = rs.getTimestamp("created_at");
		if (createdAt != null) hotel.setCreatedAt(createdAt.toLocalDateTime());
		java.sql.Timestamp updatedAt = rs.getTimestamp("updated_at");
		if (updatedAt != null) hotel.setCreatedAt(updatedAt.toLocalDateTime());
		
		return hotel;
	}

}

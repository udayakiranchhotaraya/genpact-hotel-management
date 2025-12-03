package com.restapiproject.hotelmanagementsystem.repository;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;
import java.util.Optional;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import com.restapiproject.hotelmanagementsystem.models.Hotel;
import com.restapiproject.hotelmanagementsystem.utils.HotelRowMapper;

@Repository
public class HotelDAOImpl implements HotelDAO {
	
	public HotelDAOImpl(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	private final JdbcTemplate jdbcTemplate;

	@Override
	public Hotel save(Hotel hotel) {
		// TODO Auto-generated method stub
		
		String sql = "INSERT INTO Hotels (name, address, total_rooms, available_rooms, price_per_night) VALUES (?, ?, ?, ?, ?);";
		
		KeyHolder keyHolder = new GeneratedKeyHolder();
		
		jdbcTemplate.update(connection -> {
			PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			preparedStatement.setString(1, hotel.getName());
			preparedStatement.setString(2, hotel.getAddress());
			preparedStatement.setInt(3, hotel.getTotalRooms());
			preparedStatement.setInt(4, hotel.getAvailableRooms());
			preparedStatement.setBigDecimal(5, hotel.getPricePerNight());
			
			return preparedStatement;
		}, keyHolder);
		
		Number key = keyHolder.getKey();
        if (key != null) {
            hotel.setId(key.longValue());
        }
		return (key != null) ? hotel : null;
	}

	@Override
	public Optional<Hotel> findById(Long id) {
		// TODO Auto-generated method stub
		
		try {
			String sql = "SELECT * FROM Hotels WHERE id = ?;";
			Hotel hotel = jdbcTemplate.queryForObject(sql, new HotelRowMapper(), id);
			
			return Optional.ofNullable(hotel);
		} catch (Exception e) {
			// TODO: handle exception
			return Optional.empty();
		}
	}

	@Override
	public List<Hotel> findAll() {
		// TODO Auto-generated method stub
		
		try {
			String sql = "SELECT * FROM Hotels;";
			List<Hotel> hotels = jdbcTemplate.query(sql, new HotelRowMapper());
			
			return hotels;
		} catch (Exception e) {
			// TODO: handle exception
			return null;
		}
	}

	@Override
	public int update(Hotel hotel) {
		// TODO Auto-generated method stub
		
		String sql = "Update Hotels SET name = ?, address = ? , total_rooms = ?, available_rooms = ?, price_per_night = ? WHERE id = ?;";
		return jdbcTemplate.update(sql, hotel.getName(), hotel.getAddress(), hotel.getTotalRooms(), hotel.getAvailableRooms(), hotel.getPricePerNight(), hotel.getId());
	}

	@Override
	public int deleteById(Long id) {
		// TODO Auto-generated method stub
		
		String sql = "DELETE FROM Hotels WHERE id = ?;";
		return jdbcTemplate.update(sql, id);
	}

	@Override
	public List<Hotel> searchHotelsByName(String name) {
		// TODO Auto-generated method stub
		
		try {
			String sql = "SELECT * FROM Hotels WHERE LOWER(name) LIKE CONCAT('%', LOWER(?), '%');";
			List<Hotel> hotels = jdbcTemplate.query(sql, new HotelRowMapper(), name);
			
			return hotels;
		} catch (Exception e) {
			// TODO: handle exception
			return null;
		}
	}

}

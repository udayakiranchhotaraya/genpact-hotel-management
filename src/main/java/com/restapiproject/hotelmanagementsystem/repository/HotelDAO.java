package com.restapiproject.hotelmanagementsystem.repository;

import java.util.List;
import java.util.Optional;

import com.restapiproject.hotelmanagementsystem.models.Hotel;

public interface HotelDAO {

	Hotel save(Hotel hotel);
	Optional<Hotel> findById(Long id);
	List<Hotel> findAll();
	List<Hotel> searchHotelsByName(String name);
	int update(Hotel hotel);
	int deleteById(Long id);
}

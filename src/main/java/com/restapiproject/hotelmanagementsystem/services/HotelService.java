package com.restapiproject.hotelmanagementsystem.services;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.restapiproject.hotelmanagementsystem.exceptions.ResourceNotFoundException;
import com.restapiproject.hotelmanagementsystem.models.Hotel;
import com.restapiproject.hotelmanagementsystem.repository.HotelDAO;

@Service
public class HotelService {

	public HotelService(HotelDAO hotelDAO) {
		this.hotelDAO = hotelDAO;
	}

	public HotelDAO hotelDAO;
	
	public Hotel addHotel(Hotel hotel) {
		return hotelDAO.save(hotel);
	}
	
	public Hotel findHotelById(Long id) {
		return hotelDAO.findById(id).orElseThrow(() -> new ResourceNotFoundException("Hotel with ID '" + id + "' not found."));
	}
	
	public List<Hotel> listAllHotels() {
		return hotelDAO.findAll();
	}
	
	public List<Hotel> searchHotelByName(String name) {
		if (name == null) {
			return listAllHotels();
		}
		return hotelDAO.searchHotelsByName(name);
	}
	
	public Hotel updateHotel(Long id, Hotel hotel) {
		Hotel existingHotel = findHotelById(id);
		existingHotel.setName(hotel.getName());
		existingHotel.setAddress(hotel.getAddress());
		existingHotel.setTotalRooms(hotel.getTotalRooms());
		existingHotel.setAvailableRooms(hotel.getAvailableRooms());
		existingHotel.setPricePerNight(hotel.getPricePerNight());
		
		if (hotelDAO.update(existingHotel) <= 0) {
			throw new RuntimeException("Update failed for Hotel ID: " + id);
		}
		return existingHotel;
	}
	
	public void deleteHotel(Long id) {
		findHotelById(id);
		
		if (hotelDAO.deleteById(id) <= 0) {
			throw new RuntimeException("Delete failed for Hotel ID: " + id);
		}
		return;
	}
}

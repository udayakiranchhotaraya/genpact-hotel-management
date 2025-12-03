package com.restapiproject.hotelmanagementsystem.controllers;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.ResponseEntity;
import org.springframework.http.ResponseEntity.HeadersBuilder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.restapiproject.hotelmanagementsystem.dtos.HotelDTO;
import com.restapiproject.hotelmanagementsystem.exceptions.ResourceNotFoundException;
import com.restapiproject.hotelmanagementsystem.models.Hotel;
import com.restapiproject.hotelmanagementsystem.services.HotelService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/hotels")
public class HotelController {

	public HotelController(HotelService hotelService) {
		this.hotelService = hotelService;
	}

	private final HotelService hotelService;
	
	private Hotel dtoToEntity(HotelDTO hotelDTO) {
		Hotel hotel = new Hotel();
		hotel.setId(hotelDTO.getId());
		hotel.setName(hotelDTO.getName());
		hotel.setAddress(hotelDTO.getAddress());
		hotel.setTotalRooms(hotelDTO.getTotalRooms());
		hotel.setAvailableRooms(hotelDTO.getAvailableRooms());
		hotel.setPricePerNight(hotelDTO.getPricePerNight());
		
		return hotel;
	}
	
	private HotelDTO entityToDTO(Hotel hotel) {
		HotelDTO hotelDTO = new HotelDTO();
		hotelDTO.setId(hotel.getId());
		hotelDTO.setName(hotel.getName());
		hotelDTO.setAddress(hotel.getAddress());
		hotelDTO.setTotalRooms(hotel.getTotalRooms());
		hotelDTO.setAvailableRooms(hotel.getAvailableRooms());
		hotelDTO.setPricePerNight(hotel.getPricePerNight());
		
		return hotelDTO;
	}
	
	@GetMapping("")
	public ResponseEntity<List<HotelDTO>> getAllHotels() {
		
		// FETCH LIST OF HOTELS FROM DB;
		List<Hotel> hotelsList = hotelService.listAllHotels();
		// CONVERTING LIST OF HOTEL ENTITIES INTO CORRESPONDING DTOS
		List<HotelDTO> hotelDTOs = hotelsList.stream().map(this::entityToDTO).collect(Collectors.toList());
		
		/*
		 * RETURN HTTP RESPONSE
		 * ResponseEntity -- SPRING WRAPPER FOR HTTP RESPONSE
		 * ALLOWS YOU TO SET STATUS CODE, HEADERS, BODY (DTO OBJECTS)
		 */
		return ResponseEntity.ok(hotelDTOs);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<HotelDTO> getHotelById(@PathVariable Long id) {
		
		Hotel hotel = hotelService.findHotelById(id);
		
		return ResponseEntity.ok(entityToDTO(hotel));
	}
	
	@PostMapping("")
	public ResponseEntity<HotelDTO> createHotel(@Valid @RequestBody HotelDTO hotelDTO) {
		
		Hotel hotel = dtoToEntity(hotelDTO);
		Hotel createdHotel = hotelService.addHotel(hotel);
		
		return ResponseEntity.created(URI.create("/api/hotels/" + createdHotel.getId())).body(entityToDTO(createdHotel));
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<HotelDTO> updateHotel(@PathVariable Long id, @Valid @RequestBody HotelDTO hotelDTO) {
		
		Hotel hotel = dtoToEntity(hotelDTO);
		Hotel updatedHotel = hotelService.updateHotel(id, hotel);
		
		return ResponseEntity.ok(entityToDTO(updatedHotel));
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteHotel(@PathVariable Long id) {
		
		hotelService.deleteHotel(id);
		
		return ResponseEntity.noContent().build();
	}
	
	@GetMapping("/search")
	public ResponseEntity<List<HotelDTO>> searchHotelByName(@RequestParam(name = "name", required = false) String name) {
		List<Hotel> hotelsList = hotelService.searchHotelByName(name);
		if (hotelsList.isEmpty()) {
			throw new ResourceNotFoundException("Hotel with name '" + name + "' not found.");
		}
		List<HotelDTO> hotelDTOs = hotelsList.stream().map(this::entityToDTO).collect(Collectors.toList());

		return ResponseEntity.ok(hotelDTOs);
	}
	
}
